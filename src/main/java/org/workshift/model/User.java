package org.workshift.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "USERS")
@Data
@RequiredArgsConstructor
public class User implements Serializable {

    @Id
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "NAME")
    private String userName;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "EMAIL")
    private String email;
    @OneToMany(mappedBy="user")
    private Set<Shop> shops;

    public User(UserRequest request){
        this.userId = request.getUserId();
        this.userName = request.getUserName();
        this.address = request.getAddress();
        this.phoneNumber = request.getPhoneNumber();
        this.email = request.getEmail();
    }

}
