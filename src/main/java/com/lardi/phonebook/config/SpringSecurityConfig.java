package com.lardi.phonebook.config;

import com.lardi.phonebook.error.CustomAccessDeniedHandler;
import com.lardi.phonebook.message.Message;
import com.lardi.phonebook.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    PasswordEncoder passwordEncoder;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    /**
     * The number 4 represents how strong you want the encryption.
     * It can be in a range between 4 and 31.
     * If you do not put a number the program will use one randomly each time
     * That you start the application, so your encrypted passwords will not work well
     *
     * @return Autentifacation set hahdler - перенаправить метод пост на гет
     */
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder(4);
//    }
    @Bean("bCryptPasswordEncoder")
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        return bCryptPasswordEncoder;
    }

    /**
     * Registers the service for users and the password encryption
     * @param auth
     * @throws Exception
     */
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(bCryptPasswordEncoder());
//    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
//                .passwordEncoder(bCryptPasswordEncoder());
    }


    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint() {
        return new CustomBasicAuthenticationEntryPoint();
    }

    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * roles user allow to access /user/**
     * custom 403 access denied handler
     *
     * @return
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/home", "/register", "/about",
                        "/webjars/bootstrap/3.3.7/css/bootstrap.min.css",
                        "/css/main.css",
                        "/alert/jAlert.css",
                        "/js/reset.js",
                        "/alert/jAlert.js",
                        "/alert/jAlert-functions.js",
                        "/webjars/bootstrap/3.3.7/js/bootstrap.min.js",
                        "/resources/**",
                        "/test/**", "/error").permitAll()
                .antMatchers("/**").hasAnyRole("USER")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successForwardUrl("/user")
                .failureUrl("/login?error=true")
                .usernameParameter("login")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                .httpBasic()
                .realmName(Message.REALM.getDescription())
                .authenticationEntryPoint(getBasicAuthEntryPoint());
    }
}
