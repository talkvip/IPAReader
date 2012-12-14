package fr.xgouchet.ipareader;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import fr.xgouchet.ipareader.ui.IPAReaderWindow;

public final class IPAReader {

	/**
	 * List all available look and feels
	 */
	public static void listLookAndFeel() {
		final LookAndFeelInfo lafis[] = UIManager.getInstalledLookAndFeels();
		for (LookAndFeelInfo lafi : lafis) {
			System.out.println(lafi.getClassName()); // NOPMD
		}
	}

	/**
	 * 
	 * <li>javax.swing.plaf.metal.MetalLookAndFeel</li>
	 * 
	 * <li>com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel</li>
	 * 
	 * <li>com.sun.java.swing.plaf.motif.MotifLookAndFeel</li>
	 * 
	 * <li>com.sun.java.swing.plaf.windows.WindowsLookAndFeel</li>
	 * 
	 * <li>com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel</li>
	 * 
	 * @param laf
	 *            the class name of the look and feel to apply :
	 * 
	 */
	public static void setLookAndFeel(final String laf) {

		try {
			if ((laf == null) || (laf.length() == 0)) {
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
			} else {
				UIManager.setLookAndFeel(laf);
			}
		} catch (Exception e) {
			// ignore
			System.err.println("Error while setting look and feel, ignoring");// NOPMD
		}
	}

	/**
	 * @param args
	 *            the arguments when calling this
	 */
	public static void main(final String[] args) {
		// listLookAndFeel();
		// JFrame.setDefaultLookAndFeelDecorated(true);
		setLookAndFeel(null);

		new IPAReaderWindow();
	}

	private IPAReader() {
	}
}
