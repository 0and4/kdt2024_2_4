package service;

/*1. 영화 상단엔 상영 정보가 출력된다.
        - 상영 정보 : 1) 영화 이미지 2) 관람 등급 3) 영화 제목 4)종류(2DX, Laser, 3D) 5) 상영 시간(러닝타임) 6)상영 시작 시간 7) 총 좌석수 8) 남은 좌석 수 9) 상영관
        2. 인원 선택 : 상영정보 아래엔 관람 인원수를 선택해주세요라는 텍스트가 나오며 텍스트 아래엔 일반 / 청소년 / 경로 / 우대 란이 있다. 각각 인원수를 선택할 수 있다.
        3. 좌석 선택 : 화면 하단엔 좌석 위치가 나오며 선택 가능한 좌석은 하얀색, 이미 예약이 되었거나 선택이 불가능한 화면은 회색으로 표시된다. 좌석 위치를 클릭할 시 해당 좌석이 갈색으로 바뀐다.
        4. 좌측 하단에는 총 결제 금액이 표시된다.
        5. 우측 하단에는 [결제하기 버튼]이 표시된다.
        6. 모두 선택하였으면 [결제하기 버튼]이 활성화 된다.

        1. 좌측엔 1) 예매 정보와 2) 총 결제 금액을 나타낸다.
- 예매 정보 : 1) 영화 이미지 2) 관람 등급 3) 영화 제목 4)종류(2DX, Laser, 3D) 5) 상영 시간(러닝타임) 6)상영 시작 시간 7) 총 좌석수 8) 남은 좌석 수 9) 상영관 10) 좌석 위치 11) 인원 정보 12) 예매번호
- 인원 정보 : 일반/ 청소년/ 경로/ 우대 종류 + 인원 수 ex. 일반 1명, 청소년 2명
* 회원 정보(휴대폰 번호) 받는 과정 : 처음 전화번호를 입력했을 때 " 등록된 번호가 없습니다. 회원 가입하시겠습니까? "
[예] 선택 시, 해당 전화번호로 회원가입이 진행됩니다. 적립된 포인트 "00원" [확인]
[아니오] 선택 시, 포인트 적립을 취소합니다. [확인] 버튼 누르면 (결제창으로 다시 이동)
2. 우측(상단)엔 1) 멤버쉽 포인트와 2) 결제 수단 선택을 나타낸다.
- 멤버쉽 [포인트 적립], [포인트 사용하기] 버튼이 있다.
- 멤버쉽 포인트 적립 혹은 멤버쉽 포인트 사용하기 버튼을 클릭하면 (팝업) 전화번호 입력란을 받는 창이 뜬다.
- 비고의 <포인트 팝업 >을 참고한다.
- 포인트 적용 방식 :
3. 우측(하단)엔 결제 방법 1) 카카오 페이 2) 네이버 페이 3) 신용카드 등 결제 수단을 선택할 수 있게 한다.
4. 결제 완료 후 : (팝업) 티켓을 받을 휴대폰 번호를 입력해 주세요. -> 키패드로 입력 후 확인 -> 완료되었습니다.
 */

import Container.Container;
import classLoader.Connect;
import dto.ReservationDto;

import java.sql.Connection;

public class ReservationService {
    /*
        - 예매 정보 : 1) 영화 이미지 2) 관람 등급 3) 영화 제목 4)종류(2DX, Laser, 3D) 5) 상영 시간(러닝타임) 6)상영 시작 시간 7) 총 좌석수 8) 남은 좌석 수 9) 상영관 10) 좌석 위치 11) 인원 정보 12) 예매번호
        - 인원 정보 : 일반/ 청소년/ 경로/ 우대 종류 + 인원 수 ex. 일반 1명, 청소년 2명
        * 회원 정보(휴대폰 번호) 받는 과정 : 처음 전화번호를 입력했을 때 " 등록된 번호가 없습니다. 회원 가입하시겠습니까? "
        [예] 선택 시, 해당 전화번호로 회원가입이 진행됩니다. 적립된 포인트 "00원" [확인]
     */
    //커넥션 정보 가져오기..
    Connection connection = Connect.getConnection();
    public String setReservation(ReservationDto reservationDto){
        //상영 정보를 가져옵니다...
        int playInfoId = reservationDto.getPlay_info();
        String phoneNumber = reservationDto.getPhoneNumber();
        int discount = reservationDto.getDiscount();
        int youth = reservationDto.getYouth();
        int adult = reservationDto.getAdult();
        int old = reservationDto.getOld();
        int treatment = reservationDto.getTreatment();
        String sql = "insert into reservation (payment, discount, date, play_info_id, phone, saving) values (?, ?, ?, ?, ?, ?)";
        return null;
    }
    public String getKind(int playInfoId){
        return null;
    }
}
