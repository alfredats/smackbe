package visa.vttp.csf.smackBE.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import visa.vttp.csf.smackBE.service.AuthService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private UserDetailsService uSvc;
  private JWTAuthProps ap;

  @Autowired
  SecurityConfig(AuthService aSvc, JWTAuthProps ap) {
    this.uSvc = aSvc;
    this.ap = ap;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf((csrf) -> csrf.disable())
        .cors().and()
        .addFilterBefore(this.getJWTAuthFilter(), UsernamePasswordAuthenticationFilter.class)
        .authenticationManager(authenticationManager())
        .authorizeHttpRequests((auth) -> {
          auth
              .mvcMatchers("/auth/**", "/chat/**").permitAll()
              .anyRequest().authenticated();
        })
        .sessionManagement((sessionManagement) -> {
          sessionManagement.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        })
        .httpBasic();
    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
    CorsConfiguration cc = new CorsConfiguration();
    cc.setAllowedMethods(Arrays.asList("GET", "POST", "PUT"));
    cc.applyPermitDefaultValues();
    src.registerCorsConfiguration("/**", cc);
    return src;
  }

  @Bean
  AuthenticationManager authenticationManager() {
    DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
    dao.setPasswordEncoder(passwordEncoder());
    dao.setUserDetailsService(uSvc);
    return new ProviderManager(Collections.singletonList(dao));
  }

  JWTAuthFilter getJWTAuthFilter() {
    final JWTAuthFilter f = new JWTAuthFilter(this.uSvc, this.ap);
    return f;
  }

}
