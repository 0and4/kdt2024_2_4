package com.exam.app.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class RefundListController {

    @FXML
    private VBox reservationListContainer; // 예매 내역 목록을 담는 컨테이너

    @FXML
    private Button printButton; // 출력 버튼

    @FXML
    private AnchorPane rootPane; // 전체 화면의 루트 패널

    private AnchorPane selectedReservation = null; // 선택된 예매 항목을 저장

    // 홈 버튼 클릭 시 kiosk.fxml로 이동
    @FXML
    private void handleHomeButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/exam/app/view/kiosk.fxml"));
            Parent homeView = loader.load();

            Stage stage = (Stage) rootPane.getScene().getWindow(); // rootPane의 Scene을 얻어서 Stage를 가져옴
            Scene scene = new Scene(homeView);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 예매 내역 클릭 시 테두리 빨간색 변경 및 출력 버튼 활성화
    @FXML
    private void handleReservationClick(MouseEvent event) {
        // 이전에 선택된 예매 항목이 있으면 원래 상태로 돌림
        if (selectedReservation != null) {
            Rectangle rect = (Rectangle) selectedReservation.lookup("Rectangle");
            rect.setStroke(Color.web("#b7b7b7")); // 원래 회색 테두리로 복구
        }

        // 선택한 예매 항목의 테두리를 빨간색으로 변경
        AnchorPane clickedReservation = (AnchorPane) event.getSource();
        Rectangle rect = (Rectangle) clickedReservation.lookup("Rectangle");
        rect.setStroke(Color.RED);

        selectedReservation = clickedReservation; // 선택된 예매 항목 저장
        printButton.setDisable(false); // 버튼 활성화
    }

    
    @FXML
    private void handlePrintButtonAction(ActionEvent event) {
        if (selectedReservation != null) {
            System.out.println("출력 팝업 띄우기");
            // 팝업 추가 구현..
        }
    }
}
