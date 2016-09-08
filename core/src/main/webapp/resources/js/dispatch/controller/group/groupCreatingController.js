/**
 * Created by 刘能 on 2016/9/7.
 */
(function () {
    angular.module('dispatch').controller('groupCreatingController', ['$scope', 'groupCreatingService', groupCreating]);
    function groupCreating($scope, groupCreatingService) {
        var vm = this;
        vm.themes = [{themeName: '加载中'}];
        vm.layers = [{layerName: '请先选择相应主题'}];
        vm.group = {};
        vm.group.active = true;
        vm.dependencySelectOptions = {
            placeholder: "添加依赖",
            dataTextField: "groupName",
            dataValueField: "groupName",
            valuePrimitive: true,
            autoBind: false,
            autoClose: false,
            dataSource: [{groupName: '1组'}, {groupName: '2组'}, {groupName: '3组'}]
        };
        groupCreatingService.getThemes().then(function (data) {
            vm.themes = data;
        });
        vm.refreshLayers = function () {
            console.log('刷新层');
            vm.layers = [{layerName: '加载中'}];
            groupCreatingService.getLayers(vm.group.theme).then(function (data) {
                vm.layers = data;
            });
        }
        $scope.$watch('vm.group.theme', function () {
            vm.refreshLayers();
        });
        return vm;
    };

})()

