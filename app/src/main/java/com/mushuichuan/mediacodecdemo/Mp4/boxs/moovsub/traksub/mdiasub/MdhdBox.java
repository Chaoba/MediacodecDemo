package com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.traksub.mdiasub;

import com.mushuichuan.mediacodecdemo.Logger;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.FullBox;

/**
 * Created by yanshun.li on 10/25/16.
 */

public class MdhdBox extends FullBox {
    public long creationTime;
    public long modificationTime;
    public int timescale;
    public long duration;
    public int laguage;
    public int predefined;
    public MdhdBox(byte[] byteBuffer, int start) {
        super(byteBuffer, start);
        if (version == 1) {
            creationTime = getLongFromBuffer(byteBuffer);
            modificationTime = getLongFromBuffer(byteBuffer);
            timescale = getIntFromBuffer(byteBuffer, 4);
            duration = getLongFromBuffer(byteBuffer);
        } else {
            creationTime = getIntFromBuffer(byteBuffer, 4);
            modificationTime = getIntFromBuffer(byteBuffer, 4);
            timescale = getIntFromBuffer(byteBuffer, 4);
            duration = getIntFromBuffer(byteBuffer, 4);
        }
        laguage = getIntFromBuffer(byteBuffer, 2);
        predefined = getIntFromBuffer(byteBuffer, 2);
        Logger.i(toString());
    }

    @Override
    public String toString() {
        return "MdhdBox{" +
                "creationTime=" + creationTime +
                ", modificationTime=" + modificationTime +
                ", timescale=" + timescale +
                ", duration=" + duration +
                ", laguage=" + laguage +
                ", predefined=" + predefined +
                '}';
    }
}
