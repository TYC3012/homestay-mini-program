<template>
  <div>
    <el-upload
      ref="upload"
      :limit="1"
      accept=".xlsx, .xls"
      :action="upload.url"
      :headers="upload.headers"
      :file-list="upload.fileList"
      :on-progress="handleFileUploadProgress"
      :on-success="handleFileSuccess"
      :auto-upload="false">
      <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
      <el-button style="margin-left: 10px;" size="small" type="success" :loading="upload.isUploading"
                 @click="submitUpload">上传到服务器
      </el-button>
      <div slot="tip" class="el-upload__tip">只能上传excel文件，且不超过500kb</div>
    </el-upload>


    <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column
        prop="id"
        label="序号"
        width="150">
      </el-table-column>
      <el-table-column
        prop="address"
        label="文件地址"
        width="300">
      </el-table-column>
      <el-table-column
        prop="fileName"
        label="文件名称">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="100">
        <template slot-scope="scope">
          <el-button @click="downLoadFile(scope.row)" type="text" size="small">下载</el-button>
          <el-button @click="deleteClick(scope.row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>


    </el-table>
    <!--       页面添加分页组件，传入分页变量-->
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getAllFileList"
    />
  </div>
</template>

<script>
import {deleteByName, getAllFiles, getAllFile} from "@/api/document/document";
import {getToken} from "@/utils/auth";
import {download} from "@/utils/request";


export default {
  name: 'fileManageMent',
  data() {
    return {
      // 上传参数
      upload: {
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/common/upload",
        // 上传的文件列表
        fileList: []
      },
      tableData: [],
      // 一般在查询参数中定义分页变量
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      result: { // 请求结果
        total: 0,
        rows: []
      },
      total:0
    };
  },
  created() {
    // this.getList();
    this.getAllFileList()
  },
  methods: {
    // 文件提交处理
    submitUpload() {
      this.$refs.upload.submit();
      console.log("刷新页面")
      this.getAllFileList();
    },
// 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
// 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.isUploading = false;
      this.form.filePath = response.url;
      this.msgSuccess(response.msg);
    },
    // 获取文件列表
    getAllFileList() {
      // getAllFiles().then(response => {
      //   console.log(response)
      //   this.tableData = response.data;
      //   this.result = response;
      // });
      // 调用后台方法，传入参数 获取结果
      /*listUser(this.queryParams).then(response => {
          this.userList = response.rows;
          this.total = response.total;
        }*/
      getAllFile(this.queryParams).then(response => {
        console.log(response)
        this.tableData = response.rows;
        this.total = response.total;
        console.log(this.total)
        console.log(this.result.total)
      })
    },
    deleteClick(event) {
      // console.log(event)
      deleteByName(event.fileName).then(response => {
        this.getAllFileList()
      })
    },
    downLoadFile(row) {
      let value = "/profile/upload/file/" + row.fileName
      let list = value.split("/");
      let fileName = list[list.length - 1];
      //这是文件路径参数，因为download函数需要传三个参数，这是第二个参数
      let params = {
        resource: value
      }
      download("/common/download/resource", params, fileName);
      console.log("文件名");
      console.log("value");
    },
  }
};
</script>
