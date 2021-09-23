package io.github.godsantos.rest.controller;

import io.github.godsantos.domain.entity.Client;
import io.github.godsantos.domain.repository.Clients;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private Clients clients;
    private String msgClientNotFound = "Client not found";

    public ClientController(Clients clients) {
        this.clients = clients;
    }

    @GetMapping("{id}")
    public Client getClientById(@PathVariable Integer id ){
        return clients
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                msgClientNotFound));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client save(@RequestBody @Valid Client client){
        return clients.save(client);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Integer id ){
        clients.findById(id)
                .map( client -> {
                    clients.delete(client );
                    return client;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        msgClientNotFound) );

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update( @PathVariable Integer id,
                        @RequestBody @Valid Client client){
        clients
                .findById(id)
                .map( clientExistent -> {
                    client.setId(clientExistent.getId());
                    clients.save(client);
                    return clientExistent;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    msgClientNotFound) );
    }

    @GetMapping
    public List<Client> find(Client filter ){
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(
                                            ExampleMatcher.StringMatcher.CONTAINING );

        Example example = Example.of(filter, matcher);
        return clients.findAll(example);
    }
}
