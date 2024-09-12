package controller;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.AppData;

public class SettingController {
	@FXML ScrollPane scrollPane;

    @FXML
    private VBox movieListVBox;  // 영화 목록이 들어가는 VBox
    @FXML
    private VBox screeningInfoVBox;  // 상영 정보 출력용 VBox
    private HBox selectedMovie;  // 선택된 영화 항목을 저장할 변수
    private String selectedMovieTitle; // 선택된 영화의 제목을 저장
    private String selectedMovieRuntime; //선택된 영화의 상영시간을 저장할 변수
    private String selectedMovieRating;// 선택된 영화의 상영등급을 저장할 변수
    private String selectedMovietype;//상영관 타입을 저장할 변수
    private String selectedMoviePoster;//선택된 영화의 포스터를 저장할 변수
    
    @FXML
    private Button backToMenuButton; // 이전 버튼
    @FXML
    private Button backTosetting;
    
    @FXML private Button registerButton;
    @FXML private Button registerButton1;
    
    String url = "jdbc:mysql://localhost:3306/kiosk";
	private Connection con;
    
    @FXML
    public void initialize() {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("데이터베이스 연결중...");
			con = DriverManager.getConnection(url,"root","root1234");
			System.out.println("연결성공");
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
        if (movieListVBox == null) {
            System.err.println("movieListVBox is not initialized.");
        }else {
        	addMovieToList();
        }

        if (screeningInfoVBox == null) {
            screeningInfoVBox = new VBox();  // 간단한 초기화. FXML이 아닌 코드로 생성.
            System.err.println("screeningInfoVBox was null and has been initialized programmatically.");
        }

        // 초기 영화 목록 추가
        /*addMovieToList("Movie 1");
        addMovieToList("Movie 2");
        addMovieToList("Movie 3");*/
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    // '상영 정보 추가/수정' 버튼 클릭 시
    @FXML
    public void switchToEdit(ActionEvent event) throws IOException {
        if (selectedMovieTitle != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MovieSettingEdit.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MovieSettingCheck.fxml"));
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
    public void selectMovie(HBox movieItem, String movieTitleLabel, String rating, String runtime, String movietype, String poster) {
        if (selectedMovie != null) {
            selectedMovie.setStyle("");  // 테두리 제거
        }

        selectedMovie = movieItem;
        selectedMovieTitle = movieTitleLabel;  // 선택된 영화 제목을 저장
        selectedMovieRuntime = runtime; // 선택된 러닝타임을 저장
        selectedMovieRating = rating; // 선택된 상영등급을 저장
        selectedMovietype = movietype;// 상영관 타입
        selectedMoviePoster = poster; // 선택된 영화의 포스터를 저장
        selectedMovie.setStyle("-fx-border-color: blue; -fx-border-width: 2;");

        /*Label movieTitleLabel = (Label) movieItem.lookup("#movieTitleLabel");
        selectedMovieTitle = movieTitleLabel.getText();*/
    }

    // 영화 목록을 동적으로 추가하는 메서드
    @FXML
    public void addMovieToList() {
    	try {
    		String sql = "SELECT title,runtime,rating,movietype,poster FROM showmovie";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String title = rs.getString("title");
				String runtime = rs.getString("runtime");
				String rating = rs.getString("rating");
				String movietype = rs.getString("movietype");
				String poster = rs.getString("poster");
				
				HBox movieItem = new HBox();
	            movieItem.setSpacing(10);

	            // 이미지뷰 추가
	            File file = new File(poster);
				Image image = new Image(file.toURI().toString());
	            ImageView moviePoster = new ImageView(image);
	            moviePoster.setFitWidth(100);
	            moviePoster.setFitHeight(150);
	            // moviePoster.setImage(new Image("file:path_to_image.jpg"));  // 실제 이미지 경로로 대체 필요

	            Label movieTitleLabel = new Label(title);
	            movieTitleLabel.setId("movieTitleLabel");

	            movieItem.getChildren().addAll(moviePoster, movieTitleLabel);

	            movieItem.setOnMouseClicked(event -> selectMovie(movieItem,movieTitleLabel.getText(),rating,runtime,movietype,poster));

	            movieListVBox.getChildren().add(movieItem);
			}
			
    		con.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    // 메뉴로 돌아가는 버튼 클릭 이벤트 처리
    @FXML
    private void handleBackToMenuAction(ActionEvent event) {
        try {
            // FXML 파일을 로드하여 메뉴 화면으로 전환
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AdminMenuPopup.fxml"));
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
    
 // 메뉴로 돌아가는 버튼 클릭 이벤트 처리
    @FXML
    private void handleBackToMenu(ActionEvent event) {
        try {
            // FXML 파일을 로드하여 메뉴 화면으로 전환
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AdminMenuPopup.fxml"));
            Parent menuRoot = loader.load();

            // 현재 창의 Stage를 가져옴
            Stage stage = (Stage) backTosetting.getScene().getWindow();
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