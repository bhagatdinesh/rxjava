import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class BlockingNonBlockingSubscribe {
    public static void main(String[] args) {
       // blocking();
        nonBlocking();
    }

    private static void nonBlocking() {
        System.out.println("Before subscribe");
        System.out.println("Before Thread: " + Thread.currentThread());

        Observable.timer(1, TimeUnit.SECONDS, Schedulers.io())
                .concatWith(Observable.timer(1, TimeUnit.SECONDS, Schedulers.single()))
                .subscribe(t -> {
                    System.out.println("Thread: " + Thread.currentThread());
                    System.out.println("Value:  " + t);
                });


        System.out.println("After subscribe");
        System.out.println("After Thread: " + Thread.currentThread());
    }

    private static void blocking() {
        System.out.println("Before blockingSubscribe");
        System.out.println("Before Thread: " + Thread.currentThread());

        Observable.interval(1, TimeUnit.SECONDS)
                .take(5)
                .blockingSubscribe(t -> {
                    System.out.println("Thread: " + Thread.currentThread());
                    System.out.println("Value:  " + t);
                });

        System.out.println("After blockingSubscribe");
        System.out.println("After Thread: " + Thread.currentThread());
    }
}
