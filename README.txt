多线程和并行程序设计
    多线程使得程序中的多个任务可以同时执行
    线程：一个任务从头到尾的执行流程
    多线程使得程序反应更快，交互性更强，执行效率更高。

创建任务和线程：一个任务类必须实现Runnable接口，任务必须从线程运行

Thread类
    Thread（）                    创建一个空的线程
    Thread（task: Runnable）      为制定的任务创建一个线程
    start() : void                开始一个线程导致JVM调用run()方法
    isAlive() : boolean           测试当前线程是否在运行
    setPriority(p : int) : void   为该线程指定优先级p(1-10)
    join() : void                 等待该线程结束
    sleep(millis: long) : void    让一个线程休眠指定时间，以毫秒为单位
    yield() : void                引发一个线程暂停并允许其他线程执行
    interrupt() : void            中断该线程

 线程池
    为每个任务开始开始一个新的线程可能会限制吞吐量并造成性能降低，线程池是管理并发执行任务个数的理想方法。

    Executor接口：执行线程池中的任务
        execute(Runnable object) : void       执行可运行任务

    ExecutorService接口：管理和控制任务   ----- Executor的子接口
        shutdown（）: void                 关闭执行器，但是允许执行器中的任务执行完。一旦关闭，则不再接收新的任务
        shutdownNow() : List<Runnable>     立刻关闭执行器，返回未完成的任务的列表
        isshutdown() : boolean             如果执行器已经关闭，则返回true
        isTerminated() : boolean           如果池中的所有任务终止，则返回true

    Executors类:
        newFixedThreadPool(numberOfThreads: int) : ExecutorService     创建一个可以运行指定数目线程的线程池。一个线程
                                                                      在当前任务已经完成的情况下可以重用，来执行下一个任务
        newCachedThreadPool() : ExecutorService                        创建一个线程池，它会在必要的时候创建新的线程，但是如果
                                                                       之前创建的线程可用，则重用之前的线程。

 线程同步
    线程同步用于协调相互依赖的线程的执行
    如果一个资源被多个线程同时访问，可能会遭到破坏。竞争状态，

 Synchronized关键字
    临界区：程序中的特定部分
    public synchronized void deposit(double amount)为安全线程
    调用一个对象的同步实例方法，需要给该对象加锁。而调用一个类上的同步静态方法需要给该类加锁。当执行
    方法中的某一个代码块时，称为同步块。
    synchronized(expr) {
        statements;
    }
    expr求值必须是一个对象的引用。如果对象被另一个线程锁定，则在解锁之前，该线程将被阻塞。当获准对一个对象加锁时，
    该线程执行同步块中的语句，解锁。

 加锁同步：
    显式的采用锁和状态来同步线程
    Lock接口
        lock() ： void                   得到一个锁
        unlock() : void                  释放锁
        newCondition() : Condition       返回一个绑定到该Lock实例的Condition实例

    ReentrantLock类：实现Lock接口
        ReentrantLock()                  等价于ReentrantLock(false)
        ReentrantLock(fair:boolean)      根据给定的公平策略创建要给锁，如果fairness为真，一个最长等待时间的线程将得到该锁。

 线程间的协作：
    锁上的条件可以用于协调线程之间的交互。
    条件通过调用Lock对象的newCondition（）方法创建。
    Condition接口：
        await() : void                  引起当前线程等待，直到发出条件信号
        signal() : void                 唤醒一个等待线程
        signalAll() : Condition         唤醒所有等待线程


 阻塞队列
    Java合集框架提供了ArrayBlockingQueue,LinkedBlockingQueue, PriorityBlockingQueue来支持阻塞队列
    定义：
        在试图向一个满队列添加元素或者从空队列中删除元素时会导致线程阻塞。
    Collection接口
        Queue接口
            BlockingQueue接口：
                +put(element : E) : void         插入一个元素到队列的尾部，如果队列满了则等待
                +take() : E                      从该队列的头部获取并删除元素，如果队列为空则等待

                ArrayBlockingQueue接口：
                    ArrayBlockingQueue(capacity : int)                      指定容量
                    ArrayBlockingQueue(capacity : int, fair : boolean)      指定公平性策略
                LindedBlockingQueue接口：
                    LinkedBlockingQueue()                                   创建无边界或者有边界的队列
                    LinkedBlockingQueue(capacity : int)
                PriorityBlockingQueue()
                    PriorityBlockingQueue()                                 创建无边界或者有边界的优先队列
                    PriorityBlockingQueue(capacity : int)


 信号量
    可以使用信号量来限制访问一个共享资源的线程数
    访问资源之前，线程必须从信号量获取许可，访问结束之后归还许可。
    Semaphore(numberOfPermits : int)
    Semaphore(numberOfPermits : int, fair : boolean)                    创建一个具有指定数目许可以及公平性策略的信号量
    acquire() : void                                                    从该信号量获取一个许可，如果许可不可用，线程将被阻塞。
    release() : void                                                    释放一个许可返回给信号量

 死锁
    资源排序：按照一定的顺序获取多个资源

 线程状态：
    新建          New
    就绪          start()->Ready
    运行          run()
    阻塞          join() wait() sleep()
    结束

    interrupt()中断线程：就绪或运行状态的线程设置一个中断标志，阻塞状态的线程变为就绪状态，抛出InterruptedException

 同步合集
    Collections interface
        synchronizedCollection(c : Collection) : Collection
        synchronizedList(list : List) : List
        synchronizedMap(m : Map) : Map
        synchronizedSet(s : Set) : Set
        synchronizedSortedMap(m : SortedMap) : SortedMap
        synchronizedSortedSet(s : SortedSet) : SortedSet

 并行编程
    Fork/Join框架用于在Java中实现并行编程

    Future<V>接口：
        cancel（interrupt : boolean) : boolean           试图取消该任务
        get() : V                                        如果需要，等待计算结束并返回一个结果
        isDone() : boolean                               如果任务完成，则返回true

        ForkJoinTask<V>类                                定义一个任务
            adapt(Runnable task): ForkJoinTask<V>        从一个运行的任务处返回一个ForkJoinTask
            fork() : ForkJoinTask<V>                     安排一个任务的异步执行
            join() : V                                   当计算完成的时候，返回该计算的结果
            invoke() : V                                 执行任务并等待完成，并且返回其结果
            invokeAll(task : ForkJoinTask<?>..) : void   分解给定的任务，并在所有任务都完成的时候返回

            RecursiveAction<V>类
                compute() : void                        定义任务是如何运行的
            RecursiveTask<V>类
                compute() : V                           定义任务如何执行的，当任务完成时返回值

    ExecutorService接口
        ForkJoinPool
            ForkJoinPool()                              使用所有可用的处理器来创建一个ForkJoinPool
            ForkJoinPool(parallelism : int)             使用指定数量的处理器来创建一个ForkJoinPool
            invoke(ForkJoinTask<T>) : T                 执行任务，并在任务结束的时候返回其结果
