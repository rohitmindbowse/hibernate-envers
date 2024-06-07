package com.mb.project.service;

import java.util.List;
import java.util.Map;

import com.mb.project.model.ProjectModel;
import com.mb.user.dto.ProjectDto;

import jakarta.validation.Valid;

/**
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 *
 */
public interface ProjectService {
	
	/**
	 * Create a project
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param projectDto
	 * @return {@link ProjectModel}
	 */
	ProjectModel createProject(ProjectDto projectDto);
	
	/**
	 * Update a project
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param projectDto
	 * @return {@link ProjectModel}
	 */
	ProjectModel updateProject(ProjectDto projectDto);

	/**
	 * Delete a project
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param projectId
	 * @return {@link boolean}
	 */
	boolean deleteProject( Integer projectId);

	/**
	 * Project Revisiob by id
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param projectId
	 * @return {@link List<Map<String, Object>> }
	 */
	List<Map<String, Object>> projectRevisionById(Integer projectId);

}
