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
                .csrf().disable() //Отключает CSRF Protection, поскольку она не нужна для API
                .httpBasic() //Базовая http аутентификация
                .and()
                .sessionManagement().disable(); //не следует хранить информацию о сеансе для пользователей, поскольку это не нужно для API

        http.authorizeRequests()//
                .antMatchers("/auth/*").permitAll() // Разрешаем всем регистрироваться и входить
//                .antMatchers("/proba/*").hasRole("ADMIN");
//                .antMatchers("/user/*").hasRole("USER")
                .anyRequest().authenticated();  // Ограничение для всех остальных


        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




}
