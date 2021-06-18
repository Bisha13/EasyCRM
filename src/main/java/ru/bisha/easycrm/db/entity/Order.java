package ru.bisha.easycrm.db.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
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
    private Integer fullPrice; // когда редактируется заказ

    @Column(name = "time_close")
    private Date timeClose; // подумать

    @CreationTimestamp
    @Column(name="timestamp",
            nullable = false, updatable = false, insertable = false)
    private Timestamp timestamp;

    @OneToOne(fetch = FetchType.LAZY, cascade =
            {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "small_description")
    private String smallDescription; //сделать

    @Column(name = "full_description")
    private String fullDescription;

    @Column(name = "execute_status")
    @Enumerated(EnumType.ORDINAL)
    private Status executeStatus; // изменять когда редактируется заказ или
    // много заказов

    @Column(name = "executor_id")
    private Integer executorId;

    @Column(name = "parts_price")
    private Integer partsPrice; // Не знаю, нужно вообще или нет

    @Column(name = "work_price")
    private Integer workPrice; // same

    @Column(name = "parts")
    private String parts;

    @Column(name = "works")
    private String works;

    @Column(name = "payment_status")
    private Integer paymentStatus;

    @OneToOne(fetch = FetchType.LAZY, cascade =
            {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "bike_id")
    private Device device;

    @Column(name = "order_description")
    private String orderDescription; // сделать

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "order_id")
//    private List<Service> services;

    @ToString.Exclude
    @OneToMany(mappedBy = "order",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    private List<Work> listOfWorks;

    public Order() {
        this.executeStatus = Status.NEW;
        this.device = new Device();
        this.listOfWorks = new ArrayList<>();
        listOfWorks.add(new Work());
    }

    enum Status {

        NEW("Новый"),
        WAITING("Ожидает"),
        WAITING_GLEB("Ждёт Глеба"),
        WAITING_PARTS("Ждёт запчастей"),
        READY("Готов"),
        CLOSED("Закрыт");

        private String status;

        Status(String status) {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }
    }




























}
