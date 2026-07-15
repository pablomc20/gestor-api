package com.gestor.dominator.repository.images;

public final class ImageQueryBD {

    private ImageQueryBD() {
    }
    
    public static final String CREATE_IMAGE = """
            INSERT INTO images (filename, ext, size, mimeType)
            VALUES (?, ?, ?, ?)
            RETURNING image_id;
            """;
}
