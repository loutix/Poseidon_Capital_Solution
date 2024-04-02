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
public class UserCreationDTO {

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

    public User convertToUser() {
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setFullname(this.fullname);
        user.setRole(String.valueOf(this.role));
        return user;
    }

}
