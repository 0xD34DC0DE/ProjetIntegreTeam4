package com.team4.backend.util;

import lombok.extern.java.Log;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@Log
@ExtendWith(SpringExtension.class)
@DataMongoTest
@ContextConfiguration(classes ={PBKDF2Encoder.class})
@EnableAutoConfiguration
public class PBKDF2EncoderTest {

    @Autowired
    PBKDF2Encoder pbkdf2Encoder;

    @Test
    void encode() {
        //ARRANGE
        CharSequence charSequence = "myPassword";

        //ACT
        String myPassEncoded = pbkdf2Encoder.encode(charSequence);

        //ASSERT
        assertNotNull(myPassEncoded);
    }

    @Test
    void matches() throws NoSuchAlgorithmException, InvalidKeySpecException {
        //ARRANGE
        CharSequence passwordEntered = "myPassword";
        CharSequence wrongPassword = "sdadasd";
        String encodedPassword = Base64.getEncoder().encodeToString(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                .generateSecret(new PBEKeySpec(passwordEntered.toString().toCharArray(), pbkdf2Encoder.getSecret().getBytes(), pbkdf2Encoder.getIteration(), pbkdf2Encoder.getKeyLength()))
                .getEncoded());

        //ACT
        boolean passwordMatches = pbkdf2Encoder.matches(passwordEntered,encodedPassword);
        boolean passwordNotMatching = pbkdf2Encoder.matches(wrongPassword,encodedPassword);

        //ASSERT
        assertTrue(passwordMatches);
        assertFalse(passwordNotMatching);
    }
}
