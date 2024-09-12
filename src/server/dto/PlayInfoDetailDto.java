package server.dto;

import com.sun.jdi.event.ThreadDeathEvent;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

@Builder
@Setter
@Getter
@Data
public class PlayInfoDetailDto {
    //상영관 number
    private int theater_id;
    //해당 상영관의 종류...
    private String kind;
    //해당 상영관의 위치..
    private String section;
    //해당 상영관의
    private ArrayList<DateDeatilDto> dateDeatilDtos;
}
