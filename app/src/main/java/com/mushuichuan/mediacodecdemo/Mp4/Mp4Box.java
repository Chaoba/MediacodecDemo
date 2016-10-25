package com.mushuichuan.mediacodecdemo.Mp4;

import com.mushuichuan.mediacodecdemo.Logger;

import static com.mushuichuan.mediacodecdemo.Mp4.Util.BITS_PER_BYTE;

/**
 * head:
 * 4 bytes size
 * 4 bytes type
 * 1 bytes version for fullbox
 * 3 bytes flags for fullbox
 * 8 bytes largesize (if siz==0)
 * 16 bytes UUIDs (if type==uuid)
 * Created by yanshun.li on 10/20/16.
 */

public class Mp4Box {
    public int headSize = 8;
    public int size;
    public String type;
    public long largeSize;
    public boolean hasSubBox = true;
    public int index;
    public long end;

    public Mp4Box(byte[] byteBuffer, int start) {
        this.index = start;
        size = getSize(byteBuffer);
        end = start + size;
        type = getType(byteBuffer);
        if (type.equals("uuid")) {
            headSize += 16;
        }

        if (size == 1) {
            largeSize = getLongFromBuffer(byteBuffer);
            end = start + largeSize;
            headSize += 8;
        } else if (size == 0) {
            //box extends to the end of file
        }


        Logger.i(type + " size:" + size + " index:" + start);
    }

    public void parseSub(byte[] byteBuffer) {
    }

    public String getType(byte[] byteBuffer) {
        final String stringFromBuffer = getStringFromBuffer(byteBuffer, index, 4);
        index += 4;
        return stringFromBuffer;
    }

    public int getSize(byte[] byteBuffer) {
        final int intFromBuffer = Util.getIntFromBuffer(byteBuffer, index, 4);
        index += 4;
        return intFromBuffer;
    }

    public int getIntFromBuffer(byte[] buffer, int byteCount) {
        int result = 0;
        if (buffer.length - index >= byteCount) {
            for (int i = 0; i < byteCount; i++) {
                result |= (buffer[index + i] & 0xff) << BITS_PER_BYTE * (byteCount - i - 1);
            }
        }
        index += byteCount;
        return result;
    }

    public long getLongFromBuffer(byte[] buffer) {
        long result = 0;
        if (buffer.length - index >= 8) {
            for (int i = 0; i < 8; i++) {
                result |= (buffer[index + i] & 0xff) << BITS_PER_BYTE * (8 - i - 1);
            }
        }
        index += 8;
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
