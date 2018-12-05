/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.shardingproxy;

import io.shardingsphere.shardingproxy.backend.netty.future.FutureRegistry;
import lombok.RequiredArgsConstructor;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class CallableErrorTest {
    
    private static ExecutorService executorService = Executors.newFixedThreadPool(8);
    
    private AtomicInteger serialNo = new AtomicInteger(0);
    
    @AfterClass
    public static void afterClass() {
        executorService.shutdown();
    }
    
    @Test
    public void throwErrorTest() throws ExecutionException, InterruptedException {
        List<Future<Integer>> futureList = new ArrayList<>();
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            futureList.add(executorService.submit(new CallableTask(serialNo.getAndIncrement())));
        }
        try {
            for (Future<Integer> each : futureList) {
                results.add(each.get());
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        System.out.println(results);
    }
    
    @RequiredArgsConstructor
    private class CallableTask implements Callable<Integer> {
        private final int serialNo;
    
        @Override
        public Integer call() throws Exception {
            if (serialNo == 5) {
                throw new RuntimeException("trigger error");
            }
            System.out.println(serialNo);
            return 1;
        }
    };
}
