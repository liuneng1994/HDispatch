(function(){
    'use strict;'
    console.log(angular.module('dispatch'));
    angular.module('dispatch').directive('workflowName', ['$q','$http',function ($q, $http) {
        return {
            required:'ngModel',
            link: function(scope, elm, attrs, ctrl) {
                ctrl.$asyncValidators.workflowName = function(modelValue, viewValue) {
                    if (ctrl.$isEmpty(modelValue)) {
                        return $q.when();
                    }
                    var def = $q.defer();
                    $http({
                        url:'/dispatcher/workflow/hasWorkflow',
                        method:'GET',
                        params: {name:modelValue}
                    }).success(function (data) {
                        if (data.success) {
                            def.resolve();
                        } else {
                            def.reject();
                        }
                    })
                    return def.promise;
                };
            }

        }
    }]);
})();