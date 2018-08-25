package com.pmaptechnotech.pldua.api;


import com.pmaptechnotech.pldua.models.DeptListInput;
import com.pmaptechnotech.pldua.models.DeptListResult;
import com.pmaptechnotech.pldua.models.MushroomInput;
import com.pmaptechnotech.pldua.models.MushroomResult;
import com.pmaptechnotech.pldua.models.UploadDepartmentsInput;
import com.pmaptechnotech.pldua.models.UploadDepartmentsResult;
import com.pmaptechnotech.pldua.models.UserLoginInput;
import com.pmaptechnotech.pldua.models.UserLoginResult;
import com.pmaptechnotech.pldua.models.UserRegisterInput;
import com.pmaptechnotech.pldua.models.UserRegisterResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Admin on 1/20/2018.
 */

public interface WebServices {
    @POST("PmapPldUserLogin_c/userLogin")
    Call<UserLoginResult> userLogin(@Body UserLoginInput input);

    @POST("PLDUserRegister_c/userRegister")
    Call<UserRegisterResult> userRegister(@Body UserRegisterInput input);

    @POST("AdminUploadDepartments_c/uploadDepartments")
    Call<UploadDepartmentsResult> uploadDepartments(@Body UploadDepartmentsInput input);

    @POST("UserGetForestDepartmets_c/getUserDepartments")
    Call<DeptListResult> getUserDepartments(@Body DeptListInput input);

    @POST("UploadMushroomDisease_c/uploadMushroom")
    Call<MushroomResult> uploadMushroom(@Body MushroomInput input);


}


