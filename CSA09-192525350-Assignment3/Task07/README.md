# Task 07 – Concurrent, Fault-Tolerant Semester Result Processor

**Aim:** Design a bounded concurrent processing system using `ExecutorService`, `Callable`, `Future` and concurrent collections. A failed file must not stop other work.

## Compile and run
```bash
cd Task07
javac -d out src/ResultProcessor.java
java -cp out ResultProcessor
```

## Design
- `ScoreTask implements Callable<Result>` reads and validates one score file.
- A fixed-size `ExecutorService` limits concurrent worker threads.
- `Future<Result>` carries each result or its exception to the coordinating thread.
- `ConcurrentHashMap` stores successful results; `ConcurrentLinkedQueue` records failures.
- The coordinator checks all futures and prints a complete success/failure summary.

## Result
The sample input contains four valid scores, one corrupt score and one out-of-range score. An additional missing-file task is submitted explicitly. Each failure is isolated to its own future; other tasks continue. The successful score total is 320. The program runs with pool sizes 1, 2, 4 and 8 and records measured elapsed milliseconds. Timing varies by machine and JVM; no fixed speedup is assumed.

## Complexity and evaluation
For N files with average processing cost C, ideal parallel work is approximately O(NC/p) for p workers, plus scheduling and I/O overhead. The executor uses O(p) worker stacks rather than creating one thread per file. The result map and failure queue use O(N) space. More threads may not improve performance when file I/O, CPU cores or storage throughput become bottlenecks. Parallel streams are concise but provide less direct control over pool configuration and per-task failure reporting; one thread per file can impose high stack and scheduling overhead.

## Limitation
The demonstration uses local text files and a small sample, not a 10,000-file production dataset. Production use should add structured logging, cancellation policy, durable result storage, input schema/version checks and benchmark repetitions.
