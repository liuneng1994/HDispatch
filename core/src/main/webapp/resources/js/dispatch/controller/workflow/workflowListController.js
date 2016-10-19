/**
 * Created by hasee on 2016/9/21.
 */
(function () {
    angular.module('dispatch').controller('workflowListController', ['$window', '$scope', 'workflowService', function ($window, $scope, workflowService) {
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
                        if (vm.workflow.themeId != undefined && isNaN(vm.workflow.themeId)) vm.workflow.themeId = 0;
                        if (vm.workflow.themeId != undefined && isNaN(vm.workflow.layerId)) vm.workflow.layerId = 0;
                        workflowService.query(vm.workflow).then(function (data) {
                            vm.total = data.total;
                            options.success(data.rows);
                        });
                    }
                },
                batch: true,
                serverPaging: true,
                pageSize: 50,
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
            scrollable: true,
            editable: false,
            pageable: {
                pageSizes: [10, 20, 50, 100],
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
                    title: '任务流',
                    width: 100
                },
                {
                    field: "theme",
                    title: '任务组',
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
                    width: 200
                },
                {
                    field: "",
                    title: '',
                    width: 150,
                    template: function (item) {
                        var html = "<button class='btn btn-info' ng-click='vm.edit(" + item.workflowId + ")'>编辑</button>"
                        return html;
                    }
                }]
        };

        vm.search = function () {
            $('#grid').data('kendoGrid').dataSource
                .read();
        };
        vm.create=function(url) {
            window.location = url;
        }

        vm.edit = function (id) {
            $window.sessionStorage['workflowId'] = id;
            location = '/dispatch/workflow/workflow_update.html';
        };

        vm.themeChange = function (themeId) {
            vm.workflow.layerId = undefined;
            refreshLayers('layers', themeId);
        }


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
