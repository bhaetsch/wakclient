package de.techspread.wakclient.dateiablage;

import java.awt.Component;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class FortschrittsBalken extends JProgressBar implements TableCellRenderer {

	private static final long	serialVersionUID	= -8557188876711548498L;

	public FortschrittsBalken() {
		super(0, 100);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		setValue((Integer) value);
		setStringPainted(true);
		return this;
	}
}
