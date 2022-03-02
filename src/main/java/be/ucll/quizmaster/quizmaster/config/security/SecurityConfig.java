package be.ucll.quizmaster.quizmaster.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberUserDetailService memberUserDetailService;

    /*
        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.httpBasic().and().authorizeRequests()
                    .anyRequest().permitAll();
        }

        protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authProvider());
        }
    */

    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("Aroen").password(passwordEncoder().encode("THIS")).roles("USER")
                .and()
                .withUser("Oderick").password(passwordEncoder().encode("IS")).roles("USER")
                .and()
                .withUser("Yvo").password(passwordEncoder().encode("TOP")).roles("USER")
                .and()
                .withUser("Ates").password(passwordEncoder().encode("TIER")).roles("USER")
                .and()
                .withUser("Wout").password(passwordEncoder().encode("SECURITY")).roles("USER");
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(memberUserDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
