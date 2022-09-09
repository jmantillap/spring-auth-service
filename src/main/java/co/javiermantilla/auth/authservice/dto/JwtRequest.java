package co.javiermantilla.auth.authservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JwtRequest {

    String username;
    String password;
}