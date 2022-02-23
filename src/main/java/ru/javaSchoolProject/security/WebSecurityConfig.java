package ru.javaSchoolProject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private JwtProvider jwtProvider;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //Disable CSRF Protection, not needed
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Not to save info about user's session, not needed for api
                .and()
                .authorizeRequests()//
                .antMatchers("/auth/*").permitAll() // Allow everyone to register and login
                .antMatchers("/manage/*").permitAll() // Allow everyone to register and login
//                .antMatchers("/user/*").hasAuthority("USER")
                .anyRequest().authenticated()  // Other things only for authorized users
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

        ;


    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




}
