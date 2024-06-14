package student;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.commons.lang3.StringUtils;

/**
 * @version 1.0 11/09/98
 */
public class ExcelTableFrame extends JFrame implements ConfigAbs {

	private static final long serialVersionUID = 1L;
	private final DefaultTableModel dm;
	private ClazzConfig clazzConfig;
	private ClazzConfig[] configs = new ClazzConfig[] {};
	private final JTable table;
	private Details genDatas;
	private final JLabel jLb = new JLabel();
	private final JComboBox<ClazzConfig> petList = new JComboBox<>(configs);
	public static final Font font = new Font("楷体", Font.BOLD, 12);
	public static final Color BgColor = Color.GREEN;
	public static final Color FontColor = Color.BLACK;

	{
		this.dm = new DefaultTableModel();
		this.table = new JTable(dm);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(30);
	}

	public static void main(String[] args) throws Exception {
		ClazzConfig[] clazzConfigs = CheckZuoyeUtil.getClazzConfigs();
		new ExcelTableFrame(clazzConfigs[0], clazzConfigs);
	}

	public ExcelTableFrame(ClazzConfig clazzConfig, ClazzConfig[] clazzConfigs) throws Exception {
		super(clazzConfig.toString());
		this.setClazzConfigs(clazzConfigs);
		this.setClazzConfig(this.configs[0]);
		petList.setSelectedItem(this.clazzConfig);
		this.setTitle(this.clazzConfig.toString());

		BorderLayout gridLayout = new BorderLayout(20, 5);
		getContentPane().setLayout(gridLayout);
		getContentPane().add(getTop(), BorderLayout.NORTH);
		JScrollPane scroll = new JScrollPane(table);
		getContentPane().add(scroll, BorderLayout.CENTER);
		getContentPane().add(getBottom(), BorderLayout.SOUTH);
		this.setLocationByPlatform(true);
		setSize(1024, 560);
		UItools.centerDisplay(this);
		setVisible(true);
		setAlwaysOnTop(ConfigData.DEFAULT_DING);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	void reloadConfigs(ClazzConfig[] clazzConfigs) throws Exception {
		this.setClazzConfigs(clazzConfigs);
		petList.setSelectedItem(this.clazzConfig);
		this.setTitle(this.clazzConfig.toString());
	}

	public static JSlider createSlider(int min, int max, int value) {
		JSlider zyJs = new JSlider(min, max, value);
		zyJs.setPaintLabels(true);
		zyJs.setPaintTicks(true);
		zyJs.setMajorTickSpacing((int) Math.ceil((max - min) / 5.0));
		zyJs.setMinorTickSpacing((int) Math.ceil((max - min) / 25.0));
		return zyJs;
	}

	public JPanel getTop() {
		JPanel jpTop = new JPanel();
		JSlider baseJs = createSlider(0, 100, ConfigData.BASE_RATE);
		JSlider zyJs = createSlider(0, 100, ConfigData.ZY_RATE);
		JSlider checkJs = createSlider(0, 100, ConfigData.CHECK_RATE);
		JSlider cJs = createSlider(0, 5, ConfigData.CHAT_RATE);
		baseJs.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					int fps = (int) source.getValue();
					ConfigData.BASE_RATE = fps;
				}
			}
		});
		zyJs.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					int fps = (int) source.getValue();
					ConfigData.ZY_RATE = fps;
				}
			}
		});
		checkJs.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					int fps = (int) source.getValue();
					ConfigData.CHECK_RATE = fps;
				}
			}
		});
		cJs.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					int fps = (int) source.getValue();
					ConfigData.CHAT_RATE = fps;
				}
			}
		});
		JButton btn = new JButton("重新计算");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ExcelTableFrame.this.refresh(null);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		this.jLb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.jLb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CheckZuoyeUtil.openFile(ConfigData.EXPORT_FILE_PATH);
			}
		});
		JButton exportBtn = new JButton("导出");
		exportBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File file = new File(ConfigData.EXPORT_FILE_PATH);
				if (file.exists()) {
					final JOptionPane optionPane = new JOptionPane(
							String.format("%s\n存在，是否覆盖 ?", ConfigData.EXPORT_FILE_PATH), JOptionPane.QUESTION_MESSAGE,
							JOptionPane.YES_NO_OPTION);
					final JDialog dialog = new JDialog(ExcelTableFrame.this, "Click a button", true);
					dialog.setContentPane(optionPane);
					dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
					dialog.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent we) {
							System.err.println("Thwarted user attempt to close window.");
						}
					});
					optionPane.addPropertyChangeListener(new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent e) {
							String prop = e.getPropertyName();
							if (dialog.isVisible() && (e.getSource() == optionPane)
									&& (prop.equals(JOptionPane.VALUE_PROPERTY))) {
								dialog.setVisible(false);
							}
						}
					});
					dialog.pack();
					UItools.centerDisplay(dialog);
					dialog.setVisible(true);
					int value = ((Integer) optionPane.getValue()).intValue();
					if (value == JOptionPane.YES_OPTION) {

					} else if (value == JOptionPane.NO_OPTION) {
						JFileChooser j = new JFileChooser(new File(ConfigData.EXPORT_FILE_PATH));
						j.setAcceptAllFileFilterUsed(false);
						j.setDialogTitle("Select a .csv file");
						FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .csv files", "csv");
						j.addChoosableFileFilter(restrict);
						int r = j.showSaveDialog(ExcelTableFrame.this);
						if (r == JFileChooser.APPROVE_OPTION) {
							String tmp = j.getSelectedFile().getAbsolutePath();
							ConfigData.EXPORT_FILE_PATH = tmp.endsWith(".csv") ? tmp : tmp + ".csv";
						} else {
							ConfigData.EXPORT_FILE_PATH = String.format("./%s%s.csv",
									ExcelTableFrame.this.clazzConfig.getName(),
									ExcelTableFrame.this.clazzConfig.getLecture());
						}
						jLb.setText("保存：" + ConfigData.EXPORT_FILE_PATH);
						file = new File(ConfigData.EXPORT_FILE_PATH);
					}
				}
				try {
					if (!file.exists()) {
						file.createNewFile();
					}
					BufferedWriter writer = new BufferedWriter(new FileWriter(file));
					writer.append("\uFEFF" + StringUtils.join(Arrays.asList(Record.columnIdentifiers), ","));
					for (int i = 0; i < dm.getDataVector().size(); i++) {
						Vector<Object> vec = (Vector<Object>) dm.getDataVector().elementAt(i);
						writer.append("\r\n").append(StringUtils.join(vec, ","));
					}
					writer.close();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(ExcelTableFrame.this, String.format("写入失败， %s.", e1.getMessage()));
					e1.printStackTrace();
				}
			}
		});
		jpTop.setLayout(new FlowLayout());
		jpTop.add(new JLabel("基本值"));
		jpTop.add(baseJs);
		jpTop.add(new JLabel("作业权重"));
		jpTop.add(zyJs);
		jpTop.add(new JLabel("网签权重"));
		jpTop.add(checkJs);
		jpTop.add(new JLabel("活跃权重"));
		jpTop.add(cJs);
		jpTop.add(btn);
		jpTop.add(exportBtn);
		jpTop.add(jLb);
		return jpTop;
	}

	public JPanel getBottom() {
		JPanel jpBottom = new JPanel();
		JLabel jL = new JLabel(
				String.format("当前班级：%s，课程：%s", this.clazzConfig.getName(), this.clazzConfig.getLecture()));
		petList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<ClazzConfig> cb = (JComboBox<ClazzConfig>) e.getSource();
				ClazzConfig config = (ClazzConfig) cb.getSelectedItem();
				if (config == null || ExcelTableFrame.this.clazzConfig == config) {
					return;
				}
				try {
					ExcelTableFrame.this.setClazzConfig(config);
					ExcelTableFrame.this.setTitle(config.toString());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				jL.setText(String.format("当前班级：%s，课程：%s", config.getName(), config.getLecture()));
			}
		});

		JButton zuoyeBtn = new JButton("打开作业记录");
		zuoyeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CheckZuoyeUtil.openDirectory(ExcelTableFrame.this.clazzConfig.getZuoyePath());
			}
		});
		JButton nameBtn = new JButton("班级配置信息");
		nameBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] possibleValues = { "打开配置文件", "重新加载配置文件" };
				int option = JOptionPane.showOptionDialog(ExcelTableFrame.this, "选择操作", "班级配置信息",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE, null, possibleValues, possibleValues[0]);
				if (option == JOptionPane.CLOSED_OPTION) {
					return;
				} else if (option == 0) {
					CheckZuoyeUtil.openFile(ClazzConfig.getClazzsPath());
				} else if (option == 1) {
					try {
						ClazzConfig[] clazzConfigs = CheckZuoyeUtil.getClazzConfigs();
						ExcelTableFrame.this.reloadConfigs(clazzConfigs);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		JButton wkBtn = new JButton("打开网课记录");
		wkBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CheckZuoyeUtil.openDirectory(ExcelTableFrame.this.clazzConfig.getWkPath());
			}
		});
		JCheckBox check = new JCheckBox("定住");
		check.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConfigData.DEFAULT_DING = ((JCheckBox) e.getSource()).isSelected();
				ExcelTableFrame.this.setAlwaysOnTop(ConfigData.DEFAULT_DING);
			}
		});
		JCheckBox firstCheck = new JCheckBox("首选", ConfigData.DEFAULT_FIRST);
		firstCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConfigData.DEFAULT_FIRST = ((JCheckBox) e.getSource()).isSelected();
				if (ConfigData.DEFAULT_FIRST) {
					List<Object[]> list = new ArrayList<>();
					for (int i = 0; i < dm.getDataVector().size(); i++) {
						Vector<Object> object = (Vector<Object>) dm.getDataVector().elementAt(i);
						Object[] arr = object.toArray();
						if ((boolean) arr[Arrays.asList(Record.columnIdentifiers).indexOf(Record.FIRST)]) {
							list.add(arr);
						}
					}
					try {
						ExcelTableFrame.this.refresh(list.toArray(new Object[list.size()][]));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						ExcelTableFrame.this.refresh(ExcelTableFrame.this.genDatas.getData());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		jpBottom.setLayout(new FlowLayout());
		jpBottom.add(nameBtn);
		jpBottom.add(petList);
		jpBottom.add(jL);
		jpBottom.add(zuoyeBtn);
		jpBottom.add(wkBtn);
		jpBottom.add(check);
		jpBottom.add(firstCheck);
		return jpBottom;
	}

	@Override
	public void setClazzConfigs(ClazzConfig[] clazzConfigs) {
		this.configs = clazzConfigs;
		this.petList.setSelectedItem(null);
		this.petList.removeAllItems();
		for (ClazzConfig config : this.configs) {
			this.petList.addItem(config);
		}
	}

	@Override
	public ClazzConfig getClazzConfig() {
		return this.clazzConfig;
	}

	@Override
	public void setClazzConfig(ClazzConfig config) throws Exception {
		if (config == null || ExcelTableFrame.this.clazzConfig == config) {
			return;
		}
		this.clazzConfig = config;
		this.refresh(null);
		ConfigData.EXPORT_FILE_PATH = String.format("./%s%s.csv", ExcelTableFrame.this.clazzConfig.getName(),
				ExcelTableFrame.this.clazzConfig.getLecture());
		this.jLb.setText("保存：" + ConfigData.EXPORT_FILE_PATH);
	}

	private void refresh(Object[][] data) throws IOException {
		if (data == null) {
			this.genDatas = CheckZuoyeUtil.genDatas(this.clazzConfig);
		} else {
			this.genDatas.setFirstData(data);
		}
		dm.setDataVector(this.genDatas.getFirstData(), Record.columnIdentifiers);
		int index = 0;
		table.getColumnModel().getColumn(index++).setPreferredWidth(40);
		table.getColumnModel().getColumn(index++).setPreferredWidth(100);
		table.getColumnModel().getColumn(index++).setPreferredWidth(600);
		table.getColumnModel().getColumn(index++).setPreferredWidth(80);
		table.getColumnModel().getColumn(index++).setPreferredWidth(40);
		table.getColumnModel().getColumn(index++).setPreferredWidth(40);
		table.getColumnModel().getColumn(index++).setPreferredWidth(50);
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JComponent cellRenderer = (JComponent) super.getTableCellRendererComponent(table, value, isSelected,
						hasFocus, row, column);
				Enum<Types> type = (Enum<Types>) table.getValueAt(row, 3);
				if (Types.nosubmit == type) {
					cellRenderer.setBackground(BgColor);
					cellRenderer.setForeground(FontColor);
				} else {
					if (isSelected) {
						cellRenderer.setForeground(table.getSelectionForeground());
						cellRenderer.setBackground(table.getSelectionBackground());
					} else {
						cellRenderer.setForeground(table.getForeground());
						cellRenderer.setBackground(UIManager.getColor("Button.background"));
					}
				}
				return cellRenderer;
			}
		});
		table.getColumn("作业路径").setCellRenderer(new ButtonRenderer(this));
		table.getColumn("作业路径").setCellEditor(new ButtonEditor(new JCheckBox("test"), this));
	}

	@Override
	public Details getGenDatas() {
		return this.genDatas;
	}
}

