package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticateService implements AuthenticationProvider {

    @Autowired
    UserMapper userMapper;

    @Autowired
    HashService hashService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User users = userMapper.isExist(username);
        if (users != null) {
            String encodedSalt = users.getSalt();
            String hashedPassword = hashService.getHashedValue(password, encodedSalt);
            if (users.getPassword().equals(hashedPassword)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
                System.out.println("usernamePasswordAuthenticationToken:" + usernamePasswordAuthenticationToken);
                return usernamePasswordAuthenticationToken;
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
