package ru.bisha.easycrm.db.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "services")
@EqualsAndHashCode
@Getter @Setter
@Builder
@AllArgsConstructor
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Integer serviceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id")
    private UserEntity executor;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private int qty;

    @Column(name = "price")
    private Double price;

    @OneToOne(fetch = FetchType.LAZY, cascade =
            {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "item_id")
    private ItemEntity item;

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

    @Column(name = "status")
    private ServiceStatus status;

    public boolean getIsCustom() {
        return isCustom;
    }

    public void setIsCustom(boolean custom) {
        isCustom = custom;
    }

    public ServiceEntity() {
        this.description = "";
        this.qty = 1;
    }

    public ServiceEntity(ItemEntity item) {
        this.description = "";
        this.qty = 1;
        this.item = item;
    }

    enum ServiceStatus {
        NEW,
        DONE,
        PAID
    }
}
