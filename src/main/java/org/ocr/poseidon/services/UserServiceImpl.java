package org.ocr.poseidon.services;

import jakarta.validation.ValidationException;
import org.ocr.poseidon.domain.User;
import org.ocr.poseidon.dto.UserCreationDTO;
import org.ocr.poseidon.exceptions.ItemNotFoundException;
import org.ocr.poseidon.repositories.UserRepository;
import org.ocr.poseidon.util.PasswordEncrypt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends AbstractCrudService<User> {

    private final UserRepository userRepository;
    private final PasswordEncrypt passwordEncrypt;

    protected UserServiceImpl(JpaRepository<User, Integer> repository, UserRepository userRepository, PasswordEncrypt passwordEncrypt) {
        super(repository);

        this.userRepository = userRepository;
        this.passwordEncrypt = passwordEncrypt;
    }


    public void saveUser(UserCreationDTO userCreationDTO) {

        if (userRepository.existsByUsername(userCreationDTO.getUsername())) {
            throw new ValidationException("There is already an account registered with the same unsername" + userCreationDTO.getUsername());
        }

        User user = new User();
        user.setUsername(userCreationDTO.getUsername());
        user.setFullname(userCreationDTO.getFullname());
        user.setRole(String.valueOf(userCreationDTO.getRole()));
        user.setPassword(passwordEncrypt.createPassword(userCreationDTO.getPassword()));
        userRepository.save(user);
    }


    public User controlUser(User user) {

        Integer id = user.getId();

        if (!repository.existsById(id)) {
            throw new ItemNotFoundException(STR."Item id n°\{id}is not found, controle entity: \{user.toString()}");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ValidationException("There is already an account registered with the same unsername" + user.getUsername());
        }

        user.setPassword(passwordEncrypt.createPassword(user.getPassword()));
        return user;
    }
}
