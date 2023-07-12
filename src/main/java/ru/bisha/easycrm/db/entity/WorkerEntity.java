package ru.bisha.easycrm.db.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "workers")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class WorkerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "procent")
    private Integer percent;

    @OneToMany(mappedBy = "executor")
    private List<ServiceEntity> serviceList;

    @PreRemove
    private void preRemove() {
        for (ServiceEntity s : serviceList) {
            s.setExecutor(null);
        }
    }
}
