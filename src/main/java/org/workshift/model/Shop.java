package org.workshift.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "SHOP")
@Data
@RequiredArgsConstructor
public class Shop implements Serializable {
    @Id
    @Column(name = "SHOP_ID")
    private String shopId;
    @Column(name = "SHOP_NAME")
    private String shopName;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "USER_ID")
    private String userId;
    @ManyToOne
    @JoinColumn(name="USER_ID", nullable=false, insertable = false, updatable = false)
    private User user;

    public Shop(ShopRequest request){
        this.shopId = request.getShopId();
        this.shopName = request.getShopName();
        this.address = request.getAddress();
        this.userId = request.getUserId();
    }

}
