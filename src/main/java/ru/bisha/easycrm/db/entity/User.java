package ru.bisha.easycrm.db.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
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

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "procent")
    private Integer percent;

    @OneToMany(mappedBy = "executor")
    private List<Service> serviceList;

    @PreRemove
    private void preRemove() {
        for (Service s : serviceList) {
            s.setExecutor(null);
        }
    }
}
