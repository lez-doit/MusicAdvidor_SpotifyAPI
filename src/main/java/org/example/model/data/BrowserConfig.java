package org.example.model.data;

public enum BrowserConfig {
    PAGE(0),

    DATA_PER_PAGE(5),

    MAX_PAGE(0);

    private int actual;

    BrowserConfig(int data) {
        this.actual = data;
    }


    public int get() {
        return actual;
    }

    public void set(int newValue) {
        this.actual = newValue;
    }

    @Override
    public String toString() {
        return actual + "";
    }
}
