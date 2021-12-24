/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team.ui.panel;

import com.team.logic.KhachHang;
import com.team.logic.SanPhamCuaHang;
import com.team.logic.SanPhamKhachHang;
import com.team.ui.ActionClick;
import com.team.ui.GUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class PanelPhienLamViecCuaKhachHang extends BasePanel {

    private ActionClick ack;
    private JButton btn_listSP, btn_gioHang, btn_lichSu, btn_khac;
    private JLabel jLabel_UserName;
    private JButton jButton_Logout;
    private static final String BT_listSP = "DanhSachSP";
    private static final String BT_gioHang = "GioHang";
    private static final String BT_lichSu = "LS";
    private static final String BT_khac = "Khac";
    
    private KhachHang khachHang;
    private List<SanPhamCuaHang> listHeThong;
    private List<SanPhamKhachHang> listSPKH;
    private List<SanPhamCuaHang> gioHangs;
    private List<SanPhamKhachHang> lichSu;
    
    public ActionClick getAck() {
        return ack;
    }

    public void setAck(ActionClick ack) {
        this.ack = ack;
    }

    @Override
    public void initUI() {
        setLayout(null);
        setBackground(Color.WHITE);
        setVisible(true);
    }

    @Override
    public void addEvent() {

    }

    @Override
    public void addComp() {

    	Font font1 = new Font("Tahoma", Font.BOLD, 20);
        Font font2 = new Font("Tahoma", Font.BOLD, 15);
        Icon icon = new ImageIcon("D:\\Downloads\\ic_arrow_back_ios1.png", "comeback");
        Font font = new Font("Tahoma",Font.PLAIN,20);
        Font small_font = new Font("Tahoma",Font.CENTER_BASELINE,15);

        jLabel_UserName = createLabel("UserName",10,12,small_font,Color.black,Color.white);
        jLabel_UserName.setBackground(null);
        add(jLabel_UserName);
        jButton_Logout = createButton("Đăng xuất", jLabel_UserName.getWidth() + 15, 6,small_font,Color.black,"button_logout");
        jButton_Logout.setSize(100,30);
        jButton_Logout.setFocusable(false);
        jButton_Logout.setBorder(BorderFactory.createEmptyBorder());
        jButton_Logout.setBackground(Color.decode("#d95a09"));
        add(jButton_Logout);
        
        btn_listSP = createButton("Danh sách sản phẩm", 150, 120, font2, Color.BLACK, BT_listSP);
        btn_listSP.setSize(450, 50);
        add(btn_listSP);
        btn_gioHang = createButton("Giỏ hàng của bạn", 150, btn_listSP.getY() + 70, font2, Color.BLACK, BT_gioHang);
        btn_gioHang.setSize(450, 50);
        add(btn_gioHang);

        listHeThong = new ArrayList<>();
        lichSu = new ArrayList<>();
        listSPKH = new ArrayList<>();
        gioHangs = new ArrayList<>();
        

    }
      

    @Override
    protected void handleClick(String name) {
        if (name.equals(BT_listSP)) {
        	ack.passDataToPanelDSSPKH();
            ack.toDanhSachSanPham();
        }
        if (name.equals(BT_gioHang)) {
        	ack.passDataToPanelGioHang();
            ack.toGioHang();
        }
        if(name.equals("button_logout")) {
        	ack.saveDataKH(khachHang);
        	listHeThong.clear();
        	listSPKH.clear();
        	gioHangs.clear();
        	ack.comeBack();
        }
    }

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}
    
    public void setTextUsernam(String name) {
    	jLabel_UserName.setText(name);
    }
    
    public void addListHeThong(SanPhamCuaHang sanPhamCuaHang) {
    	listHeThong.add(sanPhamCuaHang);
    }
    
	public List<SanPhamCuaHang> getListHeThong() {
		return listHeThong;
	}

	public void setListHeThong(List<SanPhamCuaHang> listHeThong) {
		this.listHeThong = listHeThong;
	}

	public List<SanPhamKhachHang> getListSPKH() {
		return listSPKH;
	}

	public void setListSPKH(List<SanPhamKhachHang> listSPKH) {
		this.listSPKH = listSPKH;
	}

	public List<SanPhamKhachHang> getLichSu() {
		return lichSu;
	}

	public void setLichSu(List<SanPhamKhachHang> lichSu) {
		this.lichSu = lichSu;
	}
    
    public void addSPGH(SanPhamCuaHang hang) {
    	gioHangs.add(hang);
    	listSPKH.add(new SanPhamKhachHang(hang.getMaSP()));
    	khachHang.updateList(listSPKH);
    }

	public List<SanPhamCuaHang> getGioHangs() {
		return gioHangs;
	}

	public void setGioHangs(List<SanPhamCuaHang> gioHangs) {
		this.gioHangs = gioHangs;
	}
	
	public void clearListHeThong() {
		listHeThong.clear();
	}
	
	public void listenOnDelete(int index) {
		listSPKH.remove(index);
		khachHang.updateList(listSPKH);
	}
	
	public void listenOnDelAll() {
		for (int i = 0; i < listSPKH.size(); i++) {
			listSPKH.get(i).setThanhToan(true);
		}
		lichSu.addAll(listSPKH);
		listSPKH.removeAll(listSPKH);
		khachHang.updateList(listSPKH);
	}
	
	public void addNewGH(SanPhamCuaHang hang) {
		gioHangs.add(hang);
    	listSPKH.add(new SanPhamKhachHang(hang.getMaSP()));
	}
}

