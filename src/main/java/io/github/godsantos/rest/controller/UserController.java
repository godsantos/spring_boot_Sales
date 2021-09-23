package io.github.godsantos.rest.controller;

import io.github.godsantos.domain.entity.UserEntity;
import io.github.godsantos.exception.InvalidPasswordException;
import io.github.godsantos.rest.dto.CredentialsDTO;
import io.github.godsantos.rest.dto.TokenDTO;
import io.github.godsantos.security.jwt.JwtService;
import io.github.godsantos.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserEntity toSave(@RequestBody @Valid UserEntity userEntity){
        String passwordEncoded = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(passwordEncoded);
        return userService.toSave(userEntity);
    }

    @PostMapping("/auth")
    public TokenDTO authenticate(@RequestBody CredentialsDTO credentials){
        try{
            UserEntity userEntity = UserEntity.builder()
                    .login(credentials.getLogin())
                    .password(credentials.getPassword()).build();
            UserDetails userAuthenticated = userService.authenticate(userEntity);
            String token = jwtService.generateToken(userEntity);
            return new TokenDTO(userEntity.getLogin(), token);
        } catch (UsernameNotFoundException | InvalidPasswordException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
