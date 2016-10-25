package com.mushuichuan.mediacodecdemo.Mp4.boxs;

import com.mushuichuan.mediacodecdemo.Logger;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.MvhdBox;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.moovsub.TrakBox;

import java.util.ArrayList;

/**
 * Created by yanshun.li on 10/24/16.
 */

public class MoovBox extends Mp4Box {

    public MvhdBox mvhdBox;
    public ArrayList<TrakBox> trakBox = new ArrayList<>();

    public MoovBox(byte[] byteBuffer, int start) {
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
                if (type.equals("mvhd")) {
                    mvhdBox = new MvhdBox(byteBuffer, subStart);
                    Logger.i(mvhdBox.toString());
                } else if (type.equals("trak")) {
                    trakBox.add(new TrakBox(byteBuffer, subStart));
                }
                subStart += size;
                index = subStart;
            } else {
                break;
            }
        } while (subStart < end);
    }
}
