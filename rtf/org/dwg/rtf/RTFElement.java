package org.dwg.rtf;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

public class RTFElement {
    private String              originText;
    private String              tagName;
    private Map<String, String> attrMaps;
    private String              text;

    public int length() {
        return StringUtils.length(text);
    }

    public void addAttr(String key, String value) {
        if (null == attrMaps) {
            attrMaps = new HashMap<String, String>();
        }
        if (!attrMaps.containskey(key)) {
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


        public static final String  RTF_ATTR_TYPE       = "t";
        public static final String  RTF_ATTR_TYPE_AT    = "at";
        public static final String  RTF_ATTR_TYPE_TOPIC = "top";
        public static final String  RTF_ATTR_TYPE_EMOJI = "emo";
        public static final String  RTF_ATTR_ID         = "id";

        private String              originText;
        private String              tagName;
        private Map<String, String> attrMaps;
        private String              text;

        public int length() {
            if (RTF_ATTR_TYPE_EMOJI.equals(getAttr(RTF_ATTR_TYPE))) {
                return 1;
            }
            return StringUtils.length(text);
        }

        public void setAttr(String key, String value) {
            if (null == attrMaps) {
                attrMaps = new HashMap<String, String>();
            }
            attrMaps.put(key, value);
        }

        public String getAttr(String key) {
            if (MapUtils.isNotEmpty(attrMaps)) {
                return StringUtils.stripToEmpty(attrMaps.get(key));
            }
            return StringUtils.EMPTY;
        }

        public PostRTFElement clone() {
            PostRTFElement rtfElement = new PostRTFElement();
            rtfElement.originText = originText;
            rtfElement.tagName = tagName;
            rtfElement.text = text;
            if (MapUtils.isNotEmpty(attrMaps)) {
                Set<String> keySet = attrMaps.keySet();
                for (String key : keySet) {
                    rtfElement.setAttr(key, attrMaps.get(key));
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
                if (MapUtils.isNotEmpty(attrMaps)) {
                    Set<String> keySet = attrMaps.keySet();
                    for (String key : keySet) {
                        str += " " + key;
                        str += "=\"" + attrMaps.get(key) + "\"";
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
