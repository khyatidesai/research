'use strict';


var app = angular.module('app', ['ngRoute','ngResource']);
app.config(function($routeProvider){
    $routeProvider
        .when('/gallery',{
            templateUrl: 'resources/static/views/gallery.html',
            controller: 'galleryController'
        })
        .when('/contactus',{
            templateUrl: 'resources/static/views/contactus.html',
            controller: 'contactusController'
        })
        .when('/usermanagement',{
        	roleId:1,
        	workflowId: 1,
        	templateUrl: 'resources/static/views/usermanagement.html',
        })
        .when('/viewusers',{
        	roleId:2,
        	workflowId: 1,
        	templateUrl: 'resources/static/views/usermanagement.html',
        })
        .otherwise(
            { redirectTo: '/'}
        );
});


