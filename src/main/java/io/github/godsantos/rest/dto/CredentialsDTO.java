package io.github.godsantos.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               // I'm using Lombok so, I don't need generate gets and sets
@NoArgsConstructor  // I'm using Lombok so, I don't need create constructor with no args
public class CredentialsDTO {
    private String login;
    private String password;
}
