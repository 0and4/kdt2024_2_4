package client.service;

import classLoader.Connect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import client.dto.PlayInfoDto;
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
            " select p.play_info_id, p.start_date, p.end_date, m.url, m.running_time , m.title , m.age, t.kind, t.seat, t.section" +
            " from play_info p join movie m on p.movie_id = m.movie_id join theater t on t.theater_id = p.theater_id";
    //입력받은 날짜를 기준으로 상영정보를 가져오는 란....
    //일별... 2024-09-12날짜를 주면은 제가 값을 리턴하게 만들게요.....
    //이미 예약한 좌석 정보도 넘기기...
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
                        //상영 id
                        .playInfoId(rs.getInt("play_info_id"))
                        .title(rs.getString("title"))
                        .age(rs.getInt("age"))
                        .startDateTime(rs.getTimestamp("start_date").toLocalDateTime().toString())
                        .endDateTime(rs.getTimestamp("end_date").toLocalDateTime().toString())
                        .kind(rs.getString("kind"))
                        .section(rs.getString("section"))
                        //총 좌석수
                        .seat(rs.getInt("seat"))
                        //상영시간
                        .time(rs.getTime("running_time"))
                        .reserveSeats(new ArrayList<String>())
                        .build();
                //subQuery질문...
                int playInfoId = rs.getInt("play_info_id");
                String subSql = "select rd.seat\n" +
                        "from reservation_detail rd\n" +
                        "join reservation r on rd.reservation_id = r.reservation_id join play_info p on p.play_info_id = r.play_info_id where p.play_info_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(subSql);
                preparedStatement.setInt(1, playInfoId);
                ResultSet subResult = preparedStatement.executeQuery();
                while (subResult.next()){
                    String seat = subResult.getString("seat");
                    playInfoDto.getReserveSeats().add(seat);
                }
                System.out.println(playInfoDto.getReserveSeats().toString());
                playInfoDtoList.add(playInfoDto);
            }
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