package com.mushuichuan.mediacodecdemo.Mp4;

/**
 * Created by yanshun.li on 10/23/16.
 */

public class FtypBox extends Mp4Box {
    public FtypBox(byte[] byteBuffer, int start) {
        super(byteBuffer, start);
        hasSubBox = false;
        headSize = 12;
    }
}
