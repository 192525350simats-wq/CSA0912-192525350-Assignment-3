# Task 05 – Race Conditions in a Cinema Seat-Booking System

**Aim:** Protect the check-then-book critical section so one seat cannot be sold more than once.

## Compile and run
```bash
cd Task05
javac -d out src/SeatBooking.java
java -cp out SeatBooking
```

## Result
One hundred concurrent users compete for ten seats. The synchronized `book` method makes checking and updating the seat state atomic with respect to other calls on the same object. Exactly ten successful bookings are expected; the remaining attempts are rejected.

## Analysis
Without synchronization, two threads can both observe `booked[seat] == false` before either writes `true`, causing duplicate acceptance. The critical section is the check, state update and counter increment. A synchronized method locks the entire object method; a synchronized block could restrict locking to only this critical section. Static synchronization is needed only when coordination must be shared across all instances through a class-level lock.

## Complexity
A booking attempt is O(1) average time and the seat array uses O(s) space for s seats. Under contention, waiting time may increase. An `AtomicBoolean` per seat with `compareAndSet(false, true)` offers a non-blocking atomic claim; it uses O(s) space. A lock or concurrent map also adds per-seat coordination metadata.
