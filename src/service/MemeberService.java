package service;

import classLoader.Connect;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.MemberDto;

import java.lang.reflect.Member;
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
                System.out.println("멤버가 존재합니다....");
                String Response = rs.getString("phone");
                rs.close();
                return Response;
            }
            else{
                //널값을 리턴합니다..
                rs.close();
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
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
                ObjectMapper objectMapper = new ObjectMapper();
                String returnValue = objectMapper.writeValueAsString(memberDto);
                System.out.println(returnValue);
                return returnValue;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
