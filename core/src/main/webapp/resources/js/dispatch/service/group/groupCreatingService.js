/**
 * Created by 刘能 on 2016/9/7.
 */
(function () {
    angular.module('dispatch').factory('groupCreatingService', ['$q', '$http', groupCreating]);
    function groupCreating($q) {
        function getThemes() {
            var defered = $q.defer();
            var data = [{themeName: '主题1', themeId: 1}, {themeName: '主题2', themeId: 2}, {themeName: '主题3', themeId: 3}]
            defered.resolve(data);
            return defered.promise;
        }

        function getLayers(theme) {
            var defered = $q.defer();
            var data = [[{layerId: 1, layerName: '层1'}, {layerId: 2, layerName: '层2'}, {layerId: 3, layerName: '层3'}],
                [{layerId: 4, layerName: '层4'}, {layerId: 5, layerName: '层5'}, {layerId: 6, layerName: '层6'}],
                [{layerId: 7, layerName: '层7'}, {layerId: 8, layerName: '层8'}, {layerId: 9, layerName: '层9'}]]
            defered.resolve(data[theme - 1]);
            return defered.promise;
        }

        return {
            getThemes: getThemes,
            getLayers: getLayers
        };
    }
})()
