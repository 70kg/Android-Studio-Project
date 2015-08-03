package com.com.mr_wrong.Palette;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mr_wrong.androidstudioproject.R;

import butterknife.ButterKnife;

/**
 * Created by Mr_Wrong on 15/7/10.
 */
public class PaletteActivity extends Activity {


    private static int RESULT_LOAD_IMAGE = 1;

    private Bitmap mBitmap;
    private Button mButton;
    private ImageView mImageview;
    private TextView mView1, mView2, mView3, mView4, mView5, mView6;


    RadioButton mrb_1, mrb_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.palette);
        ButterKnife.inject(this);

        initviews();

        mrb_1 = (RadioButton) findViewById(R.id.rb_1);
        mrb_2 = (RadioButton) findViewById(R.id.rb_2);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case 0:
                        mrb_1.setTextColor(Color.BLUE);
                        break;
                    case 1:
                        mrb_2.setTextColor(Color.BLUE);
                        break;
                }
            }
        });


    }

    private void initviews() {
        mButton = (Button) findViewById(R.id.bt_p);
        mImageview = (ImageView) findViewById(R.id.iv_p);
        mView1 = (TextView) findViewById(R.id.v_p1);
        mView2 = (TextView) findViewById(R.id.v_p2);
        mView3 = (TextView) findViewById(R.id.v_p3);
        mView4 = (TextView) findViewById(R.id.v_p4);
        mView5 = (TextView) findViewById(R.id.v_p5);
        mView6 = (TextView) findViewById(R.id.v_p6);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();

            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            Log.e("picturePath", picturePath);
            cursor.close();
            mBitmap = BitmapFactory.decodeFile(picturePath);
            mImageview.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            Palette.generateAsync(mBitmap,
                    new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            Palette.Swatch vibrant =
                                    palette.getVibrantSwatch();
                            Palette.Swatch vibrantdark =
                                    palette.getDarkVibrantSwatch();
                            Palette.Swatch vibrantlight =
                                    palette.getLightVibrantSwatch();
                            Palette.Swatch Muted =
                                    palette.getMutedSwatch();
                            Palette.Swatch Muteddark =
                                    palette.getDarkMutedSwatch();
                            Palette.Swatch Mutedlight =
                                    palette.getLightMutedSwatch();
                            if (vibrant != null) {
                                mView1.setBackgroundColor(
                                        vibrant.getRgb());
                                mView1.setTextColor(vibrant.getTitleTextColor());
                            }
                            if (vibrantdark != null) {
                                mView2.setBackgroundColor(
                                        vibrantdark.getRgb());
                                mView2.setTextColor(vibrantdark.getTitleTextColor());

                            }
                            if (vibrantlight != null) {
                                mView3.setBackgroundColor(
                                        vibrantlight.getRgb());
                                mView3.setTextColor(vibrantlight.getTitleTextColor());

                            }
                            if (Muted != null) {
                                mView4.setBackgroundColor(
                                        Muted.getRgb());
                                mView4.setTextColor(Muted.getTitleTextColor());

                            }
                            if (Muteddark != null) {

                                mView5.setBackgroundColor(
                                        Muteddark.getRgb());
                                mView5.setTextColor(Muteddark.getTitleTextColor());

                            }
                            if (Mutedlight != null) {
                                mView6.setBackgroundColor(
                                        Mutedlight.getRgb());
                                mView6.setTextColor(Mutedlight.getTitleTextColor());

                            }


                        }
                    });
        }
    }
}
