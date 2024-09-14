package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class selectMemberController implements Initializable {

	@FXML private StackPane selectMemberPane;
	@FXML private Button home; // 메인으로 이동버튼
	@FXML private Button nextPane;//좌석선택으로 이동
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		home.setOnAction(event->switchHome(event)); // 홈화면으로 이동
		nextPane.setOnAction(event->switchSeat(event)); // 좌석 선택화면으로 이동
	}
	
	//홈화면으로 이동
	private void switchHome(ActionEvent event) {
		try {
            // FXML 파일을 로드하여 메뉴 화면으로 전환
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/kioskMain.fxml"));
            Parent homeRoot = loader.load();

            // 현재 창의 Stage를 가져옴
            Stage stage = (Stage) home.getScene().getWindow();
            Scene scene = new Scene(homeRoot);

            // 메뉴 화면으로 전환
            stage.setScene(scene);
            stage.show();
            

        } catch (IOException e) {
            e.printStackTrace();
            // 예외 발생 시 사용자에게 경고창 표시
        }
	}
	
	//다음 좌석 선택으로 이동
	private void switchSeat(ActionEvent event) {
		try {
			Parent seatstage = (Parent)FXMLLoader.load(getClass().getResource("/fxml/seat.fxml"));
			StackPane root = (StackPane) selectMemberPane.getScene().getRoot();
			root.getChildren().add(seatstage);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
