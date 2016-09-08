/**
 * Created by 刘能 on 2016/9/6.
 */
(function () {
    'use strcit';
    angular
        .module('dispatch')
        .config([
            '$stateProvider',
            '$urlRouterProvider',
            routerConfig
        ]);
    function routerConfig($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise("/dispatch/index.html");
        var helloState = {
            name: 'hello',
            url: '/hello',
            template: '<h3>hello world!</h3>'
        }

        var aboutState = {
            name: 'about',
            url: '/about',
            templateUrl: '/dispatch/sch_group/sch_group.html',
            resolve: {
                deps: [
                    '$ocLazyLoad',
                    function ($ocLazyLoad) {
                        return $ocLazyLoad.load(
                            {
                                serie: true,
                                files: [
                                    '/resources/js/dispatch/controller/sch_group/sch_groupController.js',
                                    '/resources/js/dispatch/controller/sch_group/sch_groupService.js'
                                    
                                ]
                            });
                    }]
            }
        }

        var themeAddingState = {
            name: 'themeCreate',
            url: '/themeCreate',
            templateUrl: '/dispatch/theme/theme_add.html',
            controller:'themeCreateController',
            resolve: {
                dept: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load(['../resources/js/dispatch/controller/theme/themeCreateController.js']);
                }]
            }
        }
        var layerAddingState = {
            name: 'layerCreate',
            url: '/layerCreate',
            controller:'layerCreateController',
            templateUrl: '/dispatch/layer/layer_add.html',
            resolve: {
                dept: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load(['../resources/js/dispatch/controller/layer/layerCreateController.js']);
                }]
            }
        }

        $stateProvider.state(helloState);
        $stateProvider.state(aboutState);
        $stateProvider.state(themeAddingState);
        $stateProvider.state({
            name: 'groupCreate',
            url: 'groupCreate',
            templateUrl: '/dispatch/group/group_create.html',
            controller: 'groupCreatingController',
            controllerAs: 'vm',
            resolve: {
                dept: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load(['../resources/js/dispatch/service/group/groupCreatingService.js',
                        '../resources/js/dispatch/controller/group/groupCreatingController.js']);
                }]
            }
        });
        $stateProvider.state(layerAddingState);
    }
})();