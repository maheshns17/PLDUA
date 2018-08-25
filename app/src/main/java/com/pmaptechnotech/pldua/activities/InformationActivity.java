package com.pmaptechnotech.pldua.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pmaptechnotech.pldua.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class InformationActivity extends AppCompatActivity {

    @BindView(R.id.btn_disease_identification)
    Button btn_disease_identification;
    @BindView(R.id.btn_agriculture_dept_info)
    Button btn_agriculture_dept_info;
    @BindView(R.id.btn_forest_dept_info)
    Button btn_forest_dept_info;

    private SweetAlertDialog dialog;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        context = InformationActivity.this;
        ButterKnife.bind(this);

        btn_disease_identification.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, DiseaseIdentificationActivity.class);
                startActivity(intent);

            }
        });

        btn_agriculture_dept_info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, ListDeptInfoActivity.class);
                intent.putExtra("dept_type","Agriculture");
                startActivity(intent);

            }
        });

        btn_forest_dept_info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, ListDeptInfoActivity.class);
                intent.putExtra("dept_type","Forest");
                startActivity(intent);

            }
        });

    }
}
