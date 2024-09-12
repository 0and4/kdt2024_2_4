package com.exam.app.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.exam.app.AppData;

public class SettingController {

    @FXML
    private VBox movieListVBox;  // 영화 목록이 들어갈 VBox
    @FXML
    private Button registerButton;
    @FXML
    private Button backToMenuButton;

    private HBox selectedMovie;  // 선택된 영화 항목을 저장할 변수
    private String selectedMovieTitle;  // 선택된 영화 제목
    private HashMap<String, ArrayList<String>> movieScreenings;  // 영화별 상영 정보 저장소
    
    @FXML
    public void initialize() {
        // 영화 데이터 초기화 및 영화 목록 동적 생성
        movieScreenings = AppData.getMovieScreenings();
        populateMovieList();
    }

    // 선택된 영화의 상영 정보를 업데이트하는 메서드
    public void ScreeningInfo(String movieTitle, HashMap<String, ArrayList<String>> movieScreenings) {
        if (movieTitle != null && movieScreenings != null) {
            this.movieScreenings = movieScreenings;

            // UI 업데이트 (예: 영화 목록을 새로 고침)
            populateMovieList();
        }
    }

    // 영화 목록을 동적으로 추가하는 메서드
    private void populateMovieList() {
        if (movieScreenings == null || movieScreenings.isEmpty()) {
            System.out.println("영화 목록이 없습니다.");
            return;
        }

        movieListVBox.getChildren().clear();  // 기존 영화 목록 클리어

        for (String movieTitle : movieScreenings.keySet()) {
            HBox movieItem = new HBox(10);  // 영화 항목은 HBox로 구성

            // 이미지뷰 생성 및 기본 이미지 설정 (경로는 실제 이미지 경로로 변경)
            ImageView movieImageView = new ImageView();
            movieImageView.setFitHeight(60);
            movieImageView.setFitWidth(50);

            // 영화 제목 및 정보 표시용 VBox 생성
            VBox movieDetailsVBox = new VBox(5);
            Label titleLabel = new Label(movieTitle);  // 영화 제목 레이블
            titleLabel.setId("movieTitleLabel");  // ID 추가
            
            // 상영 정보 레이블
            ArrayList<String> screeningInfo = movieScreenings.get(movieTitle);
            String screeningType = "상영 종류: N/A";
            String rating = "관람가: N/A";
            String duration = "상영 시간: N/A";
            
            if (screeningInfo != null && !screeningInfo.isEmpty()) {
                String[] infoParts = screeningInfo.get(0).split(", ");
                if (infoParts.length >= 3) {
                    screeningType = "상영 종류: " + infoParts[0];
                    rating = "관람가: " + infoParts[1];
                    duration = "상영 시간: " + infoParts[2];
                }
            }

            Label durationLabel = new Label(duration);
            Label ratingLabel = new Label(rating);
            Label screeningTypeLabel = new Label(screeningType);

            movieDetailsVBox.getChildren().addAll(titleLabel, screeningTypeLabel, durationLabel, ratingLabel);
            movieItem.getChildren().addAll(movieImageView, movieDetailsVBox);
            
            // 영화 항목에 클릭 이벤트 추가
            movieItem.setOnMouseClicked(event -> selectMovie(movieItem));

            movieListVBox.getChildren().add(movieItem);  // VBox에 추가
        }
    }

    // 영화 선택 시 파란색 테두리 추가 및 영화 제목 저장
    @FXML
    public void selectMovie(HBox movieItem) {
        if (selectedMovie != null) {
            selectedMovie.setStyle("");  // 이전 선택된 항목의 테두리 제거
        }

        selectedMovie = movieItem;
        selectedMovie.setStyle("-fx-border-color: blue; -fx-border-width: 2;");  // 파란색 테두리 추가

        Label movieTitleLabel = (Label) movieItem.lookup("#movieTitleLabel");
        selectedMovieTitle = movieTitleLabel.getText();  // 선택한 영화 제목 저장
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    // '상영 정보 추가/수정' 버튼 클릭 시 호출되는 메서드
    @FXML
    private void switchToEdit() {
        if (selectedMovieTitle == null) {
            // 영화가 선택되지 않았을 경우 처리
            System.out.println("영화를 선택하세요.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieSettingEdit.fxml"));
            root = loader.load();

            MovieSettingEditController editController = loader.getController();
            editController.initializeData(selectedMovieTitle, movieScreenings);

            switchScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // '상영 정보 확인' 버튼 클릭 시 호출되는 메서드
    @FXML
    public void switchToCheck(ActionEvent event) throws IOException {
        if (selectedMovieTitle == null) {
            System.out.println("영화를 선택하세요.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieSettingCheck.fxml"));
            root = loader.load();

            // MovieSettingCheckController 가져오기
            MovieSettingCheckController controller = loader.getController();
            // 현재 선택한 영화의 상영 정보를 전달
            ArrayList<String> screenings = movieScreenings.get(selectedMovieTitle);
            controller.setMovieScreenings(selectedMovieTitle, screenings);

            switchScene(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 씬 전환을 위한 메서드 (중복 제거용)
    private void switchScene() {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // ActionEvent가 있는 씬 전환 메서드
    private void switchScene(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // 메뉴로 돌아가는 버튼 클릭 이벤트 처리
    @FXML
    private void handleBackToMenuAction(ActionEvent event) {
        try {
            // FXML 파일을 로드하여 메뉴 화면으로 전환
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/exam/app/view/AdminMenuPopup.fxml"));
            Parent menuRoot = loader.load();

            // 현재 창의 Stage를 가져옴
            Stage stage = (Stage) backToMenuButton.getScene().getWindow();
            Scene scene = new Scene(menuRoot);

            // 메뉴 화면으로 전환
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
