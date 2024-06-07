
package com.mb.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
/**
 * DTO class used to get request body from requests. Getter methods only. Do not
 * add setter methods. Add validations for required fields using annotation. Add
 * only those fields which are relevant for request. Do not add all entity
 * related fields whenever you are saving entity using dto class.
 * 
 * @author Mindbowser | rohit.kumar@mindbowser.com
 *
 */
@Data
public class ProjectDto {

	private Integer id;
	
	@NotBlank
	private String name;

	private String discription;

	private boolean active;

	private String clientName;

}
