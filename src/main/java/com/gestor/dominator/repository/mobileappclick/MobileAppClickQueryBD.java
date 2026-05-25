package com.gestor.dominator.repository.mobileappclick;

public final class MobileAppClickQueryBD {

	private MobileAppClickQueryBD() {
	}

	public static final String CREATE_CLICK = """
			    INSERT INTO mobile_app_clicks
			    (event_type, session_id) VALUES (?, ?)
			    RETURNING id, event_type, event_date, session_id
			""";

	public static final String GET_CLICKS_BY_EVENT_TYPE = """
				SELECT id, event_type, event_date, session_id
				FROM mobile_app_clicks WHERE event_type = ?
			""";
}
