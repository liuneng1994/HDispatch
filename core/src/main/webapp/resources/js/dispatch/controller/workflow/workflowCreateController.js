(function () {
    'use strict';
    angular.module('dispatch').controller('workflowCreateController', ['$window', '$scope', 'workflowService', 'paintService', workflowCreateController]);
    function workflowCreateController($window, $scope, workflowService, paintService) {
        var vm = this;
        vm.workflow = {};
        vm.newJob = new Job();
        vm.themes = {};
        vm.layers = {};
        vm.jobLayers = [];
        vm.jobSources = [];
        vm.paint = new Paint('edit');
        paint.initScroll();
        paint.initEdit();
        paint.initHotKey();


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
            if (vm.paint.addJobNode(vm.newJob, 100, 100) == -1) {
                $window.alert("任务名称已存在");
                return;
            }
            vm.newJob = angular.copy(vm.newJob);
            vm.resetWindow();
        };

        vm.deleteJob = function () {
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
            vm.paint.format();
        }

        function Job() {
            this.themeId = new Number();
            this.layerId = 0;
            this.name = '';
            this.type = 'job';
            this.jobSource = new Number();
            this.dept = [];
        }

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