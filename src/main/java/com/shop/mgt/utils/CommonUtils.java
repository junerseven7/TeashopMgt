package com.shop.mgt.utils;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shop.mgt.model.GoodsInfoDTO;



public class CommonUtils {

	
	/**
	 * 获取当前系统时间 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getTimeStamp(){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		return sdf.format(new Date());
	}
	
	/**
	 * 获取当前系统时间 
	 * @return yyyy-MM-dd HH:mm:ss:SS
	 */
	public static String getTimeStampSS(){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss:SS" );
		return sdf.format(new Date());
	}
	
	/**
	 * 获取当前系统时间 
	 * @return HH:mm:ss
	 */
	public static String getTime(){
		SimpleDateFormat sdf =   new SimpleDateFormat( "HH:mm:ss" );
		return sdf.format(new Date());
	}
	
	
	 /**
     * 获得主机名
     */
    public static String getLocalHostName(){
        try{
            InetAddress addr=InetAddress.getLocalHost();
            return addr.getHostName();
        }catch(Exception e){
            return "";
        }
    }
    
    
    /**
     * 把毫秒转化成日期
     * @param millSec(毫秒数)
     * @return
     */
    public static String longToDate(Long millSec){
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date date= new Date(millSec);
      return sdf.format(date);
    }
    
    

    public static String convertStr(String str){
      if (str != null && !str.trim().equals("")){
    	  try {
			str = java.net.URLDecoder.decode(str.replaceAll(";", "%"), "utf-8").trim();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			str="";
		}
      }else {
    	  str="";
      }
      return str;
    }
    
    /**
     * 替换xml特殊字符
     * @param str
     * @return
     */
    public static String replaceXMLChar(String str){
    	if(null==str || str.trim().equals("")){
    		return "";
    	}
    	str = str.replace("&gt;", ">").replace("&lt;", "<").replace("&amp;", "&").replace("&apos;", "'").replace("&quot;", "\"");
    	str = str.replace("&ldquo;", "“");
    	str = str.replace("&rdquo;", "”");
    	return str;
    }
    
	  /**
     * Object 转 int
     * @param obj
     * @return
     */
    public static int objectToInt(Object obj){
    	if(obj==null||obj.toString().replace(" ","").equals("")){
    		return 0;
    	}else{
    		return Integer.valueOf(obj.toString());
    	}
    }
    
    /**
     * Object 转 String
     * @param obj
     * @return
     */
    public static String objectToString(Object obj){
    	if(obj==null){
    		return "";
    	}else{
    		return obj.toString();
    	}
    }
    
    /**
     * 获取当前日期
     * @return String 当前日期 yyyy-MM-dd
     */
    public static String getDate(){
    	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
    	Date date = new Date();
    	return sdf.format(date);
    }
    
    /**
     * 计算日期
     * @param initDate 初始化日期 yyyy-MM-dd
     * @param addNum  天数 ,如果为负数则为减
     * @return String 日期想加天数
     * @throws ParseException
     */
    public static String dateAdd(String initDate,int addNum){
    	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
    	Date date;
		try {
			date = sdf.parse(initDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			date=null;
		}
	    Calendar   calendar   =   new   GregorianCalendar(); 
	    calendar.setTime(date); 
	    calendar.add(Calendar.DATE,addNum);//把日期往后增加一天.整数往后推,负数往前移动
	    date=calendar.getTime(); 
	    return sdf.format(date);
    }
    /**
     * 计算月
     * @param initDate 初十日期 yyyy-MM-dd
     * @param addNum   加减月份 数
     * @return
     */
    public static String monthAdd(String initDate,int addNum){
    	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
    	Date date;
		try {
			date = sdf.parse(initDate);
		} catch (ParseException e) {
			e.printStackTrace();
			date=null;
		}
	    Calendar   calendar   =   new   GregorianCalendar(); 
	    calendar.setTime(date); 
	    calendar.add(Calendar.MONTH,addNum);//把日期往后增加一天.整数往后推,负数往前移动
	    date=calendar.getTime(); 
	    return sdf.format(date);
    }
    
    
  /**
   * 根据日期获取该日期所在的周的周一所对应的日期
   * @param initDate
   * @return 周日期
   */
    public static String getWeekFirstDay(String initDate){
    	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
    	Date date;
		try {
			date = sdf.parse(initDate);
		} catch (ParseException e) {
			e.printStackTrace();
			date=null;
		}
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
	    int week = calendar.get(Calendar.DAY_OF_WEEK)-1;
	    if(week==0){
	    	week=7;
	    }
		String firstDayOfWeek=dateAdd(initDate,-week+1);
		return firstDayOfWeek;
    }
    
    /**
     * 获取当月第一天
     * @return
     */
    public static String getMonthFirstDay(){
    	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
    	Calendar ca=Calendar.getInstance();//获取当前日期 
    	ca.set(Calendar.DAY_OF_MONTH, 1);
    	Date date=ca.getTime();
    	return sdf.format(date);
    }
    
    /**
     * 获取当月最后一天
     * @return
     */
    public static String getMonthLastDay(){
    	  SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
    	  Calendar ca = Calendar.getInstance();    
          ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
         return sdf.format(ca.getTime());
    }
    
    
	/**
	 * 获取指定月份 最后一天
	 * @param date yyyy-mm-dd 或 yyyymmdd
	 * @return yyyy-mm-dd 或 yyyymmdd
	 */
   public static String getLastDayOfMonth(String date)
	{
	    String year=date.substring(0,4);
	    String month=date.replace("-", "").substring(4,6);
    	Calendar cal = Calendar.getInstance();
		//设置年份
		cal.set(Calendar.YEAR,Integer.valueOf(year));
		//设置月份
		cal.set(Calendar.MONTH, Integer.valueOf(month)-1);
		//获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		//设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		//格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(cal.getTime());
		if(date.length()==8){
			return lastDayOfMonth.replace("-", "");
		}
		return lastDayOfMonth;
	}
   
	/**
	 * 获取指定月份 第一天
	 * @param date yyyy-mm-dd 或 yyyymmdd
	 * @return yyyy-mm-dd 或 yyyymmdd
	 */
   public static String getFirstDayOfMonth(String date)
	{
	   if(date.length()==8){
		   return date.substring(0,6)+"01";
	   }else if(date.length()==10){
		   return date.substring(0,8)+"01";
	   }else{
		   return date;
	   }
	}
    
    /**
     * 获取上个月第一天
     * @return
     */
    public static String getUpMonthFirstDay(){
    	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
    	Calendar ca=Calendar.getInstance();//获取当前日期 
    	ca.set(Calendar.DAY_OF_MONTH, 1);
    	ca.add(Calendar.MONTH, -1);
    	Date date=ca.getTime();
    	return sdf.format(date);
    }
    
    /**
     * 获取下个月第一天
     * @return
     */
    public static String getNextMonthFirstDay(){
    	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
    	Calendar ca=Calendar.getInstance();//获取当前日期 
    	ca.set(Calendar.DAY_OF_MONTH, 1);
    	ca.add(Calendar.MONTH, 1);
    	Date date=ca.getTime();
    	return sdf.format(date);
    }
    
    /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    } 
    
