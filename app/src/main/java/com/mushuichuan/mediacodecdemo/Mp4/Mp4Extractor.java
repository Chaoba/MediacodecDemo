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

    /**
     * Created by yanshun.li on 10/11/16.
     * {csd-1=java.nio.ByteArrayBuffer[position=0,limit=9,capacity=9],
     * mime=video/avc,
     * frame-rate=30,
     * rotation=90,
     * rotation-degrees=90,
     * height=1080,
     * width=1920,
     * max-input-size=1572864,
     * isDMCMMExtractor=1,
     * durationUs=3497822,
     * csd-0=java.nio.ByteArrayBuffer[position=0,limit=20,capacity=20]}
     */
    public MediaFormat getTrackFormat(int index) {
        MediaFormat mediaFormat = new MediaFormat();
//        byte[] sps = {0, 0, 0, 1, 103, 100, 0, 40, -84, 52, -59, 1, -32, 17, 31, 120, 11, 80, 16, 16, 31, 0, 0, 3, 3, -23, 0, 0, -22, 96, -108};
//        byte[] pps = {0, 0, 0, 1, 104, -18, 60, -128};
//        mediaFormat.setByteBuffer("csd-0", ByteBuffer.wrap(sps));
//        mediaFormat.setByteBuffer("csd-1", ByteBuffer.wrap(pps));
//        TrakBox trakBox = null;
//        for (TrakBox box : moovBox.trakBox) {
//            if (box.mTkhdBox.trackId == index) {
//                trakBox = box;
//                break;
//            }
//        }
//        TkhdBox tkhdBox = trakBox.mTkhdBox;
//        if (trakBox.mMdiaBox.componentName.startsWith("Video")) {
//            mediaFormat.setString(MediaFormat.KEY_MIME, MediaFormat.MIMETYPE_VIDEO_AVC);
//        } else {
//            mediaFormat.setString(MediaFormat.KEY_MIME, MediaFormat.KEY_AAC_PROFILE);
//        }
//        mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, );
        return mediaFormat;
    }

    private Map<String, Object> getTrackFormatMap(int index) {
        return null;
    }
}
