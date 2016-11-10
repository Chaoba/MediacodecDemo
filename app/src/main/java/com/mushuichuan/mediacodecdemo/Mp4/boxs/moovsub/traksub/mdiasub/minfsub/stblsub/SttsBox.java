package com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.minfsub.stblsub;

import com.mushuichuan.mediacodecdemo.Logger;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.FullBox;

import java.util.Arrays;

/**
 * (decoding) time-to-sample
 * Created by yanshun.li on 10/26/16.
 */

public class SttsBox extends FullBox {
    private final int entryCount;
    public int[] sampleCount;
    public int[] sampleDelta;

    public SttsBox(byte[] byteBuffer, int start) {
        super(byteBuffer, start);
        entryCount = getIntFromBuffer(byteBuffer, 4);
        sampleCount = new int[entryCount];
        sampleDelta = new int[entryCount];
        for (int i = 0; i < entryCount; i++) {
            sampleCount[i] = getIntFromBuffer(byteBuffer, 4);
            sampleDelta[i] = getIntFromBuffer(byteBuffer, 4);
        }
        Logger.i(toString());
    }

    @Override
    public String toString() {
        return "SttsBox{" +
                "entryCount=" + entryCount +
                ", sampleCount=" + Arrays.toString(sampleCount) +
                ", sampleDelta=" + Arrays.toString(sampleDelta) +
                '}';
    }
}
