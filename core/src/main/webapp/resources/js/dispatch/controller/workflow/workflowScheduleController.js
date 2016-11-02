/**
 * Created by hasee on 2016/9/21.
 */
(function () {
    'use strict';
    angular.module('dispatch').controller('workflowListController', ['$window', '$scope', 'workflowService', function ($window, $scope, workflowService) {
        var vm = this;

        vm.notification = $("#notification").kendoNotification({
            position: {
                pinned: true,
                top: 30,
                right: 30
            },
            autoHideAfter: 3000,
            stacking: "down",
            templates: [{
                type: "error",
                template: $("#errorTemplate").html()
            }, {
                type: "upload-success",
                template: $("#successTemplate").html()
            }]

        }).data("kendoNotification");
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
                        vm.selectedWorkflow = [];
                        vm.workflow.page = options.data.page;
                        vm.workflow.pageSize = options.data.pageSize;
                        if (vm.workflow.themeId != undefined && isNaN(vm.workflow.themeId)) vm.workflow.themeId = 0;
                        if (vm.workflow.themeId != undefined && isNaN(vm.workflow.layerId)) vm.workflow.layerId = 0;
                        workflowService.queryOperate(vm.workflow).then(function (data) {
                            vm.total = data.total;
                            vm.currentWorklfows = data.rows;
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
            selectable: 'multiple, rowbox',
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
                }]
        };

        vm.search = function () {
            $('#grid').data('kendoGrid').dataSource.page(1);
        };

        vm.themeChange = function (themeId) {
            vm.workflow.layerId = undefined;
            refreshLayers('layers', themeId);
        };

        function getSelectWorkflows() {
            vm.selectedWorkflow = [];
            var checked = $('#grid').data('kendoGrid').selectedDataItems();
            checked.forEach(function(item) {
                vm.selectedWorkflow.push(item.workflowId);
            });
        }

        vm.schedule = function () {
            getSelectWorkflows();
            var selectedFlows = loadSelectedWorkflow();
            vm.scheduleFlow = {};
            vm.scheduleFlow.loading = selectedFlows.size;
            if (vm.scheduleFlow.loading == 0) {
                return;
            }
            vm.scheduleFlow.isrecurring = false;
            vm.scheduleFlow.period = 'd';
            vm.scheduleFlow.flows = {};
            vm.scheduleFlow.name = '';
            vm.scheduleWindow.center().open();
            for (var flow of selectedFlows.values()) {
                workflowService.workflow(flow.workflowId).then(function (data) {
                    vm.scheduleFlow.loading--;
                    if (data.projectName && data.flowId) {
                        vm.scheduleFlow.flows[data.workflowId] = {};
                        vm.scheduleFlow.flows[data.workflowId].projectName = data.projectName;
                        vm.scheduleFlow.flows[data.workflowId].flowId = data.flowId;
                        vm.scheduleFlow.name += (data.name + '  ');
                    } else {
                        vm.notification.show({
                            title: data.name,
                            message: "未生成工作流"
                        }, "error");
                    }
                    if (vm.scheduleFlow.loading == 0 && vm.scheduleFlow.name.length == 0) {
                        vm.scheduleWindow.close();
                    }
                })
            }
            ;
        };
        vm.scheduleSubmit = function () {
            var scheduleInfo = {};
            scheduleInfo.projectName = vm.scheduleFlow.projectName;
            scheduleInfo.flow = vm.scheduleFlow.flowId;
            scheduleInfo.datetime = moment(vm.scheduleFlow.startTime).valueOf();
            scheduleInfo.isrecurring = vm.scheduleFlow.isrecurring;
            if (scheduleInfo.isrecurring) {
                scheduleInfo.period = vm.scheduleFlow.period;
                scheduleInfo.periodVal = vm.scheduleFlow.periodVal;
            }
            for (var id in vm.scheduleFlow.flows) {
                scheduleInfo.projectName = vm.scheduleFlow.flows[id].projectName;
                scheduleInfo.flow = vm.scheduleFlow.flows[id].flowId;
                var arg = angular.copy(scheduleInfo);
                workflowService.scheduleWorkflow(arg).then(function (data) {
                    vm.notification.show({
                        message: data||'计划成功'
                    }, "upload-success");
                }, function (data) {
                    vm.notification.show({
                        title: '',
                        message: data||'计划失败'
                    }, "error");
                });
            }
            vm.scheduleWindow.close();
        };
        vm.scheduleCancel = function () {
            vm.scheduleWindow.close();
        };

        vm.execute = function () {
            getSelectWorkflows();
            vm.showExecuteGraph = false;
            var selectedFlows = loadSelectedWorkflow();
            vm.executeInfo = {};
            vm.executeInfo.loading = selectedFlows.size;
            vm.executeInfo.name = '';
            vm.executeInfo.flows = {};
            vm.executeInfo.failureAction = 'finishCurrent';
            vm.executeInfo.concurrentOption = 'skip';
            if (vm.executeInfo.loading == 0) {
                return;
            }
            vm.executeWindow.maximize().open();
            if (selectedFlows.size == 1) vm.showExecuteGraph = true;
            for (var flow of selectedFlows.values()) {
                workflowService.workflow(flow.workflowId).then(function (data) {
                    vm.executeInfo.loading--;
                    if (vm.showExecuteGraph) {
                        window.setTimeout(function () {
                            vm.disabledEl = new Set;
                            vm.paint.parse(data.graph);
                            vm.paint.format({
                                rankDir: "TB",
                                marginX: 50,
                                marginY: 50
                            });
                            for (var link of vm.paint._graph.getLinks()) {
                                link.attr('.link-tools/display', 'none');
                            }
                        }, 500);
                    }
                    if (data.projectName && data.flowId) {
                        vm.executeInfo.flows[data.workflowId] = {};
                        vm.executeInfo.flows[data.workflowId].project = data.projectName;
                        vm.executeInfo.flows[data.workflowId].flow = data.flowId;
                        vm.executeInfo.name += (data.name + '  ');
                    } else {
                        vm.notification.show({
                            title: data.name,
                            message: "未生成工作流"
                        }, "error");
                    }
                    if (vm.executeInfo.loading == 0 && vm.executeInfo.name.length == 0) {
                        vm.scheduleWindow.close();
                    }
                });
            }
        };
        vm.executeSubmit = function () {
            console.log(gatherDisableElements());
            if (vm.executeInfo.successEmails) {
                vm.executeInfo.successEmailsOverride = true;
            }
            if (vm.executeInfo.failureEmails) {
                vm.failureEmailsOverride = true;
            }
            for (var id in vm.executeInfo.flows) {
                if (vm.showExecuteGraph) vm.executeInfo.disabled = JSON.stringify(gatherDisableElements());
                vm.executeInfo.project = vm.executeInfo.flows[id].project;
                vm.executeInfo.flow = vm.executeInfo.flows[id].flow;
                var arg = angular.copy(vm.executeInfo);
                workflowService.executeWorkflow(arg).then(function (data) {
                    vm.notification.show({
                        message: data
                    }, "upload-success");
                }, function (data) {
                    vm.notification.show({
                        title: '',
                        message: data
                    }, "error");
                });
            }
            vm.executeWindow.close();
        };
        vm.executeCancel = function () {
            vm.executeWindow.close();
        };

        vm.paint = new Paint();
        vm.paint.init({
            el: '#graph',
            elScroll: '#graphScroll',
            height: 600,
            width: $('#graphScroll').width()
        });
        window.paint = vm.paint;
        vm.paint.initScroll();
        vm.paint._paper.on('cell:pointerdblclick', function (cellView) {
            if (!cellView.model instanceof joint.shapes.node.flow) return;
            if (cellView.model.prop('expanded')) {
                cellView.model.collapseFlow(vm.paint._graph);
                vm.paint.format({
                    rankDir: "TB",
                    marginX: 50,
                    marginY: 50
                });
                refreshColor();
            } else {
                var workflowId = cellView.model.prop('workflowId');
                workflowService.workflow(workflowId).then(function (data) {
                    var paint = JSON.parse(data.graph);
                    var graph = new joint.dia.Graph;
                    graph.fromJSON(paint.graph);
                    cellView.model.expandFlow(vm.paint._graph, graph);
                    vm.paint.format({
                        rankDir: "TB",
                        marginX: 50,
                        marginY: 50
                    });
                    refreshColor();
                });
            }
        });
        vm.paint._paper.setInteractivity(false);
        vm.showExecuteGraph = false;
        vm.disabledEl = new Set;
        $('#cellMenu').kendoContextMenu({
            orientation: 'vertical',
            target: '#graphScroll',
            animation: {
                open: {effects: "fadeIn"},
                duration: 500
            },
            select: function (evt) {
                if (!disabledElement) return;
                var paths = [];
                var parent = disabledElement;
                do {
                    paths.push(parent.get('attrs').text.text);
                    parent = vm.paint._graph.getCell(parent.get('parent'));
                } while (parent);
                paths.reverse();
                switch (evt.item.id) {
                    case "enable":
                        vm.disabledEl.delete(paths.join('.'));
                        break;
                    case "disable":
                        var i = 1;
                        var canDisable = true;
                        do {
                            if (vm.disabledEl.has(paths.slice(0, i).join("."))) {
                                canDisable = false;
                                break;
                            }
                            i++;
                        } while (i < paths.length - 1)
                        if (canDisable) {
                            vm.disabledEl.add(paths.join('.'));
                            for (var el of vm.disabledEl) {
                                if (el.startsWith(paths.join('.') + "."))
                                    vm.disabledEl.delete(el)
                            }
                        }
                        break;
                }
                refreshColor();
            }
        });
        function refreshColor() {
            vm.paint.resetColor();
            for (var elName of vm.disabledEl) {
                if (vm.paint.getElementByPath(elName))
                    vm.paint.setNodeColor(vm.paint.getElementByPath(elName).id, 'red');
            }
        }

        function gatherDisableElements() {
            var elements = new Map;
            vm.disabledEl.forEach(function (element) {
                var path = element.split(".");
                if (path.length == 1) {
                    elements.set(element, element);
                } else {
                    var i = 0;
                    var value = null;
                    do {
                        if (i > 0) {
                            if (i < path.length - 1) {
                                var preValue = value;
                                for (var child of value.children) {
                                    if (child.id == path.slice(0, i + 1).join(".")) {
                                        value = child;
                                    }
                                }
                                if (value == preValue) {
                                    var tmp = {id: path.slice(0, i + 1).join("."), children: []};
                                    value.children.push(tmp);
                                    value = tmp;
                                }
                            } else {
                                value.children.push(path.slice(0, i + 1).join("."));
                            }
                        } else if (i == 0) {
                            if (elements.has(path.slice(0, i + 1).join("."))) {
                                value = elements.get(path.slice(0, i + 1).join("."));
                            } else {
                                value = {id: path.slice(0, i + 1).join("."), children: []};
                                elements.set(path.slice(0, i + 1).join("."), value);
                            }
                        }
                        i++;
                    } while (i < path.length);
                }
            });
            var result = [];
            for (var node of elements.values()) {
                result.push(node);
            }
            return result;
        }

        var disabledElement = null;
        vm.paint._paper.on('blank:contextmenu', function () {
            $("#cellMenu").data('kendoContextMenu').open();
        });
        vm.paint._paper.on('cell:contextmenu', function (cellView) {
            disabledElement = cellView.model;
        });
        function loadSelectedWorkflow() {
            var idMap = new Map();
            for (var id of vm.selectedWorkflow) {
                vm.currentWorklfows.forEach(function (item) {
                    if (item.workflowId == id) {
                        idMap.set(id, item);
                    }
                })
            }
            ;
            return idMap;
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
