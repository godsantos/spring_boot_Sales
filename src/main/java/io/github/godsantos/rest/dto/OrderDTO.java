package io.github.godsantos.rest.dto;

import io.github.godsantos.validation.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data               // I'm using Lombok so, I don't need generate gets and sets
@AllArgsConstructor // I'm using Lombok so, I don't need create constructor with args
@NoArgsConstructor  // I'm using Lombok so, I don't need create constructor with no args
public class OrderDTO {

    @NotNull(message = "{field.code-client.mandatory}")
    private Integer client;

    @NotNull(message = "{field.total-order.mandatory}")
    private BigDecimal total;

    @NotEmptyList(message = "{field.items-order.mandatory}")
    private List<OrderItemDTO> items;

}
