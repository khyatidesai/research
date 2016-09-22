package com.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.spring.model.WorkflowActionDetails;
import com.spring.model.WorkflowRequest;
import com.spring.model.WorkflowResponse;
import com.spring.model.WorkflowStepDetails;
import com.spring.service.WorkFlowService;

@RestController
public class WorkflowController {

	@Autowired
	WorkFlowService workflowService;

	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String getDetailsforWorkfow(
			@RequestBody WorkflowRequest pWorkflowRequest) {
		System.out.println("inside save request of User ");
		String responseJasonString = null;
		try {
			Gson gson = new Gson();

			int jobTrackingId = 0;
			WorkflowStepDetails workFlowStepObj = workflowService
					.getWorkflowStepDetails(pWorkflowRequest.getWorkflowId(),
							true);
			int jobId = workflowService.createJob(
					pWorkflowRequest.getWorkflowId(),
					pWorkflowRequest.getUsername(),
					pWorkflowRequest.getReferenceCd(), workFlowStepObj);
			if (jobId > 0) {
				jobTrackingId = workflowService.createJobTracking(
						workFlowStepObj, pWorkflowRequest.getUsername(),
						pWorkflowRequest.getReferenceCd(), jobId);
				System.out.println("jobTrackingId" + jobTrackingId);
				if (jobTrackingId > 0) {
					WorkflowResponse response = workflowService
							.getResponseForWorkflowId(
									workFlowStepObj.getWorkflowStepId(),
									jobTrackingId,
									pWorkflowRequest.getReferenceCd());
					if (response != null) {
						responseJasonString = gson.toJson(response);
					} else {
						responseJasonString = "No workflow defined for this request";
					}
				} else {
					responseJasonString = "No workflow defined for this request";
				}
			} else {
				responseJasonString = "Job already existing for this reference record";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println(responseJasonString);
		return responseJasonString;

	}

	@RequestMapping(value = "/action", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String getNextStepDetails(
			@RequestBody WorkflowRequest pWorkflowRequest) {
		System.out.println("inside next request from User ");
		Gson gson = new Gson();
		int jobId = 0;
		WorkflowResponse response = null;
		WorkflowActionDetails details = null;
		String responseJasonString = null;
		String message = null;
		try {
			int nextWorkflowstep = workflowService
					.updateAndGetNextStep(pWorkflowRequest);

			jobId = workflowService.getExistingJobId(
					pWorkflowRequest.getReferenceCd(),
					pWorkflowRequest.getWorkflowId());
			if (nextWorkflowstep != 0) {
				WorkflowStepDetails workFlowStepObj = workflowService
						.getWorkflowStepDetails(nextWorkflowstep, false);
				int jobTrackingId = workflowService.createJobTracking(
						workFlowStepObj, pWorkflowRequest.getUsername(),
						pWorkflowRequest.getReferenceCd(), jobId);
				if (workflowService.checkTaskPermissionForRole(
						workFlowStepObj.getTaskId(),
						pWorkflowRequest.getRoleId())) {

					if (jobTrackingId > 0) {
						response = workflowService.getResponseForWorkflowId(
								workFlowStepObj.getWorkflowStepId(),
								jobTrackingId,
								pWorkflowRequest.getReferenceCd());
						if (response != null) {

						} else {

							message = "No workflow defined for this request";
						}
					} else {
						message = "No workflow defined for this request";
					}
				} else {

					details = new WorkflowActionDetails(0,
							workFlowStepObj.getPendingActionTask());

				}
			} else {
				workflowService.updateJobOnCompletion(jobId);
				message = "Workflow Completed";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (response == null) {
				response = new WorkflowResponse();
				response.getActions().add(details);
				response.setMessage(message);
			}
		}

		responseJasonString = gson.toJson(response);
		return responseJasonString;

	}

	@RequestMapping(value = "/viewAll", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<String> getViewAllDetailsforUser(
			@RequestBody WorkflowRequest pWorkflowRequest) {
		System.out.println("inside view request of User ");
		Gson gson = new Gson();
		String response = null;
		List<String> responseList = new ArrayList<String>();
		try {
			if (pWorkflowRequest.getReferenceCdList() != null
					&& !pWorkflowRequest.getReferenceCdList().isEmpty()) {
				for (String refernceCd : pWorkflowRequest
						.getReferenceCdList()) {
					response = workflowService.getStringForView(
							pWorkflowRequest.getWorkflowId(),
							pWorkflowRequest.getRoleId(), refernceCd, gson);

				}
			} else {
				response = workflowService.getStringForView(
						pWorkflowRequest.getWorkflowId(),
						pWorkflowRequest.getRoleId(),
						pWorkflowRequest.getReferenceCd(), gson);
			}
			responseList.add(response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return responseList;

	}

	@RequestMapping(value = "/view", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String getWorkflowStatusforUser(
			@RequestBody WorkflowRequest pWorkflowRequest) {
		System.out.println("inside view request of User ");
		Gson gson = new Gson();
		WorkflowResponse response = null;

		try {

			response = workflowService.getWorkflowStatus(
					pWorkflowRequest.getWorkflowId(),
					pWorkflowRequest.getRoleId(),
					pWorkflowRequest.getReferenceCd());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return gson.toJson(response);

	}

}
