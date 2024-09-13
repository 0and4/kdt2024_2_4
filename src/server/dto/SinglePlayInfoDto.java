package server.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;


@Builder
@Getter
@Setter
public class SinglePlayInfoDto {
    //영화 Id
    private int movieId;

    //영화 제목...
    private String title;

    //상영시간
    private Time time;

    //영상 등급
    private int age;

    //상영관
    private String kind;

    //위치
    private String section;

    //시작 시간
    private String startDateTime;

    //종료시간
    private String endDateTime;
}