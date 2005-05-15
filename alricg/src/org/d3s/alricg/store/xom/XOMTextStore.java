package org.d3s.alricg.store.xom;

import java.util.HashMap;
import java.util.Map;

import org.d3s.alricg.store.TextStore;

public class XOMTextStore implements TextStore {

    private String lang;

    private Map<String, String> shortTxt = new HashMap<String, String>();

    private Map<String, String> middleTxt = new HashMap<String, String>();

    private Map<String, String> longTxt = new HashMap<String, String>();

    private Map<String, String> errorMsgTxt = new HashMap<String, String>();

    private Map<String, String> toolTipTxt = new HashMap<String, String>();

    private XOMTextStore() {

    }

    public XOMTextStore(String language, Map<String, String> shortT,
            Map<String, String> middleT, Map<String, String> longT, Map<String, String> errT, Map<String, String> ttT) {

        lang = language;
        shortTxt = shortT;
        middleTxt = middleT;
        longTxt = longT;
        errorMsgTxt = errT;
        toolTipTxt = ttT;
    }

    /**
     * Für alle Texte die aus genau EINEM Wort bestehen.
     * 
     * @param key Das Schlüsselword zu dem Text
     * @return Den Text zum Schlüsselwort
     */
    public String getShortTxt(String key) {
        assert shortTxt.get(key) != null;
        return shortTxt.get(key);
    }

    /**
     * Für alle Texte die aus ZWEI bis DREI Worten bestehen.
     * 
     * @param key Das Schlüsselword zu dem Text
     * @return Den Text zum Schlüsselwort
     */
    public String getMiddleTxt(String key) {
        assert middleTxt.get(key) != null;
        return middleTxt.get(key);
    }

    /**
     * Für alle Texte die aus MEHR ALS DREI Worten bestehen.
     * 
     * @param key Das Schlüsselword zu dem Text
     * @return Den Text zum Schlüsselwort
     */
    public String getLongTxt(String key) {
        assert longTxt.get(key) != null;
        return longTxt.get(key);
    }

    /**
     * Für alle Texte die Fehlermeldungen sind.
     * 
     * @param key Das Schlüsselword zu dem Text
     * @return Den Text zum Schlüsselwort
     */
    public String getErrorTxt(String key) {
        assert errorMsgTxt.get(key) != null;
        return errorMsgTxt.get(key);
    }

    /**
     * Für alle Texte die ToolTips sind.
     * 
     * @param key Das Schlüsselword zu dem Text
     * @return Den Text zum Schlüsselwort
     */
    public String getToolTipTxt(String key) {
        assert toolTipTxt.get(key) != null;
        return toolTipTxt.get(key);
    }

}
