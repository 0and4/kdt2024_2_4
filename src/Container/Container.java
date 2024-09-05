package Container;

import com.fasterxml.jackson.core.JsonProcessingException;
import controller.MainController;

import java.time.LocalDateTime;

public class Container {
    private static MainController mainController;

    //컨테이너의 컨트롤러의 정보를 가져오는 걸로하기...
    public static void setMainController(){
        //현재 메인컨트롤러 값이 비워져 있다면..
        try {
            if(mainController == null){
                mainController = new MainController();
            }
        }catch (Exception e){
            //오류 발생시...
            e.printStackTrace();
        }
    }

    public static void playerInfoService(LocalDateTime time) throws JsonProcessingException {
        if(mainController != null){
            String result = mainController.getPlayInfo(time);
            System.out.println("호출한 값" + result);
        }
        else{
            System.out.println("메인컨트롤러를 먼저 생성해주세요....");
        }
    }

    public static String getPhoneNumber(String phoneNumber) {
        if(mainController != null){
            String result = mainController.getPhoneNumber(phoneNumber);
            System.out.println("전화번호: " + result);
            return result;
        }
        else {
            System.out.println("메인 컨트롤러를 먼저 생성해주십쇼...");
            return null;
        }
    }
}
