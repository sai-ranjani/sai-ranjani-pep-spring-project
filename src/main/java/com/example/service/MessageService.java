package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.example.entity.Message;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer message_id){
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if(optionalMessage.isPresent())
        return optionalMessage.get();
        else
        return null;
    }

    public List<Message> getAllMessagesByAccountId(Integer account_id){
        return messageRepository.findMessagesByPostedBy(account_id);

    }

    public Message deleteMessageById(Integer message_id){
    
       Optional<Message> optionalMessage = messageRepository.findById(message_id);
       if(optionalMessage.isPresent())
       {
        Message message = optionalMessage.get();
        messageRepository.deleteById(message_id);
        return message;
       }
       return null;
    }

    public Message saveMessage(Message newMessage){
        if(newMessage.getMessageText()!="" && newMessage.getMessageText()!=null && newMessage.getMessageText().length()<=255){
            Optional<Account> optionalAccount = accountRepository.findById(newMessage.getPostedBy());
            if(optionalAccount.isPresent()){
                return messageRepository.save(newMessage);
            }
        }
        return null;
    }

    public Message updateMessageById(Integer message_id,Message updateMessage){
        if(updateMessage.getMessageText()!="" && updateMessage.getMessageText()!=null && updateMessage.getMessageText().length()<=255){

            Optional<Message> optionalMessage = messageRepository.findById(message_id);
            if(optionalMessage.isPresent()){
                Message message = optionalMessage.get();
                message.setMessageText(updateMessage.getMessageText());
                return messageRepository.save(message);
            }
        }
        return null;
    }

}
 