(function () {
    'use strict';
    angular.module('dispatch').factory('workflowService', ['$q', '$http', workflowService]);
    function workflowService($q, $http) {
        var themes = function () {
            var defered = $q.defer();
            $http.get('/dispatcher/theme/queryAll').success(function (data) {
                if (data.success) {
                    defered.resolve(data.rows);
                }
            })

            return defered.promise;
        };
        var layers = function (themeId) {
            var defered = $q.defer();
            $http({
                url: '/dispatcher/layer/queryAll',
                method: 'GET',
                params: {themeId: themeId}
            }).success(function (data) {
                if (data.success) {
                    defered.resolve(data.rows);
                }
            }).error(function(data) {
                defered.promise('网络异常');
            });
            return defered.promise;
        };
        var jobs = function (themeId, layerId) {
            var defered = $q.defer();
            $http({
                url: '/dispatcher/job/query',
                method: 'GET',
                params: {
                    themeId: themeId,
                    layerId: layerId,
                    pageSize: 2147483647
                }
            }).success(function (data) {
                if (data.success) {
                    defered.resolve(data.rows);
                }
            }).error(function(data) {
                defered.promise('网络异常');
            });
            return defered.promise;
        };
        var createWorkflow = function(workflow) {
            var defered = $q.defer();
            $http({
                url: '/dispatcher/workflow/create',
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                data: JSON.stringify(workflow)
            }).success(function (data) {
                if (data.success) {
                    defered.resolve(data.message);
                } else {
                    defered.reject(data.message)
                }
            }).error(function(data) {
                defered.promise('网络异常');
            });
            return defered.promise;
        };

        var saveGraph = function(workflowId, graph) {
            var defered = $q.defer();
            $http({
                url: '/dispatcher/workflow/saveGraph',
                method: 'POST',
                data: {workflowId:workflowId,graph:graph},
                headers:{'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest: function(obj) {
                    var str = [];
                    for(var p in obj){
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(JSON.stringify(obj[p])));
                    }
                    return str.join("&");
                }
            }).success(function (data) {
                if (data.success) {
                    defered.resolve(data.message);
                } else {
                    defered.reject(data.message)
                }
            }).error(function(data) {
                defered.promise('网络异常');
            });
            return defered.promise;
        };

        var generateWorkflow = function(workflowId) {
            var defered = $q.defer();
            $http({
                url: '/dispatcher/workflow/generateWorkflow',
                method: 'GET',
                params: {
                    workflowId : workflowId
                }
            }).success(function (data) {
                if (data.success) {
                    defered.resolve(data.message);
                } else {
                    defered.reject(data.message)
                }
            }).error(function(data) {
                defered.promise('网络异常');
            });
            return defered.promise;
        };

        return {
            themes: themes,
            layers: layers,
            jobs: jobs,
            createWorkflow : createWorkflow,
            saveGraph: saveGraph,
            generateWorkflow: generateWorkflow
        };
    }
})();