package com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub;

import com.mushuichuan.mediacodecdemo.Mp4.boxs.Mp4Box;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.minfsub.StblBox;

/**
 * Created by yanshun.li on 10/25/16.
 */

public class MinfBox extends Mp4Box {
    StblBox mStblBox;

    public MinfBox(byte[] byteBuffer, int start) {
        super(byteBuffer, start);
    }

    @Override
    public void parseSub(byte[] byteBuffer) {
        int subStart = index;
        do {
            int size = getSize(byteBuffer);
            String type = getType(byteBuffer);
            if (size > 8) {
                if (type.equals("stbl")) {
                    mStblBox = new StblBox(byteBuffer, subStart);
                }
                subStart += size;
                index = subStart;
            } else {
                break;
            }
        } while (subStart < end);
    }
}
