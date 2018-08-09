# rxjava
# demo examples

Notes:

Info links

https://github.com/ReactiveX/RxJava

http://www.grahamlea.com/2014/07/rxjava-threading-examples/

http://www.vogella.com/tutorials/RxJava/article.html

https://blog.gojekengineering.com/multi-threading-like-a-boss-in-android-with-rxjava-2-b8b7cf6eb5e2

https://dzone.com/articles/5-things-to-know-about-reactive-programming

NOTE: Following content is taken from above mentioned sites.


Reactive Manifesto
The Reactive Manifesto is an online document that lays out a high standard for applications within the software development industry. Simply put, reactive systems are:

	• Responsive – systems should respond in a timely manner
  
	• Message Driven – systems should use async message-passing between components to ensure loose coupling
  
	• Elastic – systems should stay responsive under high load
  
	• Resilient – systems should stay responsive when some components fail



What is RxJava and reactive programming?
In reactive programming the consumer reacts to the data as it comes in. This is the reason why asynchronous programming is also called reactive programming. Reactive programming allows to propagates event changes to registered observers.
Reactive Programming Is Programming With Asynchronous Data Streams
When using reactive programming, data streams are going to be the spine of your application. Events, messages, calls, and even failures are going to be conveyed by a data stream. With reactive programming, you observe these streams and react when a value is emitted.
So, in your code, you are going to create data streams of anything and from anything: click events, HTTP requests, ingested messages, availability notifications, changes on a variable, cache events, measures from a sensor, literally anything that may change or happen. This has an interesting side-effect on your application: it’s becoming inherently asynchronous.

