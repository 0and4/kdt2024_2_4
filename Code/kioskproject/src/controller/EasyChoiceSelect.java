package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dto.MovieData;
import java.util.Random;
import dto.ReservationDAO;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.List;
import java.util.Set;
import java.security.SecureRandom;
import java.util.HashSet;

public class EasyChoiceSelect implements Initializable{
    @FXML private StackPane EasyChoiceSelectPane;
    @FXML private Button autoSelect; //좌석 자동 선택 버튼
    @FXML private Button selfSelect; //좌석 수동 선택 버튼
    @FXML private Button home; // 홈버튼
    @FXML private Button nextPage; // 다음으로 버튼 
    @FXML private Button backBtn; // 이전으로 버튼
    int a=0;
    
    private MovieData selectMovie;
    private Integer selectedMovieSeat;
    private Integer selectedPeople;
    private Integer selectedtotalPrice;
    private List<String> PeopleDetails;
    @FXML StackPane seatPane;
	@FXML GridPane seatGrid;
    private Set<Button> selectedSeats = new HashSet<>(); // 선택된 좌석을 저장하는 Set

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        home.setOnAction(event->BackHome(event));// home 버튼
		backBtn.setOnAction(event->BackPage(event)); //이전으로 버튼
        nextPage.setOnAction(event->NextPage(event)); //다음으로 버튼
        autoSelect.setOnAction(event->auto(event));
        selfSelect.setOnAction(event->self(event));
    }
    public void auto(ActionEvent event) {
        a=0;
	}
    public void self(ActionEvent event){
        a=1;
    }
    public void keeping(MovieData moviedata,Integer seatcount, Integer selectedNumberOfPeople, Integer selectedTotalPrice,List<String> selectedPeopleDetails) {
		this.selectMovie = moviedata;
		this.selectedMovieSeat = seatcount;
		this.selectedPeople = selectedNumberOfPeople;
		this.selectedtotalPrice = selectedTotalPrice;
		this.PeopleDetails = selectedPeopleDetails;
		System.out.println("선택한 영화의 좌석 수: " + selectedMovieSeat);
		System.out.println(selectedPeople);
		System.out.println(selectedtotalPrice);
		// createSeats();
	}
    

    private void NextPage(ActionEvent event) {
            try {
                if (a==1) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/seat.fxml"));
                    Parent seatstage = loader.load();
                    seatController controller = loader.getController();
                    // 좌석 수를 전달
                    controller.initializeData(selectMovie ,selectMovie.getSelectedMovieSeat(),selectedPeople, selectedtotalPrice, PeopleDetails);
                    StackPane root = (StackPane) EasyChoiceSelectPane.getScene().getRoot();
                    root.getChildren().add(seatstage);
                    
                    //전환 효과
                    seatstage.setTranslateX(650);
                    Timeline timeline = new Timeline();
                    KeyValue keyValue = new KeyValue(seatstage.translateXProperty(), 0);
                    KeyFrame keyFrame = new KeyFrame(Duration.millis(200),keyValue); //0.2초간 실행 좌에서 우로 이동...
                    timeline.getKeyFrames().add(keyFrame);
                    timeline.play();//애니메이션 실행
                }else if(a==0){
                    // FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/buy.fxml"));
                    // Parent buybuy = loader.load();
                    switchPayment();
                    // StackPane root = (StackPane) EasyChoiceSelectPane.getScene().getRoot();
                    // root.getChildren().add(buybuy);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 예외 발생 시 사용자에게 경고창 표시
            }
        }

        //예매 번호 16자리 생성
	private String generateRandomNumber() {
        SecureRandom random = new SecureRandom();
        StringBuilder randomNumber = new StringBuilder(16);
        for (int i = 0; i < 16; i++) {
            randomNumber.append(random.nextInt(10)); // 0-9 사이의 숫자 추가
        }
        return randomNumber.toString();
    }
	
	//결제화면으로 이동

    // 결제화면으로 이동
    private void switchPayment() {
        try {
            System.out.println("스위치 페이먼트 버튼 클릭작용");
            String randomNumber = generateRandomNumber();
            

            // 좌석 정보를 랜덤하게 변환
            StringBuilder selectedSeatsString = new StringBuilder();
            for (Button seat : selectedSeats) {
                String randomSeat = getRandomSeat(seat.getText()); // 기존 좌석을 무작위 좌석으로 변환
                selectedSeatsString.append(randomSeat).append(", ");
            }
            System.out.println("랜덤좌석"+selectedSeatsString.toString());
            // 마지막 ", " 제거
            if (selectedSeatsString.length() > 0) {
                selectedSeatsString.setLength(selectedSeatsString.length() - 2);
            }
            System.out.println(selectedSeatsString);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/buy.fxml"));
            Parent paymentstage = loader.load();
            buyController controller = loader.getController();
            
            // 랜덤으로 변환된 좌석 정보를 결제 화면으로 전달
            controller.initializeData(selectMovie, selectedSeatsString.toString(), selectedtotalPrice, PeopleDetails, randomNumber);
            StackPane root = (StackPane) EasyChoiceSelectPane.getScene().getRoot();
            root.getChildren().add(paymentstage);

            // 전환 효과
            paymentstage.setTranslateX(650);
            Timeline timeline = new Timeline();
            KeyValue keyValue = new KeyValue(paymentstage.translateXProperty(), 0);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(200), keyValue); // 0.2초간 실행 좌에서 우로 이동...
            timeline.getKeyFrames().add(keyFrame);
            timeline.play(); // 애니메이션 실행
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 좌석 중복을 방지하는 로직
    private Set<String> generatedSeats = new HashSet<>();

    private String getRandomSeat(String seat) {
        Random random = new Random();
        String newSeat;
        
        // 중복되지 않는 좌석이 나올 때까지 반복
        do {
            char randomAlphabet = (char) ('A' + random.nextInt(6)); // A-F 중 하나
            int randomNumber = random.nextInt(10) + 1; // 1-10 사이의 숫자
            newSeat = randomAlphabet + Integer.toString(randomNumber);
        } while (generatedSeats.contains(newSeat)); // 중복 검사
        
        generatedSeats.add(newSeat); // 새로운 좌석을 기록
        
        return newSeat;
    }


        private void BackPage(ActionEvent event) {
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
    
}
