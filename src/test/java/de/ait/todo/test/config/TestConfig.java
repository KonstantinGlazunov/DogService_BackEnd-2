package de.ait.todo.test.config;

import de.ait.todo.models.User;
import de.ait.todo.security.details.AuthenticatedUser;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@TestConfiguration
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@Profile("test")
public class TestConfig {
    public static final String MOCK_ADMIN = "admin";

    public static final String MOCK_USER = "user";


    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                if (username.equals(MOCK_ADMIN)) {
                    return new AuthenticatedUser(
                            User.builder()
                                    .id(1L)
                                    .email(MOCK_ADMIN)
                                    .role(User.Role.ADMIN)
                                    .userName("Admin")
                                    .state(User.State.CONFIRMED)
                                    .build()
                    );
                } else if(username.equals(MOCK_USER)){
                    return new AuthenticatedUser(User.builder()
                            .id(1L)
                            .email(MOCK_USER)
                            .role(User.Role.USER)
                            .userName("User")
                            .state(User.State.CONFIRMED)
                            .build());

                } else throw new UsernameNotFoundException("Пользователь не найден");
            }
        };
    }


}
