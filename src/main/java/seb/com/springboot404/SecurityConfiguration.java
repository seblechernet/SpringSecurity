package seb.com.springboot404;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder encoder(){
        return  new BCryptPasswordEncoder();
    }
    @Autowired
    private SSUserDetailsService userDetailService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new SSUserDetailsService(userRepository);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()//.antMatchers("/login").permitAll()
//                .antMatchers("/users").hasAuthority("USER")
                .antMatchers("/","/h2/**","/register").permitAll()
                .antMatchers("/secure").hasAuthority("ADMIN")
//                .antMatchers("/").hasAnyAuthority("USER,ADMIN")
//                .antMatchers("/admin").hasAuthority("ADMIN")

                //FORGOT THE LAST TWO LINES!!
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll()
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").permitAll()
        .and()
        .httpBasic();
        http.csrf().disable();
        http.headers().frameOptions().disable();

    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //COMMA SEPARATE AUTHORITIES (SEPARATE STRINGS)
//        auth.inMemoryAuthentication().withUser("user").password(encoder().encode("password")).authorities("USER")
//        .and().withUser("dave").password(encoder().encode("begreat")).authorities("ADMIN")//,"ADMIN")
//                .and().passwordEncoder(encoder());
        auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(encoder());
    }
}