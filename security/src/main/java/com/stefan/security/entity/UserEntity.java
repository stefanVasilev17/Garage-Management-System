package com.stefan.security.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name="_user_entity")
@Data
//@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "source")
    @Enumerated(EnumType.STRING)
    private RegistrationSource source;
}
