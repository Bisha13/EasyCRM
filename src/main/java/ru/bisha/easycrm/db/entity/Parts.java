package ru.bisha.easycrm.db.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "partstable")
@Getter
@Setter
@EqualsAndHashCode
public class Parts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_id")
    private int partId;

    @Basic
    @Column(name = "vendor")
    private String vendor;

    @Basic
    @Column(name = "model")
    private String model;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "price")
    private int price;

    @Basic
    @Column(name = "type")
    private int type;

    @Basic
    @Column(name = "order_id")
    private int orderId;

    @CreationTimestamp
    @Column(name="timestamp",
            nullable = false, updatable = false, insertable = false)
    private Timestamp timestamp;

    @Basic
    @Column(name = "client_id")
    private int clientId;

    @Basic
    @Column(name = "price_2_client")
    private int price2Client;

    @Basic
    @Column(name = "profit")
    private Integer profit;
}
