package com.mushuichuan.mediacodecdemo.Mp4;

/**
 * Created by yanshun.li on 10/23/16.
 */

public class Mp4Extractor {

    public Mp4Extractor() {

    }

    public void setDataSource(String path){
        byte[] ftyps = Util.getBoxBuffer(path, "ftyp");
        Mp4Box ftypBox = new FtypBox().parse(ftyps, 0, ftyps.length);

        byte[] moovs = Util.getBoxBuffer(path, "moov");
        Mp4Box moovBox = new Mp4Box().parse(moovs, 0, moovs.length);
    }
}
