package com.youcan.core.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 验证码 
 * @author HaoNing
 * @version 1.0 
 */
public class Captcha {
	
	public static String SOURCE = "abcdefghigklmnopqrstovwxwz1234567890";
	public static String[] fontTypes = {"\u5b8b\u4f53","\u65b0\u5b8b\u4f53","\u9ed1\u4f53","\u6977\u4f53","\u96b6\u4e66"};  
	public static String sRand = "";
	
	private int width;
	private int height;
	
	public Captcha(int width,int height){
		this.width = width;
		this.height = height;
	}
	/**
	 * 生成随机颜色
	 * @param fc 
	 * @param bc
	 * @return
	 */
	@SuppressWarnings("static-method")
	private Color getColor(int fc, int bc){
		fc = (fc > 255 ? 255 : fc); 
		bc = (bc > 255 ? 255 : bc);
		Random random = new Random();
		return new Color(fc+random.nextInt(bc - fc),fc+random.nextInt(bc - fc),fc+random.nextInt(bc - fc));
	}
	
	public BufferedImage getImage(){
		// 创建内存对象
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
		// 获取图形上下文
		Graphics g = image.getGraphics();
		// 创建随机类的实例
		Random random = new Random();
		// 设定图像背景色
		g.setColor(getColor(200, 250));
		g.fillRect(0, 0, width, height);
		// 在图片背景上增加噪点
		g.setColor(getColor(160,200));
		g.drawRect(0,0,width-1,height-1);
		g.setFont(new Font("Times New Roman",Font.PLAIN,20));
		for( int i = 0; i < 4; i++){
			g.drawString("**********************", 0, 10*(i+2));
		}
		 // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
	    for (int i=0;i<155;i++)
	    {
	     int x = random.nextInt(width);
	     int y = random.nextInt(height);
	            int xl = random.nextInt(12);
	            int yl = random.nextInt(12);
	     g.drawLine(x,y,x+xl,y+yl);
	    }

		sRand = "";
		// 取随机产生的认证码
		for( int i = 0; i < 4; i++){
			boolean isUpper = (random.nextInt(62)%2==0?true:false);
			int start = random.nextInt(36);
			String rand =(isUpper? SOURCE.toUpperCase().substring(start,start+1):SOURCE.substring(start,start+1));
			sRand += rand;
			
			// 设置字体的颜色
			g.setColor(getColor(20,150));
			// 设置字体
			g.setFont(new Font(fontTypes[random.nextInt(fontTypes.length)],Font.BOLD,18+random.nextInt(4)));
			// 将此字符以、画到图片上
			g.drawString(rand,24*i + 10+random.nextInt(8),24);
		}
		g.dispose();
		return image;
	}
}
