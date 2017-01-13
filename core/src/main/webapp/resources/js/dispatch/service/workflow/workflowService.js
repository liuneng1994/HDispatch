/**
 * Created by liuneng on 2016/10/13.
 */
(function () {
    "use strict";
    angular.module('dispatch').factory('workflowService', ['httpService', workflowService]);
    function workflowService(httpService) {
        var themes = function () {
            return httpService.get(_basePath + '/dispatcher/theme/queryAll_read', {},
                function (data, defered) {
                    if (data.success) {
                        defered.resolve(data.rows);
                    }
                });
        };
        var operateThemes = function () {
            return httpService.get(_basePath + '/dispatcher/theme/queryAll_opt', {},
                function (data, defered) {
                    if (data.success) {
                        defered.resolve(data.rows);
                    }
                });
        };
        var layers = function (themeId) {
            return httpService.get(_basePath + '/dispatcher/layer/queryAll',
                {themeId: themeId},
                function (data, defered) {
                    if (data.success) {
                        defered.resolve(data.rows);
                    }
                });
        };
        var jobs = function (themeId, layerId) {
            return httpService.postForm(_basePath + '/dispatcher/job/query',
                {
                    themeId: themeId,
                    layerId: layerId,
                    pageSize: 2147483647
                }, function (data, defered) {
                    if (data.success) {
                        defered.resolve(data.rows);
                    }
                }
            );
        };
        var createWorkflow = function (workflow) {
            return httpService.postJSON(_basePath + '/dispatcher/workflow/create', workflow, function (data, defered) {
                if (data.success) {
                    defered.resolve(data.message);
                } else {
                    defered.reject(data.message)
                }
            });
        };

        var generateWorkflow = function (workflowId) {
            return httpService.get(_basePath + '/dispatcher/workflow/generateWorkflow',
                {
                    workflowId: workflowId
                }, function (data, defered) {
                    if (data.success) {
                        defered.resolve(data.message);
                    } else {
                        defered.reject(data.message)
                    }
                });
        };
        var query = function (queryInfo) {
            var params = {};
            for (var name in queryInfo) {
                if (queryInfo.hasOwnProperty(name) && queryInfo[name] != undefined) params[name] = queryInfo[name];
            }
            return httpService.postForm(_basePath + '/dispatcher/workflow/query', params, function (data, defered) {
                if (data.success) {
                    defered.resolve(data);
                }
            });
        };

        var queryOperate = function (queryInfo) {
            var params = {};
            for (var name in queryInfo) {
                if (queryInfo.hasOwnProperty(name) && queryInfo[name] != undefined) params[name] = queryInfo[name];
            }
            return httpService.postForm(_basePath + '/dispatcher/workflow/query_operate', params, function (data, defered) {
                if (data.success) {
                    defered.resolve(data);
                }
            });
        };

        var workflow = function (workflowId) {
            var params = {workflowId: workflowId};
            return httpService.get(_basePath + '/dispatcher/workflow/get', params, function (data, defered) {
                if (data.success) {
                    defered.resolve(data.rows[0]);
                } else {
                    defered.reject(data);
                }
            });
        };

        var updateWorkflow = function (workflow) {
            return httpService.postJSON(_basePath + '/dispatcher/workflow/update', workflow, function (data, defered) {
                if (data.success) {
                    defered.resolve(data.message);
                } else {
                    defered.reject(data.message);
                }
            });
        };

        var scheduleWorkflow = function (scheduleInfo) {
            return httpService.get(_basePath + '/schedule/schedule', scheduleInfo, function (data, defered) {
                if (data.code == 1) {
                    defered.resolve(scheduleInfo.projectName + '计划成功');
                } else {
                    defered.reject(data.message);
                }
            });
        };

        var cronScheduleWorkflow = function(scheduleInfo) {
            return httpService.get(_basePath + '/schedule/schedulecron', scheduleInfo, function (data, defered) {
                if (data.success) {
                    defered.resolve(scheduleInfo.projectName + '计划成功');
                } else {
                    defered.reject(data.message);
                }
            });
        }

        var executeWorkflow = function (executeInfo) {
            return httpService.postForm(_basePath + '/schedule/exeflow', executeInfo, function (data, defered) {
                if (data.code == 1) {
                    defered.resolve(executeInfo.project + '执行成功');
                } else {
                    defered.reject(data.message);
                }
            });
        };

        var deleteWorkflow = function (ids) {
            "use strict";
            return httpService.postJSON(_basePath + '/dispatcher/workflow/delete', ids, function (data, defered) {
                if (data.success)
                    defered.resolve("");
                else
                    defered.reject(data.message);
            });
        };

        var queryWorkflowDependency = function (workflowName, page, pageSize) {
            "use strict";
            var params = {
                projectName: workflowName,
                page: page,
                pageSize: pageSize
            };
            return httpService.get(_basePath + '/dispatcher/workflow_dependency/query', params, function (data, defered) {
                if (data.success) {
                    defered.resolve(data);
                }
            });
        };

        var createWorkflowDependency = function (dependencies) {
            "use strict";
            return httpService.postJSON(_basePath + "/dispatcher/workflow_dependency/insert", dependencies, function (data, defered) {
                defered.resolve('');
            });
        };

        var deleteWorkflowDependency = function (dependencies) {
            "use strict";
            return httpService.postJSON(_basePath + "/dispatcher/workflow_dependency/delete", dependencies, function (data, defered) {
                defered.resolve('');
            });
        };

        var queryWorkflowMutex = function (workflowName, page, pageSize) {
            "use strict";
            var params = {
                projectName: workflowName,
                page: page,
                pageSize: pageSize
            };
            return httpService.get(_basePath + '/dispatcher/workflow_mutex/query', params, function (data, defered) {
                if (data.success) {
                    defered.resolve(data);
                }
            });
        };

        var createWorkflowMutex = function (mutexList) {
            "use strict";
            return httpService.postJSON(_basePath + "/dispatcher/workflow_mutex/insert", mutexList, function (data, defered) {
                defered.resolve('');
            });
        };

        var deleteWorkflowMutex = function (mutexList) {
            "use strict";
            return httpService.postJSON(_basePath + "/dispatcher/workflow_mutex/delete", mutexList, function (data, defered) {
                defered.resolve('');
            });
        };

        function queryScheduleInfo(name) {
            var params = {
                project_name : name,
                page:1,
                pagesize:1
            };
            return httpService.get(_basePath + "/schedule/queryschedule",params,function(data, defered){
                if (data.success) {
                    defered.resolve(data.rows[0].cronExpression);
                }
            });
        }

        function deptGraph(name) {
            var params = {
                workflowName: name
            };
            return httpService.get(_basePath + "/dispatcher/workflow/deptGraph",params,function(data, defered){
                    defered.resolve(data);
            });
        }

        function generateAll() {
            return httpService.get(_basePath + "/dispatcher/workflow/generateAll",{},function(data, defered){
                defered.resolve(data);
            });
        }

        return {
            themes: themes,
            layers: layers,
            jobs: jobs,
            createWorkflow: createWorkflow,
            updateWorkflow: updateWorkflow,
            generateWorkflow: generateWorkflow,
            generateAll: generateAll,
            query: query,
            queryOperate: queryOperate,
            workflow: workflow,
            scheduleWorkflow: scheduleWorkflow,
            cronScheduleWorkflow: cronScheduleWorkflow,
            executeWorkflow: executeWorkflow,
            deleteWorkflow: deleteWorkflow,
            queryWorkflowDependency: queryWorkflowDependency,
            createWorkflowDependency: createWorkflowDependency,
            deleteWorkflowDependency: deleteWorkflowDependency,
            queryWorkflowMutex: queryWorkflowMutex,
            createWorkflowMutex: createWorkflowMutex,
            deleteWorkflowMutex: deleteWorkflowMutex,
            operateThemes: operateThemes,
            queryScheduleInfo:queryScheduleInfo,
            deptGraph: deptGraph
        };
    }
})();