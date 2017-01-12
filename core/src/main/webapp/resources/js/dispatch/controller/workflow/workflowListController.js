/**
 * Created by liuneng on 2016/9/21.
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
                                message: $l('hap.tip.success')
                            });
                        }, function (data) {
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
                },
                {
                    field: "",
                    title: $l('hap.action'),
                    attributes: {style: "padding-top:0;padding-bottom:0"},
                    width: 200,
                    template: function (item) {
                        var html = '';
                        var disabled = "disabled";
                        if (hasOperatePermission(item.themeId)) {
                            disabled = "";
                        }
                        var html = "<button style='margin-left:4px' class='btn btn-info' " + disabled + " ng-click='vm.edit(" + item.workflowId + ")'>编辑</button>";
                        html += "<button style='margin-left:4px' class='btn btn-info' " + " ng-click='vm.showWorkflow(" + item.workflowId + ")'>查看</button>";
                        html += "<button style='margin-left:4px' class='btn btn-info' " + " ng-click='vm.showDeptGraph(" + '"' + item.project + '"' + ")'>查看依赖</button>";
                        html += "<button style='margin-left:4px' class='btn btn-warning' " + disabled + " ng-click='vm.mutex(" + item.id + ")'>互斥</button>";
                        html += "<button style='margin-left:4px' class='btn btn-success' " + disabled + "  ng-click='vm.dependency(" + item.id + ")'>依赖</button>";
                        html += "<button style='margin-left:4px' class='btn btn-danger' " + disabled + "  ng-click='vm.delete(" + item.id + ")'>删除</button>"
                        html = "<div class='row'>" + html + "</div>";
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

        vm.resetQuery = function () {
            "use strict";
            vm.workflow.themeId = null;
            vm.workflow.layerId = null;
            vm.workflow.workflowName = '';
            vm.workflow.description = '';
            vm.layers = [];
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
        vm.showWorkflow = function (id) {
            workflowService.workflow(id).then(function (workflow) {
                kendo.ui.showDialog({
                    title: workflow.name,
                    width: 700,
                    message: $('#workflowDetail').html(),
                    buttons: [{
                        text: $l('hap.ok'),
                        type: 'success',
                        click: function (e) {
                            e.dialog.destroy();
                            e.deferred.resolve({
                                button: "yes"
                            });
                        }
                    }]
                });
                var paint = new Paint();
                paint.init({
                    el: '#graph',
                    elScroll: '#graphScroll',
                    height: 500,
                    width: 630
                });
                paint.initScroll();
                paint.parse(workflow.graph);
            });
        };

        vm.showDeptGraph = function (workflowName) {
            workflowService.deptGraph(workflowName).then(function (graph) {
                kendo.ui.showDialog({
                    title: workflowName,
                    width: 700,
                    message: $('#workflowDetail').html(),
                    buttons: [{
                        text: $l('hap.ok'),
                        type: 'success',
                        click: function (e) {
                            e.dialog.destroy();
                            e.deferred.resolve({
                                button: "yes"
                            });
                        }
                    }]
                });
                var paint = new Paint();
                paint.init({
                    el: '#graph',
                    elScroll: '#graphScroll',
                    height: 500,
                    width: 630
                });
                paint.initScroll();
                paint.fromDeptGraph(graph);
                paint.format();
                paint.setNodeColor(paint.jobs.getNodeId(workflowName),'yellow')
            });
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
