package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
public class CredentialController {
    @Autowired
    CredentialService credentialService;

    @Autowired
    UserService userService;

    @Autowired
    EncryptionService encryptionService;


    @PostMapping("/credentials")
    public String handleCredentials(@AuthenticationPrincipal User userDetail, Credential credential, Model model) {
        String mess = "";
        if (credential.getCredentialid() > 0) {
            Credential c = credentialService.findById(credential.getCredentialid());
            credential.setKey(c.getKey());
            String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
            credential.setPassword(encryptedPassword);
            credentialService.update(credential);
            mess += "Updated Successfully";
        } else {
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);
            String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
            Credential newCredential = new Credential();
            newCredential.setUrl(credential.getUrl());
            newCredential.setUsername(credential.getUsername());
            newCredential.setKey(encodedKey);
            newCredential.setPassword(encryptedPassword);
            newCredential.setUserid(userDetail.getUserid());
            credentialService.insert(newCredential);
            mess += "Inserted Successfully";
        }
        model.addAttribute("mess", mess);
        return "/success";
    }

    @GetMapping("/credentials/delete")
    public String deleteCredentials(@RequestParam("id") int id, Model model) {

        credentialService.delete(id);
        model.addAttribute("mess", "Deleted Successfully");
        return "/success";


    }
}
