public class PrintAlternately {

    private static final Object LOCK = new Object();
    private static boolean printOneTurn = true;

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            while (true) {
                synchronized (LOCK) {
                    while (!printOneTurn) {
                        try { LOCK.wait(); } catch (InterruptedException ignored) {}
                    }
                    System.out.print("1");
                    printOneTurn = false;
                    LOCK.notifyAll();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
                synchronized (LOCK) {
                    while (printOneTurn) {
                        try { LOCK.wait(); } catch (InterruptedException ignored) {}
                    }
                    System.out.print("2");
                    printOneTurn = true;
                    LOCK.notifyAll();
                }
            }
        });

        t1.start();
        t2.start();
    }
}
