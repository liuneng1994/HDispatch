(function () {
    'use strict;';
    angular.module('dispatch').factory('workflowService', ['httpService', workflowService]);
    function workflowService(httpService) {
        var themes = function () {
            return httpService.get('/dispatcher/theme/queryAll', {},
                function (data, defered) {
                    if (data.success) {
                        defered.resolve(data.rows);
                    }
                });
        };
        var layers = function (themeId) {
            return httpService.get('/dispatcher/layer/queryAll',
                {themeId: themeId},
                function (data, defered) {
                    if (data.success) {
                        defered.resolve(data.rows);
                    }
                });
        };
        var jobs = function (themeId, layerId) {
            return httpService.get('/dispatcher/job/query',
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
            return httpService.postJSON('/dispatcher/workflow/create', workflow, function (data, defered) {
                if (data.success) {
                    defered.resolve(data.message);
                } else {
                    defered.reject(data.message)
                }
            });
        };

        var saveGraph = function (workflowId, graph) {
            return httpService.postForm('/dispatcher/workflow/saveGraph',
                {workflowId: workflowId, graph: graph},
                function (data, defered) {
                    if (data.success) {
                        defered.resolve(data.message);
                    } else {
                        defered.reject(data.message)
                    }
                });
        };

        var generateWorkflow = function (workflowId) {
            return httpService.get('/dispatcher/workflow/generateWorkflow',
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
            return httpService.get('/dispatcher/workflow/query',params,function(data, defered) {
                if (data.success) {
                    defered.resolve(data);
                }
            });
        };

        return {
            themes: themes,
            layers: layers,
            jobs: jobs,
            createWorkflow: createWorkflow,
            saveGraph: saveGraph,
            generateWorkflow: generateWorkflow,
            query: query
        };
    }
})();