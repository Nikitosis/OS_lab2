package com.spos.lab2;

import com.spos.lab2.benchmark.LockBenchmark;
import com.spos.lab2.locks.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

@SpringBootApplication
public class Main implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        new LockBenchmark(10, 100000, true).run();
        new LockBenchmark(4, 10000, 10).run();
        new LockBenchmark(2, 10000, 10).run();

//        ImprovedBakeryLock improvedBakeryLock = new ImprovedBakeryLock(20);
//        for (int i = 0; i < 6; i++) {
//            new Thread(improvedBakeryLock::bakeryAlgorithmRun).start();
//        }
//        BakeryLock bakeryLock = new BakeryLock(20);
//        for (int i = 0; i < 6; i++) {
//            new Thread(bakeryLock::bakeryAlgorithmRun).start();
//        }
    }
}
