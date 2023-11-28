package com.peeerr.instagram.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SubscribeInfoResponse {

    private Long id;
    private String username;
    private String profileImageUrl;
    private Long subscribeState;  // 구독하기 or 구독취소 버튼 유동적으로 처리
    private Long equalUserState;  // 본인이면 구독 버튼 숨김

}
