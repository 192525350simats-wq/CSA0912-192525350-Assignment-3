import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class SeatBooking {
    private final boolean[] booked;
    private int bookingsMade = 0;
    SeatBooking(int seats) { booked = new boolean[seats]; }
    // TODO 1 completed: the check-and-update operation is protected by the object's intrinsic lock.
    public synchronized boolean book(int seat) {
        if (booked[seat]) return false;
        booked[seat] = true;
        bookingsMade++;
        return true;
    }
    public static void main(String[] args) throws InterruptedException {
        SeatBooking screen = new SeatBooking(10);
        Thread[] users = new Thread[100];
        AtomicInteger accepted = new AtomicInteger();
        CountDownLatch ready = new CountDownLatch(users.length);
        CountDownLatch start = new CountDownLatch(1);
        for (int i = 0; i < users.length; i++) {
            final int seat = i % 10;
            users[i] = new Thread(() -> {
                ready.countDown();
                try { start.await(); if (screen.book(seat)) accepted.incrementAndGet(); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            });
            users[i].start();
        }
        ready.await(); start.countDown();
        for (Thread t : users) t.join();
        System.out.println("Users attempted: 100");
        System.out.println("Successful bookings: " + accepted.get());
        System.out.println("Seats sold: " + screen.bookingsMade + " (expected 10)");
    }
}
