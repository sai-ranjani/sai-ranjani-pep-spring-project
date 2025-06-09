package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
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
//import java.util.ArrayList;
import java.util.List;


@RestController
public class SocialMediaController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountService accountService;
    

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> allMessages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(allMessages);
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable("message_id") Integer message_id){
        Message message = messageService.getMessageById(message_id);
        if(message!=null)
        return ResponseEntity.status(200).body(message);
        else
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable("account_id") Integer account_id){
        List<Message> messages = messageService.getAllMessagesByAccountId(account_id);
        return ResponseEntity.status(200).body(messages);
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Message> deleteMessageById(@PathVariable("message_id") Integer message_id){
        Message message = messageService.deleteMessageById(message_id);
        if(message!=null)
        return ResponseEntity.status(200).body(message);
        else
        return ResponseEntity.status(200).build();
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> saveMessage(@RequestBody Message newMessage){
        Message message = messageService.saveMessage(newMessage);
        if(message!=null)
        return ResponseEntity.status(200).body(message);
        else
        return ResponseEntity.status(400).build();
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Message> updateMessageById(@PathVariable("message_id") Integer message_id, @RequestBody Message updateMessage){
        Message message = messageService.updateMessageById(message_id,updateMessage);
        if(message!=null && message.getMessageText()!=null)
        return ResponseEntity.status(200).body(message);
        else
        return ResponseEntity.status(400).build();
    }

    @PostMapping("/register")
    public ResponseEntity<Account> saveAccount(@RequestBody Account newAccount){
        Account existingAccount = accountService.loginAccount(newAccount);
        if(existingAccount!=null)
        return ResponseEntity.status(409).build();
        else{
        Account persistedAccount = accountService.saveAccount(newAccount);
        if(persistedAccount!=null)
        return ResponseEntity.status(200).body(persistedAccount);
        }
        return ResponseEntity.status(400).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account loginAccount){
        Account account = accountService.loginAccount(loginAccount);
        if(account!=null)
        return ResponseEntity.status(200).body(account);
        else
        return ResponseEntity.status(401).build();
    }

}
