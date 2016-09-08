(function () {
    angular.module("dispatch")
        .controller("layerCreateController", function($scope,$http){
            // $scope.themes_data = {
            //     dataType: "json",
            //     serverFiltering: true,
            //     transport: {
            //         read: {
            //             url: "/dispatcher/theme/query",
            //         }
            //     }
            // };
            $scope.layerInfo = {
                themeId:'',
                layerName:'',
                layerDescription:'',
                seq:'',
                errorEmail:'',
                successEmail:''
            };

            $scope.themes_data = new kendo.data.DataSource({
                transport: {
                    read: {
                        url: "/dispatcher/theme/query",
                        dataType: "json"
                    },
                    parameterMap: function (options, operation) {
                        //封装请求参数
                        if (operation === "read") {
                            var map = {};
                            return map;
                        }
                    }
                },
                serverPaging: true,
                pageSize: 10,
                schema: {
                    model: {
                        id: 'themeId',
                        fields: {
                            themeId: {type: 'int', validation: {required: true}},
                            themeName: {type: 'string', validation: {required: true}},
                            description: {type: 'string', validation: {required: true}},
                            projectName: {type: 'string', validation: {required: true}},
                            projectDescription: {type: 'string', validation: {required: true}}
                        }
                    },
                    errors: function (res) {
                        if (!res.success && res.message) {
                            return res.message;
                        }
                    }
                },
                error: function (e) {
                    alert(e.errors)
                }
            });

            $scope.sendForm = function () {

                $http({
                    method:'POST',
                    url:'/dispatcher/layer/submit',
                    params:{
                        themeId:$scope.layerInfo.themeId,
                        layerName:$scope.layerInfo.layerName,
                        layerDescription:$scope.layerInfo.layerDescription,
                        seq:$scope.layerInfo.seq,
                        failureEmail:$scope.layerInfo.errorEmail,
                        successEmail:$scope.layerInfo.successEmail
                    }
                }).success(function (data, status, headers, config) {
                    alert("创建成功");
                }).error(function (data, status, headers, config) {
                    alert("创建失败");
                });
            }
        })
})();