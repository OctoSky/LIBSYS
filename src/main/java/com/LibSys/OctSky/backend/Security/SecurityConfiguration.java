package com.LibSys.OctSky.backend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_SUCCESS_URL = "/";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGOUT_SUCCESS_URL = "/";



    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("Select Staff_email, password, enabled from credentials where Staff_email = ?")
                .authoritiesByUsernameQuery("Select email, role from staffauthview where email = ? ");
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("Select librarycard_cardNumber, password, enabled from visitorcredentials where librarycard_cardNumber = ?")
                .authoritiesByUsernameQuery("Select librarycard_cardNumber, role from visitorauthview where librarycard_cardNumber = ? ");


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user").hasRole("ADMIN")
                .antMatchers("/archive").hasAnyRole("ADMIN", "LIBRARIAN")
                .antMatchers("/visitor").hasAnyRole("ADMIN", "LIBRARIAN")
                .antMatchers("/books").hasAnyRole("ADMIN", "LIBRARIAN")
                .antMatchers("/archive").hasAnyRole("ADMIN", "LIBRARIAN")
                .antMatchers("/borrow").hasAnyRole("ADMIN", "LIBRARIAN")
                .antMatchers("/").permitAll()
                .antMatchers("/vaadinServlet/UIDL/**").permitAll()
                .antMatchers("/vaadinServlet/HEARTBEAT/**").permitAll()
                .antMatchers("/vaadinServlet/**").permitAll()
                .antMatchers("/error").permitAll()
                .and().formLogin()
                .successForwardUrl(LOGIN_SUCCESS_URL)
                .loginProcessingUrl(LOGIN_PROCESSING_URL)
                .failureUrl(LOGIN_FAILURE_URL)
                .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    }






