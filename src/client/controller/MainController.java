package client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import client.dto.ReservationDto;
import client.service.MemeberService;
import client.service.PlayInfoService;
import client.service.ReservationService;

import java.time.LocalDateTime;

public class MainController {
    private final PlayInfoService playInfoService = new PlayInfoService();

    private final ReservationService reservationService = new ReservationService();

    private final MemeberService memeberService = new MemeberService();

    //영화 정보 불러오는 서비스를 처리하는 곳...
    public String getPlayInfo(LocalDateTime time) throws JsonProcessingException {
        String info = playInfoService.getPlayInfo(time);
        return info;
    }

    //예약 정보를 처리하는 곳...
    //필요한 값... String 전화번호, int play_info 키값...
    public String setReserve(ReservationDto reservationDto) {
        String response = reservationService.setReservation(reservationDto);
        return response;
    }

    //멤버 서비스관련...
    public String getPhoneNumber(String phoneNumber) {
        String response = memeberService.getPhoneNumber(phoneNumber);
        return phoneNumber;
    }

    public String getMemberInfo(String phoneNumber){
        String response = memeberService.getMemberInformation(phoneNumber);
        return response;
    }

    public String getReservationDetailById(int id){
        String response = reservationService.getReservationDetailById(id);
        return response;
    }
    public String getReservationDetailByPhoneNumber(String phone){
        String response = reservationService.getReservationDetailByPhoneNumber(phone);
        return response;
    }

    public String deleteByReservationId(int id){
        String response = reservationService.deleteReservationById(id);
        System.out.println(response);
        return response;
    }
}
