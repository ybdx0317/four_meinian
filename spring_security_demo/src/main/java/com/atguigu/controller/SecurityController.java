package com.atguigu.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @RequestMapping("/findAll")
    @PreAuthorize("isAuthenticated()")
    public String findAll(){
        return "success";
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('delete')")
    public String delete(){
        return "success";
    }

    @RequestMapping("/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String edit(){
        return "success";
    }

    @RequestMapping("/findById")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public String findById(){
        return "success";
    }


}
