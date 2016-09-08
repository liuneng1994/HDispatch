(function () {
    angular.module("dispatch")
        .controller("themeCreateController",function ($scope) {
            var viewModel = kendo.observable({
                model: {}
            });
            var baseUrl = "/dispatcher/theme/";
//        console.info(baseUrl);
            var themes_data = new kendo.data.DataSource({
                transport: {
                    read: {
                        url: baseUrl + "query",
                        dataType: "json"
                    },
                    create: {
                        url: baseUrl + 'submit',
                        type: 'POST',
                        dataType: "json"
                    },
                    parameterMap: function (options, operation) {
                        //封装请求参数
                        if (operation !== "read" && options.models) {
                            var datas = options.models;
//                        console.log(datas);
                            if (operation == 'create' || operation == 'update') {
                                datas = options.models.map(function (data) {
                                    data['__status'] = (operation == 'create' ? 'add' : 'update');
                                    return data;
                                });
                            }
                            var data_str = kendo.stringify(datas);
//                        console.log(data_str);
                            var map = {
                                themes_data_array: data_str
                            };
                            return map;
                        } else if (operation === "read") {
                            var map = {};
                            return map;
                        }
                    }
                },
                batch: true,
                serverPaging: true,
                pageSize: 10,
                schema: {
                    model: {
                        id: 'themeId',
                        fields: {
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
                    },
                    success:function (e) {
                        alert("success1:"+e);
                    }
                },
                error: function (e) {
                    alert(e.errors)
                },
                success:function (e) {
                    alert("success2:"+e);
                }
            });

            $("#reportListGrid").kendoGrid({
                dataSource: themes_data,
                resizable: true,
                selectable: "multiple",
                scrollable: false,
                pageable: {
                    pageSizes: [5, 10, 20, 50],
                    refresh: true,
                    buttonCount: 5,
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
                },
                toolbar: [{
                    name: "create",
                    text: '创建'
                }, {
                    name: "save",
                    text: '保存'
                }, {
                    name: "cancel",
                    text: '取消'
                }
                ],
                columns: [
                    {'field': 'themeName', 'title': '主题名称', 'width': 70, 'height': 100},
                    {'field': 'description', 'title': '主题描述', 'width': 70, 'height': 100},
                    {'field': 'projectName', 'title': '项目名称', 'width': 70, 'height': 100},
                    {'field': 'projectDescription', 'title': '项目描述', 'width': 70, 'height': 100}
                ],
                editable: true
            });
            kendo.bind($('#page-content'), viewModel);
        });
})();