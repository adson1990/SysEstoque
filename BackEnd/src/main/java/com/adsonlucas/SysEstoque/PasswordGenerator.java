package com.adsonlucas.SysEstoque;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("ADSON: " + encoder.encode("123456"));
        System.out.println("LUIZ: " + encoder.encode("654321"));
        System.out.println("VALERIA: " + encoder.encode("321456"));
    }
}
