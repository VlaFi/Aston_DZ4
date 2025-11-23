public class LiveLockDemo {

    static class Door {
        private volatile Walker walker;

        public Door(Walker walker) {
            this.walker = walker;
        }

        public Walker getWalker() {
            return walker;
        }

        public void setWalker(Walker walker) {
            this.walker = walker;
        }

        public void go() {
            System.out.println(walker.name + " прошёл!");
        }
    }

    static class Walker {
        String name;
        volatile boolean isWaiting = true;

        Walker(String name) {
            this.name = name;
        }

        public void goThrough(Door door, Walker partner) {
            while (isWaiting) {
                if (door.getWalker() != this) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ignored) {}
                    continue;
                }

                if (partner.isWaiting) {
                    System.out.println(name + ": Проходите вы...");
                    door.setWalker(partner);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ignored) {}
                    continue;
                }

                door.go();
                isWaiting = false;
                System.out.println(name + " смог пройти!");
            }
        }
    }

    public static void main(String[] args) {
        Walker a = new Walker("Альфа");
        Walker b = new Walker("Бета");

        Door door = new Door(a);

        new Thread(() -> a.goThrough(door, b)).start();
        new Thread(() -> b.goThrough(door, a)).start();
    }
}