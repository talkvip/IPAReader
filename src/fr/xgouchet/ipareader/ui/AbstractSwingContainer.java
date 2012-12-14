package fr.xgouchet.ipareader.ui;

import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionListener;

import org.swixml.SwingEngine;

/**
 * A container using the SWIXML engine to build it's UI
 * 
 */
public abstract class AbstractSwingContainer implements ActionListener {

	/**
	 * Constructor
	 * 
	 * @param path
	 *            the path to the XML resource file
	 */
	public AbstractSwingContainer(final String path) {
		mSwingPath = path;
		mSwingEngine = new SwingEngine(this);
	}

	/**
	 * Builds the UI from the xml definition
	 */
	public void buildUI() {

		try {
			mSwingEngine.render(mSwingPath);
		} catch (Exception e) {
			System.err.println("Error while building ui"); // NOPMD
		}

		mSwingEngine.setActionListener(mSwingEngine.getRootComponent(),
				(ActionListener) this);
	}

	/**
	 * @return the root component for this panel
	 */
	public Container getRootComponent() {
		return mSwingEngine.getRootComponent();
	}

	/**
	 * Sets the root component in this container visible or hidden
	 * 
	 * @param visible
	 *            if true, show , else hide
	 */
	public void setVisible(final boolean visible) {
		mSwingEngine.getRootComponent().setVisible(visible);
	}

	/**
	 * If the container is holding a window, then pack the window
	 */
	public void pack() {
		final Window wnd = (Window) mSwingEngine.getRootComponent();
		if (wnd != null) {
			wnd.pack();
		}
	}

	/** the Swing XML engine */
	protected final SwingEngine mSwingEngine;

	/** the resources XML file */
	protected final String mSwingPath;
}
