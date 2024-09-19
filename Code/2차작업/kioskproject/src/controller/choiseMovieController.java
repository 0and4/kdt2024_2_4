package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.PlayInfoDto;
import dto.RequestCode;
import dto.RequestDto;
import dto.ResponseDto;
import dto.StatusCode;
import handleMessage.HandleFunction;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
 

public class choiseMovieController implements Initializable {
	@FXML private Label ToDay; // 오늘 날짜 설정
	@FXML private Button home; // 홈버튼
	@FXML private Button nextPage;
	@FXML private Button day1,day2,day3,day4,day5,day6,day7; // 날짜 선택버튼
	@FXML private VBox screeningInfoVBox;
	
	@FXML private ScrollPane scrollPane;
	private DateTimeFormatter formatter,buttonformatter;
	
	private int playInfoId;
	private String url;
	private String title;
	private String time;
	
	//최종적으로 선택된 PlayInfoDto
	private PlayInfoDto selected;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		LocalDate today = LocalDate.now();
		formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd(E)");
		buttonformatter = DateTimeFormatter.ofPattern("d"+"일"+"\n(E)");
		ToDay.setText(today.format(formatter)); // 오늘 날짜 불러옴
		
		//오늘 날짜 기준 7일
		day1.setText(today.format(buttonformatter));
		day2.setText(today.plusDays(1).format(buttonformatter));
		day3.setText(today.plusDays(2).format(buttonformatter));
		day4.setText(today.plusDays(3).format(buttonformatter));
		day5.setText(today.plusDays(4).format(buttonformatter));
		day6.setText(today.plusDays(5).format(buttonformatter));
		day7.setText(today.plusDays(6).format(buttonformatter));
		nextPage.setOnMouseClicked(e->{
			try {
				handleNextPage();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
	@FXML
	public void handleDateSelection(ActionEvent event) {
		Button selectedButton = (Button) event.getSource();
		String selectedDate = selectedButton.getText();
		RequestDto requestDto = new RequestDto();
		requestDto.setRequestCode(RequestCode.GET_PLAYINFO_TIME);
		LocalDateTime localDateTime = LocalDateTime.now();
		//숫자가 아닌 문자는 공백으로 만듭니다...
		String number = selectedDate.replaceAll("[^0-9]", "");
		System.out.println(number);
		int dayAfter = Integer.valueOf(number);
		System.out.println(dayAfter - localDateTime.getDayOfMonth());
		dayAfter -= localDateTime.getDayOfMonth();
		localDateTime = localDateTime.plusDays(dayAfter);
		//테스트를 위해 일부러..
		localDateTime = localDateTime.minusDays(3);
		requestDto.setRequestCode(RequestCode.GET_PLAYINFO_TIME);
		requestDto.setBody(localDateTime.toString());
		System.out.println("원하는 날짜.."+localDateTime);
		HandleFunction handleFunction = new HandleFunction();
		String message = handleFunction.submit(requestDto);
		System.out.println(message);
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonMap = null;
		try {
			jsonMap = objectMapper.readValue(message, new TypeReference<Map<String, Object>>() {});
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//만약 성공했다면...
		Integer statusCode = (Integer) jsonMap.get("statusCode");
		if(statusCode == StatusCode.SUCCESS.getStatusCode()) {
			//응답 바디를 얻은 후...
	
	        List<PlayInfoDto> list = null;
			list = objectMapper.convertValue(jsonMap.get("body"), new TypeReference<List<PlayInfoDto>>() {});
			System.out.println("변환 성공.." + list.toString());
			for(PlayInfoDto playDto: list) {
				//정보를 가져옵니다...
				int playInfoId = playDto.getPlayInfoId();
				int age = playDto.getAge();
				String title = playDto.getTitle();
				Time time = playDto.getTime();
				int playTime = time.getHours() * 60 + time.getMinutes();
				String section = playDto.getSection();
				String kind = playDto.getKind();
				String url = playDto.getUrl();
				int seat = playDto.getSeat();
				ArrayList<String> seatList = playDto.getReserveSeats();  
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
				String startTime = LocalDateTime.parse(playDto.getStartDateTime()).format(formatter);
				String endTime = LocalDateTime.parse(playDto.getEndDateTime()).format(formatter);
				playDto.setStartDateTime(startTime);
				playDto.setEndDateTime(endTime);
				//
				
				// Create ImageView
		        ImageView imageView = new ImageView();
		        imageView.setFitHeight(166.0);
		        imageView.setFitWidth(129.0);
		        imageView.setPreserveRatio(true);
		        Image image = new Image(url);
		        imageView.setImage(image);
		        // Create TextFields
		        TextField textField1 = new TextField(String.format("[%d] %s %d분", age, title, playTime));
		        textField1.setPrefHeight(31.0);
		        textField1.setPrefWidth(478.0);
		        
		        TextField textField2 = new TextField(String.format("%s %s (%d/%d)석", kind, section, seat - seatList.size(), seat));
		        textField2.setPrefHeight(31.0);
		        textField2.setPrefWidth(478.0);
		        
		        TextField textField3 = new TextField(String.format("%s ~ %s", startTime, endTime));
		        textField3.setPrefHeight(31.0);
		        textField3.setPrefWidth(478.0);
		        
		        // Create VBox and add TextFields
		        VBox vbox = new VBox();
		        vbox.setPrefHeight(139.0);
		        vbox.setPrefWidth(478.0);
		        vbox.getChildren().addAll(textField1, textField2, textField3);
		        
		        // Create HBox and add ImageView and VBox
		        HBox hbox = new HBox();
		        hbox.setPrefHeight(167.0);
		        hbox.setPrefWidth(651.0);
		        hbox.getChildren().addAll(imageView, vbox);
		        hbox.setOnMouseClicked((e) ->{
		        	this.playInfoId = playInfoId;
		        	this.url = url;
					this.title = title;
					this.time = startTime;
					this.selected = playDto;
		        	System.out.println("현재 선택환 playInfoId: " + this.playInfoId);
		        });
		        screeningInfoVBox.getChildren().add(hbox);
			}
			scrollPane.setContent(screeningInfoVBox);
		}
	}
	
	@FXML
	public void handleNextPage()  throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SeatAndNumber.fxml"));
        Parent nextScreen = loader.load();
        SeatAndNumberController seatAndNumberController = loader.getController();
        Image image = new Image(selected.getUrl());
        seatAndNumberController.getImageView().setImage(image);
 
        seatAndNumberController.getTitle().setText(selected.getTitle());
        seatAndNumberController.getTime().setText(selected.getTime().toString());
        seatAndNumberController.setPlayInfoDto(selected);
        // Get the Stage from the current scene and set the new scene
        Stage stage = (Stage) nextPage.getScene().getWindow();
        stage.setScene(new Scene(nextScreen));
	}

}