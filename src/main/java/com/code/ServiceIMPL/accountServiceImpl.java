package com.code.ServiceIMPL;

import com.code.Entity.account;
import com.code.Service.accountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static com.code.Enum.role.CLIENT;

@Service
public class accountServiceImpl implements accountService {

    @Autowired
    private com.code.Repository.accountRepository accountRepository;

    @Override
    public void save(account account) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        account.setUserRole(CLIENT);
        accountRepository.save(account);
    }

    @Override
    public account getByUserName(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public List<account> getAll() {
        return accountRepository.findAll();
    }
}
