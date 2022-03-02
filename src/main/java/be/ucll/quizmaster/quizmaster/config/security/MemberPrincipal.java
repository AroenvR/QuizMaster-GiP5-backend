package be.ucll.quizmaster.quizmaster.config.security;

import be.ucll.quizmaster.quizmaster.model.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;

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
        return null;
    }

    @Override
    public String getPassword() {
        return passwordEncoder.encode(member.getPassword());
    }

    @Override
    public String getUsername() {
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
}
