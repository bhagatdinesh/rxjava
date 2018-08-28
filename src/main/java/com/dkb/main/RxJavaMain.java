package com.dkb.main;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

public class Main {

        static class Observable1 {
        public Observable<String> get() {
            Observable<String> obs = Observable.fromArray("a", "b","c","d","e");
            return obs.subscribeOn(Schedulers.io());
        }
    }

    static class Observable2 {
        public Observable<String> get() {
            Observable<String> obs = Observable.fromArray("p", "q","r","s","t");
            return obs.subscribeOn(Schedulers.io());
        }
    }
    
    public static void main(String[] args) {
        
        // Combine two streams in to one
        
         Observable1 ob1 = new Observable1();
        Observable2 ob2 = new Observable2();
        Observable<String> obs = Observable.zip(ob1.get(), ob2.get(), (String t1, String t2) -> { return t1+t2;});

        System.out.println(obs.toList().blockingGet());
        //end
        
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        Flowable.just("Hello world")
                .subscribe(consumer);
        Observable.just(1, 2, 3, 4, 5)
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("Emitting " + integer + " item on: " + Thread.currentThread().getName());
                    }
                })
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer) throws Exception {
                        System.out.println("Processing " + integer + " item on: " + Thread.currentThread().getName());
                        return integer * 2;
                    }
                })
                .subscribeWith(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(@NonNull Integer integer) {
                        System.out.println("Consuming "  + integer + " item on: " + Thread.currentThread().getName() + "\n");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("------------ completed consumption on " + Thread.currentThread().getName() + " -------------- ");
                    }
                });

    }
}
