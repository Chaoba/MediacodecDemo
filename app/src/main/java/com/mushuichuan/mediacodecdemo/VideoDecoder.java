package com.mushuichuan.mediacodecdemo;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Environment;
import android.view.Surface;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by yanshun.li on 10/11/16.
 */

public class VideoDecoder {
    private static final String SAMPLE = Environment.getExternalStorageDirectory() + "/DCIM/Camera/20161013_140201.mp4";
    private final Surface mSurface;
    private MediaExtractor mMediaExtractor;
    private MediaCodec mDecoder;

    public VideoDecoder(Surface surface) {
        mSurface = surface;
    }

    public void start() {
        mMediaExtractor = new MediaExtractor();
        try {
            mMediaExtractor.setDataSource(SAMPLE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Observable.range(0, mMediaExtractor.getTrackCount())
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        //find the video track
                        MediaFormat mediaFormat = mMediaExtractor.getTrackFormat(integer);
                        String mime = mediaFormat.getString(MediaFormat.KEY_MIME);
                        Logger.d(mime);
                        return mime.startsWith("video");
                    }
                })
                .flatMap(new Func1<Integer, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(Integer integer) {
                        //create decoder according the video track
                        MediaFormat mediaFormat = mMediaExtractor.getTrackFormat(integer);
                        String mime = mediaFormat.getString(MediaFormat.KEY_MIME);
                        Logger.d(mime);
                        mMediaExtractor.selectTrack(integer);
                        try {
                            mDecoder = MediaCodec.createDecoderByType(mime);
                            mDecoder.configure(mediaFormat, mSurface, null, 0);
                            mDecoder.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return Observable.interval(33, TimeUnit.MILLISECONDS);
                    }
                })
                .map(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long aLong) {
                        int inIndex = mDecoder.dequeueInputBuffer(10000);
                        if (inIndex >= 0) {
                            ByteBuffer buffer = mDecoder.getInputBuffer(inIndex);
                            int sampleSize = mMediaExtractor.readSampleData(buffer, 0);
                            if (sampleSize < 0) {
                                Logger.d("Input buffer eos");
                                mDecoder.queueInputBuffer(inIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                            } else {
                                mDecoder.queueInputBuffer(inIndex, 0, sampleSize, mMediaExtractor.getSampleTime(), 0);
                                mMediaExtractor.advance();
                            }
                        }

                        MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
                        int outIndex = mDecoder.dequeueOutputBuffer(info, 10000);
                        switch (outIndex) {
                            case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
                                Logger.d("INFO_OUTPUT_FORMAT_CHANGED: " + mDecoder.getOutputFormat());
                                break;
                            case MediaCodec.INFO_TRY_AGAIN_LATER:
                                Logger.d("INFO_TRY_AGAIN_LATER");
                                break;
                            default:
                                if (outIndex > 0) {
                                    mDecoder.releaseOutputBuffer(outIndex, true);
                                }
                        }

                        // All decoded frames have been rendered, we can stop playing now
                        if ((info.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                            Logger.d("DecodeActivity", "OutputBuffer BUFFER_FLAG_END_OF_STREAM");
                            return true;
                        }
                        return false;
                    }
                })
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        release();
                    }

                    @Override
                    public void onError(Throwable e) {
                        release();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            release();
                            unsubscribe();
                        }

                    }
                });


    }

    private void release() {
        mDecoder.stop();
        mDecoder.release();
        mMediaExtractor.release();
    }
}
