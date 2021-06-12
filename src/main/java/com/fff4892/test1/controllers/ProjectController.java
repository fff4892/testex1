package com.fff4892.test1.controllers;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fff4892.test1.dao.ProjectDAO;
import com.fff4892.test1.models.Device;
import com.fff4892.test1.models.Project;

@RestController
public class ProjectController {
	
	@Autowired
	private ProjectDAO projectDao;

	@GetMapping("/projects/{id}")
	public Map<String, Device> getDeviceList(@PathVariable(name = "id") int id) {
		return projectDao.getDevices(id).stream().collect(Collectors.toMap(Device::getSerialNumber, 
				Function.identity()));
	}
	
	@GetMapping("/projects")
	public List<Project> getProjects(){
		return projectDao.getProjects();
	}
}
