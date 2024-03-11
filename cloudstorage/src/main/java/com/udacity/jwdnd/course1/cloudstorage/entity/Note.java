package com.udacity.jwdnd.course1.cloudstorage.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private int noteid;
    private String notetitle;
    private String notedescription;
    private int userid;
    //private int userid;
}
