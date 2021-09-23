package io.github.godsantos.domain.repository;

import io.github.godsantos.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Products extends JpaRepository<Product,Integer> {
}
