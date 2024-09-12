package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class choiseMovieController implements Initializable {
	@FXML private Label ToDay; // 오늘 날짜 설정
	@FXML private Button home; // 홈버튼
	@FXML private Button nextPage;
	@FXML private Button day1,day2,day3,day4,day5,day6,day7; // 날짜 선택버튼
	
	private DateTimeFormatter formatter,buttonformatter;

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
	}
	
	@FXML
	public void handleDateSelection(ActionEvent event) {
		Button selectedButton = (Button) event.getSource();
		String selectedDate = selectedButton.getText();
		System.out.println("선택날짜: "+selectedDate);
	}

}
