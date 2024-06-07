package com.mb.projectDao;

import com.mb.project.entity.Project;

/**
 * @author Mindbowser | rohit.kumar@mindbowser.com
 *
 */
public interface ProjectDao {
	
	/**
	 * Project by ProjectId
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param projectId
	 * @return {@link Project}
	 */
	Project projectById(Integer projectId);
	
	/**
	 * save project
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param project
	 */
	Project saveProject(Project project);
	
	/**
	 * delete project
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param project
	 */
	void deleteProject(Project project);

}
