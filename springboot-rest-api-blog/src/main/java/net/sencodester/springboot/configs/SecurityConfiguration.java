package net.sencodester.springboot.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authz -> authz
                        .antMatchers("/api/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails khalid = User.builder().username("khalid").password(passwordEncoder()
                .encode("password1")).roles("ADMIN").build();
        UserDetails abraham = User.builder().username("abraham").password(passwordEncoder()
                .encode("password2")).roles("ADMIN").build();
        UserDetails diallo = User.builder().username("diallo").password(passwordEncoder()
                .encode("password3")).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(khalid, abraham, diallo);
    }
}
