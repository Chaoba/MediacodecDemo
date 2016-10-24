package com.mushuichuan.mediacodecdemo.Mp4;

import java.util.ArrayList;

/**
 * Created by yanshun.li on 10/24/16.
 */

public class MoovBox extends Mp4Box {

    public MvhdBox mvhdBox;
    public ArrayList<TrakBox> trakBox = new ArrayList<>();

    public MoovBox(byte[] byteBuffer, int start) {
        super(byteBuffer, start);
    }

    @Override
    public void parseSub(byte[] byteBuffer) {
        int subStart = start + headSize;
        long totalLength = 0;
        do {
            String type = getType(byteBuffer, subStart);
            int size = getSize(byteBuffer, subStart);
            if (size > 8) {
                if (type.equals("mvhd")) {
                    mvhdBox = new MvhdBox(byteBuffer, subStart);
                } else if (type.equals("trak")) {
                    trakBox.add(new TrakBox(byteBuffer, subStart));
                }
                subStart += size;
            } else {
                break;
            }
        } while (totalLength < start + size);
    }
}
