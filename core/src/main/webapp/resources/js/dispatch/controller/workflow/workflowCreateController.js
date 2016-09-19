(function () {
    'use strict';
    angular.module('dispatch').controller('workflowCreateController', ['$window', '$scope', 'workflowService', workflowCreateController]);
    function workflowCreateController($window, $scope, workflowService) {
        var vm = this;
        vm.workflow = {};
        vm.newJob = new Job();
        vm.themes = {};
        vm.layers = {};
        vm.jobLayers = [];
        vm.jobSources = [];
        vm.graph = new joint.dia.Graph;
        vm.paper = initWorkflowPaper(1111, 500, vm.graph, '#graph');
        refreshThemes();
        $scope.$watch('vm.workflow.themeId', function () {
            refreshLayers('layers', vm.workflow.themeId);
        });
        $scope.$watch('vm.newJob.themeId', function () {
            refreshLayers('jobLayers', vm.newJob.themeId);
        });
        $scope.$watch('vm.newJob.layerId', function () {
            refreshJobs('jobSources', vm.newJob.themeId, vm.newJob.layerId);
        });


        vm.createJob = function () {
            vm.graphTool.createJobNode(vm.newJob);
            vm.newJob = new Job();
            vm.jobWindow.close();
        };

        vm.deleteJob = function () {
            vm.graphTool.deleteJobNode();
        }

        vm.save = function () {
            console.log(vm.jobStore.jobs);
        }

        vm.resetWindow = function () {
            vm.newJob = {};
            vm.jobLayers = [];
            vm.jobWindow.close();
        }

        function Job() {
            this.themeId = new Number();
            this.layerId = 0;
            this.name = '';
            this.jobSource = new Number();
            this.dept = [];
        }

        vm.jobStore = {
            // 以name，value的形式存储， job含有name，jobSource和dept（依赖job的数组）
            jobs: {},
            get: function (name) {
                return vm.jobStore.jobs[name];
            },
            add: function (job) {
                vm.jobStore.jobs[job.name] = job;
            },
            remove: function (name) {
                delete vm.jobStore.jobs[name];
            },
            contains: function (name) {
                return vm.jobStore.get(name) != undefined;
            },
            addJobDept: function (name, deptName) {
                if (!vm.jobStore.hasJobDept(name, deptName)) {
                    vm.jobStore.get(name).dept.push(deptName);
                    return true;
                }
                return false;
            },
            removeJobDept: function (name, deptName) {
                if (vm.jobStore.hasJobDept(name, deptName)) {
                    vm.jobStore.get(name).dept = _.without(vm.jobStore.get(name).dept, deptName);
                }
            },
            hasJobDept: function (name, deptName) {
                var has = false;
                _.each(vm.jobStore.get(name).dept, function (name) {
                    if (name == deptName) has = true;
                });
                return has;
            }
        }

        vm.graphTool = (function () {
            var selected = null;
            return {
                select: function (cellView) {
                    _.each(vm.graph.getElements(), function (element) {
                        var elementView = vm.paper.findViewByModel(element);
                        if (elementView instanceof joint.dia.ElementView) {
                            elementView.unhighlight();
                        }
                    });
                    if (cellView instanceof joint.dia.ElementView) {
                        cellView.highlight();
                        selected = cellView.model;
                    }
                },
                createJobNode: function (job) {
                    var node = new joint.shapes.basic.Rect({
                        position: {x: 100, y: 30},
                        size: {width: job.name.length * 7 + 20, height: 45},
                        attrs: {
                            rect: {
                                'stroke-width': '2',
                                'stroke-opacity': .7,
                                stroke: 'black',
                                rx: 3,
                                ry: 3,
                                fill: 'lightgray',
                                'fill-opacity': .5
                            },
                            text: {text: job.name, fill: 'black', cursor: 'crosshair'}
                        }
                    });
                    node.prop('job', job);
                    vm.graph.addCell(node);
                },
                deleteJobNode: function () {
                    vm.graph.removeLinks(selected);
                    selected.remove();
                },
                connect: function (source, target) {
                    if (source && target)
                        vm.graph.addCell(new joint.dia.Link({
                                source: source,
                                target: target,
                                attrs: {
                                    '.connection': {
                                        stroke: '#333333',
                                        'stroke-width': 3
                                    },
                                    '.marker-target': {
                                        fill: '#333333',
                                        d: 'M 10 0 L 0 5 L 10 10 z'
                                    }
                                }
                            }
                        ));
                },
                linkNode: (function () {
                    var draw = false;
                    var source = null;
                    var target = null;
                    var tempLink = new joint.dia.Link({
                            source: {x: 0, y: 0},
                            target: {x: 0, y: 0},
                            attrs: {
                                '.connection': {
                                    stroke: '#333333',
                                    'stroke-width': 3
                                },
                                '.marker-target': {
                                    fill: '#333333',
                                    d: 'M 10 0 L 0 5 L 10 10 z'
                                }
                            }
                        }
                    );
                    return {
                        pointerdown: function (cellView, event, x, y) {
                            if (event.target.nodeName == 'tspan') {
                                draw = true;
                                tempLink.set('source', cellView.model);
                                source = cellView.model;
                                tempLink.set('target', {x: x, y: y});
                                tempLink.addTo(vm.graph);
                            }
                        },
                        pointermove: function (cellView, event, x, y) {
                            if (draw) {
                                tempLink.set('target', {x: x, y: y});
                            }
                        },
                        pointerup: function (cellView, event) {
                            if (draw) {
                                var targetPoint = tempLink.get('target');
                                var elements = vm.paper.findViewsFromPoint(targetPoint);
                                if (elements.length && elements[0] instanceof joint.dia.ElementView) {
                                    if (elements[0].model.id != source.id) {
                                        if (!vm.graphTool.isConnected(source, elements[0].model)) {
                                            target = elements[0].model;
                                            vm.graphTool.connect(source, target);
                                        }
                                    }
                                    target = elements[0].model
                                }
                                tempLink.remove();
                                tempLink = new joint.dia.Link({
                                        source: {x: 0, y: 0},
                                        target: {x: 0, y: 0},
                                        attrs: {
                                            '.connection': {
                                                stroke: '#333333',
                                                'stroke-width': 3
                                            },
                                            '.marker-target': {
                                                fill: '#333333',
                                                d: 'M 10 0 L 0 5 L 10 10 z'
                                            }
                                        }
                                    }
                                );
                                draw = false;
                                source = null;
                                target = null;
                            }
                        },
                        changePosition: function (cell) {
                            if (draw) {
                                cell.set('position', cell.previous('position'));
                            }
                        }
                    }
                })(),
                isConnected: function (source, target) {
                    if (!source || !target) return false;
                    var links = vm.graph.getConnectedLinks(source);
                    if (links.length) {
                        for (var i = 0; i < links.length; i++) {
                            if ((links[i].getTargetElement() && links[i].getTargetElement().id == target.id) || (links[i].getTargetElement() && links[i].getSourceElement().id == target.id))
                                return true;
                        }
                    }
                    return false;
                }
            }
        })();

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

        function initWorkflowPaper(width, height, model, elemeneId) {
            return new joint.dia.Paper({
                el: $(elemeneId),
                width: width,
                height: height,
                model: model,
                gridSize: 1
            });
        }
    }
})()