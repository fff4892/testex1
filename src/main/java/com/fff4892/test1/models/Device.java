package com.fff4892.test1.models;

import java.util.Map;

public class Device {
	private final int id;
	private final String serialNumber;
	private final int projectId;
	private final boolean hasErrors;
	private final Map<String, Integer> summaryInfo;
	
	public Device(int id, String serialNumber, int projectId, boolean hasErrors, 
			Map<String, Integer> summaryInfo) {
		this.id = id;
		this.serialNumber = serialNumber;
		this.projectId = projectId;
		this.hasErrors = hasErrors;
		this.summaryInfo = summaryInfo;
	}

	public int getId() {
		return id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public int getProjectId() {
		return projectId;
	}

	public boolean isHasErrors() {
		return hasErrors;
	}

	public Map<String, Integer> getSummaryInfo() {
		return summaryInfo;
	}
}
