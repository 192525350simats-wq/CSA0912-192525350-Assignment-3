public class ScannerStations {
    static class ExtendedStation extends Thread {
        private final int station, batch;
        ExtendedStation(int station, int batch) { super("Station-"+station); this.station=station; this.batch=batch; }
        public void run() { process(station, batch); }
    }
    static class RunnableStation implements Runnable {
        private final int station, batch;
        RunnableStation(int station, int batch) { this.station=station; this.batch=batch; }
        public void run() { process(station, batch); }
    }
    static void process(int station, int batch) {
        try { Thread.sleep(40L + station * 15L); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
        System.out.println("Station " + station + " scanned " + batch + " scripts on " + Thread.currentThread().getName());
    }
    public static void main(String[] args) throws InterruptedException {
        Thread[] stations = new Thread[4];
        stations[0] = new ExtendedStation(1, 25);
        stations[1] = new Thread(new RunnableStation(2, 30), "Runnable-Station-2");
        stations[2] = new ExtendedStation(3, 20);
        stations[3] = new Thread(new RunnableStation(4, 25), "Runnable-Station-4");
        for (Thread t : stations) { t.start(); System.out.println(t.getName()+" alive after start: "+t.isAlive()); }
        for (Thread t : stations) t.join();
        System.out.println("All stations completed. Total scripts scanned: 100");
    }
}
