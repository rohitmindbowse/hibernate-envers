package com.mb.projectDao;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.mb.common.constant.ExceptionMessage;
import com.mb.common.exception.CustomException;
import com.mb.project.entity.Project;
import com.mb.project.repo.ProjectRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author Mindbowser | rohit.kumar@mindbowser.com
 *
 */
@Repository
@RequiredArgsConstructor
public class ProjectDaoImpl implements ProjectDao{
	
	private final ProjectRepository projectRepository;
	
	private final Environment environment;

	
	/**
	 * Project by id
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param projectId
	 * @return {@link Project}
	 */
	@Override
	public Project projectById(Integer projectId) {
		return projectRepository.findById(projectId)
				.orElseThrow(() -> new CustomException(environment.getProperty(ExceptionMessage.PROJECT_NOT_FOUND),
						HttpStatus.NOT_FOUND));

	
	}

	/**
	 * save project
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param project
	 */
	@Override
	public Project saveProject(Project project) {
		return projectRepository.save(project);
		
	}

	/**
	 * delete project
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param project
	 */
	@Override
	public void deleteProject(Project project) {
		projectRepository.delete(project);
		
	}

}
