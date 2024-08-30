package com.shurygin.englishroad;

import com.shurygin.englishroad.model.User;
import com.shurygin.englishroad.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(request -> {
                    request
                            //.requestMatchers("/game").hasRole("USER")
                            .anyRequest().permitAll();
                })
//                .csrf(AbstractHttpConfigurer::disable)
                .csrf(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults())
                .formLogin(form -> form
                        .loginPage("/auth")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/", true)
                )
                .httpBasic(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )

                .build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username ->
            userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User '"+username+"' not found!"));

        // In memory
//        List<UserDetails> list = new ArrayList<>();
//        list.add(new User("user1", passwordEncoder.encode("123")
//                , Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
//        list.add(new User("user2", passwordEncoder.encode("123")
//                , Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
//        return new InMemoryUserDetailsManager(list);
    }


}
