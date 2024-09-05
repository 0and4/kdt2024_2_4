import Container.Container;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        Container.setMainController();
        Container.playerInfoService(LocalDateTime.now());
    }
}