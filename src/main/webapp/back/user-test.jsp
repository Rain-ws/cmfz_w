<%@page pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>Document</title>
    <script src="../echarts/echarts.min.js"></script>
    <script src="../statics/boot/js/jquery-3.3.1.min.js"></script>

</head>
<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>
<script>
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '上半年用户注册趋势'
        },
        tooltip: {},
        legend: {
            data:['男','女']
        },
        xAxis: {
            data: ["一月份","二月份","三月份","四月份","五月份","六月份"]
        },
        yAxis: {},
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

    $.ajax({
        url:"${pageContext.request.contextPath}/user/selectReg",
        type:"get",
        datatype:"json",
        success:function (data) {
            console.log(data);

            myChart.setOption({
                xAxis: {
                    data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
                },
                series: [{
                    name: '男',
                    type: 'bar', //柱状图
                    data: data.nan
                }, {
                    name: '女',
                    type: 'bar', //柱状图
                    data: data.nv
                }]
            });
        }
    })
</script>

</body>
</html>
