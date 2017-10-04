package com.quanglinhit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Driver;
import com.quanglinhit.reflection.Student;

public class ListStudent extends JFrame {

	DefaultTableModel tableModel;
	JTable table;
	JButton btnAdd, btnModify, btnDelete;

	public static Connection conn;
	public static Statement state = null;
	static Student student = new Student();

	public ListStudent(String title) {
		super(title);
		addControls();
		addEvents();
		connectMySQL();
		displayDetail();
	}

	private void connectMySQL() {
		try {
			String strConn = "jdbc:mysql://localhost/db_hocvien?useUnicode=true&characterEncoding=utf-8";
			Properties pro = new Properties();
			pro.put("user", "root");
			pro.put("password", "");
			Driver driver = new Driver();
			conn = driver.connect(strConn, pro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void displayDetail() {
		try {
			state = conn.createStatement();
			ResultSet rs = state.executeQuery("SELECT * FROM hocvien");
			tableModel.setRowCount(0);
			while (rs.next()) {
				Vector<Object> vec = new Vector<>();
				vec.add(rs.getString(1));
				vec.add(rs.getString(2));
				vec.add(rs.getDate(3));
				vec.add(rs.getString(4));
				vec.add(rs.getString(5));
				tableModel.addRow(vec);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addEvents() {
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FormStudent ui = new FormStudent("Thông tin chi tiết sinh viên");
				ui.setVisible(true);
				if (FormStudent.result > 0) {
					displayDetail();
				}
			}
		});
		
		btnModify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					return;
				}
				String id = table.getValueAt(row, 0) + "";
				// int col = table.getSelectedColumn();
				// JOptionPane.showConfirmDialog(table, "Row: " + row + "Col: "
				// + col);

				FormStudent form = new FormStudent("Sửa thông tin sinh viên");
				form.strId = id;
				form.displayInfoDetail();
				form.setVisible(true);
				if (FormStudent.result > 0) {
					displayDetail();
				}
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				processDel();
			}
		});
	}

	protected void processDel() {
		String strID = table.getValueAt(table.getSelectedRow(), 0) + "";
		try {
			String sql = "DELETE FROM hocvien Where id='" + strID + "'";
			state = conn.createStatement();
			int x = state.executeUpdate(sql);
			if (x > 0) {
				displayDetail();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addControls() {
		Container con = getContentPane();
		con.setLayout(new BorderLayout());
		JPanel pnNorth = new JPanel();
		JLabel lblTitle = new JLabel("Danh sách sinh viên");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitle.setForeground(Color.BLUE);
		pnNorth.add(lblTitle);
		con.add(pnNorth, BorderLayout.NORTH);

		tableModel = new DefaultTableModel();
		tableModel.addColumn("Mã học viên");
		tableModel.addColumn("Tên học viên");
		tableModel.addColumn("Ngày sinh");
		tableModel.addColumn("Số điện thoại");
		tableModel.addColumn("Địa chỉ");
		table = new JTable(tableModel) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JScrollPane scTable = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		con.add(scTable, BorderLayout.CENTER);

		JPanel pnButton = new JPanel();
		pnButton.setLayout(new FlowLayout(FlowLayout.LEFT));
		btnAdd = new JButton("Thêm mới");
		btnModify = new JButton("Sửa thông tin");
		btnDelete = new JButton("Xóa thông tin");
		pnButton.add(btnAdd);
		pnButton.add(btnModify);
		pnButton.add(btnDelete);
		con.add(pnButton, BorderLayout.SOUTH);

		this.setSize(700, 500);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
}
