package org.ocr.poseidon.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.ocr.poseidon.domain.User;
import org.ocr.poseidon.enums.UserRole;

@Data
public class UserUpdateDTO {

    private Integer id;

    @NotBlank(message = "The username is mandatory")
    @Size(min = 5, max = 15, message = "Lastname name must be between 5 and 15 characters")
    private String username;

    @NotBlank
    @Pattern(message = "Le mot de passe au moins une lettre majuscule, au moins 8 caractères, au moins un chiffre et un symbole ex: }a5p5i5Q-N",
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&(){}:;',?/*~$^+=<>]).{8,20}$"
    )
    private String password;

    @NotBlank(message = "The fullname is mandatory")
    @Size(min = 5, max = 15, message = "Lastname name must be between 5 and 15 characters")
    private String fullname;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserUpdateDTO() {

    }

    public UserUpdateDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.fullname = user.getFullname();
        this.role = UserRole.valueOf(user.getRole());
    }

    public User convertToUser() {
        User user = new User();
        user.setId(this.id);
        user.setUsername(this.getUsername());
        user.setFullname(this.fullname);
        user.setPassword(this.password);
        user.setRole(String.valueOf(this.role));
        return user;
    }

}
