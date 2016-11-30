
package org.dwg.rtf;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class RTFContent {
    private List<RTFElement> elements;

    public RTFContent() {
    }

    public RTFContent(String content) {
        if (StringUtils.isNotBlank(content)) {
            Pattern pattern = Pattern.compile("<.*?>.*?</.*?>");
            Matcher matcher = pattern.matcher(content);
            int start = 0;
            int lastEnd = 0;
            while (matcher.find()) {
                start = matcher.start();
                addTextElement(content.substring(lastEnd, start));
                String groupStr = matcher.group();
                addRTFElement(groupStr);
                lastEnd = matcher.end();
            }
            addTextElement(content.substring(lastEnd, content.length()));
        }
    }

    private void addElement(RTFElement rtfElement) {
        if (null == elements) {
            elements = new ArrayList<RTFElement>();
        }
        elements.add(rtfElement);
    }

    public void addTextElement(String text) {
        RTFElement rtfElement = new RTFElement();
        rtfElement.setOriginText(text);
        rtfElement.setText(text);
        addElement(rtfElement);
    }

    public RTFElement getRTFElement(String rtfText) {
        RTFElement rtfElement = null;
        String[] split = rtfText.split("</|=| |\"|>|<");
        try {
            if (StringUtils.equals(split[split.length - 1], split[1]) && 3 < split.length) {
                rtfElement = new RTFElement();
                rtfElement.setTagName(split[1]);
                rtfElement.setText(split[split.length - 2]);
                for (int i = 2; i < split.length - 3; i++) {
                    if (StringUtils.isNotBlank(split[i])) {
                        rtfElement.addAttr(split[i], split[i + 2]);
                        i += 2;
                    }
                }
            } else {
                rtfElement = new RTFElement();
                rtfElement.setText(rtfText);
            }
        } catch (Exception e) {
            rtfElement = new RTFElement();
            rtfElement.setText(rtfText);
        }
        rtfElement.setOriginText(rtfText);
        return rtfElement;
    }

    public void addRTFElement(String rtfText) {
        addElement(getRTFElement(rtfText));
    }

    public void addRTFElement(RTFElement rtfElement) {
        addElement(rtfElement);
    }

    public int length() {
        int length = 0;
        if (null != elements) {
            for (RTFElement rtfElement : elements) {
                length += rtfElement.length();
            }
        }
        return length;
    }

    public RTFContent subContent(int start, int end) {
        RTFContent rtfContent = new RTFContent();
        if (null != elements) {
            int length = 0;
            int lastLength = 0;
            RTFElement subRTF = null;
            for (RTFElement rtfElement : elements) {
                length += rtfElement.length();
                subRTF = rtfElement.clone();
                if (lastLength < start && start < length) {
                    subRTF.setOriginText(subRTF.getOriginText().replace(StringUtils.substring(subRTF.getText(), 0, start), ""));
                    subRTF.setText(StringUtils.substring(subRTF.getText(), start, subRTF.length()));
                    rtfContent.addElement(subRTF);
                } else if (start < lastLength && length < end) {
                    rtfContent.addElement(subRTF);
                } else if (lastLength < end && end < length) {
                    subRTF.setOriginText(subRTF.getOriginText().replace(StringUtils.substring(subRTF.getText(), end - lastLength, subRTF.length()), ""));
                    subRTF.setText(StringUtils.substring(subRTF.getText(), 0, end - lastLength));
                    rtfContent.addElement(subRTF);
                }
                lastLength = length;
            }
        }
        return rtfContent;
    }

    public List<RTFElement> getElements() {
        return elements;
    }

    public void setElements(List<RTFElement> elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        String str = StringUtils.EMPTY;
        if (null != elements) {
            for (RTFElement rtfElement : elements) {
                str += rtfElement.toString();
            }
        }
        return str;
    }

}
