package com.spring.model;

import org.springframework.stereotype.Component;

@Component
public class WorkflowStepDetails {

	private int workflowStepId;
	private int workflowId;
	private int taskId;
	private int formId;
	private String preceedingStep;
	private int sequenceNo;
	private int isMandatory;
	private int isLast;
	private String pendingActionTask;
	// private String recordtypeId;

	public WorkflowStepDetails() {

	}

	public WorkflowStepDetails(int workflowStepId, int workflowId, int taskId,
			String preceedingStep, int sequnenceNo, int isMandatory, int isLast,
			int formId) {
		this.workflowStepId = workflowStepId;
		this.workflowId = workflowId;
		this.taskId = taskId;
		this.preceedingStep = preceedingStep;
		this.sequenceNo = sequnenceNo;
		// this.sStep = suceedingStep;
		this.isMandatory = isMandatory;
		this.isLast = isLast;
		this.formId = formId;
	}

	public WorkflowStepDetails(int workflowStepId, int workflowId, int taskId,
			String preceedingStep, int sequnenceNo, int isMandatory, int isLast,
			String pendingActionTask) {
		this.workflowStepId = workflowStepId;
		this.workflowId = workflowId;
		this.taskId = taskId;
		this.preceedingStep = preceedingStep;
		this.sequenceNo = sequnenceNo;
		// this.sStep = suceedingStep;
		this.isMandatory = isMandatory;
		this.isLast = isLast;
		this.pendingActionTask = pendingActionTask;
		// this.recordtypeId = recordtypeId;
	}

	public String getPendingActionTask() {
		return pendingActionTask;
	}

	public int getWorkflowStepId() {
		return workflowStepId;
	}

	public int getWorkflowId() {
		return workflowId;
	}

	public int getTaskId() {
		return taskId;
	}

	public String getPreceedingStep() {
		return preceedingStep;
	}

	public int getSequnenceNo() {
		return sequenceNo;
	}

	public int getIsMandatory() {
		return isMandatory;
	}

	public int getIsLast() {
		return isLast;
	}

	public int getFormId() {
		return formId;
	}

}
