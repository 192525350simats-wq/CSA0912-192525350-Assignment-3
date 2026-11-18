# Task 02 – User-Defined Exceptions and Propagation

**Aim:** Model portal-specific checked exceptions, propagate them through service/storage layers, and retain the root cause through exception chaining.

## Compile and run
```bash
cd Task02
javac -d out src/StorageDemo.java
java -cp out StorageDemo
```

## Result
A normal file is saved. Oversized files become `SubmissionStorageException` while retaining `IOException("Disk quota exceeded")` as the cause. Registration and exam-window rules produce meaningful domain exceptions. The controller-level `catch (ExamException)` displays the message and cause where present.

## Chaining note
If the cause is not passed to the superclass constructor, `getCause()` returns `null`; the original low-level failure and its stack-trace relationship are lost. Checked exceptions require callers to catch or declare them. If these classes extended `RuntimeException`, `throws` declarations would not be required by the compiler, although explicit documentation and handling would still be useful.

## Complexity
Normal validation and wrapping are O(1) time and O(1) additional object space per failure, excluding stack-trace storage. Capturing a stack trace has cost proportional to the depth of the call stack; chaining retains references to the cause and its trace.
