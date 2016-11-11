/**
 * Created by liuneng on 2016/9/22.
 */
(function () {
    'use strict';
    angular.module('dispatch').controller('workflowUpdateController', ['$timeout', '$window', '$scope', 'workflowService', workflowUpdateController]);
    function workflowUpdateController($timeout, $window, $scope, workflowService) {
        var vm = this;
        vm.workflow = {};
        vm.newJob = new Job();
        vm.themes = [];
        vm.optThemes = [];
        vm.layers = [];
        vm.jobLayers = [];
        vm.jobSources = [];
        vm.paint = new Paint('edit');
        window.paint = vm.paint;
        vm.paint.init({
            el: '#graph',
            elScroll: '#graphScroll',
            height: 600,
            width: $('body').width()
        });
        vm.paint.initScroll();
        vm.paint.initEdit();
        vm.paint.initHotKey();
        vm.themeChange = function () {
            vm.workflow.layerId = null;
            refreshLayers('layers', vm.workflow.themeId);
        };

        vm.layerChange = function () {
            if (vm.newJob.type == 'job') {
                vm.newJob.jobSource = null;
                refreshJobs('jobSources', vm.workflow.themeId, vm.workflow.layerId);
            }
        };

        vm.jobTypeChange = function() {
            vm.jobSources = [];
            vm.layerChange();
            vm.jobLayerChange();
        };
        vm.jobThemeChange = function () {
            vm.newJob.layerId = null;
            refreshLayers('jobLayers', vm.newJob.themeId);
        };

        vm.jobLayerChange = function () {
            if (vm.newJob.type == 'flow') {
                vm.newJob.jobSource = null;
                refreshFlows('jobSources', vm.newJob.themeId, vm.newJob.layerId);
            }
        };


        refreshThemes();
        refreshOptThemes();
        init();
        var jobPosition = {x: 100, y: 100};
        vm.createJob = function () {
            if (vm.paint.addJobNode(vm.newJob, jobPosition.x, jobPosition.y) == -1) {
                window.alert("任务名称已存在");
                return;
            }
            vm.newJob = angular.copy(vm.newJob);
            vm.resetWindow();
        };
        vm.format = function () {
            vm.paint.format();
        };

        vm.deleteJob = function () {
        };
        vm.save = function () {
            var workflow = {};
            workflow.workflowId = vm.workflow.workflowId;
            workflow.themeId = Number(vm.workflow.themeId);
            workflow.layerId = Number(vm.workflow.layerId);
            workflow.name = vm.workflow.workflowName;
            workflow.description = vm.workflow.description;
            workflow.jobs = [];
            for (var job of vm.paint.jobs.jobs.values()) {
                var newJob = {};
                newJob.workflowId = vm.workflow.workflowId;
                newJob.workflowJobId = job.name;
                newJob.jobSource = job.jobSource;
                newJob.jobType = job.type || 'job';
                newJob.parentsJobId = vm.paint.jobs.getDeptNameByName(job.name).join(',');
                workflow.jobs.push(newJob);
            }

            var graphJson = vm.paint.toJSON();
            workflow.graph = JSON.stringify(graphJson);
            workflowService.updateWorkflow(workflow).then(function (data) {
                kendo.ui.showDialog({
                    title: '生成新任务流',
                    width: 400,
                    message: '任务流保存成功,是否立即生成',
                    buttons: [{
                        text: "是",
                        type: 'success',
                        click: function(e) {
                            e.dialog.destroy();
                            e.deferred.resolve({
                                button: "yes"
                            });
                        }
                    }, {
                        text: "否",
                        type: 'danger',
                        click: function(e) {
                            e.dialog.destroy();
                            e.deferred.resolve({
                                button: "no"
                            });
                        }
                    }]
                }).done(function(e) {
                    if (e.button == 'yes') {
                        vm.generateWorkflow();
                    }
                });
            }, function (data) {
                window.alert(data);
            });
        };

        vm.generateWorkflow = function () {
            workflowService.generateWorkflow(vm.workflow.workflowId).then(function (data) {
                $window.alert(data);
            }, function (data) {
                $window.alert(data);
            });
        }

        vm.resetWindow = function () {
            vm.jobWindow.close();
            vm.newJob.name = null;
        };

        function Job() {
            this.themeId = new Number();
            this.layerId = 0;
            this.name = '';
            this.type = 'job';
            this.jobSource = new Number();
            this.dept = [];
        }

        $('#blankMenu').kendoContextMenu({
            orientation: 'vertical',
            animation: {
                open: {effects: "fadeIn"},
                duration: 500
            },
            select: function (evt) {
                if (evt.item.id == "createJob") {
                    vm.jobWindow.center().open();
                }
            }
        });

        vm.paint._paper.on('cell:contextmenu', function (cellView, evt, x, y) {
            $("#blankMenu").data('kendoContextMenu').close();
        });

        vm.paint._paper.on('cell:pointerdblclick', function (cellView) {
            if (!cellView.model instanceof joint.shapes.node.flow) return;
            if (cellView.model.prop('expanded')) {
                cellView.model.collapseFlow(vm.paint._graph);
            } else {
                var workflowId = cellView.model.prop('workflowId');
                workflowService.workflow(workflowId).then(function (data) {
                    var paint = JSON.parse(data.graph);
                    var graph = new joint.dia.Graph;
                    graph.fromJSON(paint.graph);
                    cellView.model.expandFlow(vm.paint._graph, graph);
                });
            }
        });

        vm.paint._paper.on('blank:contextmenu', function (evt, x, y) {
            jobPosition.x = x;
            jobPosition.y = y;
        });

        return vm;

        function init() {
            var workflowId = $window.sessionStorage["workflowId"];
            if (isNaN(workflowId)) {
                window.location = "/dispatch/workflow/workflow_list.html";
            }
            vm.workflow.workflowId = workflowId;
            workflowService.workflow(workflowId).then(function (data) {
                refreshLayers('layers', data.themeId);
                vm.workflow.themeId = data.themeId;
                $timeout(function () {
                    vm.workflow.layerId = data.layerId;
                    vm.layerChange();
                }, 200);

                vm.workflow.workflowName = data.name;
                vm.workflow.description = data.description;
                vm.workflow.graph = data.graph;
                initGraph();
            }, function (data) {
                alert(data);
            })
        }

        function initGraph() {
            vm.paint.parse(vm.workflow.graph);
        }

        function refreshThemes() {
            workflowService.themes().then(function (data) {
                vm.themes = data;
            });
        }

        function refreshOptThemes() {
            workflowService.operateThemes().then(function (data) {
                vm.optThemes = data;
            });
        }

        function refreshLayers(layers, themeId) {
            workflowService.layers(themeId).then(function (data) {
                vm[layers] = data;
            });
        }

        function refreshJobs(jobs, themeId, layerId) {
            if (themeId && layerId) {
                workflowService.jobs(themeId, layerId).then(function (data) {
                    vm[jobs] = data;
                });
            }
        }

        function refreshFlows(jobs, themeId, layerId) {
            workflowService.query({
                themeId: themeId,
                layerId: layerId,
                workflowName: '',
                description: '',
                page: 1,
                pageSize: 100
            }).then(function (data) {
                data = data.rows;
                var flows = [];
                if (data.length)
                    data.forEach(function (flow) {
                        var job = {};
                        job.jobId = flow.workflowId;
                        job.jobName = flow.name;
                        flows.push(job);
                    });
                vm[jobs] = flows;
            });
        }
    }
})();