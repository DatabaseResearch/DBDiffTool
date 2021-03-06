package org.sharpsw.kraken.data;

/**
 * Created by andersonkmi on 11/26/15.
 */
public class Column {
    private String name = "";
    private SQLDataType dataType;
    private String typeName = "";
    private int size = 0;
    private int decimalDigits = 0;
    private String defaultValue = "";
    private int ordinalPosition = 0;
    private boolean isNullable = false;
    private boolean isAutoIncrement = false;
    private boolean isGenerated = false;
    private boolean isUnique = false;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDataType(SQLDataType type) {
        dataType = type;
    }

    public SQLDataType getDataType() {
        return dataType;
    }

    public void setTypeName(String name) {
        typeName = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setDecimalDigits(int digits) {
        decimalDigits = digits;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }

    public void setDefaultValue(String value) {
        defaultValue = value;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setOrdinalPosition(int position) {
        ordinalPosition = position;
    }

    public int getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setIsNullable(boolean info) {
        isNullable = info;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public void setIsAutoIncrement(boolean info) {
        isAutoIncrement = info;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public void setIsGenerated(boolean info) {
        isAutoIncrement = info;
    }

    public boolean isGenerated() {
        return isGenerated;
    }

    public void setIsUnique(boolean flag) {
        isUnique = flag;
    }

    public boolean isUnique() {
        return isUnique;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) {
            return true;
        }

        if(!getClass().equals(other.getClass())) {
            return false;
        }

        Column instance = (Column) other;
        return getName().equals(instance.getName());
    }
}
