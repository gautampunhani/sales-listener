package org.jpchase.codingtest.listener;

import org.jpchase.codingtest.domain.Sales;
import org.jpchase.codingtest.dto.SalesNotification;
import org.jpchase.codingtest.service.SalesService;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

/**
 * Created by gautampunhani on 15/03/2018.
 */
public class SalesNotificationListenerTest {

  @Test
  public void shouldRecordEachSales() {
    BlockingQueue<SalesNotification> salesNotificationQueue = new ArrayBlockingQueue<>(10);
    salesNotificationQueue.add(new SalesNotification("any_type", 12.4));
    salesNotificationQueue.add(new SalesNotification("any_type_with_quantity", 13.4, 12));

    DummySalesService salesService = new DummySalesService();
    SalesNotificationListener listener = new SalesNotificationListener(salesNotificationQueue, salesService);

    listener.processNotifications(salesNotificationQueue.stream());

    List<Sales> allSales = salesService.getSales();
    Assert.assertEquals(12.4, allSales.get(0).getPrice(), 0);
    Assert.assertEquals(13.4, allSales.get(1).getPrice(), 0);
  }

  @Test
  public void shouldNotSubmitReportBeforeTenMessages() {
    BlockingQueue<SalesNotification> salesNotificationQueue = new ArrayBlockingQueue<>(10);
    salesNotificationQueue.add(new SalesNotification("any_type", 12.4));
    salesNotificationQueue.add(new SalesNotification("any_type_with_quantity", 13.4, 12));

    DummySalesService salesService = new DummySalesService();
    SalesNotificationListener listener = new SalesNotificationListener(salesNotificationQueue, salesService);

    listener.processNotifications(salesNotificationQueue.stream());
    Assert.assertEquals(0, salesService.getLogProductSalesReportCalled());
  }

  @Test
  public void shouldSubmitProductSalesReportAfterTenMessages() {
    BlockingQueue<SalesNotification> salesNotificationQueue = new ArrayBlockingQueue<>(100);
    IntStream.range(0, 33).forEach(i ->
      salesNotificationQueue.add(new SalesNotification("any_type", 12.4 + i))
    );
    salesNotificationQueue.add(new SalesNotification("any_type_with_quantity", 13.4, 12));

    DummySalesService salesService = new DummySalesService();
    SalesNotificationListener listener = new SalesNotificationListener(salesNotificationQueue, salesService);

    listener.processNotifications(salesNotificationQueue.stream());
    Assert.assertEquals(3, salesService.getLogProductSalesReportCalled());
  }
}

class DummySalesService implements SalesService {

  private List<Sales> recordedSales = new ArrayList<>();
  private int logProductSalesReportCalled = 0;

  public void record(Sales sales) {
    recordedSales.add(sales);
  }

  @Override
  public void publishProductSalesReport() {
    logProductSalesReportCalled++;
  }

  public int getLogProductSalesReportCalled() {
    return logProductSalesReportCalled;
  }

  public List<Sales> getSales() {
    return recordedSales;
  }
}

