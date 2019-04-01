var mainPlatform = {

	init: function(){

		this.bindEvent();
		this._createTopMenu();
	},

	bindEvent: function(){
		var self = this;
		// 顶部大菜单单击事件
		$(document).on('click', '.pf-nav-item', function() {
            $('.pf-nav-item').removeClass('current');
            $(this).addClass('current');

            // 渲染对应侧边菜单
            var m = $(this).data('sort');
            self._createSiderMenu(SystemMenu[m], m);
        });

		//点击一级菜单，隐藏其他菜单，同时应用滑动效果
        $(document).on('click', '.sider-nav .pf-menu-title', function() {

        	//$(this) = .pf-menu-title
			$(this).next().slideDown("fast");
			$(this).parent().addClass("current");
			$(this).parent().siblings().find("ul").slideUp("fast");
			$(this).parent().siblings().removeClass("current");


            // if is no-child
            if($(this).closest('.no-child').size() > 0) {
            	var index = $(this).closest('.pf-sider').attr('arrindex');
	        	if($('.easyui-tabs1[arrindex='+ index +']').tabs('exists', $(this).find('.sider-nav-title').text())){
	        		$('.easyui-tabs1[arrindex='+ index +']').tabs('select', $(this).find('.sider-nav-title').text())
	        		return false;
	        	}
	        	$('.easyui-tabs1[arrindex='+ index +']').tabs('add',{
					title: $(this).find('.sider-nav-title').text(),
					content: '<iframe class="page-iframe" src="'+ $(this).closest('.no-child').data('href') +'" frameborder="no" border="no" height="100%" width="100%" style="width:100%;height:100%" scrolling="auto"></iframe>',
					closable: true
				});
            }
            //$('iframe').attr('src', $(this).data('src'));
        });

        $(document).on('click', '.sider-nav-s > li', function(e){


        	var index = $(this).closest('.pf-sider').attr('arrindex');
        	var titleName = $(this).find('a').eq(0).text();
        	var url = $(this).data('href');
        	var $this = $(this);
        	var $target = $(e.target);

        	if($target.closest('ul').hasClass('sider-nav-t')) {
        		return;
        	}

        	$(this).closest('.pf-sider').find('.active').removeClass('active');
    		$(this).addClass('active');

        	if(!url) {

        		$this.toggleClass('no-child');
        		return;

        	}

        	if($('.easyui-tabs1[arrindex='+ index +']').tabs('exists', titleName)){
        		$('.easyui-tabs1[arrindex='+ index +']').tabs('select', titleName)
        		return;
        	}
        	$('.easyui-tabs1[arrindex='+ index +']').tabs('add',{
				title: titleName,
				content: '<iframe class="page-iframe" src="'+ $(this).data('href') +'" frameborder="no" border="no" height="100%" width="100%" scrolling="auto" style="width:100%;height:100%"></iframe>',
				closable: true
			});

        	var id = $(this).attr("id");
        	var name = $(this).attr("name");
        	var url = $(this).attr("url");
            var islogineddisplay = $(this).attr("islogineddisplay");
            var action = $(this).attr("action");
            var icon = $(this).attr("icon");

            self._getMenuInfo(id,name,url,islogineddisplay,action,icon);

        });

        $(document).on('click', '.sider-nav-t > li', function(e){

        	var index = $(this).closest('.pf-sider').attr('arrindex');
        	var titleName = $(this).find('a').eq(0).text();
        	var url = $(this).data('href');
        	var $this = $(this);

        	$(this).closest('.pf-sider').find('.active').removeClass('active');
    		$(this).addClass('active');	

        	if(!url) {

        		return;

        	}

        	if($('.easyui-tabs1[arrindex='+ index +']').tabs('exists', titleName)){
        		$('.easyui-tabs1[arrindex='+ index +']').tabs('select', titleName)
        		$(this).parent().closest('li').addClass('active');
        		return;
        	}
        	$('.easyui-tabs1[arrindex='+ index +']').tabs('add',{
				title: titleName,
				content: '<iframe class="page-iframe" src="'+ $(this).data('href') +'" frameborder="no" border="no" height="100%" width="100%" scrolling="auto" style="width:100%;height:100%"></iframe>',
				closable: true
			});

        	$(this).parent().closest('li').addClass('active');

			e.stopPropagation();
        });



        //左侧菜单收起
		$(document).on("click",".sider_fold_btn",function(){

            var f_width = parseInt($('.pf-sider').css('width'));//侧边栏宽度
            var t_width = parseInt($('.tabs-panels').css('width'))+f_width+'px';
            if($("#pf-bd").css("left")=="0px"){

                $("#pf-bd").animate({left:-224},300,function(){
                    $(window).resize();
				});
                $('.tabs-container,.tabs-header,.tabs-panels,.panel,.panel-body').css('width',t_width);
                $(this).attr("src","/images/back_btn_right.png");
				$(this).attr("title","点击展示菜单栏");

            }else{

                $("#pf-bd").animate({left:0},300,function(){
                    $(window).resize();
				});
                $(this).attr("src","/images/back_btn_left.png");
                $(this).attr("title","点击收起菜单栏");
            }


		});


		//置顶区收起
		$(document).on("click",".back_btn",function(){

			var DOM = $(".top_area ");
			if(DOM.height()==0){

				DOM.animate({height:160},300,function(){

                    $(window).resize();
				});
				$(this).attr("src","/images/back_btn_bottom.png");
                $(this).css("bottom","-1px");
                $(this).attr("title","点击收起置顶区");

			}else{

                DOM.animate({height:0},300,function(){

                    $(window).resize();
				});
                $(this).attr("src","/images/back_btn_top.png");
                $(this).css("bottom","-9px");
                $(this).attr("title","点击展开置顶区");
			}


		});

        $('.pf-logout a').on('click', function() {
        	$.messager.confirm({
        		title: '对话框',
        		ok: '确定',
        		cancel: '取消',
        		msg: '你确定要退出系统吗？',
        		fn: function(r) {

        			if(r) {

        				window.location.href = 'login.html';

        			}

        		}
        	});
        	$('.messager-window').find('.l-btn-small').eq(0).addClass('l-btn-selected');
        })

         //关闭当前
	     $('#mm-tabclose').click(function(){
	         var currtab_title = $('#mm').data("currtab");
	         $(".easyui-tabs1:visible").tabs('close',currtab_title);
	     })
	     //全部关闭
	     $('#mm-tabcloseall').click(function(){
	         $(".easyui-tabs1:visible").find('.tabs li').each(function(i,n){
	             $(".easyui-tabs1:visible").tabs('close', $(n).text());
	         });    
	     });
	     //关闭除当前之外的TAB
	     $('#mm-tabcloseother').click(function(){
	         var currtab_title = $('#mm').data("currtab");
	         $('.tabs-inner span').each(function(i,n){
	             if($(n).text() !== currtab_title)
	                 $(".easyui-tabs1:visible").tabs('close',$(n).text());
	         });    
	     });


        $(document).on('click', '.pf-modify-pwd', function() {
            $('#pf-page').find('iframe').eq(0).attr('src', 'backend/modify_pwd.html')
        });

        $(document).on('click', '.pf-notice-item', function() {
            $('#pf-page').find('iframe').eq(0).attr('src', 'backend/notice.html')
        });
	},

	//renderTopMenu
	_createTopMenu: function(){
		var menuStr = '',
			currentIndex = 0;
		for(var i = 0, len = SystemMenu.length; i < len; i++) {
            
			menuStr += '<li class="pf-nav-item project" data-sort="'+ i +'" data-menu="system_menu_" + i>'+
                      '<a href="javascript:;">'+
                          '<span class="iconfont">'+ (SystemMenu[i].icon || "") +'</span>'+
                          '<span class="pf-nav-title">'+ (SystemMenu[i].title || "") +'</span>'+
                      '</a>'+
                  '</li>';
            // 渲染当前
            if (SystemMenu[i].isCurrent){
            	currentIndex = i;
            	this._createSiderMenu(SystemMenu[i], i);
            }
		}

		$('.pf-nav').html(menuStr);
		$('.pf-nav-item').eq(currentIndex).addClass('current');
	},

	_createSiderMenu: function(menu, index){
		$('.pf-sider').hide();
		this._createPageContainer(index);
		if($('.pf-sider[arrindex='+ index +']').size() > 0) {
			
			$('.pf-sider[arrindex='+ index +']').show();
			return false;
		};

		/*生成菜单时，不生成pf-model-name区域 boris_deng修改*/
		/*var menuStr = '<h2 class="pf-model-name">'+
                    '<span class="iconfont">'+ (menu.icon || "")+'</span>'+
                    '<span class="pf-name">'+ (menu.title || "") +'</span>'+
                    '<span class="toggle-icon"></span>'+
                '</h2><ul class="sider-nav">';*/

		var menuStr='<ul class="sider-nav">';

        for(var i = 0, len = menu.menu.length; i < len; i++){
        	var m = menu.menu[i],
        		mstr = '';
        	var str = '';

        	if(m.isCurrent){
        		if(m.children && m.children.length > 0) {
        			str = '<li class="current">';
        		}else{
        			str = '<li class="current no-child" data-href="'+ m.href +'">';
        		}
        	}else{
        		if(m.children && m.children.length > 0) {
        			str = '<li>';
        		}else{
        			str = '<li class="no-child" data-href="'+ m.href +'">';
        		}
        	}
        	//str = m.isCurrent ? '<li class="current">' : '<li>';

           str += '<a href="javascript:;" class="pf-menu-title">'+
                '<span class="iconfont sider-nav-icon">'+ m.icon +'</span>'+
                '<span class="sider-nav-title" >'+m.title+'</span>'+
                '<i class="iconfont">&#xe642;</i>'+
            '</a>'+
            '<ul class="sider-nav-s">';
            var childStr = '';

            if(m.children && m.children.length > 0){

            	//默认打开设计师首页
                this._createTab(index,'设计师首页','/htdxjk/designerindex/index','',false);

                var indexUrls = SystemMenu[0].indexUrls;
                if(indexUrls !=undefined && indexUrls && indexUrls!="0"){

                    var urlArray = indexUrls.split(",");
                    //如果角色用户登录了，则关闭设计师首页，打开相关角色主页
                    $(".easyui-tabs1[arrindex="+ index +"]").tabs('close','设计师首页')

                    for(var _u in urlArray){

                        var _title = urlArray[_u].toLowerCase();
                        if(_title.indexOf("watcher")!=-1) _title="值班人首页";
						if(_title.indexOf("designer")!=-1) _title="设计师首页";
                        if(_title.indexOf("leader")!=-1) _title="责任人首页";
						var _url = urlArray[_u];
                        this._createTab(index,_title,_url,'',false);
                        var tabPanel = $(".easyui-tabs1[arrindex="+ index +"]");
                        tabPanel.tabs('select',_title);
                        this._getMenuInfo(null,null,null,null,null);
                    }

                }

				for(var j = 0, jlen = m.children.length; j < jlen; j++){
					var child = m.children[j];
					var dataHref = (child.children && child.children.length > 0) ? '' : ('data-href="' + child.href + '"');
					if(child.isCurrent){
						childStr += '<li class="active ' + (!dataHref ? 'no-child' : '') + '" text="'+ child.title +'" ' + dataHref + '><a href="#">'+ child.title +'</a>' + this._renderThreeLevelMenu(child.children) + '</li>';
						/*$('.easyui-tabs1[arrindex='+ index +']').tabs('add',{
							title: child.title,
							content: '<iframe class="page-iframe" src="'+ child.href +'" frameborder="no" border="no" height="100%" width="100%" scrolling="auto"></iframe>',
							closable: true
						});*/

                        this._createTab(index,child.title,child.href,child.icon,true);
                        this._getMenuInfo(child.id,child.title,child.href,child.islogineddisplay,child.action)

					}else {
						childStr += '<li id="'+child.id+'" name="'+child.title+'" islogineddisplay="'+child.islogineddisplay+'" icon="'+child.icon+'" action="'+child.action+'" url="'+child.href+'" class="' + (!dataHref ? 'no-child' : '') + '" text="'+ child.title +'" ' + dataHref + '><a href="#">'+ child.title +'</a>' + this._renderThreeLevelMenu(child.children) + '</li>';
					}
				}

            };
            mstr = str + childStr + '</ul></li>';

            menuStr += mstr;

            
        }
        $('.pf-sider-wrap').append($('<div class="pf-sider" arrindex="'+ index +'"></div>').html(menuStr + '</ul>'));

	},

	_renderThreeLevelMenu: function(list) {

		var html = '<ul class="sider-nav-t">',
			i = 0,
			len,
			one;

		if(!list || len === 0) {

			return '';

		}

		len = list.length;

		for(;i<len;i++) {

			one = list[i];

			html += '<li text="' + one.title +'" data-href="' + one.href + '"><a href="#">' + one.title + '</a></li>';

		}

		html += '</ul>';

		return html;

	},

	_createPageContainer: function(index){
		$('.easyui-tabs1').hide();
		if($('.easyui-tabs1[arrindex='+ index +']').size() > 0){
			$('.easyui-tabs1[arrindex='+ index +']').show();
			return false;
		}
		var $tabs = $('<div class="easyui-tabs1" arrindex="'+ index +'" style="width:100%;height:100%;">');
		$('#pf-page').append($tabs);
		$tabs.tabs({
	      tabHeight: 44,
	      onSelect:function(title, index){
	        var currentTab = $tabs.tabs("getSelected");
	        var $active, $parent;
	        if(currentTab.find("iframe") && currentTab.find("iframe").size() && !currentTab.find("iframe").attr("src")){
	            currentTab.find("iframe").attr("src",currentTab.find("iframe").attr("src"));
	        }
	        currentTab.find("iframe").resize();
	        $('.pf-sider:visible').find('.sider-nav-s li').removeClass('active');
	        var $active = $('.pf-sider:visible').find('.sider-nav-s li[text='+ title +']').addClass('active');
	        var $parent = $active.parent();
	        if($parent.hasClass('sider-nav-t')) {
	        	$parent.closest('li').addClass('active');
	        }
	      }
	    });

	    $tabs.find('.tabs-header').on('contextmenu', function(e){
	    	e.preventDefault();
	    	if($(e.target).closest('li').size() > 0 || $(e.target).is('li')){
	    		$('#mm').menu('show', {
		             left: e.pageX,
		             top: e.pageY,
		         });
	    		var subtitle = $(e.target).closest('li').size() ? $(e.target).closest('li') : $(e.target);
        		$('#mm').data("currtab",subtitle.text());
	    	}
	    })
	},

	_createTab:function(index,title,url,icon,closeable){

        //var tabPanel = $("#index_easyui_tab");
        var tabPanel = $(".easyui-tabs1[arrindex="+ index +"]");
        if(!tabPanel.tabs('exists',title)){

            tabPanel.tabs('add',{

                title:title,
                content:"<iframe src="+url+" frameborder='0' scrolling='auto' width='100%' height='100%' />",
                closable:closeable,
				iconCls:icon
            })

        }else{

            tabPanel.tabs('select',title);
        }

    },

	_getMenuInfo:function(id,name,url,islogineddisplay,action,icon){

		$("#menuId").val(id);
        $("#menuIcon").val(icon);
        $("#menuIsLoginedDisplay").val(islogineddisplay);
        $("#menuName").val(name);
        $("#menuAction").val(action);
        $("#menuUrl").val(url);
        //alert([id,name,url,islogineddisplay,action,icon]);
	}

};

mainPlatform.init();


var height = $(window).height() - $("#pf-hd").height()  -$(".notice").height()- $(".top_area").height() - $(".tabs").height() - $("#pf-ft").height();
var width =$(window).width()-$(".pf-sider").width()-9;
$(window).resize(function(){

    //var h=$(window).height() - $("#pf-hd").height()  - $(".top_area").height() - $(".tabs").height() - 75;
    var h = $(window).height() - $("#pf-hd").height()  -$(".notice").height()- $(".top_area").height() - $(".tabs").height() - $("#pf-ft").height();
    var w =$(window).width()-$(".pf-sider").width()-9;
    $(".tabs-panels").height(h);
    $(".tabs-panels").width(w);
    // $(".tabs-panels").find("iframe:visible").height(h);


});

$(".tabs-panels").height(height);
$(".tabs-panels").height(width);

