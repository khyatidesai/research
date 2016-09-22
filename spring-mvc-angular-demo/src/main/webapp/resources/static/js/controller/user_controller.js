'use strict';


app.controller('galleryController', function($scope) {
    $scope.headingTitle = "Photo Gallery Items";
});

app.controller('contactusController', function($scope) {
    $scope.headingTitle = "Contact Info";
});

app.controller('UserController', ['$scope','$route', '$compile', 'UserService','WorkflowService', function($scope,$route,$compile, UserService, WorkflowService) {
    var self = this;
    $scope.headingTitle = "UserRegistration";
    $scope.wActive=true;
    $scope.canUpdate=true;
    
    var status='';
    var roleId =$route.current.roleId; 
    var workflowId =$route.current.workflowId; 

    self.user={id:null,username:'',address:'',email:'', status:''};
    self.workflow={actionId:null,workflowId:workflowId,jobTrackingId:null,username:"Khyati", roleId: roleId, referenceCd: null, referenceCdList: null};
    var workresp={jobTrackingId:null, actions: null, referenceCd:null, message:null}
    self.users=[];
    self.workflows=[];

    self.submit = submit;
    self.edit = edit;
    self.remove = remove;
    self.reset = reset;
    self.workflowSubmit = workflowSubmit;

    fetchAllUsers();
    
    
    function fetchAllUsers(){
        UserService.fetchAllUsers()
            .then(
            function(d) {
                self.users = d;
                angular.forEach(self.users, function(user) {                	
                    user.status =getStatus(user.id);
                  });
            },
            function(errResponse){
                console.error('Error while fetching Users');
            }
        );
    }
    
    function fetchActions(){
    	WorkflowService.fetchActions()
            .then(
            function(d) {
                self.workflow = d;
            },
            function(errResponse){
                console.error('Error while fetching Users');
            }
        );
    }
    
    function workflowSubmit(actionId, jobTrackingId, refId){
    	
    	self.workflow.actionId=actionId;
    	self.workflow.jobTrackingId=jobTrackingId;
    	self.workflow.referenceCd=refId;
    	
    	WorkflowService.updateAction(self.workflow).then(
    			function(d){
    				console.log(d);
    				$scope.wActive=false;
    				showWorkFlowButtons(d.id);
    				//alert(d.isComplete);
    			}
    		);
    	
    }

    function createUser(user){
        UserService.createUser(user)
            .then(
            function(d) {
            self.user=d;
            self.workflow.referenceCd=d.id;
            fetchAllUsers();
            WorkflowService.save(self.workflow)
            	.then(
            		function(e){
            			showWorkFlowButtons(d.id);
            		}
            );
            
            },
            function(errResponse){
                console.error('Error while creating User');
            }
        );
    }

    function updateUser(user, id){
        UserService.updateUser(user, id)
            .then(
            		function(d) {
                        self.user=d;
                        self.workflow.referenceCd=d.id;
                        fetchAllUsers();
                        WorkflowService.save(self.workflow)
                        	.then(
                        		function(e){
                        			showWorkFlowButtons(d.id);
                        		}
                        );
                        
                        },
            function(errResponse){
                console.error('Error while updating User');
            }
        );
    }

    function deleteUser(id){
        UserService.deleteUser(id)
            .then(
            fetchAllUsers,
            function(errResponse){
                console.error('Error while deleting User');
            }
        );
    }

    function submit() {
        if(self.user.id===null){
            console.log('Saving New User', self.user);
            createUser(self.user);
        }else{
            updateUser(self.user, self.user.id);
            console.log('User updated with id ', self.user.id);
        }
        reset();
    }

    function edit(id){
        console.log('id to be edited', id);     
        for(var i = 0; i < self.users.length; i++){
            if(self.users[i].id === id) {
                self.user = angular.copy(self.users[i]);
                break;
            }
        }
        showWorkFlowButtons(id);
    }

    function remove(id){
        console.log('id to be deleted', id);
        if(self.user.id === id) {//clean form if the user to be deleted is shown there.
            reset();
        }
        deleteUser(id);
    }
    
    function removeWorkflowButtons(){
    	var element = angular.element(document.getElementById('space-for-workflow-buttons'));
    	element.empty();
    }

    function getStatus(id){
    	
    	var workflow = self.workflow;
    	workflow.referenceCd=id;
    	if(id>3){
   	 	WorkflowService.view(workflow)
    	.then(
    		function(e){  			
    			console.log(e)   			
    			$scope.wActive=false;
    			status=e.actions[0].buttonText;
    		},
            function(errResponse){
    			console.error(errResponse);  
                console.error('Error while fetching workflow details');
            });
    	}
    }
    
    function showWorkFlowButtons(id){
    	if(id>3){
    	removeWorkflowButtons();
    	self.workflow.referenceCd=id;
    	 WorkflowService.view(self.workflow)
     	.then(
     		function(e){
     			console.log(e)
     			if(e.message != null){
     				 console.error(e.message);
     			}else{
     			$scope.wActive = e.isActive;
     			if(e.actions[0].actionId == 1){
     				$scope.canUpdate = true;
     			}else{
     				$scope.canUpdate = false;
     			}
     			if(e.isActive == true){
     			var actions = e.actions;
     			for(var i=0; i<actions.length;i++){
    				angular.element(document.getElementById('space-for-workflow-buttons')).append($compile("<button type=\"button\" class='btn btn-primary btn-sm' ng-show=\"wActive\" ng-click=\"ctrl.workflowSubmit("+actions[i].actionId+","+e.jobTrackingId+","+e.referenceCd+")\">"+actions[i].buttonText+"</button>")($scope))
     			}
     			}else{
     				$scope.wActive=false;
     				if(e.actions[0])
    				angular.element(document.getElementById('space-for-workflow-buttons')).append($compile("<button type=\"button\" class='btn btn-primary btn-sm' ng-disabled=\"!wActive\">"+e.actions[0].buttonText+"</button>")($scope))
    			
     			}
     			}
     		});
    	}
    }

    function reset(){
        self.user={id:null,username:'',address:'',email:'', status:''};
        $scope.wActive = false;
        removeWorkflowButtons();
        $scope.myForm.$setPristine(); //reset Form
    }

}]);
