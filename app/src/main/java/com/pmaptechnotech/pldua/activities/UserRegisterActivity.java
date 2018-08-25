package com.pmaptechnotech.pldua.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pmaptechnotech.pldua.R;
import com.pmaptechnotech.pldua.api.Api;
import com.pmaptechnotech.pldua.api.WebServices;
import com.pmaptechnotech.pldua.logics.P;
import com.pmaptechnotech.pldua.models.UserRegisterInput;
import com.pmaptechnotech.pldua.models.UserRegisterResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class UserRegisterActivity extends AppCompatActivity {


    @BindView(R.id.edt_name)
    EditText edt_name;
    @BindView(R.id.edt_number)
    EditText edt_number;
    @BindView(R.id.edt_password)
    EditText edt_password;
    @BindView(R.id.edt_confirm_password)
    EditText edt_confirm_password;
    @BindView(R.id.btn_register)
    Button btn_register;

    private Context context;
    private SweetAlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        context = UserRegisterActivity.this;
        ButterKnife.bind(this);

        btn_register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, UserRegisterActivity.class);
                startActivity(intent);

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

    }

    private void validation() {
        if (!P.isValidEditText(edt_name, "name")) return;
        if (!P.isValidEditText(edt_number, "number")) return;
        if (!P.isValidEditText(edt_password, "Password")) return;
        if (!P.isValidEditText(edt_confirm_password, "confirm Password")) return;
        Register();
    }
    private void Register() {

        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        UserRegisterInput UserRegisterInput = new UserRegisterInput(
                edt_name.getText().toString().trim(),
                edt_number.getText().toString().trim(),
                edt_password.getText().toString().trim()

        );
        dialog = P.showBufferDialog(context, "Processing...");
        // btn_Submit.setProgress(1);
        btn_register.setEnabled(false);
        P.hideSoftKeyboard(UserRegisterActivity.this);

        //CALL NOW

        webServices.userRegister(UserRegisterInput)
                .enqueue(new Callback<UserRegisterResult>() {
                    @Override
                    public void onResponse(Call<UserRegisterResult> call, Response<UserRegisterResult> response) {
                        if (dialog.isShowing()) dialog.dismiss();
                        if (!P.analyseResponse(response)) {
                            // btn_Submit.setProgress(0);
                            btn_register.setEnabled(true);
                            P.ShowErrorDialogAndBeHere(context, getString(R.string.error), getString(R.string.server_error));
                            return;
                        }
                        UserRegisterResult result = response.body();
                        if (result.is_success) {
                            // btn_Submit.setProgress(100);
                            P.ShowSuccessDialog(context, getString(R.string.Success), result.msg);
                            Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context, UserLoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            //  btn_Submit.setProgress(0);
                            btn_register.setEnabled(true);
                            P.ShowErrorDialogAndBeHere(context, getString(R.string.error), result.msg);
                        }

                    }

                    @Override
                    public void onFailure(Call<UserRegisterResult> call, Throwable t) {
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();
                        if (dialog.isShowing()) dialog.dismiss();
                        //  btn_Submit.setProgress(0);
                        btn_register.setEnabled(true);
                        P.ShowErrorDialogAndBeHere(context, getString(R.string.error), getString(R.string.check_internet_connection));

                    }
                });
    }
}