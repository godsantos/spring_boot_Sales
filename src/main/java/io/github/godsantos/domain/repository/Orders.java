package io.github.godsantos.domain.repository;

import io.github.godsantos.domain.entity.Client;
import io.github.godsantos.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface Orders extends JpaRepository<Order, Integer> {

    List<Order> findByClient(Client client);

    @Query(" select o from Order o left join fetch o.items where o.id = :id ")
    Optional<Order> findByIdFetchItems(@Param("id") Integer id);
}
