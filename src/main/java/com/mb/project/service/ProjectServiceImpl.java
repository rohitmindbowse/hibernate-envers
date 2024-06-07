package com.mb.project.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Service;

import com.mb.audit.RevInfo;
import com.mb.audit.constant.AuditConstant;
import com.mb.common.util.Mapper;
import com.mb.project.entity.Project;
import com.mb.project.model.ProjectModel;
import com.mb.projectDao.ProjectDao;
import com.mb.user.dto.ProjectDto;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

/**
 * @author Mindbowser | rohit.kumar@mindbowser.com
 *
 */
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

	private final Mapper mapper;

	private final ProjectDao projectDao;

	private final EntityManager entityManager;

	/**
	 * Create a project
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param projectDto
	 * @return {@link ProjectModel}
	 */
	@Override
	public ProjectModel createProject(ProjectDto projectDto) {
		Project project = mapper.convert(projectDto, Project.class);
		return mapper.convert(projectDao.saveProject(project), ProjectModel.class);

	}

	/**
	 * Update a project
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param projectDto
	 * @return {@link ProjectModel}
	 */
	@Override
	public ProjectModel updateProject(ProjectDto projectDto) {
		Project project = projectDao.projectById(projectDto.getId());
		if (projectDto.getClientName() != null) {
			project.setClientName(projectDto.getClientName());
		}
		if (projectDto.getDiscription() != null) {
			project.setDiscription(projectDto.getDiscription());
		}
		if (projectDto.getName() != null) {
			project.setName(projectDto.getName());
		}
		return mapper.convert(projectDao.saveProject(project), ProjectModel.class);
	}

	/**
	 * Delete a project
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param projectId
	 * @return {@link boolean}
	 */
	@Override
	public boolean deleteProject(Integer projectId) {
		Project project = projectDao.projectById(projectId);
		projectDao.deleteProject(project);
		return true;
	}

	/**
	 * Project Revisiob by id
	 * 
	 * @author Mindbowser | rohit.kumar@mindbowser.com
	 * @param projectId
	 * @return {@link List<Map<String, Object>> }
	 */
	@Override
	public List<Map<String, Object>> projectRevisionById(Integer projectId) {
		List<Map<String, Object>> result = new ArrayList<>();
		AuditReader auditReader = AuditReaderFactory.get(entityManager);
		AuditQuery q = auditReader.createQuery().forRevisionsOfEntity(Project.class, false, true);
		q.add(AuditEntity.id().eq(projectId));
		List<Object[]> resultList = q.getResultList();
		if (!resultList.isEmpty()) {

			for (int index = resultList.size() - 1; index >= 0; index--) {
				if (index > 0) {

					Object[] previousData = resultList.get(index - 1);
					Object[] currData = resultList.get(index);
					Project current = (Project) currData[0];
					Project previous = (Project) previousData[0];
					RevInfo revInfo = (RevInfo) currData[1];
					RevisionType revisionType = (RevisionType) currData[2];
					int revTypeId = revisionType.getRepresentation();
					String revType = "";
					if (revTypeId == 0) {
						revType = AuditConstant.CREATED;
					} else if (revTypeId == 1) {
						revType = AuditConstant.UPDATED;
					} else if (revTypeId == 2) {
						revType = AuditConstant.DELETED;
					}

					if (Objects.nonNull(previous.getName()) && Objects.nonNull(current.getName()))
						if (!current.getName().equals(previous.getName())) {
							result.add(responseOfHistory(previous, current, 1, revInfo, revType));
						}
					if (Objects.nonNull(previous.getDiscription()) && Objects.nonNull(current.getDiscription()))
						if (!current.getDiscription().equals(previous.getDiscription())) {
							result.add(responseOfHistory(previous, current, 2, revInfo, revType));
						}
					if (Objects.nonNull(previous.getClientName()) && Objects.nonNull(current.getClientName()))
						if (!current.getClientName().equals(previous.getClientName())) {
							result.add(responseOfHistory(previous, current, 3, revInfo, revType));
						}

				}

			}
		}
		return result;
	}

	private Map<String, Object> responseOfHistory(Project previous, Project current, int i, RevInfo revInfo,
			String revType) {
		Map<String, Object> res = new HashMap<>();
		if (i == 1) {
			res.put(AuditConstant.FIELD,AuditConstant.PROJECT_NAME);
			res.put(AuditConstant.CURRENT, current.getName());
			res.put(AuditConstant.PREVIOUS, previous.getName());
			res.put(AuditConstant.OPERATION, revType);
		} else if (i == 2) {
			res.put(AuditConstant.FIELD, AuditConstant.DISCRIPTION);
			res.put(AuditConstant.CURRENT, current.getDiscription());
			res.put(AuditConstant.PREVIOUS, previous.getDiscription());
			res.put(AuditConstant.OPERATION, revType);
		} else if (i == 3) {
			res.put(AuditConstant.FIELD, AuditConstant.CLIENT_NAME);
			res.put(AuditConstant.CURRENT, current.getClientName());
			res.put(AuditConstant.PREVIOUS, previous.getClientName());
			res.put(AuditConstant.OPERATION, revType);
		}
		res.put(AuditConstant.UPDATED_BY, revInfo.getAuditorName());
		res.put(AuditConstant.TIMESTAMP, DateUtils.truncate(new Date(revInfo.getTimestamp()), Calendar.DATE));
		res.put(AuditConstant.UPDATED_ON, new Date(revInfo.getTimestamp()));
		res.put(AuditConstant.REV_ID, revInfo.getId());
		return res;

	}

}
