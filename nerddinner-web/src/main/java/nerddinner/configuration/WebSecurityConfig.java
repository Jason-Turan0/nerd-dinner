package nerddinner.configuration;

import nerddinner.controller.Paths;
import nerddinner.data.repositories.NerdRepository;
import nerddinner.domain.NerdUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        Paths.Login.forgotPassword,
                        Paths.Login.registerAccount,
                        Paths.Login.login,
                        Paths.Login.resetPassword,
                        "/console/**",
                        "/webjars/**",
                        "/js/**",
                        "/css/**",
                        "/images/**"
                )
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(Paths.Login.login)
                .passwordParameter("password")
                .usernameParameter("username")
                .defaultSuccessUrl(Paths.Home.index)
                .and()
                .logout()
                .logoutSuccessHandler(new LogoutSuccessHandlerImpl())
                .permitAll();
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService);
        builder.authenticationProvider(authProvider());
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService(NerdRepository nr){
        return new NerdUserDetailsService(nr);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}