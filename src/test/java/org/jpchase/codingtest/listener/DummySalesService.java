package org.jpchase.codingtest.listener;

import org.jpchase.codingtest.domain.PriceAdjustment;
import org.jpchase.codingtest.domain.Sales;
import org.jpchase.codingtest.service.SalesService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gautampunhani on 15/03/2018.
 */
class DummySalesService implements SalesService {

  private List<Sales> recordedSales = new ArrayList<>();
  private int publishProductSalesReportCalled = 0;
  private int publishAdjustmentReportCalled = 0;

  private List<PriceAdjustment> calledAdjustPrices = new ArrayList<>();
  public void record(Sales sales) {
    recordedSales.add(sales);
  }

  @Override
  public void adjust(PriceAdjustment adjustment) {
    calledAdjustPrices.add(adjustment);
  }

  public List<PriceAdjustment> getCalledAdjustPrice() {
    return calledAdjustPrices;
  }

  @Override
  public void publishProductSalesReport() {
    publishProductSalesReportCalled++;
  }

  @Override
  public void publishAdjustmentReport() {
    publishAdjustmentReportCalled++;
  }

  public int getPublishProductSalesReportCalled() {
    return publishProductSalesReportCalled;
  }
  public int getPublishAdjustmentReportCalled() {
    return publishAdjustmentReportCalled;
  }

  public List<Sales> getSales() {
    return recordedSales;
  }
}
