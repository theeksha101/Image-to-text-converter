package camscan;

import org.opencv.core.Core;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class HomeScreen extends JFrame {

	private JButton camera = new JButton("Take Image");
	private JButton selectImgBtn = new JButton("Select Image");
	private JButton convert = new JButton("Convert to Text");
	private JTextField input_file_name = new JTextField();
	private JLabel message = new JLabel("Enter File Name: ");
	private JFileChooser fileChooser;

	HomeScreen() {
		message.setBounds(250, 70, 200, 10);
		input_file_name.setBounds(115, 120, 200, 30);
		camera.setBounds(250, 400, 120, 40);
		selectImgBtn.setBounds(320, 120, 140, 30);
		convert.setBounds(240, 160, 120, 40);

		input_file_name.setEditable(false);

		this.add(convert);
		this.add(message);
		this.add(input_file_name);
		this.add(selectImgBtn);
		this.add(camera);
		this.setSize(new Dimension(600, 500));
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setupListeners();
	}

	void setupListeners() {
		convert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				EventQueue.invokeLater(new Runnable() {
					// Overriding existing run() method
					@Override
					public void run() {
						final Scan scan = new Scan();

						// Start camera in thread
						new Thread(new Runnable() {
							@Override
							public void run() {
								String file_name = input_file_name.getText();
								scan.scanFile(file_name, false);
							}
						}).start();
					}
				});
			}
		});

		camera.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				EventQueue.invokeLater(new Runnable() {
					// Overriding existing run() method
					@Override
					public void run() {
						final Camera camera = new Camera();

						// Start camera in thread
						new Thread(new Runnable() {
							@Override
							public void run() {
								camera.startCamera();
							}
						}).start();
					}
				});
			}
		});
		selectImgBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showFileChooserDialog();
			}
		});
	}

	void showFileChooserDialog() {
		if (fileChooser == null)
			fileChooser = new JFileChooser("C:");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setDialogTitle("Select JPG File");
		FileNameExtensionFilter restrict = new FileNameExtensionFilter("jpg", "jpg");
		fileChooser.addChoosableFileFilter(restrict);
		fileChooser.setMultiSelectionEnabled(false);
		int dialog = fileChooser.showOpenDialog(null);
		if (dialog == JFileChooser.APPROVE_OPTION) {
			input_file_name.setText(fileChooser.getSelectedFile().getAbsolutePath());
		}
	}

	public static void main(String[] args) {
		new HomeScreen();
	}
}