package com.lushprojects.circuitjs1.client.gui;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.Callable;

import com.lushprojects.circuitjs1.client.CheckboxMenuItem;

public class Options {
    public enum Type {
	SHOW_CURRENT_DOTS,
	SHOW_VOLTAGE_COLORS,
	SMALL_GRID,
	CROSS_HAIR,
	SHOW_VALUES,
	CONDUCTANCE,
	EURO_RESISTOR,
	EURO_GATES,
	PRINTABLE,
	ALTERNATIVE_COLOR,
	CONVENTION, 
    }
    
    static final int size = Type.values().length;
    
    static final EnumSet<Type> persistSet = EnumSet.of(
	Type.CROSS_HAIR,
	Type.EURO_RESISTOR,
	Type.EURO_GATES,
	Type.PRINTABLE,
	Type.ALTERNATIVE_COLOR,
	Type.CONVENTION
    );
    
    boolean[] options = new boolean[size];;
    ArrayList<Runnable>[] listeners = new ArrayList[size];;
    boolean enablePersist = false;
    OptionsStorage storage;
    
    public Options(OptionsStorage aStorage) {
	storage = aStorage;
	for (int i = 0; i < size; ++i)
	    listeners[i] = new ArrayList();
    }

    public void set(Type t, boolean val) {
	if (options[t.ordinal()] == val)
	    return;
	options[t.ordinal()] = val;
	for (Runnable r : listeners[t.ordinal()]) {
	    r.run();
	}
	if (enablePersist && persistSet.contains(t)) {
	    storage.set(t, val);
	}
    }
    
    public void enablePersist() {
	enablePersist = true;
    }
    
    public void setAllFromStorage() {
	for (Type t : Type.values()) {
	    Boolean val = storage.get(t);
	    if (val != null)
		options[t.ordinal()] = val;
	}
    }
    
    public boolean get(Type t) {
	return options[t.ordinal()];
    }
    
    public void addListener(Type t, Runnable r) {
	listeners[t.ordinal()].add(r);
    }
}
