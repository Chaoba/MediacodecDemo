package com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.minfsub.stblsub;

import com.mushuichuan.mediacodecdemo.Mp4.boxs.FullBox;

/**
 * Created by yanshun.li on 10/26/16.
 */

public class StcoBox extends FullBox {
    private final int entryCount;
    public int[] chunkOffset;

    public StcoBox(byte[] byteBuffer, int start) {
        super(byteBuffer, start);
        entryCount = getIntFromBuffer(byteBuffer, 4);
        chunkOffset = new int[entryCount];
        for (int i = 0; i < entryCount; i++) {
            chunkOffset[i] = getIntFromBuffer(byteBuffer, 4);
        }
    }
}
