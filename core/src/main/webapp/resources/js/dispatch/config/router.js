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

        $stateProvider.state(helloState);
        $stateProvider.state(aboutState);
    }
})();