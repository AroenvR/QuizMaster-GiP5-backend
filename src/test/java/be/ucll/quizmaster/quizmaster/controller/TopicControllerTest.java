package be.ucll.quizmaster.quizmaster.controller;

import be.ucll.quizmaster.quizmaster.AbstractIntegrationTesting;
import be.ucll.quizmaster.quizmaster.controller.dto.MemberDTO;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class TopicControllerTest extends AbstractIntegrationTesting {


    @Test
    public void getAllTopics() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/topics")
                        .with(httpBasic(wout.getEmail(), wout.getPassword()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void testCleanupAndSetup(){
        // test om te zien of de cleanup is uitgevoerd zodat de 2de beforeEach kan werken
        // may be removed later
    }


}