package io.github.godsantos.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data               // I'm using Lombok so, I don't need generate gets and sets
@AllArgsConstructor // I'm using Lombok so, I don't need create constructor with args
@NoArgsConstructor  // I'm using Lombok so, I don't need create constructor with no args
@Builder            // I'm using builder so I don't need to create a instance of the object
@Entity
@Table(name = "userEntity")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    @NotEmpty(message = "{field.login.mandatory}")
    private String login;
    @Column
    @NotEmpty(message = "{field.password.mandatory}")
    private String password;
    @Column
    private boolean admin;

}
