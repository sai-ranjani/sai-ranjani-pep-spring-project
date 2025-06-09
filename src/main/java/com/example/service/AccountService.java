package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*this class encapsulates all the business logic associated with managing account entity in social media blog API*/

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    
    public Account saveAccount(Account newAccount){
        /*The registration will be successful if and only if the username is not blank, 
        the password is at least 4 characters long, and an Account with that username does not already exist */
        if(newAccount.getUsername()!="" && newAccount.getUsername()!=null && newAccount.getPassword().length()>=4)
        {
            Account account = accountRepository.save(newAccount);
            return account;
        }
        return null;
    }

    public Account loginAccount(Account loginAccount){
        /*The login will be successful if and only if the username and password provided in the request body JSON 
        match a real account existing on the database */
        Account account = accountRepository.findAccountByUsernameAndPassword(loginAccount.getUsername(),loginAccount.getPassword());
        if(account!=null){
            return account;
        }
        else{
            return null;
        }
    }
   
}
