(function () {
    'use strict';
    angular.module('dispatch').controller('workflowCreateController', ['$window', '$scope', 'workflowService', 'workflowDiagramService', workflowCreateController]);
    function workflowCreateController($window, $scope, workflowService, wfDiaService) {
        var vm = this;
        vm.workflow = {};
        vm.newJob = new Job();
        vm.themes = {};
        vm.layers = {};
        vm.jobLayers = [];
        vm.jobSources = [];
        vm.paperName = 'create';
        vm.graph = new joint.dia.Graph;
        vm.paper = wfDiaService.newPaper($('#graph').parent().width(), 800, vm.graph, '#graph');
        vm.jobStore = wfDiaService.newJobStore();
        vm.graphTool = wfDiaService.newGraphTool(vm.paper, vm.graph);


        refreshThemes();
        $scope.$watch('vm.workflow.themeId', function () {
            vm.workflow.layerId = null;
            refreshLayers('layers', vm.workflow.themeId);
        });
        $scope.$watch('vm.newJob.themeId', function () {
            vm.newJob.layerId = null;
            refreshLayers('jobLayers', vm.newJob.themeId);
        });
        $scope.$watch('vm.newJob.layerId', function () {
            vm.newJob.jobSource = null;
            refreshJobs('jobSources', vm.newJob.themeId, vm.newJob.layerId);
        });


        vm.createJob = function () {
            if (vm.jobStore.contains(vm.newJob.name)) {
                $window.alert("任务名称已存在");
                return;
            }
            console.log();
            vm.graphTool.createJobNode(vm.newJob);
            vm.newJob = angular.copy(vm.newJob);
            vm.resetWindow();
        };

        vm.deleteJob = function () {
            vm.graphTool.deleteJobNode();
        };

        vm.save = function () {
            var workflow = {};
            workflow.themeId = vm.workflow.themeId;
            workflow.layerId = vm.workflow.layerId;
            workflow.name = vm.workflow.workflowName;
            workflow.description = vm.workflow.description;
            workflow.jobs = [];
            for (var jobName in vm.jobStore.jobs) {
                var job = vm.jobStore.jobs[jobName];
                var newJob = {};
                newJob.workflowJobId = job.name;
                newJob.jobSource = job.jobSource;
                newJob.parentsJobId = job.dept.join(',');
                workflow.jobs.push(newJob);
            }

            var graphJson = vm.graph.toJSON();
            graphJson.jobStore = vm.jobStore;
            workflow.graph = JSON.stringify(graphJson);
            workflowService.createWorkflow(workflow).then(function (data) {
                vm.workflow.workflowId = parseInt(data);
                window.hdispatch.confirm("保存成功，是否立刻生成任务流").accept(vm.generateWorkflow);
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
        }

        function Job() {
            this.themeId = new Number();
            this.layerId = 0;
            this.name = '';
            this.jobSource = new Number();
            this.dept = [];
        }

        wfDiaService.bindEvent(vm);
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

        function refreshJobs(jobs, themeId, layerId) {
            workflowService.jobs(themeId, layerId).then(function (data) {
                vm[jobs] = data;
            })
        }
    }
})()