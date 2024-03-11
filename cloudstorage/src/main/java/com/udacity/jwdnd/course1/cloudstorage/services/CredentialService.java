package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;


@Service
public class CredentialService {

    @Autowired
    CredentialMapper credentialMapper;

    @Autowired
    EncryptionService encryptionService;

    public List<Credential> getAll(int userid){
        return credentialMapper.getAll(userid);
    }

    public Credential findById(int id){
        return credentialMapper.getById(id);
    }
    public void update(Credential credential){
        credentialMapper.update(credential);
    }

    public void insert(Credential credential){

        credentialMapper.insert(credential);
    }

    public void delete(int id){
        credentialMapper.delete(id);
    }

}
