package be.ucll.quizmaster.quizmaster.controller;

import be.ucll.quizmaster.quizmaster.controller.dto.MemberDto;
import be.ucll.quizmaster.quizmaster.service.MemberService;
import org.springframework.http.ResponseEntity;

public class MemberController {

    private MemberService memberService;

    public ResponseEntity<MemberDto> getMemberById(){
        return null;
    }

    public ResponseEntity<MemberDto> createMember(MemberDto dto){
        return null;
    }



}
