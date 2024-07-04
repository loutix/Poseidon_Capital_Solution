package org.ocr.poseidon.repositories;

import org.ocr.poseidon.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to retrieve.
     * @return An Optional containing the user with the specified username, if found.
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if a user with the given username exists in the database.
     *
     * @param username The username to check.
     * @return true if a user with the username exists, false otherwise.
     */
    Boolean existsByUsername(String username);


}
