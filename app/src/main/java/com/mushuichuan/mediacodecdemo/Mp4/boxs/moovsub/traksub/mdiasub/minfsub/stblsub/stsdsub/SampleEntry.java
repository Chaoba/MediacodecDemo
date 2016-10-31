package com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.minfsub.stblsub.stsdsub;

import com.mushuichuan.mediacodecdemo.Mp4.boxs.Mp4Box;

/**
 * Created by yanshun.li on 10/31/16.
 */

public class SampleEntry extends Mp4Box {
    public int dataReferenceIndex;

    public SampleEntry(byte[] byteBuffer, int start) {
        super(byteBuffer, start);
        //skip 12 bytes reserved;
        index += 12;
        dataReferenceIndex = getIntFromBuffer(byteBuffer, 2);
    }
}
