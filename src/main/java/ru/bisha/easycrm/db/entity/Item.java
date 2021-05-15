package ru.bisha.easycrm.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "item")
@Getter
@Setter
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

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,  CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "priority")
    private Integer priority;

    public Item(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
