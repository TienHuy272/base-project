package hnt.com.base.entities.embedded;

import lombok.Data;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@Data
public class Address {
    private String street;
    private String city;
    private String zipcode;
}
