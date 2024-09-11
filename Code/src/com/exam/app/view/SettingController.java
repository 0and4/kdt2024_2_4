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

import com.exam.app.AppData;

public class SettingController {

    @FXML
    private VBox movieListVBox;  // 영화 목록이 들어가는 VBox
    @FXML
    private VBox screeningInfoVBox;  // 상영 정보 출력용 VBox
    private HBox selectedMovie;  // 선택된 영화 항목을 저장할 변수
    private String selectedMovieTitle; // 선택된 영화의 제목을 저장

    @FXML
    private Button backToMenuButton;
    
    @FXML
    public void initialize() {
        if (movieListVBox == null) {
            System.err.println("movieListVBox is not initialized.");
        }

        if (screeningInfoVBox == null) {
            screeningInfoVBox = new VBox();  // 간단한 초기화. FXML이 아닌 코드로 생성.
            System.err.println("screeningInfoVBox was null and has been initialized programmatically.");
        }

        // 초기 영화 목록 추가
        addMovieToList("Movie 1");
        addMovieToList("Movie 2");
        addMovieToList("Movie 3");
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    // '상영 정보 추가/수정' 버튼 클릭 시
    @FXML
    public void switchToEdit(ActionEvent event) throws IOException {
        if (selectedMovieTitle != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieSettingEdit.fxml"));
            root = loader.load();

            MovieSettingEditController controller = loader.getController();
            controller.initializeData(selectedMovieTitle, AppData.getMovieScreenings());

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            System.out.println("영화를 선택해 주세요.");
        }
    }

    // '상영 정보 확인' 버튼 클릭 시
    @FXML
    public void switchToCheck(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieSettingCheck.fxml"));
        root = loader.load();

        // MovieSettingCheckController 가져오기
        MovieSettingCheckController controller = loader.getController();
        // 현재 영화 상영 정보를 전달
        controller.setMovieScreenings();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // 영화 선택 시 파란색 테두리 추가
    @FXML
    public void selectMovie(HBox movieItem) {
        if (selectedMovie != null) {
            selectedMovie.setStyle("");  // 테두리 제거
        }

        selectedMovie = movieItem;
        selectedMovie.setStyle("-fx-border-color: blue; -fx-border-width: 2;");

        Label movieTitleLabel = (Label) movieItem.lookup("#movieTitleLabel");
        selectedMovieTitle = movieTitleLabel.getText();
    }

    // 영화 목록을 동적으로 추가하는 메서드
    @FXML
    public void addMovieToList(String movieTitle) {
        HBox movieItem = new HBox();
        movieItem.setSpacing(10);

        // 이미지뷰 추가
        ImageView moviePoster = new ImageView();
        moviePoster.setFitWidth(100);
        moviePoster.setFitHeight(150);
        // moviePoster.setImage(new Image("file:path_to_image.jpg"));  // 실제 이미지 경로로 대체 필요

        Label movieTitleLabel = new Label(movieTitle);
        movieTitleLabel.setId("movieTitleLabel");

        movieItem.getChildren().addAll(moviePoster, movieTitleLabel);

        movieItem.setOnMouseClicked(event -> selectMovie(movieItem));

        movieListVBox.getChildren().add(movieItem);
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
            // 예외 발생 시 사용자에게 경고창 표시
        }
    }
}
