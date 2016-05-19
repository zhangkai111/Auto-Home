package com.lanou3g.autohome.threadpool;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by dllo on 16/4/22.
 * 自定义的线程池
 */
public class MyThreadPool {

    private static MyThreadPool myThreadPool;
    //线程池对象
    private ThreadPoolExecutor threadPoolExecutor;

    private MyThreadPool() {

        //对线程池初始化
        //使用五个参数的构造方法

        //int corePoolSize,核心线程数量
        //  核心线程会随着线程池你的创建同时创建出来,如果没有任务,核心线程就会等待,并不会销毁,
        //  只有核心线程才能执行任务,通常核心线程数是CPU核心数+1

        //int maximumPoolSize,最大线程数
        //  线程池中所能容纳的线程数量的上限 一定大于等于核心线程数
        //  超过核心线程数的线程叫做工作线程,
        //  工作线程并不能执行任务,他们会等待核心线程执行完毕,在开始执行,所有的工作线程满足FIFO策略
        //  工作线程是有可能被销毁的,当工作线程没有工作可做的时候
        //  同时,时间超过了keepAliveTime所规定的时间,就会把该工作线程销毁掉

        //long keepAliveTime,保持存活时间
        //  确定工作线程没有工作是还能存活的最大时间

        //TimeUnit unit,用来确定keepAliveTime的单位,
        //  最大的是天,最小的是纳秒

        //BlockingQueue<Runnable> workQueue,任务队列
        //  当线程池内的线程数达到线程池规定最大线程数时,还继续向线程池中提交任务
        //  这些任务就会放进任务队列,BlockingQueue是一个接口对象
        //  我们在使用的时候,需要使用实现该接口的实现类
        //Android为我们封装 了几个常用的任务队列
        //  常用的任务队列有:
        //      1.LinkedBlockingQueue:无界任务队列
        //      2.SynchronousQueue:直接提交的任务队列,
        //          该任务队列不能存储任务,所以一般配合线程池的最大线程数是无上限(int的最大值)的情况使用
        //      3.DelayQueue:等待队列,他会让工作队列里的任务等待一会(自己定义的事件)在进入线程池
        //      4.ArrayBlockingQueue:有界任务队列
        //      5.PriorityBlockingQueue:优先级任务队列
        //          可以指定每个任务的优先级,优先级高的先进入线程池

        //CPU核心数:
        int CPUCores = Runtime.getRuntime().availableProcessors();
        threadPoolExecutor = new ThreadPoolExecutor(CPUCores + 1 ,
                CPUCores * 2 + 1 ,
                60l ,
                TimeUnit.SECONDS ,
                new LinkedBlockingQueue<Runnable>());
    }

    //获得线程池对象
    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    public static MyThreadPool getInstence() {
        if (myThreadPool == null) {
            synchronized (MyThreadPool.class) {
                if (myThreadPool == null) {
                    myThreadPool = new MyThreadPool();
                }

            }

        }
        return myThreadPool;
    }

}
