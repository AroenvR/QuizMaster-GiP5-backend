package be.ucll.quizmaster.quizmaster.controller;

import be.ucll.quizmaster.quizmaster.AbstractIntegrationTesting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LoginControllerTest extends AbstractIntegrationTesting {

    @Test
    void login() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/login")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword())))
                .andExpect(status().isOk())
                .andReturn();
    }


    //Todo Re-enable when logout is adjusted/re-added
//    @Test
//    void logout() throws Exception {
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/logout")
//                        .with(httpBasic(oderick.getEmail(), oderick.getPassword())))
//                .andExpect(status().isOk())
//                .andReturn();
//    }
}