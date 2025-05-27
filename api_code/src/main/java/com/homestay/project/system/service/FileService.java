package com.homestay.project.system.service;


import com.homestay.framework.web.domain.AjaxResult;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public interface FileService {

    AjaxResult getPage();

    AjaxResult deleteByName(String name);

    ResponseEntity<FileSystemResource> downloadByName(String name, HttpServletResponse response);
}
