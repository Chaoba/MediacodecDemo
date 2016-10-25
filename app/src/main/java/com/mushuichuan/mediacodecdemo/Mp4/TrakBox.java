package com.mushuichuan.mediacodecdemo.Mp4;

import com.mushuichuan.mediacodecdemo.Logger;

/**
 * Created by yanshun.li on 10/24/16.
 */

public class TrakBox extends Mp4Box {
    TkhdBox mTkhdBox;
    MdiaBox mMdiaBox;

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
                    Logger.i(mTkhdBox.toString());
                } else if (type.equals("mMdiaBox")) {
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
