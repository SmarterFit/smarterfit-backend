package com.smarterfit.model.UserRole;

import com.smarterfit.enums.RoleType;
import com.smarterfit.model.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"user", "roleType"})
@IdClass(UserRoleId.class)
@Entity
@Table(name = "SF_USER_ROLE")
public class UserRole {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", nullable = false)
    private RoleType roleType;


}
