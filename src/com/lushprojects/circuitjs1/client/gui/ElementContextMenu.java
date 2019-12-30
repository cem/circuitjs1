package com.lushprojects.circuitjs1.client.gui;

import java.util.HashMap;

import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.lushprojects.circuitjs1.client.MyCommand;

public class ElementContextMenu {
    public MenuBar menuBar;
    public MenuItem editMenuItem;
    MenuItem cutMenuItem;
    MenuItem copyMenuItem;
    MenuItem deleteMenuItem;
    public MenuItem scopeMenuItem;
    public MenuItem floatScopeMenuItem;
    public MenuItem flipMenuItem;
    public MenuItem splitMenuItem;
    public MenuItem sliderMenuItem;
    MenuBar mainMenuBar;
    MenuItem scopeRemovePlotMenuItem;
    MenuItem scopeSelectYMenuItem;
    
    String ctrlMetaKey;
    HashMap<String,String> localizationMap;
    
    public ElementContextMenu(HashMap<String,String> aLocalizationMap) {
	localizationMap = aLocalizationMap;
	menuBar = new MenuBar(true);
	menuBar.addItem(editMenuItem = new MenuItem(LS("Edit..."),new MyCommand("elm","edit")));
	menuBar.addItem(scopeMenuItem = new MenuItem(LS("View in Scope"), new MyCommand("elm","viewInScope")));
	menuBar.addItem(floatScopeMenuItem  = new MenuItem(LS("View in Undocked Scope"), new MyCommand("elm","viewInFloatScope")));
	menuBar.addItem(cutMenuItem = new MenuItem(LS("Cut"),new MyCommand("elm","cut")));
	menuBar.addItem(copyMenuItem = new MenuItem(LS("Copy"),new MyCommand("elm","copy")));
	menuBar.addItem(deleteMenuItem = new MenuItem(LS("Delete"),new MyCommand("elm","delete")));
	menuBar.addItem(                    new MenuItem(LS("Duplicate"),new MyCommand("elm","duplicate")));
	menuBar.addItem(flipMenuItem = new MenuItem(LS("Swap Terminals"),new MyCommand("elm","flip")));
	menuBar.addItem(splitMenuItem = MenuHelper.menuItemWithShortcut(LS("Split Wire"), LS(ctrlMetaKey + "-click"), new MyCommand("elm","split")));
	menuBar.addItem(sliderMenuItem = new MenuItem(LS("Sliders..."),new MyCommand("elm","sliders")));
    }
    
    String LS(String s) {
	if (s == null)
	    return null;
	String sm = localizationMap.get(s);
	if (sm != null)
	    return sm;
	
	// use trailing ~ to differentiate strings that are the same in English but need different translations.
	// remove these if there's no translation.
	int ix = s.indexOf('~');
	if (ix < 0)
	    return s;
	s = s.substring(0, ix);
	sm = localizationMap.get(s);
	if (sm != null)
	    return sm;
	return s;
    }
}
