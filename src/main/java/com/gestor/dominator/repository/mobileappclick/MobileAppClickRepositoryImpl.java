package com.gestor.dominator.repository.mobileappclick;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gestor.dominator.model.postgre.mobileappclick.MobileAppClickRq;
import com.gestor.dominator.model.postgre.mobileappclick.MobileAppClickRs;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MobileAppClickRepositoryImpl implements MobileAppClickRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<MobileAppClickRs> clickMapper = (rs, rowNum) -> MobileAppClickRs.builder()
            .id(rs.getInt("id"))
            .eventType(rs.getString("event_type"))
            .eventDate(rs.getObject("event_date", OffsetDateTime.class))
            .sessionId(rs.getString("session_id"))
            .build();

    @Override
    public MobileAppClickRs createClick(MobileAppClickRq rq) {
        return jdbcTemplate.queryForObject(
                MobileAppClickQueryBD.CREATE_CLICK,
                clickMapper,
                rq.eventType(),
                rq.sessionId());
    }

    @Override
    public List<MobileAppClickRs> getClicksByEventType(String eventType) {
        return jdbcTemplate.query(
                MobileAppClickQueryBD.GET_CLICKS_BY_EVENT_TYPE,
                clickMapper,
                eventType);
    }
}
