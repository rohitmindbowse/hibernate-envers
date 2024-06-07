package com.mb.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mb.common.constant.ApiPath;
import com.mb.common.constant.ResponseMessage;
import com.mb.common.model.SuccessResponse;
import com.mb.common.util.CustomResponseBuilder;
import com.mb.project.model.ProjectModel;
import com.mb.project.service.ProjectService;
import com.mb.user.dto.ProjectDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Auth controller
 * 
 * @author Mindbowser | rohit.kumar@mindbowser.com
 *
 */
@RestController
@RequestMapping(ApiPath.PROJECT_BASE_URL)
@RequiredArgsConstructor
public class ProjectController {
	
	private final Environment environment;

	private final CustomResponseBuilder responseBuilder;
	
	private final ProjectService projectService;

	/**
	 * Create project
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param projectDto
	 * @return {@link ResponseEntity}
	 */
	@PostMapping()
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<SuccessResponse<ProjectModel>> createProject(@RequestBody @Valid ProjectDto projectDto) {

		ProjectModel project = projectService.createProject(projectDto);

		return responseBuilder.buildSuccessResponse(environment.getProperty(ResponseMessage.SUCCESS), project,
				HttpStatus.OK);
	}
	
	/**
	 * Update project
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param projectDto
	 * @return {@link ResponseEntity}
	 */
	@PutMapping()
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<SuccessResponse<ProjectModel>> updateProject(@RequestBody @Valid ProjectDto projectDto) {

		ProjectModel project = projectService.updateProject(projectDto);

		return responseBuilder.buildSuccessResponse(environment.getProperty(ResponseMessage.SUCCESS), project,
				HttpStatus.OK);
	}
	
	/**
	 * Delete project
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param projectId
	 * @return {@link ResponseEntity}
	 */
	@DeleteMapping()
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<SuccessResponse<Boolean>> deleteProject(@RequestParam @Valid Integer projectId) {

		boolean project = projectService.deleteProject(projectId);

		return responseBuilder.buildSuccessResponse(environment.getProperty(ResponseMessage.SUCCESS), project,
				HttpStatus.OK);
	}
	
	/**
	 *Project revision by projectId
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param projectId
	 * @return {@link ResponseEntity}
	 */
	@GetMapping()
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<SuccessResponse<List<Map<String, Object>>>> projectRevisionById(@RequestParam @Valid Integer projectId) {

		List<Map<String, Object>> project = projectService.projectRevisionById(projectId);

		return responseBuilder.buildSuccessResponse(environment.getProperty(ResponseMessage.SUCCESS), project,
				HttpStatus.OK);
	}

}
