package com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub;

import com.mushuichuan.mediacodecdemo.Logger;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.FullBox;

/**
 * Created by yanshun.li on 10/25/16.
 */

public class HdlrBox extends FullBox {
    public int componentType;
    public int componentSubType;
    public String componentName;

    public HdlrBox(byte[] byteBuffer, int start) {
        super(byteBuffer, start);

        componentType = getIntFromBuffer(byteBuffer, 4);
        componentSubType = getIntFromBuffer(byteBuffer, 4);
        index += 12;
        componentName = getStringFromBuffer(byteBuffer, 12);
        Logger.i(toString());
    }

    @Override
    public String toString() {
        return "HdlrBox{" +
                "componentType='" + componentType + '\'' +
                ", componentSubType='" + componentSubType + '\'' +
                ", componentName='" + componentName + '\'' +
                '}';
    }
}
