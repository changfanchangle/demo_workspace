package java_web;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 最简单的web，
 */
public class demo_01_simple_socket {
    public static void main(String[] arge) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8010);
        System.out.println("服务器启动...");
        while (true){
            Socket socket = serverSocket.accept(); // 阻塞至有请求
            System.out.println("出现请求");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 处理请求
                    System.out.println(socket);
                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        //读取客户端发送来的消息
                        String mess = br.readLine();
                        System.out.println("客户端："+mess);
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        bw.write(mess+"\n");
                        bw.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
