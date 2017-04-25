'use strict';
//平台管理模块的配置
angular.module('feedbackAdminModule',[]).config(function($stateProvider) {
	//路由配置
	$stateProvider.state('index.feedbackManage', {
		url: "/feedbackManage",
		controller: "feedbackManageCtrl",
		templateUrl: "admin/views/feedbackManage.html"
	});
//服务配置
}).service("feedbackRestService", function($resource, commonService){
	var config = commonService.getDefaultRestSetting();
	config.findAll = {url:"feedback/all", method:"GET", isArray:true};
	return $resource("feedback/:id", {id:"@id"}, config);
//控制器
}).controller('feedbackManageCtrl', function($scope, $uibModal, feedbackRestService, commonService) {
	
	$scope.pageInfo = commonService.getDefaultPageSetting();
	
	$scope.query = function() {
		var condition = commonService.buildPageCondition($scope.condition, $scope.pageInfo);
		feedbackRestService.query(condition).$promise.then(function(data){
			$scope.pageInfo.totalElements = data.totalElements;
			$scope.feedbacks = data.content;
		});
	}
	
	$scope.create = function() {
		$scope.save({type: ''});
	}
	
	$scope.update = function(feedback) {
		$scope.save(feedback);
	}
	
	$scope.save = function(feedback){
		$uibModal.open({
			size: "lg",
			templateUrl : 'admin/views/feedbackForm.html',
			controller: 'feedbackFormCtrl',
			resolve: {
		        feedback : function() {return feedback;},
			}
		}).result.then(function(form){
			if(form.id){
				new feedbackRestService(form).$save().then(function(){
					commonService.showMessage("修改反馈信息成功");
				},function(response){
					for (var i = 0; i < $scope.feedbacks.length; i++) {
						if(form.id == $scope.feedbacks[i].id) {
							$scope.feedbacks[i] = feedbackRestService.get({id:form.id});
							break;
						}
					}
				});
			}else{
				new feedbackRestService(form).$create().then(function(feedback){
					$scope.feedbacks.unshift(feedback);
					commonService.showMessage("新建反馈成功");
				});
			}
		});
	}
	
	$scope.remove = function(feedback) {
		commonService.showConfirm("您确认要删除此反馈?").result.then(function() {
			feedbackRestService.remove({id:feedback.id});
		}).then(function(){
			commonService.showMessage("删除反馈成功");
			$scope.feedbacks.splice($scope.feedbacks.indexOf(feedback), 1);
			if($scope.feedbacks.length == 0){
				$scope.pageInfo.page = $scope.pageInfo.page - 1;
				$scope.query();
			}
		});
	} 
	
	$scope.cleanCondition = function() {
		$scope.condition = {};
		$scope.query();
	}
	
	$scope.query();
	
}).controller('feedbackFormCtrl',function ($scope, $uibModalInstance, feedback, commonService) {
	
	$scope.feedback = feedback;
	
	$scope.tinymceOptions = commonService.getDefaultTinymceOptions();
	
	$scope.save = function(feedback) {
		$uibModalInstance.close(feedback);
	};
	
	$scope.doUpload = function(files){
		commonService.uploadImage(files, $scope, function(imageUrl){
			$scope.feedback.image = imageUrl;
		})	
	}
	
	$scope.doUpload2 = function(files){
		commonService.uploadImage(files, $scope, function(imageUrl){
			$scope.feedback.desc = imageUrl;
		})		
	}
	
});