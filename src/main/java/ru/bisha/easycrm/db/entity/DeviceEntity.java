package ru.bisha.easycrm.db.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "devices")
@Getter
@Setter
@EqualsAndHashCode
public class DeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private int deviceId;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "owner")
    private int ownerId;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name="timestamp",
            nullable = false, updatable = false, insertable = false)
    private Timestamp timestamp;
}
