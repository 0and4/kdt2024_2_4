package com.exam.app.view;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.stage.Stage;

public class NoReservationPopupController {

    // "재입력" 버튼 클릭 시 팝업을 닫음
    @FXML
    private void handleRetryButtonAction(ActionEvent event) {
        // 현재 팝업 창을 닫음
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    // "처음으로" 버튼 클릭 시 홈 화면으로 이동
    @FXML
    private void handleHomeButtonAction(ActionEvent event) {
        try {
            // 홈 화면의 FXML 파일을 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/exam/app/view/kiosk.fxml"));
            Parent homeView = loader.load();

            // 현재 스테이지 가져오기
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(homeView);

            // 새로운 씬으로 전환
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace(); // 에러 발생 시 스택 트레이스 출력
        }
    }
}
