package com.example.asusx555l.projecttoolbar;

import android.renderscript.Sampler;

/**
 * Created by AsusX555L on 23.11.2017.
 */

public class Valute {
    private String charCode;
    private String value;

    public Valute(String charCode, String value) {
        this.charCode = charCode;
        this.value = value;
    }

    public Valute() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }
}
