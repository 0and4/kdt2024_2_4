package client.service;

/*1. 영화 상단엔 상영 정보가 출력된다.
        - 상영 정보 : 1) 영화 이미지 2) 관람 등급 3) 영화 제목 4)종류(2DX, Laser, 3D) 5) 상영 시간(러닝타임) 6)상영 시작 시간 7) 총 좌석수 8) 남은 좌석 수 9) 상영관
        2. 인원 선택 : 상영정보 아래엔 관람 인원수를 선택해주세요라는 텍스트가 나오며 텍스트 아래엔 일반 / 청소년 / 경로 / 우대 란이 있다. 각각 인원수를 선택할 수 있다.
        3. 좌석 선택 : 화면 하단엔 좌석 위치가 나오며 선택 가능한 좌석은 하얀색, 이미 예약이 되었거나 선택이 불가능한 화면은 회색으로 표시된다. 좌석 위치를 클릭할 시 해당 좌석이 갈색으로 바뀐다.
        4. 좌측 하단에는 총 결제 금액이 표시된다.
        5. 우측 하단에는 [결제하기 버튼]이 표시된다.
        6. 모두 선택하였으면 [결제하기 버튼]이 활성화 된다.

        1. 좌측엔 1) 예매 정보와 2) 총 결제 금액을 나타낸다.
- 예매 정보 : 1) 영화 이미지 2) 관람 등급 3) 영화 제목 4)종류(2DX, Laser, 3D) 5) 상영 시간(러닝타임) 6)상영 시작 시간 7) 총 좌석수 8) 남은 좌석 수 9) 상영관 10) 좌석 위치 11) 인원 정보 12) 예매번호
- 인원 정보 : 일반/ 청소년/ 경로/ 우대 종류 + 인원 수 ex. 일반 1명, 청소년 2명
* 회원 정보(휴대폰 번호) 받는 과정 : 처음 전화번호를 입력했을 때 " 등록된 번호가 없습니다. 회원 가입하시겠습니까? "
[예] 선택 시, 해당 전화번호로 회원가입이 진행됩니다. 적립된 포인트 "00원" [확인]
[아니오] 선택 시, 포인트 적립을 취소합니다. [확인] 버튼 누르면 (결제창으로 다시 이동)
2. 우측(상단)엔 1) 멤버쉽 포인트와 2) 결제 수단 선택을 나타낸다.
- 멤버쉽 [포인트 적립], [포인트 사용하기] 버튼이 있다.
- 멤버쉽 포인트 적립 혹은 멤버쉽 포인트 사용하기 버튼을 클릭하면 (팝업) 전화번호 입력란을 받는 창이 뜬다.
- 비고의 <포인트 팝업 >을 참고한다.
- 포인트 적용 방식 :
3. 우측(하단)엔 결제 방법 1) 카카오 페이 2) 네이버 페이 3) 신용카드 등 결제 수단을 선택할 수 있게 한다.
4. 결제 완료 후 : (팝업) 티켓을 받을 휴대폰 번호를 입력해 주세요. -> 키패드로 입력 후 확인 -> 완료되었습니다.
 */

