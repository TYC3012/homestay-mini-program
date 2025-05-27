<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="店主id" prop="bossId">
        <el-input
          v-model="queryParams.bossId"
          placeholder="请输入店主id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="民宿名字" prop="homeName">
        <el-input
          v-model="queryParams.homeName"
          placeholder="请输入民宿名字"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所在省份" prop="province">
        <el-input
          v-model="queryParams.province"
          placeholder="请输入所在省份"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所在城市" prop="city">
        <el-input
          v-model="queryParams.city"
          placeholder="请输入所在城市"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="名宿地址" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入名宿地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否营业，0：否，1：是" prop="asTrade">
        <el-input
          v-model="queryParams.asTrade"
          placeholder="请输入是否营业，0：否，1：是"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="删除标志：0 未删除，1已删除" prop="isDelete">
        <el-input
          v-model="queryParams.isDelete"
          placeholder="请输入删除标志：0 未删除，1已删除"
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
          v-hasPermi="['system:homestay:add']"
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
          v-hasPermi="['system:homestay:edit']"
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
          v-hasPermi="['system:homestay:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:homestay:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="homestayList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="店主id" align="center" prop="bossId" />
      <el-table-column label="民宿名字" align="center" prop="homeName" />
      <el-table-column label="民宿图片" align="center" prop="homeFile" />
      <el-table-column label="所在省份" align="center" prop="province" />
      <el-table-column label="所在城市" align="center" prop="city" />
      <el-table-column label="名宿地址" align="center" prop="address" />
      <el-table-column label="是否营业，0：否，1：是" align="center" prop="asTrade" />
      <el-table-column label="备注说明" align="center" prop="remark" />
      <el-table-column label="删除标志：0 未删除，1已删除" align="center" prop="isDelete" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:homestay:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:homestay:remove']"
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

    <!-- 添加或修改名宿信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="店主id" prop="bossId">
          <el-input v-model="form.bossId" placeholder="请输入店主id" />
        </el-form-item>
        <el-form-item label="民宿名字" prop="homeName">
          <el-input v-model="form.homeName" placeholder="请输入民宿名字" />
        </el-form-item>
        <el-form-item label="民宿图片" prop="homeFile">
          <file-upload v-model="form.homeFile"/>
        </el-form-item>
        <el-form-item label="所在省份" prop="province">
          <el-input v-model="form.province" placeholder="请输入所在省份" />
        </el-form-item>
        <el-form-item label="所在城市" prop="city">
          <el-input v-model="form.city" placeholder="请输入所在城市" />
        </el-form-item>
        <el-form-item label="名宿地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入名宿地址" />
        </el-form-item>
        <el-form-item label="是否营业，0：否，1：是" prop="asTrade">
          <el-input v-model="form.asTrade" placeholder="请输入是否营业，0：否，1：是" />
        </el-form-item>
        <el-form-item label="备注说明" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注说明" />
        </el-form-item>
        <el-form-item label="删除标志：0 未删除，1已删除" prop="isDelete">
          <el-input v-model="form.isDelete" placeholder="请输入删除标志：0 未删除，1已删除" />
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
import { listHomestay, getHomestay, delHomestay, addHomestay, updateHomestay } from "@/api/business/homestay";

export default {
  name: "Homestay",
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
      // 名宿信息表格数据
      homestayList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bossId: null,
        homeName: null,
        homeFile: null,
        province: null,
        city: null,
        address: null,
        asTrade: null,
        isDelete: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询名宿信息列表 */
    getList() {
      this.loading = true;
      listHomestay(this.queryParams).then(response => {
        this.homestayList = response.rows;
        this.total = response.total;
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
        bossId: null,
        homeName: null,
        homeFile: null,
        province: null,
        city: null,
        address: null,
        asTrade: null,
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
      this.title = "添加名宿信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getHomestay(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改名宿信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateHomestay(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addHomestay(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除名宿信息编号为"' + ids + '"的数据项？').then(function() {
        return delHomestay(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/homestay/export', {
        ...this.queryParams
      }, `homestay_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
