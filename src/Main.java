import Container.Container;
import com.fasterxml.jackson.core.JsonProcessingException;
import controller.MainController;
import dto.ReservationDto;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        MainController controller = Container.setMainController();
        Container.playerInfoService(LocalDateTime.now());
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("D4");
        ReservationDto reservationDto = ReservationDto.builder().phoneNumber("01091922782").seatList(arrayList).adult(1).isSave(true).play_info(2).build();
        controller.setReserve(reservationDto);
    }
}