package org.jpchase.codingtest.repository;

import org.jpchase.codingtest.domain.AdjustmentType;
import org.jpchase.codingtest.domain.PriceAdjustment;
import org.jpchase.codingtest.domain.Sales;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

public class SalesRepositoryImplTest {
  @Test
  public void shouldStoreSales() {
    SalesRepository salesRepository = new SalesRepositoryImpl();
    salesRepository.add(new Sales("any_type", 12.5, 10));

    List<Sales> allSales = salesRepository.getSales();
    Assert.assertEquals(12.5, allSales.get(0).getPrice(), 0);
  }

  @Test
  public void shouldAppendToExistingSales() {
    SalesRepository salesRepository = new SalesRepositoryImpl();
    salesRepository.add(new Sales("any_existing_type", 12.5, 10));
    salesRepository.add(new Sales("any_type", 13.5, 12));

    List<Sales> allSales = salesRepository.getSales();
    Assert.assertEquals(12.5, allSales.get(0).getPrice(), 0);
    Assert.assertEquals(13.5, allSales.get(1).getPrice(), 0);
  }

  @Test
  public void shouldStoreAdjustmentsApplied() {
    SalesRepository salesRepository = new SalesRepositoryImpl();
    PriceAdjustment priceAdjustment = new PriceAdjustment(AdjustmentType.MULTIPLY, "random_product", 2);
    salesRepository.recordAdjustmentApplied(priceAdjustment);

    List<PriceAdjustment> allAdjustments = salesRepository.getAdjustmentsApplied();
    Assert.assertEquals(priceAdjustment, allAdjustments.get(0));
  }

  @Test
  public void shouldAppendToExistingAdjustmentApplied() {
    SalesRepository salesRepository = new SalesRepositoryImpl();
    PriceAdjustment twicePriceAdjustment = new PriceAdjustment(AdjustmentType.MULTIPLY, "random_product", 2);
    PriceAdjustment thricePriceAdjustment = new PriceAdjustment(AdjustmentType.ADD, "random_product", 3);
    salesRepository.recordAdjustmentApplied(twicePriceAdjustment);
    salesRepository.recordAdjustmentApplied(thricePriceAdjustment);

    List<PriceAdjustment> allAdjustments = salesRepository.getAdjustmentsApplied();
    Assert.assertEquals(twicePriceAdjustment, allAdjustments.get(0));
    Assert.assertEquals(thricePriceAdjustment, allAdjustments.get(1));
  }

}
