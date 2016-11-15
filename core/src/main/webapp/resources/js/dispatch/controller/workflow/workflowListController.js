/**
 * Created by hasee on 2016/9/21.
 */
(function () {
    'use strict;';
    angular.module('dispatch').controller('workflowListController', ['$window', '$scope', 'workflowService', function ($window, $scope, workflowService) {
        var vm = this;
        vm.workflow = {};
        vm.workflow.workflowName = '';
        vm.workflow.description = '';
        vm.themes = {};
        vm.layers = {};
        vm.gridOptions = {
            dataSource: {
                transport: {
                    read: function (options) {
                        vm.workflow.page = options.data.page;
                        vm.workflow.pageSize = options.data.pageSize;
                        if (vm.workflow.themeId != undefined && isNaN(vm.workflow.themeId)) delete vm.workflow.themeId;
                        if (vm.workflow.themeId != undefined && isNaN(vm.workflow.layerId)) delete vm.workflow.layerId;
                        workflowService.query(vm.workflow).then(function (data) {
                            options.success(data);
                        });
                    },
                    destroy: function (options) {
                        "use strict";
                        var ids = [];
                        options.data.models.forEach(function (item) {
                            ids.push(item.workflowId);
                        });
                        workflowService.deleteWorkflow(ids).then(function () {
                            options.success();
                            kendo.ui.showInfoDialog({
                                message: '成功'
                            });
                        },function (data) {
                            $("#grid").data("kendoGrid").dataSource.cancelChanges();
                            kendo.ui.showErrorDialog({
                                message: data
                            });
                        });
                    }
                },
                batch: true,
                serverPaging: true,
                pageSize: 50,
                schema: {
                    data: "rows",
                    total: "total",
                    model: {
                        id: 'workflowId',
                        fields: {
                            name: {},
                            theme: {},
                            layer: {},
                            description: {}
                        }
                    }
                }
            },
            //width:500,
            navigatable: true,
            columnMenu: true,
            reorderable: true,
            resizable: true,
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
                    title: '层次',
                    width: 100
                },
                {
                    field: "description",
                    title: '描述',
                    width: 200
                },
                {
                    field: "",
                    title: '操作',
                    attributes: {style: "padding-top:0;padding-bottom:0"},
                    width: 200,
                    template: function (item) {
                        var html = '';
                        var disabled = "disabled";
                        if (hasOperatePermission(item.themeId)) {
                            disabled = "";
                        }
                        var html = "<button style='margin-left:4px' class='btn btn-info' " + disabled + " ng-click='vm.edit(" + item.workflowId + ")'>编辑</button>";
                        html += "<button style='margin-left:4px' class='btn btn-warning' " + disabled + " ng-click='vm.mutex(" + item.id + ")'>互斥</button>";
                        html += "<button style='margin-left:4px' class='btn btn-success' " + disabled + "  ng-click='vm.dependency(" + item.id + ")'>依赖</button>";
                        html += "<button style='margin-left:4px' class='btn btn-danger' " + disabled + "  ng-click='vm.delete(" + item.id + ")'>删除</button>"
                        html = "<div class='row'>"+html+"</div>";
                        return html;
                    }
                }]
        };

        vm.search = function () {
            $('#grid').data('kendoGrid').dataSource.page(1);
        };
        vm.create = function (url) {
            window.location = url;
        };

        vm.edit = function (id) {
            $window.sessionStorage['workflowId'] = id;
            location = _basePath + '/dispatch/workflow/workflow_update.html';
        };

        vm.resetQuery = function() {
            "use strict";
            vm.workflow.themeId = null;
            vm.workflow.layerId = null;
            vm.workflow.workflowName = '';
            vm.workflow.description = '';
        };

        vm.delete = function (id) {
            "use strict";
            kendo.ui.showConfirmDialog({
                title: $l('hap.tip.info'),
                message: $l('hap.tip.delete_confirm')
            }).done(function (event) {
                if (event.button == 'OK') {
                    $('#grid').data('kendoGrid').dataSource.remove($('#grid').data('kendoGrid').dataSource.get(id));
                    $('#grid').data('kendoGrid').dataSource.sync();
                }
            });
        };

        vm.themeChange = function (themeId) {
            vm.workflow.layerId = "";
            refreshLayers('layers', themeId);
        };

        vm.dependency = function (id) {
            "use strict";
            var item = $('#grid').data('kendoGrid').dataSource.get(id);
            $('#dependencyFrame').attr('src', _basePath + '/dispatch/workflow/dept_workflow_list.html?workflowId=' + item.workflowId + "&projectName=" + item.project + "&flowId=" + item.flowId);
            vm.dependencyWindow.maximize().open();
        };

        vm.mutex = function (id) {
            "use strict";
            var item = $('#grid').data('kendoGrid').dataSource.get(id);
            $('#mutexFrame').attr('src', _basePath + '/dispatch/workflow/mutex_workflow_list.html?workflowId=' + item.workflowId + "&projectName=" + item.project + "&flowId=" + item.flowId);
            vm.mutexWindow.maximize().open();
        };

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
})();
