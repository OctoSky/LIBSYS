package com.LibSys.OctSky.backend.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/login";

    /**
     * The main security function for allowing users to login or redirects a non-user to the /login route.
     * @param http
     * @throws Exception
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .requestCache().requestCache(new CustomRequestCache())
                .and().authorizeRequests()
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

                .anyRequest().authenticated()

                .and().formLogin()
                .loginPage(LOGIN_URL).permitAll()
                .loginProcessingUrl(LOGIN_PROCESSING_URL)
                .failureUrl(LOGIN_FAILURE_URL)
                .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
    }

    /**
     * Users allowed to access the "Personal" views. The LoginOverlay accepts any of these userdetails.
     * @return
     */

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("Tim").password("{noop}Kenedi").roles("USER").build();
        UserDetails user1 = User.withUsername("Emil").password("{noop}Belin").roles("USER").build();
        UserDetails user2 = User.withUsername("Joel").password("{noop}Reboia").roles("USER").build();
        UserDetails user3 = User.withUsername("Ahmed").password("{noop}Surchi").roles("USER").build();
        UserDetails user4 = User.withUsername("Sebastian").password("{noop}Ivanoff").roles("USER").build();

        return new InMemoryUserDetailsManager(user,user1,user2,user3,user4);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/VAADIN/**",
                "/css/**",
                "/js/**",
                "/",
                "/visitor",
                "/favicon.ico",
                "/robots.txt",
                "/sw.js",
                "/Bokning/**",
                "/offline.html",
                "/icons/**",
                "/images/**",
                "/styles/**",
                "/h2-console/**");
    }
}
