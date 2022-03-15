package be.ucll.quizmaster.quizmaster.controller;

import be.ucll.quizmaster.quizmaster.AbstractIntegrationTesting;
import be.ucll.quizmaster.quizmaster.controller.dto.CreateQuestionDTO;
import be.ucll.quizmaster.quizmaster.controller.dto.CreateQuizDTO;
import be.ucll.quizmaster.quizmaster.controller.dto.QuestionDTO;
import be.ucll.quizmaster.quizmaster.service.QuizService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QuizControllerTest extends AbstractIntegrationTesting {

    private final Logger logger = LoggerFactory.getLogger(QuizControllerTest.class);

    @Test
    void createQuiz() throws Exception {
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

        List<String> answers4 = new ArrayList<>();
        answers4.add("vraag");

        CreateQuestionDTO question4 = new CreateQuestionDTO.Builder()
                .questionString("Dit is _____ 4!")
                .description("Complete the statement")
                .topic(topic)
                .type(3)
                .answersDTOs(answers4)
                .build();

        MvcResult mvcPost4 = mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .content(toJson(question4))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();


        List<String> answers5 = new ArrayList<>();
        answers5.add("vraag");

        CreateQuestionDTO question5 = new CreateQuestionDTO.Builder()
                .questionString("Dit is _____ 5!")
                .description("Complete the statement")
                .topic(topic)
                .type(3)
                .answersDTOs(answers5)
                .build();

        MvcResult mvcPost5 = mockMvc.perform(MockMvcRequestBuilders.post("/questions")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .content(toJson(question5))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();


        //get questions
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/questions/" + topic)
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                )
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper oM = new ObjectMapper();

        List<QuestionDTO> questions = oM.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<QuestionDTO>>() {});

        //get question ID's
        Set<Long> questionIds = new HashSet<>();
        for (QuestionDTO question: questions) {
            questionIds.add(question.getQuestionId());
        }

        logger.error("HERE !!!!!!!!!!!!!!!!!!!  -----> " + questionIds);

        //Create quiz
        CreateQuizDTO quizDTO = new CreateQuizDTO.Builder()
                .quizTitle("TESTQUIZ")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusDays(10))
                .questionIds(questionIds)
                .build();

        logger.error("HERE !!!!!!!!!!!!!!!!!!!  -----> " + quizDTO);

        // Post quiz
        MvcResult mvcPostQuiz = mockMvc.perform(MockMvcRequestBuilders.post("/quizzes")
                    .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                    .content(toJson(quizDTO))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
    }
}