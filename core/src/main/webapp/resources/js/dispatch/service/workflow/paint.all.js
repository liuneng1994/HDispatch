/**
 * Created by hasee on 2016/10/13.
 */
var Paint = (function (mode) {
    'use strict';
    function Paint(mode) {
        this._paper = null;
        this._graph = null;
        this.scroller = null;
        this.jobs = new JobStorage();
        this.selected = [];
        switch (mode) {
            case 'edit' :
                this.editable = true;
                break;
        }
    }

    /**
     * 初始化函数
     * @param attr 初始化时提供的参数.
     *              el:画布所在元素的css选择器;
     *              elScroll: 滚动框的css选择器
     *              height(optional): 画布高度默认500;
     *              width(optional): 画布宽度默认500;
     *              gridSize: 网格的大小;
     */
    Paint.prototype.init = function (attr) {
        if (!attr) throw new ReferenceError("未设置初始化参数");
        var $this = this;
        if (!attr.el) throw new ReferenceError("画布所在元素选择器为空");
        if (!attr.elScroll) throw new ReferenceError("画布滚动框所在元素选择器为空");
        $(attr.elScroll).css('overflow', 'scroll');
        $(attr.elScroll).height(attr.height || 500);
        $(attr.elScroll).width(attr.width || 500);
        this._graph = new joint.dia.Graph;
        this._paper = new joint.dia.Paper({
            el: $(attr.el),
            width: attr.width * 5,
            height: attr.height * 5 || 500 * 5,
            model: $this._graph,
            gridSize: attr.gridSize || 10,
            drawGrid: true,
            interactive: $this.editable
        });
        $(attr.el).width(attr.width * 5 || 500 * 5);
        this.scroller = new Scroll(this._paper.options.width, this._paper.options.height);
        new Drag(attr.elScroll, this._paper);
        var highlightedCellViews = [];
        // 增加选中
        this._paper.on('cell:pointerclick', function (cellView, event) {
            if (event.ctrlKey && cellView.model.isElement() && !cellView.model.get('parent')) {
                if (!highlightedCellViews.includes(cellView)) {
                    cellView.highlight();
                    highlightedCellViews.push(cellView);
                    $this.selected.push(cellView.model);
                }
                else {
                    cellView.unhighlight();
                    highlightedCellViews.splice(highlightedCellViews.indexOf(cellView), 1);
                    $this.selected.splice($this.selected.indexOf(cellView.model), 1);
                }
            }
        });

        this._paper.on('blank:pointerclick', function (cellView) {
            for (var i = 0; i < highlightedCellViews.length; i++) {
                highlightedCellViews[i].unhighlight();
            }
            highlightedCellViews = [];
            $this.selected = [];
        });
    };

    Paint.prototype.initScroll = function () {
        var $this = this;
        this._paper.on('blank:mousewheel', function (e, x, y, delta) {
            if (e.altKey) {
                e = e || window.event;
                if (e.stopPropagation) e.stopPropagation();
                else e.cancelBubble = true;
                if (e.preventDefault) e.preventDefault();
                else e.returnValue = false;
                $this.scroller.zoom($this._paper, x, y, delta);
            }
        });
    };

    Paint.prototype.setNodeColor = function(name,color) {
        name = name.split('.');
        for (var element of this._graph.getElements()) {
            var index = name.length -1;
            if(element.get('attrs').text.text != name[index]) continue;
            var parentId = element.get('parent')
            while(parentId) {
                index--;
                if (this._graph.getCell(parentId).get('attrs').text.text != name[index]) continue;
                parentId = this._graph.getCell(parentId).get('parent');
            }
            if (index == 0) {
                element.attr('rect/fill',color);
                return;
            }
        }
    };

    Paint.prototype.format = function(opt) {
        opt = opt || {
                nodeSep: 50,
                edgeSep: 80,
                rankDir: "TB"
            };
        for (var element of this._graph.getElements()) {
            if (!element.get('parent') && element instanceof joint.shapes.node.flow) {
                element.collapseFlow(this._graph);
            }
        }
        joint.layout.DirectedGraph.layout(this._graph, opt);
    };

    Paint.prototype.toJSON = function() {
        var json = {};
        json.graph = this._graph.toJSON();
        json.jobs = this.jobs;
    };

    Paint.prototype.parse = function(json) {
        var object = JSON.parse(json);
        this._graph.fromJSON(object.graph);
        this.jobs.jobs = object.jobs.jobs;
        this.jobs.names = object.jobs.names;
        this.jobs.depts = object.jobs.depts;
    };

    function Scroll(baseWidth, baseHeight) {
        this.baseWidth = baseWidth;
        this.baseHeight = baseHeight;
        this.rate = 0.2;
        this.scale = 1;
        this.minScale = 0.2;
        this.maxScale = 5;
    }

    Scroll.prototype.zoom = function (paper, x, y, delta) {
        var newScale = this.scale + delta * this.rate;
        if (newScale > this.maxScale)
            newScale = this.maxScale;
        else if (newScale < this.minScale)
            newScale = this.minScale;
        this.scale = newScale;
        paper.scale(this.scale, this.scale);
        paper.setDimensions(this.baseWidth * this.scale, this.baseHeight * this.scale);
        $(paper.options.el).height(this.baseHeight * this.scale);
        $(paper.options.el).width(this.baseWidth * this.scale);
    };

    function Drag(el, paper) {
        this.paper = {};
        this.paper = paper;
        this.el = el || '';
        this.dragWrap = $(el);
        this.init.apply(this, arguments);
    };
    Drag.prototype = {
        constructor: Drag,
        _dom: {},
        _x: 0,
        _y: 0,
        _top: 0,
        _left: 0,
        move: false,
        down: false,
        init: function () {
            this.bindEvent();
        },
        bindEvent: function () {
            var t = this;
            t.paper.on('blank:pointerdown', function (e) {
                e && e.preventDefault();
                if (!t.move) {
                    t.mouseDown(e);
                }
            });
            $('body').on('mouseup', t.el, function (e) {
                t.mouseUp(e);
            });

            $('body').on('mousemove', t.el, function (e) {
                if (t.down) {
                    t.mouseMove(e);
                }
            });
        },
        mouseMove: function (e) {
            e && e.preventDefault();
            this.move = true;
            var x = this._x - e.clientX,
                y = this._y - e.clientY,
                dom = $(this.el)[0];
            dom.scrollLeft = (this._left + x);
            dom.scrollTop = (this._top + y);
        },
        mouseUp: function (e) {
            e && e.preventDefault();
            this.move = false;
            this.down = false;
            this.dragWrap.parent().css('cursor', '');
        },
        mouseDown: function (e) {
            this.move = false;
            this.down = true;
            this._x = e.clientX;
            this._y = e.clientY;
            this._top = $(this.el)[0].scrollTop;
            this._left = $(this.el)[0].scrollLeft;
            this.dragWrap.parent().css('cursor', 'move');
        }
    };

    Paint.prototype.node = Paint.prototype.node || {};
    Paint.prototype.node.job = (function () {
        "use strict";
        return function JobNode(name, x, y) {
            var defaultAttrs = {
                rect: {
                    'stroke-width': '1',
                    'stroke-opacity': .7,
                    stroke: 'black',
                    rx: 3,
                    ry: 3,
                    fill: 'lightgray'
                },
                text: {text: name, fill: 'black'}
            };
            var width = name.length * 7 + 20 > 100 ? name.length * 7 + 20 : 100;
            return new joint.shapes.basic.Rect({
                position: {x: x, y: y},
                size: {
                    width: width - width % 10 + (width % 10 ? 10 : 0),
                    height: 50
                },
                attrs: defaultAttrs
            });
        }
    })();
    Paint.prototype.node.flow = (function () {
        "use strict";
        joint.shapes.node = joint.shapes.node || {};
        joint.shapes.node.flow = joint.shapes.basic.Generic.extend({
            markup: '<g class="rotatable"><g class="scalable"><rect/></g><image/><text/></g>',
            defaults: _.defaultsDeep({

                type: 'node.flow',
                attrs: {
                    rect: {
                        fill: '#ffffff',
                        stroke: '#000000',
                        width: 100,
                        height: 60
                    },
                    image: {
                        height: 40,
                        width: 40,
                        ref: 'rect',
                        y: 25,
                        x: 10,
                        'y-alignment': 'middle',
                        'href': 'graph-icon.png'
                    },
                    text: {
                        ref: 'rect',
                        y: 60,
                        x: 60,
                        'font-family': 'Arial, helvetica, sans-serif',
                        'font-size': 14,
                        'y-alignment': 'middle'
                    }
                }
            }, joint.shapes.basic.Generic.prototype.defaults)
        });

        joint.shapes.node.flow.prototype.expandFlow = function (sourceGraph, graph) {
            var $this = this;
            var offsetX = 10;
            var offsetY = 50;
            var position = this.get('position');
            var bbox = graph.getBBox();
            this.resize(bbox.width + offsetX + 10, bbox.height + offsetY + 10);
            for (var element of graph.getElements()) {
                element = element.clone();
                var elPostion = element.get('position');
                var x = elPostion.x - bbox.x + offsetX +position.x;
                var y = elPostion.y - bbox.y + offsetY+position.y;
                element.position(x, y);
                $this.embed(element);
                element.addTo(sourceGraph);
                console.log(element);
                element.toFront();
            }
            sourceGraph.addCells(graph.getLinks());
        };

        joint.shapes.node.flow.prototype.collapseFlow = function (graph) {
            graph.removeCells(this.getEmbeddedCells({deep:true}));
            var name = this.get('attrs').text.text;
            var width = name.length * 7 + 60 > 100 ? name.length * 7 + 60 : 100;
            this.resize(width - width % 10 + (width % 10 ? 10 : 0),50);
        };

        return function (name, x, y) {
            var width = name.length * 7 + 60 > 100 ? name.length * 7 + 60 : 100;
            var node = new joint.shapes.node.flow({
                position: {x: x, y: y},
                attrs: {
                    rect: {
                        'stroke-width': '2',
                        'stroke-opacity': .7,
                        stroke: 'black',
                        rx: 3,
                        ry: 3,
                        fill: 'white'
                    },
                    text: {text: name, fill: 'black'}
                }
            });
            node.resize(width - width % 10 + (width % 10 ? 10 : 0), 50);
            return node;
        };
    })();

    Paint._defaultLinkAttr = {
        '.connection': {
            stroke: '#333333',
            'stroke-width': 3
        },
        '.marker-target': {
            fill: '#333333',
            d: 'M 10 0 L 0 5 L 10 10 z'
        },
        '.marker-arrowheads': {
            'display': 'none'
        }
    };
    Paint.prototype.addJobNode = function (job, x, y) {
        "use strict";
        if (!this.editable) return;
        if (this.jobs.getNodeId(job.name)) return;
        var node = null;
        switch (job.type) {
            case 'job':
                node = new this.node.job(job.name, x, y);
                this._graph.addCell(node);
                this.jobs.addJob(node.id, job);
                break;
            case 'flow':
                node = new this.node.flow(job.name, x, y);
                this._graph.addCell(node);
                this.jobs.addJob(node.id, job);
                break;
        }
        return node;
    };
    Paint.prototype.deleteNode = function (id) {
        "use strict";
        if (!this.editable) return;
        this.jobs.removeJob(id);
        this._graph.getCell(id).remove();

    };
    Paint.prototype.linkNode = function (sourceId, targetId) {
        "use strict";
        if (!this.editable) return;
        if (this._graph.getCell(sourceId) && this._graph.getCell(targetId)) {
            this._graph.addCell(new joint.dia.Link({
                source: {id: sourceId},
                target: {id: targetId},
                attrs: Paint._defaultLinkAttr
            }));
        }
    };

    Paint.prototype.initEdit = function () {
        "use strict";
        if (!this.editable) return;
        var $this = this;
        var tempLink = null;
        var linkMode = false;

        function resetPosition(cell) {
            if (linkMode) {
                cell.set('position', cell.previous('position'));
            }
        }

        this._paper.on('cell:pointerdown', function (cellView, event, x, y) {
            if (event.button == 2 && cellView.model.isElement() && !cellView.model.get('parent')) {
                linkMode = true;
                cellView.model.on('change:position', resetPosition);
                for (var cell of cellView.model.getEmbeddedCells({deep:true})) {
                    if (cell.isElement()) {
                        cell.on('change:position', resetPosition);
                    }
                }
                tempLink = new joint.dia.Link({
                    source: {id: cellView.model.id},
                    target: {x: x, y: y},
                    attrs: Paint._defaultLinkAttr
                });
                tempLink.addTo($this._graph);
            }
        });

        this._paper.on('cell:pointermove', function (cellView, event, x, y) {
            if (linkMode && tempLink) {
                // event && event.preventDefault();
                tempLink.set('target', {x: x, y: y});
            }
        });

        this._paper.on('cell:pointerup', function (cellView, event) {
            if (linkMode) {
                var source = tempLink.getSourceElement();
                source.off('change:position', resetPosition);
                for (var cell of source.getEmbeddedCells({deep:true})) {
                    if (cell.isElement()) {
                        cell.off('change:position', resetPosition);
                    }
                }
                linkMode = false;
                tempLink.remove();
                var targetPoint = tempLink.get('target');
                var elements = $this._paper.findViewsFromPoint(targetPoint);
                if (elements.length && elements[0] instanceof joint.dia.ElementView) {
                    if (elements[0].model.id != source.id) {
                        if (!$this.jobs.hasDept(source.id, elements[0].model.id)) {
                            var target = elements[0].model;
                            $this.linkNode(source.id, target.id);
                        }
                    }
                }
                tempLink = null;
            }
        });

        paint._graph.on('change:position', (function (cell) {
            var child = [];
            return function (cell) {
                var parentId = cell.get('parent');
                if (!parentId) {
                    cell.getEmbeddedCells({deep: true}).forEach(function (cell) {
                        child.push(cell.id);
                    });
                    return;
                }
                var parent = paint._graph.getCell(parentId);
                if (child.includes(cell.id)) {
                    child.splice(child.indexOf(cell.id),1);
                    return;
                }
                cell.set('position', cell.previous('position'));
            };
        })());

        this._graph.on('add', function (cell) {
            if (cell instanceof joint.dia.Link && cell.getSourceElement() && cell.getTargetElement()) {
                var sourceId = cell.getSourceElement().id;
                var targetId = cell.getTargetElement().id;
                if (sourceId && targetId) {
                    $this.jobs.addDept(sourceId,targetId);
                }
            }
        });

        this._graph.on('remove', function (cell) {
            if (cell.isLink() && cell.get('source') && cell.get('target')) {
                var sourceId = cell.get('source').id;
                var targetId = cell.get('target').id;
                if (sourceId && targetId) {
                    $this.jobs.removeDept(sourceId,targetId);
                }
            }
        });
    };

    Paint.prototype.initHotKey = function () {
        "use strict";
        var $this = this;
        if (this.editable) {
            hotkeys('del',function(event, handler) {
                $this.selected.forEach(function (cell) {
                    $this.deleteNode(cell.id);
                });
                $this.selected = [];
            });
        };
    };

    function JobStorage() {
        "use strict";
        this.jobs = new Map();
        this.names = new Map();
        this.depts = new Map();
    }

    JobStorage.prototype.addJob = function (id, job) {
        "use strict";
        this.jobs.set(id, job);
        this.names.set(job.name,id);
    };

    JobStorage.prototype.getJob = function (id) {
        "use strict";
        return this.jobs.get(id);
    };

    JobStorage.prototype.getNodeId = function (name) {
        "use strict";
        return this.names.get(name);
    };

    JobStorage.prototype.removeJob = function (id) {
        "use strict";
        this.names.delete(this.jobs.get(id).name);
        return this.jobs.delete(id);
    };

    JobStorage.prototype.addDept = function (id, deptId) {
        "use strict";
        var depts = this.depts.get(id) || [];
        depts.push(deptId);
        this.depts.set(id, depts);
    };

    JobStorage.prototype.removeDept = function (id, deptId) {
        "use strict";
        var depts = this.depts.get(id);
        if (depts && depts.includes(deptId)) {
            depts.splice(depts.indexOf(deptId), 1);
        }
    };

    JobStorage.prototype.hasDept = function (id, deptId) {
        "use strict";
        var depts = this.depts.get(id);
        if (depts) {
            return depts.includes(deptId);
        }
        return false;
    };

    JobStorage.prototype.getDepts = function (id) {
        return this.depts.get(id) || [];
    };

    return Paint;
})();