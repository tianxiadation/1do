package com.demo.interfaces.implementation;

import com.demo.interfaces.Sender;

public class FactoryTest {
	public static void main(String[] args) {
		SenderFactory factory = new SenderFactory();
		Sender sender = factory.produce("qq");
		sender.Send();
		Sender sender1 = factory.produce("mail");
		sender1.Send();
		//Sender sender2 = factory.produce("ios");
		//sender2.Send();
		Sender sender3 = factory.produceMail();
		sender3.Send();
		Sender sender4 = factory.produceQQ();
		sender4.Send();
		Sender sender5 = SenderFactory.produceMail1();
		sender5.Send();
		
		
		Provider provider = new Mprovider();
		Sender sender7 = provider.produce();
		sender7.Send();

	}

}
