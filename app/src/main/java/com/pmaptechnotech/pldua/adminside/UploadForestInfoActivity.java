package com.pmaptechnotech.pldua.adminside;

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
import com.pmaptechnotech.pldua.models.UploadDepartmentsInput;
import com.pmaptechnotech.pldua.models.UploadDepartmentsResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UploadForestInfoActivity extends AppCompatActivity {

    @BindView(R.id.edt_Name)
    EditText edt_Name;
    @BindView(R.id.edt_latitude)
    EditText edt_latitude;
    @BindView(R.id.edt_longitude)
    EditText edt_longitude;
    @BindView(R.id.edt_phone)
    EditText edt_phone;
    @BindView(R.id.btn_submit)
    Button btn_submit;

    private Context context;
    private SweetAlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_forest_info);
        context = UploadForestInfoActivity.this;
        ButterKnife.bind(this);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, UploadAgricultureInfoActivity.class);
                startActivity(intent);

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

    }

    private void validation() {
        if (!P.isValidEditText(edt_Name, "Name")) return;
        if (!P.isValidEditText(edt_latitude, "latitude")) return;
        if (!P.isValidEditText(edt_longitude, "longitude")) return;
        if (!P.isValidEditText(edt_phone, "Phone")) return;
        Register();
    }
    private void Register() {

        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        UploadDepartmentsInput uploadDepartmentsInput = new UploadDepartmentsInput(
                "Forest",
                edt_Name.getText().toString().trim(),
                edt_latitude.getText().toString().trim(),
                edt_longitude.getText().toString().trim(),
                edt_phone.getText().toString().trim()

        );
        dialog = P.showBufferDialog(context, "Processing...");
        // btn_Submit.setProgress(1);
        btn_submit.setEnabled(false);
        P.hideSoftKeyboard(UploadForestInfoActivity.this);

        //CALL NOW

        webServices.uploadDepartments(uploadDepartmentsInput)
                .enqueue(new Callback<UploadDepartmentsResult>() {
                    @Override
                    public void onResponse(Call<UploadDepartmentsResult> call, Response<UploadDepartmentsResult> response) {
                        if (dialog.isShowing()) dialog.dismiss();
                        if (!P.analyseResponse(response)) {
                            // btn_Submit.setProgress(0);
                            btn_submit.setEnabled(true);
                            P.ShowErrorDialogAndBeHere(context, getString(R.string.error), getString(R.string.server_error));
                            return;
                        }
                        UploadDepartmentsResult result = response.body();
                        if (result.is_success) {
                            // btn_Submit.setProgress(100);
                            P.ShowSuccessDialog(context, getString(R.string.Success), result.msg);
                            Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();
                         /* Intent intent = new Intent(context, UserLoginActivity.class);
                            startActivity(intent);
                            finish();*/
                        } else {
                            //  btn_Submit.setProgress(0);
                            btn_submit.setEnabled(true);
                            P.ShowErrorDialogAndBeHere(context, getString(R.string.error), result.msg);
                        }

                    }

                    @Override
                    public void onFailure(Call<UploadDepartmentsResult> call, Throwable t) {
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();
                        if (dialog.isShowing()) dialog.dismiss();
                        //  btn_Submit.setProgress(0);
                        btn_submit.setEnabled(true);
                        P.ShowErrorDialogAndBeHere(context, getString(R.string.error), getString(R.string.check_internet_connection));

                    }
                });
    }
}