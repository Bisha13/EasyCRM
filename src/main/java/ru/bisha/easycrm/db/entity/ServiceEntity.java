package ru.bisha.easycrm.db.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.bisha.easycrm.dto.ServiceDto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

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
    @Enumerated(EnumType.STRING)
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

    public ServiceDto mapToDto() {
        return ServiceDto.builder()
                .id(String.valueOf(this.serviceId))
                .orderId(String.valueOf(this.order.getOrderId()))
                .qty(this.qty)
                .description(this.description)
                .price(Optional.ofNullable(this.price).map(BigDecimal::valueOf).orElse(null))
                .executorId(Optional.ofNullable(this.executor).map(UserEntity::getId).map(String::valueOf).orElse(null))
                .itemId(Optional.ofNullable(this.item).map(ItemEntity::getId).map(String::valueOf).orElse(null))
                .isCustom(this.isCustom)
                .status(this.status)
                .build();
    }
}
