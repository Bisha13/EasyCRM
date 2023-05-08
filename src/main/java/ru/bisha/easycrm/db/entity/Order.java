package ru.bisha.easycrm.db.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@EqualsAndHashCode
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "full_price")
    private Double fullPrice;

    @Column(name = "time_close")
    private Date timeClose;

    @CreationTimestamp
    @Column(name = "timestamp",
            nullable = false, updatable = false, insertable = false)
    private Timestamp timestamp;

    @OneToOne(fetch = FetchType.LAZY, cascade =
            {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "small_description")
    private String smallDescription;

    @Column(name = "full_description")
    private String fullDescription;

    @OneToOne
    @JoinColumn(name = "execute_status")
    private Status executeStatus;

    @Column(name = "parts_price")
    private Double partsPrice;

    @Column(name = "work_price")
    private Double workPrice;


    @OneToOne(fetch = FetchType.LAZY, cascade =
            {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "bike_id")
    private Device device;

    @ToString.Exclude
    @OneToMany(mappedBy = "order",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ServiceEntity> listOfServices;

    @ToString.Exclude
    @OneToMany(mappedBy = "order",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Part> listOfParts;

    public Order() {
        this.device = new Device();
        this.listOfServices = new ArrayList<>();
        listOfServices.add(new ServiceEntity());
        this.listOfParts = new ArrayList<>();
        listOfParts.add(new Part());
    }

    public Order(Item item, Stock stock, Status status) {
        this.device = new Device();
        this.listOfServices = new ArrayList<>();
        listOfServices.add(new ServiceEntity(item));
        this.executeStatus = status;
        this.listOfParts = new ArrayList<>();
        listOfParts.add(new Part(stock));
    }

    @PostLoad
    public void postLoad() {
        try {
            if (getDevice() != null && getDevice().getDeviceId() == 0) {
                setDevice(null);
            }
        } catch (EntityNotFoundException e) {
            setDevice(null);
        }
    }
}
