package handleMessage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import dto.RequestCode;
import dto.RequestDto;

public class HandleFunction {
	String message;
	public String submit(RequestDto dto) {
	SocketChannel socketChannel = null;
    try {
        socketChannel = SocketChannel.open();
        System.out.println("[연결요청]");
        socketChannel.connect(new InetSocketAddress("10.2.12.171",50001));
        System.out.println("[연결성공]");

        ByteBuffer byteBuffer = null;
        Charset charset = Charset.forName("UTF-8");

        //서버로 데이터를 보냅니다...
   
        String request = dto.requestBuild().toString();
        byteBuffer = charset.encode(dto.requestBuild().toString());
        socketChannel.write(byteBuffer);

        //서버가 보낸 데이터 받기
        byteBuffer = ByteBuffer.allocate(20000);
        int byteNum = socketChannel.read(byteBuffer);
        if(byteNum == -1){
            throw new IOException();
        }
        byteBuffer.flip();
        String message = charset.decode(byteBuffer).toString();
        System.out.println("받은 데이터: " + message);
        this.message = message;
    }catch (Exception e){
        e.printStackTrace();
    }finally {
        System.out.println("연결을 끊습니다..");
        try {
            socketChannel.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
	return message;
	}
}

