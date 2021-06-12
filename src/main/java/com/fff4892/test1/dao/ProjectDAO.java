package com.fff4892.test1.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fff4892.test1.mappings.DeviceRowMapper;
import com.fff4892.test1.mappings.ProjectRowSetExtractor;
import com.fff4892.test1.models.Device;
import com.fff4892.test1.models.Project;

@Repository
public class ProjectDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Device> getDevices(int projectId){
		return jdbcTemplate.query("SELECT "
				+ "devices.id AS id,"
				+ "devices.serial_number AS serial_number,"
				+ "devices.project_id AS project_id,"
				+ "SUM(CASE WHEN event_types.name = 'error' THEN 1 ELSE 0 END) > 0 AS has_errors,"
				+ "SUM(CASE WHEN event_types.name = 'event' THEN 1 ELSE 0 END) AS event_count,"
				+ "SUM(CASE WHEN event_types.name = 'warning' THEN 1 ELSE 0 END) AS warning_count,"
				+ "SUM(CASE WHEN event_types.name = 'error' THEN 1 ELSE 0 END) AS error_count"
				+ " FROM devices"
				+ " LEFT JOIN events ON devices.id = events.device_id"
				+ " LEFT JOIN event_types ON events.type = event_types.id"
				+ " WHERE devices.project_id = ?"
				+ " GROUP BY devices.id, devices.serial_number, devices.project_id", new DeviceRowMapper(), 
				projectId);
	}
	
	public List<Project> getProjects(){
		return jdbcTemplate.query("WITH cte_device_with_errors AS ("
				+ "SELECT DISTINCT "
				+ "devices.id AS id"
				+ " FROM devices"
				+ " LEFT JOIN events ON devices.id = events.device_id"
				+ " LEFT JOIN event_types ON events.type = event_types.id"
				+ " WHERE event_types.name IN ('warning','error')"
				+ " AND events.date > (now() - interval '1 day')),"
				+ "cte_unstable_devices AS ("
				+ "SELECT DISTINCT "
				+ "devices.id AS id"
				+ " FROM devices"
				+ " LEFT JOIN events ON devices.id = events.device_id"
				+ " LEFT JOIN event_types ON events.type = event_types.id"
				+ " WHERE event_types.name IS NOT NULL AND event_types.name <> 'event') "
				+ "SELECT "
				+ "GROUPING(devices.serial_number) AS grouping_device,"
				+ "projects.id AS id,"
				+ "projects.name AS name,"
				+ "devices.serial_number AS device,"
				+ "SUM(CASE WHEN devices.id IS NOT NULL THEN 1 ELSE 0 END) AS device_count,"
				+ "SUM(CASE WHEN devices.id IN (SELECT cte_device_with_errors.id FROM cte_device_with_errors)"
				+ " THEN 1 ELSE 0 END) AS device_with_errors,"
				+ "SUM(CASE WHEN devices.id NOT IN (SELECT cte_unstable_devices.id FROM cte_unstable_devices)"
				+ " THEN 1 ELSE 0 END) AS stable_devices"
				+ " FROM projects"
				+ " LEFT JOIN devices ON projects.id = devices.project_id"
				+ " GROUP BY GROUPING SETS((projects.id, projects.name, devices.serial_number),"
				+ " (projects.id, projects.name))"
				+ " ORDER BY projects.id, grouping_device DESC", new ProjectRowSetExtractor());
	}
}
