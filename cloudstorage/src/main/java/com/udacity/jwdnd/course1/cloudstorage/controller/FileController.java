package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/files")
    public String handleUpload(@AuthenticationPrincipal User userDetail, @RequestParam("fileUpload") MultipartFile fileUpload, Model model) {
        String mess = "";
        //check empty
        if (fileUpload.isEmpty()) {
            mess += "Please select a file to upload";
        }

        // max 10mb
        if (fileUpload.getSize() > 10 * 1024 * 1024) {
            mess += "File too large( must be < 10MB)";
        }
        if (!fileService.checkFileExist(userDetail.getUserid(), fileUpload.getOriginalFilename())) {
            mess += "File existed, choose other file";
        }

        if (!mess.equals("")) {
            model.addAttribute("mess", mess);
            return "/result";
        }

        try {
            fileService.addFile(fileUpload, userDetail.getUserid());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("mess","Uploaded File Successfully");
        return "/success";

    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("id") int id) {
        File file = fileService.getById(id);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] fileData = file.getFiledata();
        ByteArrayResource resource = new ByteArrayResource(fileData);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, file.getContenttype());

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    @GetMapping("/file/delete/{id}")
    public String deleteFile(@PathVariable("id") int id,Model model) {
        fileService.delete(id);
        model.addAttribute("mess","Deleted Successfully");
        return "/success";

    }


}
