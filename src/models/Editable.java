package models;

public interface Editable {
    void edit(int field, String value);
    String[] getFieldNames();
    void display();
}
