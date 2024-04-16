package org.ocr.poseidon.services;

import jakarta.validation.ValidationException;
import org.ocr.poseidon.domain.User;
import org.ocr.poseidon.dto.UserCreationDTO;
import org.ocr.poseidon.exceptions.ItemNotFoundException;
import org.ocr.poseidon.repositories.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends AbstractCrudService<User> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    protected UserServiceImpl(JpaRepository<User, Integer> repository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(repository);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * This method save a new user
     *
     * @param userCreationDTO DTO object to new user
     */

    public void saveUser(UserCreationDTO userCreationDTO) {

        if (userRepository.existsByUsername(userCreationDTO.getUsername())) {
            throw new ValidationException("There is already an account registered with the same username" + userCreationDTO.getUsername());
        }

        User user = new User();
        user.setUsername(userCreationDTO.getUsername());
        user.setFullname(userCreationDTO.getFullname());
        user.setRole(String.valueOf(userCreationDTO.getRole()));
        user.setPassword(passwordEncoder.encode(userCreationDTO.getPassword()));
        userRepository.save(user);
    }

    /**
     * This method checks errors before updated a new user
     *
     * @param user user updated from form
     * @return user updated from form checked
     */
    public User controlUser(User user) {

        Integer id = user.getId();

        if (!repository.existsById(id)) {
            throw new ItemNotFoundException("User with ID " + id + " is not found");
        }

        User userUpdated = userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("User with ID " + id + " is not found"));
        String newUsername = user.getUsername();
        if (!userUpdated.getUsername().equalsIgnoreCase(newUsername) && userRepository.existsByUsername(user.getUsername())) {
            throw new ValidationException("Username " + newUsername + " is already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return user;
    }
}
