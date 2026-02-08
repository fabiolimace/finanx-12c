package net.sf.finanx.fx12c.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import net.sf.finanx.fx12c.calc.Configuration;
import net.sf.finanx.fx12c.calc.Controller;
import net.sf.finanx.fx12c.calc.Key;
import net.sf.finanx.fx12c.calc.Skin;
import net.sf.finanx.fx12c.resource.lang.StringList;
import net.sf.finanx.fx12c.resource.loader.SkinLoader;
import net.sf.finanx.fx12c.resource.menu.MenuItem;
import net.sf.finanx.fx12c.resource.menu.MenuList;
import net.sf.finanx.fx12c.utils.HexColor;

public class MainWindow {

	// Presenter object
	private Controller controller;
	
	private JFrame frame;
	private ImagePanel mainPanel, displayPanel;

	// Path to the skins directory
	private String basePath;
	// Path to the selected skin
	private String skinPath;
	// Path to the selected skin font
	private String skinFontPath;

	// HP12C Buttons
	private ImageButton B00, B01, B02, B03, B04, B05, B06, B07, B08, B09;
	private ImageButton B10, B20, B30, B40;
	private ImageButton B11, B12, B13, B14, B15, B16;
	private ImageButton B21, B22, B23, B24, B25, B26;
	private ImageButton B31, B32, B33, B34, B35, B36;
	private ImageButton B41, B42, B43, B44, B45, B48, B49;

	// Positioning Rules
	private GridBagConstraints constraintsMainPanel, constraintsDisplayPanel;
	private GridBagConstraints constraintsDisplay, constraintsFlagDisplay;
	private GridBagConstraints R00, R01, R02, R03, R04, R05, R06, R07, R08,
			R09;
	private GridBagConstraints R10, R20, R30, R40;
	private GridBagConstraints R11, R12, R13, R14, R15, R16;
	private GridBagConstraints R21, R22, R23, R24, R25, R26;
	private GridBagConstraints R31, R32, R33, R34, R35, R36;
	private GridBagConstraints R41, R42, R43, R44, R45, R48, R49;

	// Temporary objects used by private method loadImageIcon()
	private Image img;
	private ImageIcon ico;

	// Main Panel Background Image
	private Image bgImage;

	// Images over calculator's buttons
	private ImageIcon I10, I20, I30, I40;
	private ImageIcon I00, I01, I02, I03, I04, I05, I06, I07, I08, I09;
	private ImageIcon I11, I12, I13, I14, I15, I16;
	private ImageIcon I21, I22, I23, I24, I25, I26;
	private ImageIcon I31, I32, I33, I34, I35, I36;
	private ImageIcon I41, I42, I43, I44, I45, I48, I49;

	// Images over calcullator's buttons when pressed
	private ImageIcon I10p, I20p, I30p, I40p;
	private ImageIcon I00p, I01p, I02p, I03p, I04p, I05p, I06p, I07p, I08p,
			I09p;
	private ImageIcon I11p, I12p, I13p, I14p, I15p, I16p;
	private ImageIcon I21p, I22p, I23p, I24p, I25p, I26p;
	private ImageIcon I31p, I32p, I33p, I34p, I35p, I36p;
	private ImageIcon I41p, I42p, I43p, I44p, I45p, I48p, I49p;

	// Menu icons
	private ImageIcon IExit;
	private ImageIcon ICopy, IPaste, IErase, IReset;

	// Hash Tables
	private Hashtable<String, ImageButton> bot;
	private Hashtable<String, ImageIcon> imageMap;
	private Hashtable<String, ImageIcon> imageMapPressed;
	private Hashtable menu;

	// Font related variables
	private Font font;
	private int fontSize;
	private File fontFile, imageFile;
	private FileInputStream fontInput;

	// Component colors
	private Color bgColor;
	private Color faceColor;
	private Color displayBgColor;
	private Color displayFaceColor;
	private Color buttonBgColor;
	private Color buttonFaceColor;

	// Dimensions
	private int wmainpan, hmainpan;
	private int wdispan, hdispan;
	private int wbot, hbot, webot, hebot;
	private int wdis, hdis, wfdis, hfdis;

	// Paddings
	private int xpad, ypad;
	private int tdis, ldis, bdis, rdis;
	private int tfdis, lfdis, bfdis, rfdis;

	private TextField display, flagDisplay;

	// Configuration language and skin persistence
	private Configuration cfg;
	private Skin skn;
	private SkinLoader sknDao;
	private StringList strList;
	private MenuList lngList;
	private MenuList sknList;
	private MenuItem lngArray[];
	private MenuItem sknItems[];

	// Menus
	private JMenuBar menuBar;
	private JMenu mnFile, mnEdit, mnView, mnOptions, mnTools, mnAbout;
	private JMenu mnErase, mnSize, mnSkin, mnLang;
	private JMenu mnNumFormat, mnDateFormat, mnPayment;
	private JMenuItem itImport, itExport, itQuit;
	private JMenuItem itCopy, itPaste, itEraseDsp, itEraseStk, itEraseFin,
			itEraseSta, itEraseReg, itErasePrg;
	private JMenuItem itSizeVerySmall, itSizeSmall, itSizeMedium, itSizeLarge,
			itSizeHuge;
	private JMenuItem itCommaNumFormat, itDotNumFormat, itDMYDateFormat,
			itMDYDateFormat, itBegPayment, itEndPayment;
	private JMenuItem itRegistersView, itInstructionsHistory;
	private JMenuItem itAuthor, itContributors, itSoftware;
	private JMenuItem itLang[], itSkin[];
	private ButtonGroup gpNumFormat, gpDateFormat, gpPayment, gpSize, gpSkin,
			gpLang;

	private CustomMenuListener menuListener;

	// Menu item Types
	private static final int ITEM_PLAIN = 0;
	private static final int ITEM_CHECK = 1;
	private static final int ITEM_RADIO = 2;

	public static final int MODE_GOLD = 1;
	public static final int MODE_PLATINUM = 2;

	public static final double SIZE_VERY_SMALL = 0.5;
	public static final double SIZE_SMALL = 0.75;
	public static final double SIZE_MEDIUM = 1.0;
	public static final double SIZE_LARGE = 1.25;
	public static final double SIZE_HUGE = 1.5;

	public static final String CMD_FILE_MENU = "FILE_MENU";
	public static final String CMD_FILE_IMPORT = "FILE_IMPORT";
	public static final String CMD_FILE_EXPORT = "FILE_EXPORT";
	public static final String CMD_FILE_QUIT = "FILE_QUIT";

	public static final String CMD_EDIT_MENU = "EDIT_MENU";
	public static final String CMD_EDIT_COPY = "EDIT_COPY";
	public static final String CMD_EDIT_PASTE = "EDIT_PASTE";
	public static final String CMD_EDIT_ERASE = "EDIT_ERASE";
	public static final String CMD_EDIT_ERASE_DSP = "EDIT_ERASE_DSP";
	public static final String CMD_EDIT_ERASE_STK = "EDIT_ERASE_STK";
	public static final String CMD_EDIT_ERASE_FIN = "EDIT_ERASE_FIN";
	public static final String CMD_EDIT_ERASE_STA = "EDIT_ERASE_STA";
	public static final String CMD_EDIT_ERASE_REG = "EDIT_ERASE_REG";
	public static final String CMD_EDIT_ERASE_PRG = "EDIT_ERASE_PRG";

	public static final String CMD_VIEW_MENU = "VIEW_MENU";
	public static final String CMD_VIEW_SIZE = "VIEW_SIZE";
	public static final String CMD_VIEW_SIZE_VERY_SMALL = "VIEW_SIZE_VERY_SMALL";
	public static final String CMD_VIEW_SIZE_SMALL = "VIEW_SIZE_SMALL";
	public static final String CMD_VIEW_SIZE_MEDIUM = "VIEW_SIZE_MEDIUM";
	public static final String CMD_VIEW_SIZE_LARGE = "VIEW_SIZE_LARGE";
	public static final String CMD_VIEW_SIZE_HUGE = "VIEW_SIZE_HUGE";
	public static final String CMD_VIEW_SKIN = "VIEW_SKIN";
	public static final String CMD_VIEW_LANGUAGE = "VIEW_LANGUAGE";

	public static final String CMD_OPTIONS_MENU = "OPTIONS_MENU";
	public static final String CMD_OPTIONS_NUMBER_FORMAT = "OPTIONS_NUMBER_FORMAT";
	public static final String CMD_OPTIONS_NUMBER_FORMAT_DOT = "OPTIONS_NUMBER_FORMAT_DOT";
	public static final String CMD_OPTIONS_NUMBER_FORMAT_COMMA = "OPTIONS_NUMBER_FORMAT_COMMA";
	public static final String CMD_OPTIONS_DATE_FORMAT = "OPTIONS_DATE_FORMAT";
	public static final String CMD_OPTIONS_DATE_FORMAT_DAY = "OPTIONS_DATE_FORMAT_DAY";
	public static final String CMD_OPTIONS_DATE_FORMAT_MONTH = "OPTIONS_DATE_FORMAT_MONTH";
	public static final String CMD_OPTIONS_PAYMENT_MODE = "OPTIONS_PAYMENT_MODE";
	public static final String CMD_OPTIONS_PAYMENT_MODE_BEGIN = "OPTIONS_PAYMENT_MODE_BEGIN";
	public static final String CMD_OPTIONS_PAYMENT_MODE_END = "OPTIONS_PAYMENT_MODE_END";

	public static final String CMD_TOOLS_MENU = "TOOLS_MENU";
	public static final String CMD_TOOLS_REGISTERS_VIEW = "TOOLS_REGISTERS_VIEW";
	public static final String CMD_TOOLS_HISTORY = "TOOLS_HISTORY";

	public static final String CMD_ABOUT_MENU = "ABOUT_MENU";
	public static final String CMD_ABOUT_AUTHOR = "ABOUT_AUTHOR";
	public static final String CMD_ABOUT_CONTRIBUTORS = "ABOUT_CONTRIBUTORS";
	public static final String CMD_ABOUT_SOFTWARE = "ABOUT_SOFTWARE";

	public MainWindow() {
		this.cfg = new Configuration();
		this.init();
	}

	public MainWindow(Configuration cfg) {
		this.cfg = cfg;
		this.init();
	}

	public void init() {

		this.setSize(cfg.getSize());

		this.loadSkin();
		this.loadLanguage();
		
		this.build();
	}

