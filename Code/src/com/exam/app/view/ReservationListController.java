package com.exam.app.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class ReservationListController {

    @FXML
    private Button printButton;

    @FXML
    private Rectangle reservationBorder;

    @FXML
    private AnchorPane reservationItem;

    // 홈 버튼을 클릭했을 때 실행되는 메서드
    @FXML
    private void handleHomeButtonAction(ActionEvent event) {
        try {
            // FXML 파일을 로드합니다.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/exam/app/view/kiosk.fxml"));
            Parent kioskView = loader.load();

            // 현재 Stage를 가져와서 새로운 Scene으로 교체합니다.
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(kioskView);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace(); // 오류 발생 시 스택 트레이스 출력
        }
    }

    // 예매 항목 클릭 시 출력 버튼 활성화 및 테두리 변경
    @FXML
    private void handleReservationItemClick(MouseEvent event) {
        // 출력 버튼 활성화
        printButton.setDisable(false);

        // 선택된 항목의 테두리를 빨간색으로 변경
        reservationBorder.setStroke(javafx.scene.paint.Color.RED);
    }
}
