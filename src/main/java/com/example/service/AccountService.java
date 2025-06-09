package com.example.service;

import com.example.entity.Account;
//import com.example.entity.Message;
import com.example.repository.AccountRepository;
//import com.example.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    

    public Account saveAccount(Account newAccount){
        if(newAccount.getUsername()!="" && newAccount.getUsername()!=null && newAccount.getPassword().length()>=4)
        {
            Account account = accountRepository.save(newAccount);
            return account;
        }
        return null;
    }

    public Account loginAccount(Account loginAccount){
        Account account = accountRepository.findAccountByUsernameAndPassword(loginAccount.getUsername(),loginAccount.getPassword());
       if(account!=null)
        return account;
        else
        return null;
    }
   
}
