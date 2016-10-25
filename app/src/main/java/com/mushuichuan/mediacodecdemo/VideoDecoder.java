package com.mushuichuan.mediacodecdemo;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Build;
import android.view.Surface;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.media.MediaCodec.BUFFER_FLAG_END_OF_STREAM;

/**
 * A video mFileDecoder use Mediacodec decode video
 * Created by yanshun.li on 10/11/16.
 */

public class VideoDecoder {
    private final Surface mSurface;
    private MediaCodec mDecoder;
    private Subscriber mSubscriber;

    public VideoDecoder(Surface surface) {
        mSurface = surface;
    }

    public void config(MediaFormat mediaFormat) {
        try {
            mDecoder = MediaCodec.createDecoderByType(Config.VIDEO_MIME);
            mDecoder.configure(mediaFormat, mSurface, null, 0);
            mDecoder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void config(int width, int height, ByteBuffer csd0) {
        Logger.i("config:" + csd0.limit());
        MediaFormat format = MediaFormat.createVideoFormat(Config.VIDEO_MIME, width, height);
        format.setByteBuffer("csd-0", csd0);
        config(format);
    }

    public int dequeueInputBuffer(long timeout) {
        return mDecoder.dequeueInputBuffer(timeout);
    }

    public ByteBuffer getInputBuffer(int index) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return mDecoder.getInputBuffers()[index];
        } else {
            return mDecoder.getInputBuffer(index);
        }
    }

    /**
     * queue data to the input buffer of codec
     */
    public void queueInputBuffer(int inIndex, int offset, int size, long presentationTimeUs, int flags) {
        mDecoder.queueInputBuffer(inIndex, offset, size, presentationTimeUs, flags);
    }


    /**
     * index to render the content to the surfaceview
     */
    public void start() {
        Logger.i("index");
        if (mSubscriber != null && !mSubscriber.isUnsubscribed()) {
            mSubscriber.unsubscribe();
        }
        mSubscriber = new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                stop();
            }

            @Override
            public void onError(Throwable e) {
                stop();
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    stop();
                    unsubscribe();
                }

            }
        };

        Observable.interval(Config.INTERVAL, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long aLong) {
                        MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
                        int outIndex = mDecoder.dequeueOutputBuffer(info, 10000);
                        if (outIndex > 0) {
                            mDecoder.releaseOutputBuffer(outIndex, true);
                        }

                        if ((info.flags & BUFFER_FLAG_END_OF_STREAM) != 0) {
                            Logger.d("OutputBuffer BUFFER_FLAG_END_OF_STREAM");
                            return true;
                        }
                        return false;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .subscribe(mSubscriber);

    }

    /**
     * stop mFileDecoder
     */
    public void stop() {
        Logger.e("stop");
        if (mSubscriber != null && !mSubscriber.isUnsubscribed()) {
            mSubscriber.unsubscribe();
        }
        if (mDecoder != null) {
            mDecoder.stop();
            mDecoder.release();
        }
    }
}
