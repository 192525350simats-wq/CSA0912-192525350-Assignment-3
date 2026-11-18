# Programming in Java – Assignment 3 (CO3)

**Student:** Yameenullah M H  
**Register Number:** 192525350  
**Course:** Java Programming  
**Course Code:** CSA0912  
**Academic Year:** 2026  
**Java version used:** OpenJDK 21 (source is compatible with Java 17+)

## Assignment coverage
This repository implements the seven questions in the supplied Assignment 3 – CO3 PDF (50 marks):
1. ATM exception handling – exception categories, multi-catch and finally.
2. User-defined checked exceptions, propagation and exception chaining; completed TODO 1.
3. Resource safety – manual close and try-with-resources, failure logging and report output.
4. Thread creation, lifecycle, `Thread`, `Runnable`, `start`, `sleep`, `join` and `isAlive`.
5. Race conditions and synchronized booking; completed TODO 1.
6. Deadlock prevention by lock ordering and bounded producer-consumer communication; completed TODO 1 and TODO 2.
7. Fault-tolerant concurrent result processing with `ExecutorService`, `Callable`, `Future` and concurrent collections.

## Folder structure
Each `Task01` through `Task07` contains:
- `src/` – Java source code
- `input/` – sample CSV or text input data
- `output/console_output.txt` – captured program output
- `output/execution_screenshot.png` – terminal-style image of the captured run
- `README.md` – task aim, compile/run commands, result and analysis

## Requirements
Install JDK 17 or later. Check with:
```bash
java -version
javac -version
```

## Run a task
Open a terminal, enter the required task folder, then use its README commands. General pattern:
```bash
cd Task01
javac -d out src/*.java
java -cp out ATMWithdrawal
```
For Task07, run `ResultProcessor`; for Task06, run `TokenCounter`; use the exact class name shown in that task's README.

## Important submission notes
- The assignment is pair work, but both students must individually execute all tasks in their own repositories. Add the second student's details and both GitHub repository links before submission.
- Create a repository named `CSA09-192525350-Assignment3`, share it with faculty, and commit progressively (at least one commit per task). Do not make one final bulk commit.
- Replace the sample execution screenshots with screenshots captured on your own machine if your faculty requires personally captured IDE/terminal evidence. The included screenshots are generated from the actual captured console outputs in this package.
- The assignment asks for raw measurement data and graphs. The included Q7 program records measured elapsed times at pool sizes 1, 2, 4 and 8; repeat runs on your machine and preserve the raw results for a defensible comparison. Q4 scheduling order and Q5/Q6 concurrency observations can vary by runtime.
- Fill in the individual contribution record and pair-work sections honestly; do not claim measurements or work that you did not perform.

## Validation
All Java programs in this package were compiled and executed during package generation using the installed JDK. Outputs are stored in each task's `output/` folder.
