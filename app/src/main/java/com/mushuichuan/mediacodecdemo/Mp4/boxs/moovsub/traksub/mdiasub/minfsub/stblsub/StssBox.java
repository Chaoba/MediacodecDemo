package com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.minfsub.stblsub;

import com.mushuichuan.mediacodecdemo.Mp4.boxs.FullBox;

/**
 * Created by yanshun.li on 10/26/16.
 */

public class StssBox extends FullBox {
    public int[] samplerNumbers;

    public StssBox(byte[] byteBuffer, int start) {
        super(byteBuffer, start);
        int entryCount = getIntFromBuffer(byteBuffer, 4);
        samplerNumbers = new int[entryCount];
        for (int i = 0; i < entryCount; i++) {
            samplerNumbers[i] += getIntFromBuffer(byteBuffer, 4);
        }
    }
}
