/**
 * Created by hasee on 2016/10/25.
 */
(function () {
    "use strict";
    angular.module('dispatch').controller('workflowMutexController',
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
                                workflowService.queryWorkflowMutex(vm.projectName, options.data.page, options.data.pageSize).then(function (data) {
                                    options.success(data);
                                });
                            },
                            create: function (options) {
                                var mutexList = [];
                                options.data.models.forEach(function (item) {
                                    mutexList.push({
                                        projectName: vm.projectName,
                                        flowId: vm.flowId,
                                        mutexProjectName: item.mutexProjectName,
                                        mutexFlowId: item.mutexFlowId
                                    });
                                });
                                workflowService.createWorkflowMutex(mutexList).then(function () {
                                    options.success('');
                                    kendo.ui.showInfoDialog({
                                        message: $l('hap.tip.success')
                                    });
                                });
                            },
                            destroy: function (options) {
                                var mutexList = [];
                                options.data.models.forEach(function (item) {
                                    mutexList.push({
                                        projectName: vm.projectName,
                                        flowId: vm.flowId,
                                        mutexProjectName: item.mutexProjectName,
                                        mutexFlowId: item.mutexFlowId
                                    });
                                });
                                workflowService.deleteWorkflowMutex(mutexList).then(function () {
                                    options.success('');
                                    kendo.ui.showInfoDialog({
                                        message: $l('hap.tip.success')
                                    });
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
                    navigatable: true,
                    columnMenu: true,
                    resizable: true,
                    reorderable: true,
                    scrollable: true,
                    pageable: {
                        pageSizes: [5, 10, 20, 50],
                        refresh: true,
                        buttonCount: 5,
                        messages: {
                            noRecords: $l('hdispatch.grid_find_no_data'),
                            display: "{0} - {1} " + $l('hdispatch.grid_data_total_num') + " {2} " + $l('hdispatch.grid_data_records'),
                            empty: $l('hdispatch.grid_find_no_data'),
                            page: $l('hdispatch.grid_page'),
                            of: "/ {0}",
                            itemsPerPage: $l('hdispatch.grid_pages_per_page'),
                            first: $l('hdispatch.grid_first_page'),
                            previous: $l('hdispatch.grid_pre_page'),
                            next: $l('hdispatch.grid_next_page'),
                            last: $l('hdispatch.grid_last_page'),
                            refresh: $l('hdispatch.grid_refreshff')
                        }
                    },
                    columns: [
                        {
                            field: "mutexWorkflowName",
                            title: $l('hdispatch.workflow'),
                            width: 100
                        },
                        {
                            field: "mutexTheme",
                            title: $l('hdispatch.theme'),
                            width: 100
                        },
                        {
                            field: "mutexLayer",
                            title: $l('hdispatch.layer'),
                            width: 100
                        }]
                };
                vm.themeOptions = {
                    dataTextField: "themeName",
                    dataValueField: "themeId",
                    optionLabel: "",
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
                    optionLabel: "",
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
                    optionLabel: "",
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
                                    options.success(data.rows.filter(function (item) {
                                        return item.workflowId != vm.workflowId;
                                    }));
                                });
                            }
                        }
                    }
                };
                vm.createMutexWorkflow = function () {
                    $('#grid').data('kendoGrid').dataSource.add({
                        mutexProjectName: vm.mutexWorkflow.project,
                        mutexFlowId: vm.mutexWorkflow.flowId
                    });
                    $('#grid').data('kendoGrid').dataSource.sync().then(function () {
                        $('#grid').data('kendoGrid').dataSource.page(1);
                    });
                    vm.resetMutexWindow();
                };
                vm.resetMutexWindow = function () {
                    vm.mutexWorkflow = null;
                    vm.createMutexWindow.close();
                };
                vm.deleteMutexWorkflow = function () {
                    Hap.deleteGridSelection({grid: $("#grid")});
                };
                vm.themeChange = function () {
                    vm.layer = null;
                    $("#layer").data('kendoDropDownList').dataSource.read();
                };
                vm.layerChange = function () {
                    vm.mutexWorkflow = null;
                    $("#mutexWorkflow").data('kendoDropDownList').dataSource.read();
                };
                vm.openMutexWindow = function () {
                    vm.createMutexWindow.center().open();
                }
            }
        }]);
})();