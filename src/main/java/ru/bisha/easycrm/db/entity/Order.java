package ru.bisha.easycrm.db.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

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

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "small_description")
    private String smallDescription;

    @Column(name = "full_description")
    private String fullDescription;

    @Column(name = "execute_status")
    @Enumerated(EnumType.ORDINAL)
    private Status executeStatus;

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

    @OneToOne
    @JoinColumn(name = "bike_id")
    private Device device;

    @Column(name = "order_description")
    private String orderDescription;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<Service> services;

enum Status {
    NEW, WAITING, WAITING_GLEB, WAITING_PARTS, READY, CLOSED,
}




























}
