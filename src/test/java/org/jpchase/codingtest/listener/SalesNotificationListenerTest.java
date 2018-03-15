package org.jpchase.codingtest.listener;

import org.jpchase.codingtest.domain.Sales;
import org.jpchase.codingtest.domain.AdjustmentType;
import org.jpchase.codingtest.dto.SalesNotification;
import org.junit.Assert;
import org.junit.Test;

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
    Assert.assertEquals(0, salesService.getPublishProductSalesReportCalled());
  }

  @Test
  public void shouldSubmitAdjustmentReport() {
    BlockingQueue<SalesNotification> salesNotificationQueue = new ArrayBlockingQueue<>(100);
    IntStream.range(0, 33).forEach(i -> {
        salesNotificationQueue.add(new SalesNotification("any_type", 12.4 + i));
        salesNotificationQueue.add(new SalesNotification(AdjustmentType.ADD, "any_type", 6.4));
    });

    DummySalesService salesService = new DummySalesService();
    SalesNotificationListener listener = new SalesNotificationListener(salesNotificationQueue, salesService);

    listener.processNotifications(salesNotificationQueue.stream());
    Assert.assertEquals(1, salesService.getPublishAdjustmentReportCalled());
  }

  @Test
  public void shouldProcessOnyFiftyMessages() {
    BlockingQueue<SalesNotification> salesNotificationQueue = new ArrayBlockingQueue<>(200);
    IntStream.range(0, 133).forEach(i -> {
      salesNotificationQueue.add(new SalesNotification("any_type", 12.4 + i));
    });

    DummySalesService salesService = new DummySalesService();
    SalesNotificationListener listener = new SalesNotificationListener(salesNotificationQueue, salesService);

    listener.processNotifications(salesNotificationQueue.stream());
    Assert.assertEquals(5, salesService.getPublishProductSalesReportCalled());
  }

  @Test
  public void shouldSubmitProductSalesReportAfterTenMessages() {
    BlockingQueue<SalesNotification> salesNotificationQueue = new ArrayBlockingQueue<>(100);
    IntStream.range(0, 33).forEach(i ->
      salesNotificationQueue.add(new SalesNotification("any_type", 12.4 + i))
    );

    DummySalesService salesService = new DummySalesService();
    SalesNotificationListener listener = new SalesNotificationListener(salesNotificationQueue, salesService);

    listener.processNotifications(salesNotificationQueue.stream());
    Assert.assertEquals(3, salesService.getPublishProductSalesReportCalled());
  }

  @Test
  public void shouldAdjustPriceBasedOnOperator() {
    BlockingQueue<SalesNotification> salesNotificationQueue = new ArrayBlockingQueue<>(10);
    salesNotificationQueue.add(new SalesNotification("any_type", 12.4));
    salesNotificationQueue.add(new SalesNotification("any_type", 14.4));
    salesNotificationQueue.add(new SalesNotification(AdjustmentType.ADD, "any_type", 6.4));
    salesNotificationQueue.add(new SalesNotification(AdjustmentType.SUBTRACT, "any_type", 3.2));

    DummySalesService salesService = new DummySalesService();
    SalesNotificationListener listener = new SalesNotificationListener(salesNotificationQueue, salesService);

    listener.processNotifications(salesNotificationQueue.stream());
    Assert.assertEquals(AdjustmentType.ADD.getAdjustmentFunction(6.4).apply(3d), salesService.getCalledAdjustPrice().get(0).getAdjustmentFunction().apply(3d));
    Assert.assertEquals(AdjustmentType.SUBTRACT.getAdjustmentFunction(3.2).apply(3d), salesService.getCalledAdjustPrice().get(1).getAdjustmentFunction().apply(3d));
  }
}
