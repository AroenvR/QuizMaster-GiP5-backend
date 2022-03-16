package be.ucll.quizmaster.quizmaster.controller;

import be.ucll.quizmaster.quizmaster.AbstractIntegrationTesting;
import be.ucll.quizmaster.quizmaster.controller.dto.MemberDTO;
import be.ucll.quizmaster.quizmaster.model.Member;
import be.ucll.quizmaster.quizmaster.repo.MemberRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends AbstractIntegrationTesting {

    @Autowired
    MemberRepo memberRepo;

    @Test
    void createMember() throws Exception {
        MemberDTO newMember = new MemberDTO.Builder()
                .email("newTest@test.test")
                .username("newTest")
                .password("newTest")
                .build();

        MvcResult mvcPost = mockMvc.perform(MockMvcRequestBuilders.post("/members")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .content(toJson(newMember))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        Member dbMember = memberRepo.getByEmailAddress(newMember.getEmail()).get();

        assertEquals(newMember.getEmail(), dbMember.getEmailAddress());
        assertEquals(newMember.getUsername(), dbMember.getUsername());
        assertNotNull(dbMember.getPassword());
        assertNotEquals(newMember.getPassword(), dbMember.getPassword());
        //assertEquals(encoder.encode(newMember.getPassword()), dbMember.getPassword());
    }

    @Test
    void getMemberById() {
        //todo method still returns null
    }


}