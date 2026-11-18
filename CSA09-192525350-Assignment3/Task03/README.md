# Task 03 – Resource Safety, Debugging and Robust Code

**Aim:** Compare manual resource closing in `finally` with Java try-with-resources.

## Compile and run
```bash
cd Task03
javac -d out src/ReportGenerator.java
java -cp out ReportGenerator
```

## Result
The supplied input deliberately references one missing store file between two valid files. Both implementations log the failure and continue to the next file. The try-with-resources form closes each opened reader automatically, including when reading throws an exception. Reports are written to `output/manual_report.txt` and `output/safe_report.txt`.

## Debugging observations
A reader opened before an exception must always be closed. A manual implementation can leak if control exits before its close path is reached or if close logic is omitted. Try-with-resources closes resources in reverse declaration order. If the body and close operation both fail, the close failure is recorded as a suppressed exception and can be inspected with `getSuppressed()`.

## Complexity
For f files containing a total of L lines, reading and writing is O(L) time. Each version processes one file at a time, so the number of simultaneously open input readers is O(1). Try-with-resources reduces the chance of a resource leak but does not replace validation, logging, recovery policy, or output-integrity checks.
