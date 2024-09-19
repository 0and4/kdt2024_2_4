package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.Admin;
import dto.RequestCode;
import dto.RequestDto;
import dto.StatusCode;
import handleMessage.HandleFunction;

public class AdminLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String pass = passwordField.getText();
        HandleFunction hf = new HandleFunction();
        RequestDto rd = new RequestDto();
        rd.setRequestCode(RequestCode.POST_LOGIN_IDANDPASS);
        Admin admin = new Admin();
        admin.setId(username);
        admin.setPassword(pass);
        rd.setBody(admin);
        String returnValue = hf.submit(rd);
        ObjectMapper obm = new ObjectMapper();
        Map<String, Object> jsonMap = null;
        System.out.print(returnValue);
        boolean isCorrect = false;
        try {
			jsonMap = obm.readValue(returnValue,  new TypeReference<Map<String, Object>>() {});
			if(StatusCode.SUCCESS.getStatusCode() == Integer.parseInt(jsonMap.get("statusCode").toString())) {
				isCorrect = true;
			}
			else {
				isCorrect = false;
			}
        } catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (isCorrect) {
            try {
                // Load the AdminMenuPopup.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AdminMenuPopup.fxml"));
                Parent menuPopup = loader.load();

                // Get the Stage from the current scene and set the new scene
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(menuPopup));

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }
}
