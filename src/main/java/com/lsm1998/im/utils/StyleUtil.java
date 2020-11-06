package com.lsm1998.im.utils;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class StyleUtil
{
    private static final Map<String, String> styleMap = new LinkedHashMap<>();
    private static final String DEFAULT_STYLE = "默认";
    private static String CURRENT_STYLE = null;

    static
    {
        styleMap.put("默认", "javax.swing.plaf.metal.MetalLookAndFeel");
        styleMap.put("Windows", "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        styleMap.put("WindowsClassic", "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
        styleMap.put("Motif", "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        styleMap.put("mac", "com.sun.java.swing.plaf.mac.MacLookAndFeel");
        styleMap.put("GTK", "com.sun.java.swing.plaf.gtk.GTKLookAndFeel");

        setStyle(DEFAULT_STYLE);
    }

    /**
     * 设置风格
     *
     * @param styleName
     */
    public static void setStyle(String styleName)
    {
        if (styleMap.containsKey(styleName))
        {
            try
            {
                UIManager.setLookAndFeel(styleMap.get(styleName));
                CURRENT_STYLE = styleName;
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        } else
        {
            log.error("设置style失败，原因是找不到目标风格：{}", styleName);
        }
    }

    /**
     * 获取当前风格
     *
     * @return
     */
    public static String getCurrentStyle()
    {
        return CURRENT_STYLE;
    }
}
