package com.pmaptechnotech.pldua.activities;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.pmaptechnotech.pldua.R;
import com.pmaptechnotech.pldua.adapters.DeptAdapter;
import com.pmaptechnotech.pldua.api.Api;
import com.pmaptechnotech.pldua.api.WebServices;
import com.pmaptechnotech.pldua.listview.Department;
import com.pmaptechnotech.pldua.listview.RecyclerTouchListener;
import com.pmaptechnotech.pldua.logics.P;
import com.pmaptechnotech.pldua.models.DeptListInput;
import com.pmaptechnotech.pldua.models.DeptListResult;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListDeptInfoActivity extends AppCompatActivity {

    private List<Department> departmentList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DeptAdapter mDeptAdapter;
    private Context context;
    private SweetAlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dept_info);

        context = ListDeptInfoActivity.this;
        ButterKnife.bind(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mDeptAdapter = new DeptAdapter(context, departmentList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mDeptAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Department department = departmentList.get(position);
                //Toast.makeText(getApplicationContext(), train.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        getDept();
    }

    private void getDept() {
        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        DeptListInput DeptListInput = new DeptListInput(

                getIntent().getExtras().getString("dept_type")


        );



        dialog = P.showBufferDialog(context, "Processing...");
        // btn_Submit.setProgress(1);
       // btn_register.setEnabled(false);
        P.hideSoftKeyboard(ListDeptInfoActivity.this);
        //CALL NOW
        webServices.getUserDepartments(DeptListInput)
                .enqueue(new Callback<DeptListResult>() {
                    @Override
                    public void onResponse(Call<DeptListResult> call, Response<DeptListResult> response) {
                        if (dialog.isShowing()) dialog.dismiss();
                        if (!P.analyseResponse(response)) {
                            // btn_Submit.setProgress(0);
                            //btn_register.setEnabled(true);
                            P.ShowErrorDialogAndBeHere(context, getString(R.string.error), getString(R.string.server_error));
                            return;
                        }
                        DeptListResult result = response.body();
                        if (result.is_success) {
                            departmentList = result.Departments;
                            mDeptAdapter = new DeptAdapter(context, departmentList);
                            recyclerView.setAdapter(mDeptAdapter);
                        } else {
                            //  btn_Submit.setProgress(0);
                            //btn_register.setEnabled(true);
                            P.ShowErrorDialogAndBeHere(context, getString(R.string.error), result.msg);
                        }

                    }

                    @Override
                    public void onFailure(Call<DeptListResult> call, Throwable t) {
                        P.displayNetworkErrorMessage(getApplicationContext(), null, t);
                        t.printStackTrace();
                        if (dialog.isShowing()) dialog.dismiss();
                        //  btn_Submit.setProgress(0);
                       // btn_register.setEnabled(true);
                        P.ShowErrorDialogAndBeHere(context, getString(R.string.error), getString(R.string.check_internet_connection));

                    }
                });
    }
}