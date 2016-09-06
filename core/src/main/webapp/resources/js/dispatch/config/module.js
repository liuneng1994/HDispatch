/**
 * Created by 刘能 on 2016/9/6.
 */
(function () {
    'use strict;';
    angular.module('dispatch', [
        'ui.router',
        'oc.lazyLoad',
        'kendo.directives'
    ]);
    angular.element(document).ready(function () {
        angular.bootstrap(document.getElementById("app"), ["dispatch"]);
        console.log(angular.module('dispatch'));
    });
})()