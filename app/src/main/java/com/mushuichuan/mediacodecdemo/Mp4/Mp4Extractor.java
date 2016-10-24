package com.mushuichuan.mediacodecdemo.Mp4;

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
        byte[] ftyps = Util.getBoxBuffer(path, "ftyp");
        ftypBox = new FtypBox(ftyps, 0);

        byte[] moovs = Util.getBoxBuffer(path, "moov");
        moovBox = new MoovBox(moovs, 0);
        moovBox.parseSub(moovs);
    }
}
