package net.sencodester.springboot.configs;

import net.sencodester.springboot.Security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Désactive le CSRF
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers(HttpMethod.GET,"/api/**").permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());

        // Pas besoin de configurer explicitement le UserDetailsService et le PasswordEncoder ici
        // Spring Security les utilisera automatiquement car ils sont définis comme des beans

        return http.build();
    }
 /*   @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails khalid = User.builder().username("khalid").password(passwordEncoder()
                .encode("password1")).roles("ADMIN").build();
        UserDetails abraham = User.builder().username("abraham").password(passwordEncoder()
                .encode("password2")).roles("ADMIN").build();
        UserDetails diallo = User.builder().username("diallo").password(passwordEncoder()
                .encode("password3")).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(khalid, abraham, diallo);
    }*/
}
