package be.ucll.quizmaster.quizmaster.config.security;

import be.ucll.quizmaster.quizmaster.model.Member;
import be.ucll.quizmaster.quizmaster.repo.MemberRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberUserDetailService implements UserDetailsService {

    private final MemberRepo memberRepo;
    private final Logger logger = LoggerFactory.getLogger(MemberUserDetailService.class);

    public MemberUserDetailService(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.warn("load user -> " + email);
        Member member = memberRepo.getByEmailAddress(email).orElseThrow(() -> new UsernameNotFoundException(String.format("user with %s not fount", email)));
        logger.warn("account found -> " + member.toString());
        MemberPrincipal memberPrincipal = new MemberPrincipal(member);
        logger.warn(memberPrincipal.toString());
        return memberPrincipal;

    }






}
