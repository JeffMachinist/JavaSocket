

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;


public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(InetAddress.getLocalHost(),10081);
        // ����Socket�׽���
        System.out.println("��������");
        // ����һ���̳߳������з��ͺͽ�����Ϣ
		ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
        tpe.execute(() ->{
               try {InputStream inputStream = socket.getInputStream();
            // ����һ���ֽ�����������ȡ��Ϣ
                byte[] buf = new byte[1024 * 4];
                // ����һ��Byte����������ȡ��Ϣ
                while(true) {
                    int read = inputStream.read(buf);
                    System.out.println("����ˣ�"+new String(buf, 0, read));
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
                    System.out.println("��������Ҫ���͵�����,�ͻ���");
                    String concent = scanner.nextLine();
                    outputStream.write(concent.getBytes());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
		});

	}
}
