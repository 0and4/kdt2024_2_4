package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import dto.PlayInfoDto;
import dto.RequestCode;
import dto.RequestDto;
import dto.ReservationDto;
import handleMessage.HandleFunction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyController implements Initializable{
	private PlayInfoDto playInfo;
	private ReservationDto reserveDto;
	@FXML private Label title, time, seatAndSection, number, totalCost;
	@FXML private ImageView imageView;
	@FXML private Button savePointBtn;
	@FXML private Button usePointBtn;
	@FXML private Button payment;
	public void init() {
		Image image = new Image(playInfo.getUrl());
		imageView.setImage(image);
		title.setText(playInfo.getTitle());
		time.setText(String.format("%s : %s", playInfo.getStartDateTime(), playInfo.getEndDateTime()));
		seatAndSection.setText(String.format("%s : %s", playInfo.getSection(),reserveDto.getSeatList().toString()));
		number.setText(String.format("%d 명", reserveDto.getSeatList().size()));
		HandleFunction handle = new HandleFunction();
		RequestDto rDto = new RequestDto();
		rDto.setRequestCode(RequestCode.GET_COST_RESERVATIONDTO);
		rDto.setBody(reserveDto);
		int returnValue = Integer.parseInt(handle.submit(rDto));
		System.out.print(returnValue);
		totalCost.setText((returnValue -reserveDto.getDiscount()) + "(원)");
		
		//포인트 적립 버튼 클릭시...
		savePointBtn.setOnMouseClicked(e->{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PointSavingPopup.fxml"));
			Parent nextPage = null;
			try {
				nextPage = loader.load();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			PointSavingPopupController pointController = loader.getController();
			pointController.setPlayInfo(playInfo);
			pointController.setReserveDto(reserveDto);
			Stage stage = (Stage) savePointBtn.getScene().getWindow();
			stage.setScene(new Scene(nextPage));
		});
		usePointBtn.setOnMouseClicked(e->{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PointUsingPopup.fxml"));
			System.out.println("로드 성공..");
			Parent nextPage = null;
			try {
				nextPage = loader.load();
				PointUsingPopupController pointController = loader.getController();
				pointController.setPlayInfoDto(playInfo);
				pointController.setReserveDto(reserveDto);
				Stage stage = (Stage) usePointBtn.getScene().getWindow();
				stage.setScene(new Scene(nextPage));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		//결제하기 버튼 클릭시...
		payment.setOnMouseClicked(e->{
			RequestDto requestDto = new RequestDto();
			
			requestDto.setBody(reserveDto);
			requestDto.setRequestCode(RequestCode.POST_RESERVE_RESERVEDTO);
			HandleFunction handle1 = new HandleFunction();
			String answer = handle1.submit(requestDto);
			System.out.println("예약번호: " + answer);
			Parent root = null;
			try {
				root = (Parent)FXMLLoader.load(getClass().getResource("/fxml/kioskMain.fxml"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Scene scene = new Scene(root);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PointSavingPopup.fxml"));
			Parent nextPage = null;
			try {
				nextPage = loader.load();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Stage stage = (Stage) payment.getScene().getWindow();
			stage.setTitle("kiosk");
			stage.setResizable(false);
			stage.setScene(new Scene(nextPage));
		});
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
