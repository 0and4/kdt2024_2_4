package com.exam.app;

import java.util.ArrayList;
import java.util.HashMap;

public class AppData {
    private static HashMap<String, ArrayList<String>> movieScreenings = new HashMap<>();

    static {
        // 상영정보 관리에서 사용할 예시 영화 데이터 추가
        ArrayList<String> screeningInfo1 = new ArrayList<>();
        screeningInfo1.add("2D, 전체 관람가, 123분");
        movieScreenings.put("Movie Title 1", screeningInfo1);

        ArrayList<String> screeningInfo2 = new ArrayList<>();
        screeningInfo2.add("4D, 15세 이상 관람가, 115분");
        movieScreenings.put("Movie Title 2", screeningInfo2);
    }

    public static HashMap<String, ArrayList<String>> getMovieScreenings() {
        return movieScreenings;
    }
}
