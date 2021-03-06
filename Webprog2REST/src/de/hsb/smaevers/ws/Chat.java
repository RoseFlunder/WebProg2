package de.hsb.smaevers.ws;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket/chat", encoders = {ChatMessageEncoder.class}, decoders = {ChatMessageDecoder.class})
public class Chat {
	
	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<>());
	private static Thread robot = new Thread(new ChatRoboter());
	private static Lock lock = new ReentrantLock();
	
	static class ChatRoboter implements Runnable {
		@Override
		public void run() {
			while (!Thread.interrupted()){
				synchronized (clients) {
					for (Session client : clients){
						try {
							ChatMessage msg = new ChatMessage();
							msg.setName("robot");
							msg.setNumber((int) (Math.random() * 1000));
							client.getBasicRemote().sendObject(msg);
						} catch (IOException | EncodeException e) {
							e.printStackTrace();
						}
					}
				}
				
				try {
					Thread.sleep((int) (Math.random() * 5000));
				} catch (InterruptedException e) {
					break;
				} 
			}
			System.err.println("robot stopping");
		}
	}

	@OnOpen
	public void open(Session session, EndpointConfig config) {
		clients.add(session);
		
		lock.lock();
		try {
			if (!robot.isAlive()){
				System.err.println("start robot");
				robot.start();
			}
		} finally {
			lock.unlock();
		}
	}
	
	@OnClose
	public void close(Session session){
		clients.remove(session);
		
		try {
			lock.lock();
			if (clients.isEmpty()){
				robot.interrupt();
				robot = new Thread(new ChatRoboter());
			}
		} finally {
			lock.unlock();
		}
	}
	
	@OnMessage
	public void onMessage(ChatMessage message, Session session){
		synchronized (clients) {
			for (Session client : clients){
				try {
					client.getBasicRemote().sendObject(message);
				} catch (IOException | EncodeException e) {
					System.err.println("could not send message");
				}
			}
		}
	}

}
