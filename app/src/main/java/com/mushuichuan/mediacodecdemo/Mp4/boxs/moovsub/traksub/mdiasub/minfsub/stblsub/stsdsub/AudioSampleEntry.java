package com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.minfsub.stblsub.stsdsub;

import com.mushuichuan.mediacodecdemo.Logger;

/**
 * Created by yanshun.li on 10/31/16.
 */

public class AudioSampleEntry extends SampleEntry {
    private final int mSampleRate;
    private final int mChannelCount;
    private final int mSampleSize;

    public AudioSampleEntry(byte[] byteBuffer, int start) {
        super(byteBuffer, start);
        index += 8;
        mChannelCount = getIntFromBuffer(byteBuffer, 2);
        mSampleSize = getIntFromBuffer(byteBuffer, 2);
        index += 2;
        mSampleRate = (int) ((getIntFromBuffer(byteBuffer, 4) >> 16) & 0xffff);
        Logger.i(toString());
    }

    @Override
    public String toString() {
        return "AudioSampleEntry{" +
                "mSampleRate=" + mSampleRate +
                ", mChannelCount=" + mChannelCount +
                ", mSampleSize=" + mSampleSize +
                '}';
    }
}
