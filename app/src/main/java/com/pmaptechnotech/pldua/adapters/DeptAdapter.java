package com.pmaptechnotech.pldua.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.pmaptechnotech.pldua.R;
import com.pmaptechnotech.pldua.listview.Department;
import java.util.List;

public class DeptAdapter extends RecyclerView.Adapter<DeptAdapter.MyViewHolder> {

    private List<Department> departmentList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView dep_name, dep_lat, dep_lng, dep_phone_number;
        public LinearLayout row_details;



        public MyViewHolder(View view) {
            super(view);


            dep_name = (TextView) view.findViewById(R.id.name);
            dep_lat = (TextView) view.findViewById(R.id.latitude);
            dep_lng = (TextView) view.findViewById(R.id.longitude);
            dep_phone_number = (TextView) view.findViewById(R.id.phonenumber);
            row_details = (LinearLayout) view.findViewById(R.id.row_details);
        }
    }

    public DeptAdapter(Context context, List<Department> departmentList) {
        this.departmentList = departmentList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Department Department = departmentList.get(position);
        holder.dep_name.setText(Department.getDep_name());
        holder.dep_lat.setText(Department.getDep_lat());
        holder.dep_lng.setText(Department.getDep_lng());
        holder.dep_phone_number.setText(Department.getDep_phone_number());
    }
    @Override
    public int getItemCount() {
        return departmentList.size();
    }
}
