package org.primeit.governmentholidays.utils.toggle.interfaces;


import org.primeit.governmentholidays.utils.toggle.model.ToggleableView;

public interface OnToggledListener {
    void onSwitched(ToggleableView toggleableView, boolean isOn);
}