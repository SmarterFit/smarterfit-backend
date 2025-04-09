package com.smarterfit.model.UserRole;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smarterfit.enums.RoleType;
import com.smarterfit.model.Profile;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "SF_USER")
public class User{

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "no_email", nullable = false, unique = true)
    private String email;

    @Column(name = "no_username", nullable = false, unique = true)
    private String username;


    @Column(name = "no_password", nullable = false)
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Profile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> roles = new HashSet<>();

    @Column(name = "dt_created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(name = "dt_updated_at", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


    @PrePersist
    public void onPrePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void addRole(UserRole role) {
        role.setUser(this);
        roles.add(role);
    }

}
