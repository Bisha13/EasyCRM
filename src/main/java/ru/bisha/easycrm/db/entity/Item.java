package ru.bisha.easycrm.db.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "item")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "category_id")
    private int category;

    @Column(name = "priority")
    private Integer priority;

    public Item(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
