$(function () {
    //饼图


    ht.data.getData("/api/balarmfx/countZrrIndexType","POST",null,function(d){
        if(d["status"]=="1"){
            var legendData = [];
            var seriesData = [];
            var data = d.data;
            // console.log(data);
            var totalCount = 0;
            for(var k in data){

                if(data[k]["type"]!="全部卫星"){

                    legendData.push(data[k]["type"]);
                    seriesData.push({value:data[k]["count"],name:data[k]["type"]});

                }else{
                    totalCount=data[k]["count"];
                }

            }
            var myChart = echarts.init(document.getElementById('main'));
            var option = {
                title : {
                    text: '近期卫星报警分类',
                    // subtext: '所有卫星'+ totalCount + '颗',
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    type: 'scroll',
                    orient: 'vertical',
                    right: 10,
                    top:150,
                    bottom: 20,
                    data: legendData
                },
                series : [
                    {
                        name: '数据',
                        type: 'pie',
                        radius : '55%',
                        center: ['40%', '50%'],
                        data: seriesData,
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
            myChart.setOption(option);

        }else{

            // parent.showAlert("提示",d.msg,"warning");
        }

    })



    //计算时间 d:天数
    function calTime(d){

        if(d==0) return false;
        var now = new Date().getTime();
        var final = new Date(now-d*24*60*60*1000);
        var year =  final.getFullYear();
        var month =  final.getMonth()+1;
        var day =  final.getDate();
        var time = year+'-'+month+'-'+day;
        return {s:time+" 00:00:00",e:time+" 23:59:59"};
    }

    //获取请求参数
    function getPara(){

        var keyword = $("#keyword").val();
        var alarmlevel = [];
        $(".alarm_level input[type=checkbox]").each(function(){
            if($(this).prop("checked")){
                alarmlevel.push($(this).val());
            }
        });
        var startTime = $(".start_time").val();
        var endTime = $(".end_time").val();
        var showQr = $("#seeOnlyUnHander").prop("checked")?1:0;

        return {

            keyword:keyword,
            alarmlevel:alarmlevel.join(","),
            startTime:$(".start_time").datetimebox('getValue'),
            endTime:$(".end_time").datetimebox('getValue'),
            showQr:showQr
        }
    }
});