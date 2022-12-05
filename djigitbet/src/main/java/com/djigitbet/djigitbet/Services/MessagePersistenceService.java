package com.djigitbet.djigitbet.Services;

import com.djigitbet.djigitbet.Model.Entity.Message;
import com.djigitbet.djigitbet.Model.Entity.Player;
import com.djigitbet.djigitbet.Model.Entity.UserType;
import com.djigitbet.djigitbet.Repositories.IMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessagePersistenceService 
{
    final
     private IMessageRepository messageRepository;

    public MessagePersistenceService(IMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    
    public void saveMessage(Message message)
    {
        messageRepository.save(message);
    }
    public List<Message> getallMessages()
    {
        return messageRepository.findAll();
    }

    public List<Message> getPageedMessages(int pageNo, int pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Message> pagedResult = messageRepository.findAll(paging);   
        return pagedResult.getContent();
    }
    
}
