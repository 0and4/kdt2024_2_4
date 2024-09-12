package server.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Data
public class DateDeatilDto {
    private String startDateTime;
    private String endDateTime;
}
