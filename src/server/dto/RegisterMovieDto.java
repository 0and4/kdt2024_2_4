package server.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Builder
public class RegisterMovieDto {
    //영화 id
    private int movie_id;

    //상영시작 시간
    private LocalDateTime localDateTime;

    //상영할 상영관
    private int theater_id;
    
    //상영시간 텀
    //분
    @Builder.Default
    private int term = 10;
}
