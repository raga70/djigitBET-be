package com.djigitbet.djigitbet.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.djigitbet.djigitbet.Model.Entity.Message;
import com.djigitbet.djigitbet.Model.Entity.MessageType;
import com.djigitbet.djigitbet.Repositories.IMessageRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MessagePersistenceService.class})
@ExtendWith(SpringExtension.class)
class MessagePersistenceServiceTest {
    @MockBean
    private IMessageRepository iMessageRepository;

    @Autowired
    private MessagePersistenceService messagePersistenceService;

    /**
     * Method under test: {@link MessagePersistenceService#saveMessage(Message)}
     */
    @Test
    void testSaveMessage() {
        //arrange
        Message message = new Message();
        message.setContent("Not all who wander are lost");
        message.setId(1);
        message.setSender("Sender");
        message.setType(MessageType.CONNECT);
        when(iMessageRepository.save((Message) any())).thenReturn(message);

        Message message1 = new Message();
        message1.setContent("Not all who wander are lost");
        message1.setId(1);
        message1.setSender("Sender");
        message1.setType(MessageType.CONNECT);
        //act
        messagePersistenceService.saveMessage(message1);
        verify(iMessageRepository).save((Message) any());
        //assert
        assertEquals("Not all who wander are lost", message1.getContent());
        assertEquals(MessageType.CONNECT, message1.getType());
        assertEquals("Sender", message1.getSender());
        assertEquals(1, message1.getId());
    }

    /**
     * Method under test: {@link MessagePersistenceService#getallMessages()}
     */
    @Test
    void testGetallMessages() {
        //arrange
        ArrayList<Message> messageList = new ArrayList<>();
        when(iMessageRepository.findAll()).thenReturn(messageList);
       //act
        List<Message> actualGetallMessagesResult = messagePersistenceService.getallMessages();
        //assert           
        assertSame(messageList, actualGetallMessagesResult);
        assertTrue(actualGetallMessagesResult.isEmpty());
        verify(iMessageRepository).findAll();
    }

    /**
     * Method under test: {@link MessagePersistenceService#getPageedMessages(int, int)}
     */
    @Test
    void testGetPageedMessages() {
        //arrange
        when(iMessageRepository.findAll((Pageable) any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        //act/assert
        assertTrue(messagePersistenceService.getPageedMessages(1, 3).isEmpty());
        verify(iMessageRepository).findAll((Pageable) any());
    }


}

