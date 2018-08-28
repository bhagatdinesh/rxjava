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
            Consumer<String> onNext = new Consumer() {

                @Override
                public void accept(Object o) throws Exception {
                    System.out.println("on next:" + Thread.currentThread());
                }
            };
            Consumer<Throwable> onError = new Consumer() {

                @Override
                public void accept(Object o) throws Exception {
                    System.out.println("on error:" + Thread.currentThread());
                }
            };
            Action act = new Action() {
                @Override
                public void run() throws Exception {
                    System.out.println("On completed:" + Thread.currentThread());
                }
            };
            Consumer<Disposable> onsubscribe = new Consumer() {

                @Override
                public void accept(Object o) throws Exception {
                    System.out.println("on onsubscribe:" + Thread.currentThread());
                }
            };
            //output
            /*  on onsubscribe:Thread[main,5,main]
                on next:Thread[RxCachedThreadScheduler-1,5,main]
                on next:Thread[RxCachedThreadScheduler-1,5,main]
                on next:Thread[RxCachedThreadScheduler-1,5,main]
                on next:Thread[RxCachedThreadScheduler-1,5,main]
                on next:Thread[RxCachedThreadScheduler-1,5,main]
                On completed:Thread[RxCachedThreadScheduler-1,5,main]
                1pa
                2qb
                3rc
                4sd
                5te
            */
            Observable<String> stringObservable = obs.subscribeOn(Schedulers.io());
            stringObservable.subscribe(onNext, onError, act, onsubscribe);
            return stringObservable;
            /* change above 3 lines and check the thread observer runs on
            obs.subscribe(onNext, onError, act, onsubscribe);
            Observable<String> stringObservable = obs.subscribeOn(Schedulers.io());
            return stringObservable;
            on onsubscribe:Thread[main,5,main]
            on next:Thread[main,5,main]
            on next:Thread[main,5,main]
            on next:Thread[main,5,main]
            on next:Thread[main,5,main]
            on next:Thread[main,5,main]
            On completed:Thread[main,5,main]
            1pa
            2qb
            3rc
            4sd
            5te
            */
        }
    }
    
    static class Observable3 {
        public Observable<Long> get() {
            Observable<Long> obs = Observable.rangeLong(1, 6);
            return obs.subscribeOn(Schedulers.io());
        }
    }
        
    public static void main(String[] args) {
        
        // Combine two streams in to one using zip function
        
        Observable1 ob1 = new Observable1();
        Observable2 ob2 = new Observable2();
        Observable3 ob3 = new Observable3();
        Observable<String> str = Observable.zip(ob1.get(), ob2.get(), ob3.get(),
                                                (String t1, String t2, Long t3) -> { return t1+t2 + t3;}
                                               );

        Iterator<String> iterator = str.blockingIterable().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
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
