package be.ucll.quizmaster.quizmaster.controller;

import be.ucll.quizmaster.quizmaster.AbstractIntegrationTesting;
import be.ucll.quizmaster.quizmaster.controller.dto.CreateQuestionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QuestionControllerTest extends AbstractIntegrationTesting {
    @Autowired
    private QuestionController QuestionController;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    String memberEmail = "test@test.test";
    String memberName = "test";
    String memberPass = "test";

    @BeforeEach
    void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

        MvcResult mvcPost = this.mockMvc.perform(MockMvcRequestBuilders.post("/members")
                        .content("{\"email\":\"" + memberEmail + "\"," +
                                "\"username\":\"" + memberName + "\"," +
                                "\"password\":\"" + memberPass + "\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    void createQuestion() throws Exception {

        List<String> answers = new ArrayList<>();
        CreateQuestionDTO question = new CreateQuestionDTO.Builder()
                .questionString("Is dit vraag 1?")
                .description("True or False.")
                .topic("Quizzes")
                .type(1)
                .answersDTOs(answers)
                .build();

        MvcResult mvcPost = this.mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .with(httpBasic(memberEmail, memberPass))
                        .content(toJson(question))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
    }
}