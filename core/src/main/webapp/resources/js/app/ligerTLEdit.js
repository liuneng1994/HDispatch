(function ($)
{

    $.fn.ligerTLEdit = function (options)
    {
        return $.ligerui.run.call(this, "ligerTLEdit", arguments);
    };

    $.fn.ligerGetTLEditManager = function ()
    {
        return $.ligerui.run.call(this, "ligerGetTLEditManager", arguments);
    };

    $.ligerDefaults.TLEdit = {
        valueFieldID: null,     //生成的value input:hidden 字段名
        css: null,             //附加css
        nullText: null,         //不能为空时的提示
        disabled: false,        //是否无效
        cancelable: true,
        width: 200,
        heigth: null,
        textField: 'text',   //显示字段
        readonly: false 
    };

    //扩展方法
    $.ligerMethos.TLEdit = $.ligerMethos.TLEdit || {};

    $.ligerui.controls.TLEdit = function (element, options)
    {
        $.ligerui.controls.TLEdit.base.constructor.call(this, element, options);
    };
    $.ligerui.controls.TLEdit.ligerExtend($.ligerui.controls.Input, {
        __getType: function ()
        {
            return 'TLEdit';
        },
        _extendMethods: function ()
        {
            return $.ligerMethos.TLEdit;
        },
        _init: function ()
        {
            $.ligerui.controls.TLEdit.base._init.call(this);
        },
        _render: function ()
        {
            $.ligerui.controls.TLEdit.base._render.call(this); 
            var g = this, p = this.options;
            g.inputText = null;
            //文本框初始化
            if (this.element.tagName.toLowerCase() == "input")
            {
                this.element.readOnly = true;
                g.inputText = $(this.element);
                g.textFieldID = this.element.id;
            }
            if (g.inputText[0].name == undefined || g.inputText[0].name == "undefined") g.inputText[0].name = g.textFieldID;
            //隐藏域初始化
            g.valueField = null;
            
            //开关
            g.link = $('<div class="l-trigger"><div class="l-trigger-icon"></div></div>');
            //外层
            g.wrapper = g.inputText.wrap('<div class="l-text l-text-tl"></div>').parent(); 
            g.wrapper.append('<div class="l-text-l"></div><div class="l-text-r"></div>');
            g.wrapper.append(g.link);
            g.inputText.addClass("l-text-field");
            //开关 事件
            g.link.hover(function ()
            {
                if (p.disabled) return;
                this.className = "l-trigger-hover";
            }, function ()
            {
                if (p.disabled) return;
                this.className = "l-trigger";
            }).mousedown(function ()
            {
                if (p.disabled) return;
                this.className = "l-trigger-pressed";
            }).mouseup(function ()
            {
                if (p.disabled) return;
                this.className = "l-trigger-hover";
            }).click(function ()
            { 
                if (p.disabled) return; 
                var row,data,name;
                if(p.host_form){  
                    name = p.id,row = p.host_form.getData(); 
                }else if(p.host_grid){
                    name = p.host_grid_column.name,row = p.host_grid_row;
                }
                data = (row['__tls']||{})[name]||{};
                if(g.getValue()) data[_locale] = g.getValue(); 
                $.ligerDialog.open({
                    height : 300,
                    width : 500,
                    title : p.title||$l('hap.multilanguage_editor')||'输入多语言信息',
                    url : _basePath+'/sys/sys_multilanguage_editor.html?id='+(row[p.idField]||'')+'&dto='+p.dto + '&field='+name,
                    data : data,
                    buttons : [ {
                            text : $l("hap.ok"), 
                            onclick : function(item, dialog) {
                                var form = dialog.frame.tl_form;
                                if (form.valid()) {
                                    if(p.callback) {
                                        p.callback(form.getData());
                                    }else{
                                        var d = form.getData(), tls = row['__tls'];
                                        if(!tls){
                                            tls = {};
                                            row['__tls'] = tls; 
                                        }
                                        g.setValue(d[_locale]);
                                        tls[name] = d;
                                        if(row['__status'] == "nochanged"){ 
                                            row['__status'] = 'update';
                                        }
                                    }
                                    dialog.close();
                                } 
                            }
                        }, {
                            text : $l("hap.cancel"),
                            onclick : function(item, dialog) {
                                dialog.close();
                            }
                        }
                    ]
                })
            });
            g.inputText.click(function ()
            {
                if (p.disabled) return; 
            }).blur(function ()
            {
                if (p.disabled) return;
                g.wrapper.removeClass("l-text-focus");
            }).focus(function ()
            {
                if (p.disabled) return;
                g.wrapper.addClass("l-text-focus");
            });
            g.wrapper.hover(function ()
            {
                if (p.disabled) return;
                g.wrapper.addClass("l-text-over");
            }, function ()
            {
                if (p.disabled) return;
                g.wrapper.removeClass("l-text-over");
            });

            g.set(p);
        },
        destroy: function ()
        {
            if (this.wrapper) this.wrapper.remove();
            this.options = null;
            $.ligerui.remove(this);
        },
        clear: function ()
        {
            var g = this, p = this.options;
            g.inputText.val("");
        },
        _setCss: function (css)
        {
            if (css)
            {
                this.wrapper.addClass(css);
            }
        },
        //取消选择 
        _setCancelable: function (value)
        {
            var g = this, p = this.options;
            if (!value && g.unselect)
            {
                g.unselect.remove();
                g.unselect = null;
            }
            if (!value && !g.unselect) return;
            g.unselect = $('<div class="l-trigger l-trigger-cancel"><div class="l-trigger-icon"></div></div>').hide();
            g.wrapper.hover(function ()
            {
                g.unselect.show();
            }, function ()
            {
                g.unselect.hide();
            })
            if (!p.disabled && p.cancelable)
            {
                g.wrapper.append(g.unselect);
            }
            g.unselect.hover(function ()
            {
                this.className = "l-trigger-hover l-trigger-cancel";
            }, function ()
            {
                this.className = "l-trigger l-trigger-cancel";
            }).click(function ()
            {
                g.clear();
            });
        },
        _setDisabled: function (value)
        {
            if (value)
            {
                this.wrapper.addClass('l-text-disabled');
            } else
            {
                this.wrapper.removeClass('l-text-disabled');
            }
        },
        _setWidth: function (value)
        {
            var g = this;
            if (value > 20)
            {
                g.wrapper.css({ width: value });
                g.inputText.css({ width: value - 20 });
            }
        },
        _setHeight: function (value)
        {
            var g = this;
            if (value > 10)
            {
                g.wrapper.height(value);
                g.inputText.height(value - 2);
            }
        },
        _getValue: function ()
        {
            return $(this.inputText).val();
        },
        getValue: function ()
        {
            return this._getValue(); 
        },
        setValue: function (value)
        {
            var g = this;
            g.inputText.val(value);
        }
    });
})(jQuery);