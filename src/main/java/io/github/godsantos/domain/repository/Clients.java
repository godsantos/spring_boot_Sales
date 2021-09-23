package io.github.godsantos.domain.repository;

import io.github.godsantos.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Clients extends JpaRepository<Client, Integer > {

    @Query(value = " select * from client c where c.name like '%:name%' ", nativeQuery = true)
    List<Client> findByName(@Param("name") String name ); //

    @Query(" delete from Client c where c.name =:name ")
    @Modifying
    void deleteByNome(String name);

    boolean existsByName(String name);

    @Query(" select c from Client c left join fetch c.orders where c.id = :id  ")
    Client findClientFetchOrders(@Param("id") Integer id );


}
