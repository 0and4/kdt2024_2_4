package socketserver;

import client.dto.ReservationDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import enumcode.RequestCode;
import server.container.Container;
import server.controller.MainController;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final MainController mainControllerManager;
    private final client.controller.MainController mainControllerClient;
    public Server(){
        this.mainControllerManager = Container.setMainController();
        this.mainControllerClient = client.container.Container.setMainController();
        InitServer();
    }

    public void InitServer() {
        try {
            //스레드 풀 생성합니다..
            ExecutorService executorService = Executors.newFixedThreadPool(10);

            //ServerSocketChannel 열기
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

            //ServerSocketChannel 포트 바인딩
            serverSocketChannel.bind(new InetSocketAddress(50001));
            System.out.println("[서버 시작]");

            executorService.execute(() -> {
                try {
                    //지속적인 클라이언트의 연결 요청 수락
                    while (true) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        InetSocketAddress isa = (InetSocketAddress) socketChannel.getRemoteAddress();
                        System.out.println(isa.getHostName() + "연결 수락..");

                        //스레드풀 작업정리
                        executorService.execute(() -> {
                            //작업 스레드 이름 얻기
                            String threadName = Thread.currentThread().getName();
                            try {
                                Charset charset = Charset.forName("UTF-8");
                                //클라이언트가 보낸 데이터 받기

                                ByteBuffer byteBuffer = ByteBuffer.allocate(200000);
                                int byteNum = socketChannel.read(byteBuffer);
                                if (byteNum == -1) {
                                    throw new IOException();
                                }
                                byteBuffer.flip();
                                String message  = charset.decode(byteBuffer).toString();
                                System.out.println("[" + threadName + "]" +
                                         isa.getHostName() + " 데이터 받기: " + message);

                                //messageJson to RequestDto
                                ObjectMapper objectMapper = new ObjectMapper();
                                HashMap<String, Object> jsonMap = objectMapper.readValue(message, new TypeReference<HashMap<String, Object>>() {});
                                RequestCode requestCode = RequestCode.valueOf(jsonMap.get("requestCode").toString());
                                switch (requestCode) {
                                    case RequestCode.GET_PLAYINFO_TIME:

                                        System.out.println("원하는 날짜" + jsonMap.get("body").toString());
                                        LocalDateTime dateTime = LocalDateTime.parse(jsonMap.get("body").toString());

                                        String returnValue = mainControllerClient.getPlayInfo(dateTime);
                                        System.out.println("보낼 데이터: " + returnValue);
                                        byteBuffer =charset.encode(returnValue);
                                        socketChannel.write(byteBuffer);
                                        break;
                                    case RequestCode.GET_COST_RESERVATIONDTO:
                                        ReservationDto reservationDto = objectMapper.convertValue(jsonMap.get("body"), new TypeReference<ReservationDto>() {
                                        });
                                        Integer value = mainControllerClient.getCost(reservationDto);
                                        byteBuffer =charset.encode(value.toString());
                                        socketChannel.write(byteBuffer);
                                        break;
                                    case RequestCode.GET_PHONENUMBER_STRING:
                                        String info = mainControllerClient.getMemberInfo(jsonMap.get("body").toString());
                                        byteBuffer =charset.encode(info);
                                        socketChannel.write(byteBuffer);
                                        break;
                                    case RequestCode.POST_RESERVE_RESERVEDTO:
                                        ReservationDto reservationDto1 = objectMapper.convertValue(jsonMap.get("body"), new TypeReference<ReservationDto>() {
                                        });
                                        mainControllerClient.setReserve(reservationDto1);
                                        break;
                                        //로그인 요청을 할 경우
                                    case RequestCode.POST_LOGIN_IDANDPASS:

                                        HashMap<String, Object> admin = objectMapper.convertValue(jsonMap.get("body"), new TypeReference<HashMap<String,Object>>() {
                                        });
                                        String answer = mainControllerManager.login(admin.get("admin").toString(), admin.get("password").toString());
                                        byteBuffer =charset.encode(answer);
                                        socketChannel.write(byteBuffer);
                                        break;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            finally {
                                //연결끊기...
                                try {
                                    socketChannel.close();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try {
                        //serverSocketChannel닫기..
                        serverSocketChannel.close();
                        //스레드풀 종료..
                        executorService.shutdown();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
            };
        });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
