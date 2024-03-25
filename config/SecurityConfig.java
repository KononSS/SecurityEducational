package ru.kononov.springcourseSecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kononov.springcourseSecurity.services.PersonDetailsService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PersonDetailsService personDetailsService;
@Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

protected void configure(HttpSecurity http) throws Exception{

    //Конфигурируем Security
    http//отключаем защиту от межсайтовой подделки запросов
            .authorizeRequests()
            .antMatchers("/auth/login","/auth/registration","/error").permitAll()
            .anyRequest().authenticated()
            .and()
            //Конфигурируем авторизацию
            .formLogin().loginPage("/auth/login")
            .loginProcessingUrl("/process_login")
            .defaultSuccessUrl("/hello", true)
            .failureUrl("/auth/login?error")
            .and()
            .logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login");
}
    //Настраевает аутентификацию
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(personDetailsService).passwordEncoder(passwordEncoder());
}
@Bean
public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}
}
