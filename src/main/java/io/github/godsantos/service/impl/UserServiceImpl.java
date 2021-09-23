package io.github.godsantos.service.impl;

import io.github.godsantos.domain.entity.UserEntity;
import io.github.godsantos.domain.repository.UserRepository;
import io.github.godsantos.exception.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository repository;

    @Transactional
    public UserEntity toSave(UserEntity userEntity){
        return repository.save(userEntity);
    }

    public UserDetails authenticate(UserEntity userEntity){
        UserDetails user = loadUserByUsername(userEntity.getLogin());
        boolean passwordMatch = encoder.matches( userEntity.getPassword(), user.getPassword() );

        if(passwordMatch){
            return user;
        }

        throw new InvalidPasswordException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        String[] roles = userEntity.isAdmin() ?
                new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User
                .builder()
                .username(userEntity.getLogin())
                .password(userEntity.getPassword())
                .roles(roles)
                .build();
    }

}
