package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.example.entity.Message;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import java.util.List;

/*this class encapsulates all the business logic associated with managing message entity in social media blog API*/

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;

    //retrieve all persisted messages
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    //retrieve a specific message matching the message_id
    public Message getMessageById(Integer message_id){
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if(optionalMessage.isPresent()){
            return optionalMessage.get();
        }
        else{
            return null;
        }
    }

    //retrieve all the message posted by a specific user matching the account_id
    public List<Message> getAllMessagesByAccountId(Integer account_id){
        return messageRepository.findMessagesByPostedBy(account_id);

    }

    //delete a message identified by message_id
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

    //save a new message
    public Message saveMessage(Message newMessage){
        /*The creation of the message will be successful if and only if the messageText is not blank, 
        is not over 255 characters, and postedBy refers to a real, existing user */
        if(newMessage.getMessageText()!="" && newMessage.getMessageText()!=null && newMessage.getMessageText().length()<=255){
            Optional<Account> optionalAccount = accountRepository.findById(newMessage.getPostedBy());
            if(optionalAccount.isPresent()){
                return messageRepository.save(newMessage);
            }
        }
        return null;
    }

    //update a existing message
    public Message updateMessageById(Integer message_id,Message updateMessage){
        /*The update of a message should be successful if and only if 
        the message id already exists and the new messageText is not blank and is not over 255 characters */
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
 