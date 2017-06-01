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
	config.deny = {url:"inform/:id/deny", method:"PUT"};
	config.modify = {url:"inform/:id/modify", method:"PUT"};
	return $resource("inform/:id", {id:"@id"}, config);
//控制器
}).controller('informManageCtrl', function($scope, $uibModal, informRestService, commonService) {
	
	$scope.types = [
	                {name:'暴露垃圾',value:'暴露垃圾'},
	                {name:'道路不洁',value:'道路不洁'},
	                {name:'公共厕所保障不到位',value:'公共厕所保障不到位'},
	                {name:'垃圾箱破损',value:'垃圾箱破损、保洁不到位'},
	                {name:'店外经营',value:'店外（占道）经营'},
	                {name:'乱堆物料',value:'乱堆物料'},
	                {name:'露天烧烤',value:'露天烧烤'},
	                {name:'违规晾晒',value:'违规晾晒'},
	                {name:'违规设置标语宣传品',value:'违规设置标语宣传品'},
	                {name:'违规设置牌匾',value:'违规设置牌匾、标识、灯箱等'},
	                {name:'小广告',value:'小广告'}
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
				informRestService.modify(form).$promise.then(function(){
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
	
	$scope.deny = function(inform) {
		commonService.showConfirm("您确认要拒绝此爆料?").result.then(function() {
			informRestService.deny({id:inform.id}).$promise.then(function(data){
				commonService.showMessage("爆料拒绝");
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
	
	$scope.removeImg1 = function(image) {
		$scope.inform.images.splice($scope.inform.images.indexOf(image), 1);
	}
	
	$scope.removeImg2 = function(image) {
		$scope.inform.images2.splice($scope.inform.images2.indexOf(image), 1);
	}
	
	$scope.doUpload1 = function(files){
		commonService.uploadImage(files, $scope, function(result){
			$scope.inform.images.push(result);
		});
	}
	
	$scope.doUpload2 = function(files){
		commonService.uploadImage(files, $scope, function(result){
			$scope.inform.images2.push(result);
		});
	}
	
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