package scan;

public class Main {

    private static final String PATH = "C:\\Users\\Piotr_Walczak\\Documents";

    public static void main(String[] args) {
        ProgressAnimation anim = new ProgressAnimation();
        Thread thread = new Thread(anim);
        thread.start();
        ScanTask.scan(PATH, anim::stopAnimation);
    }
}
