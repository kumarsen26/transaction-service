package com.eberry.transactionservice.utils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public interface TestUtils {
    static String readFileToString(String file) {
        try {
            return new String(Files.readAllBytes(Paths.get("src/test/resources/" + file)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
