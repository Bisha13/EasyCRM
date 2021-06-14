package ru.bisha.easycrm.db.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "userstable")
@Getter
@Setter
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "login")
    private String login;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "procent")
    private String percent;

    @Column(name = "access_level")
    private Integer accessLevel;

    @Column(name = "status")
    private Integer status;

    @Column(name = "password")
    private String password;
}
