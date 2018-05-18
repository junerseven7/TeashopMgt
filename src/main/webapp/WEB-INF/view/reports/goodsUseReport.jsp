<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MGTS后台管理系统——商品使用报表</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/he.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/eCharts/echarts.min.js"></script>
</head>
<body>
	<div data-options="region:'north',border:false" style="width: 98%; height: 37px; padding: 10px">
		<form id="searchForm">
			<table>
				<tr>
					<td class="he-td">商品 : </td>
					<!-- <td><input name="good_id" id="good_id" class="easyui-combobox" maxLength=50 style="width: 200px;" data-options="url:'/mgt/goods/dropDownList',method: 'post',valueField: 'dKey',textField: 'dValue',panelWidth: 200,panelHeight:300,formatter: formatDropDown,required: true,editable:false,onLoadSuccess: function(data){setDefaultValue(data)}"></td> -->
					<td><input name="good_id" id="good_id" class="easyui-combobox" maxLength=50 style="width: 150px;" data-options="url:'/mgt/goods/dropDownList',method: 'post',valueField: 'dKey',textField: 'dValue',panelWidth: 200,panelHeight:300,formatter: formatDropDown,required: true,editable:false"></td>
					<td><a onclick="query()" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width: 80px;">查询</a></td>
				</tr>
			</table>
		</form>
	</div>


	<div id="main" style="width: 95%; height: 520px;"></div>

	<script type="text/javascript">
		function setDefaultValue(data){
			if (data) {
                $('#good_id').combobox('setValue',data[0].dKey);
            }
		}
	
		function formatDropDown(row) {
			var fm = '<span style="font-weight:bold">' + row.dKey + '</span> : ' + '<span style="color:#888">' + row.dValue + '</span>';
			//var fm = '<span style="color:#888">' + row.dValue + '</span>';
			return fm;
		}
		
		var myChart = echarts.init(document.getElementById('main'));
		var date = [];
		var data = [];
		option = {
			//提示框 每一针选中后跳出的提示框位置
		    tooltip: {
		        trigger: 'axis',
		        position: function (pt) {
		            return [pt[0], '10%'];
		        }
		    },
		    title: {
		        left: 'center',
		        text: '商品使用数量图',
		    },
		    //工具箱 右上角
		    toolbox: {
		        feature: {
		            dataZoom: {
		                yAxisIndex: 'none'
		            },
		            restore: {},
		            saveAsImage: {}
		        }
		    },
		    xAxis: {
		        type: 'category',
		        boundaryGap: false,
		        data: date
		    },
		    yAxis: {
		        type: 'value',
		        boundaryGap: [0, '100%']
		    },
		    //数据区域缩放
		    dataZoom: [{
		        type: 'inside',
		        start: 0,
		        end: 30
		    }, {
		        start: 0,
		        end: 10,
		        handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
		        handleSize: '80%',
		        handleStyle: {
		            color: '#fff',
		            shadowBlur: 3,
		            shadowColor: 'rgba(0, 0, 0, 0.6)',
		            shadowOffsetX: 2,
		            shadowOffsetY: 2
		        }
		    }],
		    series: [
		        {
		            name:'使用数量',
		            type:'line',
		            smooth:true,
		            symbol: 'none',
		            sampling: 'average',
		            itemStyle: {
		                normal: {
		                    color: 'rgb(255, 70, 131)'
		                }
		            },
		            areaStyle: {
		                normal: {
		                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
		                        offset: 0,
		                        color: 'rgb(255, 158, 68)'
		                    }, {
		                        offset: 1,
		                        color: 'rgb(255, 70, 131)'
		                    }])
		                }
		            },
		            data: data
		        }
		    ]
		};

		myChart.setOption(option);	
		
		function query(){
			var detailValidate = $('#detailForm').form('validate');
			if (!detailValidate) {
				return;
			}
			$.ajax({
				type : "post",
				url : "/mgt/reports/inventorData",
				data : $('#searchForm').serialize(),
				success : function(data) {
					var dynamicDate=[];
				 	var dynamicData=[];
					if(''!=data){
						var returnDate =data.date;
						var returnData =data.data;
						
						for (var i = 0; i < returnDate.length; i++) {
							dynamicDate.push(returnDate[i]);
						}
						for (var i = 0; i < returnData.length; i++) {
							dynamicData.push(returnData[i]);
						}
					}else{
						$.messager.alert('提示', "当前查询到当前商品的使用记录");
					}
					myChart.setOption({
						xAxis : {
							type : 'category',
							boundaryGap : false,
							data : dynamicDate
						},
						series : [ {
							name : '数量',
							type : 'line',
							smooth : true,
							symbol : 'none',
							sampling : 'average',
							itemStyle : {
								normal : {
									color : 'rgb(255, 70, 131)'
								}
							},
							areaStyle : {
								normal : {
									color : new echarts.graphic.LinearGradient(
											0, 0, 0, 1, [ {
												offset : 0,
												color : 'rgb(255, 158, 68)'
											}, {
												offset : 1,
												color : 'rgb(255, 70, 131)'
											} ])
								}
							},
							data : dynamicData
						} ]
					});
				},
				error : function() {
					$.messager.alert('提示', "查询库存失败", 'error');
				}

			})

		}
		
		$(function() {
			$('#good_id').combobox('select',6);
			query();
		});
	</script>
</body>
</html>


