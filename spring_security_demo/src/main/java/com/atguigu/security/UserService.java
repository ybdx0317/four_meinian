package com.atguigu.security;



import com.atguigu.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserService implements UserDetailsService {

    static Map<String, User> map = new HashMap<>();

    static {
        map.put("wl", new User("wl", "$2a$10$4BbNOs6R3LQ5YA5RVCMyU.MTOcadEl2yFAt6DrzczkPWVXDsIBXr6"));
        map.put("root", new User("root", "123456"));
    }

    //表示用户输入的用户名
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = map.get(username);
        if(user == null){
            //表示根据用户名没有查询到用户信息
            return null;
        }
        //String password ="{noop}" + user.getPassword();
        String password = user.getPassword();
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("delete"));
        list.add(new SimpleGrantedAuthority("edit"));
        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        //模拟根据用户名查询数据库
        return new org.springframework.security.core.userdetails.User(username, password,list);

    }
}
