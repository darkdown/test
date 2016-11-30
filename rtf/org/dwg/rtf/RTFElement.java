package org.dwg.rtf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

public class RTFElement {
    private String              originText;
    private String              tagName;
    private List<String>        attrs;
    private Map<String, String> attrMaps;
    private String              text;

    public int length() {
        return StringUtils.length(text);
    }

    public void addAttr(String key, String value) {
        if (null == attrs) {
            attrs = new ArrayList<String>();
            attrMaps = new HashMap<String, String>();
        }
        if (!attrs.contains(key)) {
            attrs.add(key);
        }
        attrMaps.put(key, value);
    }

    public String getAttr(String key) {
        if (MapUtils.isNotEmpty(attrMaps)) {
            return StringUtils.stripToEmpty(attrMaps.get(key));
        }
        return StringUtils.EMPTY;
    }

    public RTFElement clone() {
        RTFElement rtfElement = new RTFElement();
        rtfElement.originText = originText;
        rtfElement.tagName = tagName;
        rtfElement.text = text;
        if (CollectionUtils.isNotEmpty(attrs)) {
            if (MapUtils.isNotEmpty(attrMaps)) {
                for (String key : attrs) {
                    rtfElement.addAttr(key, attrMaps.get(key));
                }
            }
        }
        return rtfElement;
    }

    public String getOriginText() {
        return originText;
    }

    public void setOriginText(String originText) {
        this.originText = originText;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        String str = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(tagName)) {
            str += "<";
            str += tagName;
            if (CollectionUtils.isNotEmpty(attrs)) {
                if (MapUtils.isNotEmpty(attrMaps)) {
                    for (String key : attrs) {
                        str += " " + key;
                        str += "=\"" + attrMaps.get(key) + "\"";
                    }
                }
            }
            str += ">";
            str += text;
            str += "</" + tagName + ">";
        } else {
            str = text;
        }
        return str;
    }

}
