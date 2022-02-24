package com.myfawwaz.bakulan_android.sayur.datepicker;

public interface OnDateSelectedListener {
    void onDateSelected(int year, int month, int day, int dayOfWeek);

    void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled);
}
