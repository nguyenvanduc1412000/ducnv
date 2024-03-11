package com.udacity.jwdnd.course1.cloudstorage.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
   private int userid;
   private String username;
   private String salt = "";
   private  String password;
   private  String firstname;
   private String lastname;
   private Boolean enabled = true;
   private String role = "ADMIN";

   @Override
   public Collection<GrantedAuthority> getAuthorities() {
      List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
      grantedAuthorities.add(new SimpleGrantedAuthority(role));
      return grantedAuthorities;
   }

   @Override
   public boolean isAccountNonExpired() {
      return enabled;
   }

   @Override
   public boolean isAccountNonLocked() {
      return enabled;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return enabled;
   }

   @Override
   public boolean isEnabled() {
      return enabled;
   }
}
