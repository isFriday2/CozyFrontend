package com.maddev.coozy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserTest {

    private User user;
    @BeforeEach
    public void setUp() {
        user = new User("testName", "test@email.com", "testAccount", "123 Test Lane", "TestPass");
    }

    @Test
    void setUsername() {
        user.setUsername("usernameSetter");
        assertEquals("usernameSetter",user.getUsername());
    }

    @Test
    void setEmail() {
        user.setEmail("emailSetter");
        assertEquals("emailSetter",user.getEmail());
    }

    @Test
    void setNickname() {
        user.setNickname("nicknameSetter");
        assertEquals("nicknameSetter",user.getNickname());
    }

    @Test
    void setHome() {
        user.setHome("123 Address Setter");
        assertEquals("123 Address Setter",user.getHome());
    }

    @Test
    void PasswordHash() {
        String expectedHash = "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4";
        String inputPassword = "1234";
        String actualPassword = user.hashPassword(inputPassword);
        assertEquals(expectedHash, actualPassword);
    }

}
