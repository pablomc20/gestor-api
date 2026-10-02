package com.gestor.dominator.repository.chape;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.gestor.dominator.exceptions.custom.DataValidationException;
import com.gestor.dominator.model.postgre.chape.ChapeRq;
import com.gestor.dominator.model.postgre.chape.ChapeRs;


@Repository
public class ChapeRepositoryImpl implements ChapeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ChapeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final @NonNull RowMapper<ChapeRs> chapeMapper = (rs, rowNum) -> new ChapeRs(
            rs.getString("chape_id"),
            rs.getString("name"),
            rs.getString("slug"));

    @Override
    public List<ChapeRs> getAllChapes() {
        return jdbcTemplate.query(
                ChapeQueryBD.READ_ALL_CHAPES,
                chapeMapper);
    }

    @Override
    public ChapeRs createChape(ChapeRq chapeRq) {
        return jdbcTemplate.queryForObject(
                ChapeQueryBD.CREATE_CHAPE,
                chapeMapper,
                mapCreateChapeParams(chapeRq));
    }

    @Override
    public ChapeRs updateChape(ChapeRq chapeRq, String chapeId) {
        return jdbcTemplate.queryForObject(
                ChapeQueryBD.UPDATE_CHAPE,
                chapeMapper,
                mapUpdateChapeParams(chapeRq, chapeId));
    }

    @Override
    public Integer deleteChape(String chapeId) {
        return jdbcTemplate.update(
                ChapeQueryBD.DELETE_CHAPE,
                mapIdToUUID(chapeId));
    }

    private Object[] mapCreateChapeParams(ChapeRq chapeRq) {
        return new Object[] { chapeRq.name(), chapeRq.slug() };
    }

    private Object[] mapUpdateChapeParams(ChapeRq chapeRq, String chapeId) {
        return new Object[] { chapeRq.name(), chapeRq.slug(), mapIdToUUID(chapeId) };
    }

    private Object mapIdToUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw DataValidationException.invalidValue("chape_id", id);
        }
    }
}
