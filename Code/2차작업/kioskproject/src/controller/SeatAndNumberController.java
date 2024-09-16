package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dto.PlayInfoDto;
import dto.ReservationDto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SeatAndNumberController implements Initializable{

	@FXML private Label title;
	@FXML private Label time;
	@FXML private ImageView imageView;
	@FXML private Spinner general, old, youth, treat; 
	@FXML private Button nextPage;
	private Image image;
	private PlayInfoDto playInfoDto;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		general.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
		old.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
		youth.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
		treat.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
		
		nextPage.setOnMouseClicked(e ->{
			Integer generalNum = (Integer) general.getValue();
			Integer oldNum = (Integer) old.getValue();
			Integer youthNum = (Integer) this.youth.getValue();
			Integer treatNum = (Integer) treat.getValue();
			int sum = generalNum + oldNum + youthNum + treatNum;
			if(sum != 0) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SelectSeat.fxml"));
		        try {
					Parent nextScreen = loader.load();
					SelectSeatController controller = loader.getController();
					controller.setPlayInfoDto(playInfoDto);
					ReservationDto rDto = ReservationDto
							.builder()
							.youth(youthNum)
							.adult(generalNum)
							.treatment(treatNum)
							.old(oldNum)
							.play_info(this.playInfoDto.getPlayInfoId())
							.seatList(new ArrayList<>())
							.build();
					controller.setReservationDto(rDto);
					controller.init();
					Stage stage = (Stage) nextPage.getScene().getWindow();
					stage.setScene(new Scene(nextScreen));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		         
			}
		});
	}
}
