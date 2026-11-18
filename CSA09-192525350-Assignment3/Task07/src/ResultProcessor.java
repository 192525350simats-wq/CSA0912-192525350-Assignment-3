import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class ResultProcessor {
    static class Result { final String file; final int score; Result(String f,int s){file=f;score=s;} }
    static class ScoreTask implements Callable<Result> {
        private final Path file;
        ScoreTask(Path file){this.file=file;}
        public Result call() throws Exception {
            if (!Files.exists(file)) throw new FileNotFoundException("Missing file: "+file.getFileName());
            String raw=Files.readString(file).trim();
            if (!raw.matches("\\d{1,3}")) throw new IOException("Corrupt score data in "+file.getFileName());
            int score=Integer.parseInt(raw);
            if(score<0 || score>100) throw new IOException("Score outside 0..100 in "+file.getFileName());
            return new Result(file.getFileName().toString(),score);
        }
    }
    static void runPool(Path input,int poolSize) throws Exception {
        ExecutorService pool=Executors.newFixedThreadPool(poolSize);
        ConcurrentHashMap<String,Integer> successes=new ConcurrentHashMap<>();
        ConcurrentLinkedQueue<String> failures=new ConcurrentLinkedQueue<>();
        List<Future<Result>> futures=new ArrayList<>();
        long start=System.nanoTime();
        try (var paths=Files.list(input)) {
            paths.filter(p->p.toString().endsWith(".txt")).sorted().forEach(p->futures.add(pool.submit(new ScoreTask(p))));
        }
        // Explicitly add a missing-file task to test fault isolation.
        futures.add(pool.submit(new ScoreTask(input.resolve("missing_student.txt"))));
        pool.shutdown();
        for(Future<Result> f:futures) {
            try { Result r=f.get(); successes.put(r.file,r.score); }
            catch(ExecutionException e) { failures.add(e.getCause().getMessage()); }
        }
        if(!pool.awaitTermination(10,TimeUnit.SECONDS)) pool.shutdownNow();
        long elapsed=(System.nanoTime()-start)/1_000_000;
        int total=successes.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("Pool size: "+poolSize+", success: "+successes.size()+", failure: "+failures.size()+", score total: "+total+", elapsed_ms: "+elapsed);
        for(String f:failures) System.out.println("  Failure: "+f);
        System.out.println("  Successful files: "+new TreeSet<>(successes.keySet()));
    }
    public static void main(String[] args) throws Exception {
        Path input=Paths.get("input");
        for(int p:new int[]{1,2,4,8}) runPool(input,p);
    }
}
