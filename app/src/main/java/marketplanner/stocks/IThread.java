package marketplanner.stocks;

import android.os.Looper;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class IThread
{
    private static Map<Long, WeakReference<ICancelable>> CancelTask =
            new HashMap<Long, WeakReference<ICancelable>>();

    protected Thread thread_;
    protected Lock lock_ = new ReentrantLock();
    protected Condition cv_;
    protected boolean shutdown_ = false;

    public IThread() {
        cv_ = lock_.newCondition();
        thread_ = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processThreadActions();
                } catch (InterruptedException e) {
                }
            }
        });
    }

    public IThread(Runnable runnable) {
        cv_ = lock_.newCondition();
        thread_ = new Thread(runnable);
        thread_.start();
    }

    protected void processThreadActions() throws InterruptedException {
    }

    public void shutdown() {
        lock_.lock();
        try {
            shutdown_ = true;
            interrupt();
        } finally {
            lock_.unlock();
        }

        CancelTask.remove(thread_.getId());

        try {
            thread_.join();
        } catch (InterruptedException e) {
        }
    }

    private void interrupt() {
        synchronized (CancelTask) {
            WeakReference<ICancelable> task_ref = CancelTask.get(thread_.getId());
            if (task_ref != null) {
                ICancelable task = task_ref.get();
                if (task != null)
                    task.onCancel();
            }
            CancelTask.remove(thread_.getId());
        }
        thread_.interrupt();
    }

    public static void addCancelTask(ICancelable task) {
        // Ignore the UI thread.
        if (Looper.getMainLooper().isCurrentThread())
            return;

        Thread current = Thread.currentThread();
        synchronized (CancelTask) {
            CancelTask.put(current.getId(), new WeakReference<ICancelable>(task));
        }
    }

    public static void removeCancelTask(ICancelable task) {
        // Ignore the UI thread.
        if (Looper.getMainLooper().isCurrentThread())
            return;

        Thread current = Thread.currentThread();
        synchronized (CancelTask) {
            CancelTask.remove(current.getId());
        }
    }
}
