import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Canvas;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;

public class SwingMain extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	BufferedImage image;
	BufferedImage images[] = null;
	int imageNum;
	
	SwingMain window = this;
	JTabbedPane tabbedPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					SwingMain frame = new SwingMain();
					frame.setVisible(true);
				}catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SwingMain()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setTitle("Bitwise TileMapper");
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		JButton btnNew = new JButton("New");
		toolBar.add(btnNew);
		
		JButton btnExport = new JButton("Export");
		toolBar.add(btnExport);
		
		JButton btnSave = new JButton("Save tile");
		toolBar.add(btnSave);
		
		JButton btnLeft = new JButton("<=");
		toolBar.add(btnLeft);
		
		JButton buttonRight = new JButton("=>");
		toolBar.add(buttonRight);
		
		JButton btnCreateFromStrip = new JButton("Create from strip");
		toolBar.add(btnCreateFromStrip);
		
		JButton btnCreateFromFile = new JButton("Create from file");
		toolBar.add(btnCreateFromFile);
		
		JButton btnAddFromFile = new JButton("Add from file");
		toolBar.add(btnAddFromFile);
		
		JButton btnRemove = new JButton("Remove");
		toolBar.add(btnRemove);
		
		JButton btnGenerateTiles = new JButton("Generate tiles");
		toolBar.add(btnGenerateTiles);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		images = new BufferedImage[256];
		imageNum = -1;
	    
	    //NEW button
	    btnNew.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				//NEW
				if(imageNum < 255)
				{
					image = null;
			    	
			    	imageNum++;
			    	images[imageNum] = image;
					
			    	JLabel jLabel = new JLabel();
					jLabel.setVisible(true);
					
					tabbedPane.addTab("" + imageNum, null, jLabel, null);
				}
			}
		});
	    
	    //EXPORT button
	    btnExport.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				//EXPORT
				ExportWindow exportWin = new ExportWindow();
		    	exportWin.setImages(images);
		    	exportWin.setImageNum(imageNum);
		    	exportWin.setVisible(true);
			}
		});
	    
	    //SAVE button
	    btnSave.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				//SAVE
				JFileChooser fileChooser = new JFileChooser();
				int fileReturn = fileChooser.showDialog(null, "Save");
			    if(fileReturn == JFileChooser.APPROVE_OPTION)
			    {
			    	//get save location
			    	File file = fileChooser.getSelectedFile();
			    	
			    	//export image
					try
					{
						image = images[tabbedPane.getSelectedIndex()];
					    ImageIO.write(image, "png", new File(file.toString()+".png"));
					    System.out.println("Saved " + file + ".png");
					}catch (IOException e)
					{
						e.printStackTrace();
					}
			    }
			}
		});
	    
	  // <= (LEFT) button
	    btnLeft.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				//LEFT
				int index = tabbedPane.getSelectedIndex();
				if(index != 0)
				{
					BufferedImage temp = images[index];
					images[index] = images[index-1];
					images[index-1] = temp;
					
					tabbedPane.remove(index-1);
					
					image = images[index];
					JLabel jLabel;
					if(image != null)
						jLabel = new JLabel(new ImageIcon(image));
					else
						jLabel = new JLabel();
			    	jLabel.setVisible(true);
					
					tabbedPane.insertTab("" + index, null, jLabel, null, index);
					
					tabbedPane.setTitleAt(index-1, "" + (index-1));
					
				}
			}
		});
	    
	  // => (RIGHT) button
	    buttonRight.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				//RIGHT
				int index = tabbedPane.getSelectedIndex();
				if(index < imageNum)
				{
					BufferedImage temp = images[index];
					images[index] = images[index+1];
					images[index+1] = temp;
					
					tabbedPane.remove(index+1);
					
					image = images[index];
					JLabel jLabel;
					if(image != null)
						jLabel = new JLabel(new ImageIcon(image));
					else
						jLabel = new JLabel();
			    	jLabel.setVisible(true);
					
					tabbedPane.insertTab("" + index, null, jLabel, null, index);
					
					tabbedPane.setTitleAt(index+1, "" + (index+1));
				}
			}
		});
	    
	  //CREATE FROM STRIP button
	    btnCreateFromStrip.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				//CREATE FROM STRIP
				JFileChooser fileChooser = new JFileChooser();
				int fileReturn = fileChooser.showDialog(null, "Import");
			    if(fileReturn == JFileChooser.APPROVE_OPTION)
			    {
			    	File file = fileChooser.getSelectedFile();
			    	System.out.println("Opened " + file);
			    	
			    	try{image = ImageIO.read(file);}
			    	catch(IOException e){e.printStackTrace();}
			    	
			    	ImportWindow importWin = new ImportWindow(window, image);
			    	importWin.setImage(image);
			    	importWin.setVisible(true);
			    }
			}
		});
	    
	    //CREATE FROM FILE button
	    btnCreateFromFile.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				//CREATE FROM FILE
				JFileChooser fileChooser = new JFileChooser();
				int fileReturn = fileChooser.showDialog(null, "Import");
			    if(fileReturn == JFileChooser.APPROVE_OPTION)
			    {
			    	File file = fileChooser.getSelectedFile();
			    	System.out.println("Opened " + file);
			    	
			    	try{image = ImageIO.read(file);}
			    	catch(IOException e){e.printStackTrace();}
			    	
			    	tabbedPane.removeAll();
			    	
			    	images[0] = image;
			    	imageNum = 0;
					
			    	JLabel jLabel = new JLabel(new ImageIcon(image));
					jLabel.setVisible(true);
					
					tabbedPane.addTab("0", null, jLabel, null);
			    }
			}
		});
	    
	    //ADD FROM FILE button
	    btnAddFromFile.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				//ADD FROM FILE
				if(imageNum < 255)
				{
					JFileChooser fileChooser = new JFileChooser();
					int fileReturn = fileChooser.showDialog(null, "Import");
				    if(fileReturn == JFileChooser.APPROVE_OPTION)
				    {
				    	File file = fileChooser.getSelectedFile();
				    	System.out.println("Opened " + file);
				    	
				    	try{image = ImageIO.read(file);}
				    	catch(IOException e){e.printStackTrace();}
				    	
				    	imageNum++;
				    	images[imageNum] = image;
						
				    	JLabel jLabel = new JLabel(new ImageIcon(image));
						jLabel.setVisible(true);
						
						tabbedPane.addTab("" + imageNum, null, jLabel, null);
				    }
				}
			}
		});
	    
	  //REMOVE button
	    btnRemove.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				//REMOVE
				if(imageNum >= 0)
				{
					for(int i=tabbedPane.getSelectedIndex();i<=imageNum-1;i++)
			    		images[i] = images[i+1];
			    	images[imageNum] = null;
			    	imageNum--;
			    	
			    	tabbedPane.remove(tabbedPane.getSelectedIndex());
			    	
			    	if(imageNum > 0)
				    	for(int i=tabbedPane.getSelectedIndex();i<=imageNum;i++)
				    		tabbedPane.setTitleAt(i, "" + i);
				}
			}
		});
	    
	  //GENERATE TILES button
	    btnGenerateTiles.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				//GENERATE
				if(imageNum >= 4)
				{
					tabbedPane.removeAll();
					
					//derive remaining images
					if(imageNum >= 8)
					{
						//n = 256
						images[16] = images[5];
						images[5] = null;
						images[32] = images[6];
						images[6] = null;
						images[64] = images[7];
						images[7] = null;
						images[128] = images[8];
						images[8] = images[4];
						images[4] = images[3];
						images[3] = null;
						
						imageNum = 255;
						
						for(int i=1;i<256;i++)
						{
							int num = i;
							int[] vals = new int[10];
							int v = 9;
							for(int j=0;j<10;j++)
								vals[j] = 0;
							for(int j=128;j>=1;j/=2)
							{
								if(num >= j)
								{
									vals[v--] = j;
									num -= j;
								}
								if(num == 0)
									break;
							}
							images[i] = images[vals[9]];
							for(int j=8;j>0;j--)
								if(vals[j] != 0)
									images[i] = combineImages(images[i],images[vals[j]]);
						}
					}
					else
					{
						//n = 16
						images[8] = images[4];
						images[4] = images[3];
						images[3] = null;
						
						imageNum = 15;
						
						images[3] = combineImages(images[1], images[2]);
						images[5] = combineImages(images[1], images[4]);
						images[6] = combineImages(images[2], images[4]);
						images[7] = combineImages(images[1], images[2], images[4]);
						images[9] = combineImages(images[1], images[8]);
						images[10] = combineImages(images[2], images[8]);
						images[11] = combineImages(images[1], images[2], images[8]);
						images[12] = combineImages(images[4], images[8]);
						images[13] = combineImages(images[1], images[4], images[8]);
						images[14] = combineImages(images[2], images[4], images[8]);
						images[15] = combineImages(images[1], images[2], images[4], images[8]);
					}

					
					
					//fill tabbedPane
					for(int i=0;i<=imageNum;i++)
					{
						image = images[i];
						JLabel jLabel = new JLabel(new ImageIcon(image));
						jLabel.setVisible(true);
						tabbedPane.addTab("" + i, null, jLabel, null);
					}
				}
			}
		});
	}//end constructor
	
	/**
	 * Private helper method that layers two BufferedImages and returns the resulting BufferedImage.
	 */
	private BufferedImage combineImages(BufferedImage bi1, BufferedImage bi2)
	{
		int w = bi1.getWidth(null);
    	int h = bi1.getHeight(null);
    	BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g = bi.createGraphics();
    	g.drawImage(bi1, 0, 0, null);
    	g.drawImage(bi2, 0, 0, null);
    	
    	return(bi);
	}//end 2 arg combineImages
	
	private BufferedImage combineImages(BufferedImage bi1, BufferedImage bi2, BufferedImage bi3)
	{
		int w = bi1.getWidth(null);
    	int h = bi1.getHeight(null);
    	BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g = bi.createGraphics();
    	g.drawImage(bi1, 0, 0, null);
    	g.drawImage(bi2, 0, 0, null);
    	g.drawImage(bi3, 0, 0, null);
    	
    	return(bi);
	}//end 3 arg combineImages
	
	private BufferedImage combineImages(BufferedImage bi1, BufferedImage bi2, BufferedImage bi3, BufferedImage bi4)
	{
		int w = bi1.getWidth(null);
    	int h = bi1.getHeight(null);
    	BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g = bi.createGraphics();
    	g.drawImage(bi1, 0, 0, null);
    	g.drawImage(bi2, 0, 0, null);
    	g.drawImage(bi3, 0, 0, null);
    	g.drawImage(bi4, 0, 0, null);
    	
    	return(bi);
	}//end 4 arg combineImages
	
}//end class


