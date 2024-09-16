package dto;

import lombok.*;

import java.sql.Time;
import java.util.ArrayList;

@Getter
@Setter
@ToString
@Builder
@Data
@NoArgsConstructor   // 기본 생성자 자동 생성
@AllArgsConstructor  // 모든 필드를 포함하는 생성자 자동 생성
public class PlayInfoDto {
    //상영 넘버
    private int playInfoId;
    //상영 시간...
    private Time time;
    //영화 제목...
    private String title;
    //영상 등급
    private int age;
    //상영 시작 시간...
    private String startDateTime;
    //상영 종료 시간...
    private String endDateTime;
    //좌석 종류 ex) 4DX, 2D...
    private String kind;
    //위치..
    private String section;

    //좌석 개수...
    private int seat;
    
    private String url;
    //이미 예약된 좌석들...
    private ArrayList<String> reserveSeats;
}
