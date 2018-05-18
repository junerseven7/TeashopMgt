<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MGTS后台管理系统——商品盘点管理</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/he.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/commonUtil.js"></script>
</head><body>
<!-- 头部查询信息 -->
	<div data-options="region:'north',border:false" style="width: 98%; height: 37px; padding: 10px">
		<form id="searchForm">
			<table>
				<tr>
					<td class="he-td">商品名称:</td>
					<td><input name="goods_name" class="easyui-textbox" style="width: 150px;" /></td>
					<td class="he-td">盘点日期:</td>
					<td><input name="inventory_date_start" id="inventory_date_start" class="easyui-datetimebox" style="width: 150px;" data-options="editable:false"/></td>
					<td class="he-td">&nbsp;-&nbsp;</td>
					<td><input name="inventory_date_end" id="inventory_date_end" class="easyui-datetimebox" style="width: 150px;" data-options="editable:false"/></td>
					<td><a onclick="query()" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width: 80px;">查询</a>
				</tr>
			</table>
		</form>
	</div>
	<!-- 按钮-->
	<div id="buttonToolBar">
		<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-add',plain:true" onclick="addInfo()">添加</a> 
		<!-- <a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-edit',plain:true"	onclick="editInfo()">编辑</a> -->
		<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-remove',plain:true" onclick="deleteInfo()">删除</a>
		<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-search',plain:true" onclick="summaryInfo()">盘点汇总</a>
	</div>
	<!-- 列表信息 -->
<table class="easyui-datagrid" title="盘点明细列表" id="detailList" toolbar="#buttonToolBar"  data-options="pageList : [20,50,100]">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th field="id" hidden></th>
			<th field="goods_name" style="width:200px;" align="center">商品名称</th>
			<th field="existing_inventory" style="width:100px;" align="center">现有库存</th>
			<th field="check_inventory" style="width:100px;" align="center" >盘点库存</th>
			<th field="inventory_difference" style="width:100px;" align="center" >库存差异</th>
			<th field="inventory_date" style="width:200px;" align="center" >盘点日期</th>
			<th field="adjust_inventory" style="width:100px;" align="center">手动调整库存</th>
			<th field="crt_dttm" style="width:200px;" align="center" >创建时间</th>
			<th field="lastupt_dttm" style="width:200px;" align="center" >最后修改时间</th>
			<th field="enable_flg" style="width:100px;" align="center" formatter="formatEnableFlg">是否有效</th>
		</tr>
	</thead>
</table>

<div id="detailDiv" name="detailDiv" class="easyui-dialog" style="width:350px;height:260px;padding:10px 10px" closed="true" modal=true hidden>
	<form id="detailForm" method="post" enctype="multipart/form-data">
			<table >
		        <tr>
					<td><span>商品名称</span></td>
		      		<td><input name="goods_name" id="goods_name" type="text" class="easyui-textbox" style="width:200px;"  data-options="required: false" /></td>
		        </tr>
		        <tr>
		       		<td><span>现有库存</span></td>
		      		<td><input name="existing_inventory" id="existing_inventory" type="text" class="easyui-numberbox" style="width:200px;"  data-options="precision:0,required: true,min:0,max:9999" /></td>
		      	</tr>
		        <tr>
		       		<td><span>盘点库存</span></td>
		      		<td><input name="check_inventory" type="text" class="easyui-numberbox" style="width:200px;"  data-options="precision:0,required: true,min:0,max:9999" /></td>
		      	</tr>
		      	<tr>
		       		<td><span>库存差异</span></td>
		      		<td><input name="inventory_difference" id="inventory_difference" type="text" class="easyui-numberbox" style="width:200px;"  data-options="precision:0,required: true,min:-9999,max:9999" /></td>
		      	</tr>
		      	<tr>
		       		<td><span>手动调整库存</span></td>
		      		<td><input name="adjust_inventory" type="text" class="easyui-numberbox" style="width:200px;" data-options="precision:0,min:0,max:9999" /></td>
		      	</tr>
	      	 	<tr>
		       		<td><span>盘点日期</span></td>
		      		<td><input name="inventory_date" type="text" class="easyui-datebox" style="width:200px;"  data-options="required: true,editable:false" /></td>
		      	</tr>
		      	<tr align="right">
			        <td  colspan="2">
			        	<a onclick="javascript: $('#detailDiv').dialog('close') "  class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a>
			        	<a onclick="save()" id="btnsave" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
			        </td>
		        </tr>
	        </table>
	        <input type="hidden" id="formType" name="formType" />
	        <input name="id" id="id"  type="hidden"  />
	        <input name="goods_id" id="goods_id"  type="hidden"  />
		</form>
</div>

