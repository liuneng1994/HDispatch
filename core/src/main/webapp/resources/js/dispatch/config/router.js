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
            template: '<h3>Its the UI-Router hello world app!</h3>'
        }

        $stateProvider.state(helloState);
        $stateProvider.state(aboutState);
    }
})();