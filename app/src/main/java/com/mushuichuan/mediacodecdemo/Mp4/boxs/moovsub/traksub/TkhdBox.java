package com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub;

import com.mushuichuan.mediacodecdemo.Logger;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.FullBox;

import java.util.Arrays;

/**
 * Created by yanshun.li on 10/25/16.
 */

public class TkhdBox extends FullBox {
    public long creationTime;
    public long modificationTime;
    public int trackId;
    public long duration;

    public int layer;
    public int alternateGroup;
    public int volume;
    public int[] matrix = new int[9];
    public int width;
    public int height;

    public TkhdBox(byte[] byteBuffer, int start) {
        super(byteBuffer, start);
        if (version == 1) {
            creationTime = getLongFromBuffer(byteBuffer);
            modificationTime = getLongFromBuffer(byteBuffer);
            trackId = getIntFromBuffer(byteBuffer, 4);
            index += 4;
            duration = getLongFromBuffer(byteBuffer);
        } else {
            creationTime = getIntFromBuffer(byteBuffer, 4);
            modificationTime = getIntFromBuffer(byteBuffer, 4);
            trackId = getIntFromBuffer(byteBuffer, 4);
            index += 4;
            duration = getIntFromBuffer(byteBuffer, 4);
        }
        //skip reserved
        index += 8;

        layer = getIntFromBuffer(byteBuffer, 2);
        alternateGroup = getIntFromBuffer(byteBuffer, 2);
        volume = getIntFromBuffer(byteBuffer, 2);

        index += 2;
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = getIntFromBuffer(byteBuffer, 4);
        }
        width = getIntFromBuffer(byteBuffer, 4);
        height = getIntFromBuffer(byteBuffer, 4);
        Logger.i(toString());
    }

    @Override
    public String toString() {
        return "TkhdBox{" +
                "creationTime=" + creationTime +
                ", modificationTime=" + modificationTime +
                ", trackId=" + trackId +
                ", duration=" + duration +
                ", layer=" + layer +
                ", alternateGroup=" + alternateGroup +
                ", volume=" + volume +
                ", matrix=" + Arrays.toString(matrix) +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
