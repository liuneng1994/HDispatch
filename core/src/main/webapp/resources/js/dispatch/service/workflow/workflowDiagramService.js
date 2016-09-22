/**
 * Created by 刘能 on 2016/9/16.
 */
(function () {
    angular.module("dispatch").factory('workflowDiagramService', workflowDiagramService);
    function workflowDiagramService() {
        var defaultLinkAttrs = {
            '.connection': {
                stroke: '#333333',
                'stroke-width': 3
            },
            '.marker-target': {
                fill: '#333333',
                d: 'M 10 0 L 0 5 L 10 10 z'
            }
        };
        var graphStore = {};
        var paperStore = {};

        /**
         * 图像工具
         * @param paper 画布对象
         * @param graph graph对象
         * @constructor 图像工具构造函数
         */
        function GraphTool(paper, graph) {
            var paper = paper;
            var graph = graph;
            var selected = null;
            var select = function (cellView) {
                if (cellView instanceof joint.dia.ElementView) {
                    _.each(graph.getElements(), function (element) {
                        var elementView = paper.findViewByModel(element);
                        if (elementView instanceof joint.dia.ElementView) {
                            elementView.unhighlight();
                        }
                    });
                    cellView.highlight();
                    selected = cellView.model;
                }
            };
            var createJobNode = function (job) {
                var node = new joint.shapes.basic.Rect({
                    position: {x: 100, y: 30},
                    size: {width: job.name.length * 7 + 20 > 100 ? job.name.length * 7 + 20 : 100, height: 45},
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
                graph.addCell(node);
            };
            var deleteJobNode = function () {
                if (selected) {
                    graph.removeLinks(selected);
                    selected.remove();
                }
            };
            var connect = function (source, target) {
                if (source && target)
                    graph.addCell(new joint.dia.Link({
                            source: {id: source.id},
                            target: {id: target.id},
                            attrs: defaultLinkAttrs
                        }
                    ));
            };
            return {
                /**
                 * 选中元素
                 * @param cellView 元素视图
                 */
                select: select,
                /**
                 * 创建任务节点
                 * @param job
                 */
                createJobNode: createJobNode,
                /**
                 * 删除选中的任务节点
                 */
                deleteJobNode: deleteJobNode,
                /**
                 * 连接任务节点
                 * @param source 源节点
                 * @param target 目标节点
                 */
                connect: connect,
                linkNode: (function () {
                    var draw = false;
                    var source = null;
                    var target = null;
                    var tempLink = new joint.dia.Link({
                        source: {x: 0, y: 0},
                        target: {x: 0, y: 0},
                        attrs: defaultLinkAttrs
                    });
                    return {
                        pointerdown: function (cellView, event, x, y) {
                            if (event.target.nodeName == 'tspan') {
                                draw = true;
                                tempLink.set('source', cellView.model);
                                source = cellView.model;
                                tempLink.set('target', {x: x, y: y});
                                tempLink.addTo(graph);
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
                                var elements = paper.findViewsFromPoint(targetPoint);
                                if (elements.length && elements[0] instanceof joint.dia.ElementView) {
                                    if (elements[0].model.id != source.id) {
                                        if (!isConnected(source, elements[0].model)) {
                                            target = elements[0].model;
                                            connect(source, target);
                                        }
                                    }
                                    target = elements[0].model;
                                }
                                tempLink.remove();
                                tempLink = new joint.dia.Link({
                                        source: {x: 0, y: 0},
                                        target: {x: 0, y: 0},
                                        attrs: defaultLinkAttrs
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
                })()

            };

            /**
             * 判断任务节点是否已连接
             * @param source 源节点
             * @param target 目标结点
             * @return Boolean 连接返回true，未连接返回false
             */
            function isConnected(source, target) {
                if (!source || !target) return false;
                var links = graph.getConnectedLinks(source);
                if (links.length) {
                    for (var i = 0; i < links.length; i++) {
                        if ((links[i].getTargetElement() && links[i].getTargetElement().id == target.id) ||
                            (links[i].getTargetElement() && links[i].getSourceElement().id == target.id))
                            return true;
                    }
                }
                return false;
            }
        }

        return {
            setGraph: function (name, graph) {
                graphStore[name] = graph;
            },
            setPaper: function (name, paper) {
                paperStore[name] = paper;
            },
            graph: function (name) {
                return graphStore[name];
            },
            paper: function (name) {
                return paperStore[name];
            },
            /**
             * 创建新的画布
             * @param width 画布宽度
             * @param height 画布高度
             * @param model 画布绑定的graph对象
             * @param elementSelector 画布所在元素的选择器
             * @return 新的画布对象
             */
            newPaper: function (width, height, model, elementSelector) {
                return new joint.dia.Paper({
                    el: $(elementSelector),
                    width: width,
                    height: height,
                    model: model,
                    gridSize: 1
                });
            },
            newGraphTool: function (paper, graph) {
                return new GraphTool(paper, graph);
            },
            newJobStore: function () {
                return {
                    // 以name，value的形式存储， job含有name，jobSource和dept（依赖job的数组）
                    jobs: {},
                    get: function (name) {
                        return this.jobs[name];
                    },
                    add: function (job) {
                        this.jobs[job.name] = job;
                    },
                    remove: function (name) {
                        delete this.jobs[name];
                    },
                    contains: function (name) {
                        return this.get(name) != undefined;
                    },
                    addJobDept: function (name, deptName) {
                        if (!this.hasJobDept(name, deptName)) {
                            this.get(name).dept.push(deptName);
                            return true;
                        }
                        return false;
                    },
                    removeJobDept: function (name, deptName) {
                        if (this.hasJobDept(name, deptName)) {
                            this.get(name).dept = _.without(this.get(name).dept, deptName);
                        }
                    },
                    hasJobDept: function (name, deptName) {
                        var has = false;
                        _.each(this.get(name).dept, function (name) {
                            if (name == deptName) has = true;
                        });
                        return has;
                    },
                    layer: function () {
                        var layer = {};

                        function computeLayer(name) {
                            if (!this.jobs[name].dept.length) {
                                return 1;
                            } else {
                                var deptLayers = [];
                                this.jobs[name].dept.forEach(function (dept) {
                                    deptLayers.push(computeLayer(dept));
                                })
                                return _.max(deptLayers) + 1;
                            }
                        }

                        for (var job in this.jobs) {
                            layer[job] = computeLayer(job);
                        }
                        return layer;
                    }
                };
            },
            autoFormat: function (jobStore) {
                return jobStore.layer();
            },
            bindEvent: function(vm) {
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
            }
        }
    }
})()