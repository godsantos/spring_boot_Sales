package io.github.godsantos.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data               // I'm using Lombok so, I don't need generate gets and sets
@AllArgsConstructor // I'm using Lombok so, I don't need create constructor with args
@NoArgsConstructor  // I'm using Lombok so, I don't need create constructor with no args
@Entity
@Table( name = "client" )
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 100)
    @NotEmpty(message = "{field.name.mandatory}")
    private String name;

    @JsonIgnore
    @OneToMany( mappedBy = "client" , fetch = FetchType.LAZY )
    private Set<Order> orders;

   public Client(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}
