package service;

import classLoader.Connect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.PlayInfoDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlayInfoService {
    //데이테베이스에 접근하기 위한 세팅값을 가져옵니다..
    Connection connection = Connect.getConnection();
    String sql = "" +
            " select p.start_date, p.end_date, m.url, m.running_time , m.title , m.age, t.kind, t.seat, t.section" +
            " from play_info p join movie m on p.movie_id = m.movie_id join theater t on t.theater_id = p.theater_id";
    //입력받은 날짜를 기준으로 상영정보를 가져오는 란....
    public String getPlayInfo(LocalDateTime date) throws JsonProcessingException {
        if(date == null){
            //인자로 받은 값이 null값이라면....
            date = LocalDateTime.now();
        }
        String sqlDetail =  sql;
        List<PlayInfoDto> playInfoDtoList = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlDetail);
            //26번째 줄 ?의 파라미터의 입력값이다..
//            pstmt.setTimestamp(1, Timestamp.valueOf(date));
            //정보를 가져옵니다...
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                //다음값이 있을 때가지.... 계속 값을 가져옵니다...
                //넘겨주는 정보...
                PlayInfoDto playInfoDto = PlayInfoDto.builder()
                        .title(rs.getString("title"))
                        .age(rs.getInt("age"))
                        .startDateTime(rs.getTimestamp("start_date").toLocalDateTime().toString())
                        .endDateTime(rs.getTimestamp("end_date").toLocalDateTime().toString())
                        .kind(rs.getString("kind"))
                        .section(rs.getString("section"))
                        .build();
                playInfoDtoList.add(playInfoDto);
                System.out.println("영화이름"+ rs.getString("title"));
            }
            rs.close();
        }catch (Exception e){
            System.out.println("오류발생..");
            e.printStackTrace();
        }
        //만약 오류가 없이 완벽하게 값이 다 넣어졌다면...
        ObjectMapper objectMapper = new ObjectMapper();
        String returnValue = objectMapper.writeValueAsString(playInfoDtoList);
        System.out.println(returnValue);
        return returnValue;
    }
}