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

public class ReservationPhoneController {

    @FXML
    private TextField phoneField; // 휴대폰 번호 입력 필드

    private boolean phoneFieldCleared = false; // 필드가 한 번 클릭되어 지워졌는지 여부

    // 키패드에서 숫자 버튼 클릭 시 호출되는 메서드
    @FXML
    private void handleNumberClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String number = clickedButton.getText();

        // 첫 입력 시 예시 텍스트 제거
        if (!phoneFieldCleared) {
            phoneField.clear();
            phoneFieldCleared = true;
        }

        // "지우기" 버튼 확인
        if ("지우기".equals(number)) {
            phoneField.clear(); // 전체 텍스트 삭제
        } else if ("X".equals(number)) {
            // 마지막 글자 삭제
            String currentText = phoneField.getText();
            if (!currentText.isEmpty()) {
                phoneField.setText(currentText.substring(0, currentText.length() - 1));
            }
        } else {
            // 숫자 버튼 클릭 시 해당 숫자 추가
            phoneField.appendText(number);
        }
    }

    // 전화번호 입력창 클릭 시 플레이스홀더 제거
    @FXML
    private void clearPlaceholderPhone(MouseEvent event) {
        if (!phoneFieldCleared) {
            phoneField.clear(); // 플레이스홀더 제거
            phoneFieldCleared = true; // 이후로는 지우지 않음
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

    // "예매 번호로 조회" 버튼 클릭 시 ReservationNumber.fxml로 이동
    @FXML
    private void handleTicketSearchAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/exam/app/view/ReservationNumber.fxml"));
            Parent reservationNumberView = loader.load();

            Stage stage = (Stage) phoneField.getScene().getWindow();
            Scene scene = new Scene(reservationNumberView);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // "티켓 조회" 버튼 클릭 시 ReservationList.fxml로 이동
    @FXML
    private void handleReservationListAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/exam/app/view/ReservationList.fxml"));
            Parent reservationListView = loader.load();

            Stage stage = (Stage) phoneField.getScene().getWindow();
            Scene scene = new Scene(reservationListView);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
