package dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private String phoneNumber;
    //적립금액
    private int saving;
    //사용한 할인 금액
    private int discount;
    //사용가능한 뽀인트 금액
    private int available;
}