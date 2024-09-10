package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReservationDto {
    //전화번호란
    private String phoneNumber;
    //상영정보 ID값
    private int play_info;
    //각각의 인원수...
    //청소년
    private int youth = 0;
    //으른
    private int adult = 0;
    //노인
    private int old = 0;
    //우대
    private int treatment = 0;
    //적립여부
    private boolean isSave = false;
    //사용할 뽀인트 금액
    private int discount = 0;
    //좌석 리스트...

    //시간대...
    private LocalDateTime dateTime;

    //지불 금액
    private int payment;
}