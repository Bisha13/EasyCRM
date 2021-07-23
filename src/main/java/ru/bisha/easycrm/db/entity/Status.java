package ru.bisha.easycrm.db.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "status")
@Data
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statusid")
    private Long id;

    @Column(name = "name")
    private String name;
}
