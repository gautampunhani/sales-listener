package org.jpchase.codingtest.listener;

import org.jpchase.codingtest.dto.SalesNotification;
import org.jpchase.codingtest.service.SalesService;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.stream.Stream;


public class SalesNotificationListener{
  private static Logger logger = Logger.getLogger(SalesNotificationListener.class.getName());
  private static final int SALES_REPORT_PUBLISH_AFTER_N_MESSAGES = 10;
  private static final int MAX_MESSAGES = 50;

  private BlockingQueue<SalesNotification> queue;
  private SalesService salesService;
  private AtomicInteger numberOfMessages = new AtomicInteger(0);

  public SalesNotificationListener(BlockingQueue<SalesNotification> queue, SalesService salesService) {
    this.queue = queue;
    this.salesService = salesService;
  }

  public void processQueue() {
    //Generate Infinite Stream from Blocking queue. Else the simulation doesn't run correctly
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
      .limit(MAX_MESSAGES)
      .peek(recordSalesIfNotAdjustment)
      .peek(adjustPriceIfNotSales)
      .peek(publishReportIfConfiguredMessagesHaveBeenRead)
      .count();

    logger.info("Application is Pausing");
    salesService.publishAdjustmentReport();
  }

  private Consumer<SalesNotification> recordSalesIfNotAdjustment =  salesNotification -> {
    if(salesNotification.isSales()) {
      salesService.record(salesNotification.getSale());
    }
  };

  private Consumer<SalesNotification> adjustPriceIfNotSales =  salesNotification -> {
    if(!salesNotification.isSales()) {
      salesService.adjust(salesNotification.getPriceAdjustment());
    }
  };

  private Consumer<SalesNotification> publishReportIfConfiguredMessagesHaveBeenRead = sales -> {
    int messageNumber = numberOfMessages.incrementAndGet();
    if (messageNumber % SALES_REPORT_PUBLISH_AFTER_N_MESSAGES == 0) {
      salesService.publishProductSalesReport();
    }
  };
}
