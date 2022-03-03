package be.ucll.quizmaster.quizmaster.controller;

import be.ucll.quizmaster.quizmaster.controller.dto.MemberDto;
import be.ucll.quizmaster.quizmaster.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("members")
public class MemberController {

    private final Logger logger = LoggerFactory.getLogger(TopicController.class);

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public ResponseEntity<MemberDto> getMemberById(){
        return null;
    }

    @PostMapping()
    public ResponseEntity<?> createMember(@RequestBody MemberDto dto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(memberService.saveMember(dto));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }




}
