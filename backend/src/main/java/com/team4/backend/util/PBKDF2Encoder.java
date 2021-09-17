package com.team4.backend.util;

import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Data
@Component
public class PBKDF2Encoder implements PasswordEncoder {

    @Value("${security.password.encoder.secret}")
    private String secret;

    @Value("${security.password.encoder.iteration}")
    private Integer iteration;

    @Value("${security.password.encoder.keyLength}")
    private Integer keyLength;

    @Override
    @SneakyThrows
    public String encode(CharSequence cs) {
        byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                .generateSecret(new PBEKeySpec(cs.toString().toCharArray(), secret.getBytes(), iteration, keyLength))
                .getEncoded();
        return Base64.getEncoder().encodeToString(result);
    }

    @Override
    public boolean matches(CharSequence cs, String string) {
        return encode(cs).equals(string);
    }
}
