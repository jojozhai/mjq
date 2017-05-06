'use strict';
//平台管理模块的配置
angular.module('articleAdminModule',[]).config(function($stateProvider) {
	//路由配置
	$stateProvider.state('index.articleManage', {
		url: "/articleManage",
		controller: "articleManageCtrl",
		templateUrl: "admin/views/articleManage.html"
	});
//服务配置
}).service("articleRestService", function($resource, commonService){
	var config = commonService.getDefaultRestSetting();
	config.findAll = {url:"article/all", method:"GET", isArray:true};
	return $resource("article/:id", {id:"@id"}, config);
//控制器
}).controller('articleManageCtrl', function($scope, $uibModal, articleRestService, commonService) {
	
	$scope.types = [{name:'公示公告',value:'公示公告'},{name:'办事指南',value:'办事指南'},{name:'工作动态',value:'工作动态'}];
	
	$scope.pageInfo = commonService.getDefaultPageSetting();
	
	$scope.query = function() {
		var condition = commonService.buildPageCondition($scope.condition, $scope.pageInfo);
		articleRestService.query(condition).$promise.then(function(data){
			$scope.pageInfo.totalElements = data.totalElements;
			$scope.articles = data.content;
		});
	}
	
	$scope.editContent = function(article) {
		$uibModal.open({
			size: "lg",
			templateUrl : 'admin/views/umeditor.html',
			controller: 'umeditorCtrl',
			resolve: {
		        domain : function() {return article;},
		        params : function() {
		        	return {
		        		target: 'article',
		        		targetId: article.id,
		        		targetProp: 'content'
		        	}
		        }
			}
		})
	}
	
	$scope.create = function() {
		$scope.save({type: ''});
	}
	
	$scope.update = function(article) {
		$scope.save(article);
	}
	
	$scope.save = function(article){
		$uibModal.open({
			size: "lg",
			templateUrl : 'admin/views/articleForm.html',
			controller: 'articleFormCtrl',
			resolve: {
		        article : function() {return article;},
			}
		}).result.then(function(form){
			if(form.id){
				new articleRestService(form).$save().then(function(){
					commonService.showMessage("修改政务信息成功");
				},function(response){
					for (var i = 0; i < $scope.articles.length; i++) {
						if(form.id == $scope.articles[i].id) {
							$scope.articles[i] = articleRestService.get({id:form.id});
							break;
						}
					}
				});
			}else{
				new articleRestService(form).$create().then(function(article){
					$scope.articles.unshift(article);
					commonService.showMessage("新建政务成功");
				});
			}
		});
	}
	
	$scope.remove = function(article) {
		commonService.showConfirm("您确认要删除此政务?").result.then(function() {
			articleRestService.remove({id:article.id});
		}).then(function(){
			commonService.showMessage("删除政务成功");
			$scope.articles.splice($scope.articles.indexOf(article), 1);
			if($scope.articles.length == 0){
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
	
}).controller('articleFormCtrl',function ($scope, $uibModalInstance, article, commonService) {
	
	$scope.types = [{name:'公示公告',value:'公示公告'},{name:'办事指南',value:'办事指南'},{name:'工作动态',value:'工作动态'}];
	
	$scope.article = article;
	
	$scope.tinymceOptions = commonService.getDefaultTinymceOptions();
	
	$scope.save = function(article) {
		$uibModalInstance.close(article);
	};
	
	$scope.doUpload = function(files){
		commonService.uploadImage(files, $scope, function(imageUrl){
			$scope.article.image = imageUrl;
		})		
	}
	
	$scope.doUpload2 = function(files){
		commonService.uploadImage(files, $scope, function(imageUrl){
			$scope.article.desc = imageUrl;
		})		
	}
	
});