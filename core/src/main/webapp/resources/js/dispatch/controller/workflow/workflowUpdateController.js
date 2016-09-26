/**
 * Created by liuneng on 2016/9/22.
 */
(function () {
    'use strict';
    angular.module('dispatch').controller('workflowUpdateController', ['$timeout', '$window', '$scope', 'workflowService', 'workflowDiagramService', workflowUpdateController]);
    function workflowUpdateController($timeout, $window, $scope, workflowService, wfDiaService) {
        var vm = this;
        vm.workflow = {};
        vm.newJob = new Job();
        vm.themes = {};
        vm.layers = [];
        vm.jobLayers = [];
        vm.jobSources = [];
        vm.graph = new joint.dia.Graph;
        vm.paper = wfDiaService.newPaper(5000, 5000, vm.graph, '#graph');
        vm.jobStore = wfDiaService.newJobStore();
        vm.graphTool = wfDiaService.newGraphTool(vm.paper, vm.graph);
        vm.themeChange = function (themeId) {
            vm.workflow.layerId = null;
            refreshLayers('layers', themeId);
        };
        vm.jobThemeChange = function () {
            vm.newJob.layerId = null;
            refreshLayers('jobLayers', vm.newJob.themeId);
        };
        vm.jobLayerChange = function () {
            vm.newJob.jobSource = null;
            refreshJobs('jobSources', vm.newJob.themeId, vm.newJob.layerId);
        };

        wfDiaService.bindEvent(vm);
        refreshThemes();
        init();

        vm.createJob = function () {
            if (vm.jobStore.contains(vm.newJob.name)) {
                $window.alert("job名称已存在");
                return;
            }
            vm.graphTool.createJobNode(vm.newJob);
            vm.newJob = angular.copy(vm.newJob);
            vm.resetWindow();
        };

        vm.deleteJob = function () {
            vm.graphTool.deleteJobNode();
        };

        vm.save = function () {
            var workflow = {};
            workflow.workflowId = vm.workflow.workflowId;
            workflow.themeId = vm.workflow.themeId;
            workflow.layerId = vm.workflow.layerId;
            workflow.name = vm.workflow.workflowName;
            workflow.description = vm.workflow.description;
            workflow.jobs = [];
            for (var jobName in vm.jobStore.jobs) {
                var job = vm.jobStore.jobs[jobName];
                var newJob = {};
                newJob.workflowJobId = job.name;
                newJob.workflowId = workflow.workflowId;
                newJob.jobSource = job.jobSource;
                newJob.parentsJobId = job.dept.join(',');
                workflow.jobs.push(newJob);
            }

            var graphJson = vm.graph.toJSON();
            graphJson.jobStore = vm.jobStore;
            workflow.graph = JSON.stringify(graphJson);
            workflowService.updateWorkflow(workflow).then(function (data) {
                vm.workflow.workflowId = parseInt(data);
                $window.alert("save success");
            }, function (data) {
                $window.alert(data);
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

        vm.format = function () {
            var result = vm.graphTool.autoFormat(vm.jobStore);
            console.log(result);
        };

        function Job() {
            this.themeId = new Number();
            this.layerId = 0;
            this.name = '';
            this.dept = [];
        }

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
                }, 200);

                vm.workflow.workflowName = data.name;
                vm.workflow.description = data.description;
                vm.workflow.graph = data.graph;
                initGraph();
                vm.jobStore.jobs = JSON.parse(vm.workflow.graph).jobStore.jobs;

            }, function (data) {
                alert(data);
            })
        }

        function initGraph() {
            vm.graph.fromJSON(JSON.parse(vm.workflow.graph));
        }

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

        function refreshJobs(jobs, themeId, layerId) {
            workflowService.jobs(themeId, layerId).then(function (data) {
                vm[jobs] = data;
            })
        }
    }
})()