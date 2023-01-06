package com.thehecklers.aircraftpositions;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Bean
    UserDetailsService authentication() {
        UserDetails sasha = User.builder()
                .username("sasha")
                .password(passwordEncoder.encode("sasha"))
                .roles("USER", "ADMIN")
                .build();

        UserDetails masha = User.builder()
                .username("masha")
                .password(passwordEncoder.encode("masha"))
                .roles("USER")
                .build();

        System.out.println(" >>> Sasha's password: " + sasha.getPassword());
        System.out.println(" >>> Masha's password: " + masha.getPassword());

        return new InMemoryUserDetailsManager(sasha, masha);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/aircraftadmin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }
}
