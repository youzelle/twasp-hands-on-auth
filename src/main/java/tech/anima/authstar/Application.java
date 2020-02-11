package tech.anima.authstar;

import java.util.Arrays;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

// https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#oauth2resourceserver
@SpringBootApplication
@EnableWebSecurity
public class Application extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authz -> authz
                .anyRequest().permitAll())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .csrf().disable();
//                .antMatchers("/", "/index.html", "/app.js", "/admin.html", "/admin.js", "/all.css").permitAll()
//                .anyRequest().authenticated())
    }

    @Bean
    public AvailableTickets availableTickets() {
        final List<Ticket> initialTickets = Arrays.asList(
                new Ticket("Oslo", 1002),
                new Ticket("Stockholm", 1004),
                new Ticket("Rio", 1562),
                new Ticket("Nairobi", 2000),
                new Ticket("Helsinki", 1006),
                new Ticket("Berlin", 890));
        return new AvailableTickets(initialTickets, 10);
    }

    @Bean
    public BoughtTickets boughtTickets() {
        return new BoughtTickets();
    }

}
