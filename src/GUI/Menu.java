package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.AbstractDocument.Content;

import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import java.io.File;

public class Menu extends JMenuBar{
	private JFileChooser saveJFC;
	private JFileChooser saveHTML;

	private JFileChooser openJFC;
	private JFileChooser openMD;
	
	public Menu(Editor myeditor,display mydisplay) {
		// TODO �Զ����ɵĹ��캯�����
		this.openJFC=new JFileChooser("./");
		this.saveJFC=new JFileChooser("./");
		this.openMD=new JFileChooser("./");
		this.saveHTML=new JFileChooser("./");
		
		this.openJFC.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
		this.openMD.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
		this.saveJFC.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
		this.saveHTML.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
		
		this.openJFC.setAcceptAllFileFilterUsed(false);
		this.openMD.setAcceptAllFileFilterUsed(false);
		this.saveJFC.setAcceptAllFileFilterUsed(false);
		this.saveHTML.setAcceptAllFileFilterUsed(false);
		
		this.openJFC.addChoosableFileFilter(new AFileFilter(".css","����css��ʽ"));
		this.openMD.addChoosableFileFilter(new AFileFilter(".md","����md�ļ�"));
		this.saveJFC.addChoosableFileFilter(new AFileFilter(".docx", "����docx�ļ�"));
		this.saveHTML.addChoosableFileFilter(new AFileFilter(".html", "����html�ļ�"));
		
		JMenu filemenu=new JMenu("File");
		
		JMenuItem importcss=new JMenuItem("import css");
		JMenuItem importmarkdown =new JMenuItem("import markdown");
		JMenuItem exportdcox=new JMenuItem("export docx");
		JMenuItem exporthtml=new JMenuItem("export html");
		
		filemenu.add(importcss);
		filemenu.add(importmarkdown);
		filemenu.add(exportdcox);
		filemenu.add(exporthtml);
		
		this.add(filemenu);
		
		importcss.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO �Զ����ɵķ������
				openJFC.setDialogTitle("import css file");
				if(openJFC.showDialog(null, "import")==JFileChooser.APPROVE_OPTION)
				{
					File file=openJFC.getSelectedFile();
					if(file.isFile())
					{
						BufferedReader content;
						try {
							content = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
							String temp="";
							String text="";
							while((temp=content.readLine())!=null)
							{
								text=text+temp+"\n";
							}
							if(file.getName().endsWith(".css"))
							{
								mydisplay.importCSS(text);
								myeditor.Update(mydisplay);
								
							}
							else
							{
								JOptionPane.showMessageDialog(null, "please import .css file", "warning", JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (FileNotFoundException e) {
							// TODO �Զ����ɵ� catch ��
							e.printStackTrace();
						} catch (IOException e) {
							// TODO �Զ����ɵ� catch ��
							e.printStackTrace();
						}

						
					}
				}
			}
		});
		
		importmarkdown.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO �Զ����ɵķ������
				openMD.setDialogTitle("import md file");
				if(openMD.showDialog(null, "import")==JFileChooser.APPROVE_OPTION)
				{
					File file=openMD.getSelectedFile();
					if(file.isFile())
					{
						BufferedReader content;
						try {
							content = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
							String temp="";
							String text="";
							while((temp=content.readLine())!=null)
							{
								text=text+temp+"\n";
							}
							if(file.getName().endsWith(".md")){       //if markdown
                                myeditor.geteditor().setText(text);
                                myeditor.geteditor().setCaretPosition(0);//������������0��
                            }
							else
							{
								JOptionPane.showMessageDialog(null, "please import .md file", "warning", JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (FileNotFoundException e) {
							// TODO �Զ����ɵ� catch ��
							e.printStackTrace();
						} catch (IOException e) {
							// TODO �Զ����ɵ� catch ��
							e.printStackTrace();
						}

						
					}
				}
			}
		});
		
		exportdcox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				saveJFC.setSelectedFile(new File("ouput.docx"));
				saveJFC.setDialogTitle("export to docx file");
				
				if(saveJFC.showDialog(null, "export")==JFileChooser.APPROVE_OPTION)
				{
					File file=saveJFC.getSelectedFile();
					String pathname="";
					if(file.isDirectory())
					{
						pathname=file.getAbsolutePath()+"/output.docx";
					}
					else
					{
						pathname = file.getAbsolutePath();
                        if (!pathname.toLowerCase().endsWith(".docx")) 
                        {
                        	pathname += ".docx";
                        }
					}
					
					WordprocessingMLPackage wordMLPackage;
					try {
						wordMLPackage = WordprocessingMLPackage.createPackage(PageSizePaper.A4, true);
	                    XHTMLImporterImpl xhtmlImporter = new XHTMLImporterImpl(wordMLPackage);
	                    //��һ������������һ��html�ļ�����html�ַ���
	                    wordMLPackage.getMainDocumentPart().getContent().addAll(xhtmlImporter.convert(mydisplay.gethtml(), null));
	                    wordMLPackage.save(new File(pathname));     //export docx file
					} catch (InvalidFormatException e1) {
						// TODO �Զ����ɵ� catch ��
						e1.printStackTrace();
					} catch (Docx4JException e1) {
						// TODO �Զ����ɵ� catch ��
						e1.printStackTrace();
					}

				}
			}
		});

		exporthtml.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				saveHTML.setSelectedFile(new File("ouput.html"));
				saveHTML.setDialogTitle("export to html file");
				
				if(saveHTML.showDialog(null, "export")==JFileChooser.APPROVE_OPTION)
				{
					File file=saveHTML.getSelectedFile();
					String pathname="";
					if(file.isDirectory())
					{
						pathname=file.getAbsolutePath()+"/output.html";
					}
					else
					{
						pathname = file.getAbsolutePath();
                        if (!pathname.toLowerCase().endsWith(".html")) 
                        {
                        	pathname += ".html";
                        }
					}
					
					getFileFromBytes(mydisplay.gethtml(),pathname);

				}
			}
		});
		
	}
	
	/**  
	  * ��String���ݴ�Ϊ�ļ�  
	  */  
	 public static File getFileFromBytes(String name,String path) 
	 {  
	    byte[] b=name.getBytes();  
	     BufferedOutputStream stream = null;  
	     File file = null;  
	     try {  
	         file = new File(path);  
	         FileOutputStream fstream = new FileOutputStream(file);  
	         stream = new BufferedOutputStream(fstream);  
	         stream.write(b);  
	     } catch (Exception e) {  
	         e.printStackTrace();  
	     } finally {  
	         if (stream != null) {  
	             try {  
	                 stream.close();  
	             } catch (IOException e1) {  
	                 e1.printStackTrace();  
	             }  
	         }  
	     }  
	     return file;  
	 } 

}

class AFileFilter extends FileFilter
{
	private String end;
	private String description;
	
	public AFileFilter(String end,String description) {
		// TODO �Զ����ɵĹ��캯�����
		this.end=end;
		this.description=description;
	}
	@Override
	public boolean accept(File pathname) {
		// TODO �Զ����ɵķ������
		if(pathname.isDirectory())
			return true;
		
		if(pathname.getName().toLowerCase().endsWith(end))
			return true;
		
		return false;
	}
	@Override
	public String getDescription() {
		// TODO �Զ����ɵķ������
		return this.description;
	}
	
	
	
}