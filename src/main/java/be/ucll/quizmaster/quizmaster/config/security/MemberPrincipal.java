package be.ucll.quizmaster.quizmaster.config.security;

import be.ucll.quizmaster.quizmaster.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.HashSet;

public class MemberPrincipal implements UserDetails {

    private final Member member;
    private final Logger logger = LoggerFactory.getLogger(MemberPrincipal.class);
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberPrincipal(Member member) {
        this.member = member;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        logger.warn("getting authorities for " + member.getEmailAddress());
        return new HashSet<>();
    }

    @Override
    public String getPassword() {
        logger.warn("getting password for " + member.getEmailAddress());
        String encode = passwordEncoder.encode(member.getPassword());
        logger.warn("encoded password = " + encode);
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        logger.warn("getting password for " + member.getEmailAddress());
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Member getMember() {
        return member;
    }
}
