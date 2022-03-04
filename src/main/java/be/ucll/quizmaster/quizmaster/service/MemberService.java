package be.ucll.quizmaster.quizmaster.service;

import be.ucll.quizmaster.quizmaster.controller.dto.MemberDTO;
import be.ucll.quizmaster.quizmaster.model.Member;
import be.ucll.quizmaster.quizmaster.repo.MemberRepo;
import be.ucll.quizmaster.quizmaster.service.exceptions.EmailExistsException;
import be.ucll.quizmaster.quizmaster.service.exceptions.UsernameExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class MemberService {

    private final Logger logger = LoggerFactory.getLogger(MemberService.class);

    private MemberRepo memberRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public MemberService(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }

    @Transactional
    public MemberDTO saveMember(MemberDTO memberDto) throws EmailExistsException, UsernameExistsException {

        if (emailExists(memberDto.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email adress: " + memberDto.getEmail());
        }

        if (usernameExists(memberDto.getUsername())) {
            throw new UsernameExistsException(
                    "There is an account with that username: " + memberDto.getUsername());
        }

        Member member = new Member();
        member.setEmailAddress(memberDto.getEmail());
        member.setUsername(memberDto.getUsername());
        member.setPassword(encoder.encode(memberDto.getPassword()));
        member.setMemberId(UUID.randomUUID());

        logger.info("saving member -> " + member);

        memberRepo.save(member);
        return memberDto;

    }

    private boolean emailExists(String email){

       return memberRepo.existsByEmailAddress(email);

    }

    private boolean usernameExists(String username){

        return memberRepo.existsByUsername(username);

    }

    public Optional<Member> getMember(String email) {

        return memberRepo.getByEmailAddress(email);

    }
}
