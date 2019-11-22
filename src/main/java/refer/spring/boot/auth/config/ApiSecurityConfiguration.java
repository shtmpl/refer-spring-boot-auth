package refer.spring.boot.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import refer.spring.boot.auth.controller.api.filter.JwtRequestFilter;
import refer.spring.boot.auth.repository.AccountRepository;
import refer.spring.boot.auth.service.AccountService;
import refer.spring.boot.auth.service.AccountServiceImpl;
import refer.spring.boot.auth.service.AuthService;
import refer.spring.boot.auth.service.AuthServiceImpl;
import refer.spring.boot.auth.service.JwtService;
import refer.spring.boot.auth.service.JwtServiceImpl;
import refer.spring.boot.auth.service.UserDetailsServiceImpl;

@EnableWebSecurity
@Configuration
public class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private AccountRepository accountRepository;

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtService jwtService() {
        return new JwtServiceImpl(secret);
    }

    @Bean
    public AccountService accountService() {
        return new AccountServiceImpl(passwordEncoder(), accountRepository);
    }

    @Bean
    public AuthService authService() throws Exception {
        return new AuthServiceImpl(authenticationManagerBean(), accountService(), jwtService());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(accountRepository);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtRequestFilter(authService()), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/auth/sign-in").permitAll()
                .anyRequest().authenticated();
    }
}
