package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.entity.Account;
import com.example.entity.Message;
import java.util.List;

/* this is Rest Controller class, which monitors the incoming HTTP requests and 
maps them to a specific handler for implementation. 
All the Get, Post, Patch, Delete Http methods for both account and message resources are handled
and a appropriate http response is sent back leveraging ResponseEntity status code and body*/

@RestController
public class SocialMediaController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountService accountService;
    

    //As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> allMessages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(allMessages);
    }

    //As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages/{messageId}
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable("messageId") Integer messageId){
        Message message = messageService.getMessageById(messageId);
        if(message!=null)
            return ResponseEntity.status(200).body(message);//message retrieved successfully
        else
            return ResponseEntity.status(200).build();//mesaage could not be fetched
    }

    //As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/accounts/{accountId}/messages
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable("accountId") Integer accountId){
        List<Message> messagesByAccountId = messageService.getAllMessagesByAccountId(accountId);
        return ResponseEntity.status(200).body(messagesByAccountId);
    }

    //As a User, I should be able to submit a DELETE request on the endpoint DELETE localhost:8080/messages/{messageId}
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable("messageId") Integer messageId){
        boolean deleted = messageService.deleteMessageById(messageId);
        if(deleted)
            return ResponseEntity.status(200).body(1);//message deleted
        else
            return ResponseEntity.status(200).build();//message could not be deleted
    }

    //As a user, I should be able to submit a new post on the endpoint POST localhost:8080/messages
    @PostMapping("/messages")
    public ResponseEntity<Message> saveMessage(@RequestBody Message newMessage){
        Message savedMessage = messageService.saveMessage(newMessage);
        if(savedMessage!=null)
            return ResponseEntity.status(200).body(savedMessage);//message created
        else
            return ResponseEntity.status(400).build();//message creation failed
    }

    //As a user, I should be able to submit a PATCH request on the endpoint PATCH localhost:8080/messages/{messageId}
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(
        @PathVariable("messageId") Integer messageId, @RequestBody Message updateMessage){
        boolean updated = messageService.updateMessageById(messageId,updateMessage);
        if(updated)
            return ResponseEntity.status(200).body(1);//message updated
        else
            return ResponseEntity.status(400).build();//message could not be updated
    }

    //As a user, I should be able to create a new Account on the endpoint POST localhost:8080/register
    @PostMapping("/register")
    public ResponseEntity<Account> saveAccount(@RequestBody Account newAccount){
        Account existingAccount = accountService.loginAccount(newAccount);
        if(existingAccount!=null)
            return ResponseEntity.status(409).build();//duplicate account
        else{
            Account savedAccount = accountService.saveAccount(newAccount);
            if(savedAccount!=null)
                return ResponseEntity.status(200).body(savedAccount);//account created
        }
        return ResponseEntity.status(400).build();//account creation failed
    }

    //As a user, I should be able to verify my login on the endpoint POST localhost:8080/login
    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account loginAccount){
        Account verifiedAccount = accountService.loginAccount(loginAccount);
        if(verifiedAccount!=null)
            return ResponseEntity.status(200).body(verifiedAccount);//login successful
        else
            return ResponseEntity.status(401).build();//unauthorized
    }

}
