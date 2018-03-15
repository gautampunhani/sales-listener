package org.jpchase.codingtest.listener;

import org.jpchase.codingtest.dto.SalesNotification;
import org.jpchase.codingtest.service.SalesService;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;


/**
 * Created by gautampunhani on 15/03/2018.
 */
public class SalesNotificationListener{

  private BlockingQueue<SalesNotification> queue;
  private SalesService salesService;
  private AtomicInteger numberOfMessages = new AtomicInteger(0);

  public SalesNotificationListener(BlockingQueue<SalesNotification> queue, SalesService salesService) {
    this.queue = queue;
    this.salesService = salesService;
  }

  public void processQueue() {
    Stream<SalesNotification> notificationStream = Stream.generate(() -> {
      try {
        return queue.take();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });

    processNotifications(notificationStream);
  }

  void processNotifications(Stream<SalesNotification> notificationStream) {
    notificationStream
      .map(SalesNotification::getSale)
      .peek(sales -> {
        salesService.record(sales);

        if(numberOfMessages.incrementAndGet() % 10 == 0) {
          salesService.publishProductSalesReport();
        }
      })
      .count();
  }
}
