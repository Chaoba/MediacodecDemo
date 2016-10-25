package com.mushuichuan.mediacodecdemo.Mp4;

/**
 * Created by yanshun.li on 10/25/16.
 */

public class FullBox extends Mp4Box {
    int version;
    int flag;

    public FullBox(byte[] byteBuffer, int start) {
        super(byteBuffer, start);
        version = getIntFromBuffer(byteBuffer, 1);
        flag = getIntFromBuffer(byteBuffer, 3);
        headSize = 12;
    }
}
