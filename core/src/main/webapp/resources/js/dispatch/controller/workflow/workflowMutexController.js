/**
 * Created by hasee on 2016/10/25.
 */
(function () {
    "use strict";
    angular.module('dispatch').controller('workflowMutexController',
        ['$location', 'workflowService', function ($location, workflowService) {
            var vm = this;
            vm.workflowId = $location.search('workflowId');
            vm.projectName = $location.search('projectName');
            vm.flowId = $location.search('flowId');
            if (vm.workflowId&&vm.projectName&&vm.flowId) {
                init();
            }
            function init() {
                vm.gridOptions = {
                    dataSource: {
                        transport: {
                            read: function (options) {

                            },
                            destroy: function(options) {

                            }
                        },
                        batch: true,
                        serverPaging: true,
                        pageSize: 10,
                        schema:{
                            data:"rows",
                            total:"total",
                            model: {
                                id : 'workflowId',
                                fields : {
                                    name : {},
                                    theme : {},
                                    layer : {},
                                    description: {}
                                }
                            }
                        }
                    },
                    //width:500,
                    selectable:"multiple, rowbox",
                    navigatable: true,
                    resizable: true,
                    scrollable: true,
                    editable: 'popup',
                    pageable: {
                        pageSizes: [5,10,20,50],
                        refresh: true,
                        buttonCount: 5,
                        messages: {
                            noRecords: "未找到任何数据",
                            display: "{0} - {1} 共 {2} 条数据",
                            empty: "没有数据",
                            page: "页",
                            of: "/ {0}",
                            itemsPerPage: "条每页",
                            first: "第一页",
                            previous: "前一页",
                            next: "下一页",
                            last: "最后一页",
                            refresh: "刷新"
                        }
                    },
                    columns: [
                        {
                            field: "name",
                            title: '任务流',
                            width: 100
                        },
                        {
                            field: "theme",
                            title: '任务组',
                            width: 100
                        },
                        {
                            field: "layer",
                            title: '层级',
                            width: 100
                        }]
                }
            }
    }]);
})();