/**
 * Created by 刘能 on 2016/9/7.
 */
(function () {
    angular.module('dispatch').controller('groupCreatingController', ['groupCreatingService', groupCreating]);
    function groupCreating(groupCreatingService) {
        var vm = this;
        vm.group = {};
        vm.group.active = true;
        vm.dependencySelectOptions = {
            placeholder: "添加依赖",
            dataTextField: "ProductName",
            dataValueField: "ProductID",
            valuePrimitive: true,
            autoBind: false,
            autoClose: false,
            dataSource: {
                type: "odata",
                serverFiltering: true,
                transport: {
                    read: {
                        url: "//demos.telerik.com/kendo-ui/service/Northwind.svc/Products",
                    }
                }
            }
        };
        return vm;
    };

})()

