package com.example.storeapi_customer.Models;
import jakarta.persistence.*;
import lombok.*;

@Entity
@ToString
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String ssn;

    // Borttaget vid isärkoppling av order och customer
//    @OneToMany(mappedBy = "customer")
//    @ToString.Exclude
//    @JsonIgnore
//    private Set<Order> orders = new HashSet<>();

}
