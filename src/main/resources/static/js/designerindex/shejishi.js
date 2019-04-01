$(function () {

    //饼图
    ht.data.getData("/api/bsat/countEverySatellite","POST",null,function(d){

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
                    text: '所有卫星分类数量统计',
                    subtext: '所有卫星'+ totalCount + '颗',
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
                    data: legendData,
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

            parent.showAlert("提示",d.msg,"warning");
        }

    })

    //超寿卫星数据
    ht.data.getData("/api/bsat/countIsCSSatellite","POST",null,function(d){

        if(d["status"]=="1"){

            var data = d.data;
            for(var k in data){

                var DOM = $(".designer_con").find("p");
                DOM.each(function(){

                    if($(this).hasClass(k)){

                        $(this).text(data[k]);
                    }
                })

            }

        }else{

            parent.showAlert("提示",d.msg,"warning");
        }
    })
})