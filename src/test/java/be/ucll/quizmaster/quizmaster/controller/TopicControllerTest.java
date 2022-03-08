package be.ucll.quizmaster.quizmaster.controller;

import be.ucll.quizmaster.quizmaster.AbstractIntegrationTesting;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class TopicControllerTest extends AbstractIntegrationTesting {


    @Test
    public void getAllTopics() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/topics")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

}