package sec01;

import java.util.concurrent.CountDownLatch;

public class Main1 {

    private static final int MAX_PLATFORM = 1_000;
    private static final int MAX_VIRTUAL = 1_000;

    public static void main(String[] args) throws Exception{
        long i = System.currentTimeMillis();

//        virtualThreadDemo();
        platformThreadDemo3();

        System.out.println(System.currentTimeMillis() - i);
    }

    private static void platformThreadDemo() {
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                Task.ioIntensive(finalI);
            });

            thread.start();
        }
    }

    private static void platformThreadDemo2(){
        var builder = Thread.ofPlatform()
                .name("customizada", 1);
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = builder.unstarted(() -> Task.ioIntensive(j));
            thread.start();
        }
    }

    // podemos deixar threads rodando em segundo plano com as daemon threads
    private static void platformThreadDemo3() throws InterruptedException {
        var latch = new CountDownLatch(MAX_PLATFORM); // esperar por MAX_PLATFORM threads terminarem
        var builder = Thread.ofPlatform().daemon().name("daemon", 1);
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = builder.unstarted(() -> {
                Task.ioIntensive(j);
                latch.countDown();
            });
            thread.start();
        }
        latch.await();
    }


    /*
       Para criarmos uma virtual thread, podemos usar o Thread.Builder
        - virtual threads are daemon by default
        - virtual threads do not have any default name
    */
    private static void virtualThreadDemo() throws InterruptedException {
        var latch = new CountDownLatch(MAX_VIRTUAL);
        var builder = Thread.ofVirtual().name("virtual-", 1);
        for (int i = 0; i < MAX_VIRTUAL; i++) {
            int j = i;
            Thread thread = builder.unstarted(() -> {
                Task.ioIntensive(j);
                latch.countDown();
            });
            thread.start();
        }
        latch.await();
    }
}
