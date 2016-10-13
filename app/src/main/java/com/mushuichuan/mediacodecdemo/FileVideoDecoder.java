package com.mushuichuan.mediacodecdemo;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
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

public class FileVideoDecoder {
    private MediaExtractor mMediaExtractor;
    private VideoDecoder mDecoder;

    public FileVideoDecoder(Surface surface) {
        mDecoder = new VideoDecoder(surface);
    }

    public void start(String path) {
        mMediaExtractor = new MediaExtractor();
        try {
            mMediaExtractor.setDataSource(path);
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
                        mMediaExtractor.selectTrack(integer);
                        mDecoder.config(mediaFormat);
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
                                return true;
                            } else {
                                mDecoder.queueInputBuffer(inIndex, 0, sampleSize, mMediaExtractor.getSampleTime(), 0);
                                mMediaExtractor.advance();
                            }
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

        mDecoder.start();

    }

    private void release() {
//        mDecoder.stop();
        mMediaExtractor.release();
    }
}
