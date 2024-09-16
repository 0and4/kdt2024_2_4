package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class choiseMovieController implements Initializable {
	@FXML private StackPane choiseMoviePane;
	
	@FXML private Label ToDay; // 오늘 날짜 설정
	@FXML private Button home; // 홈버튼
	@FXML private Button nextPage; // 다음페이지 버튼
	@FXML private Button day1,day2,day3,day4,day5,day6,day7; // 날짜 선택버튼
	
	private DateTimeFormatter formatter,buttonformatter;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
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
		
		//버튼 클릭 시 선택한 버튼 색상 표시
		day1.setOnMouseClicked(event -> handleButtonClick(day1));
	    day2.setOnMouseClicked(event -> handleButtonClick(day2));
	    day3.setOnMouseClicked(event -> handleButtonClick(day3));
	    day4.setOnMouseClicked(event -> handleButtonClick(day4));
	    day5.setOnMouseClicked(event -> handleButtonClick(day5));
	    day6.setOnMouseClicked(event -> handleButtonClick(day6));
	    day7.setOnMouseClicked(event -> handleButtonClick(day7));
		
		home.setOnAction(event->switchHome(event)); // 홈화면으로 이동
		nextPage.setOnAction(event->switchNextPage(event));// 다음페이지로 이동
	}
	
	//버튼 클릭 시 선택한 버튼 색상 변경 및 제거
	private void handleButtonClick(Button clickedButton) {
		day1.getStyleClass().remove("selected");
	    day2.getStyleClass().remove("selected");
	    day3.getStyleClass().remove("selected");
	    day4.getStyleClass().remove("selected");
	    day5.getStyleClass().remove("selected");
	    day6.getStyleClass().remove("selected");
	    day7.getStyleClass().remove("selected");
	    
	    clickedButton.getStyleClass().add("selected");
	}
	
	@FXML
	public void handleDateSelection(ActionEvent event) {
		Button selectedButton = (Button) event.getSource();
		String selectedDate = selectedButton.getText();
		System.out.println("선택날짜: "+selectedDate);
	}
	
	//홈으로 이동
	private void switchHome(ActionEvent event) {
		try {
            // FXML 파일을 로드하여 메뉴 화면으로 전환
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/kioskMain.fxml"));
            Parent homeRoot = loader.load();

            // 현재 창의 Stage를 가져옴
            Stage stage = (Stage) home.getScene().getWindow();
            Scene scene = new Scene(homeRoot); // 홈화면으로 전환

            // 메뉴 화면으로 전환
            stage.setScene(scene);
            stage.show(); //화면에 출력
            

        } catch (IOException e) {
            e.printStackTrace();
            // 예외 발생 시 사용자에게 경고창 표시
        }
	}
	
	//인원 선택 창으로 이동
	public void switchNextPage(ActionEvent event) {
		try {
			Parent selectMemberstage = (Parent)FXMLLoader.load(getClass().getResource("/fxml/selectMember.fxml"));
			StackPane root = (StackPane) choiseMoviePane.getScene().getRoot();
			root.getChildren().add(selectMemberstage);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
