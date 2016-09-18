(function () {
    'use strict';
    angular.module('dispatch').factory('workflowService', ['$q', workflowService]);
    function workflowService($q) {
        var themes = function () {
            var defered = $q.defer();
            var data = [{themeId: 1, themeName: '主题1'}, {themeId: 2, themeName: '主题2'}, {themeId: 3, themeName: '主题3'}];
            defered.resolve(data);
            return defered.promise;
        };
        var layers = function (themeId) {
            var defered = $q.defer();
            var data = [[{layerId: 1, layerName: '层次1'}, {layerId: 2, layerName: '层次2'}, {
                layerId: 3,
                layerName: '层次3'
            }],
                [{layerId: 4, layerName: '层次4'}, {layerId: 5, layerName: '层次5'}, {layerId: 6, layerName: '层次6'}],
                [{layerId: 7, layerName: '层次7'}, {layerId: 8, layerName: '层次8'}, {layerId: 9, layerName: '层次9'}]];
            defered.resolve(data[themeId - 1]);
            return defered.promise;
        };
        return {
            themes: themes,
            layers: layers
        };
    }
})();