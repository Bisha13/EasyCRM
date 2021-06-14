package ru.bisha.easycrm.db.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "clientstable")
@Getter
@Setter
@EqualsAndHashCode
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "phone_number2")
    private String phoneNumber2;

    @Column(name = "phone_number3")
    private String phoneNumber3;

    @Column(name = "address")
    private String address;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "vk_id")
    private String vkId;

    @Column(name = "fb_id")
    private String fbId;

    @Column(name = "instagram_id")
    private String instagramId;

    @Column(name = "mailing_approval")
    private Integer mailingApproval;

    @CreationTimestamp
    @Column(name="timestamp",
            nullable = false, updatable = false, insertable = false)
    private Timestamp timestamp;

}
