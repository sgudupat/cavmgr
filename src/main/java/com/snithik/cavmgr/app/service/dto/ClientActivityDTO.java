package com.snithik.cavmgr.app.service.dto;

import java.io.Serializable;

public class ClientActivityDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long clientActivityId;

	private String activityName;

	private Integer priority;
	private Boolean applicability;

	public Long getClientActivityId() {
		return clientActivityId;
	}

	public void setClientActivityId(Long clientActivityId) {
		this.clientActivityId = clientActivityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Boolean getApplicability() {
		return applicability;
	}

	public void setApplicability(Boolean applicability) {
		this.applicability = applicability;
	}

	@Override
	public String toString() {
		return "ClientActivityDTO [ClientActivityId=" + clientActivityId + ", activityName=" + activityName
				+ ", priority=" + priority + ", applicability=" + applicability + "]";
	}

}
