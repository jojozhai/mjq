'use strict';
//平台管理模块的配置
angular.module('accidentAdminModule',[]).config(function($stateProvider) {
	//路由配置
	$stateProvider.state('index.accidentManage', {
		url: "/accidentManage",
		controller: "accidentManageCtrl",
		templateUrl: "admin/views/accidentManage.html"
	});
//服务配置
}).service("accidentRestService", function($resource, commonService){
	var config = commonService.getDefaultRestSetting();
	config.findAll = {url:"accident/all", method:"GET", isArray:true};
	return $resource("accident/:id", {id:"@id"}, config);
//控制器
}).controller('accidentManageCtrl', function($scope, $uibModal, accidentRestService, commonService) {
	
	$scope.types = [{name:'公示公告',value:'公示公告'},{name:'办事指南',value:'办事指南'},{name:'工作动态',value:'工作动态'}];
	
	$scope.pageInfo = commonService.getDefaultPageSetting();
	
	$scope.query = function() {
		var condition = commonService.buildPageCondition($scope.condition, $scope.pageInfo);
		accidentRestService.query(condition).$promise.then(function(data){
			$scope.pageInfo.totalElements = data.totalElements;
			$scope.accidents = data.content;
		});
	}
	
	$scope.editContent = function(accident) {
		$uibModal.open({
			size: "lg",
			templateUrl : 'admin/views/umeditor.html',
			controller: 'umeditorCtrl',
			resolve: {
		        domain : function() {return accident;},
		        params : function() {
		        	return {
		        		target: 'accident',
		        		targetId: accident.id,
		        		targetProp: 'content'
		        	}
		        }
			}
		})
	}
	
	$scope.create = function() {
		$scope.save({type: ''});
	}
	
	$scope.update = function(accident) {
		$scope.save(accident);
	}
	
	$scope.save = function(accident){
		$uibModal.open({
			size: "lg",
			templateUrl : 'admin/views/accidentForm.html',
			controller: 'accidentFormCtrl',
			resolve: {
		        accident : function() {return accident;},
			}
		}).result.then(function(form){
			if(form.id){
				new accidentRestService(form).$save().then(function(){
					commonService.showMessage("修改事故信息成功");
				},function(response){
					for (var i = 0; i < $scope.accidents.length; i++) {
						if(form.id == $scope.accidents[i].id) {
							$scope.accidents[i] = accidentRestService.get({id:form.id});
							break;
						}
					}
				});
			}else{
				new accidentRestService(form).$create().then(function(accident){
					$scope.accidents.unshift(accident);
					commonService.showMessage("新建事故成功");
				});
			}
		});
	}
	
	$scope.remove = function(accident) {
		commonService.showConfirm("您确认要删除此事故?").result.then(function() {
			accidentRestService.remove({id:accident.id});
		}).then(function(){
			commonService.showMessage("删除事故成功");
			$scope.accidents.splice($scope.accidents.indexOf(accident), 1);
			if($scope.accidents.length == 0){
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
	
}).controller('accidentFormCtrl',function ($scope, $uibModalInstance, accident, commonService) {
	
	$scope.types = [{name:'公示公告',value:'公示公告'},{name:'办事指南',value:'办事指南'},{name:'工作动态',value:'工作动态'}];
	
	$scope.accident = accident;
	
	$scope.tinymceOptions = commonService.getDefaultTinymceOptions();
	
	$scope.save = function(accident) {
		$uibModalInstance.close(accident);
	};
	
	$scope.doUpload = function(files){
		commonService.uploadImage(files, $scope, function(imageUrl){
			$scope.accident.image = imageUrl;
		})		
	}
	
	$scope.doUpload2 = function(files){
		commonService.uploadImage(files, $scope, function(imageUrl){
			$scope.accident.desc = imageUrl;
		})		
	}
	
});