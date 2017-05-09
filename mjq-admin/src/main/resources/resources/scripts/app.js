'use strict';

/**
 * @ngdoc overview
 * @name testApp
 * @description # testApp
 * 
 * Main module of the application.
 */
// 应用主模块
angular.module('adminApp', [ 'admin', 'userAdminModule', 'paramAdminModule', 'buttonAdminModule',
		'informAdminModule', 'umeditorModule', 'locationAdminModule', 'posterAdminModule',
		'articleAdminModule', 'feedbackAdminModule', 'commentAdminModule',
		'accidentAdminModule' ]);
