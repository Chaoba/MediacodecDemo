package com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub;

import com.mushuichuan.mediacodecdemo.Mp4.boxs.Mp4Box;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.HdlrBox;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.MdhdBox;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub.MinfBox;

/**
 * Created by yanshun.li on 10/25/16.
 */

public class MdiaBox extends Mp4Box {
    public HdlrBox mHdlrBox;
    public MdhdBox mMdhdBox;
    public MinfBox mMinfBox;
    public String componentName;

    public MdiaBox(byte[] byteBuffer, int start) {
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
                if (type.equals("hdlr")) {
                    mHdlrBox = new HdlrBox(byteBuffer, subStart);
                    componentName = mHdlrBox.componentName;
                } else if (type.equals("mdhd")) {
                    mMdhdBox = new MdhdBox(byteBuffer, subStart);
                } else if (type.equals("minf")) {
                    mMinfBox = new MinfBox(byteBuffer, subStart, componentName);
                }
                subStart += size;
                index = subStart;
            } else {
                break;
            }
        } while (subStart < end);
    }
}
