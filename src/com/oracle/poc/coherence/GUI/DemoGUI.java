package com.oracle.poc.coherence.GUI;

import static com.tangosol.net.cache.TypeAssertion.withTypes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import com.oracle.poc.coherence.model.Employee;
import com.tangosol.coherence.dslquery.QueryPlus;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.Cluster;
import com.tangosol.net.NamedCache;
import com.tangosol.util.UUID;

public class DemoGUI {

	public static void main(String[] asArgs) throws Exception {
		String sCluster = "NONE";
		String sCommand = null;

		System.out.println("Args length: " + asArgs.length);
		for (String arg : asArgs) {
			System.out.println(arg);
		}

		if (asArgs.length >= 1) {
			sCluster = asArgs[0];
		}
		if (asArgs.length >= 2) {
			sCommand = asArgs[1];
			if (!sCommand.equals(COHQL) && !sCommand.equals(CONSOLE)) {
				System.err.println("You can only specify either " + COHQL + " or " + CONSOLE + " as an argument.");
				System.exit(1);
			}
		}

		if (!CLUSTER_A.equals(sCluster) && !CLUSTER_B.equals(sCluster)) {
			System.err.println("Please specify " + CLUSTER_A + " or " + CLUSTER_B);
			System.exit(1);
		}

		setProperties(sCluster);

		Cluster cluster = CacheFactory.ensureCluster();
		NamedCache<String, Employee> cache = CacheFactory.getTypedCache("Employee",
				withTypes(String.class, Employee.class));

		if (COHQL.equals(sCommand)) {
			QueryPlus.main(new String[0]);
		} else if (CONSOLE.equals(sCommand)) {
			CacheFactory.main(new String[0]);
		} else {
			new DemoGUI().showGUI(cluster, cache);
		}
	}

	/**
	 * Setup the GUI and run a Timer to update cache size periodically for a
	 * given cluster and cache.
	 *
	 * @param cluster
	 *            the Cluster we are connected to
	 * @param cache
	 *            the cache to work on
	 */
	private void showGUI(Cluster cluster, NamedCache<String, Employee> cache) {
		JFrame frame = new JFrame("Coherence Federated Caching Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JComponent contentPane = (JComponent) frame.getContentPane();

		JPanel pnlMain = new JPanel(new BorderLayout());

		// create the header panel
		JPanel pnlHeader = new JPanel(new GridLayout(2, 2));
		pnlHeader.add(new JLabel("Cluster name:"));
		txtClusterName = getTextField(15, JTextField.LEFT);
		txtClusterName.setText(cluster.getClusterName());
		pnlHeader.add(txtClusterName);

		pnlHeader.add(new JLabel("Employee cache size:"));
		txtCacheSize = getTextField(15, JTextField.LEFT);
		pnlHeader.add(txtCacheSize);
		pnlMain.add(pnlHeader, BorderLayout.NORTH);

		pnlMain.add(new JLabel(" "), BorderLayout.CENTER);

		// create action panel
		JPanel pnlAction = new JPanel(new FlowLayout());
		btnInsert = new JButton("Insert");
		pnlAction.add(btnInsert);

		// add handler to insert data into cache
		btnInsert.addActionListener(action -> insertData(cache, getInsertSize()));

		pnlAction.add(new JLabel("Count:"));
		txtInsertSize = getTextField(5, JTextField.LEFT);
		txtInsertSize.setEditable(true);
		txtInsertSize.setText("3");
		pnlAction.add(txtInsertSize);

		btnClear = new JButton("Clear Cache");
		pnlAction.add(btnClear);

		// add handler to clear the cache
		btnClear.addActionListener(action -> cache.clear());

		pnlMain.add(pnlAction, BorderLayout.SOUTH);

		contentPane.add(pnlMain);
		contentPane.setOpaque(true);
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		frame.setContentPane(contentPane);

		if (CLUSTER_A.equals(cluster.getClusterName())) {
			frame.setLocation(10, 10);
		} else {
			frame.setLocation(30, 30);
		}
		frame.pack();
		frame.setVisible(true);

		// refresh cache size every 2 seconds
		Timer timer = new Timer(2000, action -> {
			try {
				txtCacheSize.setText(String.format("%,-20d", cache.size()));
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		});
		timer.start();
	}

	// ----- helpers --------------------------------------------------------

	/**
	 * Return the value of the txtInsertSize field or DEFAULT if invalid.
	 *
	 * @return the value of the txtInsertSize field or DEFAULT if invalid
	 */
	private int getInsertSize() {
		final int DEFAULT = 0;

		try {
			return Integer.valueOf(txtInsertSize.getText());
		} catch (NumberFormatException e) {
			txtInsertSize.setText(String.valueOf(DEFAULT));
			return DEFAULT;
		}
	}

	/**
	 * Create a {@link JTextField} with the specified width and specified
	 * alignment.
	 *
	 * @param width
	 *            the width for the {@link JTextField}
	 * @param align
	 *            either {@link JTextField}.RIGHT or LEFT
	 *
	 * @return the newly created text field
	 */
	private JTextField getTextField(int width, int align) {
		JTextField textField = new JTextField();

		textField.setEditable(false);
		textField.setColumns(width);
		textField.setHorizontalAlignment(align);

		textField.setOpaque(true);

		return textField;
	}

	private static void setProperties(String sCluster) {
		// set the cluster name
		System.setProperty("coherence.cluster", sCluster);

		if (CLUSTER_A.equals(sCluster)) {
			System.setProperty("coherence.clusterport", "11100");
		} else if (CLUSTER_B.equals(sCluster)) {
			System.setProperty("coherence.clusterport", "11200");
		}
	}

	private static void insertData(NamedCache<String, Employee> cache, int cEntries) {
		final int BATCH = 1000;
		final int size = cache.size();

		Map<String, Employee> map = new HashMap<>();

		for (int i = 0; i < cEntries; i++) {
			String id = new UUID().toString();
			String firstName = "FN-" + (size + i + 1);
			String lastName = "LN-" + (size + i + 1);
			Employee emp = new Employee();
			emp.setId(id);
			emp.setFirstName(firstName);
			emp.setLastName(lastName);
			map.put(id, emp);
			if (i % BATCH == 0) {
				cache.putAll(map);
				map.clear();
			}
		}
		if (!map.isEmpty()) {
			cache.putAll(map);
		}
	}

	private static final String CLUSTER_A = "ClusterA";
	private static final String CLUSTER_B = "ClusterB";

	private static final String COHQL = "cohql";
	private static final String CONSOLE = "console";

	// ----- data members ---------------------------------------------------
	private JTextField txtClusterName;
	private JTextField txtCacheSize;
	private JTextField txtInsertSize;
	private JButton btnInsert;
	private JButton btnClear;
}