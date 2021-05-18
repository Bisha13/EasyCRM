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

    @Column(name = "order_id")
    private int orderId;


    @OneToOne
    @JoinColumn(name = "executor_id")
    private User executor;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @CreationTimestamp
    @Column(name="time_stamp",
            nullable = false, updatable = false, insertable = false)
    private Timestamp timeStamp;

    @Column(name = "executor_money")
    private Integer executorMoney;

    @Column(name = "profit")
    private Integer profit;

}
