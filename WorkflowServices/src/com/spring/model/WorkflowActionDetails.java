package com.spring.model;

public class WorkflowActionDetails {

	int actionId;
	String buttonText;

	public WorkflowActionDetails(int pActId, String buttontxt) {

		actionId = pActId;
		buttonText = buttontxt;

	}

	public int getActid() {
		return actionId;
	}

	public String getButtonText() {
		return buttonText;
	}

}