/**
 * @version 1.0 11/09/98
 */

class ButtonRenderer extends JPanel implements TableCellRenderer {

	private static final long serialVersionUID = 1L;
	public static final int ZY_ALIGN = FlowLayout.LEFT;
	private final ConfigAbs config;
	private String label;

	protected JButton zyFileBtn = new JButton();
	protected JButton zyDirBtn = new JButton("打开目录");
	protected JButton detailBtn = new JButton("详细数据");
	private Enum<Types> type;

	public ButtonRenderer(ConfigAbs config) {
		this.config = config;
		this.setLayout(new FlowLayout(ZY_ALIGN));
		zyFileBtn.setFont(ExcelTableFrame.font);
		zyDirBtn.setFont(ExcelTableFrame.font);
		detailBtn.setFont(ExcelTableFrame.font);
		zyFileBtn.setBounds(new Rectangle(200, 50));
		this.add(detailBtn);
		this.add(zyDirBtn);
		this.add(zyFileBtn);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		label = (value == null) ? "" : value.toString();
		type = (Enum<Types>) table.getValueAt(row, Arrays.asList(Record.columnIdentifiers).indexOf(Record.TYPE));
		zyFileBtn.setText(label.length() > this.config.getClazzConfig().getZuoyePath().length()
				? label.substring(this.config.getClazzConfig().getZuoyePath().length())
				: label);
		if (Types.nosubmit == type) {
			zyFileBtn.setBackground(ExcelTableFrame.BgColor);
			zyFileBtn.setForeground(ExcelTableFrame.FontColor);
		} else {
			if (isSelected) {
				zyFileBtn.setForeground(table.getSelectionForeground());
				zyFileBtn.setBackground(table.getSelectionBackground());
			} else {
				zyFileBtn.setForeground(table.getForeground());
				zyFileBtn.setBackground(table.getBackground());
			}
		}
		if (Types.nosubmit == type) {
			zyDirBtn.setBackground(ExcelTableFrame.BgColor);
			zyDirBtn.setForeground(ExcelTableFrame.FontColor);
		} else {
			if (isSelected) {
				zyDirBtn.setForeground(table.getSelectionForeground());
				zyDirBtn.setBackground(table.getSelectionBackground());
			} else {
				zyDirBtn.setForeground(table.getForeground());
				zyDirBtn.setBackground(table.getBackground());
			}
			if (type == Types.directory) {
				zyDirBtn.setVisible(false);
			} else {
				zyDirBtn.setVisible(true);
			}
		}
		if (type == Types.details || type == Types.nosubmit) {
			zyDirBtn.setVisible(false);
			zyFileBtn.setVisible(false);
			detailBtn.setVisible(true);
		} else {
			detailBtn.setVisible(false);
			zyFileBtn.setVisible(true);
		}
		return this;
	}
}

