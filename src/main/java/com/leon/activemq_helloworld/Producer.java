package com.leon.activemq_helloworld;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 生产者（p2p模式）
 * 
 * @author leon
 *
 */
public class Producer {
	public static void send(String msg, String distinationName) throws Exception {
		// 1、创建工厂连接对象，要指定ip和端口号（61616是默认的openwrite端口，在conf/activemq.xml里配置）
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
		// 2、使用连接工厂创建一个连接对象
		Connection connection = connectionFactory.createConnection();
		// 3、开启连接
		connection.start();
		// 4、使用连接对象创建会话（session）对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5、使用会话对象创建目标对象，分为queue（p2p：一对一）和topic（发布订阅：一对多）
		Queue queue = session.createQueue(distinationName);
		// 6、使用会话对象创建消息生产者对象
		MessageProducer producer = session.createProducer(queue);
		// 7、使用会话对象创建一个消息对象
		TextMessage textMessage = session.createTextMessage(msg);
		// 8、发送消息
		producer.send(textMessage);
		// 9、关闭资源
		producer.close();
		session.close();
		connection.close();
	}

	public static void main(String[] args) {
		try {
			send("hello world!", Const.MQ_DISTINATION_NAME);
			System.out.println("消息已发送");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
