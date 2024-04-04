package org.ocr.poseidon.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.ocr.poseidon.interfaces.CrudEntity;

@Data
@Entity
@Table(name = "users")
public class User implements CrudEntity<User> {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "is_sso", nullable = false)
    private Boolean sso = false;

    @Override
    public User update(User user) {
        username = user.getUsername();
        fullname = user.getFullname();
        role = String.valueOf(user.getRole());
        password = user.getPassword();
        return this;
    }

}
