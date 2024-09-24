package controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import dto.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EasyTime implements Initializable{
    @FXML private StackPane EasyTimePane;
    @FXML private Button home; // 홈버튼
    @FXML private Button nextPage; // 다음으로 버튼 
    @FXML private Button backBtn; // 이전으로 버튼
    @FXML private VBox movieList; // 영화 목록 패널
    @FXML private Label MovieInfo; // 영화 정보

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        home.setOnAction(event->BackHome(event));// home 버튼
        backBtn.setOnAction(event->BackPage(event)); //이전으로 버튼
        nextPage.setOnAction(event->NextPage(event)); //다음으로 버튼
    }
    private void NextPage(ActionEvent event) {
        try {
            // FXML 파일을 로드하여 메뉴 화면으로 전환
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EasySelectMember.fxml"));
            Parent homeRoot = loader.load();
            // 현재 창의 Stage를 가져옴
            Stage stage = (Stage) home.getScene().getWindow();
            Scene scene = new Scene(homeRoot); // 홈화면으로 전환

            // 메뉴 화면으로 전환
            stage.setScene(scene);
            stage.show(); //화면에 출력
            

        } catch (Exception e) {
            e.printStackTrace();
            // 예외 발생 시 사용자에게 경고창 표시
        }
    }
    private void BackPage(ActionEvent event) {
        try {
            // FXML 파일을 로드하여 메뉴 화면으로 전환
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EasyChoiceMovie.fxml"));
            Parent homeRoot = loader.load();
            // 현재 창의 Stage를 가져옴
            Stage stage = (Stage) home.getScene().getWindow();
            Scene scene = new Scene(homeRoot); // 홈화면으로 전환

            // 메뉴 화면으로 전환
            stage.setScene(scene);
            stage.show(); //화면에 출력
            

        } catch (Exception e) {
            e.printStackTrace();
            // 예외 발생 시 사용자에게 경고창 표시
        }
    }
    private void BackHome(ActionEvent event) {
        try {
            // FXML 파일을 로드하여 메뉴 화면으로 전환
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EasyMonitor.fxml"));
            Parent homeRoot = loader.load();
            // 현재 창의 Stage를 가져옴
            Stage stage = (Stage) home.getScene().getWindow();
            Scene scene = new Scene(homeRoot); // 홈화면으로 전환

            // 메뉴 화면으로 전환
            stage.setScene(scene);
            stage.show(); //화면에 출력
            

        } catch (Exception e) {
            e.printStackTrace();
            // 예외 발생 시 사용자에게 경고창 표시
        }
    }
    public void loadData(Map<String, Movie> selectedData) {
        // selectedData에서 첫 번째 항목을 가져옴
        for (String key : selectedData.keySet()) {
            // 키 값을 '|'를 기준으로 분리
            String[] parts = key.split("\\|");
            
            // 첫 세 개의 값 추출: 영화 제목, 시간, 포맷
            if (parts.length >= 3) {
                String title = parts[0];  // 예: "test"
                String time = parts[1];   // 예: "12"
                String format = parts[2]; // 예: "2D"

                // 원하는 형식으로 변환: [12] test (2D)
                String formattedString = String.format("[%s] %s (%s)", time, title, format);

                // 출력 또는 setText로 설정
                System.out.println(formattedString);
                MovieInfo.setText(formattedString);
            }
        }
        
    }
}
