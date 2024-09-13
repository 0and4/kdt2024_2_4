package com.exam.app.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import com.exam.app.AppData;

public class MovieSettingEditController {

    @FXML
    private HBox screeningInfoHBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> theaterComboBox;
    @FXML
    private TextField timeTextField;
    @FXML
    private Button saveButton;
    @FXML
    private Button backButton;
    @FXML
    private Label movieTitleLabel1, movieTitleLabel2;
    @FXML
    private Label screeningTypeLabel;
    @FXML
    private Label ageRatingLabel;
    @FXML
    private Label durationLabel;
    @FXML
    private VBox screeningInfoVBox;  // 상영정보를 추가할 VBox
    
    private String selectedMovieTitle;

    @FXML
    public void initialize() {
        // 상영관 목록 추가
        theaterComboBox.getItems().addAll("1관", "2관", "3관");

        // 날짜 선택 시 오늘 날짜부터 일주일 이후까지만 선택할 수 있도록 설정
        LocalDate today = LocalDate.now();
        LocalDate oneWeekLater = today.plusWeeks(1);

        // 날짜 선택 시 오늘 이전의 날짜는 선택할 수 없도록 설정
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(today) || date.isAfter(oneWeekLater));
                if (date.isBefore(today)) {
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });

        // 시간 입력 시 자동으로 'HH:mm' 형식으로 변환하는 리스너 추가
        timeTextField.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() == 4 && newText.matches("\\d+")) {
                String formattedTime = newText.substring(0, 2) + ":" + newText.substring(2);
                timeTextField.setText(formattedTime);
            }
        });

        // 저장 버튼 클릭 시 상영 정보를 저장하고 화면을 업데이트하도록 설정
        saveButton.setOnAction(event -> saveScreeningInfo());
        // 뒤로 가기 버튼 클릭 시 이전 화면으로 돌아가도록 설정
        backButton.setOnAction(event -> switchToSettingController());
    }

    // MovieSettingController에서 영화 정보를 전달받아 초기화하는 메서드
    public void initializeData(String movieTitle) {
        this.selectedMovieTitle = movieTitle;

        // 영화 제목 표시
        movieTitleLabel1.setText(selectedMovieTitle);
        movieTitleLabel2.setText(selectedMovieTitle);

        // 상영 정보 초기화
        String screeningType = AppData.getMovieType(selectedMovieTitle);
        String ageRating = AppData.getMovieRating(selectedMovieTitle);
        String duration = AppData.getMovieRuntime(selectedMovieTitle);

        // 상영 정보 레이블에 설정
        screeningTypeLabel.setText("(" + screeningType + ")");
        ageRatingLabel.setText(ageRating);
        durationLabel.setText(duration);
    }

    @FXML
    private void saveScreeningInfo() {
        String date = datePicker.getValue() != null ? datePicker.getValue().toString() : "정보 없음";
        String theater = theaterComboBox.getValue() != null ? theaterComboBox.getValue() : "정보 없음";
        String time = timeTextField.getText().isEmpty() ? "정보 없음" : timeTextField.getText();
        
        // 새로운 상영 정보를 담은 VBox 생성
        VBox vbox = new VBox(5);
        vbox.setPadding(new Insets(5, 10, 5, 10));
        
        Label dateLabel = new Label("날짜: " + date);
        Label theaterLabel = new Label("상영관: " + theater);
        Label timeLabel = new Label("시간: " + time);
        
        vbox.getChildren().addAll(dateLabel, theaterLabel, timeLabel);
        
        // VBox를 HBox에 추가
        screeningInfoHBox.getChildren().add(vbox);
        
        // 입력 필드 초기화
        datePicker.setValue(null);
        theaterComboBox.setValue(null);
        timeTextField.clear();
    }

    // SettingController로 상영 정보를 전달하며 돌아가는 메서드
    private void switchToSettingController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieSetting.fxml"));
            AnchorPane root = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
