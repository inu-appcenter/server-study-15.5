package com.example.mytodolist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//패스워드는 암호화 해서 저장해야 하기 때문에 PasswordEncoder를 활용해 인코딩 수행.
public class PasswordEncoderConfiguration {

     @Bean
    public PasswordEncoder passwordEncoder(){
         return PasswordEncoderFactories.createDelegatingPasswordEncoder();
     }
}