/**
 * @version 1.0 11/09/98
 */

class ButtonEditor extends DefaultCellEditor {
	private static final long serialVersionUID = 1L;

	protected JButton zyFileBtn = new JButton();
	protected JButton zyDirBtn = new JButton("打开目录");
	protected JButton detailBtn = new JButton("详细数据");
	protected JPanel jPanel = new JPanel();

	private String label;
	private Enum<Types> type;
	private final ConfigAbs config;

	private String name;

	public ButtonEditor(JCheckBox checkBox, ConfigAbs config) {
		super(checkBox);
		this.config = config;
		ActionListener fileActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
				if (type == Types.directory) {
					CheckZuoyeUtil.openDirectory(label);
				} else if (type == Types.file) {
					CheckZuoyeUtil.openFile(label);
				} else {
				}
			}
		};
		ActionListener dirActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (type == Types.directory) {
				} else if (type == Types.file) {
					CheckZuoyeUtil.openDirectory(label.substring(0, label.lastIndexOf(File.separator)));
				} else {
				}
			}
		};
		ActionListener detailActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				config.getGenDatas().print(name);
			}
		};
		zyFileBtn.setFont(ExcelTableFrame.font);
		zyDirBtn.setFont(ExcelTableFrame.font);
		detailBtn.setFont(ExcelTableFrame.font);
		zyFileBtn.addActionListener(fileActionListener);
		detailBtn.addActionListener(detailActionListener);
		zyDirBtn.addActionListener(dirActionListener);
		jPanel.setLayout(new FlowLayout(ButtonRenderer.ZY_ALIGN));
		zyFileBtn.setBounds(new Rectangle(200, 50));
		jPanel.add(detailBtn);
		jPanel.add(zyDirBtn);
		jPanel.add(zyFileBtn);
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		label = (value == null) ? "" : value.toString();
		type = (Enum<Types>) table.getValueAt(row, Arrays.asList(Record.columnIdentifiers).indexOf(Record.TYPE));
		name = (String) (table.getValueAt(row, Arrays.asList(Record.columnIdentifiers).indexOf(Record.NAME)));
		zyFileBtn.setText(label.length() > this.config.getClazzConfig().getZuoyePath().length()
				? label.substring(this.config.getClazzConfig().getZuoyePath().length())
				: label);
		if (Types.nosubmit == type) {
			zyFileBtn.setBackground(ExcelTableFrame.BgColor);
			zyFileBtn.setForeground(ExcelTableFrame.FontColor);
		} else {
			if (isSelected) {
				zyFileBtn.setForeground(table.getSelectionForeground());
				zyFileBtn.setBackground(table.getSelectionBackground());
			} else {
				zyFileBtn.setForeground(table.getForeground());
				zyFileBtn.setBackground(table.getBackground());
			}
		}
		if (Types.nosubmit == type) {
			zyDirBtn.setBackground(ExcelTableFrame.BgColor);
			zyDirBtn.setForeground(ExcelTableFrame.FontColor);
		} else {
			if (isSelected) {
				zyDirBtn.setForeground(table.getSelectionForeground());
				zyDirBtn.setBackground(table.getSelectionBackground());
			} else {
				zyDirBtn.setForeground(table.getForeground());
				zyDirBtn.setBackground(table.getBackground());
			}
			if (type == Types.directory) {
				zyDirBtn.setVisible(false);
			} else {
				zyDirBtn.setVisible(true);
			}
		}
		if (type == Types.details || type == Types.nosubmit) {
			zyDirBtn.setVisible(false);
			zyFileBtn.setVisible(false);
			detailBtn.setVisible(true);
		} else {
			detailBtn.setVisible(false);
			zyFileBtn.setVisible(true);
		}
		return jPanel;
	}

	public Object getCellEditorValue() {
		return new String(label);
	}

	public boolean stopCellEditing() {
		return super.stopCellEditing();
	}

	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
}

enum Types {
	file("文件"), directory("目录"), details("详情"), nosubmit("没交");

	private final String name;

	private Types(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
