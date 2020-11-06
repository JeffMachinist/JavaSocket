
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(10081);
        // 创建服务端套接字对象
        System.out.println("服务器套接字已创建成功\n等待客户机的连接\n");
        Socket socket = serverSocket.accept();
        // 监听客户端
		// 创建一个线程池来进行发送和接收消息
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
		    tpe.execute(() ->{
				Scanner scanner = new Scanner(System.in);
            try {
                OutputStream outputStream = socket.getOutputStream();
                while(true) {
                    System.out.println("请输入你要发送的内容，服务端");
                    String content = scanner.nextLine();
                    outputStream.write(content.getBytes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
			});
        tpe.execute(() ->{
            try {
                InputStream inputStream = socket.getInputStream();
                byte[] buf = new byte[1024*4];
                while (true){
                    int read = inputStream.read(buf);
                    System.out.println("客户端： " + new String(buf,0,read));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
		});
    }
}