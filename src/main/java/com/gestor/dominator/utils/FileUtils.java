package com.gestor.dominator.utils;

import java.util.UUID;

public class FileUtils {

    public static String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf('.') == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf('.'));
    }

    public static String getFormatFromContentType(String contentType) {
        if (contentType == null) {
            return "JPEG"; // default
        }
        if (contentType.equals("image/jpeg")) {
            return "JPEG";
        } else if (contentType.equals("image/png")) {
            return "PNG";
        } else if (contentType.equals("image/gif")) {
            return "GIF";
        } else if (contentType.equals("image/webp")) {
            return "WEBP";
        } else {
            return "JPEG"; // fallback
        }
    }

    public static boolean isValidImageType(String contentType) {
        return contentType != null && (contentType.equals("image/jpeg") ||
                contentType.equals("image/png") ||
                contentType.equals("image/gif") ||
                contentType.equals("image/webp"));
    }

    public static String generateUniqueFilename(String originalFilename, String milliSeconds) {
        return "image-" + milliSeconds + "-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public static String generateObjectName(String fileName) {
        return getMiddlePart(fileName) + "/" + fileName;
    }

    private static String getMiddlePart(String fileName) {
        if (fileName == null || !fileName.contains("-")) {
            return null; // o lanza excepción si prefieres
        }

        String[] parts = fileName.split("-");
        if (parts.length >= 3) {
            return parts[1]; // el que está enmedio
        }

        return null; // no había suficientes guiones
    }
}
