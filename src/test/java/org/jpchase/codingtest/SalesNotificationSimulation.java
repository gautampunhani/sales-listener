package org.jpchase.codingtest;

import org.jpchase.codingtest.dto.SalesNotification;
import org.jpchase.codingtest.listener.SalesNotificationListener;
import org.jpchase.codingtest.repository.SalesRepositoryImpl;
import org.jpchase.codingtest.service.SalesService;
import org.jpchase.codingtest.service.SalesServiceImpl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SalesNotificationSimulation {

  public static void main(String[] args) {
    //Dependency Injection
    SalesService service = new SalesServiceImpl(new SalesRepositoryImpl());
    BlockingQueue<SalesNotification> salesNotificationQueue = new ArrayBlockingQueue<>(200);

    DummySalesNotificationPublisher publisher = new DummySalesNotificationPublisher(salesNotificationQueue);
    SalesNotificationListener listener = new SalesNotificationListener(salesNotificationQueue, service);

    new Thread(publisher).start();
    listener.processQueue();
  }
}