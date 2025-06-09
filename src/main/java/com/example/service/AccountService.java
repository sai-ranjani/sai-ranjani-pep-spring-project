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
        Account account = accountRepository.save(newAccount);
        return account;
    }
/* 
    public Account loginAccount(Account loginAccount){
        Account account = accountRepository.findAccountByUserNameAndPassword(loginAccount.getUsername(),loginAccount.getPassword()).get();
        return account;
    }
   */ 
}
