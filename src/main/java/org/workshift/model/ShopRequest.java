package org.workshift.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@Accessors(chain = true)
public class ShopRequest implements Serializable {
    private String shopId;
    private String shopName;
    private String address;
    private String userId;
}
