package com.djigitbet.djigitbet.Controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.djigitbet.djigitbet.Model.Entity.Message;
import com.djigitbet.djigitbet.Model.Entity.MessageType;
import com.djigitbet.djigitbet.Services.MessagePersistenceService;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {GroupChatController.class})
@ExtendWith(SpringExtension.class)
class GroupChatControllerTest {
    @Autowired
    private GroupChatController groupChatController;

    @MockBean
    private MessagePersistenceService messagePersistenceService;

    /**
     * Method under test: {@link GroupChatController#getallpaged(int, int)}
     */
    @Test
    void testGetallpaged() throws Exception {
        when(messagePersistenceService.getPageedMessages(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/chat/getoldmsgpaged/{pageNo}/{pageSize}", 1, 3);
        MockMvcBuilders.standaloneSetup(groupChatController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    /**
     * Method under test: {@link GroupChatController#getallpaged(int, int)}
     */
    @Test
    void testGetallpaged2() throws Exception {
        when(messagePersistenceService.getPageedMessages(anyInt(), anyInt())).thenReturn(new ArrayList<>());

        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/chat/getoldmsgpaged/{pageNo}/{pageSize}",
                1, 3);
        getResult.characterEncoding("Encoding");

        MockMvcBuilders.standaloneSetup(groupChatController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
    
    
    
    
    
    
    
    /**
     * Method under test: {@link GroupChatController#register(Message, SimpMessageHeaderAccessor)}
     */
    @Test
    @Disabled("untestable")
    void testRegister() {
        Message message = new Message();
        message.setContent("Not all who wander are lost");
        message.setId(1);
        message.setSender("Sender");
        message.setType(MessageType.CONNECT);
        groupChatController.register(message, SimpMessageHeaderAccessor.create()); //SimpMessageHeaderAccessor.getSessionAttributes()" is null
    }

    /**
     * Method under test: {@link GroupChatController#register(Message, SimpMessageHeaderAccessor)}
     */
    @Test
    @Disabled("untestable")
    void testRegister2() {
    
        Message message = mock(Message.class);
        when(message.getSender()).thenReturn("Sender");
        doNothing().when(message).setContent((String) any());
        doNothing().when(message).setId(anyInt());
        doNothing().when(message).setSender((String) any());
        doNothing().when(message).setType((MessageType) any());
        message.setContent("Not all who wander are lost");
        message.setId(1);
        message.setSender("Sender");
        message.setType(MessageType.CONNECT);
        
        
        groupChatController.register(message, SimpMessageHeaderAccessor.create());//SimpMessageHeaderAccessor.getSessionAttributes()" is null
    }



 

    /**
     * Method under test: {@link GroupChatController#sendMessage(Message)}
     */
    @Test
    void testSendMessage() {
        // Arrange
        Message message = new Message();
        message.setContent("Not all who wander are lost");
        message.setId(1);
        message.setSender("Sender");
        message.setType(MessageType.CONNECT);
        //Act/Assert
        assertSame(message, groupChatController.sendMessage(message));
    }

    /**
     * Method under test: {@link GroupChatController#sendMessage(Message)}
     */
    @Test
    void testSendMessage2() {
        Message message = mock(Message.class);
        doNothing().when(message).setContent((String) any());
        doNothing().when(message).setId(anyInt());
        doNothing().when(message).setSender((String) any());
        doNothing().when(message).setType((MessageType) any());
        message.setContent("Not all who wander are lost");
        message.setId(1);
        message.setSender("Sender");
        message.setType(MessageType.CONNECT);
        groupChatController.sendMessage(message);
        verify(message).setContent((String) any());
        verify(message).setId(anyInt());
        verify(message).setSender((String) any());
        verify(message).setType((MessageType) any());
    }

    /**
     * Method under test: {@link GroupChatController#sendMessage(Message)}
     */
    @Test
    void testSendMessage3() {
        Message message = mock(Message.class);
        doNothing().when(message).setContent((String) any());
        doNothing().when(message).setId(anyInt());
        doNothing().when(message).setSender((String) any());
        doNothing().when(message).setType((MessageType) any());
        message.setContent("Content");
        message.setId(1);
        message.setSender("Sender");
        message.setType(MessageType.DISCONNECT);
        groupChatController.sendMessage(message);
        verify(message).setContent((String) any());
        verify(message).setId(anyInt());
        verify(message).setSender((String) any());
        verify(message).setType((MessageType) any());
    }


}

