package com.mushuichuan.mediacodecdemo.Mp4;

import com.mushuichuan.mediacodecdemo.Logger;

/**
 * head:
 * 4 bytes size
 * 4 bytes type
 * 1 bytes version for ftyp
 * 3 bytes flags for ftyp
 * 8 bytes largesize (if siz==0)
 * 16 bytes UUIDs (if type==uuid)
 * Created by yanshun.li on 10/20/16.
 */

public class Mp4Box {
    public int headSize = 8;
    public int size;
    public String type;
    public long largeSize;
    public boolean hasSubBox = true;
    public int start;

    public Mp4Box(byte[] byteBuffer, int start) {
        this.start = start;
        size = getSize(byteBuffer, start);
        if (size == 1) {
            largeSize = Util.get64LongFromBuffer(byteBuffer, 8);
            headSize += 8;
        } else if (size == 0) {
            //box extends to the end of file
        }

        type = getType(byteBuffer, start);
        if (type.equals("uuid")) {
            headSize += 16;
        }

        Logger.i(type + " size:" + size + " start:" + start);
    }

    public void parseSub(byte[] byteBuffer) {
    }

    public String getType(byte[] byteBuffer, int start) {
        return Util.getStringFromBuffer(byteBuffer, start + 4, 4);
    }

    public int getSize(byte[] byteBuffer, int start) {
        return Util.get32IntFromBuffer(byteBuffer, start);
    }
}
