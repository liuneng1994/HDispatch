(function () {
    window.stateColor = {
            "PAUSED"  : "background-color: #FFCC00;",
            "NORMAL"  : "background-color: #00CC00;",
            "COMPLETE": "background-color: grey;",
            "ERROR"   : "background-color: #FF3333;",
            "BLOCKED" : "background-color: black;",
            "NONE"    : "background-color: black;"
         };
    angular.module("dispatch")
        .controller("SchGroupController",['$scope','SchGroupService',function ($scope,SchGroupService) {
                var vm=this;
          
          
        	 $scope.mainGridOptions = {
                     dataSource: {
                         transport: {
                        	 /*read:  {
                                 url: "/dispatcher/theme/query",
                                 type : "GET",
                                 dataType: "json"
                             }*/
                        	 read:function (options) {
                                 return SchGroupService.read(options).then(function (data) {
                                
                                     options.success(data);
                                 }, function (error) {
                                     options.error(error);
                                 });
                             }
                         },
                         pageSize: 10,
                         serverPaging: true,
                         schema: {
                             data:'rows',
                             total:'total',
                             model: {
                                 id: "id",
                                 fields: {
                                     langCode: { editable: true, validation: { required: true } },
                                     baseLang: { validation: { required: true } },
                                     description: { validation: { required: false } },
                                     enable: {}
                                 }
                             },
                             errors: function(res){
                                 if(!res.success && res.message) {
                                     return res.message;
                                 }
                             }
                         },

                         serverSorting: true
                     },
                     reorderable : true,
                     resizable : true,
                     pageable: true,
                    /* pageable: {
                         pageSizes:[5, 10, 20, 50],
                         refresh:true,
                         buttonCount:5,
                         messages: {
                             noRecords: "未找到任何数据",
                             display: "{0} - {1} 共 {2} 条数据",
                             empty: "没有数据",
                             page: "页",
                             of: "/ {0}",
                             itemsPerPage: "条每页",
                             first: "第一页",
                             previous: "前一页",
                             next: "下一页",
                             last: "最后一页",
                             refresh: "刷新"
                         }
                     },*/
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
                                   template: "<a href='http://www.baidu.com'>#= group_name#</a>",
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
                                   template : function (item) {
                                       var html = "<button disabled='true'  class='btn btn-info' style='height:13px;width:100px;"+(stateColor[item.flow_id])+"'>"+item.flow_id+"</button>";
                                       return html;
                                       },
                                   width: 150

                               },
                               {
                                   field: "opration",
                                   title: "操作",
                                   template:function(item)
                                   {
                                	   var html="<a href='javascript:void(0)' ng-click='vm.start(\"" + item.flow_id + "\")'>" +
                                	   		"<span class='glyphicon glyphicon-play'></span></a>&nbsp;" +
                                	   		" <a href='javascript:void(0)' ng-click='vm.pause("+item.flow_id+")'>" +
                                	   		"<span class='glyphicon glyphicon-pause'></span></a>&nbsp;"+
                                	   		" <a href='javascript:void(0)' ng-click='vm.stop("+item.flow_id+")'>" +
                                	   		"<span class='glyphicon glyphicon-stop'></span></a>&nbsp;"+
                                	   		" <a href='javascript:void(0)' ng-click='vm.resume("+item.flow_id+")'>" +
                                	   		"<span class='glyphicon glyphicon-repeat'></span></a>";
                                	   return html;
                                   },
                                   width: 100

                               },
                               {
                                   field: "running",
                                   title: "进度",
                                   template : function (item) {
                                       var html = "<button disabled='true'  class='btn btn-info' style='height:10px;width:"+item.active+"%;background-color: #00CC00;'></button>";
                                       return html;
                                       },
                                   width: 150

                               }
                               
                           ]
                 };
        	 
        	  this.start=function(id) { 
        		  SchGroupService.start(id);
          	    }
                this.pause=function(id) { 
                	SchGroupService.pause(id);
          	    }
          	    this.stop=function(id) { 
          	    	SchGroupService.stop(id);
          	    }
          	    this.resume=function(id) { 
          	    	SchGroupService.resume(id);
          	    }
            return vm;
        }]);
})();