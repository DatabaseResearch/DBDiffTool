package org.sharpsw.kraken.data;

import java.sql.Types;

public enum SQLDataType {
    ARRAY(Types.ARRAY, "ARRAY"),
    BIGINT(Types.BIGINT, "BIGINT"),
    BINARY(Types.BINARY, "BINARY"),
    BIT(Types.BIT, "BIT"),
    BLOB(Types.BLOB, "BLOB"),
    BOOLEAN(Types.BOOLEAN, "BOOLEAN"),
    CHAR(Types.CHAR, "CHAR"),
    CLOB(Types.CLOB, "CLOB"),
    DATALINK(Types.DATALINK, "DATALINK"),
    DATE(Types.DATE, "DATE"),
    DECIMAL(Types.DECIMAL, "DECIMAL"),
    DISTINCT(Types.DISTINCT, "DISTINCT"),
    DOUBLE(Types.DOUBLE, "DOUBLE"),
    FLOAT(Types.FLOAT, "FLOAT"),
    INTEGER(Types.INTEGER, "INTEGER"),
    JAVA_OBJECT(Types.JAVA_OBJECT, "JAVA_OBJECT"),
    LONGNVARCHAR(Types.LONGNVARCHAR, "LONGNVARCHAR"),
    LONGVARBINARY(Types.LONGVARBINARY, "LONGVARBINARY"),
    LONGVARCHAR(Types.LONGVARCHAR, "LONGVARCHAR"),
    NCHAR(Types.NCHAR, "NCHAR"),
    NCLOB(Types.NCLOB, "NCLOB"),
    NULL(Types.NULL, "NULL"),
    NUMERIC(Types.NUMERIC, "NUMERIC"),
    NVARCHAR(Types.NVARCHAR, "NVARCHAR"),
    OTHER(Types.OTHER, "OTHER"),
    REAL(Types.REAL, "REAL"),
    REF(Types.REF, "REF"),
    ROWID(Types.ROWID, "ROWID"),
    SMALLINT(Types.SMALLINT, "SMALLINT"),
    SQLXML(Types.SQLXML, "SQLXML"),
    STRUCT(Types.STRUCT, "STRUCT"),
    TIME(Types.TIME, "TIME"),
    TIME_WITH_TIMEZONE(Types.TIME_WITH_TIMEZONE, "TIME_WITH_TIMEZONE"),
    TIMESTAMP(Types.TIMESTAMP, "TIMESTAMP"),
    TIMESTAMP_WITH_TIMEZONE(Types.TIMESTAMP_WITH_TIMEZONE, "TIMESTAMP_WITH_TIMEZONE"),
    TINYINT(Types.TINYINT, "TINYINT"),
    VARBINARY(Types.VARBINARY, "VARBINARY"),
    VARCHAR(Types.VARCHAR, "VARCHAR");


    private int type;
    private String name;

    private SQLDataType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
