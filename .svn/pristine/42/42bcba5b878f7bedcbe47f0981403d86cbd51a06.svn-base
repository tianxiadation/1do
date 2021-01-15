package com.luqi.interfaces.implementation;

import com.luqi.interfaces.Sender;

public class SenderFactory {
	
	public Sender produce(String type) {
		if ("mail".equals(type)) {
			return new MailSender();
		} else if ("qq".equals(type)) {
			return new QQSender();
		} else {
			System.out.println("请输入正确的类型!");
			return null;
		}
	}
	public Sender produceMail() {
		return new MailSender();
	}
	public Sender produceQQ() {
		return new QQSender();
	}
	public static Sender produceMail1(){
		return new MailSender();
	}
	
	public static Sender produceQQ1(){
		return new QQSender();
	}

}
