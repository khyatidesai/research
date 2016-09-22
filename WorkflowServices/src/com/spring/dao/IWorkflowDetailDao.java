package com.spring.dao;

import java.util.List;

import com.spring.model.WorkflowActionDetails;
import com.spring.model.WorkflowStepDetails;

public interface IWorkflowDetailDao {

	String SELECT = "select * from workflow_step_details  where workflow_id = :id and sequence =1 ";

	String SELECT_WORKFLOW_STEP = "select * from workflow_step_details  where workflow_step_id = :id";

	String SELECT_ACTION = "select * From  Workflow_Step_Actions twa ,Action_Master ta Where twa.workflow_step_id= :wrflStpId and  Ta.Id =twa.action_id";

	String SELECT_FORM = "select form_view from form where id in (select form_id from task_form where TASK_ID= :tskId) ";

	String SELECT_SUCEEDINGSTEP = "select IFNULL(SUCCEEDING_STEP_ID,0) from WORKFLOW_STEP_ACTIONS where WORKFLOW_STEP_ID=:wrflStpId and ACTION_ID=:actionId";

	String SELECT_WORKSTEPID = "select * from workflow_step_details where WORKFLOW_STEP_ID=:id";

	String SELECT_TASKPERMISSIONS = "select 1 from TASK_PERMISSION where task_id=:taskId and USER_ROLE_ID=:roleId";

	public WorkflowStepDetails getWorkflowStepDetails(int pWorkflowId,
			boolean isStepDetail);

	public List<WorkflowActionDetails> getActionDetailsforWorkflowStep(
			int pWorkflowStpId);

	public List<String> getFormDetails(int pTaskId);

	public Integer getSuceedingStepDetails(int pWorkflowStepId, int pActionId);

	public boolean checkRoleTaskPermissions(int pTaskId, int pRoleId);

}
