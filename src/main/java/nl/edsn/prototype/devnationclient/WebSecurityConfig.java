package nl.edsn.prototype.devnationclient;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().authenticationEntryPoint(getRestAuthenticationEntryPoint()).and()
            .csrf()
                .disable()
            .authorizeRequests()
                .antMatchers("/message")
                    .authenticated()
                .antMatchers("/*")
                    .permitAll()
                .and()
            .oauth2Client()
                .and()
            .oauth2Login()
        ;
    }

    private AuthenticationEntryPoint getRestAuthenticationEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/login")
//                .antMatchers("/message")
        ;
    }

    //    private final ClientRegistrationRepository clientRegistrationRepository;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .cors().configurationSource(corsConfigurationSource())
//                .and()
//            .csrf()
//                .disable()
//            .authorizeRequests()
//                .antMatchers("/details")
//                    .authenticated()
//                .antMatchers("/*")
//                    .permitAll()
//                .and()
//            .oauth2Client()
//                .and()
//            .oauth2Login()
//                .authorizationEndpoint()
//                    .authorizationRequestResolver(
//                        new CustomAuthorizationRequestResolver(
//                                this.clientRegistrationRepository))
//        ;
//    }
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8005"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST", "OPTIONS", "HEAD"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

}
