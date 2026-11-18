# Task 01 – Handling Failure in a Bank ATM Simulation

**Aim:** Demonstrate exception classification and controlled recovery using `try`, ordered `catch` blocks, multi-catch, and `finally`.

## Files
- `src/ATMWithdrawal.java` – ATM withdrawal simulation and five test paths.
- `input/atm_test_cases.csv` – test data corresponding to the five scenarios.
- `output/console_output.txt` and `output/execution_screenshot.png` – captured run evidence.

## Compile and run
```bash
cd Task01
javac -d out src/ATMWithdrawal.java
java -cp out ATMWithdrawal
```

## Result
The program accepts a valid withdrawal and handles non-numeric input, negative amounts, insufficient funds, and a missing card-reader file. The `finally` block runs after every test path. Specific exception handlers preserve useful error messages; the broad `Exception` catch is intentionally avoided.

## Exception classification
`NumberFormatException` is unchecked; `InvalidWithdrawalException` and `InsufficientFundsException` are checked; `FileNotFoundException` is checked. JVM-level `Error` conditions are not treated as normal ATM input failures.

## Alternative / complexity
Pre-validation with `Scanner.hasNextInt()` and return codes avoids exception construction for expected invalid input, but requires explicit checks at each boundary. Both approaches perform O(1) work per transaction and O(1) auxiliary space. Exception creation may cost more because stack-trace information is captured; actual cost depends on JVM and runtime conditions.
