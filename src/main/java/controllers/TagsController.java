package controllers;

import api.CreateReceiptRequest;
import api.ReceiptResponse;
import dao.ReceiptDao;
import generated.tables.records.ReceiptsRecord;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/tags/{tag}")
// @Consumes(MediaType.APPLICATION_JSON)
// @Produces(MediaType.APPLICATION_JSON)
public class TagsController {
    final ReceiptDao receipts;

    public TagsController(ReceiptDao receipts) {
        this.receipts = receipts;
    }

    @PUT
    public String toggleTag(@PathParam("tag") String tagName) {
      return receipts.insertTag(tagName, 1);
    }
}
