package com.mushuichuan.mediacodecdemo.Mp4.boxs;

/**
 * Created by yanshun.li on 10/25/16.
 */

public class FullBox extends Mp4Box {
    public int version;
    public int flag;

    public FullBox(byte[] byteBuffer, int start) {
        super(byteBuffer, start);
        version = getIntFromBuffer(byteBuffer, 1);
        flag = getIntFromBuffer(byteBuffer, 3);
        headSize = 12;
    }
}
