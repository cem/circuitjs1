package com.lushprojects.circuitjs1.client.gui;

import com.google.gwt.storage.client.Storage;

public class OptionsStorage {
    public Boolean get(Options.Type type, boolean val) {
	String key = type.toString();
	Storage stor = Storage.getLocalStorageIfSupported();
	if (stor == null)
	    return val;
	String s = stor.getItem(key);
	if (s == null)
	    return val;
	return s == "true";
    }
    
    public Boolean get(Options.Type type) {
	String key = type.toString();
	Storage stor = Storage.getLocalStorageIfSupported();
	if (stor == null)
	    return null;
	String s = stor.getItem(key);
	if (s == null)
	    return null;
	return s == "true";
    }

    public void set(Options.Type type, boolean val) {
	String key = type.toString();
	Storage stor = Storage.getLocalStorageIfSupported();
	if (stor == null)
	    return;
	stor.setItem(key, val ? "true" : "false");
    }
}
