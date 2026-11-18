import java.util.concurrent.*;

public class TokenCounter {
    private final int[] buffer = new int[5];
    private int count = 0, in = 0, out = 0;
    public synchronized void put(int token) throws InterruptedException {
        // TODO 1 completed: wait while full, insert, then notify waiting consumers/producers.
        while (count == buffer.length) wait();
        buffer[in] = token;
        in = (in + 1) % buffer.length;
        count++;
        notifyAll();
    }
    public synchronized int take() throws InterruptedException {
        // TODO 2 completed: wait while empty, remove, notify, and return the token.
        while (count == 0) wait();
        int token = buffer[out];
        out = (out + 1) % buffer.length;
        count--;
        notifyAll();
        return token;
    }
    static class Wallet {
        final int id; int balance;
        Wallet(int id, int balance) { this.id=id; this.balance=balance; }
    }
    static void transfer(Wallet from, Wallet to, int amount) {
        Wallet first = from.id < to.id ? from : to;
        Wallet second = from.id < to.id ? to : from;
        synchronized (first) {
            synchronized (second) {
                if (from.balance >= amount) { from.balance -= amount; to.balance += amount; }
            }
        }
    }
    public static void main(String[] args) throws Exception {
        TokenCounter counter = new TokenCounter();
        int[] collected = new int[2];
        Thread cook = new Thread(() -> { try { for (int t=1;t<=20;t++) counter.put(t); } catch (InterruptedException e) { Thread.currentThread().interrupt(); } });
        Thread[] students = new Thread[2];
        for (int s=0;s<2;s++) { final int id=s; students[s]=new Thread(() -> { try { for(int k=0;k<10;k++) collected[id]+=counter.take(); } catch(InterruptedException e) { Thread.currentThread().interrupt(); } }); }
        cook.start(); for(Thread t:students)t.start(); cook.join(); for(Thread t:students)t.join();
        System.out.println("Sum of tokens collected: " + (collected[0]+collected[1]) + " (expected 210)");
        Wallet a=new Wallet(1,1000), b=new Wallet(2,1000);
        Thread x=new Thread(()->transfer(a,b,100)); Thread y=new Thread(()->transfer(b,a,50));
        x.start(); y.start(); x.join(); y.join();
        System.out.println("Wallet total after concurrent transfers: Rs."+(a.balance+b.balance));
    }
}
