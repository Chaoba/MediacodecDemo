package com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.minfsub.stblsub;

import com.mushuichuan.mediacodecdemo.Logger;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.FullBox;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.minfsub.stblsub.stsdsub.AudioSampleEntry;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.minfsub.stblsub.stsdsub.SampleEntry;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.minfsub.stblsub.stsdsub.VisualSampleEntry;

/**
 * Created by yanshun.li on 10/26/16.
 */

public class StsdBox extends FullBox {
    private final String mType;
    public int numberOfEntries;
    public SampleEntry[] entrys;

    public StsdBox(byte[] byteBuffer, int start, String type) {
        super(byteBuffer, start);
        numberOfEntries = getIntFromBuffer(byteBuffer, 4);
        entrys = new SampleEntry[numberOfEntries];
        mType = type;
        parseSub(byteBuffer);
    }

    @Override
    public void parseSub(byte[] byteBuffer) {
        int subStart = index;
        for (int i = 0; i < numberOfEntries; i++) {
            int size = getSize(byteBuffer);
            String type = getType(byteBuffer);
            Logger.i("type:" + type);
            entrys[i] = mType.startsWith("Video") ? new VisualSampleEntry(byteBuffer, subStart) : new AudioSampleEntry(byteBuffer, subStart);
            subStart += size;
        }
    }
}
