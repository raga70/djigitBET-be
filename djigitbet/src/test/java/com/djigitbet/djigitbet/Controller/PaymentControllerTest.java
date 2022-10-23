package com.djigitbet.djigitbet.Controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.djigitbet.djigitbet.Model.DTO.paymentDTO;
import com.djigitbet.djigitbet.Services.StripeClient;
import com.djigitbet.djigitbet.Services.UserService;
import com.djigitbet.djigitbet.security.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {PaymentController.class})
@ExtendWith(SpringExtension.class)
class PaymentControllerTest {
    @MockBean
    private JWTUtil jWTUtil;

    @Autowired
    private PaymentController paymentController;

    @MockBean
    private StripeClient stripeClient;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link PaymentController#PlacePayment(String, paymentDTO)}
     */
    @Test
    void testPlacePayment() throws Exception {
        paymentDTO paymentDTO = new paymentDTO();
        paymentDTO.setAmount(10L);
        paymentDTO.setId("42");
        String content = (new ObjectMapper()).writeValueAsString(paymentDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/payment/")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(paymentController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("You are not authorized"));
    }

    /**
     * Method under test: {@link PaymentController#PlacePayment(String, paymentDTO)}
     */
    @Test
    void testPlacePayment2() throws Exception {
        paymentDTO paymentDTO = new paymentDTO();
        paymentDTO.setAmount(10L);
        paymentDTO.setId("42");
        String content = (new ObjectMapper()).writeValueAsString(paymentDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/payment/", "Uri Variables")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(paymentController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("You are not authorized"));
    }

    /**
     * Method under test: {@link PaymentController#PlacePayment(String, paymentDTO)}
     */
    @Test
    void testPlacePayment3() throws Exception {
        when(jWTUtil.parseJWT((String) any())).thenReturn(new DefaultClaims());

        paymentDTO paymentDTO = new paymentDTO();
        paymentDTO.setAmount(10L);
        paymentDTO.setId("42");
        String content = (new ObjectMapper()).writeValueAsString(paymentDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/payment/")
                .header("Authorization", "Values", "Values")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(paymentController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("You are not authorized"));
    }
}

