package dto;

import lombok.*;

import java.sql.Time;

@Getter
@Setter
@ToString
@Builder
@Data
public class PlayInfoDto {
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
}
