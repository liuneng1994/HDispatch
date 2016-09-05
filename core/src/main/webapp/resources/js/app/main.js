var tab = null;
var accordion = null;
var tree = null;
var tabItems = [];
$(function ()
{

	//布局
	$("#layout1").ligerLayout({ leftWidth: 190, height: '100%',heightDiff:-34,space:4, onHeightChanged: f_heightChanged });

	var height = $(".l-layout-center").height();

	//Tab
	$("#framecenter").ligerTab({
		height: height,
		showSwitchInTab : true,
		showSwitch: true,
		onAfterAddTabItem: function (tabdata)
		{
			tabItems.push(tabdata);
		},
		onAfterRemoveTabItem: function (tabid)
		{
			for (var i = 0; i < tabItems.length; i++)
			{
				var o = tabItems[i];
				if (o.tabid == tabid)
				{
					tabItems.splice(i, 1);
					break;
				}
			}
		}
	});

	//面板
	$("#accordion1").ligerAccordion({
		height: height - 31, speed: null
	});
	
	$(".l-link").hover(function ()
	{
		$(this).addClass("l-link-over");
	}, function ()
	{
		$(this).removeClass("l-link-over");
	});

	tab = liger.get("framecenter");
	accordion = liger.get("accordion1");
	$("#pageloading").hide();
	
	
	function createMenu(html,datas){
	    for(var i=0;i<datas.length;i++){
	        var data = datas[i];
            html.push('<li>');
            if(data.children){
                html.push('<a href="javascript:;">');
                html.push('<i class="'+(data.icon||'')+'"></i>');
                html.push('<span class="title">');
                html.push(data.text);
                html.push('</span><span class="arrow "></span></a><ul class="sub-menu">');
                createMenu(html,data.children);
                html.push('</ul>')
            } else if(data.url){
                html.push('<a id="link'+data.id+'" href="javascript:f_addTab(\''+data.functionCode+'\',\''+data.text+'\', \''+data.url+'\')">');
                html.push('<i class="'+(data.icon||'')+'"></i>');
                html.push(data.text);
                html.push('</a>')
            }
            html.push('</li>')
        }
	}
	//menu
    $.ajax({
        type: 'GET',
        url:_basePath + '/sys/function/menus',
        contentType: "application/json; charset=utf-8",
        success: function (datas) {
            var html=[];
            datas = [].concat(datas);
            createMenu(html,datas);
            $(".page-sidebar-menu").append(html.join(''));
        }
    });
	
	
	
	//main index
/*	$.ajax({
        type: 'GET',
        url:_basePath+"/sys/dashboard/functionshortcut/query",
        success: function (data) {
            var divStr="";
            //console.info(data.length);
            for(var i=0;i<data.length;i++){
            		divStr=divStr+'<div class="maindiv" onclick="f_addTab(\''+data[i].id+'\',\''+data[i].text+'\', \''+data[i].url+'\')"><span class="divcontent">'+data[i].text+'</span></div>';
      
            	
            }
           $("#home").append(divStr);
        },
        contentType: "application/json; charset=utf-8"

    });*/
	
});
function f_heightChanged(options)
{
	if (tab)
		tab.addHeight(options.diff);
	if (accordion && options.middleHeight - 24 > 0)
		accordion.setHeight(options.middleHeight - 24);
}
function f_addTab(tabid, text, url)
{
	setTimeout(function(){

		tab.addTabItem({
			tabid: tabid,
			text: text,
			url: url
		});
		$('.page-sidebar-menu').find('a.active').removeClass('active');
		$('#link'+tabid).addClass('active')

	},100);
}
function f_removeTab(tabId){
	setTimeout(function() {
		tab.removeTabItem(tabId);
	},100);
}
function f_removeCurrentTab(){
	f_removeTab(tab.getSelectedTabItemID());
}
