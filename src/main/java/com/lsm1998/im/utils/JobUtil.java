package com.lsm1998.im.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JobUtil
{
    private static final ExecutorService JOB_EXECUTOR = Executors.newFixedThreadPool(10);

    public static void submit(Runnable task)
    {
        JOB_EXECUTOR.submit(task);
    }
}
