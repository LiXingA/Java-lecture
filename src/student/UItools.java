package student;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

public class UItools {

	public static void centerDisplay(Component dialog) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation(dim.width / 2 - dialog.getSize().width / 2, dim.height / 2 - dialog.getSize().height / 2);
	}
}
