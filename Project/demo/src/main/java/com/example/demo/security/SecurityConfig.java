package com.example.demo.security;

import com.example.demo.filter.CustomAuthenticationFilter;
import com.example.demo.filter.CustomAuthorizationFilter;
import com.example.demo.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), userRepository);
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        http.cors().and().csrf().disable();

        http.cors().configurationSource(request -> {
            var cors = new CorsConfiguration();
            cors.setAllowedOrigins(List.of("*"));
            cors.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"));
            cors.setAllowedHeaders(List.of("*"));
            return cors;});

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers(POST,"/roles").permitAll();
        http.authorizeRequests().antMatchers(GET,"/roles").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(PUT,"/roles").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(DELETE,"/roles").hasAnyAuthority("ADMIN");

        http.authorizeRequests().antMatchers(POST,"/user/**").permitAll();
        http.authorizeRequests().antMatchers(GET,"/user/**").hasAnyAuthority("USER","ADMIN");
        http.authorizeRequests().antMatchers(PUT,"/user/**").hasAnyAuthority("USER","USER");
        http.authorizeRequests().antMatchers(DELETE,"/user/**").hasAnyAuthority("USER","ADMIN");

        http.authorizeRequests().antMatchers(POST,"/category").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(GET,"/category").permitAll();
        http.authorizeRequests().antMatchers(GET,"/category/byName/**").permitAll();
        http.authorizeRequests().antMatchers(PUT,"/category").permitAll();
        http.authorizeRequests().antMatchers(DELETE,"/category").hasAnyAuthority("ADMIN");

        http.authorizeRequests().antMatchers(GET,"/article/**").permitAll();
        http.authorizeRequests().antMatchers(POST,"/article/**").hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers(DELETE,"/article/**").hasAnyAuthority("USER","ADMIN");
        http.authorizeRequests().antMatchers(PUT,"/article/**").hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers(GET,"/article/search/**").permitAll();

        http.authorizeRequests().antMatchers(POST,"/comment").permitAll();
        http.authorizeRequests().antMatchers(GET,"/comment").permitAll();
        http.authorizeRequests().antMatchers(PUT,"/comment").permitAll();
        http.authorizeRequests().antMatchers(DELETE,"/comment").permitAll();


        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);



    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }



}

