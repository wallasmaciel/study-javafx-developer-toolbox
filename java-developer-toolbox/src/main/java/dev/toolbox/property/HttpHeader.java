package dev.toolbox.property;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HttpHeader {
    private final StringProperty key;
    private final StringProperty value;

    public HttpHeader() {
        key = new SimpleStringProperty("");
        value = new SimpleStringProperty("");
    }

    public HttpHeader(String key, String value) {
        this();

        this.key.set(key);
        this.value.set(value);
    }

    public StringProperty keyProperty() {
        return key;
    }

    public StringProperty valueProperty() {
        return value;
    }

    public String getKey() {
        return key.getValue();
    }

    public void setKey(String key) {
        this.key.set(key);
    }

    public String getValue() {
        return value.getValue();
    }

    public void setValue(String value) {
        this.value.set(value);
    }
}
