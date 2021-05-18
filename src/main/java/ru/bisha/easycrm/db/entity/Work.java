package ru.bisha.easycrm.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "work")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_id")
    private int id;

    @Column(name = "order_id")
    private int orderId;

    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "executor_id")
    private User executor;

    @Column(name = "work_status")
    private Status status;

    enum Status {
        NEW, DONE, PAYED
    }

}


