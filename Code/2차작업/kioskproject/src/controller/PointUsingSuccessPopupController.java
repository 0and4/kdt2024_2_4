package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

import java.io.IOException;

import dto.MemberDto;
import dto.PlayInfoDto;
import dto.ReservationDto;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointUsingSuccessPopupController {

    @FXML
    private TextField phoneField1; // 포인트 입력 필드
    @FXML
    private Button confirmButton; // 확인 버튼
    @FXML
    private Text currentPointText; // 상단의 현재 포인트 텍스트

    private boolean isPlaceholderVisible = true; // 플레이스홀더가 표시되고 있는지 여부

    // 현재 포인트 (나중에 DB에서 불러오는 값을 시뮬레이션, 예: 1230원)
    private int currentPoint = 1230;

    private PlayInfoDto playInfoDto;
    private ReservationDto reserveDto;
    private MemberDto memberDto;
    
    // 숫자 키패드 버튼 클릭 시 호출되는 메서드
    @FXML
    private void handleNumberClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource(); // 클릭된 버튼
        String buttonText = clickedButton.getText(); // 버튼의 텍스트(숫자)
        confirmButton.setOnMouseClicked(e->{
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/buy.fxml"));
			Parent nextPage = null;
			try {
				nextPage = loader.load();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BuyController buyController = loader.getController();
			buyController.setPlayInfo(playInfoDto);
			reserveDto.setDiscount(Integer.parseInt(phoneField1.getText()));
			buyController.setReserveDto(reserveDto);
			buyController.init();
			Stage stage = (Stage) confirmButton.getScene().getWindow();
			stage.setScene(new Scene(nextPage));
        });
        // 처음 클릭할 때 예시 텍스트를 지움
        if (isPlaceholderVisible) {
            phoneField1.clear(); // 입력 필드를 비움
            isPlaceholderVisible = false;
        }

        // "지우기" 버튼일 때 처리 - 전체 텍스트 삭제
        if ("지우기".equals(buttonText)) {
            phoneField1.clear(); // 모든 텍스트 삭제
        }
        // "최대" 버튼일 때 처리 - 500원 단위로 최대 값을 입력 필드에 추가
        else if ("최대".equals(buttonText)) {
            int maxUsablePoint = (currentPoint / 500) * 500; // 500으로 나누어지는 최대 값 계산
            phoneField1.setText(String.valueOf(maxUsablePoint)); // 최대 값을 입력
        }
        // 그 외 숫자 버튼 처리
        else {
            phoneField1.appendText(buttonText); // 클릭된 숫자를 입력 필드에 추가
        }

        validatePointInput(); // 입력 후 500 단위 및 현재 포인트 범위 내인지 확인
    }

    // 입력 필드를 클릭하면 예시 텍스트를 제거하는 메서드
    @FXML
    private void clearPlaceholder(MouseEvent event) {
        if (isPlaceholderVisible) {
            phoneField1.clear(); // 예시 텍스트를 비움
            isPlaceholderVisible = false; // 이후로는 지우지 않음
        }
    }

    // 입력된 포인트가 500원 단위이며, 현재 포인트 내에서 사용 가능한지 확인하는 메서드
    private void validatePointInput() {
        try {
            int pointValue = Integer.parseInt(phoneField1.getText()); // 입력값을 정수로 변환
            if (pointValue % 500 == 0 && pointValue > 0 && pointValue <= currentPoint) {
                confirmButton.setDisable(false); // 500원 단위이면서 현재 포인트 이하이면 버튼 활성화
            } else {
                confirmButton.setDisable(true); // 조건을 만족하지 않으면 버튼 비활성화
            }
        } catch (NumberFormatException e) {
            confirmButton.setDisable(true); // 숫자가 아닌 값이 입력되면 버튼 비활성화
        }
    }

    // 초기화 메서드로 현재 포인트를 텍스트에 표시
    @FXML
    public void initialize() {
        currentPointText.setText(String.valueOf(currentPoint) + "원"); // 현재 포인트 설정 (예: "1230원")
    }
    
    public void init() {
    	currentPointText.setText("" + memberDto.getAvailable()+"원");
    }
}
