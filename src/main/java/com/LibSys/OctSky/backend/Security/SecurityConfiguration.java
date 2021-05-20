package com.LibSys.OctSky.backend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("Select Staff_email, password, enabled from credentials where Staff_email = ?")
                .authoritiesByUsernameQuery("Select email, role from getemailrole where email = ? ")
                .usersByUsernameQuery("Select Visitor_cardNumber, password, enabled from visitorcredentials where Visitor_cardNumber = ?")
                .authoritiesByUsernameQuery("Select cardNumber, role from getcardrole where cardNumber = ? ");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/visitor").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("ADMIN", "LIBRARIAN")
                .antMatchers("/").permitAll()
                .antMatchers("/vaadinServlet/UIDL/**").permitAll()
                .antMatchers("/vaadinServlet/HEARTBEAT/**").permitAll()
                .antMatchers("/vaadinServlet/**").permitAll()
                .antMatchers("/error").permitAll()
                .and().formLogin();
        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}