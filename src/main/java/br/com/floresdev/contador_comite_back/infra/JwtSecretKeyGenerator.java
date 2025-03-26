package br.com.floresdev.contador_comite_back.infra;

import java.util.Base64;

import org.springframework.security.crypto.keygen.KeyGenerators;

public class JwtSecretKeyGenerator {
    public static void main(String[] args) {
        byte[] key = KeyGenerators.secureRandom(32).generateKey();
        String encodedKey = Base64.getEncoder().encodeToString(key);
        System.out.println("Secret key para HMAC-SHA256: " + encodedKey);
        System.out.println("Tamanho da chave: " + key.length);
    }
}
