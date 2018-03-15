package org.jpchase.codingtest.service;

import org.jpchase.codingtest.domain.Sales;
import org.jpchase.codingtest.repository.SalesRepository;

/**
 * Created by gautampunhani on 15/03/2018.
 */
public class SalesServiceImpl implements SalesService {

  private SalesRepository repository;

  public SalesServiceImpl(SalesRepository repository) {
    this.repository = repository;
  }

  @Override
  public void record(Sales sales) {
    this.repository.add(sales);
  }
}
