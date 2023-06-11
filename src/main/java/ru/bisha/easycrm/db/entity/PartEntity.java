package ru.bisha.easycrm.db.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "parts")
@EqualsAndHashCode
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_id")
    private Integer partId;

    @Column(name = "name")
    private String name;

    @Column(name = "purchase_price")
    private Double purchasePrice = 0.0;

    @Column(name = "price")
    private Double price = 0.0;

    @Column(name = "qty")
    private Integer qty = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @OneToOne
    @JoinColumn(name = "stock_id")
    private StockEntity stock;

    @Column(name = "is_stock")
    private boolean isStock;

    public boolean getIsStock() {
        return isStock;
    }

    public void setIsStock(boolean stock) {
        isStock = stock;
    }

    public PartEntity(StockEntity stock) {
        this.stock = stock;
    }
}