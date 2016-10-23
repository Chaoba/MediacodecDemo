package com.mushuichuan.mediacodecdemo.Mp4;

import com.mushuichuan.mediacodecdemo.Logger;

import java.util.ArrayList;

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
    public ArrayList<Mp4Box> subBox = new ArrayList<>();
    public boolean hasSubBox = false;

    public Mp4Box() {
    }

    public Mp4Box parse(byte[] byteBuffer, int start, int end) {
        size = end - start;
        if (size == 1) {
            largeSize = Util.get64LongFromBuffer(byteBuffer, 8);
            headSize += 8;
        } else if (size == 0) {
            //box extends to the end of file
        }
        String type = Util.getStringFromBuffer(byteBuffer, start + 4, 4);
        if (type.equals("uuid")) {
            headSize += 16;
        }
        Logger.d(type + " size:" + size);


        // there is no sub boxs
        if (!hasSubBox) {
            return this;
        }

        start += headSize;
//        String test = Util.getStringFromBuffer(byteBuffer, start, 1024);
        long totalLength = 0;
        do {
            int len = Util.get32IntFromBuffer(byteBuffer, start);
            type = Util.getStringFromBuffer(byteBuffer, start + 4, 4);
            if (len > 8) {
                subBox.add(new Mp4Box().parse(byteBuffer, start, start + len));
                totalLength += len;
                start += len;
            } else {
                break;
            }
        } while (totalLength < end);
        return this;
    }
}
