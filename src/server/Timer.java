package server;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import tools.FileoutputUtil;

public abstract class Timer
{
    private ScheduledThreadPoolExecutor ses;
    protected String file;
    protected String name;
    
    public void start() {
        if (this.ses != null && !this.ses.isShutdown() && !this.ses.isTerminated()) {
            return;
        }
        this.file = "Logs/Log_" + this.name + "_Except.rtf";
        final String tname = this.name + Randomizer.nextInt();
        final ThreadFactory thread = new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);
            
            @Override
            public Thread newThread(final Runnable r) {
                final Thread t = new Thread(r);
                t.setName(tname + "-Worker-" + this.threadNumber.getAndIncrement());
                return t;
            }
        };
        final ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(3, thread);
        stpe.setKeepAliveTime(10L, TimeUnit.MINUTES);
        stpe.allowCoreThreadTimeOut(true);
        stpe.setCorePoolSize(4);
        stpe.setMaximumPoolSize(8);
        stpe.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
        this.ses = stpe;
    }
    
    public void stop() {
        this.ses.shutdown();
    }
    
    public ScheduledFuture<?> register(final Runnable r, final long repeatTime, final long delay) {
        if (this.ses == null) {
            return null;
        }
        return this.ses.scheduleAtFixedRate(new LoggingSaveRunnable(r, this.file), delay, repeatTime, TimeUnit.MILLISECONDS);
    }
    
    public ScheduledFuture<?> register(final Runnable r, final long repeatTime) {
        if (this.ses == null) {
            return null;
        }
        return this.ses.scheduleAtFixedRate(new LoggingSaveRunnable(r, this.file), 0L, repeatTime, TimeUnit.MILLISECONDS);
    }
    
    public ScheduledFuture<?> schedule(final Runnable r, final long delay) {
        if (this.ses == null) {
            return null;
        }
        return this.ses.schedule(new LoggingSaveRunnable(r, this.file), delay, TimeUnit.MILLISECONDS);
    }
    
    public ScheduledFuture<?> scheduleAtTimestamp(final Runnable r, final long timestamp) {
        return this.schedule(r, timestamp - System.currentTimeMillis());
    }
    
    public static class WorldTimer extends Timer
    {
        private static WorldTimer instance;
        
        private WorldTimer() {
            this.name = "Worldtimer";
        }
        
        public static WorldTimer getInstance() {
            return WorldTimer.instance;
        }
        
        static {
            WorldTimer.instance = new WorldTimer();
        }
    }
    
    public static class MapTimer extends Timer
    {
        private static MapTimer instance;
        
        private MapTimer() {
            this.name = "Maptimer";
        }
        
        public static MapTimer getInstance() {
            return MapTimer.instance;
        }
        
        static {
            MapTimer.instance = new MapTimer();
        }
    }
    
    public static class BuffTimer extends Timer
    {
        private static BuffTimer instance;
        
        private BuffTimer() {
            this.name = "Bufftimer";
        }
        
        public static BuffTimer getInstance() {
            return BuffTimer.instance;
        }
        
        static {
            BuffTimer.instance = new BuffTimer();
        }
    }
    
    public static class EventTimer extends Timer
    {
        private static EventTimer instance;
        
        private EventTimer() {
            this.name = "Eventtimer";
        }
        
        public static EventTimer getInstance() {
            return EventTimer.instance;
        }
        
        static {
            EventTimer.instance = new EventTimer();
        }
    }
    
    public static class CloneTimer extends Timer
    {
        private static CloneTimer instance;
        
        private CloneTimer() {
            this.name = "Clonetimer";
        }
        
        public static CloneTimer getInstance() {
            return CloneTimer.instance;
        }
        
        static {
            CloneTimer.instance = new CloneTimer();
        }
    }
    
    public static class EtcTimer extends Timer
    {
        private static EtcTimer instance;
        
        private EtcTimer() {
            this.name = "Etctimer";
        }
        
        public static EtcTimer getInstance() {
            return EtcTimer.instance;
        }
        
        static {
            EtcTimer.instance = new EtcTimer();
        }
    }
    
    public static class MobTimer extends Timer
    {
        private static MobTimer instance;
        
        private MobTimer() {
            this.name = "Mobtimer";
        }
        
        public static MobTimer getInstance() {
            return MobTimer.instance;
        }
        
        static {
            MobTimer.instance = new MobTimer();
        }
    }
    
    public static class CheatTimer extends Timer
    {
        private static CheatTimer instance;
        
        private CheatTimer() {
            this.name = "Cheattimer";
        }
        
        public static CheatTimer getInstance() {
            return CheatTimer.instance;
        }
        
        static {
            CheatTimer.instance = new CheatTimer();
        }
    }
    
    public static class PingTimer extends Timer
    {
        private static PingTimer instance;
        
        private PingTimer() {
            this.name = "Pingtimer";
        }
        
        public static PingTimer getInstance() {
            return PingTimer.instance;
        }
        
        static {
            PingTimer.instance = new PingTimer();
        }
    }
    
    private static class LoggingSaveRunnable implements Runnable
    {
        Runnable r;
        String file;
        
        public LoggingSaveRunnable(final Runnable r, final String file) {
            this.r = r;
            this.file = file;
        }
        
        @Override
        public void run() {
            try {
                this.r.run();
            }
            catch (Throwable t) {
                FileoutputUtil.outputFileError(this.file, t);
            }
        }
    }
}
