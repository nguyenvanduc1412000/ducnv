package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    NoteService noteService;

    @Autowired
    FileService fileService;

    @Autowired
    CredentialService credentialService;



    @GetMapping("/homepage")
    public String findAll(Model model, @AuthenticationPrincipal User userDetail) {

        List<Note> list = noteService.findAll(userDetail.getUserid());
        List<File> listFile = fileService.getAll(userDetail.getUserid());
        List<Credential> listCredential = credentialService.getAll(userDetail.getUserid());
        model.addAttribute("listNote", list);
        model.addAttribute("listFile", listFile);
        model.addAttribute("credentials", listCredential);
        return "home";
    }

    @GetMapping("/result")
    public String resultPage() {
        return "/result";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "/login";
    }

    @GetMapping("/success")
    public String successPage() {
        return "/success";
    }










}
