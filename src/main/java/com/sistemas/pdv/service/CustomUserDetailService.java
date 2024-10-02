package com.sistemas.pdv.service;

import com.sistemas.pdv.entity.User;

import com.sistemas.pdv.exceptions.PasswordNotFoundException;
import com.sistemas.pdv.record.LoginRecord;
import com.sistemas.pdv.repository.UserRepository;
import com.sistemas.pdv.security.SecurityConfig;
import com.sistemas.pdv.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUserName(username);

        if(user.equals("") || user == null){
            throw new UsernameNotFoundException("Login Inválido");
        }

        return new UserPrincipal(user);
    }

    public void verifyUserCredencials(LoginRecord record){

        UserDetails user = loadUserByUsername(record.username());
        boolean passwordIsTheSame = SecurityConfig.passwordEncoder()
                .matches(record.password(), user.getPassword());

        if(!passwordIsTheSame){
            throw new PasswordNotFoundException("Senha Inválida!");
        }
    }
}
