package com.fff4892.test1.mappings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.fff4892.test1.models.Device;

public class DeviceRowMapper implements RowMapper<Device> {
	
	public Device mapRow(ResultSet rs, int rowNum) throws SQLException {
		int id = rs.getInt("id");
		String serialNumber = rs.getString("serial_number");
		int projectId = rs.getInt("project_id");
		boolean hasErrors = rs.getBoolean("has_errors");
		Map<String, Integer> summaryInfo = new HashMap<>();
		summaryInfo.put("eventCount", rs.getInt("event_count"));
		summaryInfo.put("warningCount", rs.getInt("warning_count"));
		summaryInfo.put("errorCount", rs.getInt("error_count"));
		return new Device(id, serialNumber, projectId, hasErrors, summaryInfo);
	}
}
