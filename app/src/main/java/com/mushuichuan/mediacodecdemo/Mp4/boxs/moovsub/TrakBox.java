package com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub;

import com.mushuichuan.mediacodecdemo.Mp4.boxs.Mp4Box;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.MdiaBox;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.TkhdBox;

/**
 * Created by yanshun.li on 10/24/16.
 */

public class TrakBox extends Mp4Box {
    public TkhdBox mTkhdBox;
    public MdiaBox mMdiaBox;

    public TrakBox(byte[] byteBuffer, int start) {
        super(byteBuffer, start);
        parseSub(byteBuffer);
    }

    @Override
    public void parseSub(byte[] byteBuffer) {
        int subStart = index;
        do {
            int size = getSize(byteBuffer);
            String type = getType(byteBuffer);
            if (size > 8) {
                if (type.equals("tkhd")) {
                    mTkhdBox = new TkhdBox(byteBuffer, subStart);
                } else if (type.equals("mdia")) {
                    mMdiaBox = new MdiaBox(byteBuffer, subStart);
                }
                subStart += size;
                index = subStart;
            } else {
                break;
            }
        } while (subStart < end);
    }
}
