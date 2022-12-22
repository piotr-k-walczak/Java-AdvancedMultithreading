package scan;

public class ProgressAnimation implements Runnable {

    private volatile boolean keepRunning = true;
    private int anim = 0;

    @Override
    public void run() {
        while (keepRunning) {
            animate(" Scan in progress");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void animate(String line) {
        switch (anim) {
            case 1:
                System.out.print("\r[ \\ ] " + line);
                break;
            case 2:
                System.out.print("\r[ | ] " + line);
                break;
            case 3:
                System.out.print("\r[ / ] " + line);
                break;
            default:
                anim = 0;
                System.out.print("\r[ - ] " + line);
        }
        anim++;
    }

    public void stopAnimation() {
        keepRunning = false;
    }
}
