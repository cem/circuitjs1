package com.lushprojects.circuitjs1.client.gui;

import java.util.HashMap;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.lushprojects.circuitjs1.client.CheckboxAlignedMenuItem;
import com.lushprojects.circuitjs1.client.CheckboxMenuItem;
import com.lushprojects.circuitjs1.client.CirSim;
import com.lushprojects.circuitjs1.client.CircuitElm;
import com.lushprojects.circuitjs1.client.ExportAsLocalFileDialog;
import com.lushprojects.circuitjs1.client.LoadFile;
import com.lushprojects.circuitjs1.client.MyCommand;

public class TopMenu {
    public String shortcuts[];
    public Vector<CheckboxMenuItem> mainMenuItems = new Vector<CheckboxMenuItem>();
    public Vector<String> mainMenuItemNames = new Vector<String>();
    Options options;
    
    public MenuBar menuBar;
    MenuItem aboutItem;
    
    MenuBar fileMenuBar;
    MenuItem importFromLocalFileItem, importFromTextItem, exportAsUrlItem, exportAsLocalFileItem, exportAsTextItem,
	    printItem;
    public MenuItem recoverItem;
    MenuItem importFromDropboxItem;
    
    MenuBar editMenuBar;
    public MenuItem undoItem;
    public MenuItem redoItem;
    MenuItem cutItem;
    MenuItem copyItem;
    public MenuItem pasteItem;
    MenuItem selectAllItem;
    MenuItem optionsItem;
    
    MenuBar drawMenuBar;
    
    MenuBar scopesMenuBar;
    
    MenuBar optionsMenuBar;
    CheckboxMenuItem dotsCheckItem;
    CheckboxMenuItem voltsCheckItem;
    CheckboxMenuItem powerCheckItem;
    CheckboxMenuItem smallGridCheckItem;
    CheckboxMenuItem crossHairCheckItem;
    CheckboxMenuItem showValuesCheckItem;
    CheckboxMenuItem conductanceCheckItem;
    CheckboxMenuItem euroResistorCheckItem;
    CheckboxMenuItem euroGatesCheckItem;
    CheckboxMenuItem printableCheckItem;
    CheckboxMenuItem alternativeColorCheckItem;
    CheckboxMenuItem conventionCheckItem;

    String ctrlMetaKey;
    HashMap<String,String> localizationMap;
    CirSim cirSim;
    boolean isMac;
    
    public TopMenu(CirSim aCirSim, Options aOptions, HashMap<String,String> aLocalizationMap) {
	cirSim = aCirSim;
	options = aOptions;
	localizationMap = aLocalizationMap;
	
	String os = Navigator.getPlatform();
	isMac = (os.toLowerCase().contains("mac"));
	ctrlMetaKey = (isMac) ? "Cmd" : "Ctrl";
    }
    
