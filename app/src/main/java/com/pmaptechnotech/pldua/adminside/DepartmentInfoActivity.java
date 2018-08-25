package com.pmaptechnotech.pldua.adminside;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pmaptechnotech.pldua.R;
import com.pmaptechnotech.pldua.activities.DiseaseIdentificationActivity;
import com.pmaptechnotech.pldua.activities.InformationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class DepartmentInfoActivity extends AppCompatActivity {

    @BindView(R.id.btn_agriculture)
    Button btn_agriculture;
    @BindView(R.id.btn_forest)
    Button btn_forest;
    @BindView(R.id.btn_mushroom)
    Button btn_mushroom;

    private SweetAlertDialog dialog;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_info);

        context = DepartmentInfoActivity.this;
        ButterKnife.bind(this);

        btn_agriculture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, UploadAgricultureInfoActivity.class);
                startActivity(intent);

            }
        });

        btn_forest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, UploadForestInfoActivity.class);
                startActivity(intent);

            }
        });

        btn_mushroom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, MushroomDiseasesActivity.class);
                startActivity(intent);

            }
        });

    }
}
