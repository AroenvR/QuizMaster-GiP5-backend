package be.ucll.quizmaster.quizmaster.controller;

import be.ucll.quizmaster.quizmaster.AbstractIntegrationTesting;
import be.ucll.quizmaster.quizmaster.controller.dto.MemberDTO;
import liquibase.pro.packaged.M;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends AbstractIntegrationTesting {

    @Test
    void getMemberById() {
        //todo: find a way to compare Id's since we never return an Id
    }

    @Test
    void createMember() throws Exception {
        MemberDTO newMember = new MemberDTO.Builder()
                .email("test@test.test")
                .username("testtest")
                .password("test")
                .build();

        MvcResult mvcPost = this.mockMvc.perform(MockMvcRequestBuilders.post("/members")
                        .with(httpBasic(oderick.getEmail(), oderick.getPassword()))
                        .content(toJson(newMember))
                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andReturn();

        MemberDTO savedMember = fromMvcResult(mvcPost, MemberDTO.class);

        assertEquals(savedMember.getEmail(), newMember.getEmail());
        assertEquals(savedMember.getUsername(), newMember.getUsername());
        assertEquals(savedMember.getPassword(), newMember.getPassword());

        /*TODO
        NEED to get asertion on "getMemberByMail()" or "getMemberByUsername()" to check if the member actually exists in the db or not.
         */
    }
}