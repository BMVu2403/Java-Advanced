package PTIT_CNTT1_IT203B_Session06;

public class Exercise01 {

    static class TicketPool {

        private int ticketsA;
        private int ticketsB;
        private int counterA = 1;
        private int counterB = 1;

        public TicketPool(int ticketsA, int ticketsB) {
            this.ticketsA = ticketsA;
            this.ticketsB = ticketsB;
        }

        public synchronized void sellTicket(String counterName, String room) {
            try {
                if (room.equals("A")) {
                    while (ticketsA == 0) {
                        System.out.println(counterName + ": Het ve phong A, dang cho...");
                        wait();
                    }
                    System.out.println(counterName + " ban ve A-" + String.format("%03d", counterA++));
                    ticketsA--;
                } else if (room.equals("B")) {
                    while (ticketsB == 0) {
                        System.out.println(counterName + ": Het ve phong B, dang cho...");
                        wait();
                    }
                    System.out.println(counterName + " ban ve B-" + String.format("%03d", counterB++));
                    ticketsB--;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public synchronized void addTickets(String room, int amount) {
            if (room.equals("A")) {
                ticketsA += amount;
                System.out.println("Nha cung cap: Da them " + amount + " ve vao phong A");
            } else if (room.equals("B")) {
                ticketsB += amount;
                System.out.println("Nha cung cap: Da them " + amount + " ve vao phong B");
            }
            notifyAll();
        }
    }

    static class BookingCounter extends Thread {

        private TicketPool pool;
        private String counterName;
        private String room;

        public BookingCounter(String counterName, TicketPool pool, String room) {
            this.counterName = counterName;
            this.pool = pool;
            this.room = room;
        }

        @Override
        public void run() {
            while (true) {
                pool.sellTicket(counterName, room);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Supplier extends Thread {

        private TicketPool pool;

        public Supplier(TicketPool pool) {
            this.pool = pool;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(4000);
                pool.addTickets("A", 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        TicketPool pool = new TicketPool(2, 5);

        BookingCounter counter1 = new BookingCounter("Quay 1", pool, "A");
        BookingCounter counter2 = new BookingCounter("Quay 2", pool, "B");

        Supplier supplier = new Supplier(pool);

        counter1.start();
        counter2.start();
        supplier.start();
    }
}
