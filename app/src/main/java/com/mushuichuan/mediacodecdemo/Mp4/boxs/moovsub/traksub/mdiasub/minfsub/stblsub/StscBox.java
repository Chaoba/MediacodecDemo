package com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.minfsub.stblsub;

import com.mushuichuan.mediacodecdemo.Mp4.boxs.Mp4Box;

/**
 * Created by yanshun.li on 10/26/16.
 */

public class StscBox extends Mp4Box {
    private final int entryCount;
    public int[] firstChunk;
    public int[] samplesPerChunk;
    public int[] sampleDescriptionIndex;

    public StscBox(byte[] byteBuffer, int start) {
        super(byteBuffer, start);
        entryCount = getIntFromBuffer(byteBuffer, 4);
        samplesPerChunk = new int[entryCount];
        sampleDescriptionIndex = new int[entryCount];
        for (int i = 0; i < entryCount; i++) {
            firstChunk[i] = getIntFromBuffer(byteBuffer, 4);
            samplesPerChunk[i] = getIntFromBuffer(byteBuffer, 4);
            sampleDescriptionIndex[i] = getIntFromBuffer(byteBuffer, 4);
        }
    }
}