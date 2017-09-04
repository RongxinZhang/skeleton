package controllers;

import api.CreateReceiptRequest;
// import api.NetIdController;
import dao.ReceiptDao;
import generated.tables.records.ReceiptsRecord;
import org.junit.Before;
import org.junit.Test;
import api.ReceiptResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
// import static org.hamcrest.core.IsEqual.equalTo;
// import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;


public class ReceiptControllerTest {
  private ReceiptController controller;
  private NetIdController getId;
  private ReceiptDao receiptDao;

  @Before
  public void setup (){
    receiptDao = mock(ReceiptDao.class);
    controller = new ReceiptController(receiptDao);
    getId = new NetIdController();
  }

  // Check that the ID responded correctly
  @Test
  public void runTest (){
    String res = getId.getNetId();
    assertEquals(res, "rz345");
  }
}
