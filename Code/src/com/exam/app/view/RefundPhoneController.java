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

public class RefundPhoneController {

    @FXML
    private TextField phoneField; // 휴대폰 번호 입력 필드

    private boolean isPlaceholder = true; // 예제 텍스트 상태 확인 변수

    // 키패드 버튼 클릭 시 호출되는 메서드
    @FXML
    private void handleNumberClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();

        // 예제 텍스트가 남아있다면 초기화
        if (isPlaceholder) {
            phoneField.setText("");
            isPlaceholder = false;
        }

        // "지우기" 버튼 처리
        if (buttonText.equals("지우기")) {
            phoneField.clear(); // 입력 필드 전체 초기화
        }
        // "X" 버튼 처리
        else if (buttonText.equals("X")) {
            String currentText = phoneField.getText();
            if (!currentText.isEmpty()) {
                phoneField.setText(currentText.substring(0, currentText.length() - 1)); // 마지막 글자 삭제
            }
        }
        // 숫자 버튼 처리
        else {
            phoneField.appendText(buttonText); // 클릭한 버튼의 숫자 입력
        }
    }

    // 입력 필드 클릭 시 예제 텍스트를 제거하는 메서드
    @FXML
    private void clearPlaceholderPhone(MouseEvent event) {
        if (isPlaceholder) {
            phoneField.setText("");
            isPlaceholder = false;
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

    // 예매번호로 조회 버튼 클릭 시 RefundNumber.fxml로 이동
    @FXML
    private void handleReservationNumberSearchAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/exam/app/view/RefundNumber.fxml"));
            Parent numberSearchView = loader.load();

            Stage stage = (Stage) phoneField.getScene().getWindow();
            Scene scene = new Scene(numberSearchView);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
