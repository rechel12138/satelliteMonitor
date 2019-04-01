$(function () {

    //把给定天数转为年月日时分秒格式：
    function formatDate(d) {
        var datetime = new Date(new Date().getTime()-d*24*60*60*1000);
        var year = datetime.getFullYear();
        var month = ("0" + (datetime.getMonth() + 1)).slice(-2);
        var date = ("0" + datetime.getDate()).slice(-2);
        var hour = ("0" + datetime.getHours()).slice(-2);
        var minute = ("0" + datetime.getMinutes()).slice(-2);
        var second = ("0" + datetime.getSeconds()).slice(-2);
        return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
    }
    //计算请求参数：
    function get_params(d){
        var startTime = formatDate(d);
        var endTime =formatDate(0);
        return {
            startTime:startTime,
            endTime:endTime
        }
    }

    //饼图 ：1天
    ht.data.getData("/api/balarmfx/countZrrIndexType","POST",get_params(1),function(d){
        var legendData =[];
        var seriesData =[];
        for(var k in d){
            legendData.push(d[k]["typename"]);
            seriesData.push({name:d[k]["typename"],value:d[k]["mycount"]});
        }
        var option = {
            title:{
                text:'1天',
                bottom:5,
                left:'31%'
            },
            tooltip : {//提示框，本系列默认不设置时位置会跟随鼠标的位置
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"//提示框浮层内容格式器，支持字符串模板和回调函数两种形式。
            },
            legend: {//图例组件
                type: 'scroll',//可滚动翻页的图例,当图例数量较多时可以使用。
                orient: 'vertical',//图例布局的朝向
                right: 0,//图例组件离容器右侧的距离
                bottom: 5,
                data: legendData
            },
            series : [//系列列表
                {
                    name: '报警次数',//系列名称，用于tooltip的显示，legend 的图例筛选...
                    type: 'pie',
                    radius : '55%',//饼图的半径
                    center: ['35%', '50%'],//饼图的中心坐标，第一个是横坐标，第二个是纵坐标
                    data: seriesData,
                    itemStyle: {//图形样式
                        emphasis: {//高亮的扇区和标签样式
                            shadowBlur: 10,//图形阴影的模糊大小
                            shadowOffsetX: 0,//阴影水平方向上的偏移距离
                            shadowColor: 'rgba(0, 0, 0, 0.5)'//阴影颜色
                        }
                    },
                    label:{//饼图图形上的文本标签，比如值，名称等，
                        show:true,
                        formatter:'{d}%'
                    }
                }
            ]
        };
        var myChart = echarts.init(document.getElementById('oneday'),'westeros');
        myChart.setOption(option);
    });
    //饼图 ：3天
    ht.data.getData("/api/balarmfx/countZrrIndexType","POST",get_params(3),function(d){
        var legendData =[];
        var seriesData =[];
        for(var k in d){
            legendData.push(d[k]["typename"]);
            seriesData.push({name:d[k]["typename"],value:d[k]["mycount"]});
        }
        var option = {
            title:{
                text:'3天',
                bottom:5,
                left:'31%'
            },
            tooltip : {//提示框，本系列默认不设置时位置会跟随鼠标的位置
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"//提示框浮层内容格式器，支持字符串模板和回调函数两种形式。
            },
            legend: {//图例组件
                type: 'scroll',//可滚动翻页的图例,当图例数量较多时可以使用。
                orient: 'vertical',//图例布局的朝向
                right: 0,//图例组件离容器右侧的距离
                bottom: 5,
                data: legendData
            },
            series : [//系列列表
                {
                    name: '报警次数',//系列名称，用于tooltip的显示，legend 的图例筛选...
                    type: 'pie',
                    radius : '55%',//饼图的半径
                    center: ['35%', '50%'],//饼图的中心坐标，第一个是横坐标，第二个是纵坐标
                    data: seriesData,
                    itemStyle: {//图形样式
                        emphasis: {//高亮的扇区和标签样式
                            shadowBlur: 10,//图形阴影的模糊大小
                            shadowOffsetX: 0,//阴影水平方向上的偏移距离
                            shadowColor: 'rgba(0, 0, 0, 0.5)'//阴影颜色
                        }
                    },
                    label:{//饼图图形上的文本标签，比如值，名称等，
                        show:true,
                        formatter:'{d}%'
                    }
                }
            ]
        };
        var myChart = echarts.init(document.getElementById('threeDays'),'westeros');
        myChart.setOption(option);
    });
    //饼图 ：一周
    ht.data.getData("/api/balarmfx/countZrrIndexType","POST",get_params(9),function(d){
        var legendData =[];
        var seriesData =[];
        for(var k in d){
            legendData.push(d[k]["typename"]);
            seriesData.push({name:d[k]["typename"],value:d[k]["mycount"]});
        }
        var option = {
            title:{
                text:'一周',
                bottom:5,
                left:'31%'
            },
            tooltip : {//提示框，本系列默认不设置时位置会跟随鼠标的位置
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"//提示框浮层内容格式器，支持字符串模板和回调函数两种形式。
            },
            legend: {//图例组件
                type: 'scroll',//可滚动翻页的图例,当图例数量较多时可以使用。
                orient: 'vertical',//图例布局的朝向
                right: 0,//图例组件离容器右侧的距离
                bottom: 5,
                data: legendData
            },
            series : [//系列列表
                {
                    name: '报警次数',//系列名称，用于tooltip的显示，legend 的图例筛选...
                    type: 'pie',
                    radius : '55%',//饼图的半径
                    center: ['35%', '50%'],//饼图的中心坐标，第一个是横坐标，第二个是纵坐标
                    data: seriesData,
                    itemStyle: {//图形样式
                        emphasis: {//高亮的扇区和标签样式
                            shadowBlur: 10,//图形阴影的模糊大小
                            shadowOffsetX: 0,//阴影水平方向上的偏移距离
                            shadowColor: 'rgba(0, 0, 0, 0.5)'//阴影颜色
                        }
                    },
                    label:{//饼图图形上的文本标签，比如值，名称等，
                        show:true,
                        formatter:'{d}%'
                    }
                }
            ]
        };
        var myChart = echarts.init(document.getElementById('aWeek'),'westeros');
        myChart.setOption(option);
    });


    //柱状图：近期卫星报警情况
    ht.data.getData("/api/balarmfx/getBjFlfxCount", "POST", get_params(3),render_bar);
    //选择日期时：
    $("#alarm_date_list").change(function () {
        var a_d_list = $("#alarm_date_list").val();
        ht.data.getData("/api/balarmfx/getBjFlfxCount", "POST", get_params(a_d_list), render_bar)
    });
    function render_bar(d) {
        var legendData =[];
        var seriesData =[];
        for(var k in d){
            legendData.push(d[k]["bsat_code"]);
            seriesData.push(d[k]["mycount"]);
        }
        // var legendData =[20, 12, 11, 34, 20, 30, 10, 23, 42, 21];
        // var seriesData =['CE-0003','BD-0002','BD-JFAO','CE-ASOID','QX-374','CE-0007','BD-0007','BD-JFA3','CE-ASO2D','QX-3F4'];
        var option = {
            // title: {
            //     text: ''
            // },
            legend: {
                data: ['报警次数']
            },
            grid:{
                x:50,
                y:20
            },
            xAxis: {
                data: seriesData,
                axisLabel: {
                    interval: 0,// 0 强制显示所有，1为隔一个标签显示一个标签，2为隔两个
                    rotate: 45//标签旋转角度，在标签显示不下的时可通过旋转防止重叠
                }
            },
            yAxis: {
                min: 0,
                max: 60,
                textStyle:{fontSize:16}
            },
            series: [{
                name: '',
                type: 'bar',
                data: legendData,
                itemStyle: {
                    normal: {
                        color: 'rgb(58,160,255)',
                        label: {
                            show: true,		//开启显示
                            position: 'top',	//在上方显示
                            textStyle: {	    //数值样式
                                color: 'black',
                                fontSize: 16
                            }
                        }
                    }
                },
                barWidth: '40px',
                // barCategoryGap:'同一系列的柱间距离，默认为类目间距的20%，可设固定值',
                barCategoryGap: '200%'
            }],

            axisLabel: {
                interval: 0// 0 强制显示所有，1为隔一个标签显示一个标签，2为隔两个
            }
        };
        var myChart = echarts.init(document.getElementById("bar_graph"));
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option)
    }
});