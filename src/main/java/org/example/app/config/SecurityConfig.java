package org.example.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private PasswordEncoder passwordEncoder;
    SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("ADMIN", "CLIENT").build(),
                User.withUsername("client").password(passwordEncoder.encode("1234")).roles("CLIENT").build()
        );
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/user/patients").permitAll());
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/css/**", "/vendor/**").permitAll());
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/user/**").hasRole("CLIENT"));
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/admin/**").hasRole("ADMIN"));
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        return http.build();
    }
}
