package com.exam.app.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RefundNumberController {

    @FXML
    private TextField phoneField; // 입력 필드

    private boolean isPlaceholder = true; // 플레이스홀더 상태 확인 변수

    // 숫자 버튼 클릭 시 호출되는 메서드
    @FXML
    private void handleNumberClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();

        // 플레이스홀더가 남아있으면 초기화
        if (isPlaceholder) {
            phoneField.setText("");
            isPlaceholder = false;
        }

        // "지우기"와 "X" 버튼 처리
        if (buttonText.equals("지우기")) {
            phoneField.clear(); // 입력 필드 전체 초기화
        } else if (buttonText.equals("X")) {
            String currentText = phoneField.getText();
            if (!currentText.isEmpty()) {
                phoneField.setText(currentText.substring(0, currentText.length() - 1)); // 마지막 글자 삭제
            }
        } else {
            phoneField.appendText(buttonText); // 클릭한 버튼의 텍스트 추가
        }
    }

    // 입력 필드 클릭 시 플레이스홀더 제거
    @FXML
    private void clearPlaceholderPhone(MouseEvent event) {
        if (isPlaceholder) {
            phoneField.setText("");
            isPlaceholder = false;
        }
    }

    // 휴대폰 번호로 조회 버튼 클릭 시 RefundPhone.fxml로 이동
    @FXML
    private void handlePhoneSearchAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/exam/app/view/RefundPhone.fxml"));
            Parent phoneSearchView = loader.load();

            Stage stage = (Stage) phoneField.getScene().getWindow();
            Scene scene = new Scene(phoneSearchView);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 조회 버튼 클릭 시 RefundList.fxml로 이동
    @FXML
    private void handleTicketSearchAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/exam/app/view/RefundList.fxml"));
            Parent refundListView = loader.load();

            Stage stage = (Stage) phoneField.getScene().getWindow();
            Scene scene = new Scene(refundListView);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 홈 버튼 클릭 시 kiosk.fxml로 이동
    @FXML
    private void handleHomeButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/exam/app/view/kiosk.fxml"));
            Parent homeView = loader.load();

            Stage stage = (Stage) phoneField.getScene().getWindow();
            Scene scene = new Scene(homeView);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
