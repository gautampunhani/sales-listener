package org.jpchase.codingtest.listener;

import org.jpchase.codingtest.domain.Sales;
import org.jpchase.codingtest.dto.SalesNotification;
import org.jpchase.codingtest.service.SalesService;

import java.util.concurrent.BlockingQueue;
import java.util.function.Function;
import java.util.stream.Stream;


/**
 * Created by gautampunhani on 15/03/2018.
 */
public class SalesNotificationListener{

  private BlockingQueue<SalesNotification> queue;
  private SalesService salesService;
  private Function<SalesNotification, Sales> mapSalesNotificationToSales = salesNotification -> salesNotification.getSale();

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
      .map(mapSalesNotificationToSales)
      .forEach(sales -> salesService.record(sales));
  }
}