    public void init() {
	loadShortcuts();
	
	fileMenuBar = new MenuBar(true);
	importFromLocalFileItem = new MenuItem(LS("Open File..."), new MyCommand("file", "importfromlocalfile"));
	importFromLocalFileItem.setEnabled(LoadFile.isSupported());
	fileMenuBar.addItem(importFromLocalFileItem);
	importFromTextItem = new MenuItem(LS("Import From Text..."), new MyCommand("file", "importfromtext"));
	fileMenuBar.addItem(importFromTextItem);
	importFromDropboxItem = new MenuItem(LS("Import From Dropbox..."), new MyCommand("file", "importfromdropbox"));
	fileMenuBar.addItem(importFromDropboxItem);
	exportAsLocalFileItem = new MenuItem(LS("Save As..."), new MyCommand("file", "exportaslocalfile"));
	exportAsLocalFileItem.setEnabled(ExportAsLocalFileDialog.downloadIsSupported());
	fileMenuBar.addItem(exportAsLocalFileItem);
	exportAsUrlItem = new MenuItem(LS("Export As Link..."), new MyCommand("file", "exportasurl"));
	fileMenuBar.addItem(exportAsUrlItem);
	exportAsTextItem = new MenuItem(LS("Export As Text..."), new MyCommand("file", "exportastext"));
	fileMenuBar.addItem(exportAsTextItem);
	fileMenuBar.addItem(new MenuItem(LS("Export As Image..."), new MyCommand("file", "exportasimage")));
	fileMenuBar.addItem(new MenuItem(LS("Create Subcircuit..."), new MyCommand("file", "createsubcircuit")));
	fileMenuBar.addItem(new MenuItem(LS("Find DC Operating Point"), new MyCommand("file", "dcanalysis")));
	recoverItem = new MenuItem(LS("Recover Auto-Save"), new MyCommand("file", "recover"));
	recoverItem.setEnabled(cirSim.recovery != null);
	fileMenuBar.addItem(recoverItem);
	printItem = new MenuItem(LS("Print..."), new MyCommand("file", "print"));
	fileMenuBar.addItem(printItem);
	fileMenuBar.addSeparator();
	aboutItem = new MenuItem(LS("About..."), (Command) null);
	fileMenuBar.addItem(aboutItem);
	aboutItem.setScheduledCommand(new MyCommand("file", "about"));
	
	editMenuBar = new MenuBar(true);
	editMenuBar.addItem(undoItem = MenuHelper.menuItemWithShortcut(LS("Undo"), LS("Ctrl-Z"), new MyCommand("edit", "undo")));
	editMenuBar.addItem(redoItem = MenuHelper.menuItemWithShortcut(LS("Redo"), LS("Ctrl-Y"), new MyCommand("edit", "redo")));
	editMenuBar.addSeparator();
	editMenuBar.addItem(cutItem = MenuHelper.menuItemWithShortcut(LS("Cut"), LS("Ctrl-X"), new MyCommand("edit", "cut")));
	editMenuBar.addItem(copyItem = MenuHelper.menuItemWithShortcut(LS("Copy"), LS("Ctrl-C"), new MyCommand("edit", "copy")));
	editMenuBar.addItem(pasteItem = MenuHelper.menuItemWithShortcut(LS("Paste"), LS("Ctrl-V"), new MyCommand("edit", "paste")));
	pasteItem.setEnabled(false);
	editMenuBar.addItem(MenuHelper.menuItemWithShortcut(LS("Duplicate"), LS("Ctrl-D"), new MyCommand("edit", "duplicate")));
	editMenuBar.addSeparator();
	editMenuBar.addItem(selectAllItem = MenuHelper.menuItemWithShortcut(LS("Select All"), LS("Ctrl-A"),
		new MyCommand("edit", "selectAll")));
	editMenuBar.addSeparator();
	editMenuBar.addItem(new MenuItem(cirSim.weAreInUS() ? LS("Center Circuit") : LS("Centre Circuit"),
		new MyCommand("edit", "centrecircuit")));
	editMenuBar.addItem(MenuHelper.menuItemWithShortcut(LS("Zoom 100%"), "0", new MyCommand("edit", "zoom100")));
	editMenuBar.addItem(MenuHelper.menuItemWithShortcut(LS("Zoom In"), "+", new MyCommand("edit", "zoomin")));
	editMenuBar.addItem(MenuHelper.menuItemWithShortcut(LS("Zoom Out"), "-", new MyCommand("edit", "zoomout")));
	
	scopesMenuBar = new MenuBar(true);
	scopesMenuBar.addItem(new MenuItem(LS("Stack All"), new MyCommand("scopes", "stackAll")));
	scopesMenuBar.addItem(new MenuItem(LS("Unstack All"), new MyCommand("scopes", "unstackAll")));
	scopesMenuBar.addItem(new MenuItem(LS("Combine All"), new MyCommand("scopes", "combineAll")));
	
	optionsMenuBar = new MenuBar(true);
	buildOptionsMenu(optionsMenuBar);

	drawMenuBar = new MenuBar(true);
	buildDrawMenu(getDrawMenuBar());
	
	menuBar = new MenuBar();
	menuBar.addItem(LS("File"), fileMenuBar);
	menuBar.addItem(LS("Edit"), editMenuBar);
	menuBar.addItem(LS("Draw"), getDrawMenuBar());
	menuBar.addItem(LS("Scopes"), scopesMenuBar);
	menuBar.addItem(LS("Options"), optionsMenuBar);
	
	menuBar.addDomHandler(new ClickHandler() {
	    public void onClick(ClickEvent event) {
		doMainMenuChecks();
	    }
	}, ClickEvent.getType());
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
    
    CheckboxMenuItem getClassCheckItem(String s, String t) {
    	// try {
    	//   Class c = Class.forName(t);
    	String shortcut="";
    	CircuitElm elm = CirSim.constructElement(t, 0, 0);
    	CheckboxMenuItem mi;
    	//  register(c, elm);
    	if ( elm!=null ) {
    		if (elm.needsShortcut() ) {
    			shortcut += (char)elm.getShortcut();
    			shortcuts[elm.getShortcut()]=t;
    		}
    		elm.delete();
    	}
//    	else
//    		GWT.log("Coudn't create class: "+t);
    	//	} catch (Exception ee) {
    	//	    ee.printStackTrace();
    	//	}
    	if (shortcut=="")
    		mi= new CheckboxMenuItem(s);
    	else
    		mi = new CheckboxMenuItem(s, shortcut);
    	mi.setScheduledCommand(new MyCommand("main", t) );
    	mainMenuItems.add(mi);
    	mainMenuItemNames.add(t);
    	return mi;
    }
    
    public void buildDrawMenu(MenuBar bar) {
    	bar.addItem(getClassCheckItem(LS("Add Wire"), "WireElm"));
    	bar.addItem(getClassCheckItem(LS("Add Resistor"), "ResistorElm"));

    	MenuBar passiveMenuBar = new MenuBar(true);
    	passiveMenuBar.addItem(getClassCheckItem(LS("Add Capacitor"), "CapacitorElm"));
    	passiveMenuBar.addItem(getClassCheckItem(LS("Add Capacitor (polarized)"), "PolarCapacitorElm"));
    	passiveMenuBar.addItem(getClassCheckItem(LS("Add Inductor"), "InductorElm"));
    	passiveMenuBar.addItem(getClassCheckItem(LS("Add Switch"), "SwitchElm"));
    	passiveMenuBar.addItem(getClassCheckItem(LS("Add Push Switch"), "PushSwitchElm"));
    	passiveMenuBar.addItem(getClassCheckItem(LS("Add SPDT Switch"), "Switch2Elm"));
    	passiveMenuBar.addItem(getClassCheckItem(LS("Add Potentiometer"), "PotElm"));
    	passiveMenuBar.addItem(getClassCheckItem(LS("Add Transformer"), "TransformerElm"));
    	passiveMenuBar.addItem(getClassCheckItem(LS("Add Tapped Transformer"), "TappedTransformerElm"));
    	passiveMenuBar.addItem(getClassCheckItem(LS("Add Transmission Line"), "TransLineElm"));
    	passiveMenuBar.addItem(getClassCheckItem(LS("Add Relay"), "RelayElm"));
    	passiveMenuBar.addItem(getClassCheckItem(LS("Add Memristor"), "MemristorElm"));
    	passiveMenuBar.addItem(getClassCheckItem(LS("Add Spark Gap"), "SparkGapElm"));
    	passiveMenuBar.addItem(getClassCheckItem(LS("Add Fuse"), "FuseElm"));
    	passiveMenuBar.addItem(getClassCheckItem(LS("Add Custom Transformer"), "CustomTransformerElm"));
    	bar.addItem(SafeHtmlUtils.fromTrustedString(CheckboxMenuItem.checkBoxHtml+LS("&nbsp;</div>Passive Components")), passiveMenuBar);

    	MenuBar inputMenuBar = new MenuBar(true);
    	inputMenuBar.addItem(getClassCheckItem(LS("Add Ground"), "GroundElm"));
    	inputMenuBar.addItem(getClassCheckItem(LS("Add Voltage Source (2-terminal)"), "DCVoltageElm"));
    	inputMenuBar.addItem(getClassCheckItem(LS("Add A/C Voltage Source (2-terminal)"), "ACVoltageElm"));
    	inputMenuBar.addItem(getClassCheckItem(LS("Add Voltage Source (1-terminal)"), "RailElm"));
    	inputMenuBar.addItem(getClassCheckItem(LS("Add A/C Voltage Source (1-terminal)"), "ACRailElm"));
    	inputMenuBar.addItem(getClassCheckItem(LS("Add Square Wave Source (1-terminal)"), "SquareRailElm"));
    	inputMenuBar.addItem(getClassCheckItem(LS("Add Clock"), "ClockElm"));
    	inputMenuBar.addItem(getClassCheckItem(LS("Add A/C Sweep"), "SweepElm"));
    	inputMenuBar.addItem(getClassCheckItem(LS("Add Variable Voltage"), "VarRailElm"));
    	inputMenuBar.addItem(getClassCheckItem(LS("Add Antenna"), "AntennaElm"));
    	inputMenuBar.addItem(getClassCheckItem(LS("Add AM Source"), "AMElm"));
    	inputMenuBar.addItem(getClassCheckItem(LS("Add FM Source"), "FMElm"));
    	inputMenuBar.addItem(getClassCheckItem(LS("Add Current Source"), "CurrentElm"));
    	inputMenuBar.addItem(getClassCheckItem(LS("Add Noise Generator"), "NoiseElm"));
    	inputMenuBar.addItem(getClassCheckItem(LS("Add Audio Input"), "AudioInputElm"));

    	bar.addItem(SafeHtmlUtils.fromTrustedString(CheckboxMenuItem.checkBoxHtml+LS("&nbsp;</div>Inputs and Sources")), inputMenuBar);
    	
    	MenuBar outputMenuBar = new MenuBar(true);
    	outputMenuBar.addItem(getClassCheckItem(LS("Add Analog Output"), "OutputElm"));
    	outputMenuBar.addItem(getClassCheckItem(LS("Add LED"), "LEDElm"));
    	outputMenuBar.addItem(getClassCheckItem(LS("Add Lamp"), "LampElm"));
    	outputMenuBar.addItem(getClassCheckItem(LS("Add Text"), "TextElm"));
    	outputMenuBar.addItem(getClassCheckItem(LS("Add Box"), "BoxElm"));
    	outputMenuBar.addItem(getClassCheckItem(LS("Add Voltmeter/Scobe Probe"), "ProbeElm"));
    	outputMenuBar.addItem(getClassCheckItem(LS("Add Ohmmeter"), "OhmMeterElm"));
    	outputMenuBar.addItem(getClassCheckItem(LS("Add Labeled Node"), "LabeledNodeElm"));
    	outputMenuBar.addItem(getClassCheckItem(LS("Add Test Point"), "TestPointElm"));
    	outputMenuBar.addItem(getClassCheckItem(LS("Add Ammeter"), "AmmeterElm"));
    	outputMenuBar.addItem(getClassCheckItem(LS("Add Data Export"), "DataRecorderElm"));
    	outputMenuBar.addItem(getClassCheckItem(LS("Add Audio Output"), "AudioOutputElm"));
    	outputMenuBar.addItem(getClassCheckItem(LS("Add LED Array"), "LEDArrayElm"));
    	outputMenuBar.addItem(getClassCheckItem(LS("Add Stop Trigger"), "StopTriggerElm"));
    	bar.addItem(SafeHtmlUtils.fromTrustedString(CheckboxMenuItem.checkBoxHtml+LS("&nbsp;</div>Outputs and Labels")), outputMenuBar);
    	
    	MenuBar activeMenuBar = new MenuBar(true);
    	activeMenuBar.addItem(getClassCheckItem(LS("Add Diode"), "DiodeElm"));
    	activeMenuBar.addItem(getClassCheckItem(LS("Add Zener Diode"), "ZenerElm"));
    	activeMenuBar.addItem(getClassCheckItem(LS("Add Transistor (bipolar, NPN)"), "NTransistorElm"));
    	activeMenuBar.addItem(getClassCheckItem(LS("Add Transistor (bipolar, PNP)"), "PTransistorElm"));
    	activeMenuBar.addItem(getClassCheckItem(LS("Add MOSFET (N-Channel)"), "NMosfetElm"));
    	activeMenuBar.addItem(getClassCheckItem(LS("Add MOSFET (P-Channel)"), "PMosfetElm"));
    	activeMenuBar.addItem(getClassCheckItem(LS("Add JFET (N-Channel)"), "NJfetElm"));
    	activeMenuBar.addItem(getClassCheckItem(LS("Add JFET (P-Channel)"), "PJfetElm"));
    	activeMenuBar.addItem(getClassCheckItem(LS("Add SCR"), "SCRElm"));
    	activeMenuBar.addItem(getClassCheckItem(LS("Add DIAC"), "DiacElm"));
    	activeMenuBar.addItem(getClassCheckItem(LS("Add TRIAC"), "TriacElm"));
    	activeMenuBar.addItem(getClassCheckItem(LS("Add Darlington Pair (NPN)"), "NDarlingtonElm"));
    	activeMenuBar.addItem(getClassCheckItem(LS("Add Darlington Pair (PNP)"), "PDarlingtonElm"));
    	activeMenuBar.addItem(getClassCheckItem(LS("Add Varactor/Varicap"), "VaractorElm"));
    	activeMenuBar.addItem(getClassCheckItem(LS("Add Tunnel Diode"), "TunnelDiodeElm"));
    	activeMenuBar.addItem(getClassCheckItem(LS("Add Triode"), "TriodeElm"));
    	//    	activeMenuBar.addItem(getClassCheckItem("Add Photoresistor", "PhotoResistorElm"));
    	//    	activeMenuBar.addItem(getClassCheckItem("Add Thermistor", "ThermistorElm"));
    	bar.addItem(SafeHtmlUtils.fromTrustedString(CheckboxMenuItem.checkBoxHtml+LS("&nbsp;</div>Active Components")), activeMenuBar);

    	MenuBar activeBlocMenuBar = new MenuBar(true);
    	activeBlocMenuBar.addItem(getClassCheckItem(LS("Add Op Amp (ideal, - on top)"), "OpAmpElm"));
    	activeBlocMenuBar.addItem(getClassCheckItem(LS("Add Op Amp (ideal, + on top)"), "OpAmpSwapElm"));
    	activeBlocMenuBar.addItem(getClassCheckItem(LS("Add Op Amp (real)"), "OpAmpRealElm"));
    	activeBlocMenuBar.addItem(getClassCheckItem(LS("Add Analog Switch (SPST)"), "AnalogSwitchElm"));
    	activeBlocMenuBar.addItem(getClassCheckItem(LS("Add Analog Switch (SPDT)"), "AnalogSwitch2Elm"));
    	activeBlocMenuBar.addItem(getClassCheckItem(LS("Add Tristate Buffer"), "TriStateElm"));
    	activeBlocMenuBar.addItem(getClassCheckItem(LS("Add Schmitt Trigger"), "SchmittElm"));
    	activeBlocMenuBar.addItem(getClassCheckItem(LS("Add Schmitt Trigger (Inverting)"), "InvertingSchmittElm"));
    	activeBlocMenuBar.addItem(getClassCheckItem(LS("Add CCII+"), "CC2Elm"));
    	activeBlocMenuBar.addItem(getClassCheckItem(LS("Add CCII-"), "CC2NegElm"));
    	activeBlocMenuBar.addItem(getClassCheckItem(LS("Add Comparator (Hi-Z/GND output)"), "ComparatorElm"));
    	activeBlocMenuBar.addItem(getClassCheckItem(LS("Add OTA (LM13700 style)"), "OTAElm"));
    	activeBlocMenuBar.addItem(getClassCheckItem(LS("Add Voltage-Controlled Voltage Source"), "VCVSElm"));
    	activeBlocMenuBar.addItem(getClassCheckItem(LS("Add Voltage-Controlled Current Source"), "VCCSElm"));
    	activeBlocMenuBar.addItem(getClassCheckItem(LS("Add Current-Controlled Voltage Source"), "CCVSElm"));
    	activeBlocMenuBar.addItem(getClassCheckItem(LS("Add Current-Controlled Current Source"), "CCCSElm"));
    	activeBlocMenuBar.addItem(getClassCheckItem(LS("Add Optocoupler"), "OptocouplerElm"));
    	activeBlocMenuBar.addItem(getClassCheckItem(LS("Add Subcircuit Instance"), "CustomCompositeElm"));
    	bar.addItem(SafeHtmlUtils.fromTrustedString(CheckboxMenuItem.checkBoxHtml+LS("&nbsp;</div>Active Building Blocks")), activeBlocMenuBar);
    	
    	MenuBar gateMenuBar = new MenuBar(true);
    	gateMenuBar.addItem(getClassCheckItem(LS("Add Logic Input"), "LogicInputElm"));
    	gateMenuBar.addItem(getClassCheckItem(LS("Add Logic Output"), "LogicOutputElm"));
    	gateMenuBar.addItem(getClassCheckItem(LS("Add Inverter"), "InverterElm"));
    	gateMenuBar.addItem(getClassCheckItem(LS("Add NAND Gate"), "NandGateElm"));
    	gateMenuBar.addItem(getClassCheckItem(LS("Add NOR Gate"), "NorGateElm"));
    	gateMenuBar.addItem(getClassCheckItem(LS("Add AND Gate"), "AndGateElm"));
    	gateMenuBar.addItem(getClassCheckItem(LS("Add OR Gate"), "OrGateElm"));
    	gateMenuBar.addItem(getClassCheckItem(LS("Add XOR Gate"), "XorGateElm"));
    	bar.addItem(SafeHtmlUtils.fromTrustedString(CheckboxMenuItem.checkBoxHtml+LS("&nbsp;</div>Logic Gates, Input and Output")), gateMenuBar);

    	MenuBar chipMenuBar = new MenuBar(true);
    	chipMenuBar.addItem(getClassCheckItem(LS("Add D Flip-Flop"), "DFlipFlopElm"));
    	chipMenuBar.addItem(getClassCheckItem(LS("Add JK Flip-Flop"), "JKFlipFlopElm"));
    	chipMenuBar.addItem(getClassCheckItem(LS("Add T Flip-Flop"), "TFlipFlopElm"));
    	chipMenuBar.addItem(getClassCheckItem(LS("Add 7 Segment LED"), "SevenSegElm"));
    	chipMenuBar.addItem(getClassCheckItem(LS("Add 7 Segment Decoder"), "SevenSegDecoderElm"));
    	chipMenuBar.addItem(getClassCheckItem(LS("Add Multiplexer"), "MultiplexerElm"));
    	chipMenuBar.addItem(getClassCheckItem(LS("Add Demultiplexer"), "DeMultiplexerElm"));
    	chipMenuBar.addItem(getClassCheckItem(LS("Add SIPO shift register"), "SipoShiftElm"));
    	chipMenuBar.addItem(getClassCheckItem(LS("Add PISO shift register"), "PisoShiftElm"));
    	chipMenuBar.addItem(getClassCheckItem(LS("Add Counter"), "CounterElm"));
    	chipMenuBar.addItem(getClassCheckItem(LS("Add Ring Counter"), "DecadeElm"));
    	chipMenuBar.addItem(getClassCheckItem(LS("Add Latch"), "LatchElm"));
    	//chipMenuBar.addItem(getClassCheckItem("Add Static RAM", "SRAMElm"));
    	chipMenuBar.addItem(getClassCheckItem(LS("Add Sequence generator"), "SeqGenElm"));
    	chipMenuBar.addItem(getClassCheckItem(LS("Add Full Adder"), "FullAdderElm"));
    	chipMenuBar.addItem(getClassCheckItem(LS("Add Half Adder"), "HalfAdderElm"));
    	chipMenuBar.addItem(getClassCheckItem(LS("Add Custom Logic"), "UserDefinedLogicElm")); // don't change this, it will break people's saved shortcuts
    	bar.addItem(SafeHtmlUtils.fromTrustedString(CheckboxMenuItem.checkBoxHtml+LS("&nbsp;</div>Digital Chips")), chipMenuBar);
    	
    	MenuBar achipMenuBar = new MenuBar(true);
    	achipMenuBar.addItem(getClassCheckItem(LS("Add 555 Timer"), "TimerElm"));
    	achipMenuBar.addItem(getClassCheckItem(LS("Add Phase Comparator"), "PhaseCompElm"));
    	achipMenuBar.addItem(getClassCheckItem(LS("Add DAC"), "DACElm"));
    	achipMenuBar.addItem(getClassCheckItem(LS("Add ADC"), "ADCElm"));
    	achipMenuBar.addItem(getClassCheckItem(LS("Add VCO"), "VCOElm"));
    	achipMenuBar.addItem(getClassCheckItem(LS("Add Monostable"), "MonostableElm"));
    	bar.addItem(SafeHtmlUtils.fromTrustedString(CheckboxMenuItem.checkBoxHtml+LS("&nbsp;</div>Analog and Hybrid Chips")), achipMenuBar);
    	
    	MenuBar otherMenuBar = new MenuBar(true);
    	CheckboxMenuItem mi;
    	otherMenuBar.addItem(mi=getClassCheckItem(LS("Drag All"), "DragAll"));
    	mi.setShortcut(LS("(Alt-drag)"));
    	otherMenuBar.addItem(mi=getClassCheckItem(LS("Drag Row"), "DragRow"));
    	mi.setShortcut(LS("(A-S-drag)"));
    	otherMenuBar.addItem(mi=getClassCheckItem(LS("Drag Column"), "DragColumn"));
    	mi.setShortcut(isMac ? LS("(A-Cmd-drag)") : LS("(A-M-drag)"));
    	otherMenuBar.addItem(getClassCheckItem(LS("Drag Selected"), "DragSelected"));
    	otherMenuBar.addItem(mi=getClassCheckItem(LS("Drag Post"), "DragPost"));
    	mi.setShortcut("(" + ctrlMetaKey + "-drag)");

    	bar.addItem(SafeHtmlUtils.fromTrustedString(CheckboxMenuItem.checkBoxHtml+LS("&nbsp;</div>Drag")), otherMenuBar);

    	bar.addItem(mi=getClassCheckItem(LS("Select/Drag Sel"), "Select"));
    	mi.setShortcut(LS("(space or Shift-drag)"));
    }
    
    void buildOptionsMenu(MenuBar m) {
	m.addItem(dotsCheckItem = new CheckboxMenuItem(LS("Show Current"), new Command() {
	    public void execute() {
		options.set(Options.Type.SHOW_CURRENT_DOTS, dotsCheckItem.getState());
	    }
	}));
	dotsCheckItem.setState(options.get(Options.Type.SHOW_CURRENT_DOTS));
	
	m.addItem(voltsCheckItem = new CheckboxMenuItem(LS("Show Voltage"), new Command() {
	    public void execute() {
		boolean voltsSet = voltsCheckItem.getState();
		powerCheckItem.setState(!voltsSet);
		options.set(Options.Type.SHOW_VOLTAGE_COLORS, voltsSet);
	    }
	}));
	voltsCheckItem.setState(options.get(Options.Type.SHOW_VOLTAGE_COLORS));
	
	m.addItem(powerCheckItem = new CheckboxMenuItem(LS("Show Power"), new Command() {
	    public void execute() {
		boolean powerSet = powerCheckItem.getState();
		voltsCheckItem.setState(!powerSet);
		options.set(Options.Type.SHOW_VOLTAGE_COLORS, !powerSet);
	    }
	}));
	powerCheckItem.setState(!voltsCheckItem.getState());
	
	m.addItem(showValuesCheckItem = new CheckboxMenuItem(LS("Show Values"), new Command() {
	    public void execute() {
		options.set(Options.Type.SHOW_VALUES, showValuesCheckItem.getState());
	    }
	}));
	showValuesCheckItem.setState(options.get(Options.Type.SHOW_VALUES));
	
	m.addItem(smallGridCheckItem = new CheckboxMenuItem(LS("Small Grid"), new Command() {
	    public void execute() {
		options.set(Options.Type.SMALL_GRID, smallGridCheckItem.getState());
	    }
	}));
	smallGridCheckItem.setState(options.get(Options.Type.SMALL_GRID));
	
	m.addItem(crossHairCheckItem = new CheckboxMenuItem(LS("Show Cursor Cross Hairs"), new Command() {
	    public void execute() {
		options.set(Options.Type.CROSS_HAIR, crossHairCheckItem.getState());
	    }
	}));
	crossHairCheckItem.setState(options.get(Options.Type.CROSS_HAIR));
	
	m.addItem(euroResistorCheckItem = new CheckboxMenuItem(LS("European Resistors"), new Command() {
	    public void execute() {
		options.set(Options.Type.EURO_RESISTOR, euroResistorCheckItem.getState());
	    }
	}));
	euroResistorCheckItem.setState(options.get(Options.Type.EURO_RESISTOR));
	
	m.addItem(euroGatesCheckItem = new CheckboxMenuItem(LS("IEC Gates"), new Command() {
	    public void execute() {
		options.set(Options.Type.EURO_GATES, euroGatesCheckItem.getState());
	    }
	}));
	euroGatesCheckItem.setState(options.get(Options.Type.EURO_GATES));
	
	m.addItem(printableCheckItem = new CheckboxMenuItem(LS("White Background"), new Command() {
	    public void execute() {
		options.set(Options.Type.PRINTABLE, printableCheckItem.getState());
	    }
	}));
	printableCheckItem.setState(options.get(Options.Type.PRINTABLE));
	
	m.addItem(alternativeColorCheckItem = new CheckboxMenuItem(LS("Alt Color for Volts & Pwr"), new Command() {
	    public void execute() {
		options.set(Options.Type.ALTERNATIVE_COLOR, alternativeColorCheckItem.getState());
	    }
	}));
	alternativeColorCheckItem.setState(options.get(Options.Type.ALTERNATIVE_COLOR));

	m.addItem(conventionCheckItem = new CheckboxMenuItem(LS("Conventional Current Motion"), new Command() {
	    public void execute() {
		options.set(Options.Type.CONVENTION, conventionCheckItem.getState());
	    }
	}));
	conventionCheckItem.setState(options.get(Options.Type.CONVENTION));

	m.addItem(new CheckboxAlignedMenuItem(LS("Shortcuts..."), new MyCommand("options", "shortcuts")));
	m.addItem(optionsItem = new CheckboxAlignedMenuItem(LS("Other Options..."), new MyCommand("options", "other")));
    }
    
    // load shortcuts from local storage
    void loadShortcuts() {
	// clear existing shortcuts
	shortcuts = new String[127];
	
	Storage stor = Storage.getLocalStorageIfSupported();
	if (stor == null)
	    return;
	String str = stor.getItem("shortcuts");
	if (str == null)
	    return;
	String keys[] = str.split(";");

	// clear shortcuts from menu
	for (int i = 0; i != mainMenuItems.size(); i++) {
	    CheckboxMenuItem item = mainMenuItems.get(i);
	    // stop when we get to drag menu items
	    if (item.getShortcut().length() > 1)
		break;
	    item.setShortcut("");
	}

	// go through keys (skipping version at start)
	for (int i = 1; i < keys.length; i++) {
	    String arr[] = keys[i].split("=");
	    if (arr.length != 2)
		continue;
	    int c = Integer.parseInt(arr[0]);
	    String className = arr[1];
	    shortcuts[c] = className;

	    // find menu item and fix it
	    int j;
	    for (j = 0; j != mainMenuItems.size(); j++) {
		if (mainMenuItemNames.get(j) == className) {
		    CheckboxMenuItem item = mainMenuItems.get(j);
		    item.setShortcut(Character.toString((char) c));
		    break;
		}
	    }
	}
    }
    
    // save shortcuts to local storage
    public void saveShortcuts() {
	Storage stor = Storage.getLocalStorageIfSupported();
	if (stor == null)
	    return;
	String str = "1";
	int i;
	// format: version;code1=ClassName;code2=ClassName;etc
	for (i = 0; i != shortcuts.length; i++) {
	    String sh = shortcuts[i];
	    if (sh == null)
		continue;
	    str += ";" + i + "=" + sh;
	}
	stor.setItem("shortcuts", str);
    }
    
    public void doMainMenuChecks() {
	int c = mainMenuItems.size();
	for (int i = 0; i < c; i++)
	    mainMenuItems.get(i).setState(mainMenuItemNames.get(i) == cirSim.mouseMode());
    }
    
    public int dumpOptions() {
	int f = (dotsCheckItem.getState()) ? 1 : 0;
	f |= (smallGridCheckItem.getState()) ? 2 : 0;
	f |= (voltsCheckItem.getState()) ? 0 : 4;
	f |= (powerCheckItem.getState()) ? 8 : 0;
	f |= (showValuesCheckItem.getState()) ? 0 : 16;
	return f;
    }

    public MenuBar getDrawMenuBar() {
	return drawMenuBar;
    }
}