<div id="detailDivAdd" name="detailDivAdd" class="easyui-dialog" style="width:310px;height:180px;padding:10px 10px" closed="true" modal=true hidden>
		<form id="detailFormAdd" method="post" enctype="multipart/form-data">
			<table style="text-align:center">
				<tr align="center">
					<td><span>商品</span></td>
					<td><input name="goods_id_add" id="goods_id" class="easyui-combobox" maxLength=50 style="width: 200px;"	data-options="url:'/mgt/goods/dropDownList',method: 'post',valueField: 'dKey',textField: 'dValue',panelWidth: 200,panelHeight:300,formatter: formatDropDown,required: true,editable:false"></td>
				</tr>
				<tr>
					<td><span>盘点库存数</span></td>
					<td><input name="check_inventory_add" type="text" class="easyui-numberbox" style="width: 200px;" data-options="precision:0,min:0,max:9999,required:true" /></td>
				</tr>
				<tr>
					<td><span>盘点日期</span></td>
					<td><input name="inventory_date_add" id="inventory_date" class="easyui-datebox" style="width: 200px;" data-options="editable:false,required:true"/></td>
				</tr>
				<tr align="right">
					<td colspan="2">
						<a onclick="javascript: $('#detailDivAdd').dialog('close') " class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a> 
						<a onclick="add()" id="btnAdd" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a></td>
				</tr>
			</table>
		</form>
	</div>


<div id="detailDivSummary" name="detailDivSummary" class="easyui-dialog" style="width:520px;height:400px;padding:10px 10px" closed="true" modal=true hidden>
	<form id="detailFormSummary">
			<table>
				<tr>
					<td class="he-td">盘点汇总日期:</td>
					<td><input name="inventory_summary_date" id="inventory_summary_date" class="easyui-datebox" style="width: 150px;" data-options="editable:false,required: true"/></td>
					<td><a onclick="querySummary()" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width: 80px;">查询</a></td>
				</tr>
	        </table>
	 </form>
	 <br>
	        <table id="summaryTable"  border="1" style="border-collapse: collapse;">
		        <thead>
					<tr>
						<th style="width:200px;" align="center">商品名称</th>
						<th style="width:100px;" align="center">现有库存</th>
						<th style="width:100px;" align="center" >盘点库存</th>
						<th style="width:100px;" align="center" >库存差异</th>
						<th style="width:100px;" align="center" >手动调整库存</th>
					</tr>
				</thead>
	        </table>
	        <table align="right">
	        <tr align="right">
					<td colspan="4">
						<a onclick="javascript: $('#detailDivSummary').dialog('close') " class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a> 
				</tr>
			</table>
</div>



