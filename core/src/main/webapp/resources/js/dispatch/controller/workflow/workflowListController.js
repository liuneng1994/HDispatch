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
        vm.periods = [{name: '月', value: 'M'},
            {name: '周', value: 'w'},
            {name: '天', value: 'd'},
            {name: '时', value: 'H'},
            {name: '分', value: 'm'},
            {name: '秒', value: 's'}];
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
                        var html = "<button class='btn btn-info' ng-click='vm.edit(" + item.workflowId + ")'>编辑</button>" +
                            '<button class="btn btn-primary" ng-click="vm.schedule(' + item.workflowId + ')">计划</button>' +
                            '<button class="btn btn-info" ng-click="vm.execute(' + item.workflowId + ')">执行</button>';
                        return html;
                    }
                }]
        };

        vm.search = function () {
            $('#grid').data('kendoGrid').dataSource
                .read();
        };

        vm.edit = function (id) {
            $window.sessionStorage['workflowId'] = id;
            location = '/dispatch/workflow/workflow_update.html';
        };

        vm.themeChange = function (themeId) {
            vm.workflow.layerId = undefined;
            refreshLayers('layers', themeId);
        }
        vm.schedule = function (workflowId) {
            vm.scheduleFlow = {};
            vm.scheduleFlow.loading = true;
            vm.scheduleFlow.isrecurring = false;
            vm.scheduleFlow.period = 'M';
            workflowService.workflow(workflowId).then(function (data) {
                if (data.projectName && data.flowId) {
                    vm.scheduleFlow.projectName = data.projectName;
                    vm.scheduleFlow.flowId = data.flowId;
                    vm.scheduleFlow.name = data.name;
                    vm.scheduleFlow.loading = false;
                    vm.scheduleWindow.center().open();
                } else {
                    alert('没有生成任务流');
                }
            })
        };
        vm.scheduleSubmit = function () {
            var scheduleInfo = {};
            scheduleInfo.projectName = vm.scheduleFlow.projectName;
            scheduleInfo.flow = vm.scheduleFlow.flowId;
            scheduleInfo.datetime = moment(vm.scheduleFlow.startTime).valueOf();
            //scheduleInfo.time = moment(vm.scheduleFlow.startTime).format('hh,mm,a,Z');
            //scheduleInfo.date = moment(vm.scheduleFlow.startTime).format('MM/DD/YYYY');
            scheduleInfo.isrecurring = vm.scheduleFlow.isrecurring;
            if (scheduleInfo.isrecurring) {
                scheduleInfo.period = vm.scheduleFlow.period;
                scheduleInfo.periodVal = vm.scheduleFlow.periodVal;
            }
            workflowService.scheduleWorkflow(scheduleInfo).then(function(data) {
                alert(data.message);
            });
            vm.scheduleWindow.close();
        };
        vm.scheduleCancel = function () {
            vm.scheduleWindow.close();
        };

        vm.execute = function(workflowId) {
            vm.executeInfo = {};
            vm.executeInfo.loading = true;
            workflowService.workflow(workflowId).then(function(data) {
                if (data.projectName && data.flowId) {
                    vm.executeInfo.project = data.projectName;
                    vm.executeInfo.flow = data.flowId;
                    vm.executeInfo.name = data.name;
                    vm.executeInfo.description = data.description;
                    vm.executeInfo.failureAction = 'finishCurrent';
                    vm.executeInfo.loading = false;
                    vm.executeWindow.center().open();
                } else {
                    alert('没有生成任务流');
                }
            })
        };
        vm.executeSubmit = function() {
            if (vm.executeInfo.successEmails) {
                vm.executeInfo.successEmailsOverride = true;
            }
            if (vm.executeInfo.failureEmails) {
                vm.failureEmailsOverride = true;
            }
            workflowService.executeWorkflow(vm.executeInfo).then(function(data) {
                alert(data);
            });
            console.log(vm.executeInfo);
            vm.executeWindow.close();
        };
        vm.executeCancel = function() {
            vm.executeWindow.close();
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
