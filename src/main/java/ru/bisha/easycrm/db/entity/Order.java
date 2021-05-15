package ru.bisha.easycrm.db.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "orderstable")
@EqualsAndHashCode
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "full_price")
    private Integer fullPrice;

    @Column(name = "time_close")
    private Date timeClose;

    @CreationTimestamp
    @Column(name="timestamp",
            nullable = false, updatable = false, insertable = false)
    private Timestamp timestamp;

    @Column(name = "client_id")
    private int clientId;

    @Column(name = "small_description")
    private Object smallDescription;

    @Column(name = "full_description")
    private String fullDescription;

    @Column(name = "execute_status")
    private Integer executeStatus;

    @Column(name = "executor_id")
    private Integer executorId;

    @Column(name = "parts_price")
    private Integer partsPrice;

    @Column(name = "work_price")
    private Integer workPrice;

    @Column(name = "parts")
    private String parts;

    @Column(name = "works")
    private String works;

    @Column(name = "payment_status")
    private Integer paymentStatus;

    @Column(name = "bike_id")
    private Integer bikeId;

    @Column(name = "order_description")
    private String orderDescription;






























}
