# Task 04 – Thread Creation and the Thread Life Cycle

**Aim:** Demonstrate both `Thread` subclassing and `Runnable`, along with `start()`, `sleep()`, `join()` and `isAlive()`.

## Compile and run
```bash
cd Task04
javac -d out src/ScannerStations.java
java -cp out ScannerStations
```

## Result
Four scanner-station tasks run concurrently. `join()` ensures the final total is printed after all station threads finish. The order of station completion can vary because scheduling is controlled by the JVM and operating system.

## Thread lifecycle
A thread moves through NEW, RUNNABLE, BLOCKED, WAITING/TIMED_WAITING and TERMINATED states as applicable. Calling `run()` directly is an ordinary method call on the current thread; `start()` asks the JVM to schedule a new thread. `Runnable` is usually more flexible because the task is separated from the thread object.

## Complexity
For n scripts divided evenly among p active workers, ideal processing time is approximately O(n/p) work units, ignoring startup and scheduling overhead. Each platform thread consumes stack and scheduling resources, so a bounded executor is generally preferable for a large number of tasks.
