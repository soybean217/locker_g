package com.highguard.Wisdom.struts.listener;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class UdpThread extends Thread {
	Logger logger = Logger.getLogger(UdpThread.class);
	DatagramChannel channel;

	Selector selector;

	public void run() {
		try {
			// 打开一个UDP Channel
			channel = DatagramChannel.open();

			// 设定为非阻塞通道
			channel.configureBlocking(false);
			// 绑定端口
			channel.socket().bind(new InetSocketAddress(5311));

			// 打开一个选择器
			selector = Selector.open();
			channel.register(selector, SelectionKey.OP_READ);
			logger.info("upd listener is working");
		} catch (Exception e) {
			e.printStackTrace();
		}

		ByteBuffer byteBuffer = ByteBuffer.allocate(65536);
		while (true) {
			try {
				// 进行选择
				int n = selector.select();
				if (n > 0) {
					// 获取以选择的键的集合
					Iterator iterator = selector.selectedKeys().iterator();

					while (iterator.hasNext()) {
						SelectionKey key = (SelectionKey) iterator.next();

						// 必须手动删除
						iterator.remove();

						if (key.isReadable()) {
							DatagramChannel datagramChannel = (DatagramChannel) key.channel();
							
							byteBuffer = ByteBuffer.allocate(65536);
							// 读取
							InetSocketAddress address = (InetSocketAddress) datagramChannel.receive(byteBuffer);
							logger.debug(address.toString()+" udp:"+new String(byteBuffer.array()));

							// 删除缓冲区中的数据
							byteBuffer.clear();

							String message = "OK";

							byteBuffer.put(message.getBytes());

							byteBuffer.flip();

							// 发送数据
							datagramChannel.send(byteBuffer, address);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
