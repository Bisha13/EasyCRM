package ru.bisha.easycrm.db.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "parts")
@EqualsAndHashCode
@Getter @Setter
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_id")
    private Integer partId;

    @Column(name = "name")
    private String name;

    @Column(name = "purchase_price")
    private Double purchasePrice = 0.0;

    @Column(name = "price")
    private Double price = 0.0;

    @Column(name = "qty")
    private Integer qty = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
