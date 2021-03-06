package com.team4.backend.util;

import com.team4.backend.meta.ExcludeFromGeneratedCoverage;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Data
@Log
@Component
public class PBKDF2Encoder implements PasswordEncoder {

    @Value("${security.password.encoder.secret}")
    private String secret;

    @Value("${security.password.encoder.iteration}")
    private Integer iteration;

    @Value("${security.password.encoder.keyLength}")
    private Integer keyLength;

    @Override
    @ExcludeFromGeneratedCoverage
    @SneakyThrows({InvalidKeySpecException.class, NoSuchAlgorithmException.class})
    public String encode(CharSequence cs) {
        if (cs == null)
            return "";

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
