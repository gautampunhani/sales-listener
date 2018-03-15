package org.jpchase.codingtest;

import org.jpchase.codingtest.domain.AdjustmentType;
import org.jpchase.codingtest.dto.SalesNotification;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

class DummySalesNotificationPublisher implements Runnable {
  private static Logger logger = Logger.getLogger(DummySalesNotificationPublisher.class.getName());
  private BlockingQueue<SalesNotification> salesNotificationQueue;
  private AtomicInteger messageCount = new AtomicInteger(0);
  DummySalesNotificationPublisher(BlockingQueue<SalesNotification> salesNotificationQueue) {
    this.salesNotificationQueue = salesNotificationQueue;
  }

  @Override
  public void run() {
    while(salesNotificationQueue.size() < 60) {
      salesNotificationQueue.add(new SalesNotification("apple", 12.4, 3));
      messageCount.incrementAndGet();
      salesNotificationQueue.add(new SalesNotification("orange", 13.4, 12));
      messageCount.incrementAndGet();
      salesNotificationQueue.add(new SalesNotification(AdjustmentType.MULTIPLY, "apple", 2));
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        logger.severe("Publisher thread interrupted");
      }
    }
  }
}
