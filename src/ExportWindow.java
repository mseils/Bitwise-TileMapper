import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.CardLayout;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ExportWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	
	BufferedImage image;
	BufferedImage images[] = null;
	int imageNum;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExportWindow frame = new ExportWindow();
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
	public ExportWindow()
	{
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 200);
		setTitle("Export Tileset");
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane txtpnImagesPerRow = new JTextPane();
		txtpnImagesPerRow.setEditable(false);
		txtpnImagesPerRow.setBounds(26, 11, 100, 20);
		txtpnImagesPerRow.setText("Images per row");
		contentPane.add(txtpnImagesPerRow);
		
		textField = new JTextField();
		textField.setBounds(150, 11, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setText(Integer.toString(16));
		
		JTextPane txtpnPadding = new JTextPane();
		txtpnPadding.setEditable(false);
		txtpnPadding.setBounds(26, 53, 100, 20);
		txtpnPadding.setText("Padding");
		contentPane.add(txtpnPadding);
		
		textField_1 = new JTextField();
		textField_1.setBounds(150, 53, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText(Integer.toString(2));
		
		JButton btnExport = new JButton("Export");
		btnExport.setBounds(26, 112, 80, 23);
		contentPane.add(btnExport);
		
		
		//EXPORT button
	    btnExport.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				//EXPORT
				JFileChooser fileChooser = new JFileChooser();
				int fileReturn = fileChooser.showDialog(null, "Export");
			    if(fileReturn == JFileChooser.APPROVE_OPTION)
			    {
			    	//get export location
			    	File file = fileChooser.getSelectedFile();
			    	
			    	//get grid settings
			    	int imagesPerRow = Integer.parseInt(textField.getText());
			    	int padding = Integer.parseInt(textField_1.getText());
			    	
			    	//create strip
			    	int w = images[0].getWidth(null)*imagesPerRow + padding*(imagesPerRow+1);
			    	int h;
			    	if(imageNum > imagesPerRow)
			    		h = (int) ((images[0].getHeight(null)*Math.ceil(((float)imageNum)/((float)imagesPerRow)) + (padding*(Math.ceil(((float)imageNum)/((float)imagesPerRow))+1))));
			    	else
			    		h = images[0].getHeight(null);
			    	BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			    	Graphics2D g = bi.createGraphics();
			    	
			    	int num = 0;
			    	for(int i=0;i<=imageNum/imagesPerRow;i++)
			    		for(int j=0;j<imagesPerRow;j++)
			    		{
			    			if(num > imageNum)
			    				break;
			    			else
			    				g.drawImage(images[num++], images[0].getWidth(null)*j + padding*j, images[0].getHeight(null)*i + padding*i, null);
			    		}
			    	
			    	//export image
					try
					{
						image = bi;
					    ImageIO.write(image, "png", new File(file.toString()+".png"));
					    System.out.println("Saved " + file + ".png");
					}catch (IOException e)
					{
						e.printStackTrace();
					}
					
					dispose();
			    }
			}
		});
	}//end constructor
	
	public void setImages(BufferedImage[] img)
	{
		images = img;
	}//end setImages
	
	public void setImageNum(int num)
	{
		imageNum = num;
	}//end setImageNum

}