Reactive eXtension (http://reactivex.io, a.ka. RX) is an implementation of the reactive programming principles to “compose asynchronous and event-based programs by using observable sequence”. With RX, your code creates and subscribes to data streams named Observables. While Reactive Programming is about the concepts, RX provides you an amazing toolbox. By combining the observer and iterator patterns and functional idioms, RX gives you superpowers. You have an arsenal of functions to combine, merge, filter, transform and create the data streams. The next picture illustrates the usage of RX in Java (using https://github.com/ReactiveX/RxJava).

While RX is not the only implementation of the reactive programming principles (for instance we can cite BaconJS – http://baconjs.github.io), it’s the most commonly used today. In the rest of this post, we are going to use RxJava.

Reactivex is a project which provides implementations for this concept for different programming languages. It describes itself as:
The Observer pattern done right. ReactiveX is a combination of the best ideas from the Observer pattern, the Iterator pattern, and functional programming.
RxJava is the Java implementation of this concept. RxJava is published under the Apache 2.0 license. RxJava provides Java API for asynchronous programming with observable streams.


Why Reactive Programming?

Every article ever written on RxJava starts with an obligatory “why reactive programming” section, and we won’t break the trend either. There are several benefits of taking the reactive approach of building apps in Android, but let’s talk a bit about the ones you really should care about.

No More Callback Hell

Even if you‘ve been doing Android development only for some time, you might have noticed how quickly things go out of hand with nested callbacks.

This happens when you are performing several asynchronous operations in a sequence and want individual actions to depend on the results of the previous operation. In almost no time, the code becomes super-ugly and difficult to manage.

Simple Error Handling

In the real world, when you’re performing a lot of complex, asynchronous operations, errors can happen at any place. This means you’ll need to put a lot of patchy code to handle sudden scenarios, resulting in a lot of repetitive and cumbersome code.

Super Easy Multi-Threading

We all know (and secretly admit) how difficult multi-threading can get in Java. Executing a piece of code in a background thread and getting results back in the UI thread, might sound easy, but in reality, there are a lot of tricky situations to take care of.

RxJava makes it insanely easy to perform several complex operations in any thread of your choice, maintaining proper synchronization and letting you switch threads seamlessly.

The benefits of RxJava is endless. We can talk about it for hours and bore you, instead, let’s dig deeper and start exploring the true multi-threading prowess it brings to the table.

RxJava is NOT Multi-Threaded by Default

Yes, you read it right. RxJava, by default, is not multi-threaded in any way. The definition given for RxJava on their official website is as follows:

A library for composing asynchronous and event-based programs using observable sequences for the Java VM.

From the word “asynchronous”, many tend to form a grave misconception that RxJava is multi-threaded by default. 

Yes, it does support multi-threading and offers a lot of powerful features to perform asynchronous operations with ease, but that doesn’t necessarily mean the default behavior is multi-threaded.

by default, RxJava is blocking. Everything executes entirely on the thread that the code it runs on



Build blocks for RxJava
The build blocks for RxJava code are the following:

* observables representing sources of data followed by

* One or more Operators(a set of methods for modifying and composing the data), followed by,

* subscribers (or observers) listening to the observables



An observable emits items; a subscriber consumes those items.

2.1. Observables
Observables are the sources for the data. Usually they start providing data once a subscriber starts listening. An observable may emit any number of items (including zero items). It can terminate either successfully or with an error. Sources may never terminate, for example, an observable for a button click can potentially produce an infinite stream of events.


2.2. Subscribers
A observable can have any number of subscribers. If a new item is emitted from the observable, the onNext() method is called on each subscriber. If the observable finishes its data flow successful, the onComplete() method is called on each subscriber. Similar, if the observable finishes its data flow with an error, the onError() method is called on each subscriber.


2.3 Operators
An operator is a function that takes one Observable (the source) as its first argument and returns another Observable (the destination). Then for every item that the source observable emits, it will apply a function to that item, and then emit the result on the destination Observable.

Operators can be chained together to create complex data flows that filter event based on certain criteria. Multiple operators can be applied to the same observable.

It is not difficult to get into a situation in which an Observable is emitting items faster than an operator or observer can consume them. You can read more about back-pressure here.


Why doing asynchronous programming
Reactive programming provides a simple way of asynchronous programming. This allows to simplify the asynchronously processing of potential long running operations. It also provides a defined way of handling multiple events, errors and termination of the event stream. Reactive programming provides also a simplified way of running different tasks in different threads. For example, widgets in SWT and Android have to be updated from the UI thread and reactive programming provides ways to run observables and subscribers in different threads.
It is also possible to convert the stream before its received by the observers. And you can chain operations, e.g., if a API call depends on the call of another API Last but not least, reactive programming reduces the need for state variables, which can be the source of errors.




Types of Observable
There are two types:

* Non-Blocking – asynchronous execution is supported and is allowed to unsubscribe at any point in the event stream. On this article, we’ll focus mostly on this kind of type
* Blocking – all onNext observer calls will be synchronous, and it is not possible to unsubscribe in the middle of an event stream. We can always convert an Observable into a Blocking Observable, using the method toBlocking:

	```java
	BlockingObservable<String> blockingObservable = observable.toBlocking();
	```

Observables Can Be Cold or Hot – and it Matters

At this point, you are trying to see what are the different streams (or observables) you are going to deal with in your program. But there are two classes of streams: hot and cold. Understanding the difference is key to successfully use reactive programming.

Cold observables are lazy. They don’t do anything until someone starts observing them (subscribe in RX). They only start running when they are consumed. Cold streams are used to represent asynchronous actions, for example, that it won’t be executed until someone is interested in the result. Another example would be a file download. It won’t start pulling the bytes if no one is going to do something with the data. The data produced by a cold stream is not shared among subscribers and when you subscribe you get all the items.

Hot streams are active before the subscription like a stock ticker, or data sent by a sensor or a user. The data is independent of an individual subscriber. When an observer subscribes to a hot observable, it will get all values in the stream that are emitted after it subscribes. The values are shared among all subscribers. For example, even if no one has subscribed to a thermometer, it measures and publishes the current temperature. When a subscriber registers to the stream, it automatically receives the next measure.

Why it’s so important to understand whether your streams are hot or cold? Because it changes how your code consumes the conveyed items. If you are not subscribed to a hot observable, you won’t receive the data, and this data is lost.






Obervable types,RxJava 2 features several base classes you can discover operators on:


You can create different types of observables.
Type	Description

Flowable<T>	Emits 0 or n items and terminates with an success or an error event. Supports backpressure, which allows to control how fast a source emits items.
  
Observable<T>	Emits 0 or n items and terminates with an success or an error event.
  
Single<T>	Emits either a single item or an error event. The reactive version of a method call.
  
Maybe<T>	Succeeds with an item, or no item, or errors. The reactive version of an Optional.
  
Completable	Either completes with an success or with an error event. It never emits items. The reactive version of a Runnable.


Convenience methods to create observables

RxJava provides several convenience methods to create observables

* Observable.just("Hello") - Allows to create an observable as wrapper around other data types

* Observable.fromIterable() - takes an java.lang.Iterable<T> and emits their values in their order in the data structure
  
* Observable.fromArray() - takes an array and emits their values in their order in the data structure

* Observable.fromCallable() - Allows to create an observable for a java.util.concurrent.Callable<V>
  
* Observable.fromFuture() - Allows to create an observable for a java.util.concurrent.Future

* Observable.interval() - An observable that emits Long objects in a given interval

Similar methods exists for the other data types, e.g., *Flowable.just(), Maybe.just() and Single.just.



Conversion between types
It is easy to convert between different RxJava types.
From / To	Flowable	Observable	Maybe	Single	Completable
Flowable		toObservable()	reduce()	scan()	ignoreElements()
			elementAt()	elementAt()
			firstElement()	first()/firstOrError()
			lastElement()	last()/lastOrError()
			singleElement()	single()/singleOrError()
				all()/any()/count()
				(and more…​)
Observable	toFlowable()		reduce()	scan()	ignoreElements()
			elementAt()	elementAt()
			firstElement()	first()/firstOrError()
			lastElement()	last()/lastOrError()
			singleElement()	single()/singleOrError()
				all()/any()/count()
				(and more…​)
Maybe	toFlowable()	toObservable()		toSingle()	toCompletable()
				sequenceEqual()
Single	toFlowable()	toObservable()	toMaybe()		toCompletable()
Completable	toFlowable()	toObservable()	toMaybe()	toSingle()	
				toSingleDefault()


avoid problems down the line such as MissingBackpressureException or OutOfMemoryError.
When to use Observable

	• You have a flow of no more than 1000 elements at its longest: i.e., you have so few elements over time that there is practically no chance for OOME in your application.
  
	• You deal with GUI events such as mouse moves or touch events: these can rarely be backpressured reasonably and aren't that frequent. You may be able to handle an element frequency of 1000 Hz or less with Observable but consider using sampling/debouncing anyway.
  
	• Your flow is essentially synchronous but your platform doesn't support Java Streams or you miss features from it. Using Observable has lower overhead in general than Flowable. (You could also consider IxJava which is optimized for Iterable flows supporting Java 6+).
  
When to use Flowable

	• Dealing with 10k+ of elements that are generated in some fashion somewhere and thus the chain can tell the source to limit the amount it generates.
  
	• Reading (parsing) files from disk is inherently blocking and pull-based which works well with backpressure as you control, for example, how many lines you read from this for a specified request amount).
  
	• Reading from a database through JDBC is also blocking and pull-based and is controlled by you by calling ResultSet.next() for likely each downstream request.
  
	• Network (Streaming) IO where either the network helps or the protocol used supports requesting some logical amount.
  
	• Many blocking and/or pull-based data sources which may eventually get a non-blocking reactive API/driver in the future.



OnNext, OnError, and OnCompleted
There are three methods on the observer interface that we want to know about:

	1. OnNext is called on our observer each time a new event is published to the attached Observable. This is the method where we’ll perform some action on each event
  
	2. OnCompleted is called when the sequence of events associated with an Observable is complete, indicating that we should not expect any more onNext calls on our observer
  
	3. OnError is called when an unhandled exception is thrown during the RxJava framework code or our event handling code
  
The return value for the Observables subscribe method is a subscribe interface:

```java
String[] letters = {"a", "b", "c", "d", "e", "f", "g"};

Observable<String> observable = Observable.from(letters);
  
observable.subscribe(
	  i -> result += i,  //OnNext
	  Throwable::printStackTrace, //OnError
	  () -> result += "_Completed" //OnCompleted
	);
	assertTrue(result.equals("abcdefg_Completed"));

```
 Let’s Do Some Simple Multi-Threading 
 
If you want to do some basic multi-threading in Android using RxJava, all you need to do is have a bit of familiarity with the Schedulers and the observeOn/subscribeOn operators and you are good to go.
Now, let us have a look at one of the simplest multi-threading use cases first. Suppose, we want to fetch a list of Book objects from the network and show them as a list in the UI thread of our application. A pretty common and straight-forward use case to start with.
Here, we have a getBooks() method that makes a network call and fetches a list of books for us. Network calls take time (a few milliseconds to a few seconds) because of which we are using subscribeOn() and specifying the Schedulers.io() Scheduler to perform the operation in the I/O thread.
We are also using the observeOn() operator along with the AndroidSchedulers.mainThread() Scheduler to consume the result in the main thread and to populate the books in the UI of our application. This is something you might already know or have done before.
Don’t worry, we’ll get into more advanced stuff soon. This was just to ensure we’re all on the same page and have the basic idea fleshed out before diving deeper.
Befriending The Schedulers
The main threading game in RxJava starts with the powerful set of Schedulers it provides. In RxJava, you cannot directly access or manipulate threads. If you want to play with threads, you have to do it through the in-built Schedulers.
You can think of Schedulers as threads or thread pools (a collection of threads) to perform different kinds of jobs.
In simple words, if you need to execute a task in a particular thread, you need to pick the right Scheduler for it, which will then take a thread from its pool of available threads and get the task executed.
There are several types of Schedulers available in the RxJava framework, but the tricky part is to choose the right Scheduler for the right kind of job. Your task will never run optimally if you don’t pick the right one. So, let’s try and understand each of them -
Schedulers.io()
It’s backed by an unbounded thread pool and is used for non-CPU intensive I/O work like accessing the file system, performing network calls, accessing the database, etc. This Scheduler is uncapped and the size of its thread pool can grow as needed.
Schedulers.computation()
This Scheduler is used for performing CPU-intensive work like processing large data sets, image handling, etc. It is backed by a bounded thread poolwith size up to the number of processors available.
As this Scheduler is suitable for only CPU intensive tasks, we want to limit the number of threads so they don’t fight among each other over CPU time and starve themselves.
Schedulers.newThread()
This Scheduler creates a completely new thread to perform a unit of work every time it’s used. It doesn’t benefit itself by making use of any thread pool. Threads are expensive to create and tear down, so you should be pretty careful of not abusing excessive thread spawning leading to severe system slowdowns and out of memory errors.
Ideally, you would use this Scheduler quite rarely, mostly for kicking off long-running, isolated units of work in a completely separate thread.
Schedulers.single()
This Scheduler is newly introduced in RxJava 2 and is backed by a single thread which can only be used to perform tasks in a sequential manner. This can be highly useful when you have a set of background jobs to perform from different places in your app but can’t afford to execute more than one at a time.
Schedulers.from(Executor executor)
You can use this to create a custom Scheduler backed by your own Executor. There can be several use cases where you might need to create a custom Scheduler to perform specific tasks for your app that demands custom threading logic.
Suppose you want to limit the number of parallel network calls happening in your app, then you can create a custom Scheduler with an executor of fixed thread pool size, Scheduler.from(Executors.newFixedThreadPool(n)) and use it on all network-related Observables in your code.
AndroidSchedulers.mainThread()
This is a special Scheduler that’s not available in the core RxJava library. You need to use the RxAndroid extension library to make use of it. This scheduler is specifically useful for Android apps to perform UI based tasks in the main thread of the application.
By default, it enqueues tasks in the looper associated with the main thread of the application, but there are other variations of it that allows us to use any Looper we want via APIs like this, AndroidSchedulers.from(Looper looper).
Note: Be careful while using Schedulers backed by unbounded thread pools like Schedulers.io(), as there always lies a risk of infinitely growing the thread pool and flooding the system with too many threads.
Understanding subscribeOn() & observeOn()
Now, as you have a clear understanding of the different types of Schedulers and when should you use what, let us move ahead in understanding and exploring the subscribeOn() and the observeOn() operators in detail.
You should have a deep understanding of how these two operators work individually and when combined together, to master the true multi-threading capabilities of RxJava.
subscribeOn()
In simple words, this operator tells which thread the source observable can emit its items on. You should understand the importance of the word “source” observable here. When you have a chain of observables, the source observable is always at the root or top of the chain from where the emissions originate.

As you have already seen; if we don’t use subscribeOn(), all the emissions happen directly on the thread the code is executed on (in our case, the mainthread).

Now let us direct all the emissions to the computation thread using subscribeOn() along with the Schedulers.computation() Scheduler. Once you run the code snippet below, you will notice all the emissions taking place in one of the computation threads available in the thread pool, RxComputationThreadPool-1.

For brevity purposes, we have not used the full DisposableSubscriber as we don’t need to handle the onError() and onComplete() every time in these simple scenarios. To handle just the onNext(), a single consumer is enough.

It doesn’t really matter where you put the subscribeOn() method in the chain. It only acts on the source observable and controls which thread it emits its items on.

In the example below, you will notice that there are other observables in the chain created by the map() and the filter() operators and subscribeOn()has been placed at the bottom of the chain. But once you run the snippet below, you will notice that it only affects the source observable. This would be more clear once we mix the chain with observeOn() as well. Even if we place subscribeOn() below observeOn(), it will only affect the source observable.
It is also important to understand that you cannot use subscribeOn()multiple times in your chain. Technically, you can do so, but that won’t have any additional effect. In the snippet below we are chaining three different Schedulers ,but can you guess which Scheduler will the source observable emit its items on?
If your guess is Schedulers.io(), then bingo! That was a tough guess though, and for that, you will get a five-minute hug the next time we meet. :-P
Even if you put multiple subscribeOn() operators in your chain, only the one closed to the source observable will take its effect and nothing else.
Under The Hood
It’s worth spending some time understanding the above scenario in depth. Why does the Schedulers.io() Scheduler take its effect and not the other ones? Normally, you would think that the Schedulers.newThread() should have taken effect as it was applied last in the chain.
You have to understand that in RxJava, subscriptions are always made with upstream observable instances. The snippet below is very similar to what we have already seen before, just a bit more verbose.
Let’s understand this starting from the last line of the snippet. Here, the target subscriber (or the most downstream observer in the chain) invokes the subscribe() method on observable o3 which then makes an implicit subscribe() call to its immediate upstream observable o2. The observer implementation provided by the o3 multiplies the emitted numbers by 10.
The process repeats, and o2 implicitly calls subscribe on o1 passing an observer implementation that only lets even numbers to pass. Now we have reached the root where the source observable o1 doesn’t have any upstream observable to call subscribe on. This actually completes the observable chain making the source observable emit its items.
This should clear our concept on how subscriptions work in RxJava. By now, you should have a mental model of how observable chains are formed and how events are propagated down the chain, starting from the source observable.
observeOn()
As we’v seen, subscribeOn() instructs the source observable to emit its items on a particular thread, and this is the thread responsible for pushing the items all the way down to the sink Subscriber. Thus, by default, the subscriber will also consume items on that particular thread only.
But this might not be the expected behaviour you would want all the time from your application. Suppose you want to fetch some data from the network and show it in the UI of your app?
You essentially have to accomplish two things here -

	• Make the network call in a non-blocking I/O thread
	
	• Consume the results in the main (or UI thread) of the application
	
You will have an observable that makes a network call in the I/O thread and passes the emissions down to the target subscriber. If you just use subscribeOn() with Schedulers.io(), the final subscriber will also operate in the I/O thread. And as we can’t touch UI components in any other thread, except the main thread, we are out of luck.
Now, we are in a dire need to switch threads and this is exactly where the observeOn() operator will come into play. In the observable chain, if it encounters an observeOn() somewhere in the chain, then the emissions are immediately switched to the thread specified by it.
In this contrived example, we have an observable that emits a stream of integers fetched from the network. In real-world use cases, this can be any other asynchronous operation like reading a large file, or fetching data from the database, etc. You can try the snippet and see the results for yourself. Just keep an eye on the thread names in the logs.
Now let us have a look at a slightly more complicated example, where we will be using multiple observeOn() operators to switch threads multiple times in our observable chain.
In the snippet above, the source observable emits its items on the I/O thread because we have used subscribeOn() with Schedulers.io(). Now we want to transform each item using the map() operator but want to do so in the computation thread. For that, we can use observeOn() with Schedulers.computation() just before the map() operator to switch threads and pass the emissions to the computation thread.
Next, we want to filter the items, but due to some reason, we want to perform that operation in a completely new thread for each of the items. We can use observeOn() again with Schedulers.newThread() before the filter()operator to switch to new threads for each item.
In the end, we want our subscriber to consume the final processed items and show the results in the UI and to do that we need to switch threads again, but this time, to the main thread using observeOn() with the AndroidSchedulers.mainThread() Scheduler.
But what happens if we use observeOn() multiple times consecutively? In the snippet below, which thread will the final subscriber consume the results on? Will the first observeOn() come into play or the last one? Any guesses?
If you run this snippet, you will see for yourself that all the items are consumed in the RxComputationThreadPool-1 thread, which means that the last observeOn() with Schedulers.computation() made its effect. But wonder why?
Under The Hood
You might have already guessed why the last observeOn() made its effect and not the other ones. As we already know, subscriptions always happen upstream, on the other hand, emissions always happen downstream.They originate from the source observable, flowing down the chain to the sink subscriber.
The observeOn() operator always works on the downstream observable instances because of which the last observeOn() with Schedulers.computation() overrides all other observeOn() operators specified earlier. So, whenever you want to switch threads for any particular observable, all you need to do is specify the observeOn() operator just above it. Synchronization, state inconsistencies, race conditions, and all other threading edge cases are automatically handled under the hood.






NOTE:

Misused Asynchrony Bites
There is one important word in the reactive programming definition: asynchronous. You are notified when data is emitted in the stream asynchronously – meaning independently to the main program flow. By structuring your program around data streams, you are writing asynchronous code: you write code invoked when the stream emits a new item. Threads, blocking code and side-effects are very important matters in this context. Let’s start with side-effects.
Functions without side-effects interact with the rest of the program exclusively through their arguments and return values. Side-effects can be very useful and are unavoidable in many cases. But they also have pitfalls. When using reactive programming, you should avoid unnecessary side-effects, and have a clear intention when they do use them. So, embrace immutability, and side-effect free functions. While some cases are justified, abusing side-effects leads to thunderstorms: thread safety.
That’s the second important point: threads. It’s nice to observe streams and be notified when something interesting happens, but you must never forget who is calling you, or more precisely on which thread your functions are executed. It is heavily recommended to avoid using too many threads in your program. Asynchronous programs relying on multiple threads becomes a tough synchronization puzzle often ending as a deadlock hunt.
That’s the third point: never block. Because you don’t own the thread calling you, you must be sure to never block it. If you do you may avoid the other items to be emitted, they will be buffered until … the buffer is full (back-pressure can kick in in this case, but this is not the topic of this post). By combining RX and asynchronous IO you have everything you need to write non-blocking code, and if you want more, look at Eclipse Vert.x, a reactive toolkit promoting reactiveness and asynchrony. For instance, the following code shows the Vert.x Web Client and its RX API to retrieve a JSON document from the server and display the name entry:

```java
	client.get("/api/people/4")
	.rxSend()
	.map(HttpResponse::bodyAsJsonObject)
	.map(json -> json.getString("name"))
	.subscribe(System.out::println, Throwable::printStackTrace);
```
Notice the subscribe method in this last snippet. It takes a second method called when one of the processing stages throws an exception. Always catch the exceptions. If you don’t you will spend hours trying to understand what’s going wrong.
 Keep Things Simple
As you know, “With great power comes great responsibility.” RX provides lots of very cool functions, and it’s easy to lean toward the dark side. Chaining flapmap, retry, debounce, and zip makes you feel like a ninja… BUT, never forget that good code needs to be readable by someone else.
Let’s take some code…

```java
	manager.getCampaignById(id)
	  .flatMap(campaign ->
	    manager.getCartsForCampaign(campaign)
	      .flatMap(list -> {
	        Single<List<Product>> products = manager.getProducts(campaign);
	        Single<List<UserCommand>> carts = manager.getCarts(campaign);
	        return products.zipWith(carts, 
	            (p, c) -> new CampaignModel(campaign, p, c));
	      })
	     .flatMap(model -> template
	        .rxRender(rc, "templates/fruits/campaign.thl.html")
	        .map(Buffer::toString))
	    )
	    .subscribe(
	      content -> rc.response().end(content),
	     err -> {
	      log.error("Unable to render campaign view", err);
	      getAllCampaigns(rc);
	    }
	);
	
```
Given an example like this is can be hard to understand no? It chains several asynchronous operations (flatmap), join another set of operations (zip). Reactive programming code first requires a mind-shift. You are notified of asynchronous events. Then, the API can be hard to grasp (just look at the list of operators). Don’t abuse, write comments, explain, or draw diagrams (I’m sure you are an ASCII art artist). RX is powerful, abusing it or not explaining it will make your coworkers grumpy.
 Reactive Programming != Reactive System
Probably the most confusing part. Using reactive programming does not build a reactive system. Reactive systems, as defined in the reactive manifesto, are an architectural style to build responsive distributed systems. Reactive Systems could be seen as distributed systems done right. A reactive system is characterized by four properties:

	• Responsive: a reactive system needs to handle requests in a reasonable time (I let you define reasonable).
	
	• Resilient: a reactive system must stay responsive in the face of failures (crash, timeout, 500 errors… ), so it must be designed for failures and deal with them appropriately.
	
	• Elastic: a reactive system must stay responsive under various loads. Consequently, it must scale up and down, and be able to handle the load with minimal resources.
	
	• Message driven: components from a reactive system interacts using asynchronous message passing.
	
Despite the simplicity of these fundamental principles of reactive systems, building one of them is tricky. Typically, each node needs to embrace an asynchronous non-blocking development model, a task-based concurrency model and uses non-blocking I/O. If you don’t think about these points first, it’s quickly going to be a spaghetti plate.
Reactive Programming and Reactive eXtension provides a development model to tame the asynchronous beast. By using it wisely, your code is going to stay readable, and understandable. However, using reactive programming does not transform your system into a Reactive System. Reactive Systems are the next level.
Conclusion
We finally reach the end of this post. If you want to go further and are interested in reactive, I recommend you have a look to Eclipse Vert.x – a toolkit to build reactive and distributed systems (http://vertx.io), and to the Reactive Microservices in Java minibook available from https://developers.redhat.com/promotions/building-reactive-microservices-in-java/. Combining Vert.x and Reactive eXtension unleashes your reactive superpower. You can not only use reactive programming but also build reactive systems and have access to a thrilling and growing ecosystem.


Some Terminology:
Upstream, downstream
The dataflows in RxJava consist of a source, zero or more intermediate steps followed by a data consumer or combinator step (where the step is responsible to consume the dataflow by some means):
source.operator1().operator2().operator3().subscribe(consumer);
source.flatMap(value -> source.operator1().operator2().operator3());

Here, if we imagine ourselves on operator2, looking to the left towards the source, is called the upstream. Looking to the right towards the subscriber/consumer, is called the downstream. This is often more apparent when each element is written on a separate line:
```java
source
  .operator1()
  .operator2()
  .operator3()
  .subscribe(consumer)
  
  ```
Objects in motion
In RxJava's documentation, emission, emits, item, event, signal, data and message are considered synonyms and represent the object traveling along the dataflow.
Backpressure

Backpressure is when in an Flowable processing pipeline, some asynchronous stages can't process the values fast enough and need a way to tell the upstream producer to slow down.

When the dataflow runs through asynchronous steps, each step may perform different things with different speed. To avoid overwhelming such steps, which usually would manifest itself as increased memory usage due to temporary buffering or the need for skipping/dropping data, a so-called backpressure is applied, which is a form of flow control where the steps can express how many items are they ready to process. This allows constraining the memory usage of the dataflows in situations where there is generally no way for a step to know how many items the upstream will send to it.
In RxJava, the dedicated Flowable class is designated to support backpressure and Observable is dedicated for the non-back pressured operations (short sequences, GUI interactions, etc.). The other types, Single, Maybe and Completable don't support backpressure nor should they; there is always room to store one item temporarily.
Assembly time
The preparation of dataflows by applying various intermediate operators happens in the so-called assembly time:


```java
Flowable<Integer> flow = Flowable.range(1, 5)
.map(v -> v* v)
.filter(v -> v % 3 == 0);
  ```
At this point, the data is not flowing yet and no side-effects are happening.
Subscription time
This is a temporary state when subscribe() is called on a flow that establishes the chain of processing steps internally:
flow.subscribe(System.out::println)
This is when the subscription side-effects are triggered (see doOnSubscribe). Some sources block or start emitting items right away in this state.
Runtime
This is the state when the flows are actively emitting items, errors or completion signals:

```java
Observable.create(emitter -> {
     while (!emitter.isDisposed()) {
         long time = System.currentTimeMillis();
         emitter.onNext(time);
         if (time % 2 != 0) {
             emitter.onError(new IllegalStateException("Odd millisecond!"));
             break;
         }
     }
})
.subscribe(System.out::println, Throwable::printStackTrace);
	
  ```
Practically, this is when the body of the given example above executes.
Simple background computation
One of the common use cases for RxJava is to run some computation, network request on a background thread and show the results (or error) on the UI thread:
 
```java
import io.reactivex.schedulers.Schedulers;
Flowable.fromCallable(() -> {
    Thread.sleep(1000); //  imitate expensive computation
    return "Done";
})
  .subscribeOn(Schedulers.io())
  .observeOn(Schedulers.single())
  .subscribe(System.out::println, Throwable::printStackTrace);
Thread.sleep(2000); // <--- wait for the flow to finish
  ```                         
This style of chaining methods is called a fluent API which resembles the builder pattern. However, RxJava's reactive types are immutable; each of the method calls returns a new Flowable with added behavior. To illustrate, the example can be rewritten as follows:
  
```java
Flowable<String> source = Flowable.fromCallable(() -> {
    Thread.sleep(1000); //  imitate expensive computation
    return "Done";
});
Flowable<String> runBackground = source.subscribeOn(Schedulers.io());
Flowable<String> showForeground = runBackground.observeOn(Schedulers.single());
showForeground.subscribe(System.out::println, Throwable::printStackTrace);
Thread.sleep(2000);
```

Typically, you can move computations or blocking IO to some other thread via subscribeOn. Once the data is ready, you can make sure they get processed on the foreground or GUI thread via observeOn.
Schedulers
RxJava operators don't work with Threads or ExecutorServices directly but with so called Schedulers that abstract away sources of concurrency behind an uniform API. RxJava 2 features several standard schedulers accessible via Schedulers utility class.

	• Schedulers.computation(): Run computation intensive work on a fixed number of dedicated threads in the background. Most asynchronous operator use this as their default Scheduler.
  
	• Schedulers.io(): Run I/O-like or blocking operations on a dynamically changing set of threads.
  
	• Schedulers.single(): Run work on a single thread in a sequential and FIFO manner.
  
	• Schedulers.trampoline(): Run work in a sequential and FIFO manner in one of the participating threads, usually for testing purposes.
  
These are available on all JVM platforms but some specific platforms, such as Android, have their own typical Schedulers defined: AndroidSchedulers.mainThread(), SwingScheduler.instance() or JavaFXSchedulers.gui().

In addition, there is option to wrap an existing Executor (and its subtypes such as ExecutorService) into a Schedulervia Schedulers.from(Executor). This can be used, for example, to have a larger but still fixed pool of threads (unlike computation() and io() respectively).

The Thread.sleep(2000); at the end is no accident. In RxJava the default Schedulers run on daemon threads, which means once the Java main thread exits, they all get stopped and background computations may never happen. Sleeping for some time in this example situations lets you see the output of the flow on the console with time to spare.

Concurrency within a flow
Flows in RxJava are sequential in nature split into processing stages that may run concurrently with each other:
```java
Flowable.range(1, 10)
  .observeOn(Schedulers.computation())
  .map(v -> v * v)
  .blockingSubscribe(System.out::println);
 ``` 
This example flow squares the numbers from 1 to 10 on the computation Scheduler and consumes the results on the "main" thread (more precisely, the caller thread of blockingSubscribe). However, the lambda v -> v * v doesn't run in parallel for this flow; it receives the values 1 to 10 on the same computation thread one after the other.
Parallel processing
Processing the numbers 1 to 10 in parallel is a bit more involved:
```java
Flowable.range(1, 10)
  .flatMap(v ->
      Flowable.just(v)
        .subscribeOn(Schedulers.computation())
        .map(w -> w * w)
  )
  .blockingSubscribe(System.out::println);
  ```
Practically, parallelism in RxJava means running independent flows and merging their results back into a single flow. The operator flatMap does this by first mapping each number from 1 to 10 into its own individual Flowable, runs them and merges the computed squares.
Note, however, that flatMap doesn't guarantee any order and the end result from the inner flows may end up interleaved. There are alternative operators:

	• concatMap that maps and runs one inner flow at a time and
	• concatMapEager which runs all inner flows "at once" but the output flow will be in the order those inner flows were created.
	
Alternatively, the Flowable.parallel() operator and the ParallelFlowable type help achieve the same parallel processing pattern:
```java
Flowable.range(1, 10)
  .parallel()
  .runOn(Schedulers.computation())
  .map(v -> v * v)
  .sequential()
  .blockingSubscribe(System.out::println);
  ```
Dependent sub-flows
flatMap is a powerful operator and helps in a lot of situations. For example, given a service that returns a Flowable, we'd like to call another service with values emitted by the first service:

```java
Flowable<Inventory> inventorySource = warehouse.getInventoryAsync();
inventorySource.flatMap(inventoryItem ->
    erp.getDemandAsync(inventoryItem.getId())
    .map(demand 
        -> System.out.println("Item " + inventoryItem.getName() + " has demand " + demand));
  )
  .subscribe();
  ```
Continuations
Sometimes, when an item has become available, one would like to perform some dependent computations on it. This is sometimes called continuations and, depending on what should happen and what types are involved, may involve various operators to accomplish.
Dependent
The most typical scenario is to given a value, invoke another service, await and continue with its result:
  ```java
service.apiCall()
.flatMap(value -> service.anotherApiCall(value))
.flatMap(next -> service.finalCall(next))
```
It is often the case also that later sequences would require values from earlier mappings. This can be achieved by moving the outer flatMap into the inner parts of the previous flatMap for example:
 ```java 
service.apiCall()
.flatMap(value ->
    service.anotherApiCall(value)
    .flatMap(next -> service.finalCallBoth(value, next))
)
```
Here, the original value will be available inside the inner flatMap, courtesy of lambda variable capture.
Non-dependent
In other scenarios, the result(s) of the first source/dataflow is irrelevant and one would like to continue with a quasi independent another source. Here, flatMap works as well:
 ```java 
Observable continued = sourceObservable.flatMapSingle(ignored -> someSingleSource)
continued.map(v -> v.toString())
  .subscribe(System.out::println, Throwable::printStackTrace);
  ```
however, the continuation in this case stays Observable instead of the likely more appropriate Single. (This is understandable because from the perspective of flatMapSingle, sourceObservable is a multi-valued source and thus the mapping may result in multiple values as well).
  
Often though there is a way that is somewhat more expressive (and also lower overhead) by using Completable as the mediator and its operator andThen to resume with something else:
 ```java 
sourceObservable
  .ignoreElements()           // returns Completable
  .andThen(someSingleSource)
  .map(v -> v.toString())
  
  ```
The only dependency between the sourceObservable and the someSingleSource is that the former should complete normally in order for the latter to be consumed.
  
Deferred-dependent
Sometimes, there is an implicit data dependency between the previous sequence and the new sequence that, for some reason, was not flowing through the "regular channels". One would be inclined to write such continuations as follows:
```java  
AtomicInteger count = new AtomicInteger();
Observable.range(1, 10)
  .doOnNext(ignored -> count.incrementAndGet())
  .ignoreElements()
  .andThen(Single.just(count.get()))
  .subscribe(System.out::println);
```
Unfortunately, this prints 0 because Single.just(count.get()) is evaluated at assembly time when the dataflow hasn't even run yet. We need something that defers the evaluation of this Single source until runtime when the main source completes:
```java
AtomicInteger count = new AtomicInteger();
Observable.range(1, 10)
  .doOnNext(ignored -> count.incrementAndGet())
  .ignoreElements()
  .andThen(Single.defer(() -> Single.just(count.get())))
  .subscribe(System.out::println);
  ```
  
or
```java
AtomicInteger count = new AtomicInteger();
Observable.range(1, 10)
  .doOnNext(ignored -> count.incrementAndGet())
  .ignoreElements()
  .andThen(Single.fromCallable(() -> count.get()))
  .subscribe(System.out::println);
```
