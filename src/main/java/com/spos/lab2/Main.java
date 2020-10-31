package com.spos.lab2;

import com.spos.lab2.locks.AbstractFixnumLock;
import com.spos.lab2.locks.BakeryLock;
import com.spos.lab2.locks.ImprovedBakeryLock;
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
    public void run(String... args) throws Exception {
        AbstractFixnumLock fixnumLock = new AbstractFixnumLock(3) {
            @Override
            public void lock() {

            }

            @Override
            public void lockInterruptibly() throws InterruptedException {

            }

            @Override
            public boolean tryLock() {
                return false;
            }

            @Override
            public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
                return false;
            }

            @Override
            public void unlock() {

            }

            @Override
            public Condition newCondition() {
                return null;
            }
        };

//        fixnumLock.register();
//
//        new Thread(() -> {
//            fixnumLock.register();
//            fixnumLock.unregister();
//        }).start();
//        new Thread(fixnumLock::register).start();
//        new Thread(fixnumLock::register).start();
//        new Thread(fixnumLock::register).start();
//        new Thread(fixnumLock::register).start();
//        new Thread(fixnumLock::register).start();
//
//
        ImprovedBakeryLock improvedBakeryLock = new ImprovedBakeryLock(20);
        for (int i = 0; i < 6; i++) {
            new Thread(improvedBakeryLock::bakeryAlgorithmRun).start();
        }
//        BakeryLock bakeryLock = new BakeryLock(20);
//        for (int i = 0; i < 6; i++) {
//            new Thread(bakeryLock::bakeryAlgorithmRun).start();
//        }
    }
}
