package server.service;

import classLoader.Connect;
import client.dto.Response;
import enumcode.StatusCode;
import server.dto.SalesDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SaleService {
    Connection connection = Connect.getConnection();
    public String getSalesByDate(int year ,int month){
        String sql = "select sum(payment), DATE(date) from reservation where DATE_FORMAT(date, '%Y-%m') = ? group by DATE(date)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            System.out.println(String.format("%d-%02d",year,month));
            pstmt.setString(1, String.format("%d-%02d",year,month));
            ResultSet rs = pstmt.executeQuery();
            ArrayList<SalesDto> salesDtos = new ArrayList<>();
            while (rs.next()){
                System.out.println("값 구하기..");
                SalesDto salesDto = SalesDto
                        .builder()
                        .dateTime(rs.getTimestamp(2).toString())
                        .sale(rs.getInt(1))
                        .build();
                salesDtos.add(salesDto);
            }
            Response response = new Response();
            response.setStatusCode(StatusCode.SUCCESS.getStatusCode());
            response.setBody(salesDtos);
            return response.responseBuild();
        }catch (Exception e){
            Response response = new Response();
            response.setBody("서버에 오류가 발생하였습니다..");
            response.setStatusCode(StatusCode.INTERNAL_ERROR.getStatusCode());
            return response.responseBuild();
        }
    }

    public String getSalesByMonth(int year){
            String sql = "select sum(payment), DATE_FORMAT(date, '%Y-%m') from reservation where year(date) = ? group by DATE_FORMAT(date, '%Y-%m')";
            try {
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1, year);
                ResultSet rs = pstmt.executeQuery();
                ArrayList<SalesDto> salesDtos = new ArrayList<>();
                while (rs.next()){
                    System.out.println("값 구하기..");
                    SalesDto salesDto = SalesDto
                            .builder()
                            .dateTime(rs.getString(2))
                            .sale(rs.getInt(1))
                            .build();
                    salesDtos.add(salesDto);
                }
                Response response = new Response();
                response.setStatusCode(StatusCode.SUCCESS.getStatusCode());
                response.setBody(salesDtos);
                return response.responseBuild();
            }catch (Exception e){
                e.printStackTrace();
                Response response = new Response();
                response.setBody("서버에 오류가 발생하였습니다..");
                response.setStatusCode(StatusCode.INTERNAL_ERROR.getStatusCode());
                return response.responseBuild();
            }
    }
}
