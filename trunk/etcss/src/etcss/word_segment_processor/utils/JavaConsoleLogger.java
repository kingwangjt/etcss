package etcss.word_segment_processor.utils;

/**
 * Created with IntelliJ IDEA.
 * Project Name: etcss
 * Date Created: 2013/08/31 下午3:41
 *
 * @author Wings
 */
public final class JavaConsoleLogger extends SimpleLogger {
    /**
     * 不允许外部构造实例
     */
    private JavaConsoleLogger() {
        super();
    }

    /**
     * 覆盖基类方法，并非Override
     */
    public static void Log(String message) {
        Log(message, MessagePriorityLevel.Message);
    }

    /**
     * 覆盖基类方法，并非Override
     */
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
}
