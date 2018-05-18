<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MGTS后台管理系统——库存管理</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/he.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/commonUtil.js"></script>
</head>
<body>
<!-- 头部查询信息 -->
	<div data-options="region:'north',border:false" style="width: 98%; height: 37px; padding: 10px">
		<form id="searchForm">
			<table>
				<tr>
					<td class="he-td">库存商品ID:</td>
					<td><input name="goods_id" class="easyui-numberbox" style="width: 200px;" data-options="precision:0,min:0"/></td>
					<td class="he-td">库存商品名称:</td>
					<td><input name="goods_name" class="easyui-textbox" style="width: 200px;" /></td>
					<td><a onclick="query()" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width: 80px;">查询</a>
				</tr>
			</table>
		</form>
	</div>
	<!-- 按钮-->
	<div id="buttonToolBar">
		<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-add',plain:true" onclick="addInfo()">添加</a> 
		<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-edit',plain:true"	onclick="editInfo()">编辑</a>
		<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-remove',plain:true" onclick="deleteInfo()">删除</a>
	</div>
	<!-- 列表信息 -->
<table class="easyui-datagrid" title="库存明细列表" id="detailList" toolbar="#buttonToolBar"  data-options="pageList : [20,50,100]">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th field="id" hidden></th>
			<th field="goods_id" style="width:100px;" align="center">库存商品ID</th>
			<th field="goods_name" style="width:150px;" align="center">库存商品名称</th>
			<th field="now_quantity" style="width:100px;" align="center" >现有库存个数</th>
			<th field="input_emploee" style="width:200px;" align="center" >录入人</th>
			<th field="crt_dttm" style="width:200px;" align="center" >创建时间</th>
			<th field="lastupt_dttm" style="width:200px;" align="center" >最后修改时间</th>
			<th field="enable_flg" style="width:100px;" align="center" formatter="formatEnableFlg">是否有效</th>
		</tr>
	</thead>
</table>

<div id="detailDiv" name="detailDiv" class="easyui-dialog" style="width:320px;height:210px;padding:10px 10px" closed="true" modal=true hidden>
	<form id="detailForm" method="post" enctype="multipart/form-data">
			<table>
				<tr>
					<td><span>库存商品ID</span></td>
		      		<td><input name="goods_id" id="goods_id" type="text" class="easyui-textbox" style="width:200px;"  data-options="required: false" /></td>
		        </tr>
		        <tr>
					<td><span>库存商品名称</span></td>
		      		<td><input name="goods_name" id="goods_name" type="text" class="easyui-textbox" style="width:200px;"  data-options="required: false" /></td>
		        </tr>
		        <tr>
		       		<td><span>商品库存个数</span></td>
		      		<td><input name="now_quantity" type="text" class="easyui-textbox" style="width:200px;"  data-options="precision:0,groupSeparator:' ',decimalSeparator:'.',required: true" /></td>
		      	</tr>
		      	<tr>
		       		<td><span>录入人</span></td>
		      		<td><input name="input_emploee" type="text" class="easyui-textbox" style="width:200px;"  data-options="required: true" /></td>
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
		</form>
</div>


<div id="detailDivAdd" name="detailDivAdd" class="easyui-dialog" style="width:320px;height:180px;padding:10px 10px" closed="true" modal=true hidden>
		<form id="detailFormAdd" method="post" enctype="multipart/form-data">
			<table>
				<tr>
					<td><span>库存商品</span></td>
					<td><input id="id_add" class="easyui-combobox" name="id_add" maxLength=50 style="width: 200px;"
						data-options="url:'/mgt/goods/dropDownList',method: 'post',valueField: 'dKey',textField: 'dValue',panelWidth: 200,panelHeight:300,formatter: formatDropDown,required: true,editable:false"></td>
				</tr>
				<tr>
					<td><span>商品库存个数</span></td>
					<td><input name="now_quantity_add" type="text" class="easyui-numberbox" style="width: 200px;" data-options="precision:0,min:0,required:true" /></td>
				</tr>
				<tr>
					<td><span>录入人</span></td>
					<td><input name="input_emploee_add" type="text" class="easyui-textbox" style="width: 200px;" data-options="required: true" /></td>
				</tr>
				<tr align="right">
					<td colspan="2">
						<a onclick="javascript: $('#detailDivAdd').dialog('close') " class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">关闭</a> 
						<a onclick="add()" id="btnAdd" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a></td>
				</tr>
			</table>
		</form>
	</div>

<script type="text/javascript" >
<!-- 初始化-->
	$(document).ready(function() {
		$("#detailList").datagrid({
			url : "/mgt/stock/list",
			checkOnSelect : true,
			singleSelect : false,
			pagination : true,
			rownumbers : true,
			pageSize : 20,
			fitColumns : true
		});
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
		$('#detailForm').form('reset');
		document.getElementById("formType").value = "insert";
		$("#detailDivAdd").dialog('open').dialog('setTitle', '添加');
	}
	//编辑
	function editInfo() {
		var rows = $('#detailList').datagrid('getSelections');
		if (rows.length != 1) {
			$.messager.alert('提示', "请选择一条数据");
			return;
		}
		$.ajax({
			type : "post",
			url : "/mgt/stock/info",
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
			error : function() {
				$.messager.alert('提示', "编辑失败", 'error');
			}

		})
		$('#detailForm').form('reset');
		//$('#detailForm').form('load',rows[0]);
		$("#detailDiv").dialog('open').dialog('setTitle', '编辑');
		document.getElementById("formType").value = "update";
		$("#goods_id").textbox('disable');
		$("#goods_name").textbox('disable');
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
					url : "/mgt/stock/delete",
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
	//添加和编辑保存按钮
	function save() {
		var detailValidate = $('#detailForm').form('validate');
		if (!detailValidate) {
			return;
		}
		var submitUrl = "";
		var formType = document.getElementById("formType").value;
		if (formType == "insert") {
			submitUrl = "/mgt/stock/save";
		} else if (formType == "update") {
			submitUrl = "/mgt/stock/update";
		} else {
			alert(formType);
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

	function add() {
		var detailValidate = $('#detailFormAdd').form('validate');
		if (!detailValidate) {
			return;
		}

		$.ajax({
			type : "post",
			url : "/mgt/stock/save",
			data : $('#detailFormAdd').serialize(),
			success : function(data) {
				if (data == "true") {
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
</script>
</body>
</html>
