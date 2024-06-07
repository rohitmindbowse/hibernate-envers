package com.mb.audit;

import org.hibernate.envers.RevisionListener;

public class ConferenceRevisionListener implements RevisionListener {

	@Override
	public void newRevision(Object revisionEntity) {
		RevInfo entity=(RevInfo) revisionEntity;
		entity.setAuditorId(AuditorDetails.auditorId);
		entity.setAuditorName(AuditorDetails.auditorName);
	}
}
