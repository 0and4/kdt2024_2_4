package dto;
/*
    명명 규칙
    겟 / delete/ update... _ 메서드명 _ 인자값
*/

public enum RequestCode {
    GET_PLAYINFO_TIME,
    POST_RESERVE_RESERVEDTO,
    GET_PHONENUMBER_STRING,
    GET_MEMBERINFO_STRING,
    GET_RESERVE_BYID,
    GET_RESERVE_BYPHONE,
    DELETE_RESERVE_BYID
}

