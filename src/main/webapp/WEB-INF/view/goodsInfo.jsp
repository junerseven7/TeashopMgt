<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MGTS后台管理系统</title>
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
					<td class="he-td">商品编码:</td>
					<td><input name="goods_num" class="easyui-textbox" style="width: 200px;" /></td>
					<td class="he-td">商品名称:</td>
					<td><input name="goods_name" class="easyui-textbox" style="width: 200px;" /></td>
					<td class="he-td">商品类别:</td>
					<td><input name="goods_type" class="easyui-textbox" style="width: 200px;" /></td>
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
<table class="easyui-datagrid" title="商品列表" id="detailList" toolbar="#buttonToolBar"  data-options="pageList : [20,50,100]">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th field="id" hidden></th>
			<th field="goods_num" style="width:100px;" align="center">商品编码</th>
			<th field="goods_name" style="width:150px;" align="center">商品名称</th>
			<th field="goods_type" style="width:100px;" align="center" >商品类别</th>
			<th field="specification" style="width:200px;" align="center" >规格</th>
			<th field="unit" style="width:100px;" align="center" >单位</th>
			<th field="price" style="width:100px;" align="center" >单价(元)</th>
			<th field="crt_dttm" style="width:200px;" align="center" >创建时间</th>
			<th field="lastupt_dttm" style="width:200px;" align="center" >最后修改时间</th>
			<th field="enable_flg" style="width:100px;" align="center" formatter="formatEnableFlg">是否有效</th>
		</tr>
	</thead>
</table>

<div id="detailDiv" name="detailDiv" class="easyui-dialog" style="width:320px;height:270px;padding:10px 10px" closed="true" modal=true hidden>
	<form id="detailForm" method="post" enctype="multipart/form-data">
			<table>
				<tr>
					<td><span>商品代码</span></td>
		      		<td>
		      			<input name="goods_num"  type="text" class="easyui-textbox" style="width:200px;"  data-options="required: false" />
		      		</td>
		        </tr>
		        <tr>
		       		<td><span>商品名称</span></td>
		      		<td>
		      			<input name="goods_name" type="text" class="easyui-textbox" style="width:200px;"  data-options="required: true" />
		      		</td>
		      	</tr>
		      	<tr>
		       		<td><span>商品类别</span></td>
		      		<td>
		      			<input name="goods_type" type="text" class="easyui-textbox" style="width:200px;"  data-options="required: true" />
		      		</td>
		      	</tr>
		      	<tr>
		       		
	      			<td><span>规格</span></td>
					<td>
						<input name="specification" type="text" class="easyui-textbox" style="width:200px;"  data-options="required: false" />
					</td>
		      	</tr>
		      	<tr>
		       		<td><span>单位</span></td>
		        	<td>
		      			<input name="unit"  type="text" class="easyui-textbox" style="width:200px;" />
		      		</td>
		      	</tr>
		      	<tr>
		       		<td><span>单价</span></td>
		        	<td>
		      			<input name="price"   class="easyui-numberbox" style="width:200px;"  data-options="precision:2,groupSeparator:' ',decimalSeparator:'.',required: true"  />
		      		</td>
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
	

<script type="text/javascript" >
<!-- 初始化-->
$(document).ready(function(){
	$("#detailList").datagrid({
		url:"/mgt/goods/list",
		checkOnSelect:true,
		singleSelect:false,
		pagination:true,
		rownumbers:true,
		pageSize:20,
		fitColumns:true
	});
});
//查询
function query(){
	var params = $("#searchForm").serializeArray();
	params = serializeObject(params);
	$("#detailList").datagrid("options").queryParams = params;
	$("#detailList").datagrid("load");
}
//添加
function addInfo(){
	$('#detailForm').form('reset');
	document.getElementById("formType").value="insert";
	$("#detailDiv").dialog('open').dialog('setTitle','添加');
}
//编辑
function editInfo(){
	var rows = $('#detailList').datagrid('getSelections');
	if(rows.length!=1){
		$.messager.alert('提示',"请选择一条数据");
		return;
	}
	
	$.ajax({
		type : "post", 
		url:"/mgt/goods/info",
		data:rows[0],
		success:function(data){
			$('#detailForm').form('reset');
			//$('#detailForm').form('load',rows[0]); 如果不想查询,直接从页面获取数据
			$('#detailForm').form('load',data);
			$("#detailDiv").dialog('open').dialog('setTitle','编辑');
			document.getElementById("formType").value="update";
		},
		error : function() {
			$.messager.alert('提示', "编辑失败", 'error');
		}
		
	})
	$('#detailForm').form('reset');
	//$('#detailForm').form('load',rows[0]);
	$("#detailDiv").dialog('open').dialog('setTitle','编辑');
	document.getElementById("formType").value="update";
}
//删除
function deleteInfo(){
	var rows = $('#detailList').datagrid('getSelections');
	if(rows.length==0){
		$.messager.alert('提示',"请选择要删除的数据");
		return;
	}
	
	$.messager.confirm('删除确认', '你确认要删除选中数据吗?', function(r){
		if(r){
			$.ajax({
				type : "post", 
				url:"/mgt/goods/delete",
				data:JSON.stringify(rows),
				dataType : 'json',
				contentType : 'application/json',
				success:function(data){
					data=JSON.stringify(data);
					if(data=="true"){
						$.messager.alert('提示', "删除成功");
						query();
					}else{
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
function save(){ 
	var detailValidate =$('#detailForm').form('validate');
	if(!detailValidate){
		return;
	}
	var submitUrl="";
	var formType=document.getElementById("formType").value;
	if(formType=="insert"){
		submitUrl="/mgt/goods/save";
	}else if(formType=="update"){
		submitUrl="/mgt/goods/update";
	}else{
		alert(formType);
		$.messager.alert('提示', "请刷新页面重试", 'error');
		return;
	}
	
	$.ajax({
		type : "post", 
		url:submitUrl,
		data:$('#detailForm').serialize(),
		success:function(data){
			if(data=="true"){
				$('#detailDiv').dialog('close');
				$.messager.alert('提示', "保存成功");
				query();
			}else{
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
