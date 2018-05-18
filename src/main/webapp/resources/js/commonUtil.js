//自定义格式化日期方法
Date.prototype.pattern=function(fmt) {         
	    var o = {         
	    "M+" : this.getMonth()+1, //月份         
	    "d+" : this.getDate(), //日         
	    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时         
	    "H+" : this.getHours(), //小时         
	    "m+" : this.getMinutes(), //分         
	    "s+" : this.getSeconds(), //秒         
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度         
	    "S" : this.getMilliseconds() //毫秒         
	    };         
	    var week = {         
	    "0" : "/u65e5",         
	    "1" : "/u4e00",         
	    "2" : "/u4e8c",         
	    "3" : "/u4e09",         
	    "4" : "/u56db",         
	    "5" : "/u4e94",         
	    "6" : "/u516d"        
	    };         
	    if(/(y+)/.test(fmt)){         
	        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));         
	    }         
	    if(/(E+)/.test(fmt)){         
	        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);         
	    }         
	    for(var k in o){         
	        if(new RegExp("("+ k +")").test(fmt)){         
	            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));         
	        }         
	    }         
	    return fmt;         
   };
  
   /**
    * 将yyyy-MM-dd HH:mm:ss格式的字符串
    * 转换成日期
    * @param dateStr
    */
	function stringToDate(dateStr){
		 var str = dateStr.replace(/-/g,"/");
		 
//		 var str = dateStr.split(" "); 
//		 var temp_date = str[0].split("-");
//		 var temp_time = str[1].split(":");
//		  
//		 var year = temp_date[0];
//		 var month = temp_date[1];
//		 var day = temp_date[2];
//		  
//		 var hour = temp_time[0];
//		 var min = temp_time[1];
//		 var ss = temp_time[3];
		  
//		 return new Date(year,month,day,hour,min,ss);
		 return new Date(str);
	 };
   
   
   /**
    * 定义图片工具类
    */
   function ImageUtil(){};
  /**按照输入的参数自动选择等比缩放图片大小
   * imageSrc表示待缩放的图片
   * W表示缩放后的宽度
   * H表示缩放后的高度
   */
  ImageUtil.prototype.autoResizeImage=function(imageSrc, W, H){
	  var tempImg = new Image();
	  tempImg.src= imageSrc.src;
	  var tempW = tempImg.width;
	  var tempH = tempImg.height;
	  var ratio = 1;//缩放比例
	  var wRatio = W/tempW;
	  var hRatio = H/tempH;
	  if(tempW>0&&tempH>0){
		  if(W==0&&H==0){//如果传入宽高的参数都是0则直接显示原图
			  ratio = 1;
		  }else if(W==0&&H>0){//如果传入的宽度参数为0则宽度不受限制，按比例缩放高度
			  if(hRatio<1){
				  ratio = hRatio; 
			  }
		  }else if(W>0&&H==0){//如果传入的高度参数为0则高度不受限制，按比例缩放宽度
			  if(wRatio<1){
				  ratio = wRatio;  
			  }
		  }else if(W>0&&H>0){
			  if(wRatio<1||hRatio<1){
				  ratio = (wRatio<=hRatio?wRatio:hRatio); 
			  }
		  }
	  }
	  if(ratio<1){
		  tempW = tempW*ratio;
		  tempH = tempH*ratio;
	  }
	  imageSrc.width = tempW;
	  imageSrc.height = tempH;
	  return imageSrc;
  };
 
  /**
   * 将form表中中的控件值序列化成jsonObject
   * @param formId form元素的ID
   * @returns {___anonymous3124_3125}
   */
  function formToJson(formId){
	  var value_array = $("#"+formId).serializeArray();
	  return serializeObject(value_array);
  };  
  
  /**
   * 将jsonArray转成jsonObject
   * @param serializeArray 
   * @returns 
   */
  function serializeObject(serializeArray) {
  	var a, o, h, i, e;
  	a = serializeArray;
  	o = {};
  	h = o.hasOwnProperty;
  	for (i = 0; i < a.length; i++) {
  		e = a[i];
  		if (!h.call(o, e.name)) {
  			o[e.name] = e.value;
  		}
  	}
  	return o;
  };
  
  /**
   * 关闭当前窗口
   */
  function closeWindow() {
	if (navigator.userAgent.indexOf("MSIE") > 0) {
		if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
			window.opener = null;
			window.close();
		} else {
			window.open('', '_top');
			window.top.close();
		}
	} else if (navigator.userAgent.indexOf("Firefox") > 0) {
		window.location.href = 'about:blank ';
	} else {
		window.opener = null;
		window.open('', '_self', '');
		window.close();
	}
}
/**
 * 转类似<,"等特殊字符
 * @param str
 */  
function replaceXMLChar(str){
	str = str.replace(/&lt;/g,"<");
	str =  str.replace(/&gt;/g,">");
	str =  str.replace(/&quot;/g,"\"");
	str =  str.replace(/&ldquo;/g,"“");
	str =  str.replace(/&rdquo;/g,"”");
	
	return str;
}  
  

/**
 * 字段有效性翻译
 * @param value
 * @param rowData
 * @param rowIndex
 * @returns {String}
 */
function formatEnableFlg(value,rowData,rowIndex){
	var result ="";
	switch(value){
	case "0":
		result = "无效";
		break;
	case "1":
		result = "有效";
		break;
	}
	return result;
}


function formatIsEffect(value,rowData,rowIndex){
	var result ="";
	switch(value){
	case 1:
		result = "未生效";
		break;
	case 2:
		result = "已生效";
		break;
	}
	return result;
}


/**
 * 格式日期控件显示格式
 * @param date
 * @returns {String}
 */
function formatDateStr(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return m+'-'+d+'-'+y;
}

