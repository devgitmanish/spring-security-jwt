package com.springsecurityjwt.controller;

import com.springsecurityjwt.entity.UserInfo;
import com.springsecurityjwt.model.AuthRequest;
import com.springsecurityjwt.service.JwtService;
import com.springsecurityjwt.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProductService productService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/createUser")
    public String saveUserInfo(@RequestBody UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        return productService.saveUserDetail(userInfo);
    }

    @PostMapping("/authenticate")
    public String authenciateAndGetToken(@RequestBody AuthRequest authRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(),
                        authRequest.getPassword()));
        if (authentication.isAuthenticated())
            return jwtService.generateToken(authRequest.getUserName());
        else
            throw new UsernameNotFoundException("Invalid user request!");
    }
}
