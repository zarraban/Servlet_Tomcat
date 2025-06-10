package org.example.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "names")
    private String name;

    @Column(name = "costs")
    private Integer cost;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
