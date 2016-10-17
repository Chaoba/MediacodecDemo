package com.mushuichuan.mediacodecdemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaCodec;
import android.text.TextPaint;
import android.view.Surface;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yanshun.li on 10/14/16.
 */

public class SurfaceRender implements VideoEncoder.EncodeLister {
    private Surface mSurface;
    private Subscriber mSubscriber;
    private TextPaint mPaint;
    private VideoEncoder mEncoder;
    private VideoDecoder mDecoder;

    static final int OUTPUT_WIDTH = 600;
    static final int OUTPUT_HEIGHT = 450;

    public SurfaceRender(Surface surface) {
        mDecoder = new VideoDecoder(surface);
        mEncoder = new VideoEncoder();
        mEncoder.setLister(this);
    }

    public boolean isPlaying(){
        return (mSubscriber != null && !mSubscriber.isUnsubscribed());
    }

    public void start() {
        mEncoder.prepare(OUTPUT_WIDTH, OUTPUT_HEIGHT);
        mEncoder.start();
        if (mSubscriber != null && !mSubscriber.isUnsubscribed()) {
            mSubscriber.unsubscribe();
        }
        mSubscriber = new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        };
        Observable.interval(Config.INTERVAL, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, Object>() {
                    @Override
                    public Object call(Long aLong) {
                        Canvas canvas = mSurface.lockCanvas(null);
                        try {
                            onDraw(canvas);
                        } finally {
                            mSurface.unlockCanvasAndPost(canvas);
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .subscribe(mSubscriber);
    }



    void onDraw(Canvas canvas) {
        Logger.v("onDraw");
        // non-UI thread
        canvas.drawColor(Color.BLACK);

        // setting some text paint
        if (mPaint == null) {
            mPaint = new TextPaint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.WHITE);
            mPaint.setTextSize(30f);
            mPaint.setTextAlign(Paint.Align.CENTER);
        }

        canvas.drawText(String.valueOf(System.currentTimeMillis()),
                OUTPUT_WIDTH / 2,
                OUTPUT_HEIGHT / 2,
                mPaint);
    }

    public void stop() {
        Logger.i("stop");
        if (mSubscriber != null && !mSubscriber.isUnsubscribed()) {
            mSubscriber.unsubscribe();
        }
        mEncoder.stop();
        mDecoder.stop();
    }

    @Override
    public void onSurfaceCreated(Surface surface) {
        Logger.i("onSurfaceCreated");
        mSurface = surface;
    }

    @Override
    public void onSampleEncoded(MediaCodec.BufferInfo info, ByteBuffer data) {
        Logger.v("onSample encoded");
        if ((info.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) == MediaCodec.BUFFER_FLAG_CODEC_CONFIG) {
            mDecoder.config(OUTPUT_WIDTH, OUTPUT_HEIGHT, data);
            mDecoder.start();

        } else {
            int inIndex = mDecoder.dequeueInputBuffer(10000);
            if (inIndex >= 0) {
                ByteBuffer buffer = mDecoder.getInputBuffer(inIndex);
                buffer.put(data);
                if (info.size < 0) {
                    Logger.d("Input buffer eos");
                    mDecoder.queueInputBuffer(inIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                } else {
                    mDecoder.queueInputBuffer(inIndex, 0, info.size, info.presentationTimeUs, info.flags);
                }
            }
        }
    }
}
