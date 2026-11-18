# Task 06 – Deadlock and Inter-Thread Communication

**Aim:** Implement a bounded producer-consumer buffer using `wait()`/`notifyAll()` and prevent wallet-transfer deadlock with a consistent lock order.

## Compile and run
```bash
cd Task06
javac -d out src/TokenCounter.java
java -cp out TokenCounter
```

## Result
The producer inserts tokens 1–20 into a capacity-five circular buffer. Two consumers collect ten tokens each. Their total must be 210. The wallet example always acquires account locks in ascending account-ID order, preventing circular wait.

## Correctness notes
`wait()` must be called while holding the object's monitor and inside a `while` loop because a thread can wake spuriously or another thread may consume the condition before it reacquires the lock. `notifyAll()` wakes all waiters so each can re-check its condition. `sleep()` pauses a thread without releasing an owned monitor; `wait()` releases the monitor while waiting.

The four Coffman conditions are mutual exclusion, hold-and-wait, no pre-emption and circular wait. Consistent global lock ordering removes circular wait. This project demonstrates the safe ordering rather than intentionally hanging the program.

## Alternative and complexity
An `ArrayBlockingQueue<Integer>(5)` provides a tested bounded blocking queue. `put` and `take` are O(1) for this fixed-size ring-buffer operation and the queue uses O(5) storage. `tryLock` with a timeout can avoid indefinite waiting but requires retry/failure policy and careful unlock in `finally`.
