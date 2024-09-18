package dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ReservationDAO {
	public void saveReservation(MovieData movieData, String seats, int totalPrice, String reservationNumber, List<String> peopleDetails) {
		String sql = "INSERT INTO reservation (movie_name, movie_type, movie_runtime, movie_date, movie_start, movie_end, movie_theater, seats,  people, resNumber, totalprice) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try(Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
			// 각 필드에 값 설정
	        pstmt.setString(1, movieData.getSelectedMovieTitle());
	        pstmt.setString(2, movieData.getSelectedMovieType());
	        pstmt.setString(3, movieData.getSelectedMovieRuntime());
	        pstmt.setString(4, movieData.getSelectedMovieDate());
	        pstmt.setString(5, movieData.getSelectedMovieStartTime());
	        pstmt.setString(6, movieData.getSelectedMovieEndTime());
	        pstmt.setString(7, movieData.getSelectedMovieSection());
	        pstmt.setString(8, seats); // 선택한 좌석 정보
	        pstmt.setString(9, String.join(", ", peopleDetails)); // 인원 정보
	        pstmt.setString(10, reservationNumber); // 예매 번호
	        pstmt.setInt(11, totalPrice);
	        /*pstmt.setInt(12, usePoint);
	        pstmt.setInt(13, savePoint);*/
	     // 쿼리 실행
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("예약 정보가 성공적으로 저장되었습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}
}
