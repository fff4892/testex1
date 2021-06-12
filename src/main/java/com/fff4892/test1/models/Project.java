package com.fff4892.test1.models;

import java.util.List;
import java.util.Map;

public class Project {
	private final int id;
	private final String projectName;
	private final Map<String, Integer> stats;
	private final List<String> devices;
	
	public Project(int id, String projectName, Map<String, Integer> stats, List<String> devices) {
		this.id = id;
		this.projectName = projectName;
		this.stats = stats;
		this.devices = devices;
	}

	public int getId() {
		return id;
	}

	public String getProjectName() {
		return projectName;
	}

	public Map<String, Integer> getStats() {
		return stats;
	}

	public List<String> getDevices() {
		return devices;
	}
	
}
