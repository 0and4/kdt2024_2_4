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

public class seatController implements Initializable {
	@FXML StackPane seatPane;
	@FXML Button home; // 홈버튼
	@FXML Button buyBtn; //결제하기 버튼

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		home.setOnAction(event->switchHome(event));// 홈화면으로 이동
		buyBtn.setOnAction(event->switchPayment(event));//결제화면으로 이동
	}
	
	//메인화면으로 이동
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
	
	//결제화면으로 이동
	private void switchPayment(ActionEvent event) {
		try {
			Parent paymentstage = (Parent)FXMLLoader.load(getClass().getResource("/fxml/buy.fxml"));
			StackPane root = (StackPane) seatPane.getScene().getRoot();
			root.getChildren().add(paymentstage);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
