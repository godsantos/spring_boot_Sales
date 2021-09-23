package io.github.godsantos.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data               // I'm using Lombok so, I don't need generate gets and sets
@AllArgsConstructor // I'm using Lombok so, I don't need create constructor with args
@NoArgsConstructor  // I'm using Lombok so, I don't need create constructor with no args
@Builder            // I'm using builder so I don't need to create a instance of the object
public class OrderItemInformationDTO {
    private String productDescription;
    private BigDecimal unitPrice;
    private Integer quantity;
}
