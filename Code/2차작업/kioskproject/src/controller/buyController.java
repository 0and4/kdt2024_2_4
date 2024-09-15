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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class buyController implements Initializable {
	@FXML StackPane buyPane;
	@FXML Button home;
	@FXML Button usePoint;
	@FXML Button savePoint;
	@FXML Button purChase;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		home.setOnAction(event->switchHome(event));
		usePoint.setOnAction(event->usePointPopup(event));
		savePoint.setOnAction(event->savePointPopup(event));
		purChase.setOnAction(event->rescheck(event));
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
		
	// 포인트 사용 팝업
	public void usePointPopup(ActionEvent event) {
		//현재 스테이지 정보 가져오기
		Stage usePointPopupStage = (Stage) usePoint.getScene().getWindow();
		//팝업을 띄워줄 새로운 스테이지 생성
		Stage pop = new Stage(StageStyle.DECORATED);
		pop.initModality(Modality.WINDOW_MODAL);
		pop.initOwner(usePointPopupStage);
		//팝업창 불러오기
		try {
			Parent root = (Parent)FXMLLoader.load(getClass().getResource("/fxml/PointUsingPopup.fxml"));
			Scene scene = new Scene(root);
			pop.setScene(scene);
			pop.setTitle("포인트 사용");
			pop.setResizable(false);// 창 조절 차단
			// 팝업 보여주기
			pop.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 포인트 적립 팝업
	public void savePointPopup(ActionEvent event) {
		//현재 스테이지 정보 가져오기
		Stage savePointPopupStage = (Stage) usePoint.getScene().getWindow();
		//팝업을 띄워줄 새로운 스테이지 생성
		Stage pop = new Stage(StageStyle.DECORATED);
		pop.initModality(Modality.WINDOW_MODAL);
		pop.initOwner(savePointPopupStage);
		//팝업창 불러오기
		try {
			Parent root = (Parent)FXMLLoader.load(getClass().getResource("/fxml/PointSavingPopup.fxml"));
			Scene scene = new Scene(root);
			pop.setScene(scene);
			pop.setTitle("포인트 적립");
			pop.setResizable(false);// 창 조절 차단
			// 팝업 보여주기
			pop.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//예약확인 창으로 이동
	private void rescheck(ActionEvent event) {
		try {
			Parent rescheckstage = (Parent)FXMLLoader.load(getClass().getResource("/fxml/reservationCheck.fxml"));
			StackPane root = (StackPane) buyPane.getScene().getRoot();
			root.getChildren().add(rescheckstage);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
