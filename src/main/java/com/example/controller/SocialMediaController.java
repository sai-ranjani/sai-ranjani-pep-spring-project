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
//import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
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

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable("message_id") Integer message_id){
        Message message = messageService.getMessageById(message_id);
        if(message!=null)
            return ResponseEntity.status(200).body(message);//message retrieved successfully
        else
            return ResponseEntity.status(200).build();//mesaage could not be fetched
    }

    //As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/accounts/{accountId}/messages
    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable("account_id") Integer account_id){
        List<Message> messages = messageService.getAllMessagesByAccountId(account_id);
        return ResponseEntity.status(200).body(messages);
    }

    //As a User, I should be able to submit a DELETE request on the endpoint DELETE localhost:8080/messages/{messageId}
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Message> deleteMessageById(@PathVariable("message_id") Integer message_id){
        Message message = messageService.deleteMessageById(message_id);
        if(message!=null)
            return ResponseEntity.status(200).body(message);//message deleted
        else
            return ResponseEntity.status(200).build();//message could not be deleted
    }

    //As a user, I should be able to submit a new post on the endpoint POST localhost:8080/messages
    @PostMapping("/messages")
    public ResponseEntity<Message> saveMessage(@RequestBody Message newMessage){
        Message message = messageService.saveMessage(newMessage);
        if(message!=null)
            return ResponseEntity.status(200).body(message);//message created
        else
            return ResponseEntity.status(400).build();//message creation failed
    }

    //As a user, I should be able to submit a PATCH request on the endpoint PATCH localhost:8080/messages/{messageId}
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Message> updateMessageById(@PathVariable("message_id") Integer message_id, @RequestBody Message updateMessage){
        Message message = messageService.updateMessageById(message_id,updateMessage);
        if(message!=null && message.getMessageText()!=null)
            return ResponseEntity.status(200).body(message);//message updated
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
            Account persistedAccount = accountService.saveAccount(newAccount);
            if(persistedAccount!=null)
                return ResponseEntity.status(200).body(persistedAccount);//account created
        }
        return ResponseEntity.status(400).build();//account creation failed
    }

    //As a user, I should be able to verify my login on the endpoint POST localhost:8080/login
    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account loginAccount){
        Account account = accountService.loginAccount(loginAccount);
        if(account!=null)
            return ResponseEntity.status(200).body(account);//login successful
        else
            return ResponseEntity.status(401).build();//unauthorized
    }

}
