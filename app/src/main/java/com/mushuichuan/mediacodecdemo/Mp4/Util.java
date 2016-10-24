package com.mushuichuan.mediacodecdemo.Mp4;

import com.mushuichuan.mediacodecdemo.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yanshun.li on 10/20/16.
 */

public class Util {
    public static final int FOUR_BYTES = 4;
    public static final int EIGHT_BYTES = 8;
    public static final int BITS_PER_BYTE = 8;

    public static byte[] getBoxBuffer(String path, String type) {
        Logger.i("getBoxBuffer:" + path + " type: " + type);
        InputStream in;
        byte[] boxBuffer = new byte[0];
        try {
            in = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            return null;
        }
        try {
            while (true) {
                //read length and type
                byte[] lenTypeBuffer = new byte[FOUR_BYTES * 2];
                in.read(lenTypeBuffer);

                int length = get32IntFromBuffer(lenTypeBuffer, 0);
                String boxType = getStringFromBuffer(lenTypeBuffer, FOUR_BYTES, FOUR_BYTES);

                if (type.equals(boxType)) {
                    boxBuffer = new byte[length];
                    System.arraycopy(lenTypeBuffer, 0, boxBuffer, 0, lenTypeBuffer.length);
                    int reade = lenTypeBuffer.length;

                    do {
                        int len = in.read(boxBuffer, reade, length - reade);
                        reade += len;
                    } while (reade < length);
                    break;
                } else {
                    in.skip(length - FOUR_BYTES * 2);
                    continue;
                }

            }
        } catch (IOException e) {
            Logger.e(e);
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return boxBuffer;
    }

    public static int get32IntFromBuffer(byte[] buffer, int start) {
        int result = 0;
        if (buffer.length - start >= FOUR_BYTES) {
            for (int i = 0; i < FOUR_BYTES; i++) {
                result |= (buffer[start + i] & 0xff) << BITS_PER_BYTE * (FOUR_BYTES - i - 1);
            }
        }
        return result;
    }

    public static long get64LongFromBuffer(byte[] buffer, int start) {
        long result = 0;
        if (buffer.length - start >= FOUR_BYTES) {
            for (int i = 0; i < EIGHT_BYTES; i++) {
                result |= (buffer[start + i] & 0xff) << BITS_PER_BYTE * (EIGHT_BYTES - i - 1);
            }
        }
        return result;
    }

    /**
     * @param buffer
     * @param start
     * @param size
     * @return
     */
    public static String getStringFromBuffer(byte[] buffer, int start, int size) {
        char[] result = new char[size];
        if (buffer.length - start >= size) {
            for (int i = 0; i < size; i++) {
                result[i] = (char) (buffer[start + i] & 0xff);
            }
        }
        return String.valueOf(result);
    }

}
