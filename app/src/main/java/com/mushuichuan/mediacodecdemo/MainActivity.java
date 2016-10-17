package com.mushuichuan.mediacodecdemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    SurfaceView mSurfaceView;
    private static final String SAMPLE = Environment.getExternalStorageDirectory() + "/DCIM/Camera/20161013_140201.mp4";
    SurfaceRender mSurfaceRender;
    FileVideoDecoder mFileDecoder;
    private byte[] mBuffer;
    private Button mFileButton, mRenderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mFileDecoder = new FileVideoDecoder(mSurfaceView.getHolder().getSurface());

        mSurfaceRender = new SurfaceRender(mSurfaceView.getHolder().getSurface());

        mFileButton = (Button) findViewById(R.id.button);
        mFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSurfaceRender.isPlaying()){
                    mSurfaceRender.stop();
                }else {
                    mSurfaceRender.start();
                }
            }
        });

        mRenderButton = (Button) findViewById(R.id.decode_file_button);
        mRenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFileDecoder.isPlaying()) {
                    mFileDecoder.stop();
                }else{
                    mFileDecoder.start(SAMPLE);
                }

            }
        });

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
