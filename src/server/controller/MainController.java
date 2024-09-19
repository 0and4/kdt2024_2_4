package server.controller;

import server.dto.RegisterMovieDto;
import server.service.LoginService;
import server.service.PlayInfoService;
import server.service.SaleService;

public class MainController {
    private final PlayInfoService playInfoService = new PlayInfoService();
    private final SaleService saleService = new SaleService();

    private final LoginService loginService = new LoginService();
    //무비 id값을 기준으로 현재 상영중인 영화를 보여줍니다...
    //0을 던져주면 모든 영화를 기준으로 리스트를 반환...
    public String getPlayInformation(int movieId){
        String answer = playInfoService.getPlayInfoById(movieId);
        return answer;
    }
    public String getAllPlayInformation(){
        String answer = playInfoService.getAllPlayInfo();
        return answer;
    }

    public String setPlay(RegisterMovieDto rmd){
        String answer = playInfoService.registerPlayInfo(rmd);
        return answer;
    }

    public String getSalesByDate(int year, int month){
        String answer = saleService.getSalesByDate(year ,month);
        return answer;
    }

    public String getSalesByMonth(int year){
        String answer = saleService.getSalesByMonth(year);
        return answer;
    }

    public String login(String id, String password){
        String answer = loginService.login(id, password);
        System.out.println(answer);
        return answer;
    }
}
