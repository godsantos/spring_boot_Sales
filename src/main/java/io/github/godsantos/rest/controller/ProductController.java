package io.github.godsantos.rest.controller;

import io.github.godsantos.domain.entity.Product;
import io.github.godsantos.domain.repository.Products;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private Products repository;
    private String msgProductNotFound = "Product not found";
    public ProductController(Products repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Product save(@RequestBody @Valid Product product){
        return repository.save(product);
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void update( @PathVariable Integer id,
                        @RequestBody @Valid Product product){
        repository
                .findById(id)
                .map( p -> {
                   product.setId(p.getId());
                   repository.save(product);
                   return product;
                }).orElseThrow( () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        msgProductNotFound));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id){
        repository
                .findById(id)
                .map( p -> {
                    repository.delete(p);
                    return Void.TYPE;
                }).orElseThrow( () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        msgProductNotFound));
    }

    @GetMapping("{id}")
    public Product getById(@PathVariable Integer id){
        return repository
                .findById(id)
                .orElseThrow( () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        msgProductNotFound));
    }

    @GetMapping
    public List<Product> find(Product filter ){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING );

        Example example = Example.of(filter, matcher);
        return repository.findAll(example);
    }
}
