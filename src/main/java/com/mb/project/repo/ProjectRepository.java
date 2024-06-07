package com.mb.project.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mb.project.entity.Project;

/**
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

		/**
		 * Retrieves an project entity by its id
		 * 
		 * @author Mindbowser | rohit.kumar@mindbowser.com
		 * @param projectId unique identifier of project entity. must not be null
		 * @return {@link Optional}
		 */
	Optional<Project> findById(Integer projectId);

}
