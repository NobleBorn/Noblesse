package com.example.noblesse;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class FileDecryption {

    private SecretKey decryptKey;
    private final File encryptedFile = new File("src/main/resources/com/example/noblesse/apiKey");
    private final File decryptedFile = new File("src/main/resources/com/example/noblesse/decrypted");

    public FileDecryption() {
        try {
            decryptKey = readSecretKeyFromFile("src/main/resources/com/example/noblesse/key");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private SecretKey readSecretKeyFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] encodedKey = Files.readAllBytes(path);
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, "AES");
    }


    public void decryptFile() throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, decryptKey);

        try (FileInputStream inputStream = new FileInputStream(encryptedFile);
             FileOutputStream outputStream = new FileOutputStream(decryptedFile);
             CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}
