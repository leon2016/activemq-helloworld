package com.leon.activemq_helloworld;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消费者（发布订阅模式）
 * 
 * @author leon
 *
 */
public class Consumer2 {
	public static void cust(String distinationName) throws Exception {
		// 1、创建工厂连接对象，要指定ip和端口号（61616是默认的openwrite端口，在conf/activemq.xml里配置）
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
		// 2、使用连接工厂创建一个连接对象
		Connection connection = connectionFactory.createConnection();
		// 3、开启连接
		connection.start();
		// 4、使用连接对象创建会话（session）对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5、使用会话对象创建目标对象，分为queue（p2p：一对一）和topic（发布订阅：一对多）
		Topic topic = session.createTopic(distinationName);
		// 6、使用会话对象创建消费者对象
		MessageConsumer consumer = session.createConsumer(topic);
		// 7、向消费者对象中设置一个messageListener对象，用来监听和接收消息
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				// TODO Auto-generated method stub
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					try {
						System.out.println("接收到文本消息：");
						System.out.println(textMessage.getText());
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		// 8、保持程序等待接收消息
		System.in.read();
		// 9、关闭资源
		consumer.close();
		session.close();
		connection.close();
	}

	public static void main(String[] args) {
		try {
			cust(Const.MQ_DISTINATION_NAME2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
