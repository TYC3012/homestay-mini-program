package com.homestay.project.system.service.impl;


import com.homestay.common.utils.SecurityUtils;

import com.homestay.framework.web.domain.AjaxResult;
import com.homestay.project.system.domain.FileManagement;
import com.homestay.project.system.service.FileService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service
public class FileServiceImpl implements FileService {
    @Override
    public AjaxResult getPage() {
        String userName = SecurityUtils.getLoginUser().getUser().getUserName();
        File dir = new File("D:\\ruoyi\\uploadPath\\upload\\file");

        List<FileManagement> list = listAllFile(dir);
        //admin具有所有权限
        if ("admin".equals(userName)) {

            return AjaxResult.success(list);
        } else {
//                删除数据不是自己名字的
            list.removeIf(o -> !o.getFileName().contains(userName));
        }

        return AjaxResult.success(list);
    }

    /**
     * 递归获取所有文件
     *
     * @param f
     * @return List
     */
    public List<FileManagement> listAllFile(File f) {
        ArrayList<FileManagement> list = new ArrayList<>();
        File[] files = f.listFiles();
        int i = 1;
        for (File file : Objects.requireNonNull(files)) {
            FileManagement fileManagement = new FileManagement();
            fileManagement.setId(i);
            fileManagement.setFileName(file.getName());
            fileManagement.setAddress("D:\\ruoyi\\uploadPath\\upload\\file");
            i += 1;
            list.add(fileManagement);
            System.out.println(file);
            if (file.isDirectory())
                listAllFile(file);

        }
        return list;
    }

    @Override
    public AjaxResult deleteByName(String name) {
        String userName = SecurityUtils.getLoginUser().getUser().getUserName();
        if (!"admin".equals(userName)) {
            return AjaxResult.error("普通用户无法删除文件");
        }
        File file = new File("D:\\ruoyi\\uploadPath\\upload\\file\\" + name);
        boolean b = false;
        if (file.exists()) {
            System.gc();    //加上确保文件能删除，不然可能删不掉
            b = file.delete();
        }
        return b ? AjaxResult.success() : AjaxResult.error();
    }

    @Override
    public ResponseEntity<FileSystemResource> downloadByName(String name, HttpServletResponse response) {
        File file = new File("D:\\ruoyi\\uploadPath\\upload\\file\\" + name);
//        if (file.exists()) {
//            String s = new String(file.getName().getBytes(), StandardCharsets.UTF_8);
//            response.setHeader("Content-Disposition", "attachment;filename=" + s);
//            try (FileInputStream inputStream = new FileInputStream(file);
//                 OutputStream outputStream = response.getOutputStream()) {
//                //设置Content-Disposition
//                IOUtils.copy(inputStream, outputStream);
//                response.flushBuffer();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        if (file == null) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + file.getName());
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new FileSystemResource(file));
//        return AjaxResult.success();
    }

}
