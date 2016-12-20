/**
 * Created by liuneng on 2016/9/21.
 */
(function () {
    'use strict';
    angular.module('dispatch').controller('workflowListController', ['$window', '$scope', 'workflowService', '$compile', function ($window, $scope, workflowService, $compile) {
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
        vm.gridOptions = {
            dataSource: {
                transport: {
                    read: function (options) {
                        vm.selectedWorkflow = [];
                        vm.workflow.page = options.data.page;
                        vm.workflow.pageSize = options.data.pageSize;
                        if (vm.workflow.themeId != undefined && isNaN(vm.workflow.themeId)) delete vm.workflow.themeId;
                        if (vm.workflow.themeId != undefined && isNaN(vm.workflow.layerId)) delete vm.workflow.layerId;
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
            columnMenu: true,
            reorderable: true,
            scrollable: true,
            editable: false,
            pageable: {
                pageSizes: [10, 20, 50, 100],
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
                    field: "name",
                    title: $l('hdispatch.workflow'),
                    width: 100
                },
                {
                    field: "theme",
                    title: $l('hdispatch.theme'),
                    width: 100
                },
                {
                    field: "layer",
                    title: $l('hdispatch.layer'),
                    width: 100
                },
                {
                    field: "description",
                    title: $l('hap.description'),
                    width: 200
                }]
        };

        vm.search = function () {
            $('#grid').data('kendoGrid').dataSource.page(1);
        };

        vm.themeChange = function (themeId) {
            vm.workflow.layerId = "";
            refreshLayers('layers', themeId);
        };

        function validateOperate() {
            var dataItems = $("#grid").data('kendoGrid').selectedDataItems();
            var names = [];
            for (var item of dataItems) {
                if (!hasOperatePermission(item.themeId))
                    names.push(item.name);
            }
            return names;
        }

        function getSelectWorkflows() {
            vm.selectedWorkflow = [];
            var checked = $('#grid').data('kendoGrid').selectedDataItems();
            checked.forEach(function (item) {
                vm.selectedWorkflow.push(item.workflowId);
            });
        }

        vm.showExpDetail = function () {
            kendo.ui.showDialog({
                title: 'cron',
                width: 1000,
                message: $('#expDetail').html(),
                buttons: [{
                    text: $l('hap.ok'),
                    type: 'info',
                    click: function (e) {
                        e.dialog.destroy();
                    }
                }]
            });
        }

        vm.cronSchedule = function () {
            var names = validateOperate();
            if (names.length) {
                kendo.ui.showWarningDialog({
                    message: names.join(', ') + $l('hdispatch.tip.noAuthority')
                });
                return;
            }
            vm.scheduleInfo = {};
            vm.cronExp = [0, '*', '*', '?', '*', '*'];
            getSelectWorkflows();
            vm.showScheduleGraph = false;
            var selectedFlows = loadSelectedWorkflow();
            vm.cronScheduleFlow = {};
            vm.cronScheduleFlow.loading = selectedFlows.size;
            if (vm.cronScheduleFlow.loading == 0) {
                return;
            }
            if (selectedFlows.size == 1) {
                vm.showScheduleGraph = true;
                for (var item of selectedFlows.values()) {
                    workflowService.queryScheduleInfo(item.name).then(function (data) {
                        console.log(data.split(' '));
                        vm.cronExp = data.split(' ');
                    });
                }
            }
            vm.cronScheduleFlow.flows = {};
            vm.cronScheduleFlow.name = '';
            vm.scheduleInfo.flows = {};
            vm.scheduleInfo.failureAction = 'finishCurrent';
            vm.scheduleInfo.concurrentOption = 'skip';
            for (var flow of selectedFlows.values()) {
                workflowService.workflow(flow.workflowId).then(function (data) {
                    vm.cronScheduleFlow.loading--;
                    if (vm.showScheduleGraph) {
                        vm.schedulePaint = new Paint();
                        vm.schedulePaint.init({
                            el: 'div.scheduleGraph',
                            elScroll: 'div.scheduleGraphScroll',
                            height: 400,
                            width: 500
                        });
                        vm.schedulePaint.initScroll();
                        vm.schedulePaint._paper.on('cell:pointerdblclick', function (cellView) {
                            if (!cellView.model instanceof joint.shapes.node.flow) return;
                            if (cellView.model.prop('expanded')) {
                                cellView.model.collapseFlow(vm.schedulePaint._graph);
                                vm.schedulePaint.format({
                                    rankDir: "TB",
                                    marginX: 50,
                                    marginY: 50
                                });
                                refreshColor(vm.schedulePaint);
                            } else {
                                var workflowId = cellView.model.prop('workflowId');
                                workflowService.workflow(workflowId).then(function (data) {
                                    var paint = JSON.parse(data.graph);
                                    var graph = new joint.dia.Graph;
                                    graph.fromJSON(paint.graph);
                                    cellView.model.expandFlow(vm.schedulePaint._graph, graph);
                                    vm.schedulePaint.format({
                                        rankDir: "TB",
                                        marginX: 50,
                                        marginY: 50
                                    });
                                    refreshColor(vm.schedulePaint);
                                });
                            }
                        });
                        vm.schedulePaint._paper.setInteractivity(false);
                        vm.disabledEl = new Set;
                        window.setTimeout(function () {
                            vm.schedulePaint.parse(data.graph);
                            vm.schedulePaint.format();
                            for (var link of vm.schedulePaint._graph.getLinks()) {
                                link.attr('.link-tools/display', 'none');
                            }
                        }, 500);
                    }
                    if (data.projectName && data.flowId) {
                        vm.cronScheduleFlow.flows[data.workflowId] = {};
                        vm.cronScheduleFlow.flows[data.workflowId].projectName = data.projectName;
                        vm.cronScheduleFlow.flows[data.workflowId].flowId = data.flowId;
                        vm.cronScheduleFlow.name += (data.name + '  ');
                    } else {
                        vm.notification.show({
                            title: data.name,
                            message: $l('hdispatch.tip.noflow')
                        }, "error");
                    }
                    if (vm.cronScheduleFlow.loading == 0 && vm.cronScheduleFlow.name.length == 0) {
                        vm.scheduleWindow.close();
                    }
                })
            }
            ;
            kendo.ui.showDialog({
                title: 'cron',
                width: 800,
                message: $('#cronScheduleWindow').html(),
                buttons: [{
                    text: $l('hap.ok'),
                    type: 'success',
                    click: function (e) {
                        e.dialog.destroy();
                        e.deferred.resolve({
                            button: "yes"
                        });
                    }
                }, {
                    text: $l('hap.cancel'),
                    type: 'danger',
                    click: function (e) {
                        e.dialog.destroy();
                        e.deferred.resolve({
                            button: "no"
                        });
                    }
                }]
            }).done(function (e) {
                if (e.button == 'yes') {
                    vm.cronScheduleSubmit();
                }
            });
            $compile($('div.modal-body table'))($scope);
            $('#scheduleTab').data('kendoTabStrip').select(1);
        };
        vm.cronScheduleSubmit = function () {
            vm.scheduleInfo.successEmailsOverride = true;
            vm.scheduleInfo.failureEmailsOverride = true;
            for (var id in vm.cronScheduleFlow.flows) {
                if (vm.showScheduleGraph) vm.scheduleInfo.disabled = JSON.stringify(gatherDisableElements());
                vm.scheduleInfo.projectName = vm.cronScheduleFlow.flows[id].projectName;
                vm.scheduleInfo.flowId = vm.cronScheduleFlow.flows[id].flowId;
                vm.scheduleInfo.cronExpression = vm.cronExp.join(' ');
                var arg = angular.copy(vm.scheduleInfo);
                workflowService.cronScheduleWorkflow(arg).then(function (data) {
                    vm.notification.show({
                        message: data || $l('hdispatch.workflow.scheduleSuccess')
                    }, "upload-success");
                }, function (data) {
                    vm.notification.show({
                        title: '',
                        message: data || $l('hdispatch.workflow.scheduleFail')
                    }, "error");
                });
            }
        };

        vm.execute = function () {
            var names = validateOperate();
            if (names.length) {
                kendo.ui.showWarningDialog({
                    message: names.join(', ') + $l('hdispatch.tip.noAuthority')
                });
                return;
            }
            $('#executeTab').data('kendoTabStrip').select(0);
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
            vm.executeWindow.center().setOptions({position: {left: vm.executeWindow.options.position.left, top: 100}});
            vm.executeWindow.open();
            if (selectedFlows.size == 1) vm.showExecuteGraph = true;
            for (var flow of selectedFlows.values()) {
                workflowService.workflow(flow.workflowId).then(function (data) {
                    vm.executeInfo.loading--;
                    if (vm.showExecuteGraph) {
                        window.setTimeout(function () {
                            vm.disabledEl = new Set;
                            vm.paint.parse(data.graph);
                            vm.paint.format();
                            for (var link of vm.paint._graph.getLinks()) {
                                link.attr('.link-tools/display', 'none');
                            }
                        }, 2000);
                    }
                    if (data.projectName && data.flowId) {
                        vm.executeInfo.flows[data.workflowId] = {};
                        vm.executeInfo.flows[data.workflowId].project = data.projectName;
                        vm.executeInfo.flows[data.workflowId].flow = data.flowId;
                        vm.executeInfo.name += (data.name + '  ');
                    } else {
                        vm.notification.show({
                            title: data.name,
                            message: $l('hdispatch.tip.noflow')
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
            vm.executeInfo.successEmailsOverride = true;
            vm.executeInfo.failureEmailsOverride = true;
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
        vm.resetQuery = function () {
            "use strict";
            vm.workflow.themeId = null;
            vm.workflow.layerId = null;
            vm.workflow.workflowName = '';
            vm.workflow.description = '';
            vm.layers = [];
        };

        vm.paint = new Paint();
        vm.paint.init({
            el: '#graph',
            elScroll: '#graphScroll',
            height: 400,
            width: 500
        });
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
                refreshColor(vm.paint);
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
                    refreshColor(vm.paint);
                });
            }
        });
        vm.paint._paper.setInteractivity(false);
        vm.showExecuteGraph = false;
        vm.disabledEl = new Set;

        vm.disable = function (paint) {
            for (var element of paint.selected) {
                var paths = [];
                var parent = element;
                do {
                    paths.push(parent.get('attrs').text.text);
                    parent = vm.paint._graph.getCell(parent.get('parent'));
                } while (parent);
                paths.reverse();
                var i = 1;
                var canDisable = true;
                do {
                    if (vm.disabledEl.has(paths.slice(0, i).join("."))) {
                        canDisable = false;
                        break;
                    }
                    i++;
                } while (i < paths.length - 1);
                if (canDisable) {
                    vm.disabledEl.add(paths.join('.'));
                    for (var el of vm.disabledEl) {
                        if (el.startsWith(paths.join('.') + "."))
                            vm.disabledEl.delete(el)
                    }
                }
            }
            refreshColor(paint);
        };
        vm.enable = function enable(paint) {
            for (var element of paint.selected) {
                var paths = [];
                var parent = element;
                do {
                    paths.push(parent.get('attrs').text.text);
                    parent = vm.paint._graph.getCell(parent.get('parent'));
                } while (parent);
                paths.reverse();
                vm.disabledEl.delete(paths.join('.'));
            }
            refreshColor(paint);
        };
        vm.disableAll = function disableAll(paint) {
            for (var element of paint._graph.getElements()) {
                var paths = [];
                var parent = element;
                do {
                    paths.push(parent.get('attrs').text.text);
                    parent = vm.paint._graph.getCell(parent.get('parent'));
                } while (parent);
                paths.reverse();
                var i = 1;
                var canDisable = true;
                do {
                    if (vm.disabledEl.has(paths.slice(0, i).join("."))) {
                        canDisable = false;
                        break;
                    }
                    i++;
                } while (i < paths.length - 1);
                if (canDisable) {
                    vm.disabledEl.add(paths.join('.'));
                    for (var el of vm.disabledEl) {
                        if (el.startsWith(paths.join('.') + "."))
                            vm.disabledEl.delete(el)
                    }
                }
            }
            refreshColor(paint);
        };
        vm.enableAll = function enableAll(paint) {
            for (var element of paint._graph.getElements()) {
                var paths = [];
                var parent = element;
                do {
                    paths.push(parent.get('attrs').text.text);
                    parent = vm.paint._graph.getCell(parent.get('parent'));
                } while (parent);
                paths.reverse();
                vm.disabledEl.delete(paths.join('.'));
            }
            refreshColor(paint);
        };

        function refreshColor(paint) {
            paint.resetColor();
            for (var elName of vm.disabledEl) {
                if (paint.getElementByPath(elName))
                    paint.setNodeColor(paint.getElementByPath(elName).id, 'red');
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
