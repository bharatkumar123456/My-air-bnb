package com.blogapp12.security;

import com.blogapp12.entity.User;
import com.blogapp12.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);
        if(user==null){
            throw new UsernameNotFoundException("User not Found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),null);
    }
}
