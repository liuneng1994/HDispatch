/**
 * Created by 刘能 on 2016/9/16.
 */
(function () {
    angular.module("dispatch").factory('workflowDiagramService', workflowDiagramService);
    function workflowDiagramService() {
        var graphStore = {};
        var paperStore = {};

        function graph(name, isNew) {
            if (isNew) {
                graphStore[name] = new joint.dia.Graph;
            }
            return graphStore[name];
        }
    }
})()