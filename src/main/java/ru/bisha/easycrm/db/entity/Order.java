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

    @OneToOne
    @JoinColumn(name = "execute_status")
    private Status executeStatus;

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
    private String orderDescription;

    @ToString.Exclude
    @OneToMany(mappedBy = "order",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    private List<Service> listOfServices;

    public Order() {
        this.device = new Device();
        this.listOfServices = new ArrayList<>();
        listOfServices.add(new Service());
    }

    public Order(Item item) {
        this.device = new Device();
        this.listOfServices = new ArrayList<>();
        listOfServices.add(new Service(item));
    }

    @PostLoad
    public void postLoad(){
        try {
            if(getDevice() != null && getDevice().getDeviceId() == 0){
                setDevice(null);
            }
        }
        catch (EntityNotFoundException e){
            setDevice(null);
        }
    }




























}
