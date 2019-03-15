package com.demo.interfaces.implementation;

import com.demo.interfaces.Sender;

public class FactoryTest {
	public static void main(String[] args) {
		SenderFactory factory = new SenderFactory();
		Sender sender = factory.produce("qq");
		sender.Send();
		Sender sender1 = factory.produce("mail");
		sender1.Send();
		Sender sender2 = factory.produce("ios");
		sender2.Send();
	}

}