	public void build() {
		this.findPaths();
		this.buildImageMaps();
		this.buildLayout();
		this.buildMenuBar(); // Commented to disable the Menu Bar
		this.fixWindowLocation();

		String windowTitle = getLanguageStringList().getValue("MAIN_WINDOW_TITLE") + " - v" + Configuration.VERSION;

		JFrame.setDefaultLookAndFeelDecorated(false);
		frame = new JFrame(windowTitle);
		this.setIcon();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(this.menuBar); // Commented to disable the Menu Bar
		frame.setContentPane(mainPanel);
		frame.setResizable(false);
		frame.setLocation(cfg.getXPos(), cfg.getYPos());
		frame.pack();
		frame.setVisible(true);

	}
	
	public void destruct() {
		frame.dispose();
	}

	private void setIcon(){

		Image img  = null;
		String path = skinPath + "icon.png";
		
		try {
			
			File f = new File(this.basePath + path);
			if (f.isFile()) {
				img = Toolkit.getDefaultToolkit().getImage(f.getAbsolutePath());
			} else {
				URL url = this.getClass().getResource("/resources/" + path);
				img = Toolkit.getDefaultToolkit().getImage(url);
			}
			
			frame.setIconImage(img);
		
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}		
	}
	
	private void fixWindowLocation(){
		Dimension screenSize = getScreenSize();
		
		if (cfg.getXPos() < 50)
			cfg.setXPos(0);
		else if (cfg.getYPos() < 50)
			cfg.setYPos(0);
		else if (cfg.getXPos() > screenSize.width - 50)
			cfg.setXPos(0);
		else if (cfg.getYPos() > screenSize.height - 50)
			cfg.setYPos(0);
	}
	
