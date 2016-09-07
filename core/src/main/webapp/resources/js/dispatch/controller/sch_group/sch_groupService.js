(function () {
    angular.module("dispatch").service('SchGroupService', ['$http', '$q', SchGroupService]);
    function SchGroupService($http, $q) {
    	  this.read = function (params) {
        	  var deferred = $q.defer();
             this.get('/group/selectAll', {
                  page: params.data.page,
                  pagesize: params.data.pageSize
              }).then(function (data) {
            	 
                  deferred.resolve(data);
              }, function (error) {
                  deferred.reject(error);
              });
              return deferred.promise;
        }
        this.get = function(url, params) {
    		var deferred = $q.defer();
    		$http.get(url, {
    			params: params
    		}).success(function(response) {
    			
    			deferred.resolve(response);
    		}).error(function(error, status) {
    			deferred.reject(error);
    		});
    		return deferred.promise;
    	}
        
        this.start=function(id)
        {
        	var deferred = $q.defer();
    		$http.get("/group/startGroup?id="+id).success(function(response) {
    			console.log(response);
    			deferred.resolve(response);
    			alert(response.message);
    		}).error(function(error, status) {
    			deferred.reject(error);
    		});
    		return deferred.promise;
        }
        this.pause=function(id)
        {
        	var deferred = $q.defer();
    		$http.get("/group/pauseGroup?id="+id).success(function(response) {
    			console.log(response);
    			deferred.resolve(response);
    			alert(response.message);
    		}).error(function(error, status) {
    			deferred.reject(error);
    		});
    		return deferred.promise;
        }
        this.stop=function(id)
        {
        	var deferred = $q.defer();
    		$http.get("/group/stopGroup?id="+id).success(function(response) {
    			console.log(response);
    			deferred.resolve(response);
    			alert(response.message);
    		}).error(function(error, status) {
    			deferred.reject(error);
    		});
    		return deferred.promise;
        }
        this.resume=function(id)
        {
        	var deferred = $q.defer();
    		$http.get("/group/resumeGroup?id="+id).success(function(response) {
    			console.log(response);
    			deferred.resolve(response);
    			alert(response.message);
    		}).error(function(error, status) {
    			deferred.reject(error);
    		});
    		return deferred.promise;
        }
    }
})();