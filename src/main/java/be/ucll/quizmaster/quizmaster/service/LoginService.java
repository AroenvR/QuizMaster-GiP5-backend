package be.ucll.quizmaster.quizmaster.service;


import be.ucll.quizmaster.quizmaster.config.security.MemberPrincipal;
import be.ucll.quizmaster.quizmaster.model.Member;
import be.ucll.quizmaster.quizmaster.service.exceptions.NotAuthenticatedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public Member getLoggedInMember(String actionRequested) throws NotAuthenticatedException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken || authentication == null) {
            throw new NotAuthenticatedException(actionRequested);
        }

        MemberPrincipal memberPrincipal = (MemberPrincipal)authentication.getPrincipal();
        return memberPrincipal.getMember();

    }

}
