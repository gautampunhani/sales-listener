package org.jpchase.codingtest;

import org.jpchase.codingtest.dto.SalesNotification;
import org.jpchase.codingtest.listener.SalesNotificationListener;
import org.jpchase.codingtest.repository.SalesRepository;
import org.jpchase.codingtest.service.SalesService;
import org.jpchase.codingtest.service.SalesServiceImpl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

/**
 * Created by gautampunhani on 15/03/2018.
 */
public class SalesNotificationSimulation {

  public static void main(String[] args) {
    //Dependency Injection
    SalesService service = new SalesServiceImpl(new SalesRepository());
    BlockingQueue<SalesNotification> salesNotificationQueue = new ArrayBlockingQueue<>(20);

    DummySalesNotificationPublisher publisher = new DummySalesNotificationPublisher(salesNotificationQueue);
    SalesNotificationListener listener = new SalesNotificationListener(salesNotificationQueue, service);

    new Thread(publisher).start();
    listener.processQueue();
  }
}

class DummySalesNotificationPublisher implements Runnable {
  private static Logger logger = Logger.getLogger(DummySalesNotificationPublisher.class.getName());
  private BlockingQueue<SalesNotification> salesNotificationQueue;

  DummySalesNotificationPublisher(BlockingQueue<SalesNotification> salesNotificationQueue) {
    this.salesNotificationQueue = salesNotificationQueue;
  }

  @Override
  public void run() {
    while(true) {
      salesNotificationQueue.add(new SalesNotification("apple", 12.4));
      salesNotificationQueue.add(new SalesNotification("orange", 13.4, 12));
      logger.info("Publishing Sales");
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        logger.severe("Publisher thread interrupted");
      }
    }
  }
}