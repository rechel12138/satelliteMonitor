function setstate(data) {
    for (var i=0;i<data.length;i++) {
        var $starttime=new Date(data[i].launchTime).getTime();
        var $life=(parseInt(data[i].life))*12*30*24*60*60*1000;
        var $nowtime=new Date().getTime();
        if ($starttime==""||$starttime>$nowtime) {
            data[i].active="待发射";
        } else if ($nowtime-$starttime<$life) {
            data[i].active="未超寿";
        } else if ($nowtime-$starttime>$life) {
            var $moretime=$nowtime-$starttime-$life;
            if ($moretime<1000*60*60*24*30) {
                data[i].active="超寿"+parseInt($moretime/1000/60/60/24)+"天";
            } else if ($moretime<1000*60*60*24*30*12) {
                data[i].active="超寿"+parseInt($moretime/1000/60/60/24/30)+"月";
            } else if ($moretime>=1000*60*60*24*30*12) {
                data[i].active="超寿"+parseInt($moretime/1000/60/60/24/30/12)+"年";
            }
        }
    }
    return data;
}

function setsatelite(data) {
    for (var i=0;i<data.length;i++) {
        var $str=$("<div class='satellite'>" +
            "<input type='checkbox'  class='select_box'/>" +
            "<img onclick='openExcel()'  src='../images/big_pic.png' class='satellite_pic'/>" +
            "<h3 class='type_num'>型号代号：<span>"+data[i].code+"</span></h3>" +
            "<p class='out_name'>对外名称：<span>"+data[i].outsideName+"</span></p>" +
            "<p class='shoot_time'>发射时间：<span>"+data[i].launchTime+"</span></p>" +
            "<p class='now_detail'>状态：<span>"+data[i].active+"</span></p>" +
            "<p class='check_life' style='display: none'>考核寿命：<span>"+data[i].life+"</span></p>" +
            "<p class='set_people' style='display: none'>责任人：<span>"+data[i].adminSubsId+"</span></p>" +
            "<p class='get_people' style='display: none'>替代人：<span>"+data[i].adminId+"</span></p>" +
            "<p class='set_people_phone' style='display: none'>责任人电话：<span>"+data[i].zPhone+"</span></p>" +
            "<p class='get_people_phone' style='display: none'>替代人电话：<span>"+data[i].tPhone+"</span></p>" +
            "<h3 class='foin_name'  style='display: none'>对内名称：<span>"+data[i].coda+"</span></h3>" +
            "<h3 class='type_num_out'  style='display: none'>型号代号(五院）：<span>"+data[i].name+"</span></h3>" +
            "<div class='satellite_btn_box'>" +
            "<div class='update_btn'>详情</div>" +
            "<div class='download_btn'>回放数据</div>" +
            "<p class='dwsrc' style='display: none;'>"+data[i].remark+"</p>" +
            "</div>" +
            "</div>")
        $str.appendTo($(".all_satellites"));
    }
}

function setTypeImg() {
    $("tr").each(function(){
        $(this).children("td").each(function(){
            if ($(this).attr("field")=="platformId") {
                if ($(this).children("div").children("span:eq(0)").text()!="类别") {
                    var $img=$("<img />");
                    // $(this).children("div").empty();
                    if ($(this).children("div").text()==1) {
                        $img.attr("src","../images/sateType1.png");
                    } else if ($(this).children("div").text()==2) {
                        $img.attr("src","../images/sateType2.png");
                    } else if ($(this).children("div").text()==3) {
                        $img.attr("src","../images/sateType3.png");
                    } else if ($(this).children("div").text()==4) {
                        $img.attr("src","../images/sateType4.png");
                    }
                    $img.appendTo($(this).children("div"));
                }
            }
        })
    })
}

function openExcel(){

    var path = '"D:\\ruanjian\\网易音乐\\CloudMusic\\cloudmusic.exe"';
    var shell= new ActiveXObject("WScript.Shell");
    shell.run(path);

}