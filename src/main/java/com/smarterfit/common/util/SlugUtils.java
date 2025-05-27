package com.smarterfit.common.util;

import java.text.Normalizer;

public class SlugUtils {

    private SlugUtils() {
        // Private constructor to prevent instantiation
    }

    public static String slugify(String input) {
        if (input == null) return null;

        // Remove acentos
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        return normalized.toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }
}
