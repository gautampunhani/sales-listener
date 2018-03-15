package org.jpchase.codingtest.service;

import org.jpchase.codingtest.domain.AdjustmentType;
import org.jpchase.codingtest.domain.PriceAdjustment;
import org.jpchase.codingtest.domain.Sales;
import org.jpchase.codingtest.repository.SalesRepositoryImpl;
import org.junit.Assert;
import org.junit.Test;

public class SalesServiceImplTest {
  @Test
  public void shouldApplyPriceAdjustment() {
    SalesRepositoryImpl repository = new SalesRepositoryImpl();
    SalesService salesService = new SalesServiceImpl(repository);
    salesService.record(new Sales("apple", 32.5, 19));

    salesService.adjust(new PriceAdjustment(AdjustmentType.ADD, "apple", 2));

    Assert.assertEquals(32.5 + 2, repository.getSales().get(0).getPrice(), 0);
  }

  @Test
  public void shouldNotApplyAdjustmentIfPriceAdjustmentIsOfAnotherType() {
    SalesRepositoryImpl repository = new SalesRepositoryImpl();
    SalesService salesService = new SalesServiceImpl(repository);
    salesService.record(new Sales("apple", 32.5, 19));

    salesService.adjust(new PriceAdjustment(AdjustmentType.ADD, "orange", 2));

    Assert.assertEquals(32.5, repository.getSales().get(0).getPrice(), 0);
  }
}