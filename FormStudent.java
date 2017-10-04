package com.quanglinhit;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

public class FormStudent extends JDialog {
	JTextField txtId, txtName, txtBirthday, txtPhone, txtAddress;
	JLabel lblTotalMessage, lblIdMassage, lblNameMassage, lblBirthdayMassage, lblPhoneMassage, lblAddressMassage;
	JButton btnSave;
	Connection conn = ListStudent.conn;
	Statement state = ListStudent.state;

	public static int result = -1;
	public String strId = "";

	public FormStudent(String title) {
		this.setTitle(title);
		addControls();
		addEvents();
		if (strId.length() != 0) {
			displayInfoDetail();
		}
	}

	public void displayInfoDetail() {
		try {
			String sql = "SELECT * FROM hocvien WHERE id='" + strId + "'";
			state = conn.createStatement();
			ResultSet result = state.executeQuery(sql);
			if (result.next()) {
				txtId.setText(result.getString(1));
				txtName.setText(result.getString(2));
				txtBirthday.setText(result.getString(3));
				txtPhone.setText(result.getString(4));
				txtAddress.setText(result.getString(5));
			}
			txtId.setEditable(false);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void addControls() {
		Container con = getContentPane();
		con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
		JPanel pnInfo = new JPanel();
		pnInfo.setLayout(new FlowLayout(FlowLayout.LEFT));

		lblTotalMessage = new JLabel();
		JLabel lblCode = new JLabel("Mã sinh viên: ");
		txtId = new JTextField(20);
		lblIdMassage = new JLabel();

		JLabel lblName = new JLabel("Tên sinh viên: ");
		txtName = new JTextField(20);
		lblNameMassage = new JLabel();

		JLabel lblBirthday = new JLabel("Ngày sinh: ");
		txtBirthday = new JTextField(20);
		lblBirthdayMassage = new JLabel();

		JLabel lblPhone = new JLabel("Số điện thoại: ");
		txtPhone = new JTextField(20);

		lblPhoneMassage = new JLabel();
		// MaskFormatter mkPhone = null;
		// try {
		// mkPhone = new MaskFormatter("#### ### ###");
		// } catch (ParseException e1) {
		// e1.printStackTrace();
		// }
		// mkPhone.setPlaceholderCharacter('_');
		// txtPhone = new JFormattedTextField(mkPhone);

		JLabel lblAddress = new JLabel("Địa chỉ: ");
		txtAddress = new JTextField(20);
		lblAddressMassage = new JLabel();

		btnSave = new JButton("Lưu");

		con.add(pnInfo);
		pnInfo.add(lblCode);
		pnInfo.add(txtId);
		pnInfo.add(lblName);
		pnInfo.add(txtName);
		pnInfo.add(lblBirthday);
		pnInfo.add(txtBirthday);
		pnInfo.add(lblPhone);
		pnInfo.add(txtPhone);
		pnInfo.add(lblAddress);
		pnInfo.add(txtAddress);
		pnInfo.add(btnSave);

		lblCode.setPreferredSize(lblName.getPreferredSize());
		lblBirthday.setPreferredSize(lblName.getPreferredSize());
		lblPhone.setPreferredSize(lblName.getPreferredSize());
		lblAddress.setPreferredSize(lblName.getPreferredSize());

		this.setSize(400, 250);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}

	private void addEvents() {
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (strId.length()==0) {
					saveDB();
				} else {
					updateDB();
				}
				
				// String birthday = txtBirthday.getText();
				// String phone = txtPhone.getText();
				// HashMap<String, String> erros = new
				// FormHandle().validate(birthday, phone);
				// if (erros.size() == 0) {
				// lblBirthdayMassage.setText("");
				// lblPhoneMassage.setText("");
				// lblTotalMessage.setText("");
				// saveDB();
				// } else {
				// lblTotalMessage.setForeground(Color.RED);
				// lblTotalMessage.setText("Nhap dung dinh dang");
				//
				// if (erros.containsKey("Birthday")) {
				// lblBirthdayMassage.setForeground(Color.red);
				// lblBirthdayMassage.setText(erros.get("Birthday"));
				// } else {
				// lblBirthdayMassage.setForeground(Color.green);
				// lblBirthdayMassage.setText("Success");
				// }
				// if (erros.containsKey("Phone")) {
				// lblPhoneMassage.setForeground(Color.red);
				// lblPhoneMassage.setText(erros.get("Phone"));
				//
				// } else {
				// lblPhoneMassage.setForeground(Color.green);
				// lblPhoneMassage.setText("Success");
				// }
				// }
			}
		});
	}

	private void saveDB() {
		try {
			String sql = "INSERT INTO hocvien VALUES ('" + txtId.getText() + "','" + txtName.getText() + "','"
					+ txtBirthday.getText() + "','" + txtPhone.getText() + "','" + txtAddress.getText() + "')";
			state = conn.createStatement();
			int x = state.executeUpdate(sql);
			result = x;
			dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateDB() {
		try {
			String sql = "UPDATE hocvien SET name='" + txtName.getText() + "'" + ",birthday='" + txtBirthday.getText()
					+ "'" + ",phone='" + txtPhone.getText() + "'" + " ,address='" + txtAddress.getText() + "'"
					+ " where id='" + txtId.getText() + "'";
			state = conn.createStatement();
			int x = state.executeUpdate(sql);
			result = x;
			dispose();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}