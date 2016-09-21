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
        vm.paperName = 'create'
        vm.graph = new joint.dia.Graph;
        vm.paper = wfDiaService.newPaper($('#graph').parent().width(), 800, vm.graph, '#graph');
        vm.jobStore = wfDiaService.newJobStore();
        vm.graphTool = wfDiaService.newGraphTool(vm.paper,vm.graph);


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
                $window.alert("job名称已存在");
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
            graphJson = graphJson.cells.filter(function(cell) {
                if (cell.type == 'link') {
                    return false;
                } else {
                    return true;
                }
            })
            workflowService.createWorkflow(workflow).then(function (data) {
                vm.workflow.workflowId = parseInt(data);
                workflowService.saveGraph(parseInt(data), graphJson).then(function (data) {
                    $window.alert('创建成功');
                }, function (data) {
                    $window.alert('创建失败');
                });
            }, function (data) {
                $window.alert(data);
            });
        };

        vm.generateWorkflow = function() {
            workflowService.generateWorkflow(vm.workflow.workflowId).then(function(data){
                $window.alert(data);
            },function(data){
                $window.alert(data);
            });
        }

        vm.resetWindow = function () {
            vm.jobWindow.close();
            vm.newJob.name = null;
        };

        vm.format = function() {
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

        vm.paper.on('cell:pointerclick', function (cellView, event) {
            vm.graphTool.select(cellView);
        });

        vm.paper.on('cell:pointerdown', vm.graphTool.linkNode.pointerdown);
        vm.paper.on('cell:pointermove', vm.graphTool.linkNode.pointermove);
        vm.paper.on('cell:pointerup', vm.graphTool.linkNode.pointerup);

        vm.graph.on('change:position', vm.graphTool.linkNode.changePosition);
        vm.graph.on('add', function (cell) {
            if (cell instanceof joint.dia.Element) {
                var job = cell.prop('job');
                if (job) {
                    vm.jobStore.add(job);
                }
            }
            if (cell instanceof joint.dia.Link && cell.getSourceElement() && cell.getTargetElement()) {
                var sourceName = cell.getSourceElement().prop('job/name');
                var targetName = cell.getTargetElement().prop('job/name');
                if (sourceName && targetName) {
                    vm.jobStore.addJobDept(targetName, sourceName);
                }
            }
        });
        vm.graph.on('remove', function (cell) {
            if (cell instanceof joint.dia.Element) {
                var job = cell.prop('job');
                if (job) {
                    vm.jobStore.remove(job);
                }
            }
            if (cell instanceof joint.dia.Link && cell.get('source') instanceof joint.dia.Element && cell.get('target') instanceof joint.dia.Element) {
                var sourceName = cell.get('source').prop('job/name');
                var targetName = cell.get('target').prop('job/name');
                if (sourceName && targetName) {
                    vm.jobStore.removeJobDept(targetName, sourceName);
                }
            }
        });
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