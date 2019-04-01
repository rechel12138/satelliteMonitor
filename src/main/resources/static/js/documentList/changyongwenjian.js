$(function(){
    //获取权限生成操作按钮
    var menuAction = parent.getMenuInfo().menuAction;
    var btn_up_file='<button class="up_file" id="upload" style="cursor: pointer">上传</button>';
    var btn_share_file='<button class="share_file" id="btnshar" style="cursor: pointer">添加到分享区</button>';
    var btn_del_file='<button class="del_file" id="btndel" style="cursor: pointer">删除</button>';
    var btn_top='<button class="set_top" id="set_top" style="cursor: pointer">置顶</button>';
    var btn_select_list='<select name="btnlist" id="btnlist"><option value="big">大图标</option><option value="list">列表</option></select>';
    var buttons = [];
    if(menuAction.indexOf("14")!=-1){
        buttons.push(btn_up_file)
    }
    if(menuAction.indexOf("23")!=-1){
        buttons.push(btn_share_file)
    }
    if(menuAction.indexOf("6")!=-1){
        buttons.push(btn_del_file)
    }
    if(menuAction.indexOf("1")!=-1){
        buttons.push(btn_top)
    }
    if(menuAction.indexOf("3")!=-1){
        buttons.push(btn_select_list)
    }
    var btnStr=buttons.join(' ');
    $("#toolbar").append(btnStr);

    // 常用文件信息获取,大图标
    listfile();
    function listfile(page,rows){
        var page=page||1;
        var rows=rows||10;
        $.ajax({
            url:'/api/busualfile/listBusualfile',
            type:'post',
            datatype:"json",
            cache: false,
            async: false,
            data:{
                page:page,
                rows:rows
            },
            crossDomain: true,
            success: function (d) {
                var data=d.rows;
                var sli='';
                for(var i=0;i<data.length;i++){
                    sli+='<li class="file_list" atpid="'+data[i].bufAtpid+'"><div style="width:100%;text-align:left"><input style="cursor:pointer;margin-left:10px" type="checkbox"><!--<span style="margin-left: 110px" class="file_list_del">X</span>--></div><img src="../../images/word_icon.png" alt="word图标"><p>'+data[i].bufName+'</p></li>';
                }
                $(".active_file").html(sli);
                download()
                //分页
                $('#pp').pagination({
                    total:d.total,
                    pageSize:10,
                    onSelectPage:function(pageNumber, pageSize){
                        listfile(pageNumber,pageSize);
                  /*      alert(pageNumber);
                        $(this).pagination('loading');
                        alert('pageNumber:'+pageNumber+',pageSize:'+pageSize);*/
                        $(this).pagination('loaded');
                    }
                });
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
    //大图标自适应：
    fitSize();
    function fitSize() {
        var wrapWidth = $(".active_file").width()-12;
        var sWidth = $(".file_list").width()+2;
        var sNum = Math.floor(wrapWidth/sWidth);
        var smarginl = (wrapWidth-sWidth*sNum)/(sNum+1);
        $(".file_list").css("margin-left",smarginl);
    }
    $(window).resize(function () {
        fitSize();
    });
    //上传文件
    $("#upload").click(function () {
       return $(".exportBox").click();
    });

    $(".exportBox").on("change",function(){
        var option={
           /* url:'/api/busualfile/uploadBusualfile',
            type:'post',*/
        };
        $("#exportForm").ajaxSubmit(option);
        $(this).val('');
        setTimeout(function(){
            listfile();
        },1000)

    });

    //置顶
    $("#set_top").click(function(){
        // var topPic='';
        var atpidsArr=[];
        $(".file_list").each(function(){
            if($(this).find('input[type="checkbox"]').prop('checked')==true){
                $(this).find('input[type="checkbox"]').prop('checked',false);
                // var img_url=$(this).find('img').attr('src');
                // var img_name=$(this).find('p').text();
                var _atpid=$(this).attr("atpid")
                atpidsArr.push(_atpid);
                // topPic+='<div style="text-align:center;position:relative" class="top_pic"><span style="position:absolute;top:5px;right:5px;color:#FF0000;border:0" class="top_listfile_del" atpid="'+_atpid+'">X</span><img style="width:64px;height:64px" src="'+img_url+'"/><p style="font-size: 16px" class="top_pic_title">'+img_name+'</p></div>';
            }
        });
       var strAtpids=atpidsArr.toString();
        ht.data.getData("/api/busualfile/topBusualfile","POST",{atpIds:strAtpids},function(d){
            if(d.status!=0){
                parent.top_getlist();
                // parent.$(".top_area").append(topPic);
                parent.showAlert("提示",d.msg,"info")
            }else{
                parent.showAlert("提示",d.msg,"warning")
            }
        });

    });

    //按钮删除
    $("#btndel").click(function(){
        var delArr=[];
        $(".file_list").each(function(){
            if($(this).find('input[type="checkbox"]').prop('checked')==true){
                var atpid=$(this).attr("atpid");
                // $(this).remove();
                delArr.push(atpid)
            }
        });
        var strAtpids=delArr.toString();
        if(delArr.length>0){
            ht.data.getData("/api/busualfile/removeBusualfile","POST",{busualfileAtpid :strAtpids},function(d){
             if(d.status==1){
                /* $(".file_list").each(function(){
                     if($(this).find('input[type="checkbox"]').prop('checked')==true){
                         $(this).remove();
                     }
                 });*/
                 listfile();
                 parent.showAlert("提示",d.msg,"info")
             }else{
                 parent.showAlert("提示",d.msg,"warning")
             }
            })
        }

    });

/*    //list删除
    $("body").delegate(".file_list_del","click",function(){
        var atpid=$(this).parent().parent().attr("atpid");
        ht.data.getData("/api/busualfile/removeBusualfile","POST",{busualfileAtpid :atpid},function(d){
            if(d.status==1){
      /!*          _this.parent().parent().remove();*!/
                listfile();
                parent.showAlert("提示",d.msg,"success")
            }else{
                parent.showAlert("提示",d.msg,"error")
            }
        })
    });*/
    //添加到文件分享区
    $("#btnshar").click(function(){
        var sharArr=[];
        $(".file_list").each(function(){
            if($(this).find('input[type="checkbox"]').prop('checked')==true){
                var atpid=$(this).attr("atpid");
                sharArr.push(atpid);
            }
        });
        if(sharArr.length!=0){
            var atpidStr=sharArr.toString();
            ht.data.getData("/api/busualfile/addBusualfile2Share",'post',{atpIds:atpidStr},function (d) {
                if(d.status==1){
                    $(".file_list").each(function(){
                        if($(this).find('input[type="checkbox"]').prop('checked')==true){
                            $(this).find('input[type="checkbox"]').prop("checked",false)
                        }
                    });
                    parent.showAlert("提示",d.msg,"success")
                }else{
                    parent.showAlert("提示",d.msg,"error")
                }
            });
        }else{
            parent.showAlert("提示","请选择分享文件","warning")
        }

    });

    //双击下载
    download();
    function download(){
        $(".file_list").each(function(){
            var atpid=$(this).attr("atpid");
            // var name=$(this).find('p').text();
            $(this).children("img").dblclick(function(){
                parent.document.location.href="/api/busualfile/downBusualfile?atpId="+atpid
            })
        });
    }



// 列表切换
    $('#btnlist').change(function(){
        var data_value=$(this).val();
        switch (data_value){
            case 'big':
                $('#commonBig_wrap').show();
                $('#commonTale_wrap').hide();
                break;
       /*     case 'small':
                $('#commonBig_wrap').hide();
                $('.commonTale_wrap').hide();
                // $('#all_satellites').show();
                break;*/
            case 'list':
                $('#commonBig_wrap').hide();
                $('#commonTale_wrap').show();
                //列表切换数据table
                $("#commonTale").datagrid({
                    url:'/api/busualfile/listBusualfile',
                    // nowrap: true,
                    fitColumns:true,
                    striped: true,
                    // rownumbers:true,
                    pagination:true,
                    singleSelect:false,
                    pageNumber:1,
                    pageSize:8,
                    pageList:[8,16,24,32,40],
                    height:"100%",
                    width:'100%',
                    columns:[[
                        //todo
                        //字段
                        {field:'bufName',title:'文件名称',width:'12%',align:'center'},
                        {field:'bufAtpremark',title:'文件或软件概述',width:'30%',align:'center'},
                        {field:'bsatFull_name',title:'版本',width:'10%',align:'center'},
                        {field:'bufAtpcreatedatetime',title:'发布时间',width:'20%',align:'center'},
                        {field:'op',title:'操作',width:'29%',align:'center',formatter:function(v,r){
                            console.log(r)
                            var btnopen = "<a style='cursor: pointer' class='btnlist_open' atpid='"+r.bufAtpid+"' detail_title='"+r.bsatCode+"'>预览</a>";
                            var share = "<a style='cursor: pointer' class='btnlist_share' atpid='"+r.bufAtpid+"'>添加到分享区</a>";
                            var top = "<a style='cursor: pointer' class='btnlist_top' atpid='"+r.bufAtpid+"' img_name='"+r.bufName+"'>置顶</a>";
                            var buttons = [];
                                //预览按钮权限
                              /*  if(menuAction.indexOf("15")!=-1){
                                    buttons.push(btnopen)
                                }*/
                                buttons.push(btnopen)
                                if(menuAction.indexOf("23")!=-1){
                                    buttons.push(share)
                                }
                                if(menuAction.indexOf("1")!=-1){
                                    buttons.push(top)
                                }
                            return buttons.join("<span style='margin:0 5px'>|</span>");
                            }}
                            ]],
                    onLoadSuccess:function(data){
                        console.log(data)
                        // 置顶
                        $("body").undelegate(".btnlist_top","click").delegate(".btnlist_top","click",function(){
                            var atpid=$(this).attr("atpid");
                            //todo
                            // var img_url="../../images/word_icon.png";
                            // var img_name=$(this).attr("img_name");
                            // var topPic='<div style="text-align:center" class="top_pic"><img style="width:64px;height:64px" src="'+img_url+'"/><p style="font-size: 16px" class="top_pic_title">'+img_name+'</p></div>';
                            ht.data.getData("/api/busualfile/topBusualfile","POST",{atpIds:atpid},function(d){
                                if(d.status!=0){
                                    parent.top_getlist();
                                    // parent.$(".top_area").append(topPic);
                                    parent.showAlert("提示",d.msg,"success")
                                }else{
                                    parent.showAlert("提示",d.msg,"error")
                                }
                            });
                        });
                        //添加到分享区
                        $("body").undelegate(".btnlist_share","click").delegate(".btnlist_share","click",function(){
                            var atpid=$(this).attr("atpid");
                            ht.data.getData("/api/busualfile/addBusualfile2Share",'post',{atpIds:atpid},function (d) {
                                if(d.status==1){
                                    parent.showAlert("提示",d.msg,"success")
                                }else{
                                    parent.showAlert("提示",d.msg,"error")
                                }
                            });
                        });
                        //预览
                        $("body").undelegate(".btnlist_open","click").delegate(".btnlist_open","click",function(){
                            var atpid=$(this).attr("atpid");
                            parent.createDialog({
                                element: $("#file_look"),
                                elementId: "file_look",
                                title: '文件预览',
                                icon: '',
                                ok_button_handler: function () {

                                },
                                display_ok_button:false,
                                display_cancel_button:false
                            });
                        })

                        }});
                      /*      $(".btnopen").off("click").click(function(){
                                var title_data=$(this).attr('detail_title');
                                var data_id=$(this).attr('detail_id');
                                detailsFn(title_data,data_id)
                            });
                            $(".btnload").off("click").click(function(){
                                var $satId=$(this).attr('downid');
                                opendwnFn($satId);
                                bindClick($satId);
                            })*/

                break;
        }
    });
});