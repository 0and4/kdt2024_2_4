package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dto.PlayInfoDto;
import dto.ReservationDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectSeatController implements Initializable{
	private ReservationDto reservationDto;
	private PlayInfoDto playInfoDto;
	@FXML private VBox seatVBox;
	@FXML private Button nextPage;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	public void init() {
		//좌석의 개수...
		int length = playInfoDto.getSeat();
		//테스트 코드 나중에 지우시면 됩니다..
		playInfoDto.getReserveSeats().add("D4");
		playInfoDto.getReserveSeats().add("D5");
		playInfoDto.getReserveSeats().add("D6");
		System.out.println("좌석수 " + length);
		int row = length / 10;
		int cnt = 0;
		int sum = reservationDto.getAdult() + reservationDto.getOld() + reservationDto.getYouth() + reservationDto.getTreatment();
		int check_cnt = 0;
		for(int i = 0; i < row; i++) {
			HBox hbox = new HBox();
			hbox.setAlignment(Pos.CENTER);
			hbox.setPrefHeight(65);
			hbox.setPrefWidth(650);
			for(int col = 0; col < 10; col++) {
				if(cnt < length) {
					cnt++;
					char rowA = (char)((int)'A' + i);
					String seat = ""+ rowA + (col + 1);
					ToggleButton toggle = new ToggleButton();
					
					toggle.setText(seat);
					
					toggle.setPrefHeight(65);
					toggle.setPrefWidth(65);
					
					System.out.println("생성한 좌석" + seat);
					
					//만약 이미 예약된 버튼이라면...
					if(!playInfoDto.getReserveSeats().contains(seat)) {
					//토글 버튼 설정...
						toggle.setOnAction(e -> {
							if(toggle.isSelected()) {
								if(sum > reservationDto.getSeatList().size())
									reservationDto.getSeatList().add(seat);
								else {
									System.out.println("인원수가 다 찼습니다.. max 인원수: "+ sum);
									toggle.setSelected(false);
								}
							}
							else 	{
								reservationDto.getSeatList().remove(seat);
							}
						});
					}
					else {
						toggle.setDisable(true);
						toggle.setStyle("-fx-background-color: #000000;");
					}
					hbox.getChildren().add(toggle);
				}
				else {
					break;
				}
			}
			seatVBox.getChildren().add(hbox);
		}
		
	}
}
