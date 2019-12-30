package com.lushprojects.circuitjs1.client.gui;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.MenuItem;
import com.lushprojects.circuitjs1.client.MyCommand;

public class MenuHelper {
    static MenuItem menuItemWithShortcut(String text, String shortcut, MyCommand cmd) {
	final String edithtml = "<div style=\"display:inline-block;width:80px;\">";
	String sn=edithtml + text + "</div>" + shortcut;
	return new MenuItem(SafeHtmlUtils.fromTrustedString(sn), cmd);
    }
}
