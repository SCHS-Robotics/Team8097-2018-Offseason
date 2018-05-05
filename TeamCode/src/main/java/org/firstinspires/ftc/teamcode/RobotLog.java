package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RobotLog {

    private PrintStream log = null;

    private LogType type;

    boolean loggingEnabled;
    private boolean collectionStarted = false;

    private final String PATH = "/sdcard/Logs";
    private String name;

    private double lastTime = 0;
    private ElapsedTime time;

    RobotLog(ElapsedTime opmodeTime, String name, LogType type, boolean loggingEnabled) {
        this.type = type;
        this.name = name;
        this.loggingEnabled = loggingEnabled;
        this.time = opmodeTime;
    }

    String logFileName() {
        String logName, extension, prefix;
        switch (type) {
            case DATA:
                extension = ".csv";
                prefix = "DATA";
                break;
            case DEBUG:
                extension = ".log";
                prefix = "DEBUG";
                break;
            default:
                extension = ".txt";
                prefix = "LOG";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd@HH:mm", Locale.US);
        logName = PATH + "/" + prefix + "_" + name.toUpperCase() + "_" + dateFormat.format(new Date()) + extension;
        return logName;
    }

    void startCollection() {
        if (!this.collectionStarted) {
            this.collectionStarted = true;
        }
    }

    void stopCollection() {
        if (this.collectionStarted) {
            this.collectionStarted = false;
        }
    }

    void openLog() {
        try {
            log = new PrintStream(new File(logFileName()));
        } catch (FileNotFoundException e) {
            Log.e("Logger", "Could not open log file: " + logFileName());
            log = null;
        }
    }

    void closeLog() {
        if (log != null) {
            log.close();
            log = null;
        }
    }

    void addData(float pollTime, Object ... values) {
        if (this.loggingEnabled && collectionStarted) {
            try {
                double truncated = getTruncatedTime();
                if (truncated % pollTime == 0 && lastTime != truncated) {
                    StringBuilder dataString = dataFormat(truncated, values);
                    log.append(dataString).append("\n");
                    lastTime = truncated;
                }
            } catch (NullPointerException e) {
                Log.e("Logger", "Data file nonexistent!");
            }
        }
    }

    void addDbgMessage(DbgLevel lvl, String title, String message) {
        if (this.loggingEnabled) {
            StringBuilder dbgString = dbgFormat(title, message);

            switch (lvl) {
                case ERROR:
                    Log.e(name, dbgString.toString());
                    break;
                case WARN:
                    Log.e(name, dbgString.toString());
                    break;
                case INFO:
                    Log.i(name, dbgString.toString());
                    break;
                case DEBUG:
                    Log.d(name, dbgString.toString());
            }

            try {
                double truncated = getTruncatedTime();
                log.append(String.valueOf(truncated)).append("\t");
                log.append(lvl.toString()).append("\t");
                log.append(dbgString);
            } catch (NullPointerException e) {
                Log.e("Logger", "Log file nonexistent!");
            }
        }
    }

    double getTruncatedTime() {
        if (this.time == null) {
            return 0;
        }
        else {
            double seconds = this.time.seconds();
            double truncated = Math.floor(seconds * 10) / 10;
            return truncated;
        }
    }

    private StringBuilder dbgFormat(String function, String message) {
        StringBuilder formatted = new StringBuilder();
        formatted.append(function).append(": ");
        formatted.append(message).append("\n");
        return formatted;
    }

    private StringBuilder dataFormat(double time, Object ... values) {
        StringBuilder formatted = new StringBuilder();
        formatted.append(time);
        for (Object val : values) {
            formatted.append(",").append(val);
        }
        return formatted;
    }

    boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    public enum LogType {
        DATA,
        DEBUG
    }

    public enum DbgLevel {
        ERROR,
        WARN,
        INFO,
        DEBUG
    }
}
