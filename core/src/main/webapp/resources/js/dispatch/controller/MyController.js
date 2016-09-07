/**
 * Created by 刘能 on 2016/9/6.
 */
(function () {
    angular.module("dispatch")
        .controller("MyCtrl", ['$scope', function ($scope) {
            $scope.panelBarOptions = {};
            $scope.hello = "Hello from controller";
        }]);
})();