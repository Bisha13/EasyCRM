package ru.bisha.easycrm.db.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
@EqualsAndHashCode
@Table(name = "work")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_id")
    private int id;

    @Column(name = "order_id")
    private int orderId;

    @Column(name = "item_id")
    private int itemId;

    @Column(name = "executor_id")
    private int executorId;

    @Column(name = "work_status")
    private Status status;

    enum Status {
        NEW, DONE, PAYED
    }

}


