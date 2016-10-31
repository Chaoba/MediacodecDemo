package com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.minfsub;

import com.mushuichuan.mediacodecdemo.Mp4.boxs.Mp4Box;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.minfsub.stblsub.Co64Box;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.minfsub.stblsub.StcoBox;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.minfsub.stblsub.StscBox;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.minfsub.stblsub.StsdBox;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.minfsub.stblsub.StssBox;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.minfsub.stblsub.StszBox;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.minfsub.stblsub.SttsBox;

/**
 * Created by yanshun.li on 10/26/16.
 */

public class StblBox extends Mp4Box {
    private final String mType;
    public StsdBox mStsdBox;
    public StssBox mStssBox;
    public SttsBox mSttsBox;
    public StszBox mStszBox;
    public StscBox mStscBox;
    public StcoBox mStcoBox;
    public Co64Box mCo64Box;

    public StblBox(byte[] byteBuffer, int start, String type) {
        super(byteBuffer, start);
        mType = type;
        parseSub(byteBuffer);
    }

    @Override
    public void parseSub(byte[] byteBuffer) {
        int subStart = index;
        do {
            int size = getSize(byteBuffer);
            String type = getType(byteBuffer);
            if (type.equals("stsd")) {
                mStsdBox = new StsdBox(byteBuffer, subStart, mType);
            } else if (type.equals("stts")) {
                mSttsBox = new SttsBox(byteBuffer, subStart);
            } else if (type.equals("stsz")) {
                mStszBox = new StszBox(byteBuffer, subStart);
            } else if (type.equals("stsc")) {
                mStscBox = new StscBox(byteBuffer, subStart);
            } else if (type.equals("stco")) {
                mStcoBox = new StcoBox(byteBuffer, subStart);
            } else if (type.equals("Co64")) {
                mCo64Box = new Co64Box(byteBuffer, subStart);
            }
            subStart += size;
            index = subStart;
        } while (subStart < end);
    }
}
