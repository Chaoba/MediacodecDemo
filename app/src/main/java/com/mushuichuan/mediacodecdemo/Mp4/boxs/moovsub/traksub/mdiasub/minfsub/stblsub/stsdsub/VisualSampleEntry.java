package com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.minfsub.stblsub.stsdsub;

import com.mushuichuan.mediacodecdemo.Logger;

/**
 * Created by yanshun.li on 10/31/16.
 */

public class VisualSampleEntry extends SampleEntry {
    private final int mWidth;
    private final int mHeight;
    private final int mHorizeresolution;
    private final int mVertresolution;
    private final int mFrameCount;
    private final String mCompressorName;

    public VisualSampleEntry(byte[] byteBuffer, int start) {
        super(byteBuffer, start);
        index+=16;
        mWidth = getIntFromBuffer(byteBuffer, 2);
        mHeight = getIntFromBuffer(byteBuffer, 2);
        mHorizeresolution = getIntFromBuffer(byteBuffer, 4);
        mVertresolution = getIntFromBuffer(byteBuffer, 4);
        index+=4;
        mFrameCount = getIntFromBuffer(byteBuffer, 2);
        mCompressorName = getStringFromBuffer(byteBuffer, 4);
        Logger.i(toString());
    }

    @Override
    public String toString() {
        return "VisualSampleEntry{" +
                "mWidth=" + mWidth +
                ", mHeight=" + mHeight +
                ", mHorizeresolution=" + mHorizeresolution +
                ", mVertresolution=" + mVertresolution +
                ", mFrameCount=" + mFrameCount +
                ", mCompressorName='" + mCompressorName + '\'' +
                '}';
    }
}
