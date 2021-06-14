package ru.bisha.easycrm.db.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "commenttable")
@EqualsAndHashCode
@Getter @Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    @CreationTimestamp
    @Column(name="timestamp",
            nullable = false, updatable = false, insertable = false)
    private Timestamp timestamp;

    @Column(name = "autor_id")
    private int authorId;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "client_id")
    private Integer clientId;

    @Column(name = "bike_id")
    private Integer bikeId;

    @Column(name = "text")
    private String text;

    @Column(name = "coomment_type")
    private int commentType;

}
