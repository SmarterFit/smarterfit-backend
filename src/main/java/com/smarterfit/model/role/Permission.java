package com.smarterfit.model.role;

import com.smarterfit.enums.PermissionType;
import com.smarterfit.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "permission")
@Table(name = "SF_PERMISSION")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name = "permission_type", unique = true, nullable = false)
    private PermissionType name;
}
