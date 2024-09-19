package client.service;

import classLoader.Connect;
import client.dto.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import client.dto.MemberDto;
import enumcode.StatusCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemeberService {
    //멤버가 존재하는 지 판한하는 여부....
    Connection connection = Connect.getConnection();

    public String getPhoneNumber(String phoneNumber){
        String sql = "select * from user where phone = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, phoneNumber);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                class Phone{
                    final String phoneNumber;
                    Phone(String phoneNumber){
                        this.phoneNumber = phoneNumber;
                    }
                }
                System.out.println("멤버가 존재합니다....");
                Response response = new Response();
                response.setStatusCode(StatusCode.SUCCESS.getStatusCode());
                Phone phone = new Phone(rs.getString("phone"));
                response.setBody(phone);
                rs.close();
                return response.responseBuild();
            }
            else{
                //널값을 리턴합니다..
                rs.close();
                Response response = new Response();
                response.setBody(String.format("%s와 알치한느 전화번호가 없습니다.", phoneNumber));
                response.setStatusCode(StatusCode.NOT_FOUND.getStatusCode());
                return response.responseBuild();
            }
        }catch (Exception e){
            e.printStackTrace();
            Response response = new Response();
            response.setStatusCode(StatusCode.INTERNAL_ERROR.getStatusCode());
            response.setBody("서버에 오류가 발생하였습니다...");
            return response.responseBuild();
        }
    }

    public String getMemberInformation(String phoneNumber){
        String sql = "select sum(saving) as totalSave, sum(discount) as totalDiscount from reservation where phone = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, phoneNumber);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                int totalSave = rs.getInt("totalSave");
                int totalDiscount = rs.getInt("totalDiscount");
                int available = totalSave - totalDiscount;
                MemberDto memberDto = MemberDto.builder()
                        //전화번호
                        .phoneNumber(phoneNumber)
                        //총적립금액
                        .saving(totalSave)
                        //사용가능학 적립금액
                        .available(available)
                        //총 사용한 할인금액
                        .discount(totalDiscount)
                        .build();
                Response response = new Response();
                response.setBody(memberDto);
                response.setStatusCode(StatusCode.SUCCESS.getStatusCode());
                return response.responseBuild();
            }
            else {
                Response response = new Response();
                response.setStatusCode(StatusCode.SUCCESS.getStatusCode());
                MemberDto memberDto = MemberDto
                        .builder()
                        .available(0)
                        .saving(0)
                        .build();
                response.setBody(memberDto);
                return response.responseBuild();
            }
        }catch (Exception e){
            Response response = new Response();
            response.setStatusCode(StatusCode.INTERNAL_ERROR.getStatusCode());
            response.setBody("서버에 오류가 발생하였습니다..");
            return response.responseBuild();
        }
    }
}
