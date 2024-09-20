package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


public class SalesDto {
    //해당 날짜...
    String dateTime;

    //판매금액..
    private int sale;
    public String getDateTime() {
        return this.dateTime;
    }

    public int getSale() {
        return this.sale;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }
}