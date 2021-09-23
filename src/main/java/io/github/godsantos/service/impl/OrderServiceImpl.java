package io.github.godsantos.service.impl;

import io.github.godsantos.domain.entity.Client;
import io.github.godsantos.domain.entity.Order;
import io.github.godsantos.domain.entity.Product;
import io.github.godsantos.domain.enums.StatusOrder;
import io.github.godsantos.domain.repository.Clients;
import io.github.godsantos.domain.repository.OrderItems;
import io.github.godsantos.domain.repository.Orders;
import io.github.godsantos.domain.repository.Products;
import io.github.godsantos.exception.OrderNotFoundException;
import io.github.godsantos.exception.BusinessRuleException;
import io.github.godsantos.rest.dto.OrderItemDTO;
import io.github.godsantos.rest.dto.OrderDTO;
import io.github.godsantos.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final Orders repository;
    private final Clients clientsRepository;
    private final Products productsRepository;
    private final OrderItems orderItemsRepository;

    @Override
    @Transactional
    public Order toSave(OrderDTO dto ) {
        Integer idCliente = dto.getClient();
        Client client = clientsRepository
                .findById(idCliente)
                .orElseThrow(() -> new BusinessRuleException("Código de client inválido."));

        Order order = new Order();
        order.setTotal(dto.getTotal());
        order.setDataOrder(LocalDate.now());
        order.setClient(client);
        order.setStatus(StatusOrder.FINISHED);

        List<io.github.godsantos.domain.entity.OrderItems> itemsPedido = converterItems(order, dto.getItems());
        repository.save(order);
        orderItemsRepository.saveAll(itemsPedido);
        order.setItems(itemsPedido);
        return order;
    }

    @Override
    public Optional<Order> getCompleteOrder(Integer id) {
        return repository.findByIdFetchItems(id);
    }

    @Override
    @Transactional
    public void updateStatus(Integer id, StatusOrder statusOrder) {
        repository
                .findById(id)
                .map( pedido -> {
                    pedido.setStatus(statusOrder);
                    return repository.save(pedido);
                }).orElseThrow(() -> new OrderNotFoundException() );
    }

    private List<io.github.godsantos.domain.entity.OrderItems> converterItems(Order order, List<OrderItemDTO> items){
        if(items.isEmpty()){
            throw new BusinessRuleException("Não é possível realizar um order sem items.");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduct();
                    Product product = productsRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new BusinessRuleException(
                                            "Código de product inválido: "+ idProduto
                                    ));

                    io.github.godsantos.domain.entity.OrderItems orderItems = new io.github.godsantos.domain.entity.OrderItems();
                    orderItems.setQuantity(dto.getQuantity());
                    orderItems.setOrder(order);
                    orderItems.setProduct(product);
                    return orderItems;
                }).collect(Collectors.toList());

    }
}
