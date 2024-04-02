package org.ocr.poseidon.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncrypt {
    private final PasswordEncoder passwordEncoder;

    public PasswordEncrypt(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String createPassword(String s) {
        return passwordEncoder.encode(s);
    }

}

