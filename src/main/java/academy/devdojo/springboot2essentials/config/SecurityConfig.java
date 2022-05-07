package academy.devdojo.springboot2essentials.config;

import academy.devdojo.springboot2essentials.service.DefaultUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DefaultUserDetailsService userDetailsService;

    /**
     * BasicAuthenticationFilter (Base64 Crypt)
     * UsernamePasswordAuthenticationFilter (User and Password)
     * DefaultLoginPageGeneratingFilter (Page Login)
     * DefaultLogoutPageGeneratingFilter (Page Logout)
     * FilterSecurityInterceptor (Check if authorize)
     * Authentication -> Authorization
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/animes/admin/**").hasRole("ADMIN") // Restringe URL a um tipo de Role (Admin)
                .antMatchers("/animes/**").hasRole("USER") // Restringe URL a um tipo de Role (USER)
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info("Password encoded {}" , passwordEncoder.encode("1234"));

        /**
         * Usuarios especificos em Memória
         */
        auth.inMemoryAuthentication()
                .withUser("admin2")
                .password("{noop}1234")
                .roles("USER", "ADMIN")
                .and()
                .withUser("user2")
                .password("{noop}1234")
                .roles("USER");

            /**
            * Usuários originados do banco de dados (Sobrescrevendo as interfaces de UserDetails)
            */
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
