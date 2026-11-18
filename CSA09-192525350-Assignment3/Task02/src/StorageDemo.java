import java.io.IOException;

class ExamException extends Exception {
    ExamException(String message) { super(message); }
    ExamException(String message, Throwable cause) { super(message, cause); }
}
class SubmissionStorageException extends ExamException {
    SubmissionStorageException(String message, Throwable cause) { super(message, cause); }
}
class ExamWindowClosedException extends ExamException {
    ExamWindowClosedException(String message) { super(message); }
}
class CourseRegistrationException extends ExamException {
    CourseRegistrationException(String message) { super(message); }
}
class Storage {
    void save(String fileName, long sizeKb) throws SubmissionStorageException {
        try {
            if (sizeKb > 500) throw new IOException("Disk quota exceeded");
            System.out.println("Saved " + fileName);
        } catch (IOException e) {
            // TODO 1 completed: preserve the original IOException as the cause.
            throw new SubmissionStorageException("Could not store " + fileName, e);
        }
    }
}
class ExamService {
    private final Storage storage = new Storage();
    void submit(String file, long size, boolean registered, boolean windowOpen) throws ExamException {
        if (!windowOpen) throw new ExamWindowClosedException("Exam submission window has closed");
        if (!registered) throw new CourseRegistrationException("Student is not registered for this course");
        storage.save(file, size);
    }
}
public class StorageDemo {
    public static void main(String[] args) {
        ExamService service = new ExamService();
        String[][] cases = {{"answer01.pdf","120","true","true"},{"answer02.pdf","800","true","true"},{"answer03.pdf","100","false","true"},{"answer04.pdf","100","true","false"}};
        for (String[] c : cases) {
            try {
                service.submit(c[0], Long.parseLong(c[1]), Boolean.parseBoolean(c[2]), Boolean.parseBoolean(c[3]));
            } catch (ExamException e) {
                System.out.println("Error: " + e.getMessage());
                if (e.getCause() != null) System.out.println("Cause: " + e.getCause().getMessage());
                System.out.println("Exception type: " + e.getClass().getSimpleName());
            }
        }
    }
}
