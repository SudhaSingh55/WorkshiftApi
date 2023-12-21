package org.workshift.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@Accessors(chain = true)
public class UserRequest implements Serializable {
    private String userId;
    private String userName;
    private String address;
    private String phoneNumber;
    private String email;
}