    /**
     * 日期格式化
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
    	 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
    	 return sdf.format(date);
    }
  
    public static String formatDateTime(Date date) {
   	 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
   	 return sdf.format(date);
   }
    public static String trim(Object obj){
    	if(null==obj||obj.toString().trim().replace(" ", "").equals("")){
    		return "";
    	}else{
    		return obj.toString();
    	}
    }
    
    public static Double nullToZero(Double dou){
    	if(dou==null){
    		return Double.valueOf("0");
    	}else{
    		return dou;
    	}
    }
    
    /**
     * 获取文件保存路径
     * @param filePath
     * @return 
     * windows 路径为 D:\file\, 
     * linxu为 /hbec/file/ 加上参数 filePath,
     * 如果filePath为空 Linux的路径为 /hbec/file/temp/
     */
    public static String getFilePath(String filePath){
		String saveFilePath="";
		String os = System.getProperty("os.name").toLowerCase();  
		if(os.startsWith("win")){  
			saveFilePath="D:\\file\\";
		}else{
			if(filePath==null || filePath.replace(" ", "").equals("")){
				saveFilePath="/hbec/file/";
			}else{
				saveFilePath="/hbec/file/"+filePath;
			}
		}
		return saveFilePath;
	}
    
    /**
     * 获取文件保存路径
     * @return windows 路径为 D:\file\ , Linux路径为 /hbec/file/temp/
     */
    public static String getFilePath(){
		String saveFilePath="";
		String os = System.getProperty("os.name").toLowerCase();  
		if(os.startsWith("win")){  
			saveFilePath="D:\\file\\";
		}else{
			saveFilePath="/hbec/file/";
		}
		return saveFilePath;
	}
    
    /**
     * 判断字符串是否为数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str){ 
    	if(str==null || str.replace(" ", "").equals("")){
    		return false;
    	}
	   Pattern pattern = Pattern.compile("[0-9]*"); 
	   Matcher isNum = pattern.matcher(str);
	   if( !isNum.matches() ){
	       return false; 
	   } 
	   return true; 
    }
    /**
     * 将request请求转为bean
     * @param request
     * @param beanClass
     * @return
     */
    public static <T> T request2Bean(HttpServletRequest request,Class<T> beanClass) {    //返回值为随意的类型   传入参数为request 和该随意类型  
        try {
            T bean = beanClass.newInstance();   //实例化随意类型   
            Enumeration<String> en = request.getParameterNames();   //获得参数的一个列举    
            while (en.hasMoreElements()) {         //遍历列举来获取所有的参数  
                String name = (String) en.nextElement();  
                String value = request.getParameter(name);  
                BeanUtils.setProperty(bean, name, value);   //注意这里导入的是  import org.apache.commons.beanutils.BeanUtils;   
            }  
            return bean;  
        } catch (Exception e) {  
            throw new RuntimeException(e);  //如果错误 则向上抛运行时异常  
        }  
    }  
    
    /**
     * 验证手机号码是否正确
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {  
    	if(isNumber(phone)==false){
    		return false;
    	}
        String regExp = "^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9])|(147))\\d{8}$";  
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(phone);  
        return m.matches();  
    }
    
    /**
     * 1000-9999 随机数
     * @return
     */
    public static int random4num(){
    	int min=1000;
    	int max=9999;
    	int randNum = min + (int)(Math.random() * ((max - min) + 1));
    	return randNum;
    }
    
    private final static Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    	GoodsInfoDTO dto = new GoodsInfoDTO();
    	BeanUtils.setProperty(dto, "idqweqwes", "1");
    	logger.debug(BeanUtils.getProperty(dto, "id"));
	}
}
