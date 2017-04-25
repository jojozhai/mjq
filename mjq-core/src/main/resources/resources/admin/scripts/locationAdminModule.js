'use strict';
//平台管理模块的配置
angular.module('locationAdminModule',[]).config(function($stateProvider) {
	//路由配置
	$stateProvider.state('index.locationManage', {
		url: "/locationManage",
		controller: "locationManageCtrl",
		templateUrl: "admin/views/locationManage.html"
	});
//服务配置
}).service("locationRestService", function($resource, commonService){
	var config = commonService.getDefaultRestSetting();
	config.findAll = {url:"location/all", method:"GET", isArray:true};
	return $resource("location/:id", {id:"@id"}, config);
//控制器
}).controller('locationManageCtrl', function($scope, $uibModal, locationRestService, commonService) {
	
	$scope.types = [{name:'厕所',value:'wc'},{name:'停车场',value:'car'}];

	$scope.pageInfo = commonService.getDefaultPageSetting();
	
	$scope.query = function() {
		var condition = commonService.buildPageCondition($scope.condition, $scope.pageInfo);
		locationRestService.query(condition).$promise.then(function(data){
			$scope.pageInfo.totalElements = data.totalElements;
			$scope.locations = data.content;
		});
	}
	
	$scope.create = function() {
		$scope.save({type: ''});
	}
	
	$scope.update = function(location) {
		$scope.save(location);
	}
	
	$scope.save = function(location){
		$uibModal.open({
			size: "lg",
			templateUrl : 'admin/views/locationForm.html',
			controller: 'locationFormCtrl',
			resolve: {
		        location : function() {return location;},
			}
		}).result.then(function(form){
			if(form.id){
				new locationRestService(form).$save().then(function(){
					commonService.showMessage("修改位置信息成功");
				},function(response){
					for (var i = 0; i < $scope.locations.length; i++) {
						if(form.id == $scope.locations[i].id) {
							$scope.locations[i] = locationRestService.get({id:form.id});
							break;
						}
					}
				});
			}else{
				new locationRestService(form).$create().then(function(location){
					$scope.locations.unshift(location);
					commonService.showMessage("新建位置成功");
				});
			}
		});
	}
	
	$scope.remove = function(location) {
		commonService.showConfirm("您确认要删除此位置?").result.then(function() {
			locationRestService.remove({id:location.id});
		}).then(function(){
			commonService.showMessage("删除位置成功");
			$scope.locations.splice($scope.locations.indexOf(location), 1);
			if($scope.locations.length == 0){
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
	
}).controller('locationFormCtrl',function ($scope, $uibModalInstance, location, commonService) {
	
	$scope.types = [{name:'厕所',value:'wc'},{name:'停车场',value:'car'}];
	
	$scope.location = location;
	
	$scope.tinymceOptions = commonService.getDefaultTinymceOptions();
	
	$scope.save = function(location) {
		$uibModalInstance.close(location);
	};
	
	$scope.doUpload = function(files){
		commonService.uploadImage(files, $scope, function(imageUrl){
			$scope.location.image = imageUrl;
		})		
	}
	
	$scope.doUpload2 = function(files){
		commonService.uploadImage(files, $scope, function(imageUrl){
			$scope.location.desc = imageUrl;
		})		
	}
	
}).filter("locationType", function(){
	return function (text) {
		if(text == 'wc'){
			return "厕所";
		}else if(text == 'car'){
			return "停车场";
		}else{
			return "未知";
		}
    }
});