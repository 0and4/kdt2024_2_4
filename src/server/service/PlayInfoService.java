package server.service;

import classLoader.Connect;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.dto.DateDeatilDto;
import server.dto.PlayInfoDetailDto;
import server.dto.PlayInfoDto;
import server.dto.RegisterMovieDto;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayInfoService {
    Connection connection = Connect.getConnection();
    String getPlayMainSql = "select m.title, m.url, m.running_time, m.age, p.start_date, p.end_date, t.kind, t.section, t.theater_id, m.movie_id from play_info p join movie m on p.movie_id = m.movie_id join theater t on p.theater_id = t.theater_id";

    public String getPlayInfoById(int id) {
        PlayInfoDto playInfoDto = functionSql(id);
        ObjectMapper objectMapper = new ObjectMapper();
        String returnValue = null;
        try {
            returnValue = objectMapper.writeValueAsString(playInfoDto);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(returnValue);
        return returnValue;
    }

    public PlayInfoDto functionSql(int id) {
        String getPlaySubSql = "";
        getPlaySubSql += getPlayMainSql;
        getPlaySubSql += " where p.movie_id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(getPlaySubSql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            PlayInfoDto playInfoDto;
            if (rs.next()){
                playInfoDto = PlayInfoDto
                        .builder()
                        .age(rs.getInt("age"))
                        .time(rs.getTime("running_time"))
                        .title(rs.getString("title"))
                        .movieId(rs.getInt("movie_id"))
                        .playInfoDetailDtos(new HashMap<>())
                        .build();
            }
            else {
                return null;
            }
            do {
                PlayInfoDetailDto playInfoDetailDto;
                int key = rs.getInt("theater_id");
                playInfoDetailDto = playInfoDto.getPlayInfoDetailDtos().get(key);
                if (playInfoDetailDto != null) {
                    DateDeatilDto dateDeatilDto = DateDeatilDto
                            .builder()
                            .startDateTime(rs.getTime("start_date").toString())
                            .endDateTime(rs.getTime("end_date").toString())
                            .build();
                    playInfoDetailDto.getDateDeatilDtos().add(dateDeatilDto);
                } else {
                    playInfoDetailDto = PlayInfoDetailDto
                            .builder()
                            .theater_id(rs.getInt("theater_id"))
                            .kind(rs.getString("kind"))
                            .section(rs.getString("section"))
                            .dateDeatilDtos(new ArrayList<>())
                            .build();
                    DateDeatilDto dateDeatilDto = DateDeatilDto
                            .builder()
                            .endDateTime(rs.getTime("start_date").toString())
                            .startDateTime(rs.getTime("end_date").toString())
                            .build();
                    playInfoDetailDto.getDateDeatilDtos().add(dateDeatilDto);
                    playInfoDto.getPlayInfoDetailDtos().put(key, playInfoDetailDto);
                }
            } while (rs.next());
            return playInfoDto;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getAllPlayInfo(){
        String sql = "select movie_id from movie";
        ArrayList<PlayInfoDto> playInfoDtos = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                PlayInfoDto playInfoDto = functionSql(rs.getInt("movie_id"));
                if (playInfoDto != null){
                    playInfoDtos.add(playInfoDto);
                }
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String returnValue = objectMapper.writeValueAsString(playInfoDtos);
            System.out.println(returnValue);
            return returnValue;
        }catch (Exception e){

        }
        return null;
    }

    public String registerPlayInfo(RegisterMovieDto registerMovieDto){
        int movie_id = registerMovieDto.getMovie_id();
        int theater_id = registerMovieDto.getTheater_id();
        int term = registerMovieDto.getTerm();
        LocalDateTime startTime = registerMovieDto.getLocalDateTime();
        LocalDateTime endTime = registerMovieDto.getLocalDateTime();
        String sql = "select * from play_info where theater_id = ? and start_date < ? and end_date > ?";
        String getRunningTimeSql = "select running_time from movie where movie_id = ?";
        //상영시간..
        Timestamp runningTime;
        try {
            PreparedStatement pstmtRunning = connection.prepareStatement(getRunningTimeSql);
            pstmtRunning.setInt(1, movie_id);
            ResultSet rsRunning = pstmtRunning.executeQuery();
            if(!rsRunning.next()){
                return "fail";
            }
            runningTime = rsRunning.getTimestamp("running_time");
            //종료시간을 구하는 로직...
            System.out.println(runningTime.getHours());
            System.out.println(runningTime.getMinutes());
            System.out.println(term);
            endTime = endTime.plusHours(runningTime.getHours());
            endTime = endTime.plusMinutes(runningTime.getMinutes());
            endTime = endTime.plusMinutes(term);
            System.out.println("시작 시간 : " + startTime + "종료 시간: " + endTime);

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, theater_id);
            pstmt.setTimestamp(2, Timestamp.valueOf(startTime));
            pstmt.setTimestamp(3, Timestamp.valueOf(endTime));
            ResultSet rsSql = pstmt.executeQuery();
            //현재 시간대에 상영정보를 넣을 수 있다면... 즉 겹치는게 없다면....
            if(!rsSql.next()){
                System.out.println("해당일에 영화 상영이 가능합니다...");
            }
            else {
                System.out.println("겹치는 시간이 존재합니다...");
                System.out.println("상영 정보 id: " + rsSql.getInt("play_info_id"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
