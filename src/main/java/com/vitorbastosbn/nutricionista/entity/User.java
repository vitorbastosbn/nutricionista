package com.vitorbastosbn.nutricionista.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_users_address_id", columnList = "address_id"),
                @Index(name = "idx_users_contact_id", columnList = "contact_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_users_email", columnNames = "email")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Past
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Email
    @NotBlank
    @Size(max = 255)
    @Column(name = "email", nullable = false, length = 255, unique = true)
    private String email;

    @NotBlank
    @Size(max = 150)
    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    @NotBlank
    @Size(max = 100)
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "address_id",
            foreignKey = @ForeignKey(name = "fk_users_addresses")
    )
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "contact_id",
            foreignKey = @ForeignKey(name = "fk_users_contacts")
    )
    private Contact contact;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "fk_user_roles_user")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "fk_user_roles_role")
            ),
            uniqueConstraints = {
                    @UniqueConstraint(
                            name = "uk_user_roles_user_role",
                            columnNames = {"user_id", "role_id"}
                    )
            },
            indexes = {
                    @Index(name = "idx_user_roles_user_id", columnList = "user_id"),
                    @Index(name = "idx_user_roles_role_id", columnList = "role_id")
            }
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public @NonNull String getUsername() {
        return this.email;
    }

}
