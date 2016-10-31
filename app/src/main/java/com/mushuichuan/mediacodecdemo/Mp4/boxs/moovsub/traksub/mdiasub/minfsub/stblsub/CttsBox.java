package com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.minfsub.stblsub;

import com.mushuichuan.mediacodecdemo.Mp4.boxs.FullBox;

/**
 * This box provides the offset between decoding time and composition time. Since decoding time must be less than the composition time, the offsets are expressed as unsigned numbers such that CT(n) = DT(n) + CTTS(n) where CTTS(n) is the (uncompressed) table entry for sample n.
 * The composition time to sample table is optional and must only be present if DT and CT differ for any samples. Hint tracks do not use this box.
 * Created by yanshun.li on 10/26/16.
 */

public class CttsBox extends FullBox {
    private final int mEntryCount;
    public int[] sampleCount;
    public int[] sampleOffset;

    public CttsBox(byte[] byteBuffer, int start) {
        super(byteBuffer, start);
        mEntryCount = getIntFromBuffer(byteBuffer, 4);
        sampleCount = new int[mEntryCount];
        sampleOffset = new int[mEntryCount];
        for (int i = 0; i < mEntryCount; i++) {
            sampleCount[i] = getIntFromBuffer(byteBuffer, 4);
            sampleOffset[i] = getIntFromBuffer(byteBuffer, 4);
        }
    }
}
