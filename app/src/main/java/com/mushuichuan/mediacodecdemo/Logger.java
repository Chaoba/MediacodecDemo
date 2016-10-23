package com.mushuichuan.mediacodecdemo;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

/**
 * Customized log class to show customized logcat and save logcat to files.
 */
public final class Logger {
    private static String CLASS_NAME = Logger.class.getName();
    private static PrintWriter mOutStream = null;
    private static Calendar mCalendar = Calendar.getInstance();
    private static final String V = "V/";
    private static final String D = "D/";
    private static final String I = "I/";
    private static final String W = "W/";
    private static final String E = "E/";
    private static final int SAVE_V = 4;
    private static final int SAVE_D = 3;
    private static final int SAVE_I = 2;
    private static final int SAVE_W = 1;
    private static final int SAVE_E = 0;
    private static final int NOT_SAVE = -1;
    private static int mSaveLevel = NOT_SAVE;

    /**
     * set the level of message that should be written into file.
     *
     * @param mSaveLevel
     */
    public static void setmSaveLevel(int mSaveLevel) {
        Logger.mSaveLevel = mSaveLevel;
    }

    private Logger() {
    }

    private static String getFunctionName() {

        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return null;
        }
        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }
            String className = st.getClassName();
            if (className.equals(Thread.class.getName())) {
                continue;
            }
            if (className.equals(CLASS_NAME)) {
                continue;
            }
            return st.getFileName() + "[Line: " + st.getLineNumber() + "] ";
        }
        return null;
    }

    public static void v(String tag, String message) {
        Log.v(tag, message);
        if (mSaveLevel >= SAVE_V) {
            saveToFile(tag, V + message);
        }
    }

    public static void d(String tag, String message) {
        Log.d(tag, message);
        if (mSaveLevel >= SAVE_D) {
            saveToFile(tag, D + message);
        }
    }

    public static void i(String tag, String message) {
        Log.i(tag, message);
        if (mSaveLevel >= SAVE_I) {
            saveToFile(tag, I + message);
        }
    }

    public static void w(String tag, String message) {
        Log.w(tag, message);
        if (mSaveLevel >= SAVE_W) {
            saveToFile(tag, W + message);
        }
    }

    public static void e(String tag, String message) {
        Log.e(tag, message);
        if (mSaveLevel >= SAVE_E) {
            saveToFile(tag, E + message);
        }
    }

    public static void v(String message) {
        v(getFunctionName(), message);
    }

    public static void d(String message) {
        d(getFunctionName(), message);
    }

    public static void i(String message) {
        i(getFunctionName(), message);
    }

    public static void w(String message) {
        w(getFunctionName(), message);
    }

    public static void e(Object message) {
        e(getFunctionName(), message.toString());
    }

    public static void e(String tag, String message, Throwable r) {
        Log.e(tag, message, r);
    }

    public static void v(String tag, String message, Throwable r) {
        Log.v(tag, message, r);
    }

    private static void saveToFile(String tag, String message) {
        if (message == null) {
            return;
        }
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        StringBuilder buffer = new StringBuilder();
        buffer.append("[time=")
                .append(mCalendar.get(Calendar.HOUR_OF_DAY))
                .append(":")
                .append(mCalendar.get(Calendar.MINUTE))
                .append(":")
                .append(mCalendar.get(Calendar.SECOND))
                .append("]  ");
        if (tag != null) {
            buffer.append(tag);
            if (tag.length() < 50) {
                for (int i = 0; i < 50 - tag.length(); i++) {
                    buffer.append(" ");
                }
            }
        }
        buffer.append(message);
        buffer.append("\n");
        String log = buffer.toString();

        if (mOutStream == null) {
            openLogFile();
        }
        if (mOutStream != null) {
            write(log);
        }
    }

    static void write(String s) {
        mOutStream.println(s);
        if (mOutStream.checkError()) {
            e("write error");
        }
    }

    public static void close() {
        if (mOutStream != null) {
            write("end log");
            mOutStream.close();
            mOutStream = null;
        }
    }

    private static void openLogFile() {
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        StringBuilder builder = new StringBuilder();
        builder.append(CLASS_NAME)
                .append("-")
                .append(mCalendar.get(Calendar.MONTH) + 1).append("-")
                .append(mCalendar.get(Calendar.DAY_OF_MONTH)).append("-")
                .append(mCalendar.get(Calendar.HOUR_OF_DAY)).append("-")
                .append(mCalendar.get(Calendar.MINUTE)).append(".log");

        String filename = builder.toString();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + CLASS_NAME + File.separator + filename);
            if (!file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    e("mkdirs failed!");
                    return;
                }
            }
            try {
                mOutStream = new PrintWriter(new FileWriter(file, true));
                write("Begin print log:");
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e(e);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
