package server.service;

import classLoader.Connect;
import client.dto.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import enumcode.StatusCode;
import server.dto.*;

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
        String getRunningTimeSql = "select running_time from movie where movie_id = ?";
        //상영시간..
        Timestamp runningTime;
        try {
            PreparedStatement pstmtRunning = connection.prepareStatement(getRunningTimeSql);
            pstmtRunning.setInt(1, movie_id);
            ResultSet rsRunning = pstmtRunning.executeQuery();
            if (!rsRunning.next()) {
                Response response = new Response();
                response.setStatusCode(StatusCode.BAD_REQUEST.getStatusCode());
                response.setBody("영화가 존재하지 않습니다..");
                return response.responseBuild();
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
        }catch (Exception e){
            Response response = new Response();
            response.setStatusCode(StatusCode.INTERNAL_ERROR.getStatusCode());
            response.setBody("예기치 못한 오류가 발생하였습니다...");
            return response.responseBuild();
        }
        /*
        상영이 안되는 조건
        시작 시간 혹은 종료 시간이 어떤 상영정보의 시간 사이에 존재하면 상영이 불가능하다....
        * */
        String sql = "select * from play_info where theater_id = ? and  ? between start_date and end_date";
        try{
            //시작 시간만 비교하기...
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, theater_id);
            System.out.println(Timestamp.valueOf(startTime));
            pstmt.setTimestamp(2, Timestamp.valueOf(startTime));
            ResultSet rsSql = pstmt.executeQuery();

            //종료 시간만 비교하기...
            PreparedStatement pstmt2 = connection.prepareStatement(sql);
            pstmt2.setInt(1,theater_id);
            pstmt2.setTimestamp(2, Timestamp.valueOf(endTime));
            ResultSet rsSql2 = pstmt2.executeQuery();
            //현재 시간대에 상영정보를 넣을 수 있다면... 즉 겹치는게 없다면....
            if(!rsSql.next() && !rsSql2.next()){
                System.out.println("해당일에 영화 상영이 가능합니다...");
            }
            else {
                Response response = new Response();
                response.setStatusCode(StatusCode.BAD_REQUEST.getStatusCode());
                response.setBody("상영시간이 겹칩니다...");
                return response.responseBuild();
            }
        }catch (Exception e){
            Response response = new Response();
            response.setStatusCode(StatusCode.INTERNAL_ERROR.getStatusCode());
            response.setBody("내부 서버 오류발생... 관리자에게 문의 바람");
        }

        //해당 상영정보를 입력합니다...
        String insertSql = "insert into play_info (movie_id, theater_id, start_date, end_date) values (?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, movie_id);
            pstmt.setInt(2, theater_id);
            pstmt.setTimestamp(3, Timestamp.valueOf(startTime));
            pstmt.setTimestamp(4, Timestamp.valueOf(endTime));
            int affectedRows = pstmt.executeUpdate();
            //현재 제대로 값이 들어갔다면...
            if(affectedRows > 0){
                ResultSet rs = pstmt.getGeneratedKeys();
                rs.next();
                int getKey = rs.getInt(1);
                String response = getPlayInfoByPID(getKey);
                return response;
            }
        }catch (Exception e){
            e.printStackTrace();
            Response response = new Response();
            response.setStatusCode(StatusCode.INTERNAL_ERROR.getStatusCode());
            response.setBody("예기치 못한 오류가 발생하였습니다...");
            return response.responseBuild();
        }
        return null;
    }

    public String getPlayInfoByPID(int playInfoId){
        String sql = "select m.movie_id, p.start_date, p.end_date, m.title, m.running_time, m.age, t.section, t.kind from play_info p join movie m on p.movie_id = m.movie_id join theater t on t.theater_id = p.theater_id where p.play_info_id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, playInfoId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                SinglePlayInfoDto singlePlayInfoDto = SinglePlayInfoDto
                        .builder()
                        .movieId(rs.getInt("movie_id"))
                        .title(rs.getString("title"))
                        .time(rs.getTime("running_time"))
                        .age(rs.getInt("age"))
                        .startDateTime(rs.getTimestamp("start_date").toString())
                        .endDateTime(rs.getTimestamp("end_date").toString())
                        .kind(rs.getString("kind"))
                        .section(rs.getString("section"))
                        .build();
                Response response = new Response();
                response.setStatusCode(StatusCode.SUCCESS.getStatusCode());
                response.setBody(singlePlayInfoDto);
                return response.responseBuild();
            }
        }catch (Exception e){
            e.printStackTrace();
            Response response = new Response();
            response.setStatusCode(StatusCode.INTERNAL_ERROR.getStatusCode());
            response.setBody("예기치 못한 오류가 발생하였습니다...");
            return response.responseBuild();
        }
        return null;
    }
}
