package com.udacity.jwdnd.course1.cloudstorage.services;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserMapper userMapper;

    public User registerUser(User u){
       String encryptPassword= passwordEncoder.encode(u.getPassword());
       u.setPassword(encryptPassword);
       userMapper.insertUser(u);
       return u;
    }

    public boolean isExistUser(User u){
        if(userMapper.isExist(u.getUsername()) == null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userExist = userMapper.isExist(username);
        if (userExist == null) {
            throw new UsernameNotFoundException("Can't find user");
        }
        return userExist;

    }
}
