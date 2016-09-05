/**
 * @summary Hap
 * @description 抽象通用函数
 * @version 1.0
 * @author njq.niu@hand-china.com
 * @copyright Copyright Hand China Co.,Ltd.
 * 
 */
!(function($) {
    
    $.extend({ 
        /**
         * isEmpty( Object value, [Boolean allowEmptyString] ) : Boolean
         * Returns true if the passed value is empty, false otherwise. The value is deemed to be empty if it is either:
         * null
         * undefined
         * a zero-length array
         * a zero-length string (Unless the allowEmptyString parameter is set to true)
         */
        isEmpty : function(value, allowEmptyString) {
            return (value === null) || (value === undefined)
                    || (!allowEmptyString ? value === '' : false)
                    || ($.isArray(value) && value.length === 0);
        }
    });
    
    /**
     * 获取或设置prompt信息
     * 函数描述
     * @param code
     * @param value
     * @returns
     */
    $l = function(code,value){
        var al = arguments.length,p = Hap.defaultPrompt;
        if(al == 1){
            var v = p[code];
            return v ? v : code;
        }else if(al == 2){
            p[code] = value;
        }
    }
    if (!window.Hap) {
        Hap = {
            version : '1.0',
            defaultPrompt:{}
        };

        // 右下角的提示框
        Hap.tip = function(message) {
            if (Hap.wintip) {
                Hap.wintip.set('content', message);
                Hap.wintip.show();
            } else {
                Hap.wintip = $.ligerDialog.tip({
                    content : message
                });
            }
            setTimeout(function() {
                Hap.wintip.hide()
            }, 2000);
        };
        // 显示loading
        Hap.showLoading = function(message) {
            message = message || $l('hap.tip.loading');
            $('body').append("<div class='jloading'>" + message + "</div>");
            $.ligerui.win.mask();
        };
        // 隐藏loading
        Hap.hideLoading = function(message) {
            $('body > div.jloading').remove();
            $.ligerui.win.unmask({
                id : new Date().getTime()
            });
        }
        // 显示成功提示窗口
        Hap.showSuccess = function(message, callback) {
            if (typeof (message) == "function" || arguments.length == 0) {
                callback = message;
                message = $l('hap.tip.success');
            }
            $.ligerDialog.success(message, $l('hap.tip.info'), callback);
        };
        // 显示失败提示窗口
        Hap.showError = function(message, callback) {
            if (typeof (message) == "function" || arguments.length == 0) {
                callback = message;
                message = $l('hap.tip.failure');
            }
            $.ligerDialog.error(message, $l('hap.tip.info'), callback);
        };

        // 显示自动关闭的提示窗口,add by shengyang.zhou@hand-china.com
        Hap.showAutoCloseMessage = function (message,callback) {
            if (typeof (message) == "function" || arguments.length == 0) {
                callback = message;
                message = $l('hap.tip.success');
            }
            var manager = $.ligerDialog.open({
                cls       : 'success',
                type      : 'success',
                content   : '<div style="padding:4px">' + message + '</div>',
                allowClose: false
            });
            setTimeout(function () {
                manager.close();
                if(typeof callback == 'function'){
                    callback();
                }
            }, 1000);
        };

        // 覆盖页面grid的loading效果
        Hap.overrideGridLoading = function() {
            $.extend($.ligerDefaults.Grid, {
                onloading : function() {
                    Hap.showLoading($l('hap.tip.loading'));
                },
                onloaded : function() {
                    Hap.hideLoading();
                }
            });
        };
        
        /**
         * 加载表单数据
         * <ul>
         * <li>options.form: ligerForm对象</li>
         * <li>options.url: 提交的url</li>
         * <li>options.method: 提交方法</li>
         * <li>options.para: 参数</li>
         * <li>options.callback: callback</li>
         * </ul>
         * @param options
         */
        Hap.loadForm = function(options){
            var form = options.form,url=options.url,para = options.para;
            if(!form&&!url)return;
            if(!(form instanceof $.ligerui.controls.Form)){
                form = $.ligerui.get(form);
            }
            var fields = form.options.fields;
            $.ajax({
                url : url,
                type : options.method||"POST",
                dataType : "json",
                contentType : "application/x-www-form-urlencoded; charset=UTF-8",
                data : para,
                success : function(json) {
                    if(json&&json.success){
                        var d = json.rows[0]||{};
                        $.each(fields,function(i,f){
                            if(f.type == 'date' && d[f.name]){
                                var v = d[f.name];
                                if(typeof(v)=='string' && f.options && f.options.format){
                                    d[f.name] = v.parseDate(f.options.format);
                                }
                            }
                        })
                        form.setData(d);
                        if(options.callback){
                            options.callback(d,json)
                        }
                    }else{
                        //统一ajax异常处理
                        //$.ligerDialog.alert(json.message || 'Error', options.infoTip || $l('hap.tip.info'), 'error');
                    }
                },
                error : function() {
                    $.ligerDialog.closeWaitting();
                }
            });
        }
        
        /**
         * 
         * 校验form.
         * 
         * <ul>
         * <li>form: 需要校验的form</li>
         * </ul>
         * @param forms
         */
        Hap.validateForm = function(forms){
            var valid = true;
            $.each([].concat(forms),function(i,form){
                if(!(form instanceof $.ligerui.controls.Form)){
                    form = $.ligerui.get(form);
                }
                var mainform = $(form.element);
                if (!mainform.valid()) {
                    valid = false;
                    Hap.showInvalid(mainform);
                    return false;
                }
            })
            return valid;
        }
        
        /**
         * 
         * 校验form.
         * 
         * <ul>
         * <li>form: 需要校验的form</li>
         * </ul>
         * @param forms
         */
        Hap.validateGrid = function(grid){
            var valid = true;
            $.each([].concat(grid),function(i,g){
                if(!(g instanceof $.ligerui.controls.Grid)){
                    g = $.ligerui.get(g);
                }
                // mod by jessen:valid maybe not exists
                if (g.valid instanceof Function&&!g.valid()) {
                    valid = false;
                    Hap.showInvalid(g);
                    return false;
                }
            })
            return valid;
        }
        
        /**
         * 提交表单数据(头行数据可一起提交).
         * 
         * <ul>
         * <li>options.wrapArray: 是否包装成数组</li>
         * <li>options.form: form对象</li>
         * <li>options.grid: grid对象</li>
         * <li>options.gridName: grid数据的name</li>
         * <li>options.url: 提交的url</li>
         * <li>options.success: success回调函数</li>
         * <li>options.failure: failure回调函数</li>
         * </ul>
         * @param options
         */
        Hap.submitForm = function(options) {
            var form = options.form,grids = options.grid ? [].concat(options.grid) : [],gridNames = [].concat(options.gridName),url=options.url,wa = options.wrapArray;
            if (!form || form.con) return;
            if(Hap.validateForm(form) && Hap.validateGrid(grids)){
                var d = form.getData();
                for ( var key in d) {
                    if (!d[key]) {
                        delete d[key]
                    }
                }
                if(grids.length > 0) {
                    $.each(grids,function(i,grid){
                        grid.endEdit();
                        d[gridNames[i]] = grid.getChanges();
                    })                    
                }
                form.con= $.ajax({
                    url : url,
                    type : "POST",
                    dataType : "json",
                    contentType : "application/json",
                    data : JSON2.stringify(wa === false ? d : [d]),
                    success : function(json) {
                        options.json = json;
                        Hap.defaultSuccessHandler(options);
                        form.con = null;
                    },
                    error : function() {
                        $.ligerDialog.closeWaitting();
                        form.con = null;
                    }
                });
            }
        };

        
        /**
         * 默认回调函数
         * <ul>
         * <li>json: json对象</li>
         * <li>options: options对象</li>
         * </ul>
         * @param opt
         */
        Hap.defaultSuccessHandler = function(opt){
            var options = opt||{}, json = options.json;
            $.ligerDialog.closeWaitting();
            if (json && json.success) {

                if(opt.callback) opt.callback(json)
                if(options.success){
                    options.success(json, options)

                }else{
                    var manager = $.ligerDialog.open({ cls: 'success', type: 'success', content: '<div style="padding:4px">'+(options.successTip || $l('hap.tip.success'))+'</div>', allowClose: false });
                    setTimeout(function (){
                        manager.close();
                    }, 1000);
                }
            } else if (json) {                
                if(options.failure) {
                    options.failure(json, options)
                } //统一在下面ajax的部分处理了
                
            }
        }
        
        /**
         * 验证提示信息
         * <ul>
         * <li>validator: validator对象</li>
         * </ul>
         * @param form
         */
        Hap.showInvalid = function(form) {
            if (!form) return;
            if(!(form instanceof $.ligerui.controls.Form) && !(form instanceof $.ligerui.controls.Grid)){
                form = $.ligerui.get(form);
            }
            var validator = form.validator;
            if (!validator)
                return;
            var message = '<div class="invalid">' + validator.errorList.length + $l('hap.tip.invalid_field') + '</div>';
            $.ligerDialog.error(message);
        };
        
        
        /**
         * 验证函数
         * <ul>
         * <li>form: 表单对象</li>
         * <li>options: 扩展属性</li>
         * </ul>
         * @param form
         * @param options
         */
        Hap.validate = function(form, options) {
            jQuery.metadata.setType("attr", "validate");
            if (typeof (form) == "string") form = $(form);
            else if (typeof (form) == "object" && form.NodeType == 1)
                form = $(form);

            options = $.extend({
                onfocusout: function(element) {
                    if($.ligerui.get(element) && $.ligerui.get(element).options.disabled == true) return;
                    if ( !this.checkable(element)) {
                        this.element(element);
                    }
                },
                errorPlacement : function(label, element) {
                    $.data(label,'element',element);
                    if(element[0] && element[0].gridRowCell){
                        element = $(element[0].gridRowCell).addClass("l-text-invalid");
                    }else if (element.hasClass("l-textarea")) {
                        element.addClass("l-textarea-invalid");
                    } else if (element.hasClass("l-text-field")) {
                        element.parent().addClass("l-text-invalid");
                    } else if (element.hasClass("l-grid-row-cell-inner")) {
                        element = element.parent().addClass("l-text-invalid");
                    }
                    $(element).removeAttr("title").ligerHideTip();
                    $(element).attr("title", label.html()).ligerTip({
                        distanceX : 5,
                        distanceY : -3,
                        auto : true
                    });
                },
                success : function(label) {
                    element = $.data(label,'element');
                    if(element[0] && element[0].gridRowCell){   
                        element = $(element[0].gridRowCell).removeClass("l-text-invalid");
                    }else if (element.hasClass("l-textarea")) {
                        element.removeClass("l-textarea-invalid");
                    } else if (element.hasClass("l-text-field")) {
                        element.parent().removeClass("l-text-invalid");
                    } else if (element.hasClass("l-grid-row-cell-inner")) {
                        element = element.parent().removeClass("l-text-invalid");
                    }
                    $(element).removeAttr("title").ligerHideTip();
                }
            }, options || {});
            return form.validate(options);
            //return Hap.validator;
        };
        
        //创建按钮
        Hap.createButton = function (options){
            var p = $.extend({
                appendTo: $('body')
            }, options || {});
            var btn = $('<div class="l-button" style="width:60px"><div class="l-button-l"> </div><div class="l-button-r"> </div> <span></span></div>');
            
            if (p.width) btn.width(p.width);
            if (p.click)btn.click(p.click);
            if (p.text)$("span", btn).html(p.text);
            if (typeof (p.appendTo) == "string") p.appendTo = $(p.appendTo);
            btn.appendTo(p.appendTo);
        };
        
        /**
         * 设置查询按钮
         * <ul>
         * <li>form: 取消回调函数</li>
         * <li>grid: 保存回调函数</li>
         * <li>btn1Container:查询dom容器</li>
         * </ul>
         * @param form
         * @param grid
         * @param btn1Container
         */
        Hap.addSearchButtons = function (form, grid, btn1Container){
            if (!form) return;
            if (btn1Container){
                Hap.createButton({
                    appendTo: btn1Container,
                    text: $l('hap.query'),
                    click: function (){
                        var d = $(form).ligerForm().getData();
                        for ( var key in d) {
                            if (!d[key]) {
                                delete d[key]
                            }
                        }
                        grid.set('parms', d);
                        grid.loadData(1);
                    }
                });
            }
        };
 
        /**
         * 快速设置表单底部默认的按钮:保存、取消.
         * <ul>
         * <li>cancleCallback: 取消回调函数</li>
         * <li>savedCallback: 保存回调函数</li>
         * </ul>
         * 
         * @param cancleCallback
         * @param savedCallback
         */
        Hap.setFormDefaultBtn = function(cancleCallback, savedCallback) {
            var buttons = [];
            if (cancleCallback) {
                buttons.push({
                    text : $l('hap.cancel'),
                    id:'form_btn_cancel',
                    onclick : cancleCallback
                });
            }
            if (savedCallback) {
                buttons.push({
                    text : $l('hap.save'),
                    id:'form_btn_save',
                    onclick : savedCallback
                });
            }
            Hap.addFormButtons(buttons);
        };

        /**
         * 增加表单底部按钮   
         * <ul>
         * <li>buttons:按钮数组</li>
         * </ul>
         * 
         * @param buttons
         *  
         */
        Hap.addFormButtons = function(buttons) {
            if (!buttons)
                return;
            var formbar = $("body > div.form-bar");
            if (formbar.length == 0)
                formbar = $('<div class="form-bar"><div class="form-bar-inner"></div></div>').appendTo('body');
            if (!(buttons instanceof Array)) {
                buttons = [ buttons ];
            }
            $(buttons).each(function(i, o) {   
                var btn = $('<div '+(o.id ? ('id="'+o.id+'"') : '') +' class="l-dialog-btn '+(o.disabled ? 'l-button-disabled' : '')+'"><div class="l-dialog-btn-l"></div><div class="l-dialog-btn-r"></div><div class="l-dialog-btn-inner"></div></div> ');
                $("div.l-dialog-btn-inner:first", btn).html(o.text || "BUTTON");
                if (o.onclick) {
                    btn.bind('click', function() {
                        o.onclick(o);
                    });
                }
                if (o.width) {
                    btn.width(o.width);
                }
                $("> div:first", formbar).append(btn);
            });
        };

        /**
         * 表格查询函数代理.
         * 
         * <ul>
         * <li>options.grid: grid对象</li>
         * <li>options.form: form对象</li>
         * </ul>
         * 
         * @param options
         */
        Hap.gridQuery = function(options) {
            var form = options.form, grid = options.grid;
            if (!form || !grid)
                $.ligerDialog.alert('"grid" or "form" not found in options!', $l('hap.tip.info'), 'error');
            if(!(form instanceof $.ligerui.controls.Form)){
                form = $.ligerui.get(form);
            }
            
            var d = form.getData();
            for ( var key in d) {
                if (!d[key]) {
                    delete d[key]
                }
            }
            grid.set('parms', d);
            grid.loadData(1);
        }

        /**
         * 表格保存函数代理.
         * <ul>
         * <li>options.grid: grid对象</li>
         * <li>options.url: 保存的url</li>
         * <li>options.waitingTip: 等待提示信息</li>
         * <li>options.successTip: 删除成功提示信息</li>
         * <li>options.infoTip:提示标题信息</li>
         * </ul>
         * @param options
         */
        Hap.gridSave = function(options) {
            var grid = options.grid;
            if (!grid) $.ligerDialog.alert('"grid" not found in options!', $l('hap.tip.info'), 'error');
            if(!Hap.validateGrid(grid)) return;
            
            grid.endEdit();
            var rows = grid.getChanges();
            if(rows.length==0 || grid.conn) return;
            $.ligerDialog.waitting(options.waitingTip || $l('hap.tip.processing'));
            grid.conn = $.ajax({
                url : options.url || '',
                type : "POST",
                dataType : "json",
                contentType : "application/json",
                data : JSON2.stringify(rows),
                success : function(json) {
                    options = $.extend(options,{
                        json:json,
                        grid:grid,
                        callback:function(json){
                            var rs = $.isArray(json.rows) ? json.rows :[];
                            if(rs.length >0)
                            $.each(rs,function(i,n){
                                if(n['__status'] == 'delete') return true;
                               var r = grid.records[n['__id']];
                               if(r) r = $.extend(r,n,{'__status':'nochanged'})
                            })
                            grid.deletedRows = [];
                            grid.reRender();
                        }
                    })
                    grid.conn = null;
                    Hap.defaultSuccessHandler(options);
                },
                error : function() {
                    grid.conn = null;
                    $.ligerDialog.closeWaitting();
                }
            });
        }
        
        /**
         * 获取code值.
         * 
         * <ul>
         * <li>data: code数据 </li>
         * <li>value: value</li>
         * </ul>
         * @param data
         * @param value
         * 
         */
        Hap.getCodeMeaning = function(data, value){
            for (var i in data) {
                if (data[i].value == value) {
                    return data[i].meaning;
                }
            }
            return '';
        }
        
        Hap.conns = {}
        
        /**
         * 通用ajax函数.
         * 
         * <ul>
         * <li>options.url: 提交删除的url</li>
         * <li>options.data: 提交数据</li>
         * <li>options.success: 成功success</li>
         * <li>stringify: 是否转json</li>
         * </ul>
         * @param options
         * 
         */
        Hap.ajax = function(options){
            if(Hap.conns[options.url] && Hap.conns[options.url] == JSON2.stringify(options.data)) {
                return;
            }
            Hap.conns[options.url] = JSON2.stringify(options.data);
            return $.ajax({
                url : options.url,
                type : options.type || 'POST',
                dataType : options.dataType ||"json",
                contentType : options.contentType||"application/json",
                data : options.stringify == false ? options.data : JSON2.stringify(options.data),
                opt:options,
                success : function(json) {
                    options = $.extend(options,{
                        json:json
                    })
                    Hap.defaultSuccessHandler(options);
                    delete Hap.conns[options.url];
                },
                error : function() {
                    $.ligerDialog.closeWaitting();
                    delete Hap.conns[options.url];
                }
            });
        }

        /**
         * 表格删除函数代理.
         * 
         * <ul>
         * <li>options.grid: grid对象</li>
         * <li>options.confirmTip: 是否删除提示信息</li>
         * <li>options.url: 提交删除的url</li>
         * <li>options.waitingTip: 等待提示信息</li>
         * <li>options.successTip: 删除成功提示信息</li>
         * <li>options.infoTip:提示标题信息</li>
         * </ul>
         * @param options
         * 
         */
        Hap.gridDelete = function(options) {
            var grid = options.grid;
            if (!grid)$.ligerDialog.alert('"grid" not found in options!', $l('hap.tip.info'), 'error');
            grid.endEdit();
            var rows = grid.getSelectedRows(),dls = [];            
            //TODO:给个提示比较好
            if(rows.length>0)
            $.ligerDialog.confirm(options.confirmTip || $l('hap.tip.delete_confirm'), $l('hap.tip.info'),function(yes) {
                if (yes) {
                    if(options.url){ 
                        var adds = [];
                        $.each(rows,function(i,d){
                            if(d['__status'] == 'add'){
                                adds.push(d);
                                grid.remove(d);
                            }
                        });
                        
                        dls = $.grep(rows,function(item){
                            var isL = false;
                            $.each(adds,function(i,data){
                                if(item['__id'] == data['__id']){
                                    isL = true;
                                    return false;
                                }
                            })
                            return !isL;
                        })
                        
                        if (dls.length == 0) return;
                        $.ligerDialog.waitting(options.waitingTip || $l('hap.tip.processing'));
                        $.ajax({
                            url : options.url || '',
                            type : "POST",
                            dataType : "json",
                            contentType : "application/json",
                            data : JSON2.stringify(dls),
                            success : function(json) {
                                options = $.extend(options,{
                                    json:json,
                                    grid:grid,
                                    callback:function(json){
                                        $.each(dls,function(i,n){
                                            grid.remove(grid.records[n['__id']]);
                                        })
                                    }
                                })
                                Hap.defaultSuccessHandler(options);
                            },
                            error : function() {
                                $.ligerDialog.closeWaitting();
                            }
                        });
                    }else{
                        $.each(rows,function(i,d){
                            if(d['__status'] == 'add'){
                                grid._removeData(grid.getRow(d));
                            }
                        });
                        grid.deleteSelectedRow();
                    }
                }
            });
        }
        /**
         * 表格下拉框选中值渲染代理.
         * 
         * <ul>
         * <li>field: 无下拉列表值时，渲染用到的数据名</li>
         * </ul>
         * @param field
         * 
         */
        Hap.gridSelectRenderer = function(field) {
            return function(item, row, value, column) {
                var data = (item.__combobox_datas && item.__combobox_datas[column.columnname])||(column.editor && column.editor.data), p = column.editor;
                if (data) {
                    for (var i = 0, len = data.length; i < len; i++) {
                        if (value == data[i][p.valueField]) {
                            return data[i][p.textField];
                        }
                    }
                } else {
                    return item[field] || '';
                }
            }
        };

        Hap.gridDateTimeRender = function (rowdata, index, value,obj) {
            if(value){
                value = value.replace(/-/g, '/');
                value = value.replace('T', ' ');
                value = value.replace(/(\+[0-9]{2})(\:)([0-9]{2}$)/, ' UTC\$1\$3');
                value = value.replace(/\.[0-9]{1,3}/,'');
                return new Date(value).format('yyyy-MM-dd HH:mm:ss')
            }
            return '';
        };

        Hap.gridDateRender = function (rowdata, index, value,obj) {
            if(value){
                value = value.replace(/-/g, '/');
                value = value.replace('T', ' ');
                value = value.replace(/(\+[0-9]{2})(\:)([0-9]{2}$)/, ' UTC\$1\$3');
                value = value.replace(/\.[0-9]{1,3}/,'');
                return new Date(value).format('yyyy-MM-dd')
            }
            return '';
        };

        Hap.toggleGridCheckBox = function (para) {
            var grid = $.ligerui.get(para.gid);
            if(!grid.options.enabledEdit)return;
            var row = grid.getRow(para.rowid);
            var col = grid.getColumnByName(para.columnname);
            if(col.readonly==true)return;
            var ep={
                column  : col,
                record  : row,
                value   : row[para.columnname],
                rowindex: row.__index
            };
            if(grid.trigger('beforeEdit',ep)==false)
                return;
            var newValue = para.checkValue;
            if (row[para.columnname] == para.checkValue) {
                newValue = para.uncheckValue;
            }
            grid._setValueByName(row,para.columnname,newValue);
            if(row.__status!='add')row.__status='update';
            grid.changedCells[para.rowid+"_"+col.__id]=true;
            $(grid.getCellObj(row,col)).addClass('l-grid-row-cell-edited');
            grid.isDataChanged=true;
            grid.reRender({rowdata:row});
            ep.value = newValue;
            grid.trigger('afterEdit',ep);
        };

        Hap.createGridCheckBoxRender = function (config) {
            config = config || {};
            var cv = config.checkValue || 'Y';
            var ucv = config.uncheckValue || 'N';
            return function (data, idx, value, col) {
                var cls = 'l-checkbox';
                if (value == cv) cls += ' l-checkbox-checked';
                var grid = this;
                var readonly=false;
                if(!grid.options.enabledEdit)readonly=true;
                var row = grid.getRow(data.__id);
                var readonlyClass = col.readonlyClass||'';
                if(col.readonly==true)readonly=true;
                var ep={
                    column  : col,
                    record  : data,
                    value   : value,
                    rowindex: row.__index
                };
                if(grid.trigger('beforeEdit',ep)==false)
                    readonly=true;
                if(readonly){
                    return "<div class='"+readonlyClass+"' style='text-align: center;'><a href='javascript:void(0);' class='" + cls + "'></a></div>";
                }
                var p = {
                    gid         : this.id,
                    checkValue  : cv,
                    uncheckValue: ucv,
                    rowid       : data.__id,
                    columnname  : col.columnname
                };

                var p_json = JSON2.stringify(p).replace(/"/g, "\'");
                return "<a href='javascript:void(0);' class='" + cls + "' onclick=\"Hap.toggleGridCheckBox(" + p_json + ")\"></a>"
            }
        };

        Hap.createRenderA = function(text,func) {
            if(typeof func=='function')
                func=func.name||func.toString().match(/^function\s*([^\s(]+)/)[1];
            var arr=[];
            $.each(arguments,function(i,r){
                if(i<2)return;
                if(typeof r=='string') arr.push("'"+r+"'");
                else arr.push(r);
            });
            var funcCall = func+'('+arr.join(',')+');return false';
            return '<a href="javascript:void(0);" onclick="'+funcCall+'">'+text+'</a>';
        };
        
        /**
         * 表格下拉框快速编码渲染函数.
         * 
         * @param rowdata
         * @param index
         * @param value
         * @param obj
         * 
         */
        Hap.gridCodeRenderer = function(rowdata, index, value,obj){
            var v = value;
            $.each(obj.editor.data,function(i,n){
                if((n.value||'').toLowerCase() == (value||'').toLowerCase()){
                    v = n.meaning;
                    return false;
                }
            })
            return v;
        };


        var tzOffSet = new Date().getTimezoneOffset();
        Hap.timeZone = {
            getTimezoneOffset:function(){
                return tzOffSet;
            },
            set : function(tz){
                if(!/GMT([+-]\d{4})?/.test(tz))return;
                if(tz.length>3){
                    var sign = tz.charAt(3)=='-'?-1:1;
                    var h = +tz.substring(4,6);
                    var m = +tz.substring(6);
                    tzOffSet = -sign*(h*60+m);
                }else tzOffSet=0;//GMT
            }
        };

        (function(){
            var masks = {  
                "default":      "ddd MMM dd yyyy HH:mm:ss",  
                shortDate:      "M/d/yy",  
                mediumDate:     "MMM d, yyyy",  
                longDate:       "MMMM d, yyyy",  
                fullDate:       "dddd, MMMM d, yyyy",  
                shortTime:      "h:mm TT",  
                mediumTime:     "h:mm:ss TT",  
                longTime:       "h:mm:ss TT Z",  
                isoDate:        "yyyy-MM-dd",  
                isoTime:        "HH:mm:ss",  
                isoDateTime:    "yyyy-MM-dd'T'HH:mm:ss",  
                isoUtcDateTime: "UTC:yyyy-MM-dd'T'HH:mm:ss'Z'",
                standard:       "yyyy-MM-dd HH:mm:ss"
            },
            token = /d{1,4}|M{1,4}|yy(?:yy)?|([HhmsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,  
            timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,  
            timezoneClip = /[^-+\dA-Z]/g,  
            pad = function (val, len) {  
                val = String(val);  
                len = len || 2;  
                while (val.length < len) val = "0" + val;  
                return val;  
            },
            hasTimeStamp = function(mask,token){
                return !!String(masks[mask] || mask || masks["default"]).match(token);
            },
            _parseDate=function(string,mask,fun){
                for(var i=0,arr=mask.match(token),numbers=string.match(/\d+/g),value,index=0;i<arr.length;i++){
                    if(numbers.length==arr.length)value=numbers[i];
                    else if(numbers.length == 1)value=parseInt(string.slice(index,index+=arr[i].length),10);
                    else value=parseInt(string.slice(index=mask.search(arr[i]),index+arr[i].length));
                    switch(arr[i]){
                        case "MM":;
                        case "M":value--;break;
                    }
                    fun(arr[i],value);
                }
            },
            dateFormat = {
                parseDate:function(string,mask,utc){
                    if(typeof string!="string"||string=="")return null;
                    mask = String(masks[mask] || mask || masks["default"]); 
                    if (mask.slice(0, 4) == "UTC:") {  
                        mask = mask.slice(4);  
                        utc = true;  
                    }
                    var date=new Date(1970,1,2,0,0,0),
                        _ = utc ? "setUTC" : "set",  
                        d = date[_ + "Date"],  
                        M = date[_ + "Month"],  
                        yy = date[_ + "FullYear"], 
                        y = date[_ + "Year"], 
                        H = date[_ + "Hours"],  
                        m = date[_ + "Minutes"],  
                        s = date[_ + "Seconds"],  
                        L = date[_ + "Milliseconds"],  
                        //o = utc ? 0 : date.getTimezoneOffset();
                        flags = {  
                            d:    d,  
                            dd:   d,
                            M:    M,  
                            MM:   M,  
                            yy:   y,  
                            yyyy: yy,  
                            h:    H,  
                            hh:   H,  
                            H:    H,  
                            HH:   H,  
                            m:    m,  
                            mm:   m,  
                            s:    s,  
                            ss:   s,  
                            l:    L,  
                            L:    L
                        }; 
                        try{
                            _parseDate(string,mask,function($0,value){
                                flags[$0].call(date,value);
                            });
                        }catch(e){throw new SyntaxError("invalid date");}
                        if (isNaN(date)) throw new SyntaxError("invalid date"); 
                        return date;
                },
                format:function (date, mask, utc) {    
                    if (arguments.length == 1 && (typeof date == "string" || date instanceof String) && !/\d/.test(date)) {  
                        mask = date;  
                        date = undefined;  
                    }   
                    date = date ? new Date(date) : new Date();  
                    if (isNaN(date)) throw new SyntaxError("invalid date");  
              
                    mask = String(masks[mask] || mask || masks["default"]);  
                    if (mask.slice(0, 4) == "UTC:") {  
                        mask = mask.slice(4);  
                        utc = true;  
                    }  
              
                    var _ = utc ? "getUTC" : "get",  
                        d = date[_ + "Date"](),  
                        D = date[_ + "Day"](),  
                        M = date[_ + "Month"](),  
                        y = date[_ + "FullYear"](),  
                        H = date[_ + "Hours"](),  
                        m = date[_ + "Minutes"](),  
                        s = date[_ + "Seconds"](),  
                        L = date[_ + "Milliseconds"](),  
                        o = utc ? 0 : Hap.timeZone.getTimezoneOffset(),
                        flags = {  
                            d:    d,  
                            dd:   pad(d),
                            M:    M + 1,  
                            MM:   pad(M + 1),  
                            yy:   String(y).slice(2),  
                            yyyy: y,  
                            h:    H % 12 || 12,  
                            hh:   pad(H % 12 || 12),  
                            H:    H,  
                            HH:   pad(H),  
                            m:    m,  
                            mm:   pad(m),  
                            s:    s,  
                            ss:   pad(s),  
                            l:    pad(L, 3),  
                            L:    pad(L > 99 ? Math.round(L / 10) : L),  
                            t:    H < 12 ? "a"  : "p",  
                            tt:   H < 12 ? "am" : "pm",  
                            T:    H < 12 ? "A"  : "P",  
                            TT:   H < 12 ? "AM" : "PM",  
                            Z:    utc ? "UTC" : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, ""),  
                            o:    (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),  
                            S:    ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]  
                        }; 
                    return mask.replace(token, function ($0) {  
                        return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);  
                    });  
                },
                isDateTime:function(mask){
                    return hasTimeStamp(mask,/([HhMs])\1?/);
                }
            };
            
            String.prototype.parseDate = function(mask,utc){
                return dateFormat.parseDate(this.toString(),mask,utc);
            }
            Date.prototype.format = function(mask, utc){
                var dtz = this.getTimezoneOffset()-Hap.timeZone.getTimezoneOffset();
                if(dtz==0)
                    return dateFormat.format(this, mask, utc);
                else
                    return dateFormat.format(new Date(this.getTime()+dtz*60000),mask,utc);
            }

        })();
    }
    
    
    //处理ajax异常
    $(document).ajaxSuccess(function(event,xhr,options,json){
        if (json && json.success === false) {
            if(json.code == 'session_expired') {
                if(top.sessionExpiredLogin){
                    top.sessionExpiredLogin();
                }else{
                    alert($l('hap.tip.session_expired'));
                    top.location.href = _basePath;
                }
            }else if(!options.opt||!options.opt.failure){
                $.ligerDialog.alert(json.message || 'Error', $l('hap.tip.info'), 'error');
            }
        }
    }).ajaxError(function(event, XMLHttpRequest, ajaxOptions, thrownError){
        if(!ajaxOptions.error)
        $.ligerDialog.alert(thrownError, ''+XMLHttpRequest.status, 'error');
    });
    
    
    /////////////////////LigerUI扩展//////////////////////
    /**
     * 时区问题
     * 客户的时区是注册时候定义好的,在服务端做转换处理？？
     * @returns text
     */
    Date.prototype.toJSON = function(){
        function f(n) {
            // Format integers to have at least two digits.
            return n < 10 ? '0' + n : n;
        }
        return this.getFullYear()   + '-' +
        f(this.getMonth() + 1) + '-' +
        f(this.getDate())      + ' ' +
        f(this.getHours())     + ':' +
        f(this.getMinutes())   + ':' +
        f(this.getSeconds())
    };
    $.fn.ligerForm = function ()
    {
        var _form =  $.ligerui.run.call(this, "ligerForm", arguments);
        $('<input style="display: none;" type="text" />').appendTo(_form.element); 
        _form.validator = Hap.validate($(_form.element));
        return _form;
    };
    $.fn.ligerGrid = function ()
    {
        var _grid = $.ligerui.run.call(this, "ligerGrid", arguments),
            form = _grid.form = $('<form><input style="display: none;" type="text" /></form>').appendTo(_grid.element)[0];
        _grid.toolbar.appendTo(form);
        $.data(form,'validator',_grid.validator = Hap.validate($(_grid.element)));
        setTimeout(function(){
            _grid._onResize();
        },10);
        return _grid;
    };
    $.extend($.validator.prototype,{
       grid:function(){
           this.checkGrid();
           $.extend(this.submitted, this.errorMap);
           this.invalid = $.extend({}, this.errorMap);
           this.showErrors();
           return this.valid();
       },
       checkGrid: function() {
           this.prepareGrid();
           for ( var i = 0,rows = this.currentGrid.rows,columns = this.columns();rows[i];i++){
               this.checkGridRow(rows[i],columns);
           }
           return this.valid();
       },
       checkGridRow: function(row,columns){
           var g = this,grid = g.currentGrid,gridId = grid.id;
           $.each(columns,function(index,column){
               var element = grid.getCellObj(row,column).firstChild;
               element.value = $.isEmpty(row[column.columnname])?'':String(row[column.columnname]);
               element.name = column.columnname;
               element.form = grid.form; 
               element.row = row;
               g.check($(element));
           });
       },
       columns: function(){
           return this.currentGrid.columns.filter(function(column){
              if(column.validate) {
                  return column;
              }
           });
       },
       prepareGrid: function(){
           this.reset();
           this.currentGrid = liger.get(this.currentForm.id);
       }
    });
//    var defaultConfig = {
//            height:23
//    }
//    $.extend($.ligerDefaults.TextBox,defaultConfig);
//    $.extend($.ligerDefaults.DateEditor,defaultConfig);
//    $.extend($.ligerDefaults.PopupEdit,defaultConfig);
//    $.extend($.ligerDefaults.ComboBox,defaultConfig);
//    $.extend($.ligerDefaults.Spinner,defaultConfig);
    $.extend($.ligerDefaults.Grid,{
        root: 'rows',
        record: 'total',
        pageSize: 10
    })
    $.extend($.ligerDefaults.Form,{
        rightToken: '',
        labelCss:'l-label',
        //标签对齐方式
        labelAlign: 'right'
    })
    //增加html的编辑器
    $.ligerDefaults.Form.editors.html =
    {
        create: function (container, editParm, p)
        {   
            if(editParm.field.render){
               var editor = $(editParm.field.render.call(this,''));
               container.append(editor);
            }            
            return editor;
        },
        setValue: function (editor, value, editParm)
        {
            if(editParm.field.render){
                var html = editParm.field.render.call(this,value);
                editor.parent().html(html);
             }
        },
        getValue: function (editor, editParm)
        {
            return '';
        }
    };
    //扩展默认grid函数
    $.extend($.ligerui.controls.Grid.prototype, {
        formatRecord: function (o, removeStatus){
            //delete o['__id'];
            delete o['__previd'];
            delete o['__nextid'];
            delete o['__index'];
            delete o['__combobox_datas'];
            if (this.options.tree)
            {
                //delete o['__pid'];
                delete o['__level'];
                delete o['__hasChildren'];
            }
            //if (removeStatus) delete o[this.options.statusName];
            return o;
        },
        _initBuildHeader : function() {
            var g = this, p = this.options;
            if (p.title) {
                $(".l-panel-header-text", g.header).html(p.title);
                if (p.headerImg)
                    g.header.append("<img src='" + p.headerImg + "' />").addClass("l-panel-header-hasicon");
            } else {
                g.header.hide();
            }
            if (p.toolbar) {
                if ($.fn.ligerToolBar)
                    g.toolbarManager = g.topbar.ligerToolBar(p.toolbar);
            } else {
                g.topbar.remove();
            }
        },
        addEditRow : function(rowdata) {
            var g = this;
            rowdata = g.add(rowdata);
            return g.beginEdit(rowdata);
        },
        getEditingRow : function() {
            var g = this;
            for (var i = 0, l = g.rows.length; i < l; i++) {
                if (g.rows[i]._editing)
                    return g.rows[i];
            }
            return null;
        },
        valid: function ()
        {
            var g = this, p = this.options,validator = g.validator;
            if (!validator) return true;
            return validator.grid(); 
        },
        setEditable:function(isEditable){
            this.set('enabledEdit',isEditable);
            if(this.currentData)
                this.reRender();
        }
    });
    //扩展默认form函数
    $.extend($.ligerui.controls.Form.prototype, {
        clear: function(){
            var g = this,
                p = g.options,
                data = {};
            $.each(p && p.fields,function(index,field){
                data[(p.prefixID || "")+field.name] = '';
            });
            if(!$.isEmptyObject(data))
                g.setData(data);
        },
        reset: function(){
            var g = this,
                p = g.options,
                data = {};
            $.each(p && p.fields,function(index,field){
                var fp = field.options;
                data[(p.prefixID || "")+field.name] = (fp && fp.value)||'';
            });
            if(!$.isEmptyObject(data))
                g.setData(data);
        },
        setEditable:function(isEditable){
            this.setEnabled((this.options.fields||[]).map(function(field){
                return field.name;
            }),isEditable);
            $.each(this.autoEditors||[],function(index,editor){
                editor.control[isEditable?'setEnabled':'setDisabled']();
            });
            $.each(this.options.buttons||[],function(index,button){
                liger.get(button.id).set('disabled',!isEditable);
            });
        },
        validField: function(name){
            this.validator.element(this.getEditor(name).element); 
        }
    });
    //扩展默认toolbar函数
    $.extend($.ligerui.controls.ToolBar.prototype, {
        setEditable:function(isEditable){
            var g = this;
            $.each(g.options.items,function(index,item){
                g[isEditable?'setEnabled':'setDisabled'](item.id);
            });
        }
    });
    //扩展默认popupEdit函数
    $.extend($.ligerui.controls.PopupEdit.prototype, {
        _initAutoComplete : function(){
            var g = this,p = g.options;
            if(!g.autoView){
                g.autoView = $('<div class="l-autocomplete-view"></div>').appendTo('body');
            }
            !p.autocompleteField && $.each(p.grid.columns,function(index,column){
                if(column.isAutoComplete){
                    p.autocompleteField = column.name;
                }
            });
        },
        _paintAutoComplete : function(data){
            var g = this,
                view = g.autoView;
            if(data.total){
                var columns = g.options.grid.columns,
                    html=['<table cellpadding="0" cellspacing="0" width="100%"><thead class="l-grid-header"><tr class="l-grid-hd-row">'];
                $.each(columns,function(index,column){
                    if(column.autocompleteField){
                        html.push('<td class="l-grid-hd-cell" style="height:28px"><div class="l-grid-hd-cell-inner" style="height:28px"><span class="l-grid-hd-cell-text">',column.display,'</span></div></td>');
                    }
                });
                html.push('</tr></thead><tbody class="l-grid-body">');
                $.each(data.rows,function(i,row){
                    html.push('<tr tabIndex="',i,'" class="l-grid-row');
                    if(i%2 == 1){
                        html.push(' l-grid-row-alt');
                    }
                    html.push('">');
                    $.each(columns,function(index,column){
                        if(column.autocompleteField){
                            html.push('<td class="l-grid-row-cell');
                            if(index == columns.length -1){
                                html.push(' l-grid-row-cell-last');
                            }
                            html.push('" style="height:28px"><div class="l-grid-row-cell-inner" style="height:28px;text-align:',column.align||'center','">',row[column.name],'</div></td>');
                        }
                    });
                    html.push('</tr>');
                });
                html.push('</tbody></table>');
                view.html(html.join(''));
                if(view.width() < g.wrapper.width()){
                    view.width(g.wrapper.width());
                }
            }else{
                view.html('<div style="text-align:center;line-height:26px;height:28px;width:'+g.wrapper.width()+'px">N/A</div>');
            }
            g.selectedIndex = null;
        },
        setParm:function(name, value){
            $.ligerui.controls.Grid.prototype.setParm.call(this,name,value);
        },
        _autoCompleteQuery : function(value,callback){
             if($.isEmpty(value))return;
             var g = this, o = g.options,p = o.grid;
             if(p.onLoadData)p.onLoadData.call(g);
             var url = p.url;
             if ($.isFunction(url)) url = url.call(g);
             var urlParms = $.isFunction(p.urlParms) ? p.urlParms.call(g) : p.urlParms;
             if (urlParms)
             {
                 for (name in urlParms)
                 {
                     url += url.indexOf('?') == -1 ? "?" : "&";
                     url += name + "=" + urlParms[name];
                 }
             }
             var parms = $.extend(o.parms||{},{
                 pagesize:10,
                 page:1
             });
             parms[o.autocompleteField] = value;
             $.ajax({
                 type: p.method||'post',
                 url: url,
                 data: parms,   
                 dataType: 'json',
                 success: function (data)
                 {
                     if (!data)
                     {
                         p.data = {};
                         return;
                     }
                     p.data = data;
                 },
                 complete: function(){
                     if(callback){
                         callback.call(g,p.data);
                     }else{
                         g._paintAutoComplete(p.data);
                         g._showAutoComplete();
                     }
                 },
                 error: function (XMLHttpRequest, textStatus, errorThrown)
                 {
                     p.data = {};
                 }
             });
        },
        _showAutoComplete:function(){
            var g = this,p = g.options,
                grid = p.grid,
                input = g.wrapper,
                view = g.autoView,
                pos = input.offset(),
                height = input.height(),
                win = $(window),
                viewportwidth = win.width(),
                viewportheight = win.height(),
                scrolltop = win.scrollTop(),
                scrollleft = win.scrollLeft(),
                viewwidth = view.width(),
                viewheight = view.height(),
                x = pos.left,y = pos.top;
            if(x + viewwidth > viewportwidth + scrollleft){
                x = viewportwidth - viewwidth - 30;
            }
            if(y + height + viewheight > viewportheight + scrolltop && y - viewheight>0){
                y -= viewheight;
            }else{
                y += height;
            }
            view.offset({
                left:x,
                top:y
            }).unbind('mouseover click').bind('mouseover',function(e){ 
                !$.isEmpty(g.selectedIndex) && $(g._getNode(g.selectedIndex)).removeClass('l-grid-row-over');
                var tr = $(e.target).parents('tr.l-grid-row');
                if(tr.length){
                    g.selectedIndex = Number(tr.addClass('l-grid-row-over').attr('tabIndex'));
                }else{
                    g.selectedIndex = null;
                }
            }).bind('click',function(e){
                g._commitSelected();
            });
            $(document).unbind('mousedown.autocomplete').bind('mousedown.autocomplete',function(e){
                if(!e || (!input.find(e.target).length && !view.find(e.target).length)){
                    g._hideAutoComplete();
                }
            });
            g.autoShown = true;
        },
        _hideAutoComplete:function(){
            var g = this;
            g.autoView.offset({
                left:-10000,
                top:-10000
            }).unbind('mouseover click');
            $(document).unbind('mousedown.autocomplete');
            g.autoShown = false;
        },
        _getNode: function(index){
            var nodes = this.autoView.find('tr.l-grid-row'),l = nodes.length;
            if(index >= l) index =  index % l;
            else if (index < 0) index = l + index % l;
            return nodes[index];
        },
        _commitSelected: function(){
            var g = this;
            if($.isEmpty(g.selectedIndex)){
                return;
            }
            var p = g.options,
                grid = p.grid,
                data = grid.data.rows[g.selectedIndex],
                e = {
                    data : [data],
                    value : data[p.valueField],
                    text : data[p.textField]
                };
            if (g.trigger('select', e) == false) return;   
            g.setValue(e.value);
            g.setText(e.text);
            g.trigger('selected', e);
            g._hideAutoComplete();
            g.selectedIndex = null;
        },
        _selectItem:function(index,focus){
            if($.isEmpty(index)||index < -1){
                return;
            }
            var g = this,
                node = g._getNode(index),
                selectedIndex = g.selectedIndex;
            if(node && (index = node.tabIndex)!=selectedIndex){
                !$.isEmpty(selectedIndex) && $(g._getNode(selectedIndex)).removeClass('l-grid-row-over');
                g.selectedIndex = index;
                $(node).addClass('l-grid-row-over');
            }
        },
        _setPopup: function(value){
            var g = this,p = g.options;
            if(value){
                g.link.show();
                if(p.cancelable){
                    g._setCancelable(true);
                }
            }else{
                g.link.hide();
                g._setCancelable(false);
            }
        }
    });
})(jQuery)
