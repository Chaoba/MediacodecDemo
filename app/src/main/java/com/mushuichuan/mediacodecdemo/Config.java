package com.mushuichuan.mediacodecdemo;

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
public class Config {
    public static final String VIDEO_MIME = "video/avc";
    public static final int INTERVAL = 33;

    public static final  int FRAME_RATE = 30;
    public static final  int VIDEO_I_FRAME_INTERVAL = 2;
    public static final  int VIDEO_BITRATE = 3000 * 1000;
}
