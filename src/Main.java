import server.container.Container;
import com.fasterxml.jackson.core.JsonProcessingException;
import server.controller.MainController;
import server.dto.RegisterMovieDto;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        MainController mainController = Container.setMainController();
        mainController.getPlayInformation(20249188);
        mainController.getAllPlayInformation();

        RegisterMovieDto movieDto = RegisterMovieDto
                .builder()
                .movie_id(20249188)
                .localDateTime(LocalDateTime.of(2022,9,13,10,00))
                .theater_id(8)
                .build();
        mainController.setPlay(movieDto);

        mainController.getSalesByMonth(2024);
    }
}