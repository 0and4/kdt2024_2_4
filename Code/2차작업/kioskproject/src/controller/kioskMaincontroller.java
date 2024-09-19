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
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import view.AppMain;

public class kioskMaincontroller implements Initializable {
	@FXML private MenuItem admin;
	@FXML private MenuItem unconnect;
	
	@FXML private StackPane mainpane;
	@FXML private Button buyTicket;
	
	//예약 확인 버튼..
	@FXML private Button checkRegisterBtn;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		admin.setOnAction(event->connectAdmin(event));
		unconnect.setOnAction(event->UNConnect(event));
		buyTicket.setOnAction(event->choise(event));
		checkRegisterBtn.setOnAction(event -> checkRegisger(event));
	}
	
	private void checkRegisger(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ReservationNumber.fxml"));
			Parent nextScreen = loader.load();
			Stage stage = (Stage) mainpane.getScene().getWindow();
			stage.setScene(new Scene(nextScreen));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void connectAdmin(ActionEvent event) {
		System.out.println("관리자 접속 클릭");
		try {
			//main을 stackpane으로 생성하였기 때문에 아래 주석을 해제하면 키오스크 화면에 관리자 화면이 바로 뜨는 방식입니다.
			
			//Parent adminstage = (Parent)FXMLLoader.load(getClass().getResource("/fxml/AdminLoginPopup.fxml"));
			/*StackPane root = (StackPane) mainpane.getScene().getRoot();
			root.getChildren().add(adminstage);*/
			
			Parent root = (Parent)FXMLLoader.load(getClass().getResource("/fxml/AdminLoginPopup.fxml"));
			Stage adminStage = new Stage();
			adminStage.setScene(new Scene(root));
            adminStage.setTitle("키오스크-관리자");
            adminStage.show();
            
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void UNConnect(ActionEvent e) {
		AppMain.kioskcontroller.closeServer();
	}
	
	public void choise(ActionEvent event) {
		try {
			Parent moviestage = (Parent)FXMLLoader.load(getClass().getResource("/fxml/choiseMovie.fxml"));
			StackPane root = (StackPane) mainpane.getScene().getRoot();
			root.getChildren().add(moviestage);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
