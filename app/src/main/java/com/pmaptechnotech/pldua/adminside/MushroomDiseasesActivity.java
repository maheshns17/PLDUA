package com.pmaptechnotech.pldua.adminside;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pmaptechnotech.pldua.R;
import com.pmaptechnotech.pldua.activities.DiseaseIdentificationActivity;
import com.pmaptechnotech.pldua.activities.UserLoginActivity;
import com.pmaptechnotech.pldua.activities.UserRegisterActivity;
import com.pmaptechnotech.pldua.api.Api;
import com.pmaptechnotech.pldua.api.WebServices;
import com.pmaptechnotech.pldua.logics.P;
import com.pmaptechnotech.pldua.models.MushroomInput;
import com.pmaptechnotech.pldua.models.MushroomResult;
import com.pmaptechnotech.pldua.models.UserRegisterInput;
import com.pmaptechnotech.pldua.models.UserRegisterResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MushroomDiseasesActivity extends AppCompatActivity {

    @BindView(R.id.btn_select_image)
    Button btn_select_image;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.edt_diseases_name)
    EditText edt_diseases_name;
    @BindView(R.id.edt_measures)
    EditText edt_measures;
    @BindView(R.id.btn_submit)
    Button btn_submit;

    private Context context;
    private SweetAlertDialog dialog;
    private ImageView imageview;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    Bitmap bm_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mushroom_diseases);
        ButterKnife.bind(this);
        context = MushroomDiseasesActivity.this;
        btn_select_image = (Button) findViewById(R.id.btn_select_image);
        imageview = (ImageView) findViewById(R.id.image);
        btn_select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }
    public void validation() {

        if (!P.isValidEditText(edt_diseases_name, "Diseases Name")) return;
        if (bm_image == null) {
            Toast.makeText(context, "Please Capture Image", Toast.LENGTH_LONG).show();
            if (!P.isValidEditText(edt_measures, "Disease Prevention")) return;
            return;
        }
        mushroom();
    }
    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "GALLERY.",
                "CAMERA."};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    bm_image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bm_image);
                    Toast.makeText(MushroomDiseasesActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imageview.setImageBitmap(bm_image);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MushroomDiseasesActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {
            bm_image = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(bm_image);
            saveImage(bm_image);
            Toast.makeText(MushroomDiseasesActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
    private void mushroom() {

        Retrofit retrofit = Api.getRetrofitBuilder(this);
        WebServices webServices = retrofit.create(WebServices.class);

        //PREPARE INPUT/REQUEST PARAMETERS
        MushroomInput MushroomInput = new MushroomInput(

                edt_diseases_name.getText().toString().trim(),
                P.BitmapToString(bm_image),
                edt_measures.getText().toString().trim()
        );
        dialog = P.showBufferDialog(context, "Processing...");
        // btn_Submit.setProgress(1);
        btn_submit.setEnabled(false);
        P.hideSoftKeyboard(MushroomDiseasesActivity.this);

        //CALL NOW
        webServices.uploadMushroom(MushroomInput)
                .enqueue(new Callback<MushroomResult>() {
                    @Override
                    public void onResponse(Call<MushroomResult> call, Response<MushroomResult> response) {
                        if (dialog.isShowing()) dialog.dismiss();
                        if (!P.analyseResponse(response)) {
                            // btn_Submit.setProgress(0);
                            btn_submit.setEnabled(true);
                            P.ShowErrorDialogAndBeHere(context, getString(R.string.error), getString(R.string.server_error));
                            return;
                        }
                        MushroomResult result = response.body();
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
                    public void onFailure(Call<MushroomResult> call, Throwable t) {
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
