package client.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import enumcode.StatusCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private int statusCode;

    private Object body;

    public String responseBuild(){
        String returnValue = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            returnValue = objectMapper.writeValueAsString(this);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(returnValue);
        return returnValue;
    }
}
