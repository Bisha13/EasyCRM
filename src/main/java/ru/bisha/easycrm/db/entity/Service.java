package ru.bisha.easycrm.db.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "services")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id")
    private User executor;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private int qty;

    @Column(name = "price")
    private Double price;

    @OneToOne(fetch = FetchType.LAZY, cascade =
            {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "item_id")
    private Item item;

    @CreationTimestamp
    @Column(name="time_stamp",
            nullable = false, updatable = false, insertable = false)
    private Timestamp timeStamp;

    @Column(name = "executor_money")
    private Double executorMoney;

    @Column(name = "profit")
    private Double profit;

    @Column(name = "is_custom")
    private boolean isCustom;

    public boolean getIsCustom() {
        return isCustom;
    }

    public void setIsCustom(boolean custom) {
        isCustom = custom;
    }

    public Service() {
        this.description = "";
        this.qty = 1;
    }

    public Service(Item item) {
        this.description = "";
        this.qty = 1;
        this.item = item;
    }
}