	private ImageIcon createImageIcon(int w, int h, String path) {
		
		try {
			
			InputStream i = loadByRelativePath(path);
			
			this.ico = new ImageIcon(ImageIO.read(i));
			this.img = ico.getImage().getScaledInstance(w, h,
					Image.SCALE_SMOOTH);
			this.ico = new ImageIcon(this.img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this.ico;
	}

	// Returns a file from the file system or from the Jar file
	public InputStream loadByRelativePath(String path) {

		File f = new File(this.basePath + path);
		
		if (f.isFile()) {
			try {
				return new FileInputStream(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			return this.getClass().getClassLoader().getResourceAsStream(path);
		}
		
		return null;
	}

	private boolean existBaseDirecory(){
		File dir = new File(this.basePath);
		return (dir.exists());
	}
	
	private void findPaths() {
		
		this.basePath = System.getProperty("user.home")+"/.finanx12c/";
		
		if (!existBaseDirecory()) {
			this.basePath = "/resources/";
		}
		
		if (cfg.getSkin() != "") {
			this.skinPath = "skins/" + cfg.getSkin() + "/";
		} else {
			this.skinPath = "skins/" + Configuration.DEFAULT_SKIN + "/";
		}
		
		this.skinFontPath = this.skinPath + "font.ttf";
	}
	
	private void buildImageMaps() {

		// Setting up background image
		bgImage = this.createImageIcon(wmainpan, hmainpan, skinPath + "background.png").getImage();
		
		// Setting up button icons
		I00 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b00.png");
		I01 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b01.png");
		I02 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b02.png");
		I03 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b03.png");
		I04 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b04.png");
		I05 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b05.png");
		I06 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b06.png");
		I07 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b07.png");
		I08 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b08.png");
		I09 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b09.png");
		I10 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b10.png");
		I11 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b11.png");
		I12 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b12.png");
		I13 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b13.png");
		I14 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b14.png");
		I15 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b15.png");
		I16 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b16.png");
		I20 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b20.png");
		I21 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b21.png");
		I22 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b22.png");
		I23 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b23.png");
		I24 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b24.png");
		I25 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b25.png");
		I26 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b26.png");
		I30 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b30.png");
		I31 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b31.png");
		I32 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b32.png");
		I33 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b33.png");
		I34 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b34.png");
		I35 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b35.png");
		I36 = this.createImageIcon(webot, hebot, skinPath + "buttons/b36.png");
		I40 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b40.png");
		I41 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b41.png");
		I42 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b42.png");
		I43 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b43.png");
		I44 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b44.png");
		I45 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b45.png");
		I48 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b48.png");
		I49 = this.createImageIcon(wbot, hbot, skinPath + "buttons/b49.png");

		// Setting up button icons when pressed
		I00p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b00p.png");
		I01p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b01p.png");
		I02p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b02p.png");
		I03p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b03p.png");
		I04p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b04p.png");
		I05p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b05p.png");
		I06p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b06p.png");
		I07p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b07p.png");
		I08p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b08p.png");
		I09p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b09p.png");
		I10p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b10p.png");
		I11p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b11p.png");
		I12p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b12p.png");
		I13p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b13p.png");
		I14p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b14p.png");
		I15p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b15p.png");
		I16p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b16p.png");
		I20p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b20p.png");
		I21p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b21p.png");
		I22p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b22p.png");
		I23p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b23p.png");
		I24p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b24p.png");
		I25p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b25p.png");
		I26p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b26p.png");
		I30p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b30p.png");
		I31p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b31p.png");
		I32p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b32p.png");
		I33p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b33p.png");
		I34p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b34p.png");
		I35p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b35p.png");
		I36p = this.createImageIcon(webot, hebot, skinPath + "buttons/b36p.png");
		I40p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b40p.png");
		I41p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b41p.png");
		I42p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b42p.png");
		I43p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b43p.png");
		I44p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b44p.png");
		I45p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b45p.png");
		I48p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b48p.png");
		I49p = this.createImageIcon(wbot, hbot, skinPath + "buttons/b49p.png");

		imageMap = new Hashtable<String, ImageIcon>();
		imageMap.put(Key.KEY_00.name(), I00);
		imageMap.put(Key.KEY_01.name(), I01);
		imageMap.put(Key.KEY_02.name(), I02);
		imageMap.put(Key.KEY_03.name(), I03);
		imageMap.put(Key.KEY_04.name(), I04);
		imageMap.put(Key.KEY_05.name(), I05);
		imageMap.put(Key.KEY_06.name(), I06);
		imageMap.put(Key.KEY_07.name(), I07);
		imageMap.put(Key.KEY_08.name(), I08);
		imageMap.put(Key.KEY_09.name(), I09);
		imageMap.put(Key.KEY_10.name(), I10);
		imageMap.put(Key.KEY_11.name(), I11);
		imageMap.put(Key.KEY_12.name(), I12);
		imageMap.put(Key.KEY_13.name(), I13);
		imageMap.put(Key.KEY_14.name(), I14);
		imageMap.put(Key.KEY_15.name(), I15);
		imageMap.put(Key.KEY_16.name(), I16);
		imageMap.put(Key.KEY_20.name(), I20);
		imageMap.put(Key.KEY_21.name(), I21);
		imageMap.put(Key.KEY_22.name(), I22);
		imageMap.put(Key.KEY_23.name(), I23);
		imageMap.put(Key.KEY_24.name(), I24);
		imageMap.put(Key.KEY_25.name(), I25);
		imageMap.put(Key.KEY_26.name(), I26);
		imageMap.put(Key.KEY_30.name(), I30);
		imageMap.put(Key.KEY_31.name(), I31);
		imageMap.put(Key.KEY_32.name(), I32);
		imageMap.put(Key.KEY_33.name(), I33);
		imageMap.put(Key.KEY_34.name(), I34);
		imageMap.put(Key.KEY_35.name(), I35);
		imageMap.put(Key.KEY_36.name(), I36);
		imageMap.put(Key.KEY_40.name(), I40);
		imageMap.put(Key.KEY_41.name(), I41);
		imageMap.put(Key.KEY_42.name(), I42);
		imageMap.put(Key.KEY_43.name(), I43);
		imageMap.put(Key.KEY_44.name(), I44);
		imageMap.put(Key.KEY_45.name(), I45);
		imageMap.put(Key.KEY_48.name(), I48);
		imageMap.put(Key.KEY_49.name(), I49);

		imageMapPressed = new Hashtable<String, ImageIcon>();
		imageMapPressed.put(Key.KEY_00.name(), I00p);
		imageMapPressed.put(Key.KEY_01.name(), I01p);
		imageMapPressed.put(Key.KEY_02.name(), I02p);
		imageMapPressed.put(Key.KEY_03.name(), I03p);
		imageMapPressed.put(Key.KEY_04.name(), I04p);
		imageMapPressed.put(Key.KEY_05.name(), I05p);
		imageMapPressed.put(Key.KEY_06.name(), I06p);
		imageMapPressed.put(Key.KEY_07.name(), I07p);
		imageMapPressed.put(Key.KEY_08.name(), I08p);
		imageMapPressed.put(Key.KEY_09.name(), I09p);
		imageMapPressed.put(Key.KEY_10.name(), I10p);
		imageMapPressed.put(Key.KEY_11.name(), I11p);
		imageMapPressed.put(Key.KEY_12.name(), I12p);
		imageMapPressed.put(Key.KEY_13.name(), I13p);
		imageMapPressed.put(Key.KEY_14.name(), I14p);
		imageMapPressed.put(Key.KEY_15.name(), I15p);
		imageMapPressed.put(Key.KEY_16.name(), I16p);
		imageMapPressed.put(Key.KEY_20.name(), I20p);
		imageMapPressed.put(Key.KEY_21.name(), I21p);
		imageMapPressed.put(Key.KEY_22.name(), I22p);
		imageMapPressed.put(Key.KEY_23.name(), I23p);
		imageMapPressed.put(Key.KEY_24.name(), I24p);
		imageMapPressed.put(Key.KEY_25.name(), I25p);
		imageMapPressed.put(Key.KEY_26.name(), I26p);
		imageMapPressed.put(Key.KEY_30.name(), I30p);
		imageMapPressed.put(Key.KEY_31.name(), I31p);
		imageMapPressed.put(Key.KEY_32.name(), I32p);
		imageMapPressed.put(Key.KEY_33.name(), I33p);
		imageMapPressed.put(Key.KEY_34.name(), I34p);
		imageMapPressed.put(Key.KEY_35.name(), I35p);
		imageMapPressed.put(Key.KEY_36.name(), I36p);
		imageMapPressed.put(Key.KEY_40.name(), I40p);
		imageMapPressed.put(Key.KEY_41.name(), I41p);
		imageMapPressed.put(Key.KEY_42.name(), I42p);
		imageMapPressed.put(Key.KEY_43.name(), I43p);
		imageMapPressed.put(Key.KEY_44.name(), I44p);
		imageMapPressed.put(Key.KEY_45.name(), I45p);
		imageMapPressed.put(Key.KEY_48.name(), I48p);
		imageMapPressed.put(Key.KEY_49.name(), I49p);

	}

	private void buildLayout() {

		// INICIALIZING PANELS
		mainPanel = new ImagePanel(bgImage);
		displayPanel = new ImagePanel();

		// SETTING PANEL LAYOUTS
		mainPanel.setLayout(new GridBagLayout());
		displayPanel.setLayout(new GridBagLayout());

		// INITIALIZING PANEL CONSTRANTS
		constraintsMainPanel = new GridBagConstraints();
		constraintsDisplayPanel = new GridBagConstraints();
		constraintsDisplay = new GridBagConstraints();
		constraintsFlagDisplay = new GridBagConstraints();

		// SETTING LAYOUT ORIENTATION CONSTRANTS
		constraintsMainPanel.fill = GridBagConstraints.HORIZONTAL;
		constraintsDisplayPanel.fill = GridBagConstraints.HORIZONTAL;
		constraintsDisplay.fill = GridBagConstraints.HORIZONTAL;
		constraintsFlagDisplay.fill = GridBagConstraints.HORIZONTAL;

		// SETTING PANEL DIMENSIONS
		mainPanel.setPreferredSize(new Dimension(wmainpan, hmainpan));
		displayPanel.setPreferredSize(new Dimension(wdispan, hdispan));

		// SETTING COLORS
		faceColor = HexColor.getColor(skn.getBgColor());
		faceColor = HexColor.getColor(skn.getFaceColor());
		displayBgColor = HexColor.getColor(skn.getDisplayBgColor());
		displayFaceColor = HexColor.getColor(skn.getDisplayFaceColor());
		buttonBgColor = HexColor.getColor(skn.getButtonBgColor());
		buttonFaceColor = HexColor.getColor(skn.getButtonFaceColor());

		// CALLING FONT LOADER PROCEDURE
		this.loadFont();

		// SETTING DISPLAY
		display = new TextField("");
		display.setFont(font);
		display.setAntiAlias(true);
		display.setForeground(displayFaceColor);
		display.setEditable(false);
		display.setPreferredSize(new Dimension(wdis, hdis));
		display.setBorder(null);
		display.setOpaque(false);
		display.setFocusable(false);
		constraintsDisplay.insets = new Insets(tdis, ldis, bdis, rdis);
		constraintsDisplay.gridx = 0;
		constraintsDisplay.gridy = 0;
		constraintsDisplay.gridwidth = 1;
		constraintsDisplay.gridheight = 1;
		displayPanel.add(display, constraintsDisplay);

		// SETTING FLAG DISPLAY
		flagDisplay = new TextField("");
		flagDisplay.setFont(new Font("monospaced", Font.TRUETYPE_FONT,
				(int) (fontSize / 3)));
		display.setAntiAlias(true);
		display.setForeground(displayFaceColor);
		flagDisplay.setEditable(false);
		flagDisplay.setPreferredSize(new Dimension(wfdis, hfdis));
		flagDisplay
				.setBorder(BorderFactory.createLineBorder(displayBgColor, 0));
		flagDisplay.setOpaque(false);
		flagDisplay.setFocusable(false);
		flagDisplay.setBorder(BorderFactory.createEmptyBorder());
		constraintsFlagDisplay.insets = new Insets(tfdis, lfdis, bfdis, rfdis);
		constraintsFlagDisplay.gridx = 0;
		constraintsFlagDisplay.gridy = 1;
		constraintsFlagDisplay.gridwidth = 1;
		constraintsFlagDisplay.gridheight = 1;
		displayPanel.add(flagDisplay, constraintsFlagDisplay);

		// Adding display panel to the main panel
		displayPanel.setBackground(displayBgColor);
		displayPanel.setBorder(BorderFactory
				.createLineBorder(displayBgColor, 0));
		displayPanel.setOpaque(false);
		displayPanel.setFocusable(false);
		constraintsDisplayPanel.gridx = 0;
		constraintsDisplayPanel.gridy = 0;
		constraintsDisplayPanel.gridwidth = 10;
		constraintsDisplayPanel.gridheight = 1;
		mainPanel.add(displayPanel, constraintsDisplayPanel);

		// Calculator's buttons
		B11 = new ImageButton(I11);
		B11.setKey(Key.KEY_11);
		B11.setBorderPainted(false);
		B11.setPreferredSize(new Dimension(wbot, hbot));
		B11.setBackground(buttonBgColor);
		B11.setMargin(new Insets(0, 0, 0, 0));
		B11.setOpaque(false);
		R11 = new GridBagConstraints();
		R11.fill = GridBagConstraints.HORIZONTAL;
		R11.insets = new Insets(ypad, xpad, ypad, xpad);
		R11.gridx = 0;
		R11.gridy = 1;
		R11.gridwidth = 1;
		R11.gridheight = 1;
		mainPanel.add(B11, R11);

		B12 = new ImageButton(I12);
		B12.setKey(Key.KEY_12);
		B12.setBorderPainted(false);
		B12.setPreferredSize(new Dimension(wbot, hbot));
		B12.setBackground(buttonBgColor);
		B12.setMargin(new Insets(0, 0, 0, 0));
		B12.setOpaque(false);
		R12 = new GridBagConstraints();
		R12.fill = GridBagConstraints.HORIZONTAL;
		R12.insets = new Insets(ypad, xpad, ypad, xpad);
		R12.gridx = 1;
		R12.gridy = 1;
		R12.gridwidth = 1;
		R12.gridheight = 1;
		mainPanel.add(B12, R12);

		B13 = new ImageButton(I13);
		B13.setKey(Key.KEY_13);
		B13.setBorderPainted(false);
		B13.setPreferredSize(new Dimension(wbot, hbot));
		B13.setBackground(buttonBgColor);
		B13.setMargin(new Insets(0, 0, 0, 0));
		B13.setOpaque(false);
		R13 = new GridBagConstraints();
		R13.fill = GridBagConstraints.HORIZONTAL;
		R13.insets = new Insets(ypad, xpad, ypad, xpad);
		R13.gridx = 2;
		R13.gridy = 1;
		R13.gridwidth = 1;
		R13.gridheight = 1;
		mainPanel.add(B13, R13);

		B14 = new ImageButton(I14);
		B14.setKey(Key.KEY_14);
		B14.setBorderPainted(false);
		B14.setPreferredSize(new Dimension(wbot, hbot));
		B14.setBackground(buttonBgColor);
		B14.setMargin(new Insets(0, 0, 0, 0));
		B14.setOpaque(false);
		R14 = new GridBagConstraints();
		R14.fill = GridBagConstraints.HORIZONTAL;
		R14.insets = new Insets(ypad, xpad, ypad, xpad);
		R14.gridx = 3;
		R14.gridy = 1;
		R14.gridwidth = 1;
		R14.gridheight = 1;
		mainPanel.add(B14, R14);

		B15 = new ImageButton(I15);
		B15.setKey(Key.KEY_15);
		B15.setBorderPainted(false);
		B15.setPreferredSize(new Dimension(wbot, hbot));
		B15.setBackground(buttonBgColor);
		B15.setMargin(new Insets(0, 0, 0, 0));
		B15.setOpaque(false);
		R15 = new GridBagConstraints();
		R15.fill = GridBagConstraints.HORIZONTAL;
		R15.insets = new Insets(ypad, xpad, ypad, xpad);
		R15.gridx = 4;
		R15.gridy = 1;
		R15.gridwidth = 1;
		R15.gridheight = 1;
		mainPanel.add(B15, R15);

		B16 = new ImageButton(I16);
		B16.setKey(Key.KEY_16);
		B16.setBorderPainted(false);
		B16.setPreferredSize(new Dimension(wbot, hbot));
		B16.setBackground(buttonBgColor);
		B16.setMargin(new Insets(0, 0, 0, 0));
		B16.setOpaque(false);
		R16 = new GridBagConstraints();
		R16.fill = GridBagConstraints.HORIZONTAL;
		R16.insets = new Insets(ypad, xpad, ypad, xpad);
		R16.gridx = 5;
		R16.gridy = 1;
		R16.gridwidth = 1;
		R16.gridheight = 1;
		mainPanel.add(B16, R16);

		B07 = new ImageButton(I07);
		B07.setKey(Key.KEY_07);
		B07.setBorderPainted(false);
		B07.setPreferredSize(new Dimension(wbot, hbot));
		B07.setBackground(buttonBgColor);
		B07.setMargin(new Insets(0, 0, 0, 0));
		B07.setOpaque(false);
		R07 = new GridBagConstraints();
		R07.fill = GridBagConstraints.HORIZONTAL;
		R07.insets = new Insets(ypad, xpad, ypad, xpad);
		R07.gridx = 6;
		R07.gridy = 1;
		R07.gridwidth = 1;
		R07.gridheight = 1;
		mainPanel.add(B07, R07);

		B08 = new ImageButton(I08);
		B08.setKey(Key.KEY_08);
		B08.setBorderPainted(false);
		B08.setPreferredSize(new Dimension(wbot, hbot));
		B08.setBackground(buttonBgColor);
		B08.setMargin(new Insets(0, 0, 0, 0));
		B08.setOpaque(false);
		R08 = new GridBagConstraints();
		R08.fill = GridBagConstraints.HORIZONTAL;
		R08.insets = new Insets(ypad, xpad, ypad, xpad);
		R08.gridx = 7;
		R08.gridy = 1;
		R08.gridwidth = 1;
		R08.gridheight = 1;
		mainPanel.add(B08, R08);

		B09 = new ImageButton(I09);
		B09.setKey(Key.KEY_09);
		B09.setBorderPainted(false);
		B09.setPreferredSize(new Dimension(wbot, hbot));
		B09.setBackground(buttonBgColor);
		B09.setMargin(new Insets(0, 0, 0, 0));
		B09.setOpaque(false);
		R09 = new GridBagConstraints();
		R09.fill = GridBagConstraints.HORIZONTAL;
		R09.insets = new Insets(ypad, xpad, ypad, xpad);
		R09.gridx = 8;
		R09.gridy = 1;
		R09.gridwidth = 1;
		R09.gridheight = 1;
		mainPanel.add(B09, R09);

		B10 = new ImageButton(I10);
		B10.setKey(Key.KEY_10);
		B10.setBorderPainted(false);
		B10.setPreferredSize(new Dimension(wbot, hbot));
		B10.setBackground(buttonBgColor);
		B10.setMargin(new Insets(0, 0, 0, 0));
		B10.setOpaque(false);
		R10 = new GridBagConstraints();
		R10.fill = GridBagConstraints.HORIZONTAL;
		R10.insets = new Insets(ypad, xpad, ypad, xpad);
		R10.insets = new Insets(ypad, xpad, ypad, xpad);
		R10.gridx = 9;
		R10.gridy = 1;
		R10.gridwidth = 1;
		R10.gridheight = 1;
		mainPanel.add(B10, R10);

		// =================================

		B21 = new ImageButton(I21);
		B21.setKey(Key.KEY_21);
		B21.setBorderPainted(false);
		B21.setPreferredSize(new Dimension(wbot, hbot));
		B21.setBackground(buttonBgColor);
		B21.setMargin(new Insets(0, 0, 0, 0));
		B21.setOpaque(false);
		R21 = new GridBagConstraints();
		R21.fill = GridBagConstraints.HORIZONTAL;
		R21.insets = new Insets(ypad, xpad, ypad, xpad);
		R21.insets = new Insets(ypad, xpad, ypad, xpad);
		R21.gridx = 0;
		R21.gridy = 2;
		R21.gridwidth = 1;
		R21.gridheight = 1;
		mainPanel.add(B21, R21);

		B22 = new ImageButton(I22);
		B22.setKey(Key.KEY_22);
		B22.setBorderPainted(false);
		B22.setPreferredSize(new Dimension(wbot, hbot));
		B22.setBackground(buttonBgColor);
		B22.setMargin(new Insets(0, 0, 0, 0));
		B22.setOpaque(false);
		R22 = new GridBagConstraints();
		R22.fill = GridBagConstraints.HORIZONTAL;
		R22.insets = new Insets(ypad, xpad, ypad, xpad);
		R22.insets = new Insets(ypad, xpad, ypad, xpad);
		R22.gridx = 1;
		R22.gridy = 2;
		R22.gridwidth = 1;
		R22.gridheight = 1;
		mainPanel.add(B22, R22);

		B23 = new ImageButton(I23);
		B23.setKey(Key.KEY_23);
		B23.setBorderPainted(false);
		B23.setPreferredSize(new Dimension(wbot, hbot));
		B23.setBackground(buttonBgColor);
		B23.setMargin(new Insets(0, 0, 0, 0));
		B23.setOpaque(false);
		R23 = new GridBagConstraints();
		R23.fill = GridBagConstraints.HORIZONTAL;
		R23.insets = new Insets(ypad, xpad, ypad, xpad);
		R23.gridx = 2;
		R23.gridy = 2;
		R23.gridwidth = 1;
		R23.gridheight = 1;
		mainPanel.add(B23, R23);

		B24 = new ImageButton(I24);
		B24.setKey(Key.KEY_24);
		B24.setBorderPainted(false);
		B24.setPreferredSize(new Dimension(wbot, hbot));
		B24.setBackground(buttonBgColor);
		B24.setMargin(new Insets(0, 0, 0, 0));
		B24.setOpaque(false);
		R24 = new GridBagConstraints();
		R24.fill = GridBagConstraints.HORIZONTAL;
		R24.insets = new Insets(ypad, xpad, ypad, xpad);
		R24.gridx = 3;
		R24.gridy = 2;
		R24.gridwidth = 1;
		R24.gridheight = 1;
		mainPanel.add(B24, R24);

		B25 = new ImageButton(I25);
		B25.setKey(Key.KEY_25);
		B25.setBorderPainted(false);
		B25.setPreferredSize(new Dimension(wbot, hbot));
		B25.setBackground(buttonBgColor);
		B25.setMargin(new Insets(0, 0, 0, 0));
		B25.setOpaque(false);
		R25 = new GridBagConstraints();
		R25.fill = GridBagConstraints.HORIZONTAL;
		R25.insets = new Insets(ypad, xpad, ypad, xpad);
		R25.gridx = 4;
		R25.gridy = 2;
		R25.gridwidth = 1;
		R25.gridheight = 1;
		mainPanel.add(B25, R25);

		B26 = new ImageButton(I26);
		B26.setKey(Key.KEY_26);
		B26.setBorderPainted(false);
		B26.setPreferredSize(new Dimension(wbot, hbot));
		B26.setBackground(buttonBgColor);
		B26.setMargin(new Insets(0, 0, 0, 0));
		B26.setOpaque(false);
		R26 = new GridBagConstraints();
		R26.fill = GridBagConstraints.HORIZONTAL;
		R26.insets = new Insets(ypad, xpad, ypad, xpad);
		R26.gridx = 5;
		R26.gridy = 2;
		R26.gridwidth = 1;
		R26.gridheight = 1;
		mainPanel.add(B26, R26);

		B04 = new ImageButton(I04);
		B04.setKey(Key.KEY_04);
		B04.setBorderPainted(false);
		B04.setPreferredSize(new Dimension(wbot, hbot));
		B04.setBackground(buttonBgColor);
		B04.setMargin(new Insets(0, 0, 0, 0));
		B04.setOpaque(false);
		R04 = new GridBagConstraints();
		R04.fill = GridBagConstraints.HORIZONTAL;
		R04.insets = new Insets(ypad, xpad, ypad, xpad);
		R04.gridx = 6;
		R04.gridy = 2;
		R04.gridwidth = 1;
		R04.gridheight = 1;
		mainPanel.add(B04, R04);

		B05 = new ImageButton(I05);
		B05.setKey(Key.KEY_05);
		B05.setBorderPainted(false);
		B05.setPreferredSize(new Dimension(wbot, hbot));
		B05.setBackground(buttonBgColor);
		B05.setMargin(new Insets(0, 0, 0, 0));
		B05.setOpaque(false);
		R05 = new GridBagConstraints();
		R05.fill = GridBagConstraints.HORIZONTAL;
		R05.insets = new Insets(ypad, xpad, ypad, xpad);
		R05.gridx = 7;
		R05.gridy = 2;
		R05.gridwidth = 1;
		R05.gridheight = 1;
		mainPanel.add(B05, R05);

		B06 = new ImageButton(I06);
		B06.setKey(Key.KEY_06);
		B06.setBorderPainted(false);
		B06.setPreferredSize(new Dimension(wbot, hbot));
		B06.setBackground(buttonBgColor);
		B06.setMargin(new Insets(0, 0, 0, 0));
		B06.setOpaque(false);
		R06 = new GridBagConstraints();
		R06.fill = GridBagConstraints.HORIZONTAL;
		R06.insets = new Insets(ypad, xpad, ypad, xpad);
		R06.gridx = 8;
		R06.gridy = 2;
		R06.gridwidth = 1;
		R06.gridheight = 1;
		mainPanel.add(B06, R06);

		B20 = new ImageButton(I20);
		B20.setKey(Key.KEY_20);
		B20.setBorderPainted(false);
		B20.setPreferredSize(new Dimension(wbot, hbot));
		B20.setBackground(buttonBgColor);
		B20.setMargin(new Insets(0, 0, 0, 0));
		B20.setOpaque(false);
		R20 = new GridBagConstraints();
		R20.fill = GridBagConstraints.HORIZONTAL;
		R20.insets = new Insets(ypad, xpad, ypad, xpad);
		R20.gridx = 9;
		R20.gridy = 2;
		R20.gridwidth = 1;
		R20.gridheight = 1;
		mainPanel.add(B20, R20);

		// ==================================

		B31 = new ImageButton(I31);
		B31.setKey(Key.KEY_31);
		B31.setBorderPainted(false);
		B31.setPreferredSize(new Dimension(wbot, hbot));
		B31.setBackground(buttonBgColor);
		B31.setMargin(new Insets(0, 0, 0, 0));
		B31.setOpaque(false);
		R31 = new GridBagConstraints();
		R31.fill = GridBagConstraints.HORIZONTAL;
		R31.insets = new Insets(ypad, xpad, ypad, xpad);
		R31.gridx = 0;
		R31.gridy = 3;
		R31.gridwidth = 1;
		R31.gridheight = 1;
		mainPanel.add(B31, R31);

		B32 = new ImageButton(I32);
		B32.setKey(Key.KEY_32);
		B32.setBorderPainted(false);
		B32.setPreferredSize(new Dimension(wbot, hbot));
		B32.setBackground(buttonBgColor);
		B32.setMargin(new Insets(0, 0, 0, 0));
		B32.setOpaque(false);
		R32 = new GridBagConstraints();
		R32.fill = GridBagConstraints.HORIZONTAL;
		R32.insets = new Insets(ypad, xpad, ypad, xpad);
		R32.gridx = 1;
		R32.gridy = 3;
		R32.gridwidth = 1;
		R32.gridheight = 1;
		mainPanel.add(B32, R32);

		B33 = new ImageButton(I33);
		B33.setKey(Key.KEY_33);
		B33.setBorderPainted(false);
		B33.setPreferredSize(new Dimension(wbot, hbot));
		B33.setBackground(buttonBgColor);
		B33.setMargin(new Insets(0, 0, 0, 0));
		B33.setOpaque(false);
		R33 = new GridBagConstraints();
		R33.fill = GridBagConstraints.HORIZONTAL;
		R33.insets = new Insets(ypad, xpad, ypad, xpad);
		R33.gridx = 2;
		R33.gridy = 3;
		R33.gridwidth = 1;
		R33.gridheight = 1;
		mainPanel.add(B33, R33);

		B34 = new ImageButton(I34);
		B34.setKey(Key.KEY_34);
		B34.setBorderPainted(false);
		B34.setPreferredSize(new Dimension(wbot, hbot));
		B34.setBackground(buttonBgColor);
		B34.setMargin(new Insets(0, 0, 0, 0));
		B34.setOpaque(false);
		R34 = new GridBagConstraints();
		R34.fill = GridBagConstraints.HORIZONTAL;
		R34.insets = new Insets(ypad, xpad, ypad, xpad);
		R34.gridx = 3;
		R34.gridy = 3;
		R34.gridwidth = 1;
		R34.gridheight = 1;
		mainPanel.add(B34, R34);

		B35 = new ImageButton(I35);
		B35.setKey(Key.KEY_35);
		B35.setBorderPainted(false);
		B35.setPreferredSize(new Dimension(wbot, hbot));
		B35.setBackground(buttonBgColor);
		B35.setMargin(new Insets(0, 0, 0, 0));
		B35.setOpaque(false);
		R35 = new GridBagConstraints();
		R35.fill = GridBagConstraints.HORIZONTAL;
		R35.insets = new Insets(ypad, xpad, ypad, xpad);
		R35.gridx = 4;
		R35.gridy = 3;
		R35.gridwidth = 1;
		R35.gridheight = 1;
		mainPanel.add(B35, R35);

		B36 = new ImageButton(I36);
		B36.setKey(Key.KEY_36);
		B36.setBorderPainted(false);
		B36.setPreferredSize(new Dimension(webot, hebot));
		B36.setBackground(buttonBgColor);
		B36.setMargin(new Insets(0, 0, 0, 0));
		B36.setOpaque(false);
		R36 = new GridBagConstraints();
		R36.fill = GridBagConstraints.HORIZONTAL;
		R36.insets = new Insets(ypad, xpad, ypad, xpad);
		R36.gridx = 5;
		R36.gridy = 3;
		R36.gridwidth = 1;
		R36.gridheight = 2;
		mainPanel.add(B36, R36);

		B01 = new ImageButton(I01);
		B01.setKey(Key.KEY_01);
		B01.setBorderPainted(false);
		B01.setPreferredSize(new Dimension(wbot, hbot));
		B01.setBackground(buttonBgColor);
		B01.setMargin(new Insets(0, 0, 0, 0));
		B01.setOpaque(false);
		R01 = new GridBagConstraints();
		R01.fill = GridBagConstraints.HORIZONTAL;
		R01.insets = new Insets(ypad, xpad, ypad, xpad);
		R01.gridx = 6;
		R01.gridy = 3;
		R01.gridwidth = 1;
		R01.gridheight = 1;
		mainPanel.add(B01, R01);

		B02 = new ImageButton(I02);
		B02.setKey(Key.KEY_02);
		B02.setBorderPainted(false);
		B02.setPreferredSize(new Dimension(wbot, hbot));
		B02.setBackground(buttonBgColor);
		B02.setMargin(new Insets(0, 0, 0, 0));
		B02.setOpaque(false);
		R02 = new GridBagConstraints();
		R02.fill = GridBagConstraints.HORIZONTAL;
		R02.insets = new Insets(ypad, xpad, ypad, xpad);
		R02.gridx = 7;
		R02.gridy = 3;
		R02.gridwidth = 1;
		R02.gridheight = 1;
		mainPanel.add(B02, R02);

		B03 = new ImageButton(I03);
		B03.setKey(Key.KEY_03);
		B03.setBorderPainted(false);
		B03.setPreferredSize(new Dimension(wbot, hbot));
		B03.setBackground(buttonBgColor);
		B03.setMargin(new Insets(0, 0, 0, 0));
		B03.setOpaque(false);
		R03 = new GridBagConstraints();
		R03.fill = GridBagConstraints.HORIZONTAL;
		R03.insets = new Insets(ypad, xpad, ypad, xpad);
		R03.gridx = 8;
		R03.gridy = 3;
		R03.gridwidth = 1;
		R03.gridheight = 1;
		mainPanel.add(B03, R03);

		B30 = new ImageButton(I30);
		B30.setKey(Key.KEY_30);
		B30.setBorderPainted(false);
		B30.setPreferredSize(new Dimension(wbot, hbot));
		B30.setBackground(buttonBgColor);
		B30.setMargin(new Insets(0, 0, 0, 0));
		B30.setOpaque(false);
		R30 = new GridBagConstraints();
		R30.fill = GridBagConstraints.HORIZONTAL;
		R30.insets = new Insets(ypad, xpad, ypad, xpad);
		R30.gridx = 9;
		R30.gridy = 3;
		R30.gridwidth = 1;
		R30.gridheight = 1;
		mainPanel.add(B30, R30);

		// ============================================

		B41 = new ImageButton(I41);
		B41.setKey(Key.KEY_41);
		B41.setBorderPainted(false);
		B41.setPreferredSize(new Dimension(wbot, hbot));
		B41.setBackground(buttonBgColor);
		B41.setMargin(new Insets(0, 0, 0, 0));
		B41.setOpaque(false);
		R41 = new GridBagConstraints();
		R41.fill = GridBagConstraints.HORIZONTAL;
		R41.insets = new Insets(ypad, xpad, ypad, xpad);
		R41.gridx = 0;
		R41.gridy = 4;
		R41.gridwidth = 1;
		R41.gridheight = 1;
		mainPanel.add(B41, R41);

		B42 = new ImageButton(I42);
		B42.setKey(Key.KEY_42);
		B42.setBorderPainted(false);
		B42.setPreferredSize(new Dimension(wbot, hbot));
		B42.setBackground(buttonBgColor);
		B42.setMargin(new Insets(0, 0, 0, 0));
		B42.setOpaque(false);
		R42 = new GridBagConstraints();
		R42.fill = GridBagConstraints.HORIZONTAL;
		R42.insets = new Insets(ypad, xpad, ypad, xpad);
		R42.gridx = 1;
		R42.gridy = 4;
		R42.gridwidth = 1;
		R42.gridheight = 1;
		mainPanel.add(B42, R42);

		B43 = new ImageButton(I43);
		B43.setKey(Key.KEY_43);
		B43.setBorderPainted(false);
		B43.setPreferredSize(new Dimension(wbot, hbot));
		B43.setBackground(buttonBgColor);
		B43.setMargin(new Insets(0, 0, 0, 0));
		B43.setOpaque(false);
		R43 = new GridBagConstraints();
		R43.fill = GridBagConstraints.HORIZONTAL;
		R43.insets = new Insets(ypad, xpad, ypad, xpad);
		R43.gridx = 2;
		R43.gridy = 4;
		R43.gridwidth = 1;
		R43.gridheight = 1;
		mainPanel.add(B43, R43);

		B44 = new ImageButton(I44);
		B44.setKey(Key.KEY_44);
		B44.setBorderPainted(false);
		B44.setPreferredSize(new Dimension(wbot, hbot));
		B44.setBackground(buttonBgColor);
		B44.setMargin(new Insets(0, 0, 0, 0));
		B44.setOpaque(false);
		R44 = new GridBagConstraints();
		R44.fill = GridBagConstraints.HORIZONTAL;
		R44.insets = new Insets(ypad, xpad, ypad, xpad);
		R44.gridx = 3;
		R44.gridy = 4;
		R44.gridwidth = 1;
		R44.gridheight = 1;
		mainPanel.add(B44, R44);

		B45 = new ImageButton(I45);
		B45.setKey(Key.KEY_45);
		B45.setBorderPainted(false);
		B45.setPreferredSize(new Dimension(wbot, hbot));
		B45.setBackground(buttonBgColor);
		B45.setMargin(new Insets(0, 0, 0, 0));
		B45.setOpaque(false);
		R45 = new GridBagConstraints();
		R45.fill = GridBagConstraints.HORIZONTAL;
		R45.insets = new Insets(ypad, xpad, ypad, xpad);
		R45.gridx = 4;
		R45.gridy = 4;
		R45.gridwidth = 1;
		R45.gridheight = 1;
		mainPanel.add(B45, R45);

		B00 = new ImageButton(I00);
		B00.setKey(Key.KEY_00);
		B00.setBorderPainted(false);
		B00.setPreferredSize(new Dimension(wbot, hbot));
		B00.setBackground(buttonBgColor);
		B00.setMargin(new Insets(0, 0, 0, 0));
		B00.setOpaque(false);
		R00 = new GridBagConstraints();
		R00.fill = GridBagConstraints.HORIZONTAL;
		R00.insets = new Insets(ypad, xpad, ypad, xpad);
		R00.gridx = 6;
		R00.gridy = 4;
		R00.gridwidth = 1;
		R00.gridheight = 1;
		mainPanel.add(B00, R00);

		B48 = new ImageButton(I48);
		B48.setKey(Key.KEY_48);
		B48.setBorderPainted(false);
		B48.setPreferredSize(new Dimension(wbot, hbot));
		B48.setBackground(buttonBgColor);
		B48.setMargin(new Insets(0, 0, 0, 0));
		B48.setOpaque(false);
		R48 = new GridBagConstraints();
		R48.fill = GridBagConstraints.HORIZONTAL;
		R48.insets = new Insets(ypad, xpad, ypad, xpad);
		R48.gridx = 7;
		R48.gridy = 4;
		R48.gridwidth = 1;
		R48.gridheight = 1;
		mainPanel.add(B48, R48);

		B49 = new ImageButton(I49);
		B49.setKey(Key.KEY_49);
		B49.setBorderPainted(false);
		B49.setPreferredSize(new Dimension(wbot, hbot));
		B49.setBackground(buttonBgColor);
		B49.setMargin(new Insets(0, 0, 0, 0));
		B49.setOpaque(false);
		R49 = new GridBagConstraints();
		R49.fill = GridBagConstraints.HORIZONTAL;
		R49.insets = new Insets(ypad, xpad, ypad, xpad);
		R49.gridx = 8;
		R49.gridy = 4;
		R49.gridwidth = 1;
		R49.gridheight = 1;
		mainPanel.add(B49, R49);

		B40 = new ImageButton(I40);
		B40.setKey(Key.KEY_40);
		B40.setBorderPainted(false);
		B40.setPreferredSize(new Dimension(wbot, hbot));
		B40.setBackground(buttonBgColor);
		B40.setMargin(new Insets(0, 0, 0, 0));
		B40.setOpaque(false);
		R40 = new GridBagConstraints();
		R40.fill = GridBagConstraints.HORIZONTAL;
		R40.insets = new Insets(ypad, xpad, ypad, xpad);
		R40.gridx = 9;
		R40.gridy = 4;
		R40.gridwidth = 1;
		R40.gridheight = 1;
		mainPanel.add(B40, R40);

		bot = new Hashtable<String, ImageButton>();
		bot.put(Key.KEY_00.name(), B00);
		bot.put(Key.KEY_01.name(), B01);
		bot.put(Key.KEY_02.name(), B02);
		bot.put(Key.KEY_03.name(), B03);
		bot.put(Key.KEY_04.name(), B04);
		bot.put(Key.KEY_05.name(), B05);
		bot.put(Key.KEY_06.name(), B06);
		bot.put(Key.KEY_07.name(), B07);
		bot.put(Key.KEY_08.name(), B08);
		bot.put(Key.KEY_09.name(), B09);
		bot.put(Key.KEY_10.name(), B10);
		bot.put(Key.KEY_11.name(), B11);
		bot.put(Key.KEY_12.name(), B12);
		bot.put(Key.KEY_13.name(), B13);
		bot.put(Key.KEY_14.name(), B14);
		bot.put(Key.KEY_15.name(), B15);
		bot.put(Key.KEY_16.name(), B16);
		bot.put(Key.KEY_20.name(), B20);
		bot.put(Key.KEY_21.name(), B21);
		bot.put(Key.KEY_22.name(), B22);
		bot.put(Key.KEY_23.name(), B23);
		bot.put(Key.KEY_24.name(), B24);
		bot.put(Key.KEY_25.name(), B25);
		bot.put(Key.KEY_26.name(), B26);
		bot.put(Key.KEY_30.name(), B30);
		bot.put(Key.KEY_31.name(), B31);
		bot.put(Key.KEY_32.name(), B32);
		bot.put(Key.KEY_33.name(), B33);
		bot.put(Key.KEY_34.name(), B34);
		bot.put(Key.KEY_35.name(), B35);
		bot.put(Key.KEY_36.name(), B36);
		bot.put(Key.KEY_40.name(), B40);
		bot.put(Key.KEY_41.name(), B41);
		bot.put(Key.KEY_42.name(), B42);
		bot.put(Key.KEY_43.name(), B43);
		bot.put(Key.KEY_44.name(), B44);
		bot.put(Key.KEY_45.name(), B45);
		bot.put(Key.KEY_48.name(), B48);
		bot.put(Key.KEY_49.name(), B49);

		mainPanel.setFocusCycleRoot(true);

	}

	public void updateDisplay() {

		this.display.setText(this.controller.getExecutor().getDisplay()
				.getString());
		this.flagDisplay.setText(this.controller.getExecutor().getFlags()
				.getDisplayStr());

		frame.requestFocusInWindow();
	}

	public void show() {
		frame.setVisible(true);
	}

	public void hide() {
		frame.setVisible(false);
	}

	public void keyPressed(Key key) {
		if (key == null)
			return;

		ImageButton bt = (ImageButton) bot.get(key.name());
		ImageIcon prs = (ImageIcon) imageMapPressed.get(key.name());
		bt.setImage(prs.getImage());
	}

	public void keyReleased(Key key) {

		if (key == null) {
			return;
		}

		ImageButton bt = (ImageButton) bot.get(key.name());
		ImageIcon rls = (ImageIcon) imageMap.get(key.name());
		bt.setImage(rls.getImage());
		this.updateDisplay();
	}

	public void setWindowListener(CustomWindowListener listener) {
		frame.addWindowListener(listener);
	}

	public void setMouseListener(Key key, MouseAdapter listener) {
		JButton bt = (JButton) bot.get(key.name());
		bt.addMouseListener(listener);
	}

	public void setKeyListener(CustomKeyListener listener) {
		frame.addKeyListener(listener);
	}

	public void setMenuListener(CustomMenuListener listener) {

		mnFile.addActionListener(listener);
		mnEdit.addActionListener(listener);
		mnView.addActionListener(listener);
		mnOptions.addActionListener(listener);
		mnTools.addActionListener(listener);
		mnAbout.addActionListener(listener);
		mnSize.addActionListener(listener);
		mnSkin.addActionListener(listener);
		mnLang.addActionListener(listener);
		mnNumFormat.addActionListener(listener);
		mnDateFormat.addActionListener(listener);
		mnPayment.addActionListener(listener);
		itSizeVerySmall.addActionListener(listener);
		itQuit.addActionListener(listener);
		itImport.addActionListener(listener);
		itExport.addActionListener(listener);
		itQuit.addActionListener(listener);
		itCopy.addActionListener(listener);
		itPaste.addActionListener(listener);
		itEraseDsp.addActionListener(listener);
		itEraseStk.addActionListener(listener);
		itEraseFin.addActionListener(listener);
		itEraseSta.addActionListener(listener);
		itEraseReg.addActionListener(listener);
		itErasePrg.addActionListener(listener);
		itSizeVerySmall.addActionListener(listener);
		itSizeSmall.addActionListener(listener);
		itSizeMedium.addActionListener(listener);
		itSizeLarge.addActionListener(listener);
		itSizeHuge.addActionListener(listener);
		itCommaNumFormat.addActionListener(listener);
		itDotNumFormat.addActionListener(listener);
		itDMYDateFormat.addActionListener(listener);
		itMDYDateFormat.addActionListener(listener);
		itBegPayment.addActionListener(listener);
		itEndPayment.addActionListener(listener);
		itRegistersView.addActionListener(listener);
		itInstructionsHistory.addActionListener(listener);
		itAuthor.addActionListener(listener);
		itContributors.addActionListener(listener);
		itSoftware.addActionListener(listener);

		for (int i = 0; i < itLang.length; i++)
			itLang[i].addActionListener(listener);

		for (int i = 0; i < itSkin.length; i++)
			itSkin[i].addActionListener(listener);
	}

	private void buildMenuBar() {
		if (menuBar == null) {

			/**** File Menu ****/

			if (itImport == null) {
				itImport = CreateMenuItem(
						ITEM_PLAIN,
						strList.getValue(CMD_FILE_IMPORT),
						CMD_FILE_IMPORT,
						this.createImageIcon(16, 16, this.skinPath
								+ "icons/import.png"),
						strList.getShortcut(CMD_FILE_IMPORT).charAt(0),
						strList.getDescription(CMD_FILE_IMPORT));

			}

			if (itExport == null) {
				itExport = CreateMenuItem(
						ITEM_PLAIN,
						strList.getValue(CMD_FILE_EXPORT),
						CMD_FILE_EXPORT,
						this.createImageIcon(16, 16, this.skinPath
								+ "icons/export.png"),
						strList.getShortcut(CMD_FILE_EXPORT).charAt(0),
						strList.getDescription(CMD_FILE_EXPORT));

			}

			if (itQuit == null) {
				itQuit = CreateMenuItem(
						ITEM_PLAIN,
						strList.getValue(CMD_FILE_QUIT),
						CMD_FILE_QUIT,
						this.createImageIcon(16, 16, this.skinPath
								+ "icons/quit.png"),
						strList.getShortcut(CMD_FILE_QUIT).charAt(0),
						strList.getDescription(CMD_FILE_QUIT));
			}

			/**** Edit Menu ****/

			if (itCopy == null) {
				itCopy = CreateMenuItem(
						ITEM_PLAIN,
						strList.getValue(CMD_EDIT_COPY),
						CMD_EDIT_COPY,
						this.createImageIcon(16, 16, this.skinPath
								+ "icons/copy.png"),
						strList.getShortcut(CMD_EDIT_COPY).charAt(0),
						strList.getDescription(CMD_EDIT_COPY));
			}

			if (itPaste == null) {
				itPaste = CreateMenuItem(
						ITEM_PLAIN,
						strList.getValue(CMD_EDIT_PASTE),
						CMD_EDIT_PASTE,
						this.createImageIcon(16, 16, this.skinPath
								+ "icons/paste.png"),
						strList.getShortcut(CMD_EDIT_PASTE).charAt(0),
						strList.getDescription(CMD_EDIT_PASTE));

			}

			if (itEraseDsp == null) {
				itEraseDsp = CreateMenuItem(ITEM_PLAIN,
						strList.getValue(CMD_EDIT_ERASE_DSP),
						CMD_EDIT_ERASE_DSP, null,
						strList.getShortcut(CMD_EDIT_ERASE_DSP).charAt(0),
						strList.getDescription(CMD_EDIT_ERASE_DSP));

			}

			if (itEraseStk == null) {
				itEraseStk = CreateMenuItem(ITEM_PLAIN,
						strList.getValue(CMD_EDIT_ERASE_STK),
						CMD_EDIT_ERASE_STK, null,
						strList.getShortcut(CMD_EDIT_ERASE_STK).charAt(0),
						strList.getDescription(CMD_EDIT_ERASE_STK));

			}

			if (itEraseFin == null) {
				itEraseFin = CreateMenuItem(ITEM_PLAIN,
						strList.getValue(CMD_EDIT_ERASE_FIN),
						CMD_EDIT_ERASE_FIN, null,
						strList.getShortcut(CMD_EDIT_ERASE_FIN).charAt(0),
						strList.getDescription(CMD_EDIT_ERASE_FIN));

			}

			if (itEraseSta == null) {
				itEraseSta = CreateMenuItem(ITEM_PLAIN,
						strList.getValue(CMD_EDIT_ERASE_STA),
						CMD_EDIT_ERASE_STA, null,
						strList.getShortcut(CMD_EDIT_ERASE_STA).charAt(0),
						strList.getDescription(CMD_EDIT_ERASE_STA));

			}

			if (itEraseReg == null) {
				itEraseReg = CreateMenuItem(ITEM_PLAIN,
						strList.getValue(CMD_EDIT_ERASE_REG),
						CMD_EDIT_ERASE_REG, null,
						strList.getShortcut(CMD_EDIT_ERASE_REG).charAt(0),
						strList.getDescription(CMD_EDIT_ERASE_REG));

			}

			if (itErasePrg == null) {
				itErasePrg = CreateMenuItem(ITEM_PLAIN,
						strList.getValue(CMD_EDIT_ERASE_PRG),
						CMD_EDIT_ERASE_PRG, null,
						strList.getShortcut(CMD_EDIT_ERASE_PRG).charAt(0),
						strList.getDescription(CMD_EDIT_ERASE_PRG));

			}
			if (mnErase == null) {
				mnErase = CreateMenu(strList.getValue(CMD_EDIT_ERASE),
						this.createImageIcon(16, 16, this.skinPath
								+ "icons/erase.png"),
						strList.getShortcut(CMD_EDIT_ERASE).charAt(0),
						strList.getDescription(CMD_EDIT_ERASE), new Dimension(
								200, 20));
				mnErase.add(itEraseDsp);
				mnErase.add(itEraseStk);
				mnErase.add(itEraseFin);
				mnErase.add(itEraseSta);
				mnErase.add(itEraseReg);
				mnErase.add(itErasePrg);
			}

			/**** View Menu ****/
			if (itSizeVerySmall == null) {
				itSizeVerySmall = CreateMenuItem(
						ITEM_RADIO,
						strList.getValue(CMD_VIEW_SIZE_VERY_SMALL),
						CMD_VIEW_SIZE_VERY_SMALL,
						null,
						strList.getShortcut(CMD_VIEW_SIZE_VERY_SMALL).charAt(0),
						strList.getDescription(CMD_VIEW_SIZE_VERY_SMALL));
			}
			if (itSizeSmall == null) {
				itSizeSmall = CreateMenuItem(ITEM_RADIO,
						strList.getValue(CMD_VIEW_SIZE_SMALL),
						CMD_VIEW_SIZE_SMALL, null,
						strList.getShortcut(CMD_VIEW_SIZE_SMALL).charAt(0),
						strList.getDescription(CMD_VIEW_SIZE_SMALL));
			}

			if (itSizeMedium == null) {
				itSizeMedium = CreateMenuItem(ITEM_RADIO,
						strList.getValue(CMD_VIEW_SIZE_MEDIUM),
						CMD_VIEW_SIZE_MEDIUM, null,
						strList.getShortcut(CMD_VIEW_SIZE_MEDIUM).charAt(0),
						strList.getDescription(CMD_VIEW_SIZE_MEDIUM));
			}

			if (itSizeLarge == null) {
				itSizeLarge = CreateMenuItem(ITEM_RADIO,
						strList.getValue(CMD_VIEW_SIZE_LARGE),
						CMD_VIEW_SIZE_LARGE, null,
						strList.getShortcut(CMD_VIEW_SIZE_LARGE).charAt(0),
						strList.getDescription(CMD_VIEW_SIZE_LARGE));
			}
			if (itSizeHuge == null) {
				itSizeHuge = CreateMenuItem(ITEM_RADIO,
						strList.getValue(CMD_VIEW_SIZE_HUGE), CMD_VIEW_SIZE_HUGE,
						null,
						strList.getShortcut(CMD_VIEW_SIZE_HUGE).charAt(0),
						strList.getDescription(CMD_VIEW_SIZE_HUGE));
			}
			if (mnSize == null) {
				mnSize = CreateMenu(strList.getValue(CMD_VIEW_SIZE),
						this.createImageIcon(16, 16, this.skinPath
								+ "icons/size.png"),
						strList.getShortcut(CMD_VIEW_SIZE).charAt(0),
						strList.getDescription(CMD_VIEW_SIZE), new Dimension(
								200, 20));
				mnSize.add(itSizeVerySmall);
				mnSize.add(itSizeSmall);
				mnSize.add(itSizeMedium);
				mnSize.add(itSizeLarge);
				mnSize.add(itSizeHuge);
			}

			gpSize = new ButtonGroup();
			gpSize.add(itSizeVerySmall);
			gpSize.add(itSizeSmall);
			gpSize.add(itSizeMedium);
			gpSize.add(itSizeLarge);
			gpSize.add(itSizeHuge);

			if (cfg.getSize() == SIZE_VERY_SMALL)
				itSizeVerySmall.setSelected(true);
			else if (cfg.getSize() == SIZE_SMALL)
				itSizeSmall.setSelected(true);
			else if (cfg.getSize() == SIZE_MEDIUM)
				itSizeMedium.setSelected(true);
			else if (cfg.getSize() == SIZE_LARGE)
				itSizeLarge.setSelected(true);
			else if (cfg.getSize() == SIZE_HUGE)
				itSizeHuge.setSelected(true);

			if (mnSkin == null) {
				mnSkin = CreateMenu(strList.getValue(CMD_VIEW_SKIN),
						this.createImageIcon(16, 16, this.skinPath
								+ "icons/skin.png"),
						strList.getShortcut(CMD_VIEW_SKIN).charAt(0),
						strList.getDescription(CMD_VIEW_SKIN), new Dimension(
								200, 20));
			}

			itSkin = new JMenuItem[sknItems.length];

			gpSkin = new ButtonGroup();

			for (int i = 0; i < sknItems.length; i++) {
				if (itSkin[i] == null) {
					itSkin[i] = CreateMenuItem(ITEM_RADIO,
							sknItems[i].getDescription(), "VIEW_SKIN::"
									+ sknItems[i].getId(), null, '\0', null);
					mnSkin.add(itSkin[i]);
					gpSkin.add(itSkin[i]);
					if (cfg.getSkin().equals(sknItems[i].getId()))
						itSkin[i].setSelected(true);
				}
			}

			if (mnLang == null) {
				mnLang = CreateMenu(
						strList.getValue(CMD_VIEW_LANGUAGE),
						this.createImageIcon(16, 16, this.skinPath
								+ "icons/language.png"),
						strList.getShortcut(CMD_VIEW_LANGUAGE).charAt(0),
						strList.getDescription(CMD_VIEW_LANGUAGE),
						new Dimension(200, 20));
			}

			itLang = new JMenuItem[lngArray.length];

			gpLang = new ButtonGroup();

			for (int i = 0; i < lngArray.length; i++) {
				if (itLang[i] == null) {
					itLang[i] = CreateMenuItem(ITEM_RADIO,
							lngArray[i].getDescription(), "VIEW_LANGUAGE::"
									+ lngArray[i].getId(), null, '\0', null);
					mnLang.add(itLang[i]);
					gpLang.add(itLang[i]);
					if (cfg.getLanguage().equals(lngArray[i].getId()))
						itLang[i].setSelected(true);
				}
			}

			/**** Options Menu ****/
			if (itDotNumFormat == null) {
				itDotNumFormat = CreateMenuItem(ITEM_RADIO,
						strList.getValue(CMD_OPTIONS_NUMBER_FORMAT_DOT),
						CMD_OPTIONS_NUMBER_FORMAT_DOT, null,
						strList.getShortcut(CMD_OPTIONS_NUMBER_FORMAT_DOT)
								.charAt(0),
						strList.getDescription(CMD_OPTIONS_NUMBER_FORMAT_DOT));
			}
			if (itCommaNumFormat == null) {
				itCommaNumFormat = CreateMenuItem(ITEM_RADIO,
						strList.getValue(CMD_OPTIONS_NUMBER_FORMAT_COMMA),
						CMD_OPTIONS_NUMBER_FORMAT_COMMA, null, strList
								.getShortcut(CMD_OPTIONS_NUMBER_FORMAT_COMMA)
								.charAt(0),
						strList.getDescription(CMD_OPTIONS_NUMBER_FORMAT_COMMA));
			}

			if (mnNumFormat == null) {
				mnNumFormat = CreateMenu(
						strList.getValue(CMD_OPTIONS_NUMBER_FORMAT),
						this.createImageIcon(16, 16, this.skinPath
								+ "icons/number.png"),
						strList.getShortcut(CMD_OPTIONS_NUMBER_FORMAT)
								.charAt(0),
						strList.getDescription(CMD_OPTIONS_NUMBER_FORMAT),
						new Dimension(200, 20));
				mnNumFormat.add(itDotNumFormat);
				mnNumFormat.add(itCommaNumFormat);
			}

			gpNumFormat = new ButtonGroup();
			gpNumFormat.add(itDotNumFormat);
			gpNumFormat.add(itCommaNumFormat);

			if (cfg.getCom() == 0)
				itDotNumFormat.setSelected(true);
			else if (cfg.getCom() == 1)
				itCommaNumFormat.setSelected(true);

			if (itMDYDateFormat == null) {
				itMDYDateFormat = CreateMenuItem(ITEM_RADIO,
						strList.getValue(CMD_OPTIONS_DATE_FORMAT_MONTH),
						CMD_OPTIONS_DATE_FORMAT_MONTH, null,
						strList.getShortcut(CMD_OPTIONS_DATE_FORMAT_MONTH)
								.charAt(0),
						strList.getDescription(CMD_OPTIONS_DATE_FORMAT_MONTH));
			}

			if (itDMYDateFormat == null) {
				itDMYDateFormat = CreateMenuItem(ITEM_RADIO,
						strList.getValue(CMD_OPTIONS_DATE_FORMAT_DAY),
						CMD_OPTIONS_DATE_FORMAT_DAY, null,
						strList.getShortcut(CMD_OPTIONS_DATE_FORMAT_DAY)
								.charAt(0),
						strList.getDescription(CMD_OPTIONS_DATE_FORMAT_DAY));
			}

			if (mnDateFormat == null) {
				mnDateFormat = CreateMenu(
						strList.getValue(CMD_OPTIONS_DATE_FORMAT),
						this.createImageIcon(16, 16, this.skinPath
								+ "icons/date.png"),
						strList.getShortcut(CMD_OPTIONS_DATE_FORMAT).charAt(0),
						strList.getDescription(CMD_OPTIONS_DATE_FORMAT),
						new Dimension(200, 20));
				mnDateFormat.add(itMDYDateFormat);
				mnDateFormat.add(itDMYDateFormat);
			}

			gpDateFormat = new ButtonGroup();
			gpDateFormat.add(itMDYDateFormat);
			gpDateFormat.add(itDMYDateFormat);

			if (cfg.getDmy() == 0)
				itMDYDateFormat.setSelected(true);
			else if (cfg.getDmy() == 1)
				itDMYDateFormat.setSelected(true);

			if (itBegPayment == null) {
				itBegPayment = CreateMenuItem(ITEM_RADIO,
						strList.getValue(CMD_OPTIONS_PAYMENT_MODE_BEGIN),
						CMD_OPTIONS_PAYMENT_MODE_BEGIN, null, strList
								.getShortcut(CMD_OPTIONS_PAYMENT_MODE_BEGIN)
								.charAt(0),
						strList.getDescription(CMD_OPTIONS_PAYMENT_MODE_BEGIN));
			}
			if (itEndPayment == null) {
				itEndPayment = CreateMenuItem(ITEM_RADIO,
						strList.getValue(CMD_OPTIONS_PAYMENT_MODE_END),
						CMD_OPTIONS_PAYMENT_MODE_END, null,
						strList.getShortcut(CMD_OPTIONS_PAYMENT_MODE_END)
								.charAt(0),
						strList.getDescription(CMD_OPTIONS_PAYMENT_MODE_END));
			}

			if (mnPayment == null) {
				mnPayment = CreateMenu(
						strList.getValue(CMD_OPTIONS_PAYMENT_MODE),
						this.createImageIcon(16, 16, this.skinPath
								+ "icons/payment.png"),
						strList.getShortcut(CMD_OPTIONS_PAYMENT_MODE).charAt(0),
						strList.getDescription(CMD_OPTIONS_PAYMENT_MODE),
						new Dimension(200, 20));
				mnPayment.add(itBegPayment);
				mnPayment.add(itEndPayment);
			}

			gpPayment = new ButtonGroup();
			gpPayment.add(itBegPayment);
			gpPayment.add(itEndPayment);

			if (cfg.getBeg() == 0)
				itEndPayment.setSelected(true);
			else if (cfg.getBeg() == 1)
				itBegPayment.setSelected(true);

			/**** Tools Menu ****/
			if (itRegistersView == null) {
				itRegistersView = CreateMenuItem(
						ITEM_CHECK,
						strList.getValue(CMD_TOOLS_REGISTERS_VIEW),
						CMD_TOOLS_REGISTERS_VIEW,
						this.createImageIcon(16, 16, this.skinPath
								+ "icons/regview.png"),
						strList.getShortcut(CMD_TOOLS_REGISTERS_VIEW).charAt(0),
						strList.getDescription(CMD_TOOLS_REGISTERS_VIEW));
			}

			if (itInstructionsHistory == null) {
				itInstructionsHistory = CreateMenuItem(
						ITEM_CHECK,
						strList.getValue(CMD_TOOLS_HISTORY),
						CMD_TOOLS_HISTORY,
						this.createImageIcon(16, 16, this.skinPath
								+ "icons/history.png"),
						strList.getShortcut(CMD_TOOLS_HISTORY).charAt(0),
						strList.getDescription(CMD_TOOLS_HISTORY));
			}

			/**** About Menu ****/
			if (itAuthor == null) {
				itAuthor = CreateMenuItem(
						ITEM_PLAIN,
						strList.getValue(CMD_ABOUT_AUTHOR),
						CMD_ABOUT_AUTHOR,
						this.createImageIcon(16, 16, this.skinPath
								+ "icons/author.png"),
						strList.getShortcut(CMD_ABOUT_AUTHOR).charAt(0),
						strList.getDescription(CMD_ABOUT_AUTHOR));
			}

			if (itContributors == null) {
				itContributors = CreateMenuItem(ITEM_PLAIN,
						strList.getValue(CMD_ABOUT_CONTRIBUTORS),
						CMD_ABOUT_CONTRIBUTORS, this.createImageIcon(16, 16,
								this.skinPath + "icons/contributor.png"),
						strList.getShortcut(CMD_ABOUT_CONTRIBUTORS).charAt(0),
						strList.getDescription(CMD_ABOUT_CONTRIBUTORS));
			}

			if (itSoftware == null) {
				itSoftware = CreateMenuItem(
						ITEM_PLAIN,
						strList.getValue(CMD_ABOUT_SOFTWARE),
						CMD_ABOUT_SOFTWARE,
						this.createImageIcon(16, 16, this.skinPath
								+ "icons/software.png"),
						strList.getShortcut(CMD_ABOUT_SOFTWARE).charAt(0),
						strList.getDescription(CMD_ABOUT_SOFTWARE));
			}

			if (mnFile == null) {
				mnFile = CreateMenu(strList.getValue(CMD_FILE_MENU), null,
						strList.getShortcut(CMD_FILE_MENU).charAt(0), null,
						null);
				mnFile.add(itImport);
				mnFile.add(itExport);
				mnFile.add(itQuit);
			}

			if (mnEdit == null) {
				mnEdit = CreateMenu(strList.getValue(CMD_EDIT_MENU), null,
						strList.getShortcut(CMD_EDIT_MENU).charAt(0), null,
						null);
				mnEdit.add(itCopy);
				mnEdit.add(itPaste);
				mnEdit.add(mnErase);
			}

			if (mnView == null) {
				mnView = CreateMenu(strList.getValue(CMD_VIEW_MENU), null,
						strList.getShortcut(CMD_VIEW_MENU).charAt(0), null,
						null);
				mnView.add(mnSize);
				mnView.add(mnSkin);
				mnView.add(mnLang);
			}

			if (mnOptions == null) {
				mnOptions = CreateMenu(strList.getValue(CMD_OPTIONS_MENU),
						null, strList.getShortcut(CMD_OPTIONS_MENU).charAt(0),
						null, null);
				mnOptions.add(mnNumFormat);
				mnOptions.add(mnDateFormat);
				mnOptions.add(mnPayment);
			}

			if (mnTools == null) {
				mnTools = CreateMenu(strList.getValue(CMD_TOOLS_MENU), null,
						strList.getShortcut(CMD_TOOLS_MENU).charAt(0), null,
						null);
				mnTools.add(itRegistersView);
				mnTools.add(itInstructionsHistory);
			}

			if (mnAbout == null) {
				mnAbout = CreateMenu(strList.getValue(CMD_ABOUT_MENU), null,
						strList.getShortcut(CMD_ABOUT_MENU).charAt(0), null,
						null);
				mnAbout.add(itAuthor);
				mnAbout.add(itContributors);
				mnAbout.add(itSoftware);
			}

			menuBar = new JMenuBar();
			// menuBar.add(mnFile);
			menuBar.add(mnEdit);
			menuBar.add(mnView);
			menuBar.add(mnOptions);
			// menuBar.add(mnTools);
			// menuBar.add(mnAbout);
		}
	}

	private void setDefaultSize() {

		// Button size
		this.hbot = 40;
		this.wbot = 45;

		// Enter button size
		this.hebot = 106;
		this.webot = 45;

		// Main panel size
		this.hmainpan = 400;
		this.wmainpan = 640;

		// Display panel size
		this.hdispan = 110;
		this.wdispan = 300;

		// Button insets
		this.xpad = 6;
		this.ypad = 11;

		// Display size
		this.hdis = 40;
		this.wdis = 300;

		// Display flag space size
		this.hfdis = 15;
		this.wfdis = 300;

		// Display insets
		this.tdis = 0;
		this.ldis = 0;
		this.bdis = 0;
		this.rdis = 100;

		// Flag display space insets
		this.tfdis = 0;
		this.lfdis = 0;
		this.bfdis = 30;
		this.rfdis = 100;

		// / Font size
		this.fontSize = 29;
	}

	public void setSize(double size) {

		this.setDefaultSize();

		// Button size
		this.hbot = (int) (hbot * size);
		this.wbot = (int) (wbot * size);

		// Enter button size
		this.hebot = (int) (hebot * size);
		this.webot = (int) (webot * size);

		// Main panel size
		this.hmainpan = (int) (hmainpan * size);
		this.wmainpan = (int) (wmainpan * size);

		// Display panel size
		this.hdispan = (int) (hdispan * size);
		this.wdispan = (int) (wdispan * size);

		// Button insets
		this.xpad = (int) Math.round(xpad * size);
		this.ypad = (int) Math.round(ypad * size);

		// Display size
		this.hdis = (int) (hdis * size);
		this.wdis = (int) (wdis * size);

		// Display flag space size
		this.hfdis = (int) (hfdis * size);
		this.wfdis = (int) (wfdis * size);

		// Display insets
		this.tdis = (int) (tdis * size);
		this.ldis = (int) (ldis * size);
		this.bdis = (int) (bdis * size);
		this.rdis = (int) (rdis * size);

		// Display flag space insets
		this.tfdis = (int) (tfdis * size);
		this.lfdis = (int) (lfdis * size);
		this.bfdis = (int) (bfdis * size);
		this.rfdis = (int) (rfdis * size);

		// / Font size
		this.fontSize = (int) (fontSize * size);
	}

	/**
	 * Loads skin files.
	 */
	private void loadSkin() {

		// Loading skin
		sknDao = new SkinLoader(cfg.getSkin());
		skn = sknDao.getSkin();

		// Loading skin menu items
		sknList = new MenuList("skins");
		sknItems = sknList.getAll();
	}

	/**
	 * Loads language file.
	 */
	private void loadLanguage() {
		// Loading language
		strList = new StringList(cfg.getLanguage());

		// Loading language menu items
		lngList = new MenuList("langs");
		lngArray = lngList.getAll();
	}

	/**
	 * Loads font file.
	 */
	private void loadFont() {

		try {

			InputStream i = loadByRelativePath(this.skinFontPath);

			try {
				font = Font.createFont(Font.TRUETYPE_FONT, i);
				font = font.deriveFont((float) fontSize);
			} catch (IOException ex1) {
				ex1.printStackTrace();
			} catch (FontFormatException ex1) {
				ex1.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JMenuItem CreateMenuItem(int iType, String label, String command,
			ImageIcon image, int acceleratorKey, String toolTip) {
		// Create the item
		JMenuItem menuItem;

		switch (iType) {
		case ITEM_RADIO:
			menuItem = new JRadioButtonMenuItem();
			break;

		case ITEM_CHECK:
			menuItem = new JCheckBoxMenuItem();
			break;

		default:
			menuItem = new JMenuItem();
			break;
		}

		menuItem.setPreferredSize(new Dimension(200, 20));

		// Add the item label
		if (label != null)
			menuItem.setText(label);

		// Add the item command
		if (command != null)
			menuItem.setActionCommand(command);

		// Add the optional icon
		if (image != null)
			menuItem.setIcon(image);

		// Add the accelerator key
		if (acceleratorKey > 0)
			menuItem.setMnemonic(acceleratorKey);

		// Add the optional tool tip text
		if (toolTip != null)
			menuItem.setToolTipText(toolTip);

		// Add an action handler to this menu item
		// menuItem.addActionListener( this );

		return menuItem;
	}

	private JMenu CreateMenu(String sText, ImageIcon image, int acceleratorKey,
			String sToolTip, Dimension dimension) {
		// Create the item
		JMenu menu = new JMenu();

		// Add the item test
		menu.setText(sText);

		if (dimension != null)
			menu.setPreferredSize(new Dimension(200, 20));

		// Add the optional icon
		if (image != null)
			menu.setIcon(image);

		// Add the accelerator key
		if (acceleratorKey > 0)
			menu.setMnemonic(acceleratorKey);

		// Add the optional tool tip text
		if (sToolTip != null)
			menu.setToolTipText(sToolTip);

		return menu;
	}

	public void setConfigs(Configuration cfg) {
		this.cfg = cfg;
	}

	public void setSkin(Skin skn) {
		this.skn = skn;
	}

	public void setLanguageList(MenuList lngList) {
		this.lngList = lngList;
	}

	public void setSkinList(MenuList sknList) {
		this.sknList = sknList;
	}

	public void setLanguageStringList(StringList strList) {
		this.strList = strList;
	}

	public void setController(Controller presenter) {
		this.controller = presenter;
	}

	public Skin getSkin() {
		return this.skn;
	}

	public Configuration getConfigs() {
		return this.cfg;
	}

	public StringList getLanguageStringList() {
		return this.strList;
	}

	public MenuList getSkinList() {
		return this.sknList;
	}

	public MenuList getLanguageList() {
		return this.lngList;
	}
	
	public Point getWindowLocation(){
		return frame.getLocation();
	}
	
	public Dimension getScreenSize(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return screenSize;
	}

}