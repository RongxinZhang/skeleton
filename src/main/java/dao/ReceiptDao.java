package dao;

import api.ReceiptResponse;
import generated.tables.records.ReceiptsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.util.List;

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
                .values(merchantName, amount, "none")
                .returning(RECEIPTS.ID)
                .fetchOne();

        checkState(receiptsRecord != null && receiptsRecord.getId() != null, "Insert failed");

        return receiptsRecord.getId();
    }

    public List<ReceiptsRecord> getAllReceipts() {
        return dsl.selectFrom(RECEIPTS).fetch();
    }

    public String insertTag(String tagName, int id) {
        dsl.update(RECEIPTS)
                .set(RECEIPTS.TAGS, tagName)
                .where(RECEIPTS.ID.equal(id))
                .execute();

        ReceiptsRecord receiptsRecord = dsl.selectFrom(RECEIPTS).where(RECEIPTS.ID.equal(id)).fetchOne();
        checkState(receiptsRecord != null && receiptsRecord.getTags() != null, "Insert failed");

        return receiptsRecord.getTags();
    }
}
