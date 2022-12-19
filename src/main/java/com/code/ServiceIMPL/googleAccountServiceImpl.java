package com.code.ServiceIMPL;

import com.code.Entity.googleAccount;
import com.code.Repository.googleAccountRepository;
import com.code.Service.googleAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class googleAccountServiceImpl implements googleAccountService {
    @Autowired
    private googleAccountRepository googleAccountRepository;

    @Override
    public void save(googleAccount googleAccount) {
        googleAccountRepository.save(googleAccount);
    }

    @Override
    public googleAccount get(String email ) {
        googleAccount googleAccount = googleAccountRepository.findByEmail(email);
        if(googleAccount == null) {
            googleAccount= new googleAccount();
            googleAccount.setEmail(email);
            googleAccountRepository.save(googleAccount);
        }
        return googleAccount;
    }

    @Override
    public void update(String email, String address, String phone) {
        googleAccount googleAccount = get(email);
        googleAccount.setEmail(email);
        googleAccount.setAddress(address);
        googleAccount.setPhone(phone);
        googleAccountRepository.save(googleAccount);
    }
}
