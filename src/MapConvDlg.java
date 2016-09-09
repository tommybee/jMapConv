package com.tobee.gis.ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.tobee.gis.coordconv.GeoProjection.GeoEllips;
import com.tobee.gis.coordconv.GeoProjection.GeoSystem;
import com.tobee.gis.coordconv.MapConv;
import com.tobee.gis.coordconv.GeoCoordConv;
import com.tobee.gis.coordconv.GeoException;
import com.tobee.gis.coordconv.CoordsInfo;
import com.tobee.gis.support.MutableDouble;
import com.tobee.gis.support.MutableInteger;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class MapConvDlg extends javax.swing.JDialog {
	private JPanel InputPane;
	private JComboBox EllipsTypeCombo;
	private JTextField LonMinTxt2;
	private JTextField LonDegTxt2;
	private JTextField LatSecTxt2;
	private JTextField LatMinTxt2;
	private JTextField LatDegTxt2;
	private JLabel jLabel10;
	private JLabel jLabel9;
	private JLabel jLabel8;
	private JLabel jLabel7;
	private JLabel jLabel6;
	private JLabel jLabel5;
	private JTextField LonTxt2;
	private JLabel LonDegLbl2;
	private JLabel LatDegLbl2;
	private JComboBox ProjCombo2;
	private JLabel PrjLbl2;
	private JLabel EllpsLbl;
	private JComboBox EllpsCombo2;
	private JLabel LonSecLbl;
	private JTextField LonSecTxt;
	private JTextField LonMinTxt;
	private JTextField LonDegTxt;
	private JLabel LonMinLbl;
	private JLabel LonDegLbl;
	private JTextField LatSecTxt;
	private JTextField LatMinTxt;
	private JLabel EllipsLbl;
	private JLabel ProjLbl;
	private JTextField LonSecTxt2;
	private JTextField YCoordOutTxt;
	private JLabel YCoordOutLbl;
	private JTextField XCoordOutTxt;
	private JLabel XCoordOut;
	private JTextField YCoordInTxt;
	private JLabel YCoordIn;
	private JTextField LatTxt2;
	private JLabel LatSecLbl;
	private JLabel LatMinLbl;
	private JLabel LatDegLbl3;
	private JTextField LonTxt;
	private JTextField LatTxt;
	private JLabel LonLbl;
	private JPanel CoordPaneOut;
	private JButton ExtBtn;
	private JButton FileConvBtn;
	private JButton ConvertBtn;
	private JLabel LatLbl;
	private JComboBox ProjectionCombo;
	private JTextField LatDegTxt;
	private JButton ClearBtn;
	
	private MapConvComboListener ConvListen;
	private MapConvButtonListener ConvBtnListen;
	private CoordsChangeListen CoordsFieldListen;
	private JTextField XCoordInTxt;
	private JLabel XCoordIn;
	
	
	private GeoEllips m_eSrcEllips;
	private GeoSystem m_eSrcSystem;
	private GeoEllips m_eDstEllips;
	private GeoSystem m_eDstSystem;
	
	private MapConv mapConverter;
	
	private CoordsInfo m_coordsinfo;
	
	private static final int COMBO_SRC_TYPE = 0;
	private static final int COMBO_DEST_TYPE = 1;
	
	private static final File CURRENT_DIR = new File(".");
	
	/**
	* Auto-generated main method to display this JDialog
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MapConvDlg inst = new MapConvDlg();
				inst.setVisible(true);
				
				inst.addWindowListener(new WindowAdapter() {   
					  @Override  
				      public void windowClosing(WindowEvent e) {   
				            System.exit(0);   
				        }   
				    });
			}
		});
	}
	
	static class DummyFrame extends JFrame {     
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		DummyFrame(String title) 
		{         
			super(title);         
			setUndecorated(true);         
			setVisible(true);         
			setLocationRelativeTo(null);              
		} 
	} 
	
	public MapConvDlg() {
		super(new DummyFrame("좌표 변환 프로그램"), "좌표 변환 프로그램", true);
		ConvListen = new MapConvComboListener();
		ConvBtnListen = new MapConvButtonListener();
		CoordsFieldListen = new CoordsChangeListen();
		mapConverter = new MapConv();
		m_coordsinfo = new CoordsInfo();
		initGUI();
	}
	
	public MapConvDlg(JFrame frame) {
		super(frame, "좌표 변환 프로그램", true);
		ConvListen = new MapConvComboListener();
		ConvBtnListen = new MapConvButtonListener();
		CoordsFieldListen = new CoordsChangeListen();
		mapConverter = new MapConv();
		m_coordsinfo = new CoordsInfo();
		initGUI();
	}
	
	private void initGUI() {
		try {
			getContentPane().setLayout(null);
			this.setEnabled(true);
			{
				InputPane = new JPanel();
				getContentPane().add(InputPane);
				InputPane.setBounds(24, 18, 280, 219);
				InputPane.setBorder(BorderFactory.createTitledBorder(null, "경위도 혹은 투영좌표 입력", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION));
				InputPane.setLayout(null);
				{
					ComboBoxModel EllipsTypeComboModel = 
						new DefaultComboBoxModel(
								new String[] { "Bessel 1841", "WGS84", "GRS80" });
					EllipsTypeComboModel.setSelectedItem("WGS84");
					EllipsTypeCombo = new JComboBox();
					InputPane.add(EllipsTypeCombo);
					EllipsTypeCombo.setModel(EllipsTypeComboModel);
					EllipsTypeCombo.setBounds(17, 44, 115, 22);
					EllipsTypeCombo.setSelectedItem(EllipsTypeCombo);
					EllipsTypeCombo.setSelectedIndex(0);
					EllipsTypeCombo.setFocusTraversalPolicyProvider(true);
					EllipsTypeCombo.addActionListener(ConvListen);
				}
				{
					ComboBoxModel ProjectionComboModel = 
						new DefaultComboBoxModel(
								new String[] { "Geographic", 
										"TM Korea Middle", "TM Korea West", 
										"TM Korea East", "KATEC", "UTM 52N", "UTM 51N" });
					ProjectionComboModel.setSelectedItem("Geographic");
					ProjectionCombo = new JComboBox();
					InputPane.add(ProjectionCombo);
					ProjectionCombo.setModel(ProjectionComboModel);
					ProjectionCombo.setBounds(151, 43, 134, 22);
					ProjectionCombo.setSize(115, 22);
					ProjectionCombo.addActionListener(ConvListen);
				}
				{
					EllipsLbl = new JLabel();
					InputPane.add(EllipsLbl);
					EllipsLbl.setText("타원체");
					EllipsLbl.setBounds(17, 24, 41, 15);
				}
				{
					ProjLbl = new JLabel();
					InputPane.add(ProjLbl);
					ProjLbl.setText("좌표계");
					ProjLbl.setBounds(151, 24, 48, 15);
				}
				{
					LatLbl = new JLabel();
					InputPane.add(LatLbl);
					LatLbl.setText("경도(Degree)");
					LatLbl.setBounds(17, 72, 82, 15);
				}
				{
					LonLbl = new JLabel();
					InputPane.add(LonLbl);
					LonLbl.setText("위도(Degree)");
					LonLbl.setBounds(151, 72, 86, 15);
				}
				{
					LatTxt = new JTextField();
					InputPane.add(LatTxt);
					LatTxt.setBounds(17, 93, 115, 19);
					LatTxt.setSize(115, 22);
					LatTxt.setEditable(false);
					LatTxt.addFocusListener(CoordsFieldListen);
					// getDocument().addDocumentListener(CoordsTxtListen);
				}
				{
					LonTxt = new JTextField();
					InputPane.add(LonTxt);
					LonTxt.setBounds(151, 92, 134, 20);
					LonTxt.setSize(115, 22);
					LonTxt.setEditable(false);
					LonTxt.addFocusListener(CoordsFieldListen);
				}
				{
					LatDegLbl2 = new JLabel();
					InputPane.add(LatDegLbl2);
					LatDegLbl2.setText("도(D)");
					LatDegLbl2.setBounds(17, 119, 30, 15);
				}
				{
					LatMinLbl = new JLabel();
					InputPane.add(LatMinLbl);
					LatMinLbl.setText("분(M)");
					LatMinLbl.setBounds(58, 119, 33, 15);
				}
				{
					LatSecLbl = new JLabel();
					InputPane.add(LatSecLbl);
					LatSecLbl.setText("초(S)");
					LatSecLbl.setBounds(99, 119, 30, 15);
					
				}
				{
					LatDegTxt = new JTextField();
					InputPane.add(LatDegTxt);
					LatDegTxt.setBounds(17, 140, 36, 22);
					LatDegTxt.setEditable(false);
					LatDegTxt.addFocusListener(CoordsFieldListen);
				}
				{
					LatMinTxt = new JTextField();
					InputPane.add(LatMinTxt);
					LatMinTxt.setBounds(58, 140, 33, 22);
					LatMinTxt.setSize(36, 22);
					LatMinTxt.setEditable(false);
					LatMinTxt.addFocusListener(CoordsFieldListen);
				}
				{
					LatSecTxt = new JTextField();
					InputPane.add(LatSecTxt);
					LatSecTxt.setBounds(97, 140, 31, 22);
					LatSecTxt.setSize(36, 22);
					LatSecTxt.setEditable(false);
					LatSecTxt.addFocusListener(CoordsFieldListen);
				}
				{
					LonDegLbl = new JLabel();
					InputPane.add(LonDegLbl);
					LonDegLbl.setText("도(D)");
					LonDegLbl.setBounds(151, 119, 40, 15);
				}
				{
					LonMinLbl = new JLabel();
					InputPane.add(LonMinLbl);
					LonMinLbl.setText("분(M)");
					LonMinLbl.setBounds(192, 119, 40, 15);
				}
				{
					LonSecLbl = new JLabel();
					InputPane.add(LonSecLbl);
					LonSecLbl.setText("초(S)");
					LonSecLbl.setBounds(237, 119, 40, 15);
				}
				{
					LonDegTxt = new JTextField();
					InputPane.add(LonDegTxt);
					LonDegTxt.setBounds(151, 140, 36, 22);
					LonDegTxt.setEditable(false);
					LonDegTxt.addFocusListener(CoordsFieldListen);
				}
				{
					LonMinTxt = new JTextField();
					InputPane.add(LonMinTxt);
					LonMinTxt.setBounds(191, 140, 39, 22);
					LonMinTxt.setSize(36, 22);
					LonMinTxt.setEditable(false);
					LonMinTxt.addFocusListener(CoordsFieldListen);
				}
				{
					LonSecTxt = new JTextField();
					InputPane.add(LonSecTxt);
					LonSecTxt.setBounds(234, 140, 39, 22);
					LonSecTxt.setSize(36, 22);
					LonSecTxt.setEditable(false);
					LonSecTxt.addFocusListener(CoordsFieldListen);
				}
				{
					XCoordIn = new JLabel();
					InputPane.add(XCoordIn);
					XCoordIn.setText("X 좌표");
					XCoordIn.setBounds(17, 168, 41, 15);
				}
				{
					XCoordInTxt = new JTextField();
					InputPane.add(XCoordInTxt);
					XCoordInTxt.setBounds(17, 189, 116, 20);
					XCoordInTxt.setEditable(false);
					XCoordInTxt.addFocusListener(CoordsFieldListen);
					
				}
				{
					YCoordIn = new JLabel();
					InputPane.add(YCoordIn);
					YCoordIn.setText("Y 좌표");
					YCoordIn.setBounds(151, 168, 36, 15);
				}
				{
					YCoordInTxt = new JTextField();
					InputPane.add(YCoordInTxt);
					YCoordInTxt.setBounds(151, 188, 10, 22);
					YCoordInTxt.setSize(116, 20);
					YCoordInTxt.setEditable(false);
					YCoordInTxt.addFocusListener(CoordsFieldListen);
				}
			}
			{
				ConvertBtn = new JButton();
				getContentPane().add(ConvertBtn);
				ConvertBtn.setText("변환>>");
				ConvertBtn.setActionCommand("Convert");
				ConvertBtn.addActionListener(ConvBtnListen);
				ConvertBtn.setBounds(313, 41, 100, 27);
			}
			{
				FileConvBtn = new JButton();
				getContentPane().add(FileConvBtn);
				FileConvBtn.setText("파일변환>>");
				FileConvBtn.setActionCommand("ConvertFile");
				FileConvBtn.addActionListener(ConvBtnListen);
				FileConvBtn.setBounds(312, 85, 101, 27);
			}
			{
				ExtBtn = new JButton();
				getContentPane().add(ExtBtn);
				ExtBtn.setText("종료");
				ExtBtn.setActionCommand("Exit");
				ExtBtn.addActionListener(ConvBtnListen);
				//ExtBtn.setBounds(312, 130, 101, 27);
				ExtBtn.setBounds(312, 174, 101, 27);
			}
			{
				CoordPaneOut = new JPanel();
				getContentPane().add(CoordPaneOut);
				CoordPaneOut.setBounds(424, 17, 280, 220);
				CoordPaneOut.setBorder(BorderFactory.createTitledBorder(null, "경위도 혹은 투영좌표 출력", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION));
				CoordPaneOut.setLayout(null);
				{
					ComboBoxModel jComboBox1Model = 
						new DefaultComboBoxModel(
								new String[] { "Bessel 1841", "WGS84", "GRS80" });
					EllpsCombo2 = new JComboBox();
					CoordPaneOut.add(EllpsCombo2);
					EllpsCombo2.setModel(jComboBox1Model);
					EllpsCombo2.setBounds(17, 42, 98, 22);
					EllpsCombo2.setSize(115, 22);
					EllpsCombo2.setOpaque(false);
					EllpsCombo2.addActionListener(ConvListen);
				}
				{
					EllpsLbl = new JLabel();
					CoordPaneOut.add(EllpsLbl);
					EllpsLbl.setText("타원체");
					EllpsLbl.setBounds(18, 23, 54, 15);
				}
				{
					PrjLbl2 = new JLabel();
					CoordPaneOut.add(PrjLbl2);
					PrjLbl2.setText("좌표계");
					PrjLbl2.setBounds(153, 23, 64, 15);
				}
				{
					ComboBoxModel jComboBox2Model = 
						new DefaultComboBoxModel(
								new String[] { "Geographic", 
										"TM Korea West", "TM Korea Middle", 
										"TM Korea East", "KATEC", "UTM 52N", "UTM 51N" });
					ProjCombo2 = new JComboBox();
					CoordPaneOut.add(ProjCombo2);
					ProjCombo2.setModel(jComboBox2Model);
					ProjCombo2.setBounds(153, 42, 78, 22);
					ProjCombo2.setSize(115, 22);
					ProjCombo2.addActionListener(ConvListen);
				}
				{
					LatDegLbl3 = new JLabel();
					CoordPaneOut.add(LatDegLbl3);
					LatDegLbl3.setText("경도(Degree)");
					LatDegLbl3.setBounds(17, 73, 85, 15);
				}
				{
					LonDegLbl2 = new JLabel();
					CoordPaneOut.add(LonDegLbl2);
					LonDegLbl2.setText("위도(Degree)");
					LonDegLbl2.setBounds(153, 72, 91, 15);
				}
				{
					LonTxt2 = new JTextField();
					CoordPaneOut.add(LonTxt2);
					LonTxt2.setBounds(153, 93, 67, 22);
					LonTxt2.setSize(115, 22);
					LonTxt2.setEditable(false);
				}
				{
					jLabel5 = new JLabel();
					CoordPaneOut.add(jLabel5);
					jLabel5.setText("도(D)");
					jLabel5.setBounds(17, 125, 40, 15);
				}
				{
					jLabel6 = new JLabel();
					CoordPaneOut.add(jLabel6);
					jLabel6.setText("분(M)");
					jLabel6.setBounds(57, 125, 40, 15);
				}
				{
					jLabel7 = new JLabel();
					CoordPaneOut.add(jLabel7);
					jLabel7.setText("초(S)");
					jLabel7.setBounds(102, 125, 40, 15);
				}
				{
					jLabel8 = new JLabel();
					CoordPaneOut.add(jLabel8);
					jLabel8.setText("도(D)");
					jLabel8.setBounds(154, 125, 40, 15);
				}
				{
					jLabel9 = new JLabel();
					CoordPaneOut.add(jLabel9);
					jLabel9.setText("분(M)");
					jLabel9.setBounds(195, 125, 40, 15);
				}
				{
					jLabel10 = new JLabel();
					CoordPaneOut.add(jLabel10);
					jLabel10.setText("초(S)");
					jLabel10.setBounds(238, 125, 36, 15);
				}
				{
					LatDegTxt2 = new JTextField();
					CoordPaneOut.add(LatDegTxt2);
					LatDegTxt2.setBounds(17, 146, 67, 22);
					LatDegTxt2.setSize(36, 22);
					LatDegTxt2.setEditable(false);
				}
				{
					LatMinTxt2 = new JTextField();
					CoordPaneOut.add(LatMinTxt2);
					LatMinTxt2.setBounds(59, 146, 67, 22);
					LatMinTxt2.setSize(36, 22);
					LatMinTxt2.setEditable(false);
				}
				{
					LatSecTxt2 = new JTextField();
					CoordPaneOut.add(LatSecTxt2);
					LatSecTxt2.setBounds(101, 146, 67, 22);
					LatSecTxt2.setSize(36, 22);
					LatSecTxt2.setEditable(false);
				}
				{
					LonDegTxt2 = new JTextField();
					CoordPaneOut.add(LonDegTxt2);
					LonDegTxt2.setBounds(154, 146, 67, 22);
					LonDegTxt2.setSize(36, 22);
					LonDegTxt2.setEditable(false);
				}
				{
					LonMinTxt2 = new JTextField();
					CoordPaneOut.add(LonMinTxt2);
					LonMinTxt2.setBounds(195, 146, 67, 22);
					LonMinTxt2.setSize(36, 22);
					LonMinTxt2.setEditable(false);
				}
				{
					LonSecTxt2 = new JTextField();
					CoordPaneOut.add(LonSecTxt2);
					LonSecTxt2.setBounds(238, 146, 67, 22);
					LonSecTxt2.setSize(36, 22);
					LonSecTxt2.setEditable(false);
				}
				{
					LatTxt2 = new JTextField();
					CoordPaneOut.add(LatTxt2);
					LatTxt2.setBounds(17, 93, 10, 22);
					LatTxt2.setSize(115, 22);
					LatTxt2.setEditable(false);
				}
				{
					XCoordOut = new JLabel();
					CoordPaneOut.add(XCoordOut);
					XCoordOut.setText("X 좌표");
					XCoordOut.setBounds(19, 174, 40, 15);
				}
				{
					XCoordOutTxt = new JTextField();
					CoordPaneOut.add(XCoordOutTxt);
					XCoordOutTxt.setBounds(19, 192, 10, 22);
					XCoordOutTxt.setSize(116, 20);
					XCoordOutTxt.setEditable(false);
				}
				{
					YCoordOutLbl = new JLabel();
					CoordPaneOut.add(YCoordOutLbl);
					YCoordOutLbl.setText("Y 좌표");
					YCoordOutLbl.setBounds(155, 174, 36, 15);
				}
				{
					YCoordOutTxt = new JTextField();
					CoordPaneOut.add(YCoordOutTxt);
					YCoordOutTxt.setBounds(155, 192, 116, 20);
					YCoordOutTxt.setEditable(false);
				}
				{
					ClearBtn = new JButton();
					getContentPane().add(ClearBtn);
					ClearBtn.setText("지우기");
					ClearBtn.setBounds(312, 130, 101, 27);
					ClearBtn.setSize(101, 27);
					ClearBtn.setActionCommand("ClearAll");
					ClearBtn.addActionListener(ConvBtnListen);
				}
			}
			this.setSize(719, 283);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private final class MapConvComboListener implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JComboBox jcmbType = (JComboBox) e.getSource();
			
			String cmbType = (String) jcmbType.getSelectedItem();
			//System.out.println(cmbType);
			
			if (jcmbType == EllpsCombo2) 
			{
				setEllpsCombo(cmbType,COMBO_DEST_TYPE);
			}
			else if(jcmbType == ProjCombo2)
			{
				setInProjCombo(cmbType,COMBO_DEST_TYPE);
			}
			else if(jcmbType == EllipsTypeCombo)
			{
				setEllpsCombo(cmbType,COMBO_SRC_TYPE);
			}
			else if(jcmbType == ProjectionCombo)
			{
				setInProjCombo(cmbType,COMBO_SRC_TYPE);
			}
			
		}
		
	}
	
	/*
	private GeoEllips m_eSrcEllips;
	private GeoEllips m_eDstEllips;
	
		kBessel1984(0),
		kWgs84(1);
		"Bessel 1841", "WGS84"
	*/
	private void setEllpsCombo(String cmbType, int type)
	{
		String cmbtype = cmbType;
		
		if(cmbtype.equals("Bessel 1841"))
		{
			if(type == COMBO_DEST_TYPE)
				m_eDstEllips = GeoEllips.kBessel1984;
			else if(type == COMBO_SRC_TYPE)
				m_eSrcEllips = GeoEllips.kBessel1984;
			else
				System.out.println("[setEllpsCombo] ERROR!!!");
		}
		else if(cmbtype.equals("WGS84"))
		{
			if(type == COMBO_DEST_TYPE)
				m_eDstEllips = GeoEllips.kWgs84;
			else if(type == COMBO_SRC_TYPE)
				m_eSrcEllips = GeoEllips.kWgs84;
			else
				System.out.println("[setEllpsCombo] ERROR!!!");
		}
		else if(cmbtype.equals("GRS80"))
		{
			if(type == COMBO_DEST_TYPE)
				m_eDstEllips = GeoEllips.kGRS80;
			else if(type == COMBO_SRC_TYPE)
				m_eSrcEllips = GeoEllips.kGRS80;
			else
				System.out.println("[setEllpsCombo] ERROR!!!");
		}
		else
		{
			System.out.println("[setEllpsCombo] ERROR!!!");
		}
	}
	
	/*
	private GeoSystem m_eSrcSystem;
	private GeoSystem m_eDstSystem;
	
		kGeographic(0),
		kTmWest(1),
		kTmMid(2),
		kTmEast(3), 
		kKatec(4),
		kUtm52(5),
		kUtm51(6);
		m_eSrcSystem = GeoSystem.kGeographic;
		m_eSrcSystem = GeoSystem.kTmWest;
		m_eSrcSystem = GeoSystem.kTmMid;
		m_eSrcSystem = GeoSystem.kTmEast;
		m_eSrcSystem = GeoSystem.kKatec;
		m_eSrcSystem = GeoSystem.kUtm52;
		m_eSrcSystem = GeoSystem.kUtm51;

		
		"Geographic", 
		"TM Korea Middle", "TM Korea West", 
		"TM Korea East", "KATEC", "UTM 52N", "UTM 51N"
		
	*/
	private void setInProjCombo(String cmbtype, int type)
	{
		//System.out.println("################### " + cmbtype + ":" + type);
		if(cmbtype.startsWith("Geographic"))
		{
			//System.out.println("1");
			if(!LatTxt.isEditable()) LatTxt.setEditable(true);
			if(!LonTxt.isEditable()) LonTxt.setEditable(true);

			if(!LatDegTxt.isEditable()) LatDegTxt.setEditable(true);
			if(!LatMinTxt.isEditable()) LatMinTxt.setEditable(true);
			if(!LatSecTxt.isEditable()) LatSecTxt.setEditable(true);

			if(!LonDegTxt.isEditable()) LonDegTxt.setEditable(true);
			if(!LonMinTxt.isEditable()) LonMinTxt.setEditable(true);
			if(!LonSecTxt.isEditable()) LonSecTxt.setEditable(true);
			
			if(XCoordInTxt.isEditable()) XCoordInTxt.setEditable(false);
			if(YCoordInTxt.isEditable()) YCoordInTxt.setEditable(false);
			
			if(type == COMBO_DEST_TYPE)
				m_eDstSystem = GeoSystem.kGeographic;
			else
			{
				m_eSrcSystem = GeoSystem.kGeographic;
				
			}
			
		}
		else if(cmbtype.startsWith("TM Korea Middle"))
		{
			//System.out.println("2");
			
			if(type == COMBO_DEST_TYPE)
				m_eDstSystem = GeoSystem.kTmMid;
			else
			{
				m_eSrcSystem = GeoSystem.kTmMid;

				if(LatTxt.isEditable()) LatTxt.setEditable(false);
				if(LonTxt.isEditable()) LonTxt.setEditable(false);
	
				if(LatDegTxt.isEditable()) LatDegTxt.setEditable(false);
				if(LatMinTxt.isEditable()) LatMinTxt.setEditable(false);
				if(LatSecTxt.isEditable()) LatSecTxt.setEditable(false);
	
				if(LonDegTxt.isEditable()) LonDegTxt.setEditable(false);
				if(LonMinTxt.isEditable()) LonMinTxt.setEditable(false);
				if(LonSecTxt.isEditable()) LonSecTxt.setEditable(false);
				
				if(!XCoordInTxt.isEditable()) XCoordInTxt.setEditable(true);
				if(!YCoordInTxt.isEditable()) YCoordInTxt.setEditable(true);
			}
		
		}
		else if(cmbtype.startsWith("TM Korea West"))
		{
			//System.out.println("3");
			
			
			if(type == COMBO_DEST_TYPE)
				m_eDstSystem = GeoSystem.kTmWest;
			else
			{
				m_eSrcSystem = GeoSystem.kTmWest;
				if(LatTxt.isEditable()) LatTxt.setEditable(false);
				if(LonTxt.isEditable()) LonTxt.setEditable(false);
	
				if(LatDegTxt.isEditable()) LatDegTxt.setEditable(false);
				if(LatMinTxt.isEditable()) LatMinTxt.setEditable(false);
				if(LatSecTxt.isEditable()) LatSecTxt.setEditable(false);
	
				if(LonDegTxt.isEditable()) LonDegTxt.setEditable(false);
				if(LonMinTxt.isEditable()) LonMinTxt.setEditable(false);
				if(LonSecTxt.isEditable()) LonSecTxt.setEditable(false);
				
				if(!XCoordInTxt.isEditable()) XCoordInTxt.setEditable(true);
				if(!YCoordInTxt.isEditable()) YCoordInTxt.setEditable(true);
			}
		
		}
		else if(cmbtype.startsWith("TM Korea East"))
		{
			//System.out.println("4");
			
			if(type == COMBO_DEST_TYPE)
				m_eDstSystem = GeoSystem.kTmEast;
			else
			{
				m_eSrcSystem = GeoSystem.kTmEast;
				if(LatTxt.isEditable()) LatTxt.setEditable(false);
				if(LonTxt.isEditable()) LonTxt.setEditable(false);
	
				if(LatDegTxt.isEditable()) LatDegTxt.setEditable(false);
				if(LatMinTxt.isEditable()) LatMinTxt.setEditable(false);
				if(LatSecTxt.isEditable()) LatSecTxt.setEditable(false);
	
				if(LonDegTxt.isEditable()) LonDegTxt.setEditable(false);
				if(LonMinTxt.isEditable()) LonMinTxt.setEditable(false);
				if(LonSecTxt.isEditable()) LonSecTxt.setEditable(false);
				
				if(!XCoordInTxt.isEditable()) XCoordInTxt.setEditable(true);
				if(!YCoordInTxt.isEditable()) YCoordInTxt.setEditable(true);
			}
		
		}
		else if(cmbtype.startsWith("KATEC"))
		{
			//System.out.println("5");
			
			if(type == COMBO_DEST_TYPE)
				m_eDstSystem = GeoSystem.kKatec;
			else
			{
				m_eSrcSystem = GeoSystem.kKatec;
				if(LatTxt.isEditable()) LatTxt.setEditable(false);
				if(LonTxt.isEditable()) LonTxt.setEditable(false);
	
				if(LatDegTxt.isEditable()) LatDegTxt.setEditable(false);
				if(LatMinTxt.isEditable()) LatMinTxt.setEditable(false);
				if(LatSecTxt.isEditable()) LatSecTxt.setEditable(false);
	
				if(LonDegTxt.isEditable()) LonDegTxt.setEditable(false);
				if(LonMinTxt.isEditable()) LonMinTxt.setEditable(false);
				if(LonSecTxt.isEditable()) LonSecTxt.setEditable(false);
				
				if(!XCoordInTxt.isEditable()) XCoordInTxt.setEditable(true);
				if(!YCoordInTxt.isEditable()) YCoordInTxt.setEditable(true);
			}
		}
		else if(cmbtype.startsWith("UTM 52N"))
		{
			//System.out.println("6");
			
			if(type == COMBO_DEST_TYPE)
				m_eDstSystem = GeoSystem.kUtm52;
			else
			{
				m_eSrcSystem = GeoSystem.kUtm52;
				
				if(LatTxt.isEditable()) LatTxt.setEditable(false);
				if(LonTxt.isEditable()) LonTxt.setEditable(false);
	
				if(LatDegTxt.isEditable()) LatDegTxt.setEditable(false);
				if(LatMinTxt.isEditable()) LatMinTxt.setEditable(false);
				if(LatSecTxt.isEditable()) LatSecTxt.setEditable(false);
	
				if(LonDegTxt.isEditable()) LonDegTxt.setEditable(false);
				if(LonMinTxt.isEditable()) LonMinTxt.setEditable(false);
				if(LonSecTxt.isEditable()) LonSecTxt.setEditable(false);
				
				if(!XCoordInTxt.isEditable()) XCoordInTxt.setEditable(true);
				if(!YCoordInTxt.isEditable()) YCoordInTxt.setEditable(true);
			}
		}
		else if(cmbtype.equals("UTM 51N"))
		{
			//System.out.println("7");
			
			
			if(type == COMBO_DEST_TYPE)
				m_eDstSystem = GeoSystem.kUtm51;
			else
			{
				m_eSrcSystem = GeoSystem.kUtm51;
				if(LatTxt.isEditable()) LatTxt.setEditable(false);
				if(LonTxt.isEditable()) LonTxt.setEditable(false);
	
				if(LatDegTxt.isEditable()) LatDegTxt.setEditable(false);
				if(LatMinTxt.isEditable()) LatMinTxt.setEditable(false);
				if(LatSecTxt.isEditable()) LatSecTxt.setEditable(false);
	
				if(LonDegTxt.isEditable()) LonDegTxt.setEditable(false);
				if(LonMinTxt.isEditable()) LonMinTxt.setEditable(false);
				if(LonSecTxt.isEditable()) LonSecTxt.setEditable(false);
				
				if(!XCoordInTxt.isEditable()) XCoordInTxt.setEditable(true);
				if(!YCoordInTxt.isEditable()) YCoordInTxt.setEditable(true);
			}
			
		}
		else
		{
			System.out.println("[setInProjCombo] ERROR!!!");
			System.out.println("[setProjcombo] ERROR!!!");
		}
	}
	
	
	private void setOutProjCombo(String cmbtype)
	{
		//System.out.println("################### " + cmbtype + ":" + type);
		if(cmbtype.startsWith("Geographic"))
		{
			m_eDstSystem = GeoSystem.kGeographic;
			
			if(!LatTxt2.isEditable()) LatTxt2.setEditable(true);
			if(!LonTxt2.isEditable()) LonTxt2.setEditable(true);
			
			if(!LatDegTxt2.isEditable()) LatDegTxt2.setEditable(true);
			if(!LatMinTxt2.isEditable()) LatMinTxt2.setEditable(true);
			if(!LatSecTxt2.isEditable()) LatSecTxt2.setEditable(true);
			
			if(!LonDegTxt2.isEditable()) LonDegTxt2.setEditable(true);
			if(!LonMinTxt2.isEditable()) LonMinTxt2.setEditable(true);
			if(!LonSecTxt2.isEditable()) LonSecTxt2.setEditable(true);
			
			if(YCoordOutTxt.isEditable()) YCoordOutTxt.setEditable(false);
			if(XCoordOutTxt.isEditable()) XCoordOutTxt.setEditable(false);
			
			LatTxt2.setText(m_coordsinfo.getsOutLongitude());
			LonTxt2.setText(m_coordsinfo.getsOutLatitude());
			
			LatDegTxt2.setText(m_coordsinfo.getsOutXDegree());
			LatMinTxt2.setText(m_coordsinfo.getsOutXMinute());
			LatSecTxt2.setText(m_coordsinfo.getsOutXSecond());
			
			LonDegTxt2.setText(m_coordsinfo.getsOutYDegree());
			LonMinTxt2.setText(m_coordsinfo.getsOutYMinute());
			LonSecTxt2.setText(m_coordsinfo.getsOutYSecond());
			
			YCoordOutTxt.setText(m_coordsinfo.getsOutY());
			XCoordOutTxt.setText(m_coordsinfo.getsOutX());
			
			
			
			LatDegTxt2.setToolTipText(m_coordsinfo.getsOutXDegree());
			LatMinTxt2.setToolTipText(m_coordsinfo.getsOutXMinute());
			LatSecTxt2.setToolTipText(m_coordsinfo.getsOutXSecond());
			
			LonDegTxt2.setToolTipText(m_coordsinfo.getsOutYDegree());
			LonMinTxt2.setToolTipText(m_coordsinfo.getsOutYMinute());
			LonSecTxt2.setToolTipText(m_coordsinfo.getsOutYSecond());
			
			YCoordOutTxt.setToolTipText(m_coordsinfo.getsOutY());
			XCoordOutTxt.setToolTipText(m_coordsinfo.getsOutX());
			LatTxt2.setToolTipText(m_coordsinfo.getsOutLongitude());
			LonTxt2.setToolTipText(m_coordsinfo.getsOutLatitude());
		}
		else if(cmbtype.startsWith("TM Korea Middle"))
		{
			m_eDstSystem = GeoSystem.kTmMid;
			
			if(LatTxt2.isEditable()) LatTxt2.setEditable(false);
			if(LonTxt2.isEditable()) LonTxt2.setEditable(false);
			
			if(LatDegTxt2.isEditable()) LatDegTxt2.setEditable(false);
			if(LatMinTxt2.isEditable()) LatMinTxt2.setEditable(false);
			if(LatSecTxt2.isEditable()) LatSecTxt2.setEditable(false);
			
			if(LonDegTxt2.isEditable()) LonDegTxt2.setEditable(false);
			if(LonMinTxt2.isEditable()) LonMinTxt2.setEditable(false);
			if(LonSecTxt2.isEditable()) LonSecTxt2.setEditable(false);
			
			if(!YCoordOutTxt.isEditable()) YCoordOutTxt.setEditable(true);
			if(!XCoordOutTxt.isEditable()) XCoordOutTxt.setEditable(true);

			YCoordOutTxt.setText(m_coordsinfo.getsOutY());
			XCoordOutTxt.setText(m_coordsinfo.getsOutX());
						
			YCoordOutTxt.setToolTipText(m_coordsinfo.getsOutY());
			XCoordOutTxt.setToolTipText(m_coordsinfo.getsOutX());
		}
		else if(cmbtype.startsWith("TM Korea West"))
		{
				m_eDstSystem = GeoSystem.kTmWest;
				
				if(LatTxt2.isEditable()) LatTxt2.setEditable(false);
			if(LonTxt2.isEditable()) LonTxt2.setEditable(false);
			
			if(LatDegTxt2.isEditable()) LatDegTxt2.setEditable(false);
			if(LatMinTxt2.isEditable()) LatMinTxt2.setEditable(false);
			if(LatSecTxt2.isEditable()) LatSecTxt2.setEditable(false);
			
			if(LonDegTxt2.isEditable()) LonDegTxt2.setEditable(false);
			if(LonMinTxt2.isEditable()) LonMinTxt2.setEditable(false);
			if(LonSecTxt2.isEditable()) LonSecTxt2.setEditable(false);
			
			if(!YCoordOutTxt.isEditable()) YCoordOutTxt.setEditable(true);
			if(!XCoordOutTxt.isEditable()) XCoordOutTxt.setEditable(true);
	
			YCoordOutTxt.setText(m_coordsinfo.getsOutY());
			XCoordOutTxt.setText(m_coordsinfo.getsOutX());
					
			YCoordOutTxt.setToolTipText(m_coordsinfo.getsOutY());
			XCoordOutTxt.setToolTipText(m_coordsinfo.getsOutX());
		}
		else if(cmbtype.startsWith("TM Korea East"))
		{
				m_eDstSystem = GeoSystem.kTmEast;
				
				if(LatTxt2.isEditable()) LatTxt2.setEditable(false);
			if(LonTxt2.isEditable()) LonTxt2.setEditable(false);
			
			if(LatDegTxt2.isEditable()) LatDegTxt2.setEditable(false);
			if(LatMinTxt2.isEditable()) LatMinTxt2.setEditable(false);
			if(LatSecTxt2.isEditable()) LatSecTxt2.setEditable(false);
			
			if(LonDegTxt2.isEditable()) LonDegTxt2.setEditable(false);
			if(LonMinTxt2.isEditable()) LonMinTxt2.setEditable(false);
			if(LonSecTxt2.isEditable()) LonSecTxt2.setEditable(false);
			
			if(!YCoordOutTxt.isEditable()) YCoordOutTxt.setEditable(true);
			if(!XCoordOutTxt.isEditable()) XCoordOutTxt.setEditable(true);
			
			YCoordOutTxt.setText(m_coordsinfo.getsOutY());
			XCoordOutTxt.setText(m_coordsinfo.getsOutX());
						
			YCoordOutTxt.setToolTipText(m_coordsinfo.getsOutY());
			XCoordOutTxt.setToolTipText(m_coordsinfo.getsOutX());
		}
		else if(cmbtype.startsWith("KATEC"))
		{
				m_eDstSystem = GeoSystem.kKatec;
				
				if(LatTxt2.isEditable()) LatTxt2.setEditable(false);
			if(LonTxt2.isEditable()) LonTxt2.setEditable(false);
			
			if(LatDegTxt2.isEditable()) LatDegTxt2.setEditable(false);
			if(LatMinTxt2.isEditable()) LatMinTxt2.setEditable(false);
			if(LatSecTxt2.isEditable()) LatSecTxt2.setEditable(false);
			
			if(LonDegTxt2.isEditable()) LonDegTxt2.setEditable(false);
			if(LonMinTxt2.isEditable()) LonMinTxt2.setEditable(false);
			if(LonSecTxt2.isEditable()) LonSecTxt2.setEditable(false);
			
			if(!YCoordOutTxt.isEditable()) YCoordOutTxt.setEditable(true);
			if(!XCoordOutTxt.isEditable()) XCoordOutTxt.setEditable(true);
			
			YCoordOutTxt.setText(m_coordsinfo.getsOutY());
			XCoordOutTxt.setText(m_coordsinfo.getsOutX());
					
			YCoordOutTxt.setToolTipText(m_coordsinfo.getsOutY());
			XCoordOutTxt.setToolTipText(m_coordsinfo.getsOutX());
		}
		else if(cmbtype.startsWith("UTM 52N"))
		{
				m_eDstSystem = GeoSystem.kUtm52;
				
				if(LatTxt2.isEditable()) LatTxt2.setEditable(false);
			if(LonTxt2.isEditable()) LonTxt2.setEditable(false);
			
			if(LatDegTxt2.isEditable()) LatDegTxt2.setEditable(false);
			if(LatMinTxt2.isEditable()) LatMinTxt2.setEditable(false);
			if(LatSecTxt2.isEditable()) LatSecTxt2.setEditable(false);
			
			if(LonDegTxt2.isEditable()) LonDegTxt2.setEditable(false);
			if(LonMinTxt2.isEditable()) LonMinTxt2.setEditable(false);
			if(LonSecTxt2.isEditable()) LonSecTxt2.setEditable(false);
			
			if(!YCoordOutTxt.isEditable()) YCoordOutTxt.setEditable(true);
			if(!XCoordOutTxt.isEditable()) XCoordOutTxt.setEditable(true);
			
			YCoordOutTxt.setText(m_coordsinfo.getsOutY());
			XCoordOutTxt.setText(m_coordsinfo.getsOutX());
						
			YCoordOutTxt.setToolTipText(m_coordsinfo.getsOutY());
			XCoordOutTxt.setToolTipText(m_coordsinfo.getsOutX());
		}
		else if(cmbtype.equals("UTM 51N"))
		{
				m_eDstSystem = GeoSystem.kUtm51;
				
				if(LatTxt2.isEditable()) LatTxt2.setEditable(false);
			if(LonTxt2.isEditable()) LonTxt2.setEditable(false);
			
			if(LatDegTxt2.isEditable()) LatDegTxt2.setEditable(false);
			if(LatMinTxt2.isEditable()) LatMinTxt2.setEditable(false);
			if(LatSecTxt2.isEditable()) LatSecTxt2.setEditable(false);
			
			if(LonDegTxt2.isEditable()) LonDegTxt2.setEditable(false);
			if(LonMinTxt2.isEditable()) LonMinTxt2.setEditable(false);
			if(LonSecTxt2.isEditable()) LonSecTxt2.setEditable(false);
			
			if(!YCoordOutTxt.isEditable()) YCoordOutTxt.setEditable(true);
			if(!XCoordOutTxt.isEditable()) XCoordOutTxt.setEditable(true);
	
			YCoordOutTxt.setText(m_coordsinfo.getsOutY());
			XCoordOutTxt.setText(m_coordsinfo.getsOutX());
			
			YCoordOutTxt.setToolTipText(m_coordsinfo.getsOutY());
			XCoordOutTxt.setToolTipText(m_coordsinfo.getsOutX());
		}
		else
		{
			System.out.println("[setOutProjCombo] ERROR!!!");
		}
	}
	
	private final class MapConvButtonListener implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			
			System.out.println("Command is " + command);
			
			if(command.equals("Convert"))
			{
				try
				{
					String destPrjName = (String)ProjCombo2.getSelectedItem();
					String srcPrjName = (String)ProjectionCombo.getSelectedItem();
					String destEllpName = (String)EllpsCombo2.getSelectedItem();
					String srcEllpName = (String)EllipsTypeCombo.getSelectedItem();
					
					
					
					setInProjCombo(destPrjName, COMBO_DEST_TYPE);
					setInProjCombo(srcPrjName, COMBO_SRC_TYPE);
					setEllpsCombo(destEllpName, COMBO_DEST_TYPE);
					setEllpsCombo(srcEllpName, COMBO_SRC_TYPE);
					
					mapConverter.calcCoordConv(m_eSrcEllips, m_eSrcSystem, m_eDstEllips, m_eDstSystem, m_coordsinfo);			
					setOutProjCombo((String)ProjCombo2.getSelectedItem());
					
				}
				catch(GeoException ge)
				{
					System.out.println("[MapConvButtonListener::calcCoordConv]ERROR"+ge);
				}
				
			}
			else if(e.getActionCommand().equals("ConvertFile"))
			{
				JFileChooser fileopen = new JFileChooser();
				fileopen.setCurrentDirectory(CURRENT_DIR);

				FileFilter filter = new FileNameExtensionFilter("TEXT  Files (*.txt)|*.txt|ASCII Files ", "asc", "txt");
				fileopen.addChoosableFileFilter(filter);

				int ret = fileopen.showDialog(null, "Open file");

				if (ret == JFileChooser.APPROVE_OPTION) {
					String path = null;
					File file = fileopen.getSelectedFile();
					path = file.getAbsolutePath();
		      
					setInProjCombo((String)ProjCombo2.getSelectedItem(), COMBO_DEST_TYPE);
					setInProjCombo((String)ProjectionCombo.getSelectedItem(), COMBO_SRC_TYPE);
					setEllpsCombo((String)EllpsCombo2.getSelectedItem(), COMBO_DEST_TYPE);
					setEllpsCombo((String)EllipsTypeCombo.getSelectedItem(), COMBO_SRC_TYPE);
			
					try
					{
						mapConverter.calcFileCoordConv(path,m_eSrcEllips, m_eSrcSystem, m_eDstEllips, m_eDstSystem);
					} 
					catch(GeoException ge)
					{
						System.out.println("GeoException");
					}
					catch(FileNotFoundException fe)
					{
						System.out.println("FileNotFoundException");
					}
					catch(IOException ie)
					{
						System.out.println("IOException");
					}
				}
			}
			else if(e.getActionCommand().equals("ClearAll"))
			{
				LonMinTxt2.setText("");
				LonDegTxt2.setText("");
				LatSecTxt2.setText("");
				LatMinTxt2.setText("");
				LatDegTxt2.setText("");
				LonTxt2.setText("");
				LonSecTxt.setText("");
				LonMinTxt.setText("");
				LonDegTxt.setText("");
				LatSecTxt.setText("");
				LatMinTxt.setText("");
				LonSecTxt2.setText("");
				YCoordOutTxt.setText("");
				XCoordOutTxt.setText("");
				YCoordInTxt.setText("");
				LatTxt2.setText("");
				LonTxt.setText("");
				LatTxt.setText("");
				LatDegTxt.setText("");
				XCoordInTxt.setText("");
			}
			else  if(e.getActionCommand().equals("Exit"))
			{
				System.exit(0);
				
			}
			else return;
			
		}
		
	}
	
	
	class CoordsTxtChangeListen implements DocumentListener
	{
		public void changedUpdate(DocumentEvent e) 
    {
    	System.out.println(e);
		}
		
 		public void insertUpdate(DocumentEvent e) 
    {
    	System.out.println(e);
		}
		
 		public void removeUpdate(DocumentEvent e) 
 		{
    	System.out.println(e);
		}
		

	}
	
	public class CoordsChangeListen implements FocusListener
	{
		
		public void focusGained(FocusEvent e) 
    {
    	/*
    	//System.out.println(e);
    	JTextField txtItem = (JTextField)e.getComponent();
			
			
			if(txtItem == LatTxt)
			{
				System.out.println("[focusGained] Latitude");
			}
			else if(txtItem == LonTxt)
			{
				System.out.println("[focusGained] Longitude");
			}
			else if(txtItem == LatDegTxt)
			{
				System.out.println("[focusGained] Latitude of degree");
			}
			else if(txtItem == LatMinTxt)
			{
				System.out.println("[focusGained] Latitude of Minutes");
			}
			else if(txtItem == LatSecTxt)
			{
				System.out.println("[focusGained] Latitude of Seconds");
			}
			else if(txtItem == LonDegTxt)
			{
				System.out.println("[focusGained] Longitude of degree");
			}
			else if(txtItem == LonMinTxt)
			{
				System.out.println("[focusGained] Longitude of Minutes");
			}
			else if(txtItem == LonSecTxt)
			{
				System.out.println("[focusGained] Longitude of Seconds");
			}
			else if(txtItem == XCoordInTxt)
			{
				System.out.println("[focusGained] XCoordInTxt of Seconds");
			}
			else if(txtItem == YCoordInTxt)
			{
				System.out.println("[focusGained] YCoordInTxt of Seconds");
			}
			*/
    }
    
    
	 	public void focusLost(FocusEvent e) 
    {
    	JTextField txtItem = (JTextField)e.getComponent();
			
			try
			{
				if(txtItem == LatTxt)
				{
					//System.out.println("[focusLost] Latitude");
					OnKillfocusInLatitude();
				}
				else if(txtItem == LonTxt)
				{
					//System.out.println("[focusLost] Longitude");
					OnKillfocusInLongitude();
				}
				else if(txtItem == LatDegTxt)
				{
					//System.out.println("[focusLost] Latitude of degree");
					OnKillfocusInXDms();
				}
				else if(txtItem == LatMinTxt)
				{
					//System.out.println("[focusLost] Latitude of Minutes");
					OnKillfocusInXDms();
				}
				else if(txtItem == LatSecTxt)
				{
					//System.out.println("[focusLost] Latitude of Seconds");
					OnKillfocusInXDms();
				}
				else if(txtItem == LonDegTxt)
				{
					//System.out.println("[focusLost] Longitude of degree");
					OnKillfocusInYDms();
				}
				else if(txtItem == LonMinTxt)
				{
					//System.out.println("[focusLost] Longitude of Minutes");
					OnKillfocusInYDms();
				}
				else if(txtItem == LonSecTxt)
				{
					//System.out.println("[focusLost] Longitude of Seconds");
					OnKillfocusInYDms();
				}
				else if(txtItem == XCoordInTxt)
				{
					//System.out.println("[focusLost] XCoordInTxt of Seconds");
					m_coordsinfo.setsInX(XCoordInTxt.getText());
					
				}
				else if(txtItem == YCoordInTxt)
				{
					//System.out.println("[focusLost] YCoordInTxt of Seconds");
					m_coordsinfo.setsInY(YCoordInTxt.getText());
				}
			}
			catch(GeoException ge)
			{
				System.out.println(ge);
			}
	  }
	}
	
	
	/*
			LatTxt.setEditable(false);
			LonTxt.setEditable(false);

			LatDegTxt.setEditable(false);
			LatMinTxt.setEditable(false);
			LatSecTxt.setEditable(false);

			LonDegTxt.setEditable(false);
			LonMinTxt.setEditable(false);
			LonSecTxt.setEditable(false);
	*/
	void OnKillfocusInLongitude()  throws GeoException
	{
		double dInLon = 0;
		MutableInteger iDeg = new MutableInteger(), iMin = new MutableInteger();
		MutableDouble dSec = new MutableDouble();
		//System.out.println("OnKillfocusInLongitude" + LonTxt.getText());
		
		// Get Geographic Value
		String sInLongitude = null;
		
		if((LonTxt.getText().equals("")))	return;
		
		dInLon = Double.valueOf(LonTxt.getText()).doubleValue();
		sInLongitude = String.format("%.10f", dInLon);
		
		//System.out.println("OnKillfocusInLatitude" + sInLongitude);
		
		// Display Input Geographic Value
		LonTxt.setText(sInLongitude);
		
		m_coordsinfo.setsInLongitude(sInLongitude);
		
		mapConverter.D2Dms(new MutableDouble(dInLon), iDeg, iMin, dSec);
		
		LonDegTxt.setText(String.format("%d", iDeg.getInteger()));
		LonMinTxt.setText(String.format("%d", iMin.getInteger()));
		LonSecTxt.setText(String.format("%.4f", dSec.getDouble()));
	
	}
	
	void OnKillfocusInLatitude() throws GeoException
	{
		double dInLat;
		MutableInteger iDeg = new MutableInteger(), iMin = new MutableInteger();
		MutableDouble dSec = new MutableDouble();
	
		// Get Geographic Value
		String sInLatitude = null;
		if((sInLatitude=LatTxt.getText()).equals( ""))
			return;
			
		dInLat = Double.valueOf(sInLatitude).doubleValue();
		sInLatitude = String.format("%.10f", dInLat);
		
		//System.out.println("OnKillfocusInLatitude" + sInLatitude);
		
		// Display Input Geographic Value
		LatTxt.setText(sInLatitude);
		
		m_coordsinfo.setsInLatitude(sInLatitude);
		
		mapConverter.D2Dms(new MutableDouble(dInLat), iDeg, iMin, dSec);
		
		LatDegTxt.setText(String.format("%d", iDeg.getInteger()));
		LatMinTxt.setText(String.format("%d", iMin.getInteger()));
		LatSecTxt.setText(String.format("%.4f", dSec.getDouble()));
	}
	
	
	void OnKillfocusInXDms() 
	{
		double dInLon;
		String sInLongitude = null;
		//System.out.println("OnKillfocusInXDms");
		
		String sDeg = LatDegTxt.getText();
		String sMin = LatMinTxt.getText();
		String sSec = LatSecTxt.getText();
		
		if(sDeg.equals("")) sDeg = "0";
		if(sMin.equals("")) sMin = "0";
		if(sSec.equals("")) sSec = "0";
		
		int sInXDegree = Integer.valueOf(sDeg).intValue();
		int sInXMinute = Integer.valueOf(sMin).intValue();
		
		double sInXSecond = Double.valueOf(sSec).doubleValue();
		
		
		
		dInLon = (double)(sInXDegree) + (sInXMinute)/60.0 + (sInXSecond)/3600.0;
		sInLongitude = String.format("%.10f", dInLon);
		
		m_coordsinfo.setsInLongitude(sInLongitude);
		
		//System.out.println(sInXDegree+":"+sInXMinute +":" + sInXSecond + ":" + sInLongitude);
		
		LatTxt.setText(sInLongitude);
	}
	
	void OnKillfocusInYDms() 
	{
		double dInLat =0;
		String sInLatitude = null;
		//System.out.println("OnKillfocusInYDms");
		
		String sDeg = LonDegTxt.getText();
		String sMin = LonMinTxt.getText();
		String sSec = LonSecTxt.getText();
		
		
		
		if(sDeg.equals("")) sDeg = "0";
		if(sMin.equals("")) sMin = "0";
		if(sSec.equals("")) sSec = "0";
		
		int sInYDegree = Integer.valueOf(sDeg).intValue();
		int sInYMinute = Integer.valueOf(sMin).intValue();
		//System.out.println("OnKillfocusInYDms");
		
		double sInYSecond = Double.valueOf(sSec).doubleValue();
		
		dInLat = (double)(sInYDegree) + (sInYMinute)/60.0 + (sInYSecond)/3600.0;
		sInLatitude = String.format("%.10f", dInLat);
		
		m_coordsinfo.setsInLatitude(sInLatitude);
		
		
		LonTxt.setText(sInLatitude);
		//System.out.println(sInYDegree+":"+sInYMinute +":" + sInYSecond + ":" + sInLatitude);
	}
	
}
