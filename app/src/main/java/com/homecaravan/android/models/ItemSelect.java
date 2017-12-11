package com.homecaravan.android.models;


public class ItemSelect {
    private boolean showViewTop;
    private String value;
    private boolean select;
    private int position;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isShowViewTop() {
        return showViewTop;
    }

    public void setShowViewTop(boolean showViewTop) {
        this.showViewTop = showViewTop;
    }
}
