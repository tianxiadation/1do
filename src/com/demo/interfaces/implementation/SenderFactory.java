package com.demo.interfaces.implementation;

import com.demo.interfaces.Sender;

public class SenderFactory {
	Sender s;
	public Sender produce(String type) {
		if ("mail".equals(type)) {
			return new MailSender();
		} else if ("qq".equals(type)) {
			return new QQSender();
		} else {
			System.out.println("请输入正确的类型!");
			return s;
		}
	}
	

}
