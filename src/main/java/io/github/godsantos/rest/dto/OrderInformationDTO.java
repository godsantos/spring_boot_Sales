package io.github.godsantos.rest.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data               // I'm using Lombok so, I don't need generate gets and sets
@AllArgsConstructor // I'm using Lombok so, I don't need create constructor with args
@NoArgsConstructor  // I'm using Lombok so, I don't need create constructor with no args
@Builder            // I'm using builder so I don't need to create a instance of the object
public class OrderInformationDTO {
    private Integer code;
    private String clientName;
    private BigDecimal total;
    private String orderDate;
    private String status;
    private List<OrderItemInformationDTO> items;
}
