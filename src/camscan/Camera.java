package camscan;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.*;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class Camera extends JFrame {

	private JLabel cameraScreen;

	private JButton btnCapture;

	private VideoCapture capture;

	private Mat image;

	private boolean clicked = false;

	public Camera() {

		// Designing UI
		setLayout(null);

		cameraScreen = new JLabel();
		cameraScreen.setBounds(0, 0, 640, 480);
		add(cameraScreen);

		btnCapture = new JButton("capture");
		btnCapture.setBounds(300, 480, 80, 40);
		add(btnCapture);

		btnCapture.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				clicked = true;
			}
		});

		setSize(new Dimension(640, 560));
		setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	// Creating a camera
	public void startCamera() {
		capture = new VideoCapture(0);
		image = new Mat();
		byte[] imageData;

		ImageIcon icon;
		while (true) {
			// read image to matrix
			capture.read(image);

			// convert matrix to byte
			final MatOfByte buf = new MatOfByte();
			Imgcodecs.imencode(".jpg", image, buf);

			imageData = buf.toArray();

			// Add to JLabel
			icon = new ImageIcon(imageData);
			cameraScreen.setIcon(icon);

			// Capture and save to file
			if (clicked) {

				String name = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				Imgcodecs.imwrite("images/" + name + ".jpg", image);
				Scan scan = new Scan();
				scan.scanFile(name, true);
				clicked = false;
			}
		}
	}

}
