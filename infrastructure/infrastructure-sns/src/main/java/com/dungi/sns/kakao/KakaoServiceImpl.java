package com.dungi.sns.kakao;

import com.dungi.core.integration.sns.SnsService;
import com.dungi.sns.kakao.dto.KakaoInfoDto;
import com.dungi.sns.kakao.dto.SnsTokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class KakaoServiceImpl implements SnsService {
    @Value("${kakao.accountId}")
    private String kakaoAccountId;
    @Value("${kakao.secret}")
    private String kakaoSecret;
    @Value("${kakao.callbackUri}")
    private String kakaoCallbackUri;

    private static final String KAKAO_API_URL = "https://kapi.kakao.com";
    private static final String KAKAO_AUTH_URL = "https://kauth.kakao.com";

    private final KakaoHttpInterface kakaoApiService;
    private final KakaoHttpInterface kakaoAuthService;

    public KakaoServiceImpl() {
        Retrofit kakaoApiRetrofit = new Retrofit.Builder()
                .baseUrl(KAKAO_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        kakaoApiService = kakaoApiRetrofit.create(KakaoHttpInterface.class);

        Retrofit kakaoAuthRetrofit = new Retrofit.Builder()
                .baseUrl(KAKAO_AUTH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        kakaoAuthService = kakaoAuthRetrofit.create(KakaoHttpInterface.class);
    }

    // 카카오 이메일 가져오기 메서드
    public String getSnsInfo(String token) throws Exception {
        Call<KakaoInfoDto> retrofitCall = kakaoApiService.getKakaoInfo(
                "Bearer "+token,"application/x-www-form-urlencoded");
        Response<KakaoInfoDto> response = retrofitCall.execute();
        return response.body().getKakao_account().getEmail();
    }

    // 카카오 토큰 가져오기 메서드
    public String getSnsToken(String code) throws Exception{
        Call<SnsTokenDto> retrofitCall = kakaoAuthService.getKakaoToken(
                "authorization_code", kakaoAccountId, kakaoCallbackUri, code, kakaoSecret
                ,"application/x-www-form-urlencoded");
        Response<SnsTokenDto> response = retrofitCall.execute();
        assert response.body() != null;
        return response.body().getAccess_token();
    }
}
