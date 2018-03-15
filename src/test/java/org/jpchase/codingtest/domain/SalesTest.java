package org.jpchase.codingtest.domain;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by gautampunhani on 15/03/2018.
 */

public class SalesTest {
  @Test
  public void shouldAdjustPrice() {
    Sales sales = new Sales("apple", 11d, 2);
    sales.adjust(previousPrice -> previousPrice*2);

    Assert.assertEquals(22d, sales.getPrice(), 0);
  }

  @Test
  public void shouldGiveSalesValue() {
    Sales sales = new Sales("apple", 11d, 3);
    Assert.assertEquals(33d, sales.getSalesValue(), 0);
  }
}
