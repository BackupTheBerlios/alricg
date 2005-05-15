package org.d3s.alricg.store;

public interface TextStore {

    String getShortTxt(String key);

    String getMiddleTxt(String key);

    String getLongTxt(String key);

    String getErrorTxt(String key);

    String getToolTipTxt(String key);

}
