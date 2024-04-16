package org.ocr.poseidon.util;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {


    /**
     * This method checks if the current user has the admin role
     *
     * @return boolean
     */

    public boolean isAdmin() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getAuthorities() != null) {
            return auth.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        }
        return false;
    }

    /**
     * This method checks if the current user is logged in and is registered
     *
     * @return boolean
     */
    public boolean isUserAnonymous() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            return principal instanceof String && principal.equals("anonymousUser");
        }
        return false;
    }

}
