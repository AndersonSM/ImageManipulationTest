

import javax.imageio.ImageIO;
import javax.swing.*;

import com.jhlabs.image.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

public class Principal extends JPanel implements ActionListener{
	ContrastFilter contrast = new ContrastFilter();
	CropFilter crop = new CropFilter();
	FlipFilter flip = new FlipFilter(4);
	JTextField contrastField;
	JTextField brightnessField;
	JTextField xField;
	JTextField yField;
	JTextField widthField;
	JTextField heightField;
	JButton cropButton;
	JButton apply;
	JButton flipButton;
	JButton reset;
	File fileImg2 = new File("Teste.jpg");
	BufferedImage img1 = ImageIO.read(fileImg2);
	BufferedImage img2 = ImageIO.read(fileImg2);
	BufferedImage img3 = ImageIO.read(fileImg2);
	boolean changedSource = false;
	BufferedImage source = img1;
	BufferedImage destiny = img2;
	JFrame novoF;
	
	public Principal() throws IOException{
		JLabel lblimage2 = new JLabel(new ImageIcon(source));
		apply = new JButton("Apply");
		cropButton = new JButton("Crop");
		flipButton = new JButton("Flip 90");
		reset = new JButton("Reset");
		xField = new JTextField();
		xField.setColumns(5);
		yField = new JTextField();
		yField.setColumns(5);
		widthField = new JTextField();
		widthField.setColumns(5);
		heightField = new JTextField();
		heightField.setColumns(5);
		JLabel contrastLabel = new JLabel("Contraste");
		JLabel brightnessLabel = new JLabel("Brilho");
		JLabel xLabel = new JLabel("x");
		JLabel yLabel = new JLabel("y");
		JLabel widthLabel = new JLabel("Width");
		JLabel heightLabel = new JLabel("Height");
		contrastField = new JTextField();
		contrastField.setColumns(20);
		
		brightnessField = new JTextField();
		brightnessField.setColumns(20);
		
		apply.addActionListener(this);
		cropButton.addActionListener(this);
		flipButton.addActionListener(this);
		reset.addActionListener(this);
		
		add(xLabel);
		add(xField);
		add(yLabel);
		add(yField);
		add(widthLabel);
		add(widthField);
		add(heightLabel);
		add(heightField);
		add(cropButton);
		add(contrastLabel);
		add(contrastField);
		add(brightnessLabel);
		add(brightnessField);
		add(apply);
		add(flipButton);
		add(reset);
		add(lblimage2, BorderLayout.EAST);
	}
	public static void main(String[] args) throws IOException {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
					createAndShowGUI();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
            }
        });
		
		//RotateFilter rotate = new RotateFilter(-0.04f, false);
		//rotate.filter(img3, img3);
		
		
		/*BufferedImage img4 = new BufferedImage(img3.getWidth(), img3.getHeight(), img3.getType());
		flip.filter(img3, img4);*/

	}
	
	private void setBrightness(float value){
		contrast.setBrightness(value);
		contrast.filter(source, destiny);
	}
	
	private void setContrast(float value){
		//img2 = new BufferedImage(img1.getWidth(), img1.getHeight(), img1.getType());
		contrast.setContrast(value);
		contrast.filter(source, destiny);
	}
	
	private void setCrop(int x, int y, int width, int height){
		// 80 150 600 900
		changedSource = true;
		
		crop.setWidth(width);
		crop.setHeight(height);
		crop.setX(x);
		crop.setY(y);
		
		img3 = new BufferedImage(width, height, source.getType());
		crop.filter(source, img3);
		img2 = new BufferedImage(width, height, source.getType());
		crop.filter(img3, img2);
		
		source = img2;
		destiny = img3;
	}
	
	private void flip90(){
		destiny = new BufferedImage(source.getHeight(), source.getWidth(), source.getType());
		flip.filter(source, destiny);
		BufferedImage aux = source;
		aux = new BufferedImage(source.getHeight(), source.getWidth(), source.getType());
		flip.filter(source, aux);
		source = aux;
		img3 = aux;
	}
	
	private void reset() throws IOException{
		img1 = ImageIO.read(fileImg2);
		img2 = ImageIO.read(fileImg2);
		img3 = ImageIO.read(fileImg2);
		source = img1;
		destiny = img2;
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource(); 
		if(source == cropButton){
			if(!xField.getText().isEmpty() && !yField.getText().isEmpty()
					&& !widthField.getText().isEmpty() && !heightField.getText().isEmpty()){	
				int x = Integer.parseInt(xField.getText());
				int y = Integer.parseInt(yField.getText());
				int width = Integer.parseInt(widthField.getText());
				int height = Integer.parseInt(heightField.getText());
				setCrop(x, y, width, height);
			}
		}
		else if(source == flipButton){
			flip90();
		}
		else if(source == reset){
			try {
				reset();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else{
			if(!contrastField.getText().isEmpty()){
				float contrastValue = Float.parseFloat(contrastField.getText());
				if(contrastValue >= 0 && contrastValue <= 2)
					setContrast(contrastValue);
			}
			if(!brightnessField.getText().isEmpty()){
				float brightnessValue = Float.parseFloat(brightnessField.getText());
				if(brightnessValue >= 0 && brightnessValue <= 2)
					setBrightness(brightnessValue);
			}
		}
		createUpdateWindow();
		//revalidate();
		//repaint();
	}
	
	private void createUpdateWindow(){
		novoF = new JFrame();
		novoF.add(new JLabel(new ImageIcon(destiny)));
		novoF.setSize(800, 1100);
		novoF.setVisible(true);
	}
	
	private static void createAndShowGUI() throws IOException {

        //Create and set up the window.
        JFrame frame = new JFrame("Teste");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 1000);

        //Create and set up the content pane.
        Principal newContentPane = new Principal();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
