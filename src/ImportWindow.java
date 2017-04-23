import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class ImportWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextPane txtpnImageWidth;
	private JTextField textField_3;
	private JTextPane txtpnImageHeight;
	private JTextField textField_4;
	
	BufferedImage image;
	BufferedImage images[] = null;
	int imageNum = 0;
	static boolean ready = false;
	static boolean finished = false;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImportWindow frame = new ImportWindow(new SwingMain(), null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ImportWindow(SwingMain m, BufferedImage img)
	{
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int h = 250;
		if(img.getHeight() > 200)
			h = img.getHeight()+64; 
		setBounds(100, 100, img.getWidth()+300, h);
		setTitle("Import Strip or Tileset");
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane txtpnImagesPerRow = new JTextPane();
		txtpnImagesPerRow.setEditable(false);
		txtpnImagesPerRow.setBounds(26, 11, 116, 20);
		txtpnImagesPerRow.setText("Images per row");
		contentPane.add(txtpnImagesPerRow);
		
		textField = new JTextField();
		textField.setBounds(178, 11, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setText("1");
		
		JTextPane txtpnPadding = new JTextPane();
		txtpnPadding.setEditable(false);
		txtpnPadding.setBounds(26, 42, 116, 20);
		txtpnPadding.setText("Padding");
		contentPane.add(txtpnPadding);
		
		textField_1 = new JTextField();
		textField_1.setBounds(178, 42, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText("0");
		
		JButton btnImport = new JButton("Import");
		btnImport.setBounds(26, 178, 80, 23);
		contentPane.add(btnImport);
		
		JTextPane txtpnNumberOfImages = new JTextPane();
		txtpnNumberOfImages.setEditable(false);
		txtpnNumberOfImages.setText("Number of images");
		txtpnNumberOfImages.setBounds(26, 73, 116, 20);
		contentPane.add(txtpnNumberOfImages);
		
		textField_2 = new JTextField();
		textField_2.setText("1");
		textField_2.setBounds(178, 73, 86, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		txtpnImageWidth = new JTextPane();
		txtpnImageWidth.setText("Image width");
		txtpnImageWidth.setBounds(26, 104, 116, 20);
		contentPane.add(txtpnImageWidth);
		
		textField_3 = new JTextField();
		textField_3.setText("32");
		textField_3.setBounds(178, 104, 86, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		txtpnImageHeight = new JTextPane();
		txtpnImageHeight.setText("Image height");
		txtpnImageHeight.setBounds(26, 135, 116, 20);
		contentPane.add(txtpnImageHeight);
		
		textField_4 = new JTextField();
		textField_4.setText("32");
		textField_4.setBounds(178, 135, 86, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel label = new JLabel(new ImageIcon(img));
		label.setBounds(274, 11, img.getWidth(), img.getHeight());
		contentPane.add(label);
		
		
		//IMPORT button
	    btnImport.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				//IMPORT
				
		    	//get grid settings
		    	int imagesPerRow = Integer.parseInt(textField.getText());
		    	int padding = Integer.parseInt(textField_1.getText());
		    	imageNum = Integer.parseInt(textField_2.getText())-1;
		    	int width = Integer.parseInt(textField_3.getText());
		    	int height = Integer.parseInt(textField_4.getText());
		    	
		    	images = new BufferedImage[256];
		    	
		    	//parse strip
		    	int index = 0;
		    	for(int i=0;i<=imageNum/imagesPerRow;i++)
		    		for(int j=0;j<imagesPerRow;j++)
		    		{
		    			if(index > imageNum)
		    				break;
		    			images[index++] = image.getSubimage(j*width+padding*j, i*height+padding*i, width, height);
		    		}
				
		    	m.images = images;
		    	m.imageNum = imageNum;
		    	
		    	m.tabbedPane.removeAll();
		    	
		    	//fill tabbedPane
				for(int i=0;i<=imageNum;i++)
				{
					image = images[i];
					JLabel jLabel = new JLabel(new ImageIcon(image));
					jLabel.setVisible(true);
					m.tabbedPane.addTab("" + i, null, jLabel, null);
				}
				
				dispose();
		    }
		});
	}//end constructor
	
	public void setImage(BufferedImage img)
	{
		image = img;
	}//end setImages
	
	public int getImageNum()
	{
		return(imageNum);
	}//end getImageNum
	
	public BufferedImage[] getImages()
	{
		return(images);
	}//end getImages
}
