'use strict';

angular.module('app').factory('WorkflowService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = 'http://localhost:8080/WorkflowAPI';

    var factory = {
        fetchActions: fetchActions,
        save: save,
        updateAction:updateAction,
        view: view
    };

    return factory;

    function fetchActions() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching Users');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function save(workflow) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI+'/save', workflow)
            .then(
            function (response) {
            	 console.log(response);
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while creating worflow');
                console.error(errResponse);
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }


    function updateAction(workflow) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI+'/action', workflow)
            .then(
            function (response) {
            	 console.log(response);
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while updating workflow');
                console.log(errResponse);
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function view(workflow) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI+'/view', workflow)
            .then(
            function (response) {
            	 console.log(response);
                deferred.resolve(response.data);
            },
            function(errResponse){
            	 console.log(errResponse);
                console.error('Error while fetching workflow');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

}]);
