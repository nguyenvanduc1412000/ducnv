package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    @Autowired
    FileMapper fileMapper;

    public List<File> getAll(int uid){
        return fileMapper.getAll(uid);
    }

    public void addFile(MultipartFile fileUpload, int userid) throws IOException {
        File file = new File();
        try {
            file.setContenttype(fileUpload.getContentType());
            file.setFiledata(fileUpload.getBytes());
            file.setFilename(fileUpload.getOriginalFilename());
            file.setFilesize(Long.toString(fileUpload.getSize()));
            file.setUserid(userid);
        } catch (IOException e) {
            throw e;
        }
        fileMapper.insert(file);
    }

    //delete
    public void delete(int fid){
        fileMapper.delete(fid);
    }

    public boolean checkFileExist(Integer uid, String fileName){
          File f = fileMapper.checkExistByName(uid,fileName);
          if(f == null){
              return true;
          }

          return false;

    }

    public File getById(int id){
        return fileMapper.getById(id);
    }
}
