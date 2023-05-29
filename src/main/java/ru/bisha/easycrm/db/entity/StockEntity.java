package ru.bisha.easycrm.db.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "stock")
@Data
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_id")
    private Integer id;

    @Column(name = "article_number")
    private String article;

    @Column(name = "name")
    private String name;

    @Column(name = "purchase_price")
    private Double purchase;

    @Column(name = "price")
    private Double price;

    @Column(name = "extra")
    private Integer extra;
}
