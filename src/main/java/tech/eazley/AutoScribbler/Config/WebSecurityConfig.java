package tech.eazley.AutoScribbler.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import tech.eazley.AutoScribbler.Services.PrincipalUserDetailService;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig {
    @Autowired
    PrincipalUserDetailService appUserDetailService;
    AuthenticationManager authenticationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);

            authBuilder.userDetailsService(appUserDetailService).passwordEncoder(passwordEncoder());
            authenticationManager = authBuilder.build();

            httpSecurity.csrf().disable().cors().disable().authorizeHttpRequests().antMatchers("/api/users/auth").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .authenticationManager(authenticationManager)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
