/*
 * See whether we can catch a ThreadDeath exception.
 *
 * Turns out we can't do it reliably, so we need to use a work-around.
 * This test demonstrates and tests the work-around.
 *
 * The work-around is the have the try/catch clause in an outer context
 * that is not executing when the stop request arrives.
 * See try { loop(); } catch ()...
 */
public class CatchDeath implements Runnable
{
    boolean alwaysTrue()	{ return (true); }

    void loop() {
	while (alwaysTrue())
	    Thread.yield();
    }

    public void run()
    {
	try {
	    loop();
	    System.out.println("CatchDeath should not be here");
	} catch(Error o) {
	    System.out.println("CD Caught " + o);
	    System.out.flush();
	    throw o;
	}
	System.out.println("Ditto, CatchDeath should not be here");
    }

    public static void main(String av[]) throws Exception
    {
	Thread t[] = new Thread[3];
	t[0] = new Thread(new CatchDeath());
	t[1] = new Thread(new CatchSyncDeath());
	t[2] = new Thread(new CatchSyncWaitDeath());

	for (int i = 0; i < t.length; i++) {
	    t[i].start();
	    Thread.sleep(500);

	    synchronized (t[i]) {
		t[i].stop();
	    }
	    Thread.sleep(500);
	}
	System.out.flush();
    }
}

class CatchSyncDeath implements Runnable
{
    boolean alwaysTrue()        { return (true); }

    void loop() {
	while (alwaysTrue())
	    Thread.yield();
    }

    public void run()
    {
	try {
	    synchronized(this) {
		loop();
	    }
	    System.out.println("CatchSyncDeath should not be here");
	} catch(Error o) {
	    System.out.println("CSD Caught " + o);
	    System.out.flush();
	    throw o;
	}
	System.out.println("Ditto, CatchSyncDeath should not be here");
    }
}

class CatchSyncWaitDeath implements Runnable
{
    void go() {
	synchronized(this) {
	    try {
		wait();
	    } catch (InterruptedException e) {
		System.out.println("Interrupted " + e);
	    }
	}
    }

    public void run()
    {
	try {
	    go();
	    System.out.println("CatchSyncWaitDeath should not be here");
	} catch(Error o) {
	    System.out.println("CSWD Caught " + o);
	    System.out.flush();
	    throw o;
	}
	System.out.println("Ditto, CatchSyncWaitDeath should not be here");
    }
}


// javac flags: -nowarn
/* Expected Output:
CD Caught java.lang.ThreadDeath
CSD Caught java.lang.ThreadDeath
CSWD Caught java.lang.ThreadDeath
*/