<script type="text/javascript" >
<!-- 初始化-->
	$(document).ready(function() {
		$("#detailList").datagrid({
			url : "/mgt/inventory/list",
			checkOnSelect : true,
			singleSelect : false,
			pagination : true,
			rownumbers : true,
			pageSize : 20,
			fitColumns : true
		});
		
		var curr_time = new Date();
	    var strDate = curr_time.getFullYear()+"-";
		   strDate += curr_time.getMonth()+1+"-";
		   strDate += curr_time.getDate();
		var startTime = strDate+" 00:00:00";
		var endTime=strDate+" 23:59:59"
		$('#inventory_date_start').datetimebox('setValue',startTime);
		$('#inventory_date_end').datetimebox('setValue',endTime);
	});

	function formatDropDown(row) {
		// var s = '<span style="font-weight:bold">' + row.dKey + '</span><br/>' + '<span style="color:#888">' + row.dValue + '</span>';
		var s = '<span style="color:#888">' + row.dValue + '</span>';
		return s;
	}

	//查询
	function query() {
		var params = $("#searchForm").serializeArray();
		params = serializeObject(params);
		$("#detailList").datagrid("options").queryParams = params;
		$("#detailList").datagrid("load");
	}
	//添加
	function addInfo() {
		$('#detailFormAdd').form('reset');
		document.getElementById("formType").value = "insert";
		$("#detailDivAdd").dialog('open').dialog('setTitle', '添加盘点记录');
	}
	//编辑
	function editInfo() {
		var rows = $('#detailList').datagrid('getSelections');
		if (rows.length != 1) {
			$.messager.alert('提示', "请选择一条数据");
			return;
		}
		if(rows[0].is_effect==2){
			$.messager.alert('提示', "订单已经生效无法编辑");
			return;
		}
		$.ajax({
			type : "post",
			url : "/mgt/inventory/info",
			data : JSON.stringify(rows[0]),
			dataType : 'json',
			contentType : 'application/json',
			success : function(data) {
				$('#detailForm').form('reset');
				//$('#detailForm').form('load',rows[0]); 如果不想查询,直接从页面获取数据
				$('#detailForm').form('load', data);
				$("#detailDiv").dialog('open').dialog('setTitle', '编辑');
				document.getElementById("formType").value = "update";
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
					$.messager.alert('提示', "后台服务出错", 'error');
			}

		})
		$('#detailForm').form('reset');
		//$('#detailForm').form('load',rows[0]);
		$("#detailDiv").dialog('open').dialog('setTitle', '编辑');
		document.getElementById("formType").value = "update";
		$("#goods_name").textbox('disable');
		$("#existing_inventory").numberbox('disable');
		$("#inventory_difference").numberbox('disable');
		
	}
	//删除
	function deleteInfo() {
		var rows = $('#detailList').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert('提示', "请选择要删除的数据");
			return;
		}

		$.messager.confirm('删除确认', '你确认要删除选中数据吗?', function(r) {
			if (r) {
				$.ajax({
					type : "post",
					url : "/mgt/inventory/delete",
					data : JSON.stringify(rows),
					dataType : 'json',
					contentType : 'application/json',
					success : function(data) {
						data = JSON.stringify(data);
						if (data == "true") {
							$.messager.alert('提示', "删除成功");
							query();
						} else {
							$.messager.alert('提示', "删除失败", 'error');
						}
					},
					error : function() {
						$.messager.alert('提示', "删除失败", 'error');
					}

				})
			}
		});
	}
	//保存编辑按钮
	function save() {
		var detailValidate = $('#detailForm').form('validate');
		if (!detailValidate) {
			return;
		}
		var submitUrl = "";
		var formType = document.getElementById("formType").value;
		if (formType == "update") {
			submitUrl = "/mgt/inventory/update";
		} else {
			$.messager.alert('提示', "请刷新页面重试", 'error');
			return;
		}

		$.ajax({
			type : "post",
			url : submitUrl,
			data : $('#detailForm').serialize(),
			success : function(data) {
				if (data == "true") {
					$('#detailDiv').dialog('close');
					$.messager.alert('提示', "保存成功");
					query();
				} else {
					$.messager.alert('提示', "保存失败", 'error');
				}
			},
			error : function() {
				$.messager.alert('提示', "保存失败", 'error');
			}

		})

	}
	//新增按钮
	function add() {
		var detailValidate = $('#detailFormAdd').form('validate');
		if (!detailValidate) {
			return;
		}

		$.ajax({
			type : "post",
			url : "/mgt/inventory/save",
			data : $('#detailFormAdd').serialize(),
			success : function(data) {
				if (data == true) {
					$('#detailDivAdd').dialog('close');
					$.messager.alert('提示', "保存成功");
					query();
				} else {
					$.messager.alert('提示', "保存失败", 'error');
				}
			},
			error : function() {
				$.messager.alert('提示', "保存失败", 'error');
			}

		})
	}
	//盘点汇总弹出层
	function summaryInfo(){
		$('#detailFormSummary').form('reset');
		$("#detailDivSummary").dialog('open').dialog('setTitle', '详情');
		
		var tableHtml='<thead><tr><th style="width:200px;" align="center">商品名称</th><th style="width:100px;" align="center">现有库存</th><th style="width:100px;" align="center" >盘点库存</th><th style="width:100px;" align="center" >库存差异</th><th style="width:100px;" align="center" >手动调整库存</th></thead>';
		$("#summaryTable").empty(); 
		$("#summaryTable").append(tableHtml);
		
		var curr_time = new Date();
        var strDate = curr_time.getFullYear()+"-";
	    strDate += curr_time.getMonth()+1+"-";
		strDate += curr_time.getDate();
		$('#inventory_summary_date').datebox('setValue',strDate);
		querySummary();
	}
	
	//盘点汇总信息
	function querySummary(){
		var detailValidate = $('#detailFormSummary').form('validate');
		if (!detailValidate) {
			return;
		}
		$.ajax({
			type : "post",
			url : "/mgt/inventory/summary",
			data : $('#detailFormSummary').serialize(),
			success : function(data) {
				var tableHtml='<thead><tr><th style="width:200px;" align="center">商品名称</th><th style="width:100px;" align="center">现有库存</th><th style="width:100px;" align="center" >盘点库存</th><th style="width:100px;" align="center" >库存差异</th><th style="width:100px;" align="center" >手动调整库存</th></thead>';
				for (var i = 0; i < data.length; i++) {
					var temp = data[i];
					tableHtml+='<tr align="center"><td>'+temp.goods_name+'</td><td>'+temp.existing_inventory+'</td><td>'+temp.check_inventory+'</td><td>'+temp.inventory_difference+'</td>';
					if(eval(temp.adjust_inventory) == null){
						tableHtml+='<td>-</td></tr>';
					}else{
						tableHtml+='<td>'+temp.adjust_inventory+'</td></tr>';
					}
				}
				$("#summaryTable").empty(); 
				$("#summaryTable").append(tableHtml);
			},
			error : function() {
				$.messager.alert('提示', "查询详情失败", 'error');
			}
		})
	}
</script>
</body>
</html>
