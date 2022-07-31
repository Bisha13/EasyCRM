package ru.bisha.easycrm.db.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "clients")
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

    @Column(name = "address")
    private String address;

    @Column(name = "discount")
    private Integer discount;

    @CreationTimestamp
    @Column(name = "timestamp",
            nullable = false, updatable = false, insertable = false)
    private Timestamp timestamp;

    public void setPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.trim().replaceAll("[^0-9]", "");
        if (phoneNumber.length() == 11 && (
                phoneNumber.startsWith("8")
                        || phoneNumber.startsWith("7"))) {
            this.phoneNumber = phoneNumber.substring(1);
        } else this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        if (phoneNumber == null) {
            return null;
        }
        phoneNumber = this.phoneNumber.replaceAll("[^0-9]", "").trim();
        if (phoneNumber.length() < 10) {
            return phoneNumber;
        }

        var phoneSb = new StringBuilder();
        if (phoneNumber.length() == 11) {
            phoneSb.append(
                    phoneNumber.charAt(0) == '8' || phoneNumber.charAt(0) == '7' ?
                            "+7" : phoneNumber.charAt(0));
            phoneSb.append("(");
            phoneSb.append(phoneNumber, 1, 4);
            phoneSb.append(")");
            phoneSb.append(phoneNumber, 4, 7);
            phoneSb.append("-");
            phoneSb.append(phoneNumber, 7, 9);
            phoneSb.append("-");
            phoneSb.append(phoneNumber, 9, 11);
        }

        if (phoneNumber.length() == 10) {
            phoneSb.append("+7(");
            phoneSb.append(phoneNumber, 0, 3);
            phoneSb.append(")");
            phoneSb.append(phoneNumber, 3, 6);
            phoneSb.append("-");
            phoneSb.append(phoneNumber, 6, 8);
            phoneSb.append("-");
            phoneSb.append(phoneNumber, 8, 10);
        }
        return phoneSb.toString();
    }
}
