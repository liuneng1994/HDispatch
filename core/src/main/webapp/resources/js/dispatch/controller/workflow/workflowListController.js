/**
 * Created by hasee on 2016/9/21.
 */
(function () {
    angular.module('dispatch').controller('workflowListController', ['$scope', 'workflowService', function ($scope, workflowService) {
        var vm = this;
        vm.workflow = {};
        vm.workflow.workflowName = '';
        vm.workflow.description = '';
        vm.total = 0;
        vm.themes = {};
        vm.layers = {};
        vm.gridOptions = {
            dataSource: {
                transport: {
                    read: function (options) {
                        vm.workflow.page = options.data.page;
                        vm.workflow.pageSize = options.data.pageSize;
                        if (vm.workflow.themeId != undefined && isNaN(vm.workflow.themeId)) vm.workflow.themeId=0;
                        if (vm.workflow.themeId != undefined && isNaN(vm.workflow.layerId)) vm.workflow.layerId=0;
                        workflowService.query(vm.workflow).then(function(data) {
                            vm.total = data.total;
                            options.success(data.rows);
                        });
                    }
                },
                batch: true,
                serverPaging: true,
                pageSize: 5,
                schema: {
                    total: function () {
                        return vm.total;
                    }
                }
            },
            //width:500,
            navigatable: true,
            resizable: true,
            reorderable: true,
            scrollable: false,
            editable: false,
            pageable: {
                pageSizes: [5, 10, 20, 50],
                refresh: true,
                buttonCount: 5,
                messages: {
                    noRecords: "未找到任何数据",
                    display: "{0} - {1} 共 {2} 条数据",
                    empty: "没有数据",
                    page: "页",
                    of: "/ {0}",
                    itemsPerPage: "条每页",
                    first: "第一页",
                    previous: "前一页",
                    next: "下一页",
                    last: "最后一页",
                    refresh: "刷新"
                }
            },
            columns: [
                {
                    field: "name",
                    title: '工作流',
                    width: 100
                },
                {
                    field: "theme",
                    title: '主题',
                    width: 100
                },
                {
                    field: "layer",
                    title: '层级',
                    width: 100
                },
                {
                    field: "description",
                    title: '描述',
                    width: 300
                },
                {
                    field: "",
                    title: '编辑',
                    width: 100,
                    template: function (item) {
                        var html = "<button  class='btn btn-info'onclick='openEditPage(\"" + item.flowId + "\")'"
                            + ">编辑"
                            + "</button>";
                        return html;
                    }
                }]
        };

        vm.search = function() {
            $('#grid').data('kendoGrid').dataSource
                .read();
        };

        $scope.$watch('vm.workflow.themeId', function () {
            vm.workflow.layerId = undefined;
            refreshLayers('layers', vm.workflow.themeId);
        });
        refreshThemes();
        return vm;

        function refreshThemes() {
            workflowService.themes().then(function (data) {
                vm.themes = data;
            });
        }

        function refreshLayers(layers, themeId) {
            workflowService.layers(themeId).then(function (data) {
                vm[layers] = data;
            });
        }
    }]);
})()
