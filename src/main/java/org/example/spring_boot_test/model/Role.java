package org.example.spring_boot_test.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String role;
    @Transient
    @ManyToMany
    private List<User> users;

    @Override
    public String getAuthority() {
        return getRole();
    }
}
