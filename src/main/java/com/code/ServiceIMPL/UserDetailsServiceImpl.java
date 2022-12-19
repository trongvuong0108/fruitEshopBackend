package com.code.ServiceIMPL;

import com.code.Entity.account;
import com.code.Service.accountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;


@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private accountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        account account = accountService.getByUserName(username);
        if(account == null ) throw new UsernameNotFoundException("User not find");
        Set<GrantedAuthority> auth = new HashSet<>();
        auth.add(new SimpleGrantedAuthority(account.getUserRole().getText()));
        return  new org.springframework.security.core.userdetails
                .User(account.getUsername(),account.getPassword(),true,true,true,true,auth);
    }
}
