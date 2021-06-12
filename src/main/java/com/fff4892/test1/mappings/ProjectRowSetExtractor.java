package com.fff4892.test1.mappings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.fff4892.test1.models.Project;

public class ProjectRowSetExtractor implements ResultSetExtractor<List<Project>>{

	@Override
	public List<Project> extractData(ResultSet rs) throws SQLException {
		List<Project> projects = new ArrayList<>();
		while (rs.next()) {
			boolean groupingDevice = rs.getBoolean("grouping_device");
			if (groupingDevice) {
				int id = rs.getInt("id");
				String projectName = rs.getString("name");
				Map<String, Integer> stats = new HashMap<>();
				stats.put("deviceCount", rs.getInt("device_count"));
				stats.put("deviceWithErrors", rs.getInt("device_with_errors"));
				stats.put("stableDevices", rs.getInt("stable_devices"));
				Project pr = new Project(id, projectName, stats, new ArrayList<>());
				projects.add(pr);
			} else {
				String device = rs.getString("device");
				if (device != null) {
					projects.get(projects.size() - 1).getDevices().add(device);
				}
			}
		}
		return projects;
	}
}
