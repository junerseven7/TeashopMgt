<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MGTS后台管理系统——商品使用管理</title>
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
					<td class="he-td">商品使用量:</td>
					<td><input name="quantity" class="easyui-numberbox" style="width: 150px;" data-options="precision:0,min:0,max:999"/></td>
					<td class="he-td">商品使用日期:</td>
					<td><input name="use_dttm_start" id="use_dttm_start" class="easyui-datetimebox" style="width: 150px;" data-options="editable:false"/></td>
					<td class="he-td">&nbsp;-&nbsp;</td>
					<td><input name="use_dttm_end" id="use_dttm_end" class="easyui-datetimebox" style="width: 150px;" data-options="editable:false"/></td>
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
		<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-ok',plain:true" onclick="isEffectInfo()">使用生效</a>
	</div>
	<!-- 列表信息 -->
<table class="easyui-datagrid" title="使用列表" id="detailList" toolbar="#buttonToolBar"  data-options="pageList : [20,50,100]">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th field="id" hidden></th>
			<th field="goods_name" style="width:150px;" align="center">商品名称</th>
			<th field="quantity" style="width:100px;" align="center">使用量</th>
			<th field="use_emploee" style="width:100px;" align="center" >使用人</th>
			<th field="use_dttm" style="width:200px;" align="center" >使用日期</th>
			<th field="is_effect" style="width:100px;" align="center" formatter="formatIsEffect">是否生效</th>
			<th field="crt_dttm" style="width:200px;" align="center" >创建时间</th>
			<th field="lastupt_dttm" style="width:200px;" align="center" >最后修改时间</th>
			<th field="enable_flg" style="width:100px;" align="center" formatter="formatEnableFlg">是否有效</th>
		</tr>
	</thead>
</table>

<div id="detailDiv" name="detailDiv" class="easyui-dialog" style="width:320px;height:220px;padding:20px 10px" closed="true" modal=true hidden>
	<form id="detailForm" method="post" enctype="multipart/form-data">
			<table>
				<tr>
					<td><span>商品名称</span></td>
		      		<td><input name="goods_name" id="goods_name" type="text" class="easyui-textbox" style="width:200px;"  data-options="required: true" /></td>
		        </tr>
		        <tr>
					<td><span>使用数量</span></td>
		      		<td><input name="quantity" id="quantity" type="text" class="easyui-numberbox" style="width:200px;"  data-options="precision:0,required: true,min:0,max:200" /></td>
		        </tr>
		        <tr>
		       		<td><span>使用人</span></td>
		      		<td><input name="use_emploee" type="text" class="easyui-textbox" style="width:200px;" /></td>
		      	</tr>
		      	<tr>
					<td><span>使用日期</span></td>
					<td><input name="use_dttm" id="use_dttm" class="easyui-datebox" style="width: 200px;" data-options="editable:false,required:true"></input></td>
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

<div id="detailDivAdd" name="detailDivAdd" class="easyui-dialog" style="width:320px;height:220px;padding:20px 10px" closed="true" modal=true hidden>
		<form id="detailFormAdd" method="post" enctype="multipart/form-data">
			<table>
				<tr>
					<td><span>使用商品</span></td>
					<td><input name="id_add" id="id_add" class="easyui-combobox" maxLength=50 style="width: 200px;"	data-options="url:'/mgt/goods/dropDownList',method: 'post',valueField: 'dKey',textField: 'dValue',panelWidth: 200,panelHeight:300,formatter: formatDropDown,required: true,editable:false"></td>
				</tr>
				<tr>
					<td><span>使用数量</span></td>
					<td><input name="quantity_add" type="text" class="easyui-numberbox" style="width: 200px;" data-options="precision:0,min:0,max:999,required:true" /></td>
				</tr>
				<tr>
					<td><span>使用人</span></td>
					<td><input name="use_emploee_add" type="text" class="easyui-textbox" style="width: 200px;" /></td>
				</tr>
				<tr>
					<td><span>使用日期</span></td>
					<td><input name="use_dttm_add" id="use_dttm_add" class="easyui-datebox" style="width: 200px;" data-options="editable:false,required:true"></input></td>
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
			url : "/mgt/use/list",
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
		var endTime=strDate+" 23:59:59";
		$('#use_dttm_start').datetimebox('setValue',startTime);
		$('#use_dttm_end').datetimebox('setValue',endTime);
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
		$("#detailDivAdd").dialog('open').dialog('setTitle', '添加');
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
					url : "/mgt/use/delete",
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
	//编辑
	function editInfo() {
		var rows = $('#detailList').datagrid('getSelections');
		if (rows.length != 1) {
			$.messager.alert('提示', "请选择一条数据");
			return;
		}
		if(rows[0].is_effect==2){
			$.messager.alert('提示', "使用记录已生效无法修改");
			return;
		}
		$.ajax({
			type : "post",
			url : "/mgt/use/info",
			data : JSON.stringify(rows[0]),
			dataType : 'json',
			contentType : 'application/json',
			success : function(data) {
				$('#detailForm').form('reset');
				$('#detailForm').form('load', data);
				$("#detailDiv").dialog('open').dialog('setTitle', '编辑');
				document.getElementById("formType").value = "update";
			},
			error : function() {
				$.messager.alert('提示', "编辑失败", 'error');
			}

		})
		$('#detailForm').form('reset');
		$("#detailDiv").dialog('open').dialog('setTitle', '编辑');
		document.getElementById("formType").value = "update";
		$("#goods_name").textbox('disable');
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
			submitUrl = "/mgt/use/update";
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
			url : "/mgt/use/save",
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
	//把订单生效
	function isEffectInfo() {
		var rows = $('#detailList').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert('提示', "请选择要生效的记录");
			return;
		}
		
		$.messager.confirm('生效', '你确认要生效选中的记录吗?', function(r) {
			if (r) {
				$.ajax({
					type : "post",
					url : "/mgt/use/isEffect",
					data : JSON.stringify(rows),
					dataType : 'json',
					contentType : 'application/json',
					success : function(data) {
						data = JSON.stringify(data);
						if (data == "true") {
							$.messager.alert('提示', "记录生效成功");
							query();
						} else {
							$.messager.alert('提示', "记录生效失败", 'error');
						}
					},
					error : function() {
						$.messager.alert('提示', "记录生效失败", 'error');
					}

				})
			}
		});
	}
	
</script>
</body>
</html>
