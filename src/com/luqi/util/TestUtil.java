package com.luqi.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.core.Controller;
import com.luqi.service.BoardTaskServiceA;
import com.luqi.timer.TimerManager;

//测试工具
public class TestUtil extends Controller {
	/*
	 2019年3月4日 coco 注解：
	*/
	public void action() {

		renderJson(MsgUtil.successMsg("方法中拦截返回"));
		return;
	}

	/*public static void main(String[] args) {
		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int i =-402;i<0;i++) {
			Date date=new TimerManager().addDay(new Date(), i);
			System.out.println(s.format(date));
			//BoardTaskServiceA.TaskCalculate(date);
	
		}
		//long time=(1600418987485l - (1600418987485l + 8 * 3600000) % 86400000+86399999);
		//System.out.println(time);
		int i=1;
		System.out.println(10000L+i*1000);
		String str="需求[:：](.{2,50}?)[，。\\s\\,]((?=.*?(版本|Version|[Vv]\\d.\\d|移动端|网页端|PC端|客户端|优化|功能|模块|[需要]求]|要求|缺陷|[Bb][Uu][Gg]|问题|应该|[急极切]需).*?).{1,500})$";
		String str1="需求:1懂。在知识类型实现定义页上点确认时，无论是否改动规则定义，都弹出是否保留已有解析的提示，保留则不进行重解析，不保留时对所有未归档的原始知识点进行该规则的重算。目前仅在知识类型定义时可触发该功能，需要优化。";
		System.out.println(str1.matches(str));
		System.out.println(divide(300000000,3));
	}*/
	private static int add(Integer a, Integer b) {

		Integer sum = 0;
		while (b != 0) {
			// a与b无进位相加
			sum = a ^ b;
			b = (a & b) << 1;
			a = sum;
		}
		return sum;
	}

	// 乘法
	private static int multi(int a, int b) {
		// 将乘数和被乘数都取绝对值
		int multiplicand = a < 0 ? add(~a, 1) : a;
		int multiplier = b < 0 ? add(~b, 1) : b;

		int res = 0;
		// 判断multiplier 任何数*0=0
		while (multiplier != 0) {
			// 判断 multiplier 是不是 奇数
			if ((multiplier & 1) != 0) {
				// 如果是奇数 则加上一次multiplicand本身
				res = add(res, multiplicand);
			}
			// multiplicand * 2
			multiplicand <<= 1;
			// multiplier / 2
			multiplier >>>= 1;
		}
		// 计算乘积的符号
		if ((a ^ b) < 0) {
			res = add(~res, 1);
		}
		return res;

	}

	/**
	 * 求相反数：将各位取反加一
	 */
	private static int negative(int num) {
		return add(~num, 1);
	}

	/**
	 * 减法
	 */
	private static int Minus(int a, int b) {
		return add(a, negative(b));
	}

	private static int divide(int a, int b) {
		// 先取被除数和除数的绝对值
		int dividend = a > 0 ? a : add(~a, 1);
		int divisor = b > 0 ? b : add(~b, 1);
		int quotient = 0;// 商
		int remainder = 0;// 余数
		for (int i = 31; i >= 0; i--) {
			// 比较dividend是否大于divisor的(1<<i)次方，不要将dividend与(divisor<<i)比较，
			// 而是用(dividend>>i)与divisor比较，
			// 效果一样，但是可以避免因(divisor<<i)操作可能导致的溢出，
			// 如果溢出则会可能dividend本身小于divisor，但是溢出导致dividend大于divisor
			if ((dividend >> i) >= divisor) {
				quotient = add(quotient, 1 << i);
				dividend = Minus(dividend, divisor << i);
			}
		}
		// 确定商的符号
		if ((a ^ b) < 0) {
			// 如果除数和被除数异号，则商为负数
			quotient = add(~quotient, 1);
		}
		// 确定余数符号
		remainder = b > 0 ? dividend : add(~dividend, 1);
		System.out.println("余数：" + remainder);
		// 返回商
		return quotient;

	}

	/*
	 * public static void main(String[] args) throws Exception { String
	 * str="http://www.4btbtt.com/attach-download-fid-8-aid-28937.htm"; URL url =
	 * new URL(str); HttpURLConnection conn=(HttpURLConnection)url.openConnection();
	 * conn.getResponseCode(); String realUrl=conn.getURL().toString();
	 * conn.disconnect(); System.out.println("真实URL:"+realUrl); }
	 */
	/*
	 * public static void main(String[] args) {
	 * 
	 * String[] str= {"ssss","aaaa","sssss"}; StringBuffer s=new StringBuffer(); for
	 * (String string : str) { s.append("'").append(string).append("',"); }
	 * System.out.println(s.deleteCharAt(s.length()-1));
	 * 
	 * }
	 */
	/**
	 * 描述： 创建人：coco 创建时间：2019年9月3日 上午11:26:50
	 */

	public static void main(String[] args) {
			
	}
 
}