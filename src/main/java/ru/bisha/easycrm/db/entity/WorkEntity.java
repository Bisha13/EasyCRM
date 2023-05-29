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
public class WorkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @OneToOne(fetch = FetchType.LAZY, cascade =
            {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    @Column(name = "executor_id")
    private int executorId;

    @Column(name = "work_status")
    private Status status;

    enum Status {
        NEW, DONE, PAYED
    }

    public WorkEntity() {
        this.executorId = 20;
        this.status = Status.NEW;
    }
}


