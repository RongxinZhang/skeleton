package dao;

import api.ReceiptResponse;
import generated.tables.records.ReceiptsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.RECEIPTS;

public class ReceiptDao {
    DSLContext dsl;

    public ReceiptDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }

    public int insert(String merchantName, BigDecimal amount) {
        ReceiptsRecord receiptsRecord = dsl
                .insertInto(RECEIPTS, RECEIPTS.MERCHANT, RECEIPTS.AMOUNT, RECEIPTS.TAGS)
                .values(merchantName, amount, null)
                .returning(RECEIPTS.ID)
                .fetchOne();

        checkState(receiptsRecord != null && receiptsRecord.getId() != null, "Insert failed");

        return receiptsRecord.getId();
    }

    public List<ReceiptsRecord> getAllReceipts() {
        return dsl.selectFrom(RECEIPTS).fetch();
    }

    public String insertTag(String tagName, int id) {

        ReceiptsRecord receiptsRecord = dsl.selectFrom(RECEIPTS).where(RECEIPTS.ID.equal(id)).fetchOne();
        String tags = receiptsRecord.getTags();
        String toAdd = "";

        if (tags != null){
          String[] t = tags.split(" ");

          toAdd = tagName + " " + tags;

          for(int i = 0; i < t.length; i++) {
            if (t[i].equals(tagName)){
              toAdd = String.join(" ", ArrayUtils.removeElement(t, tagName));
            }
          }
        } else {
          toAdd = tagName;
        }

        dsl.update(RECEIPTS)
                .set(RECEIPTS.TAGS, toAdd)
                .where(RECEIPTS.ID.equal(id))
                .execute();

        receiptsRecord = dsl.selectFrom(RECEIPTS).where(RECEIPTS.ID.equal(id)).fetchOne();
        checkState(receiptsRecord != null && receiptsRecord.getTags() != null, "Insert failed");

        return receiptsRecord.getTags();
    }

    public List<ReceiptsRecord> getTags(String tagName) {
        return dsl.selectFrom(RECEIPTS).where(RECEIPTS.TAGS.contains(tagName)).fetch();
    }
}
