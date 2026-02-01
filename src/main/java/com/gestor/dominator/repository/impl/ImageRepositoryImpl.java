package com.gestor.dominator.repository.impl;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gestor.dominator.components.ObjectManipulationUtil;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.image.ImageCreateRs;
import com.gestor.dominator.model.postgre.image.ImageRq;
import com.gestor.dominator.model.postgre.image.ImageRs;
import com.gestor.dominator.repository.ImageRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectManipulationUtil objectManipulationUtil;

    @Override
    public ImageRs findById(String id) {
        String sql = """
                    SELECT image_id, filename, ext, size, mimeType, created_at
                    FROM images
                    WHERE image_id = ?
                """;

        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new PostgreDbException("ID inválido");
        }

        try {
            ImageRs result = jdbcTemplate.queryForObject(
                    sql,
                    rowMapperImage(),
                    uuid);

            return result;
        } catch (EmptyResultDataAccessException e) {
            throw new PostgreDbException("Error al obtener imagen", e);
        }
    }

    private RowMapper<ImageRs> rowMapperImage() {
        return (rs, rowNum) -> ImageRs.builder()
                .id(rs.getString("image_id"))
                .filename(rs.getString("filename"))
                .ext(rs.getString("ext"))
                .size(rs.getLong("size"))
                .mimeType(rs.getString("mimeType"))
                .createdAt(rs.getObject("created_at", OffsetDateTime.class))
                .build();
    }

    @Override
    public ImageCreateRs save(ImageRq imageRq) {
        String sql = """
                    SELECT fn_insert_image(?, ?, ?, ?)
                """;

        String jsonResponse = jdbcTemplate.queryForObject(
                sql,
                String.class,
                imageRq.filename(),
                imageRq.ext(),
                imageRq.size(),
                imageRq.mimeType());

        ImageCreateRs imageCreateRs = objectManipulationUtil
                .objectMapperToString(jsonResponse, ImageCreateRs.class);

        if (!imageCreateRs.status().equals("ok")) {
            throw new PostgreDbException("Error al crear imagen");
        }

        return imageCreateRs;

    }

    @Override
    public void deleteById(String id) {
        String sql = """
                    DELETE FROM images
                    WHERE image_id = ?
                """;

        jdbcTemplate.update(sql, UUID.fromString(id));
    }

}
