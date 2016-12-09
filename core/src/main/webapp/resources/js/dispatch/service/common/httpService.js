/**
 * Created by liuneng on 2016/9/21.
 */

(function () {
    'use strict;'
    angular.module('dispatch').factory('httpService', ['$window', '$http', '$q', function ($window, $http, $q) {
        /**
         * 请求错误处理函数
         */
        function errorHandler() {
            kendo.ui.showErrorDialog({message:"网络错误"});
        };
        return {
            /**
             * get请求函数
             * @param url 请求的url
             * @param param 请求参数
             * @param successFunc 成功时的函数
             * @return 返回$q.defer().promise
             */
            get: function (url, params, successFunc) {
                var defered = $q.defer();
                $http({
                    url: url,
                    method: 'GET',
                    params: params,
                    headers: {'X-Requested-With': 'XMLHttpRequest'}
                }).success(function (data) {
                    if (data.success === false) {
                        kendo.ui.showErrorDialog({message:data.message});
                        return;
                    }
                    successFunc(data, defered);
                }).error(errorHandler);
                return defered.promise;
            },
            /**
             * post表单函数
             * @param url 请求url
             * @param data  表单数据对象键值对的形式存储
             * @param successFunc  成功时的函数
             * @return 返回$q.defer().promise
             */
            postForm: function (url, data, successFunc) {
                var defered = $q.defer();
                successFunc.defered = defered;
                $http({
                    url: url,
                    method: 'POST',
                    data: data,
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'X-Requested-With': 'XMLHttpRequest'
                    },
                    transformRequest: function (obj) {
                        var str = [];
                        for (var p in obj) {
                            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                        }
                        return str.join("&");
                    }
                }).success(function (data) {
                    if (data.success === false) {
                        kendo.ui.showErrorDialog({message:data.message});
                        return;
                    }
                    successFunc(data, defered);
                }).error(errorHandler);
                return defered.promise;
            },
            /**
             * post json数据函数
             * @param url 请求url
             * @param JSONdata 发送的json对象
             * @param successFunc 成功时的函数
             * @return {Promise}
             */
            postJSON: function (url, JSONdata, successFunc) {
                var defered = $q.defer();
                $http({
                    url: url,
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-Requested-With': 'XMLHttpRequest'
                    },
                    data: JSON.stringify(JSONdata)
                }).success(function (data) {
                    if (data.success === false) {
                        kendo.ui.showErrorDialog({message:data.message});
                        return;
                    }
                    successFunc(data, defered);
                }).error(errorHandler);
                return defered.promise;
            }
        }
    }])
})();