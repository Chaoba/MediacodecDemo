package com.mushuichuan.mediacodecdemo;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SurfaceView mSurfaceView;
    private static String SAMPLE = Environment.getExternalStorageDirectory() + "/DCIM/Camera/20161013_140201.mp4";
    SurfaceRender mSurfaceRender;
    FileVideoDecoder mFileDecoder;
    TextView mFilePathText;
    private Button mFileButton, mRenderButton, mChooseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mFileDecoder = new FileVideoDecoder(mSurfaceView.getHolder().getSurface());

        mSurfaceRender = new SurfaceRender(mSurfaceView.getHolder().getSurface());

        mFilePathText = (TextView) findViewById(R.id.file_path);
        mFilePathText.setText(SAMPLE);
        mFileButton = (Button) findViewById(R.id.button);
        mFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSurfaceRender.isPlaying()) {
                    mSurfaceRender.stop();
                } else {
                    mSurfaceRender.start();
                }
            }
        });

        mRenderButton = (Button) findViewById(R.id.decode_file_button);
        mRenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFileDecoder.isPlaying()) {
                    mFileDecoder.stop();
                } else {
                    mFileDecoder.start(SAMPLE);
                }

            }
        });

        mChooseButton = (Button) findViewById(R.id.decode_file_button);
        mChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                SAMPLE = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                mFilePathText.setText(SAMPLE);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
