package com.spacecodee.sprpsqlsec.config.security;

import com.spacecodee.sprpsqlsec.data.vo.UDUserVo;
import com.spacecodee.sprpsqlsec.exceptions.ObjectNotFoundException;
import com.spacecodee.sprpsqlsec.persistence.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@Configuration
public class SecurityBeansInjector {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final IUserRepository userRepository;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return this.authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationStrategy = new DaoAuthenticationProvider();
        daoAuthenticationStrategy.setPasswordEncoder(this.passwordEncoder());
        daoAuthenticationStrategy.setUserDetailsService(this.userDetailsService());

        return daoAuthenticationStrategy;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            var user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ObjectNotFoundException("User isn't found with username: " + username));
            var uDetails = new UDUserVo();
            uDetails.setId(user.getId());
            uDetails.setName(user.getName());
            uDetails.setUsername(user.getUsername());
            uDetails.setPassword(user.getPassword());

            return uDetails;
        };
    }
}
