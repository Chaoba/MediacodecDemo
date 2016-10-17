package com.mushuichuan.mediacodecdemo;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.view.Surface;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yanshun.li on 10/14/16.
 */

public class VideoEncoder {

    private MediaCodec mCodec;
    private Surface mSurface;
    private MediaCodec.BufferInfo mBufferInfo = new MediaCodec.BufferInfo();
    Subscriber mSubscriber;

    public void setLister(EncodeLister lister) {
        mLister = lister;
    }

    EncodeLister mLister;

    void prepare(int mWidth, int mHeight) {
        MediaFormat format = MediaFormat.createVideoFormat(Config.VIDEO_MIME, mWidth, mHeight);
        format.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
        format.setInteger(MediaFormat.KEY_BIT_RATE, Config.VIDEO_BITRATE);
        format.setInteger(MediaFormat.KEY_FRAME_RATE, Config.FRAME_RATE);
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, Config.VIDEO_I_FRAME_INTERVAL);

        try {
            mCodec = MediaCodec.createEncoderByType(Config.VIDEO_MIME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mCodec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        mSurface = mCodec.createInputSurface();
        if (mLister != null) {
            mLister.onSurfaceCreated(mSurface);
        }
        mCodec.start();
    }

    void start() {
        if (mSubscriber != null && !mSubscriber.isUnsubscribed()) {
            mSubscriber.unsubscribe();
        }
        mSubscriber = new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean aBoolean) {

            }

        };
        Observable.interval(Config.INTERVAL, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long aLong) {
                        int status = mCodec.dequeueOutputBuffer(mBufferInfo, 10000);
                        if (status >= 0) {
                            // encoded sample
                            ByteBuffer data = mCodec.getOutputBuffer(status);
                            if (data != null) {
                                final int endOfStream = mBufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM;
                                // pass to whoever listens to
                                if (endOfStream == 0 && mLister != null) {
                                    mLister.onSampleEncoded(mBufferInfo, data);
                                }
                                // releasing buffer is important
                                mCodec.releaseOutputBuffer(status, false);
                                if (endOfStream == MediaCodec.BUFFER_FLAG_END_OF_STREAM)
                                    return true;
                            }
                        }
                        return false;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .subscribe(mSubscriber);
    }

    void stop() {
        if (mSubscriber != null && !mSubscriber.isUnsubscribed()) {
            mSubscriber.unsubscribe();
        }
        if (mCodec != null) {
            mCodec.stop();
            mCodec.release();
        }
        mSurface.release();
    }

    public interface EncodeLister {
        void onSurfaceCreated(Surface surface);

        void onSampleEncoded(MediaCodec.BufferInfo info, ByteBuffer data);
    }

}
