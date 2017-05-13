'use strict';
//平台管理模块的配置
angular.module('informAdminModule',[]).config(function($stateProvider) {
	//路由配置
	$stateProvider.state('index.informManage', {
		url: "/informManage",
		controller: "informManageCtrl",
		templateUrl: "admin/views/informManage.html"
	});
//服务配置
}).service("informRestService", function($resource, commonService){
	var config = commonService.getDefaultRestSetting();
	config.bonus = {url:"inform/:id/bonus", method:"POST"};
	config.accept = {url:"inform/:id/accept", method:"PUT"};
	return $resource("inform/:id", {id:"@id"}, config);
//控制器
}).controller('informManageCtrl', function($scope, $uibModal, informRestService, commonService) {
	
	$scope.types = [
	                {name:'市容环境',value:'市容环境'},
	                {name:'宣传广告',value:'宣传广告'},
	                {name:'园林绿化',value:'园林绿化'},
	                {name:'环卫设施不洁',value:'环卫设施不洁'},
	                {name:'交通设置破坏',value:'交通设置破坏'},
	                {name:'市政设施破损',value:'市政设施破损'},
	                {name:'其他',value:'其他'}
	                ];
	
	$scope.statuses = [{name:'未受理',value:'WAITING'},{name:'已受理',value:'WORKING'},{name:'已处理',value:'WORKED'},{name:'已办结',value:'FINISH'}];

	$scope.pageInfo = commonService.getDefaultPageSetting();
	
	$scope.query = function() {
		var condition = commonService.buildPageCondition($scope.condition, $scope.pageInfo);
		informRestService.query(condition).$promise.then(function(data){
			$scope.pageInfo.totalElements = data.totalElements;
			$scope.informs = data.content;
		});
	}
	
	$scope.create = function() {
		$scope.save({saleCount: 0, saleCountPlus: 0});
	}
	
	$scope.update = function(inform) {
		$scope.save(inform);
	}
	
	$scope.save = function(inform){
		$uibModal.open({
			size: "lg",
			templateUrl : 'admin/views/informForm.html',
			controller: 'informFormCtrl',
			resolve: {
		        inform : function() {return inform;},
			}
		}).result.then(function(form){
			if(form.id){
				new informRestService(form).$save().then(function(){
					commonService.showMessage("修改爆料信息成功");
				},function(response){
					for (var i = 0; i < $scope.informs.length; i++) {
						if(form.id == $scope.informs[i].id) {
							$scope.informs[i] = informRestService.get({id:form.id});
							break;
						}
					}
				});
			}else{
				new informRestService(form).$create().then(function(inform){
					$scope.informs.unshift(inform);
					commonService.showMessage("新建爆料成功");
				});
			}
		});
	}
	
	$scope.remove = function(inform) {
		commonService.showConfirm("您确认要删除此爆料?").result.then(function() {
			informRestService.remove({id:inform.id});
		}).then(function(){
			commonService.showMessage("删除爆料成功");
			$scope.informs.splice($scope.informs.indexOf(inform), 1);
			if($scope.informs.length == 0){
				$scope.pageInfo.page = $scope.pageInfo.page - 1;
				$scope.query();
			}
		});
	} 
	
	$scope.cleanCondition = function() {
		$scope.condition = {};
		$scope.query();
	}
	
	$scope.bonus = function(inform) {
		commonService.showConfirm("您确认要办结此爆料?").result.then(function() {
			informRestService.bonus({id:inform.id}).$promise.then(function(data){
				commonService.showMessage("爆料已办结");
			});
		});
		
		
	}
	
	$scope.accept = function(inform) {
		commonService.showConfirm("您确认要受理此爆料?").result.then(function() {
			informRestService.accept({id:inform.id}).$promise.then(function(data){
				commonService.showMessage("爆料已受理");
			});
		});
	}
	
	$scope.query();
	
}).controller('informFormCtrl',function ($scope, $uibModalInstance, inform, commonService) {
	
	console.log(inform.images);
	
	$scope.inform = inform;
	
	$scope.tinymceOptions = commonService.getDefaultTinymceOptions();
	
	$scope.save = function(inform) {
		$uibModalInstance.close(inform);
	};
	
}).filter("informStatus", function(){
	return function (text) {
		if(text == 'WAITING'){
			return "未受理";
		}else if(text == 'WORKING'){
			return "已受理";
		}else if(text == 'WORKED'){
			return "已处理";
		}else if(text == 'FINISH'){
			return "已办结";
		}
    }
}).filter("informBonus", function(){
	return function (text) {
		console.log(text);
		if(text == '0'){
			return "未发放";
		}else{
			return text+"分";
		}
    }
});