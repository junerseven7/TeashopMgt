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
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/index.js"></script>
<style>
    	.panel-body{ overflow: inherit;}
    	.panel-body.accordion-body {overflow: auto;}
    	.accordion-noborder .accordion-header, .easyui-accordion ul li{text-align:left;}
    	.easyui-accordion ul li div{border:1px solid #fff;}
    	a:link {color: black;text-decoration:none;}     /* 未被访问的链接     蓝色 */
		a:visited {color: black;text-decoration:none;}  /* 已被访问过的链接   蓝色 */
		a:hover {color: red;text-decoration:none;}    /* 鼠标悬浮在上的链接 蓝色 */
		a:active {color: black;text-decoration:none;}   /* 鼠标点中激活链接   蓝色 */
</style>
<script type="text/javascript">
	function time(){
		var vWeek,vWeek_s,vDay;
		vWeek = ["星期天","星期一","星期二","星期三","星期四","星期五","星期六"];
		var date =  new Date();
		year = date.getFullYear();
		month = date.getMonth() + 1;
		day = date.getDate();
		hours = date.getHours();
		minutes = date.getMinutes();
		seconds = date.getSeconds();
		vWeek_s = date.getDay();
		
		if(month<10){
	        month = "0"+month;
	    }
	    if(date<10){
	        date = "0"+date;
	    }
	    if(hours<10){
	        hours = "0"+hours;
	    }
	    if(minutes<10){
	        minutes = "0"+minutes;
	    }
	    if(seconds<10){
	        seconds = "0"+seconds;
	    }
		
		document.getElementById("currentTime").innerHTML = year + "年" + month + "月" + day + "日" + "\t" + hours + ":" + minutes +":" + seconds + "\t" + vWeek[vWeek_s] ;
	};
	setInterval("time()",1000);
		
</script>
</head>
  <body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
		<noscript>
	    	<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">  </div>
	    </noscript>
	<div region="north" split="true" border="false" style="overflow: hidden; height: 60px; background: url(<%=request.getContextPath()%>/resources/images/mgts.jpg);line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体"> 
        <span style="float: right;padding-right: 20px;height: 55px;padding-top: 15px;" class="head">Welcome Admin<div id="currentTime"></div>
         	<!-- <a href="#" id="editpass">修改密码</a> 
         	<a href="#" id="btnResetPassword">重置密码</a>
         	<a href="#" id="loginOut">安全退出</a> -->
        </span> 
    </div>
    
	<div region="south" split="true" style="height: 30px; background: #D2E0F2;text-align: center;padding-top: 2px;">All Rights Reserved, Copyright &copy; 2018, JH, Ltd.</div>
	<div region="west"  split="false" title="导航菜单" style="width:210px;height:700px;overflow:scroll;" id="west" >
		<div id="nav" class="easyui-accordion" data-options="fit:false,border:false"> 
	   	 	<!--  导航内容 --> 

		</div>
    </div>
    
	<div id="mainPanle" region="center" style="background: #eee; overflow-y: hidden">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			<div title="首页" style="padding: 0px; overflow: hidden;">
				<iframe id="ifrPage" name="ifrPage" width=100% height=100% frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"
					src="/mgt/reports/goodsUse">
				</iframe>
			</div>
		</div>
	</div>
	<div region="east"  split="false" style="width:6px;overflow:hidden;"/>

	<div id="mm" class="easyui-menu" style="width: 150px;">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<!-- <div id="mm-exit">退出</div> -->
	</div>
	
</body>
</html>
