package etcss.word_segment_processor.utils;

/**
 * Created with IntelliJ IDEA.
 * Project Name: etcss
 * Date Created: 2013/08/31 下午2:48
 *
 * @author Wings
 */
public class SimpleLogger {
    protected static int muteLevel;

    /**
     * 不允许外部构造实例
     */
    protected SimpleLogger() {
    }

    public static void SetMuteLevel(MuteLevel level) {
        switch (level) {
            case Verbose:
                muteLevel = -2;
                break;
            case Log:
                muteLevel = -1;
                break;
            case Message:
                muteLevel = 0;
                break;
            case Warning:
                muteLevel = 1;
                break;
            case Error:
                muteLevel = 2;
                break;
            case FatalError:
                muteLevel = 3;
                break;
            case MuteEverything:
                muteLevel = 4;
                break;
            default:
                muteLevel = 0;
                break;
        }
    }

    public static MuteLevel GetMuteLevel() {
        switch (muteLevel) {
            case -2:
                return MuteLevel.Verbose;
            case -1:
                return MuteLevel.Log;
            case 0:
                return MuteLevel.Message;
            case 1:
                return MuteLevel.Warning;
            case 2:
                return MuteLevel.Error;
            case 3:
                return MuteLevel.FatalError;
            case 4:
                return MuteLevel.MuteEverything;
            default:
                return MuteLevel.Message;
        }
    }

    public static void Log(String message) {
        Log(message, MessagePriorityLevel.Message);
    }

    public static void Log(String message, MessagePriorityLevel level) {
        switch (level) {
            case Verbose:
                if (muteLevel <= -2) {
                    System.out.println(message);
                }
                break;
            case Log:
                if (muteLevel <= -1) {
                    System.out.println(message);
                }
                break;
            case Message:
                if (muteLevel <= 0) {
                    System.out.println(message);
                }
                break;
            case Warning:
                if (muteLevel <= 1) {
                    System.err.println("Warning:" + message);
                }
                break;
            case Error:
                if (muteLevel <= 2) {
                    System.err.println("!Error:" + message);
                }
                break;
            case FatalError:
                if (muteLevel <= 3) {
                    System.err.println("!!!FATAL ERROR:" + message);
                }
                break;
            default:
                if (muteLevel <= 0) {
                    System.out.println(message);
                }
                break;
        }
    }

    public enum MessagePriorityLevel {
        Verbose,            //mute level -2
        Log,                //mute level -1
        Message,            //mute level 0
        Warning,            //mute level 1
        Error,              //mute level 2
        FatalError,         //mute level 3
    }

    public enum MuteLevel {
        Verbose,            //mute level -2
        Log,                //mute level -1
        Message,            //mute level 0
        Warning,            //mute level 1
        Error,              //mute level 2
        FatalError,         //mute level 3
        MuteEverything,     //mute level 4
    }
}
