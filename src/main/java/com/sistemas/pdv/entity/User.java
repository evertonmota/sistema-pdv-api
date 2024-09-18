package com.sistemas.pdv.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String username;
    @Column(length = 60, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String name;
    private boolean isEnabled;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Sale> sales;
}
