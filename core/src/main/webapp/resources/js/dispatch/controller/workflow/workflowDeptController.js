/**
 * Created by hasee on 2016/10/25.
 */
(function () {
    "use strict";
    angular.module('dispatch').controller('workflowDeptController',
        ['workflowService', function (workflowService) {
            var vm = this;

            function getQueryString(name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                var r = window.location.search.substr(1).match(reg);
                if (r != null)return unescape(r[2]);
                return null;
            }

            vm.workflowId = getQueryString("workflowId");
            vm.projectName = getQueryString("projectName");
            vm.flowId = getQueryString("flowId");
            if (vm.workflowId && vm.projectName && vm.flowId) {
                init();
            }
            function init() {
                vm.gridOptions = {
                    dataSource: {
                        transport: {
                            read: function (options) {
                                workflowService.queryWorkflowDependency(vm.projectName, options.data.page, options.data.pageSize).then(function (data) {
                                    options.success(data);
                                });
                            },
                            create: function (options) {
                                var dependencies = [];
                                options.data.models.forEach(function (item) {
                                    dependencies.push({
                                        projectName: vm.projectName,
                                        flowId: vm.flowId,
                                        deptProjectName: item.deptProjectName,
                                        deptFlowId: item.deptFlowId
                                    });
                                });
                                workflowService.createWorkflowDependency(dependencies).then(function () {
                                    options.success('');
                                });
                            },
                            destroy: function (options) {
                                var dependencies = [];
                                options.data.models.forEach(function (item) {
                                    dependencies.push({
                                        projectName: vm.projectName,
                                        flowId: vm.flowId,
                                        deptProjectName: item.deptProjectName,
                                        deptFlowId: item.deptFlowId
                                    });
                                });
                                workflowService.deleteWorkflowDependency(dependencies).then(function () {
                                    options.success('');
                                });
                            }
                        },
                        batch: true,
                        serverPaging: true,
                        pageSize: 10,
                        schema: {
                            data: "rows",
                            total: "total",
                            model: {
                                id: 'workflowId',
                                fields: {}
                            }
                        }
                    },
                    //width:500,
                    selectable: "multiple, rowbox",
                    columnMenu: true,
                    reorderable: true,
                    navigatable: true,
                    resizable: true,
                    scrollable: true,
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
                            field: "deptWorkflowName",
                            title: '任务流',
                            width: 100
                        },
                        {
                            field: "deptTheme",
                            title: '任务组',
                            width: 100
                        },
                        {
                            field: "deptLayer",
                            title: '层级',
                            width: 100
                        }]
                };
                vm.themeOptions = {
                    dataTextField: "themeName",
                    dataValueField: "themeId",
                    optionLabel: "请选择主题",
                    autoBind: false,
                    dataSource: {
                        transport: {
                            read: function (options) {
                                workflowService.themes().then(function (data) {
                                    options.success(data);
                                });
                            }
                        }
                    }
                };
                vm.layerOptions = {
                    dataTextField: "layerName",
                    dataValueField: "layerId",
                    optionLabel: "请选择层级",
                    autoBind: false,
                    dataSource: {
                        transport: {
                            read: function (options) {
                                if (!vm.theme) {
                                    options.success('');
                                    return;
                                }
                                workflowService.layers(vm.theme).then(function (data) {
                                    options.success(data);
                                });
                            }
                        }
                    }
                };
                vm.workflowOptions = {
                    dataTextField: "name",
                    dataValueField: "workflowId",
                    optionLabel: "请选择任务流",
                    autoBind: false,
                    dataSource: {
                        transport: {
                            read: function (options) {
                                if (!vm.theme || !vm.layer) {
                                    options.success('');
                                    return;
                                }
                                workflowService.queryOperate({
                                    themeId: vm.theme,
                                    layerId: vm.layer,
                                    workflowName: '',
                                    description: '',
                                    page: 1,
                                    pageSize: 2147483647
                                }).then(function (data) {
                                    options.success(data.rows.filter(function(item) {
                                        return item.workflowId != vm.workflowId;
                                    }));
                                });
                            }
                        }
                    }
                };
                vm.createDeptWorkflow = function () {
                    $('#grid').data('kendoGrid').dataSource.add({
                        deptProjectName: vm.deptWorkflow.project,
                        deptFlowId: vm.deptWorkflow.flowId
                    });
                    $('#grid').data('kendoGrid').dataSource.sync().then(function() {
                        $('#grid').data('kendoGrid').dataSource.page(1);
                    });
                    vm.resetDeptWindow();
                };
                vm.resetDeptWindow = function () {
                    vm.deptWorkflow = null;
                    vm.createDependencyWindow.close();
                };
                vm.deleteDeptWorkflow = function() {
                    Hap.deleteGridSelection({grid:$("#grid")});
                };
                vm.themeChange = function () {
                    vm.layer = null;
                    $("#layer").data('kendoDropDownList').dataSource.read();
                };
                vm.layerChange = function () {
                    vm.deptWorkflow = null;
                    $("#deptWorkflow").data('kendoDropDownList').dataSource.read();
                };
                vm.openDeptWindow = function () {
                    vm.createDependencyWindow.center().open();
                }
            }
        }]);
})();