package com.mushuichuan.mediacodecdemo.Mp4.boxs;

import com.mushuichuan.mediacodecdemo.Logger;

import java.util.Arrays;

/**
 * Created by yanshun.li on 10/23/16.
 */

public class FtypBox extends FullBox {
    public int majorBrand;
    public int minor_version;
    public int[] compatibleBrands;

    public FtypBox(byte[] byteBuffer, int start) {
        super(byteBuffer, start);
        hasSubBox = false;
        majorBrand = getIntFromBuffer(byteBuffer, 4);
        minor_version = getIntFromBuffer(byteBuffer, 4);

        int num = (size - index) / 4;
        compatibleBrands = new int[num];
        for (int i = 0; i < num; i++) {
            compatibleBrands[i] = getIntFromBuffer(byteBuffer, 4);
        }
        Logger.i(toString());
    }

    @Override
    public String toString() {
        return "FtypBox{" +
                "majorBrand=" + majorBrand +
                ", minor_version=" + minor_version +
                ", compatibleBrands=" + Arrays.toString(compatibleBrands) +
                '}';
    }
}
