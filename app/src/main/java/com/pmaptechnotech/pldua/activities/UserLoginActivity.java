package com.pmaptechnotech.pldua.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.pmaptechnotech.pldua.R;

import com.pmaptechnotech.pldua.adminside.DepartmentInfoActivity;
import com.pmaptechnotech.pldua.api.Api;
import com.pmaptechnotech.pldua.api.WebServices;
import com.pmaptechnotech.pldua.logics.P;
import com.pmaptechnotech.pldua.logics.U;
import com.pmaptechnotech.pldua.models.UserLoginInput;
import com.pmaptechnotech.pldua.models.UserLoginResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserLoginActivity extends AppCompatActivity {
    @BindView(R.id.edt_mobile_number)
    EditText edt_mobile_number;
    @BindView(R.id.edt_Password)
    EditText edt_Password;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.btn_register)
    Button btn_register;
    @BindView(R.id.tool_bar_logo)
    ImageView tool_bar_logo;

    private SweetAlertDialog dialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        context = UserLoginActivity.this;
        ButterKnife.bind(this);
        tool_bar_logo.setImageResource(R.drawable.ic_more_vert_black_24dp);// tool bar icon

        btn_register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, UserRegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

    }

    private void validation() {
        if (!P.isValidEditText(edt_mobile_number, "Mobile Number")) return;
        if (!P.isValidEditText(edt_Password, "Password")) return;
        userLogin();
    }
    
    private void userLogin() {

        if (edt_mobile_number.getText().toString().equals("admin") && (edt_Password.getText().toString().equals("admin"))) {
            Intent intent = new Intent(context, DepartmentInfoActivity.class);
            startActivity(intent);
            finish();
        }

        else {
        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        UserLoginInput userLoginInput = new UserLoginInput(
                edt_mobile_number.getText().toString().trim(),
                edt_Password.getText().toString().trim()
        );
        dialog = P.showBufferDialog(context, "Processing...");
        //btn_login.setProgress(1);
        btn_login.setEnabled(false);
        P.hideSoftKeyboard(UserLoginActivity.this);


        //CALL NOW
        webServices.userLogin(userLoginInput)
                .enqueue(new Callback<UserLoginResult>() {
                    @Override
                    public void onResponse(Call<UserLoginResult> call, Response<UserLoginResult> response) {
                        if (dialog.isShowing()) dialog.dismiss();
                        if (!P.analyseResponse(response)) {
                            //btn_login.setProgress(0);
                            btn_login.setEnabled(true);
                            //Toast.makeText(context, "Server Error", Toast.LENGTH_LONG).show();
                            P.ShowErrorDialogAndBeHere(context, getString(R.string.error), getString(R.string.server_error));
                            return;
                        }
                        UserLoginResult result = response.body();
                        if (result.is_success) {
                            //btn_login.setProgress(100);
                            // U.userDetails = result.user.get(0);
                            Intent intent = new Intent(context, InformationActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            // btn_login.setProgress(0);
                            btn_login.setEnabled(true);
                            P.ShowErrorDialogAndBeHere(context, getString(R.string.error), result.msg);
                            //Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserLoginResult> call, Throwable t) {
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();
                        if (dialog.isShowing()) dialog.dismiss();
                        // btn_login.setProgress(0);

                        btn_login.setEnabled(true);
                        P.ShowErrorDialogAndBeHere(context, getString(R.string.error), getString(R.string.check_internet_connection));
                    }
                });

      /*  btn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (edt_mobile_number.getText().toString().equals("admin") && (edt_mobile_number.getText().toString().equals("admin"))) {
                    Intent intent = new Intent(context, DepartmentInfoActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                   // Toast.makeText(context, "incorrect username or password", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, InformationActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });*/

    }}
}

