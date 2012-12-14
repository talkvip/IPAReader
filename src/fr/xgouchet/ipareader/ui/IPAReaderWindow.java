package fr.xgouchet.ipareader.ui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import fr.xgouchet.ipareader.data.IPAInfo;
import fr.xgouchet.ipareader.data.IPAUtils;

// TODO window with icon attribution
//
// IPA Reader uses the Silk Icon pack under Creative Commons Attribution 2.5 License.
// http://www.famfamfam.com/lab/icons/silk/
//

public class IPAReaderWindow extends AbstractSwingContainer {

	public JLabel mPath;

	public JTextArea mDevices;
	public JLabel mAppId;
	public JLabel mExpirationDate;
	public JLabel mCreationDate;
	public JLabel mName;

	/**
	 * 
	 */
	public IPAReaderWindow() {
		// set the xml
		super("xml/ui/wnd_ipa_reader.xml");

		// build the window
		buildUI();
		mFileChooser.setFileFilter(new FileNameExtensionFilter(
				"iOS Package Application (*.ipa)", "ipa"));

		// finalize window
		pack();
		setVisible(true);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent event) {
		if ("ACTION_BROWSE_IPA".equals(event.getActionCommand())) {
			final int res = mFileChooser.showOpenDialog(getRootComponent());

			if (res == JFileChooser.APPROVE_OPTION) {
				mPath.setText(mFileChooser.getSelectedFile().getName());
				readIPAContent(mFileChooser.getSelectedFile());
			}
		}
	}

	private void readIPAContent(final File ipa) {
		try {
			updateInfo(IPAUtils.readIPA(ipa));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(getRootComponent(),
					"An error occured while reading the IPA", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void updateInfo(final IPAInfo info) {
		if (info != null) {
			mName.setText(info.getProvisioning());
			mCreationDate.setText(mDateFormat.format(new Date(info
					.getCreation())));
			mExpirationDate.setText(mDateFormat.format(new Date(info
					.getExpiration())));
			mAppId.setText(info.getAppId());

			mDevices.setText(info.getDevices());
		}
	}

	// Create a file chooser
	private final JFileChooser mFileChooser = new JFileChooser();
	private final SimpleDateFormat mDateFormat = new SimpleDateFormat(
			"yyyy MMM dd", Locale.US);
}
