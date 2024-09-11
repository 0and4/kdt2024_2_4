import Container.Container;
import com.fasterxml.jackson.core.JsonProcessingException;
import controller.MainController;
import dto.ReservationDto;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        MainController controller = Container.setMainController();
        controller.getReservationDetailByPhoneNumber("01091922782");
    }
}