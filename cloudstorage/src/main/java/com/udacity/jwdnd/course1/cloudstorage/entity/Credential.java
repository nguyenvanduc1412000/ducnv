package com.udacity.jwdnd.course1.cloudstorage.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Credential {
    private int credentialid;
    private String url;
    private String username;
    private String key;
    private String password;
    private int userid;
}
