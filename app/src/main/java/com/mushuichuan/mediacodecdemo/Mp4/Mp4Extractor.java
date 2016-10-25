package com.mushuichuan.mediacodecdemo.Mp4;

import android.media.MediaFormat;

import com.mushuichuan.mediacodecdemo.Mp4.boxs.FtypBox;
import com.mushuichuan.mediacodecdemo.Mp4.boxs.MoovBox;

import java.util.Map;

/**
 * Created by yanshun.li on 10/23/16.
 */

public class Mp4Extractor {
    FtypBox ftypBox;
    MoovBox moovBox;

    public Mp4Extractor() {

    }

    public final int getTrackCount() {
        return moovBox.trakBox.size();
    }

    public void setDataSource(String path) {
//        byte[] ftyps = Util.getBoxBuffer(path, "ftyp");
//        ftypBox = new FtypBox(ftyps, 0);

        byte[] moovs = Util.getBoxBuffer(path, "moov");
        moovBox = new MoovBox(moovs, 0);
    }

    public MediaFormat getTrackFormat(int index) {
        MediaFormat mediaFormat = new MediaFormat();


        return mediaFormat;
    }

    private Map<String, Object> getTrackFormatMap(int index) {
        return null;
    }
}
