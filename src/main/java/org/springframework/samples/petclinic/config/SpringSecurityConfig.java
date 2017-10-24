package org.springframework.samples.petclinic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

       http
            .authorizeRequests()
            .antMatchers("/", "/home","/resources/**").permitAll()
            .antMatchers("/owners/*/pets/new").hasRole("ADD")
            .antMatchers("/owners/new").hasRole("ADD")
            .antMatchers("/owners/*/pets/*/edit").hasRole("EDIT")
            .antMatchers("/owners/*/edit").hasRole("EDIT")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
            .logout()
            .permitAll();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("Bill").password("password").roles("USER")
                .and()
                .withUser("Stephen").password("password").roles("USER","EDIT")
                .and()
                .withUser("Samantha").password("password").roles("USER","EDIT","ADD");
    }

}
