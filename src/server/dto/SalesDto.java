package server.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Builder
@Setter
public class SalesDto {
    //해당 날짜...
    String dateTime;

    //판매금액..
    private int sale;
}