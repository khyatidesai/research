package com.spring.model;

public class WorkflowTaskFormDetails {

	int id;
	int formNm;
	int formView;

	public WorkflowTaskFormDetails(int id, int formNm, int formView) {
		this.id = id;
		this.formNm = formNm;
		this.formView = formView;
	}

	public int getId() {
		return id;
	}

	public int getFormNm() {
		return formNm;
	}

	public int getFormView() {
		return formView;
	}

}