import classLoader.Connect;
import client.dto.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import client.dto.ReservationDetailDto;
import client.dto.ReservationDto;
import enumcode.StatusCode;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class ReservationService {
    /*
        - 예매 정보 : 1) 영화 이미지 2) 관람 등급 3) 영화 제목 4)종류(2DX, Laser, 3D) 5) 상영 시간(러닝타임) 6)상영 시작 시간 7) 총 좌석수 8) 남은 좌석 수 9) 상영관 10) 좌석 위치 11) 인원 정보 12) 예매번호
        - 인원 정보 : 일반/ 청소년/ 경로/ 우대 종류 + 인원 수 ex. 일반 1명, 청소년 2명
        * 회원 정보(휴대폰 번호) 받는 과정 : 처음 전화번호를 입력했을 때 " 등록된 번호가 없습니다. 회원 가입하시겠습니까? "
        [예] 선택 시, 해당 전화번호로 회원가입이 진행됩니다. 적립된 포인트 "00원" [확인]
     */
    //커넥션 정보 가져오기..
    Connection connection = Connect.getConnection();

    public String setReservation(ReservationDto reservationDto) {
        //만약 전화번호가 없는값이라면.... -> 강제 회원가입을 진행
        
        //상영 정보를 가져옵니다...
        int playInfoId = reservationDto.getPlay_info();
        String phoneNumber = reservationDto.getPhoneNumber();
        int discount = reservationDto.getDiscount();
        int youth = reservationDto.getYouth();
        int adult = reservationDto.getAdult();
        int old = reservationDto.getOld();
        int treatment = reservationDto.getTreatment();
        LocalDateTime localDateTime = LocalDateTime.now();
        int reservationId = 0;
        ArrayList<String> arrayList = reservationDto.getSeatList();


        try {
            String sqlGetMemeber = "select * from user where phone = ?";
            PreparedStatement pstmt = connection.prepareStatement(sqlGetMemeber);
            pstmt.setString(1,phoneNumber);
            ResultSet rs = pstmt.executeQuery();
            //값이 없다면 회원가입을 진행..
            if(!rs.next()){
                String insertSql = "insert into user(phone) values(?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
                preparedStatement.setString(1,phoneNumber);
                preparedStatement.executeUpdate();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if (arrayList.size() != (youth + adult + old + treatment)) {
            System.out.println("좌석 리스트와 인원수가 맞지 않음...");
            return "fail";
        }
        String sql = "insert into reservation (payment, discount, date, play_info_id, phone, saving) values (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //TODO 적립금액 설정하기...
            int cost = getCost(reservationDto);
            pstmt.setInt(1, cost);
            pstmt.setInt(2, discount);
            pstmt.setTimestamp(3, Timestamp.valueOf(localDateTime));
            pstmt.setInt(4, playInfoId);
            pstmt.setString(5, phoneNumber);
            //적립 금액 ... 설정하기
            if (reservationDto.isSave()) {
                int save = (int) (cost * 0.05);
                pstmt.setInt(6, save);
            } else {
                pstmt.setInt(6, 0);
            }
            int affetedRows = pstmt.executeUpdate();
            if (affetedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    reservationId = rs.getInt(1);
                    System.out.println(reservationId);
                    setReservationDetail(reservationId, reservationDto);
                    return Integer.toString(reservationId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //playInfoId를 기준으로 상영관 정보가져오기..
    public int getTheaterId(int playInfoId) {
        try {
            String sql = "select theater_id from play_info where play_info_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, playInfoId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int kindId = rs.getInt("theater_id");
                return kindId;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //상영관 종류 정보를 가져오는 함수
    public String getKind(int theater_id) {
        String sql = "select kind from theater where theater_id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, theater_id);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            String kind = rs.getString("kind");
            return kind;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCost(ReservationDto reservationDto) {
        System.out.println("요금 구하기...");
        //청소년 요금
        int youth = 0;
        int youthNum = reservationDto.getYouth();
        //으른 요금
        int adult = 0;
        int adultNum = reservationDto.getAdult();
        //경로 요금
        int old = 0;
        int oldNum = reservationDto.getOld();
        //우대 요금
        int treat = 0;
        int treatNum = reservationDto.getTreatment();
        String day;
        try {
            int theater_id = getTheaterId(reservationDto.getPlay_info());
            String getTimeSql = "select start_date from play_info where play_info_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(getTimeSql);
            pstmt.setInt(1, reservationDto.getPlay_info());
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            Timestamp timestamp = rs.getTimestamp("start_date");
            //요일 구하기...
            LocalDateTime localDateTime = timestamp.toLocalDateTime();
            DayOfWeek ofWeek = localDateTime.getDayOfWeek();
            System.out.println("해당 요일" + ofWeek);
            switch (ofWeek) {
                case FRIDAY:
                case SUNDAY:
                case SATURDAY:
                    day = "공휴일";
                    break;
                default:
                    day = "평일";
                    break;
            }

            int time = timestamp.getHours();
            System.out.println("시간대" + time);
            //만약 오전 6시 ~ 9시 사이라면...
            Time timeCost;

            if (time >= 6 && time <= 9) {
                LocalTime localTime = LocalTime.of(6, 0);
                timeCost = Time.valueOf(localTime);
                System.out.println(timeCost.toString());
            } else if (time >= 10 && time <= 13) {
                LocalTime localTime = LocalTime.of(10, 0);
                timeCost = Time.valueOf(localTime);
                System.out.println(timeCost.toString());
            } else {
                LocalTime localTime = LocalTime.of(13, 0);
                timeCost = Time.valueOf(localTime);
            }

            String kind = getKind(theater_id);
            System.out.println("상영관 종류" + kind);
            String getCostSql = "select * from charge where time = ? and kind = ? and day = ?";
            pstmt = connection.prepareStatement(getCostSql);
            pstmt.setTime(1, timeCost);
            pstmt.setString(2, kind);
            pstmt.setString(3, day);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String type = rs.getString("type");
                int cost = rs.getInt("cost");
                System.out.println("cost: " + cost);
                switch (type) {
                    case "일반":
                        adult = cost;
                        break;
                    case "청소년":
                        youth = cost;
                        break;
                    case "경로":
                        old = cost;
                        break;
                    case "우대":
                        treat = cost;
                        break;
                }
            }
            int total = adult * adultNum + youth * youthNum + old * oldNum + treat * treatNum;
            return total;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    //예약 상세를 설정하는 함수...

    public void setReservationDetail(int reservationId, ReservationDto reservationDto) {
        String sql = "insert into reservation_detail(reservation_id, seat) values( ?, ?)";

        reservationDto.getSeatList().forEach(seat -> {
            PreparedStatement pstmt = null;
            try {
                pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1, reservationId);
                pstmt.setString(2, seat);
                int affectRow = pstmt.executeUpdate();
                System.out.println("디테일 좌석 만들기.. 성공");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    //예약번호 또는 전화번호로 조회하기....
    public String getReservationDetailById(int id) {
        String sql = "select p.start_date, p.end_date, m.running_time, m.title, t.seat, t.section, t.kind from play_info p join movie m on p.movie_id = m.movie_id join theater t on t.theater_id = p.theater_id  where p.play_info_id = (" +
                "select play_info_id from reservation where reservation_id = ?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1,id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                System.out.println(rs.getString("title"));
                ReservationDetailDto reservationDetailDto = ReservationDetailDto
                        .builder()
                        .title(rs.getString("title"))
                        .startDateTime(rs.getString("start_date"))
                        .endDateTime(rs.getString("end_date"))
                        .section(rs.getString("section"))
                        .kind(rs.getString("kind"))
                        .time(rs.getTime("running_time"))
                        .build();
                System.out.println("예약 상세 찾기...");
                String sqlDetail = "select seat from reservation_detail where reservation_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlDetail);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                ArrayList<String> seatList = new ArrayList<>();
                while (resultSet.next()){
                    seatList.add(resultSet.getString("seat"));
                }
                reservationDetailDto.setSeatList(seatList);
                ObjectMapper objectMapper = new ObjectMapper();
                String returnValue = objectMapper.writeValueAsString(reservationDetailDto);
                System.out.println(returnValue);
                return returnValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //전화번호로 예매내역 출력하기...
    public String getReservationDetailByPhoneNumber(String phoneNumber){
        String sql = "select p.start_date, p.end_date, m.running_time, m.title, t.seat, t.section, t.kind, r.reservation_id from play_info p join movie m on p.movie_id = m.movie_id join theater t on t.theater_id = p.theater_id join reservation r on r.play_info_id = p.play_info_id where p.play_info_id in (" +
                "select play_info_id from reservation where phone = ?) and p.start_date > ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, phoneNumber);
            pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ResultSet rs = pstmt.executeQuery();
            ArrayList<ReservationDetailDto> reservationDetailDtos = new ArrayList<>();
            while (rs.next()){
                ReservationDetailDto reservationDetailDto = ReservationDetailDto
                        .builder()
                        .time(rs.getTime("running_time"))
                        .kind(rs.getString("kind"))
                        .title(rs.getString("title"))
                        .startDateTime(rs.getTimestamp("start_date").toString())
                        .endDateTime(rs.getTimestamp("end_date").toString())
                        .section(rs.getString("section"))
                        .build();
                String sqlDetail = "select seat from reservation_detail where reservation_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlDetail);
                preparedStatement.setInt(1, rs.getInt("reservation_id"));
                System.out.println("예약번호: " + rs.getInt("reservation_id"));
                ResultSet resultSet = preparedStatement.executeQuery();
                ArrayList<String> seatList = new ArrayList<>();
                while (resultSet.next()){
                    seatList.add(resultSet.getString("seat"));
                    System.out.println("넣은 좌석" + resultSet.getString("seat"));
                }
                reservationDetailDto.setSeatList(seatList);
                reservationDetailDtos.add(reservationDetailDto);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String resultValue = objectMapper.writeValueAsString(reservationDetailDtos);
            System.out.println(resultValue);
            return resultValue;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String deleteReservationById(int id){
        try {
            String reservationInfoSql = "select start_date from play_info where play_info_id = (" +
                    "select play_info_id from reservation where reservation_id = ?)";
            PreparedStatement pstmt = connection.prepareStatement(reservationInfoSql);
            pstmt.setInt(1, id);
            ResultSet resultInfo = pstmt.executeQuery();
            Timestamp start_date = null;
            if (resultInfo.next()){
                start_date = resultInfo.getTimestamp("start_date");
                System.out.println(start_date.toString());
            }
            else {
                Response response = new Response();
                response.setStatusCode(StatusCode.NOT_FOUND.getStatusCode());
                response.setBody("일치하는 예약 번호가 없습니다...");
                return response.responseBuild();
            }
            //만약 시간이 초과되지 않았다면...
            if (start_date.toLocalDateTime().isAfter(LocalDateTime.now())){
                String deleteSql = "delete from reservation where reservation_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
                preparedStatement.setInt(1, id);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    Response response = new Response();
                    response.setStatusCode(StatusCode.SUCCESS.getStatusCode());
                    return "success";  // 삭제 성공
                } else {
                    return "false";  // 삭제 실패 (삭제된 행이 없음)
                }
            }
            else {
                return "false";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}