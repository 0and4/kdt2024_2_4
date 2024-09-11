package dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.ArrayList;

@Data
@Builder
@Getter
@Setter
public class ReservationDetailDto {
    //상영 시간...
    private Time time;
    //영화 제목...
    private String title;
    //상영 시작 시간...
    private String startDateTime;
    //상영 종료 시간...
    private String endDateTime;
    //좌석 종류 ex) 4DX, 2D...
    private String kind;
    //위치..
    private String section;
    //좌석 리스트....
    private ArrayList<String> seatList;
}
