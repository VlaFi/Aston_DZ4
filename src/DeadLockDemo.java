public class DeadLockDemo {

    private static final Object LOCK_A = new Object();
    private static final Object LOCK_B = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            synchronized (LOCK_A) {
                System.out.println("Поток 1: заблокирован A");

                try { Thread.sleep(100); } catch (InterruptedException ignored) {}

                synchronized (LOCK_B) {
                    System.out.println("Поток 1: заблокирован B");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (LOCK_B) {
                System.out.println("Поток 2: заблокирован B");

                try { Thread.sleep(100); } catch (InterruptedException ignored) {}

                synchronized (LOCK_A) {
                    System.out.println("Поток 2: заблокирован A");
                }
            }
        });

        t1.start();
        t2.start();
    }
}
