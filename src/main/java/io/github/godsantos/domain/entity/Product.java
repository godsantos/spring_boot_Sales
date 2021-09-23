package io.github.godsantos.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data               // I'm using Lombok so, I don't need generate gets and sets
@AllArgsConstructor // I'm using Lombok so, I don't need create constructor with args
@NoArgsConstructor  // I'm using Lombok so, I don't need create constructor with no args
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")  // I just use @Column to organize because it's not necessary
    private Integer id;

    @Column(name = "description") // I just use @Column to organize because it's not necessary
    @NotEmpty(message = "{field.description.mandatory}")
    private String description;

    @Column(name = "unit_price") // I just change the name to show that I know it's possible
    @NotNull(message = "{field.price.mandatory}")
    private BigDecimal price;

}
