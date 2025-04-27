package com.smarterfit.modules.useraccess.entity;

import com.smarterfit.common.enums.RoleType;
import com.smarterfit.modules.useraccess.entity.id.UserRoleId;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "user", "roleType" })
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
