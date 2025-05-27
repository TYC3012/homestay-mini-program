package com.homestay.project.system.controller;


import com.homestay.common.utils.SecurityUtils;
import com.homestay.common.utils.StringUtils;
import com.homestay.common.utils.file.FileUtils;

import com.homestay.framework.web.controller.BaseController;
import com.homestay.framework.web.domain.AjaxResult;
import com.homestay.framework.web.page.TableDataInfo;
import com.homestay.project.system.domain.FileManagement;
import com.homestay.project.system.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.homestay.common.utils.PageUtils.startPage;

@RestController
@RequestMapping("fileManagement/file")
public class FileController extends BaseController {
    @Autowired
    private FileService fileService;

    @GetMapping("test")
    public String getAllFiles() {
        System.out.println("成功");
        return "成功";
    }

    @GetMapping("/getPage")
    public AjaxResult getPage() {
        return fileService.getPage();
    }

    @GetMapping("/getAllFileWithPage")
    public TableDataInfo getAllFileWithPage(HashMap<String, String> map) {
        startPage();
        String userName = SecurityUtils.getLoginUser().getUser().getUserName();
        File dir = new File("E:\\项目模板\\vue2单模块版本\\single-module\\uploadPath\\file\\");

        List<FileManagement> list = listAllFile(dir);
        //admin具有所有权限
        if ("admin".equals(userName)) {

            return getDataTable(list);
        } else {
//                删除数据不是自己名字的
            list.removeIf(o -> !o.getFileName().contains(userName));
        }
        return getDataTable(list);
    }

    public List<FileManagement> listAllFile(File f) {
        ArrayList<FileManagement> list = new ArrayList<>();
        File[] files = f.listFiles();
        int i = 1;
        for (File file : Objects.requireNonNull(files)) {
            FileManagement fileManagement = new FileManagement();
            fileManagement.setId(i);
            fileManagement.setFileName(file.getName());
            fileManagement.setAddress("E:\\项目模板\\vue2单模块版本\\single-module\\uploadPath\\file\\");
            i += 1;
            list.add(fileManagement);
            System.out.println(file);
            if (file.isDirectory())
                listAllFile(file);

        }
        return list;
    }

    /**
     * 根据文件名字删除
     *
     * @return AjaxResult
     */
    @DeleteMapping("/deleteByName")
    public AjaxResult deleteByName(@RequestParam String name) {
        return fileService.deleteByName(name);
    }

    /**
     *  文件下载
     */
    /**
     * 本地资源通用下载
     */
    @GetMapping("/download")
    public void resourceDownload(String fileName, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 本地资源路径
        String localPath = "E:\\项目模板\\vue2单模块版本\\single-module\\uploadPath\\file\\";
        // 数据库资源地址
        String downloadPath = localPath + fileName;
        // 下载名称
        String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, downloadName));
        FileUtils.writeBytes(downloadPath, response.getOutputStream());
    }
}
