package io.github.godsantos.rest.controller;

import io.github.godsantos.domain.entity.OrderItems;
import io.github.godsantos.domain.entity.Order;
import io.github.godsantos.domain.enums.StatusOrder;
import io.github.godsantos.rest.dto.OrderStatusUpdateDTO;
import io.github.godsantos.rest.dto.OrderItemInformationDTO;
import io.github.godsantos.rest.dto.OrderInformationDTO;
import io.github.godsantos.rest.dto.OrderDTO;
import io.github.godsantos.service.OrderService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save( @RequestBody @Valid OrderDTO dto ){
        Order order = service.toSave(dto); // I created as toSave to show that is customized so, different of the "save"
        return order.getId();
    }

    @GetMapping("{id}")
    public OrderInformationDTO getById(@PathVariable Integer id ){
        return service
                .getCompleteOrder(id)
                .map( p -> converter(p) )
                .orElseThrow(() ->
                        new ResponseStatusException(NOT_FOUND, "Order not found.")); // As the message just happen once I won't use a variable for that
    }

    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable Integer id ,
                             @RequestBody OrderStatusUpdateDTO dto){
        String newStatus = dto.getNewStatus();
        service.updateStatus(id, StatusOrder.valueOf(newStatus));
    }

    private OrderInformationDTO converter(Order order){
        return OrderInformationDTO
                .builder()
                .code(order.getId())
                .orderDate(order.getDataOrder().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .clientName(order.getClient().getName())
                .total(order.getTotal())
                .status(order.getStatus().name())
                .items(converter(order.getItems()))
                .build(); //Using @Builder I can use the entity order with no instance of Order
    }

    private List<OrderItemInformationDTO> converter(List<OrderItems> items){
        if(CollectionUtils.isEmpty(items)){
            return Collections.emptyList();
        }
        return items.stream().map(
                item -> OrderItemInformationDTO
                            .builder().productDescription(item.getProduct().getDescription())
                            .unitPrice(item.getProduct().getPrice())
                            .quantity(item.getQuantity())
                            .build()
        ).collect(Collectors.toList());
    }
}
