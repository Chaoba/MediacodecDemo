package com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub;

import com.mushuichuan.mediacodecdemo.Logger;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.FullBox;

import java.util.Arrays;

/**
 * Created by yanshun.li on 10/24/16.
 */

public class MvhdBox extends FullBox {

    public long creationTime;
    public long modificationTime;
    public int timescale;
    public long duration;

    public int rate;
    public int volume;
    public int reserved = 0;
    public int[] matrix = new int[9];
    public int[] preDefined = new int[6];
    public int nextTrackId;

    public MvhdBox(byte[] byteBuffer, int start) {
        super(byteBuffer, start);
        if (version == 1) {
            creationTime = getLongFromBuffer(byteBuffer);
            modificationTime = getLongFromBuffer(byteBuffer);
            timescale = getIntFromBuffer(byteBuffer, 4);
            duration = getLongFromBuffer(byteBuffer);
        } else {
            creationTime = getIntFromBuffer(byteBuffer, 4);
            modificationTime = getIntFromBuffer(byteBuffer, 4);
            timescale = getIntFromBuffer(byteBuffer, 4);
            duration = getIntFromBuffer(byteBuffer, 4);
        }
        rate = getIntFromBuffer(byteBuffer, 4);
        volume = getIntFromBuffer(byteBuffer, 2);
        //skip reserved
        index += 10;

        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = getIntFromBuffer(byteBuffer, 4);
        }
        for (int i = 0; i < preDefined.length; i++) {
            preDefined[i] = getIntFromBuffer(byteBuffer, 4);

        }
        nextTrackId = getIntFromBuffer(byteBuffer, 4);
        Logger.i(toString());
    }

    @Override
    public String toString() {
        return "MvhdBox{" +
                "creationTime=" + creationTime +
                ", modificationTime=" + modificationTime +
                ", timescale=" + timescale +
                ", duration=" + duration +
                ", rate=" + rate +
                ", volume=" + volume +
                ", reserved=" + reserved +
                ", matrix=" + Arrays.toString(matrix) +
                ", preDefined=" + Arrays.toString(preDefined) +
                ", nextTrackId=" + nextTrackId +
                '}';
    }
}
