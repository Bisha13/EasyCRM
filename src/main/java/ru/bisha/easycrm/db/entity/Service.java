package ru.bisha.easycrm.db.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "service_table")
@EqualsAndHashCode
@Getter @Setter
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private int serviceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "executor_id")
    private int executorId;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private int qty;

    @Column(name = "price")
    private int price;

    @OneToOne(fetch = FetchType.LAZY, cascade =
            {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "item_id")
    private Item item;

    @CreationTimestamp
    @Column(name="time_stamp",
            nullable = false, updatable = false, insertable = false)
    private Timestamp timeStamp;

    @Column(name = "executor_money")
    private Integer executorMoney;

    @Column(name = "profit")
    private Integer profit;

    public Service() {
        this.executorId = 20;
        this.description = "";
        this.qty = 1;
    }

    public Service(Item item) {
        this.executorId = 20;
        this.description = "";
        this.qty = 1;
        this.item = item;
    }
}
