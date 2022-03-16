package be.ucll.quizmaster.quizmaster.controller;

import be.ucll.quizmaster.quizmaster.AbstractIntegrationTesting;
import be.ucll.quizmaster.quizmaster.controller.dto.CreateQuestionDTO;
import be.ucll.quizmaster.quizmaster.controller.dto.QuestionDTO;
import be.ucll.quizmaster.quizmaster.model.Question;
import be.ucll.quizmaster.quizmaster.service.exceptions.NotAuthenticatedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.SerializationFeature;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QuestionControllerTest extends AbstractIntegrationTesting {

    private final Logger logger = LoggerFactory.getLogger(QuestionControllerTest.class);

    @Test
    void createQuestionType1() throws Exception {
        List<String> answers1 = new ArrayList<>();
        answers1.add("1");
        answers1.add("2");
        answers1.add("3");
        answers1.add("4");

        CreateQuestionDTO question1 = new CreateQuestionDTO.Builder()
                .questionString("Welke vraag is dit?")
                .description("Multiple Choice.")
                .topic("Quizzes")
                .type(1)
                .answersDTOs(answers1)
                .build();

        MvcResult mvcPost1 = mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .content(toJson(question1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void createQuestionType2() throws Exception {

        List<String> answers2 = new ArrayList<>();
        answers2.add("false");

        CreateQuestionDTO question2 = new CreateQuestionDTO.Builder()
                .questionString("Is dit vraag 2?")
                .description("True or False.")
                .topic("Quizzes")
                .type(2)
                .answersDTOs(answers2)
                .build();

        MvcResult mvcPost2 = mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .content(toJson(question2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void createQuestionType3() throws Exception {
        List<String> answers3 = new ArrayList<>();
        answers3.add("vraag");

        CreateQuestionDTO question3 = new CreateQuestionDTO.Builder()
                .questionString("Dit is _____ 3!")
                .description("Complete the statement")
                .topic("Quizzes")
                .type(3)
                .answersDTOs(answers3)
                .build();

        MvcResult mvcPost3 = mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .content(toJson(question3))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    @DisplayName("tests bad request, here with inappropriate type")
    void badCreateQuestion400() throws Exception {
        List<String> answers3 = new ArrayList<>();
        answers3.add("vraag");

        CreateQuestionDTO question3 = new CreateQuestionDTO.Builder()
                .questionString("Dit is _____ 3!")
                .description("Complete the statement")
                .topic("Quizzes")
                .type(5)
                .answersDTOs(answers3)
                .build();

        MvcResult mvcPost3 = mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .content(toJson(question3))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @DisplayName("tests unauthorized member")
    void badCreateQuestion401() throws Exception {
        List<String> answer = new ArrayList<>();
        answer.add("vraag");

        CreateQuestionDTO question = new CreateQuestionDTO.Builder()
                .questionString("Dit is _____ 3!")
                .description("Complete the statement")
                .topic("Quizzes")
                .type(3)
                .answersDTOs(answer)
                .build();

        MvcResult mvcPost = mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .with(httpBasic("unauthorized", "person"))
                        .content(toJson(question))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();


    }

    @Test
    void getNextQuestionWeb() throws Exception {
        //TODO build quiz to check
        String topic = "Quizzes";
        List<String> answers1 = new ArrayList<>();
        answers1.add("1");
        answers1.add("2");
        answers1.add("3");
        answers1.add("4");

        CreateQuestionDTO question1 = new CreateQuestionDTO.Builder()
                .questionString("Welke vraag is dit?")
                .description("Multiple Choice.")
                .topic(topic)
                .type(1)
                .answersDTOs(answers1)
                .build();

        MvcResult mvcPost1 = mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .content(toJson(question1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        List<String> answers2 = new ArrayList<>();
        answers2.add("false");

        CreateQuestionDTO question2 = new CreateQuestionDTO.Builder()
                .questionString("Is dit vraag 2?")
                .description("True or False.")
                .topic(topic)
                .type(2)
                .answersDTOs(answers2)
                .build();

        MvcResult mvcPost2 = mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .content(toJson(question2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        List<String> answers3 = new ArrayList<>();
        answers3.add("vraag");

        CreateQuestionDTO question3 = new CreateQuestionDTO.Builder()
                .questionString("Dit is _____ 3!")
                .description("Complete the statement")
                .topic(topic)
                .type(3)
                .answersDTOs(answers3)
                .build();

        MvcResult mvcPost3 = mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .content(toJson(question3))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/questions/get-next" + answers2.get(0))
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                )
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    void getNextQuestionWebWithoutQuiz() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/questions/get-next")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                )
                .andExpect(status().isOk())
                .andReturn();

        logger.error(mvcResult.getResponse().getContentAsString());

        assertEquals(mvcResult.getResponse().getContentAsString(), "[]");
    }

    @Test
    void getNextQuestionMobile() {
        //TODO build quiz to check
    }

    @Test
    void getAllForTopic() throws Exception {
        String topic = "Quizzes";

        List<String> answers1 = new ArrayList<>();
        answers1.add("1");
        answers1.add("2");
        answers1.add("3");
        answers1.add("4");

        CreateQuestionDTO question1 = new CreateQuestionDTO.Builder()
                .questionString("Welke vraag is dit?")
                .description("Multiple Choice.")
                .topic(topic)
                .type(1)
                .answersDTOs(answers1)
                .build();

        MvcResult mvcPost1 = mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .content(toJson(question1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        List<String> answers2 = new ArrayList<>();
        answers2.add("false");

        CreateQuestionDTO question2 = new CreateQuestionDTO.Builder()
                .questionString("Is dit vraag 2?")
                .description("True or False.")
                .topic(topic)
                .type(2)
                .answersDTOs(answers2)
                .build();

        MvcResult mvcPost2 = mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .content(toJson(question2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        List<String> answers3 = new ArrayList<>();
        answers3.add("vraag");

        CreateQuestionDTO question3 = new CreateQuestionDTO.Builder()
                .questionString("Dit is _____ 3!")
                .description("Complete the statement")
                .topic(topic)
                .type(3)
                .answersDTOs(answers3)
                .build();

        MvcResult mvcPost3 = mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .content(toJson(question3))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/questions/" + topic)
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                )
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper oM = new ObjectMapper();

        List<QuestionDTO> questions = oM.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<QuestionDTO>>() {
        });

        assertEquals(questions.size(), 3);
        assertEquals(questions.get(0).getQuestionString(), "Welke vraag is dit?");
        assertEquals(questions.get(1).getQuestionString(), "Is dit vraag 2?");
        assertEquals(questions.get(2).getQuestionString(), "Dit is _____ 3!");

    }

    @Test
    void badGetAllForTopic401() throws Exception {
        String topic = "Quizzes";

        List<String> answers1 = new ArrayList<>();
        answers1.add("1");
        answers1.add("2");
        answers1.add("3");
        answers1.add("4");

        CreateQuestionDTO question1 = new CreateQuestionDTO.Builder()
                .questionString("Welke vraag is dit?")
                .description("Multiple Choice.")
                .topic(topic)
                .type(1)
                .answersDTOs(answers1)
                .build();

        MvcResult mvcPost1 = mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .content(toJson(question1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        List<String> answers2 = new ArrayList<>();
        answers2.add("false");

        CreateQuestionDTO question2 = new CreateQuestionDTO.Builder()
                .questionString("Is dit vraag 2?")
                .description("True or False.")
                .topic(topic)
                .type(2)
                .answersDTOs(answers2)
                .build();

        MvcResult mvcPost2 = mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .content(toJson(question2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        List<String> answers3 = new ArrayList<>();
        answers3.add("vraag");

        CreateQuestionDTO question3 = new CreateQuestionDTO.Builder()
                .questionString("Dit is _____ 3!")
                .description("Complete the statement")
                .topic(topic)
                .type(3)
                .answersDTOs(answers3)
                .build();

        MvcResult mvcPost3 = mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .content(toJson(question3))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/questions/" + topic)
                        .with(httpBasic("unauthorized", "person"))
                )
                .andExpect(status().isUnauthorized())
                .andReturn();

    }

    // TODO validation checks in order to simulate exceptions
    @Test
    void badGetAllForTopic400() throws Exception {
        String topic = "Quizzes";

        List<String> answers1 = new ArrayList<>();
        answers1.add("1");
        answers1.add("2");
        answers1.add("3");
        answers1.add("4");

        CreateQuestionDTO question1 = new CreateQuestionDTO.Builder()
                .questionString("Welke vraag is dit?")
                .description("Multiple Choice.")
                .topic(topic)
                .type(1)
                .answersDTOs(answers1)
                .build();

        MvcResult mvcPost1 = mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .content(toJson(question1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        List<String> answers2 = new ArrayList<>();
        answers2.add("false");

        CreateQuestionDTO question2 = new CreateQuestionDTO.Builder()
                .questionString("Is dit vraag 2?")
                .description("True or False.")
                .topic(topic)
                .type(2)
                .answersDTOs(answers2)
                .build();

        MvcResult mvcPost2 = mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .content(toJson(question2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        List<String> answers3 = new ArrayList<>();
        answers3.add("vraag");

        CreateQuestionDTO question3 = new CreateQuestionDTO.Builder()
                .questionString("Dit is _____ 3!")
                .description("Complete the statement")
                .topic(topic)
                .type(3)
                .answersDTOs(answers3)
                .build();

        MvcResult mvcPost3 = mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .content(toJson(question3))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/questions/" + "nonexistingTopic")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                )
                .andExpect(status().isBadRequest())
                .andReturn();

    }
}