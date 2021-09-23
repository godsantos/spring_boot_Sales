package io.github.godsantos.service;

import io.github.godsantos.domain.entity.Order;
import io.github.godsantos.domain.enums.StatusOrder;
import io.github.godsantos.rest.dto.OrderDTO;

import java.util.Optional;

public interface OrderService {
    Order toSave(OrderDTO dto );
    Optional<Order> getCompleteOrder(Integer id);
    void updateStatus(Integer id, StatusOrder statusOrder);
}
