

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;


public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(InetAddress.getLocalHost(),10081);
        // 创建Socket套接字
        System.out.println("尝试连接");
        // 创建一个线程池来进行发送和接收消息
		ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
        tpe.execute(() ->{
               try {InputStream inputStream = socket.getInputStream();
            // 创建一个字节输入流来读取消息
                byte[] buf = new byte[1024 * 4];
                // 创建一个Byte数组用来读取消息
                while(true) {
                    int read = inputStream.read(buf);
                    System.out.println("服务端："+new String(buf, 0, read));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
		});

        tpe.execute(() ->{
            Scanner scanner = new Scanner(System.in);
            try {
                OutputStream outputStream = socket.getOutputStream();
                while(true) {
                    System.out.println("请输入你要发送的内容,客户端");
                    String concent = scanner.nextLine();
                    outputStream.write(concent.getBytes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
		});

	}
}
