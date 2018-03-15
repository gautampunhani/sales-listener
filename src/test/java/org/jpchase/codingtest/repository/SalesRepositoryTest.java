package org.jpchase.codingtest.repository;

import org.jpchase.codingtest.domain.Sales;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by gautampunhani on 15/03/2018.
 */
public class SalesRepositoryTest {
  @Test
  public void shouldStoreSales() {
    SalesRepository salesRepository = new SalesRepository();
    salesRepository.add(new Sales("any_type", 12.5, 10));

    List<Sales> allSales = salesRepository.getSales();
    Assert.assertEquals(12.5, allSales.get(0).getPrice(), 0);
  }

  @Test
  public void shouldAppendToExistingSales() {
    SalesRepository salesRepository = new SalesRepository();
    salesRepository.add(new Sales("any_existing_type", 12.5, 10));
    salesRepository.add(new Sales("any_type", 13.5, 12));

    List<Sales> allSales = salesRepository.getSales();
    Assert.assertEquals(12.5, allSales.get(0).getPrice(), 0);
    Assert.assertEquals(13.5, allSales.get(1).getPrice(), 0);
  }
}
