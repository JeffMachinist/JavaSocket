
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(10081);
        // ����������׽��ֶ���
        System.out.println("�������׽����Ѵ����ɹ�\n�ȴ��ͻ���������\n");
        Socket socket = serverSocket.accept();
        // �����ͻ���
		// ����һ���̳߳������з��ͺͽ�����Ϣ
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
		    tpe.execute(() ->{
				Scanner scanner = new Scanner(System.in);
            try {
                OutputStream outputStream = socket.getOutputStream();
                while(true) {
                    System.out.println("��������Ҫ���͵����ݣ������");
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
                    System.out.println("�ͻ��ˣ� " + new String(buf,0,read));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
		});
    }
}