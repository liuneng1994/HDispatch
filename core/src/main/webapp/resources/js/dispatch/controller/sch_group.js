(function () {
    console.log(angular.module("dispatch"));
    
    angular.module("dispatch").service('SchGroupService', ['$http', '$q', SchGroupService]);
    function SchGroupService($http, $q) {
        this.read = function (params,reportData) {
            var deferred = $q.defer();
             httpApiService.get("/weather/index", {
                 filters: params.data.filters,
                 sort: params.data.sort,
                 currentPage: params.data.page,
                 pageSize: params.data.pageSize
             }).then(function (data) {
                 deferred.resolve(data);
             }, function (error) {
                 deferred.reject(error);
             });
            deferred.resolve(reportData);
            return deferred.promise;
        }

    }
    
    angular.module("dispatch")
        .controller("SchGroupController",['$scope',function ($scope) {
        	 $scope.mainGridOptions = {
                     dataSource: {
                       
                         transport: {
                        	 read:  {
                                 url: "/demo/select",
                                 type : "GET",
                                 dataType: "json"
                             }
                         },
                         pageSize: 5,
                         serverPaging: true,
                         serverSorting: true
                     },
                     sortable: true,
                     pageable: true,
                     dataBound: function() {
                         this.expandRow(this.tbody.find("tr.k-master-row").first());
                     },
                     columns: [
                               {
                                   field: "theme_name",
                                   title: "主题",
                                   width: 70
                               },
                               {
                                   field: "group_name",
                                   title: "组名称",
                                   template: "<a href='http://www.baidu.com'>#= name#</a>",
                                   width: 150
                               },
                               {
                                   field: "layer_name",
                                   title: "层次",
                                   width: 70
                               },
                               {
                                   field: "isDepen",
                                   title: "依赖触发",
                                   width: 70
                               },
                               {
                                   field: "nextruntime",
                                   title: "下一次执行时间",
                                   width: 150

                               },
                               {
                                   field: "status",
                                   title: "状态",
                                   width: 70

                               },
                               {
                                   field: "opration",
                                   title: "操作",
                                   width: 150

                               },
                               {
                                   field: "running",
                                   title: "进度",
                                   width: 150

                               }
                               
                           ]
                 };
        }]);
})();