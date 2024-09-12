package server.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

@Builder
@Getter
@Setter
@Data
public class PlayInfoDto {
    //상영 시간...
    private Time time;
    //영화 제목...
    private String title;
    //영상 등급
    private int age;

    //영화 id
    private int movieId;

    HashMap<Integer,PlayInfoDetailDto> playInfoDetailDtos;
}
