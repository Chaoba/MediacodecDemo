package com.mushuichuan.mediacodecdemo.Mp4.boxs;

/**
 * Created by yanshun.li on 10/23/16.
 */

public class FtypBox extends FullBox {
    public FtypBox(byte[] byteBuffer, int start) {
        super(byteBuffer, start);
        hasSubBox = false;
    }
}
