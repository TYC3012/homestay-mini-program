<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="房间名字" prop="roomName">
        <el-input
          v-model="queryParams.roomName"
          placeholder="请输入房间名字"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="房间号" prop="roomNumber">
        <el-input
          v-model="queryParams.roomNumber"
          placeholder="请输入房间号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['business:room:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['business:room:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['business:room:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:room:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="roomList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="关联的民宿" align="center" prop="hotelName" />
      <el-table-column label="房间名字" align="center" prop="roomName" />
      <el-table-column label="房间号" align="center" prop="roomNumber" />
      <el-table-column label="房间主图" align="center" prop="mainImg" >
        <template slot-scope="scope">
          <ImagePreview height="50px" :src="scope.row.mainImg" />
        </template>
      </el-table-column>
      <el-table-column label="价格" align="center" prop="price" />
      <el-table-column label="房间区域" align="center" prop="area" />
      <el-table-column label="可入住人数" align="center" prop="occupancy" />
      <el-table-column label="床型" align="center" prop="bedType" />
      <el-table-column label="楼层" align="center" prop="floor" />
      <el-table-column label="备注说明" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:room:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:room:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改房间信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="民宿" prop="hotelId">
          <el-select v-model="form.hotelId" placeholder="请选择民宿">
            <el-option
              v-for="item in hotelOptions"
              :key="item.id"
              :label="item.homeName"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="房间名字" prop="roomName">
          <el-input v-model="form.roomName" placeholder="请输入房间名字" />
        </el-form-item>
        <el-form-item label="房间号" prop="roomNumber">
          <el-input v-model="form.roomNumber" placeholder="请输入房间号" />
        </el-form-item>
        <el-form-item label="房间主图" prop="mainImg">
          <ImageUpload :limit="1" :fileListEcho="form.mainImg" v-model="form.mainImg" />
        </el-form-item>
        <el-form-item label="房间详细图" prop="mainImg">
          <ImageUpload :fileListEcho="form.detailsImg" v-model="form.detailsImg" />
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入价格" />
        </el-form-item>
        <el-form-item label="房间区域" prop="area">
          <el-input v-model="form.area" placeholder="请输入房间区域" />
        </el-form-item>
        <el-form-item label="可入住人数" prop="occupancy">
          <el-input v-model="form.occupancy" placeholder="请输入可入住人数" />
        </el-form-item>
        <el-form-item label="楼层" prop="floor">
          <el-input v-model="form.floor" placeholder="请输入楼层" />
        </el-form-item>
        <el-form-item label="床型" prop="floor">
          <el-input v-model="form.bedType" placeholder="请输入床型" />
        </el-form-item>
        <el-form-item label="备注说明" prop="remark">
          <el-input type="textarea" v-model="form.remark" placeholder="请输入备注说明" />
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRoom, getRoom, delRoom, addRoom, updateRoom } from "@/api/business/room";
import { allList } from "@/api/business/hotel";

export default {
  name: "Room",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 房间信息表格数据
      roomList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        hotelId: null,
        roomName: null,
        roomNumber: null,
        mainImg: null,
        price: null,
        area: null,
        occupancy: null,
        bedType: null,
        floor: null,
        isDelete: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      hotelOptions:[],
    };
  },
  created() {
    this.getHotelList();
    this.getList();
  },
  methods: {
    /** 查询酒店信息列表 */
    getHotelList() {
      this.loading = true;
      allList(this.queryParams).then(response => {
        console.log("response::",response)
        this.hotelOptions=response.data;
        this.loading = false;
      });
    },
    /** 查询房间信息列表 */
    getList() {
      this.loading = true;
      listRoom(this.queryParams).then(response => {
        this.roomList = response.rows;
        this.total = Number(response.total);
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        hotelId: null,
        roomName: null,
        roomNumber: null,
        mainImg: null,
        price: null,
        area: null,
        occupancy: null,
        bedType: null,
        floor: null,
        remark: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null,
        isDelete: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加房间信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRoom(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改房间信息";
      });
    },
    /** 提交按钮 */
    submitForm() {

      let obj=this.hotelOptions.find(item=>item.id==this.form.hotelId)
      if(obj){
        this.form.hotelName=obj.homeName;
      }
      console.log("提交房间信息时的参数::",this.form)
      // return
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRoom(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRoom(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除房间信息编号为"' + ids + '"的数据项？').then(function() {
        return delRoom(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/room/export', {
        ...this.queryParams
      }, `room_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
