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

public class ReservationNumberController {

    @FXML
    private TextField phoneField; // FXML 파일에 있는 TextField에 연결

    private boolean isPlaceholder = true; // 플레이스홀더 상태

    // 번호 버튼 클릭 시 호출되는 메서드
    @FXML
    private void handleNumberClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();

        if (isPlaceholder) {
            phoneField.setText(""); // 플레이스홀더 텍스트 삭제
            isPlaceholder = false;
        }

        // "지우기" 버튼 처리
        if (buttonText.equals("X")) {
            String currentText = phoneField.getText();
            if (!currentText.isEmpty()) {
                phoneField.setText(currentText.substring(0, currentText.length() - 1)); // 마지막 글자 삭제
            }
        } 
        // "X" 버튼 처리
        else if (buttonText.equals("지우기")) {
            phoneField.clear(); // 입력 필드 초기화
        } 
        // 숫자 버튼 처리
        else {
            phoneField.appendText(buttonText); // 클릭한 버튼의 텍스트 추가
        }
    }

    // 입력 필드 클릭 시 placeholder를 제거하는 메서드
    @FXML
    private void clearPlaceholderPhone(MouseEvent event) {
        if (isPlaceholder) {
            phoneField.setText(""); // 입력 필드를 비움
            isPlaceholder = false;
        }
    }

 // 홈 버튼 처리
    @FXML
    private void handleHomeButtonAction(ActionEvent event) {
        try {
            // FXML 파일 로드 (kiosk.fxml로 이동)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/exam/app/view/kiosk.fxml"));
            Parent homeView = loader.load();

            // 현재 스테이지 가져오기
            Stage stage = (Stage) phoneField.getScene().getWindow();

            // 새로운 씬으로 전환
            Scene scene = new Scene(homeView);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace(); // 에러 발생 시 콘솔에 출력
        }
    }

    // 티켓 조회 버튼 클릭 시 호출되는 메서드
    @FXML
    private void handleTicketSearchAction(ActionEvent event) {
        try {
            // FXML 파일 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/exam/app/view/ReservationList.fxml"));
            Parent reservationListRoot = loader.load();

            // 현재 스테이지 가져오기
            Stage stage = (Stage) phoneField.getScene().getWindow();

            // 새로운 씬으로 전환
            Scene scene = new Scene(reservationListRoot);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace(); // 에러 발생 시 콘솔에 출력
            // 추가적으로 오류 알림 창 등을 띄울 수 있음
        }
    }
    
    // "휴대폰 번호로 조회" 버튼 클릭 시 호출되는 메서드
    @FXML
    private void handlePhoneSearchAction(ActionEvent event) {
        try {
            // ReservationPhoneController가 연결된 FXML 파일 로드
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/exam/app/view/ReservationPhone.fxml"));
            Parent phoneSearchView = loader.load();

            // 현재 스테이지 가져오기
            Stage stage = (Stage) phoneField.getScene().getWindow();

            // 새로운 씬으로 전환
            Scene scene = new Scene(phoneSearchView);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace(); // 에러 발생 시 콘솔에 출력
        }
    }
}
