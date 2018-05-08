package com.kirandeep.ivote.retrofitBase;

import com.kirandeep.ivote.models.CheckIfPresentResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by abc on 07-05-2018.
 */

public interface ApiInterface {
    @GET("checkId/{uid}")
    Call<CheckIfPresentResponse> checkIfUidPresent(@Path("uid") String uid);
}
