/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team.ui.panel;

import com.team.logic.SanPhamCuaHang;
import com.team.logic.SanPhamKhachHang;
import com.team.ui.ActionClick;
import com.team.ui.GUI;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class PanelGioHang extends BasePanel {

    private JLabel lb_QuayLai, lb_tatCa, lb_tong;
    private JTextField tf_timKiem, tf_tongTien;
    private JButton btn_timKiem, btn_showAll, btn_thanhToan, btn_xoa;
    private ActionClick ack;
    static final String BT_timKiem = "Tìm kiếm";
    static final String BT_showAll = "Tất cả sản phẩm";
    static final String BT_thanhtoan = "Thanh toán";
    static final String BT_xoa = "Xóa";
    
    private JList<SanPhamCuaHang> listSanPhamKhachHang;
    private DefaultListModel<SanPhamCuaHang> model_sp;
    private DefaultListModel<SanPhamCuaHang> model_SpCanTim;
    private List<SanPhamCuaHang> gioHang;
    
    private boolean onFindSP = false;
    private long tongTien = 0;


    public ActionClick getAck() {
        return ack;
    }

    public void setAck(ActionClick ack) {
        this.ack = ack;
    }

    @Override
    public void initUI() {
        setLayout(null);
        setBackground(GUI.colerTheme);
        setVisible(true);

    }

    @Override
    public void addEvent() {

    }

    @Override
    public void addComp() {
        Font font1 = new Font("Tahoma", Font.BOLD, 20);
        Font font2 = new Font("Tahoma", Font.BOLD, 15);
        //quay lai
        Icon icon = new ImageIcon("D:\\Downloads\\ic_arrow_back_ios1.png", "comeback");
        lb_QuayLai = new JLabel("<html><u>Quay lại</u></html>", icon, JLabel.CENTER);
        lb_QuayLai.setLocation(20, 20);
        lb_QuayLai.setBackground(GUI.colerTheme);
        lb_QuayLai.setSize(100, 40);
        lb_QuayLai.setFont(font2);
        lb_QuayLai.setOpaque(true);
        lb_QuayLai.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ack.fromGioHang();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lb_QuayLai.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lb_QuayLai.setForeground(Color.BLACK);
            }
        });
        add(lb_QuayLai);
        // jtextfield tim kiem
        tf_timKiem = createTextField(150, 45, 320, font2, Color.BLACK);
        tf_timKiem.setSize(320, 40);
        add(tf_timKiem);
        // bnt tim kiem
        btn_timKiem = createButton("Tìm kiếm", tf_timKiem.getX() + 350, 45, font2, Color.BLACK, BT_timKiem);
        add(btn_timKiem);
        //btn filter 
        btn_showAll = createButton("Tất cả", tf_timKiem.getX() + 500, 45, font2, Color.BLACK, BT_showAll);
        add(btn_showAll);


        // jlist
        JPanel jPanel_main = new JPanel();
        jPanel_main.setBounds(0, 130, 795, 370);
        jPanel_main.setBackground(GUI.colerTheme);
        jPanel_main.setLayout(new BorderLayout());

        model_sp = new DefaultListModel<>();

        listSanPhamKhachHang = new JList<>(model_sp);
        listSanPhamKhachHang.setCellRenderer(new PanelSanPhamRender());
        jPanel_main.add(new JScrollPane(listSanPhamKhachHang), BorderLayout.CENTER);

        add(jPanel_main);

        btn_thanhToan = createButton("Thanh Toán", 120, 550, font2, Color.BLACK, BT_thanhtoan);
        add(btn_thanhToan);

        lb_tong = createLabel("Tổng:", btn_thanhToan.getX() + 200, 560, font2, Color.BLACK, GUI.colerTheme);
        add(lb_tong);
        tf_tongTien = createTextField(370, 550, 200, font2, Color.BLACK);
        tf_tongTien.setSize(200, 40);
        tf_tongTien.setEditable(false);
        add(tf_tongTien);
        btn_xoa = createButton("Xóa", tf_tongTien.getX() + 250, 550, font2, Color.BLACK, BT_xoa);
        add(btn_xoa);
        
        gioHang = new ArrayList<>();
    }

    @Override
    protected void handleClick(String name) {
        if (name.equals(BT_thanhtoan)) {
        	if(!onFindSP) {
                tf_tongTien.setText("");
                ack.updateGioHangOnDeleteAll();
                gioHang.removeAll(gioHang);
                model_sp.removeAllElements();
                listSanPhamKhachHang.setModel(model_sp);
                JOptionPane.showConfirmDialog(null, "Thanh toán thành công\nTổng tiền "+String.valueOf(tongTien), "Stores", JOptionPane.CLOSED_OPTION);
        	}else {
        		JOptionPane.showConfirmDialog(null, "Vui lòng chọn tất cả sản phẩm", "Error", JOptionPane.CLOSED_OPTION);
        	}
         
        }
        if (name.equals(BT_timKiem)) {
        	onFindSP = true;
            int check = 0;
            model_SpCanTim = new DefaultListModel<>();
            for (int i = 0; i < model_sp.size(); i++) {
                if (model_sp.get(i).getMaSP().equals(tf_timKiem.getText())) {
                    model_SpCanTim.addElement(new SanPhamCuaHang(model_sp.get(i).getMaSP(), model_sp.get(i).getTenSP(), model_sp.get(i).getPhanLoai(), model_sp.get(i).getThongTinChiTiet(), model_sp.get(i).getSoLuong(), model_sp.get(i).getGiaTien(), model_sp.get(i).getAnhMH()));
                    check += 1;
                }
            }
            listSanPhamKhachHang.setModel(model_SpCanTim);
            if (check == 0) {
                JOptionPane.showConfirmDialog(null, "Không có sản phẩm", "Error", JOptionPane.CLOSED_OPTION);
            }
        }
        if (name.equals(BT_xoa)) {
            this.xoaSanPham();
        }
        if (name.equals(BT_showAll)) {
        	onFindSP = false;
            ack.showAllGioHang();
            
        }

    }

    private void xoaSanPham() {
    	if(!onFindSP) {
    		if (!model_sp.isEmpty() && listSanPhamKhachHang.getSelectedIndex() >= 0) {
            	for (int i = 0; i < gioHang.size(); i++) {
    				if(gioHang.get(i).getMaSP().equals(listSanPhamKhachHang.getSelectedValue().getMaSP())) {
    					gioHang.remove(i);
    					ack.updateGioHangOnDelete(i);
    					break;
    				}
    			}
                model_sp.remove(listSanPhamKhachHang.getSelectedIndex());
                listSanPhamKhachHang.setModel(model_sp);
            }
    	}else {
    		if (!model_sp.isEmpty() && listSanPhamKhachHang.getSelectedIndex() >= 0) {
    			for (int i = 0; i < gioHang.size(); i++) {
    				if(gioHang.get(i).getMaSP().equals(listSanPhamKhachHang.getSelectedValue().getMaSP())) {
    					gioHang.remove(i);
    					model_sp.remove(i);
    					model_SpCanTim.remove(listSanPhamKhachHang.getSelectedIndex());
    					ack.updateGioHangOnDelete(i);
    					break;
    				}
    			}
    			listSanPhamKhachHang.setModel(model_SpCanTim);
    		}
    		
    	}
            
            
    }

    public void passGHToModel(List<SanPhamCuaHang> list) {
    	model_sp.clear();
    	if(list.size() > 0) {
    		model_sp.addAll(list);
    	}
    }
    
    public DefaultListModel<SanPhamCuaHang> getModel_sp() {
        return model_sp;
    }

    public void setModel_sp(DefaultListModel<SanPhamCuaHang> model_sp) {
        this.model_sp = model_sp;
    }

    public JList<SanPhamCuaHang> getListSanPhamKhachHang() {
        return listSanPhamKhachHang;
    }

    public void setListSanPhamKhachHang(JList<SanPhamCuaHang> listSanPhamKhachHang) {
        this.listSanPhamKhachHang = listSanPhamKhachHang;
    }
    public void setModelList(DefaultListModel<SanPhamCuaHang> model) {
        listSanPhamKhachHang.setModel(model);
    }

	public List<SanPhamCuaHang> getGioHang() {
		return gioHang;
	}

	public void setGioHang(List<SanPhamCuaHang> gioHang) {
		this.gioHang = gioHang;
	}

	public long getTongTien() {
		return tongTien;
	}

	public void setTongTien(long tongTien) {
		this.tongTien = tongTien;
	}
	
	public void setTongTienTF(long tien) {
		tf_tongTien.setText(String.valueOf(tien));
	}
	
	public void tinhTongTien() {
		tongTien = 0;
		for (int i = 0; i < model_sp.size(); i++) {
            tongTien += model_sp.get(i).getGiaTien();
        }
	}
}
