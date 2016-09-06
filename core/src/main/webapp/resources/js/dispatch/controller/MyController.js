/**
 * Created by 刘能 on 2016/9/6.
 */
(function () {
    console.log(angular.module("dispatch"));
    angular.module("dispatch")
        .controller("MyCtrl",['$scope',function ($scope) {
            $scope.panelBarOptions = {};
            $scope.hello = "Hello from controller";
            console.log('create controller');
        }]);
})();