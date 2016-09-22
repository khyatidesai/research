<!DOCTYPE html>
<!--[if lt IE 7]>      <html lang="en" ng-app="app" class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html lang="en" ng-app="app" class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html lang="en" ng-app="app" class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en" ng-app="app" class="no-js"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Workflow POC</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=2">
    <link rel="stylesheet" href="./resources/static/css/app.css">
    <link rel="stylesheet" href="./resources/static/css/bootstrap.css">
</head>
<body>
<div class="home-section">
    <ul class="menu-list">
    	<li><a href="#/usermanagement">Register Users(Role:-Entry)</a></li>
    	<li><a href="#/viewusers">View Users(Role:-Reviewer)</a></li>
       <li><a href="#/viewusersa">View Users(Role:-Approver)</a></li>
    </ul>
</div>
<div ng-view></div>
<script src="./resources/static/js/angular/angular.js"></script>
<script src="./resources/static/js/angular/angular-resource.js"></script>
<script src="./resources/static/js/angular/angular-route.js"></script>
<script src="./resources/static/js/app.js"></script>
<script src="./resources/static/js/service/user_service.js"></script>
<script src="./resources/static/js/service/workflow_service.js"></script>
<script src="./resources/static/js/controller/user_controller.js"></script>
</body>
</html>
