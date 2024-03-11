package com.udacity.jwdnd.course1.cloudstorage.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class File {

    private int fileId;
    private String filename;
    private String contenttype;
    private String filesize;
    private byte[] filedata;
    private int userid;
}
