(function () {
    'use strict';
    angular.module('dispatch').controller('workflowCreateController', ['$window', '$scope', 'workflowService', workflowCreateController]);
    function workflowCreateController($window, $scope, workflowService) {
        var vm = this;
        vm.workflow = {}
        vm.themes = {};
        vm.layers = {};
        vm.graph = new joint.dia.Graph;
        vm.paper = initWorkflowPaper(1111, 500, vm.graph, '#graph');

        vm.paper.on('cell:pointerclick', function (cellView, event) {
                var cell = cellView.model;
                vm.graphTool.toggleNode(cellView);
                vm.graphTool.mark(cellView);
            }
        );

        refreshThemes();
        $scope.$watch('vm.workflow.themeId', refreshLayers);
        vm.graphTool = (function () {
            var source = null;
            var target = null;
            var current = null;

            function setSource(s) {
                source = s;
            }

            function setTarget(t) {
                target = t;
            }

            function select(cell) {
                current = cell;
            }

            return {
                toggleNode: function (cell) {
                    if (cell.attr('rect/stroke') == 'black') {
                        cell.attr('rect/stroke', 'red');
                        select(cell);
                    }
                    else
                        cell.attr('rect/stroke', 'black');
                },
                mark: function (cell) {
                    if (source == null && target == null) {
                        setSource(source);
                    } else if (source != null && source.id == cell.id) {
                        setSource(null);
                    } else if (source != null && target == null) {
                        setTarget(cell);
                        if (!vm.graphTool.isConnected(source, target))
                            vm.graphTool.connect(source, target);
                        vm.graphTool.toggleNode(source);
                        vm.graphTool.toggleNode(target);
                        setSource(null);
                        setTarget(null);
                    }
                },
                createJobNode: function (name) {
                    var node = new joint.shapes.basic.Rect({
                        position: {x: 100, y: 30},
                        size: {width: 100, height: 30},
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
                            text: {text: name, fill: 'black'}
                        }
                    });
                    vm.graph.addCell(node);
                },
                connect: function (source, target) {
                    if (source && target)
                        console.log(source, target);
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
                isConnected: function (source, target) {
                    var links = vm.graph.getConnectedLinks(source);
                    if (links.length) {
                        for (var i = 0; i < links.length; i++) {
                            if (links[i].getTargetElement().id == target.id)
                                return true;
                        }
                    }
                    return false;
                }
            }
        })();
        return vm;

        function refreshThemes() {
            workflowService.themes().then(function (data) {
                vm.themes = data;
            });
        }

        function refreshLayers() {
            workflowService.layers(vm.workflow.themeId).then(function (data) {
                vm.layers = data;
            });
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