package com.jwtAuthentication.jwtAuthentication.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private Timestamp loginTime;
    private Timestamp logoutTime;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "customer_role",
    joinColumns = @JoinColumn(name =  "customer_id"),
    inverseJoinColumns = @JoinColumn(name = "role_roleId"))
    Set<Role> roles = new HashSet<>();
}
