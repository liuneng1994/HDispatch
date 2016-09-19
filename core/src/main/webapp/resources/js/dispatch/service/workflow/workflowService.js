(function () {
    'use strict';
    angular.module('dispatch').factory('workflowService', ['$q', '$http', workflowService]);
    function workflowService($q, $http) {
        var themes = function () {
            var defered = $q.defer();
            $http.get('/dispatcher/theme/queryAll').success(function (data) {
                if (data.success) {
                    defered.resolve(data.rows);
                } else {

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
            })
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
            })
            return defered.promise;
        }
        return {
            themes: themes,
            layers: layers,
            jobs: jobs
        };
    }
})();