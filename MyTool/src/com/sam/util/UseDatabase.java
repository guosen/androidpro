package com.sam.util;

import java.util.ArrayList;
import java.util.List;

import com.sam.model.zipcode;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UseDatabase {
	Context context;
	DBHelper dbhelper;
	public SQLiteDatabase sqlitedatabase;

	public UseDatabase(Context context) {
		super();
		this.context = context;
	}

	// 打开数据库连接
	public void opendb(Context context) {
		dbhelper = new DBHelper(context);
		sqlitedatabase = dbhelper.openDatabase();
	}

	// 关闭数据库连接
	public void closedb(Context context) {
		if (sqlitedatabase.isOpen()) {
			sqlitedatabase.close();
		}
	}

	// 查找数据
	public zipcode getZipByZipcode(String Zipcode) {
		zipcode Items = new zipcode();
		try {
			opendb(context);
			String sql = "select * from ziptb where ZipCode=" + Zipcode;
			Cursor c = sqlitedatabase.rawQuery(sql, null);
			if (c != null) {
				while (c.moveToNext()) {

					Items.setAreaCode(c.getString(c.getColumnIndex("IDCard")));
					Items.setCityName(c.getString(c.getColumnIndex("CityName")));
					Items.setCountryName(c.getString(c
							.getColumnIndex("CountryName")));
					Items.setProvinceName(c.getString(c
							.getColumnIndex("ProvinceName")));
					String str = c.getString(c.getColumnIndex("ProvinceName"));
				}
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closedb(context);
		}
		return Items;
	}

	// 通过地名查找邮编号码
	/**************************** kaishi ******/
	public zipcode getZipCodeByPlaceName(String placeName) {

		zipcode Items = new zipcode();
		try {
			opendb(context);
			String byCountry = "SELECT * FROM main.ziptb where CountryName like '%"
					+ placeName + "%'";
			Cursor c_country = sqlitedatabase.rawQuery(byCountry, null);
			if (c_country != null && c_country.getCount() != 0) {

				while (c_country.moveToNext()) {

					Items.setAreaCode(c_country.getString(c_country
							.getColumnIndex("IDCard")));
					Items.setCityName(c_country.getString(c_country
							.getColumnIndex("CityName")));
					Items.setCountryName(c_country.getString(c_country
							.getColumnIndex("CountryName")));
					Items.setProvinceName(c_country.getString(c_country
							.getColumnIndex("ProvinceName")));
					Items.setZipCode(c_country.getString(c_country
							.getColumnIndex("ZipCode")));
					String str = c_country.getString(c_country
							.getColumnIndex("ProvinceName"));
				}
				c_country.close();

			} else {
				String byCity = "SELECT * FROM main.ziptb where CityName like '%"
						+ placeName + "%'";
				Cursor c_city = sqlitedatabase.rawQuery(byCity, null);

				if (c_city != null && c_city.getCount() != 0) {
					while (c_city.moveToNext()) {
						Items.setAreaCode(c_city.getString(c_city
								.getColumnIndex("IDCard")));
						Items.setCityName(c_city.getString(c_city
								.getColumnIndex("CityName")));
						Items.setCountryName(c_city.getString(c_city
								.getColumnIndex("CountryName")));
						Items.setProvinceName(c_city.getString(c_city
								.getColumnIndex("ProvinceName")));
						Items.setZipCode(c_city.getString(c_city
								.getColumnIndex("ZipCode")));

						String str = c_city.getString(c_city
								.getColumnIndex("ProvinceName"));
					}
					c_city.close();

				} else {

					String byProvince = "SELECT * FROM main.ziptb where ProvinceName like '%"
							+ placeName + "%'";
					Cursor c_province = sqlitedatabase.rawQuery(byProvince,
							null);

					if (c_province != null) {
						while (c_province.moveToNext()) {
							Items.setAreaCode(c_province.getString(c_province
									.getColumnIndex("IDCard")));
							Items.setCityName(c_province.getString(c_province
									.getColumnIndex("CityName")));
							Items.setCountryName(c_province
									.getString(c_province
											.getColumnIndex("CountryName")));
							Items.setProvinceName(c_province
									.getString(c_province
											.getColumnIndex("ProvinceName")));
							Items.setZipCode(c_province.getString(c_province
									.getColumnIndex("ZipCode")));
							String str = c_province.getString(c_province
									.getColumnIndex("ProvinceName"));
						}
						c_province.close();

					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closedb(context);
		}

		return Items;

	}
	// 通过地名查找邮编号码返回列表
		/**************************** kaishi ******/
		public ArrayList<zipcode> getZipCodeListByPlaceName(String placeName) {
             List<zipcode>zipcodelist=new ArrayList<zipcode>();
             zipcode Items;
			try {
				opendb(context);
				String byCountry = "SELECT * FROM main.ziptb where CountryName like '%"
						+ placeName + "%'";
				Cursor c_country = sqlitedatabase.rawQuery(byCountry, null);
				if (c_country != null && c_country.getCount() != 0) {

					while (c_country.moveToNext()) {
						Items= new zipcode();
						Items.setAreaCode(c_country.getString(c_country
								.getColumnIndex("IDCard")));
						Items.setCityName(c_country.getString(c_country
								.getColumnIndex("CityName")));
						Items.setCountryName(c_country.getString(c_country
								.getColumnIndex("CountryName")));
						Items.setProvinceName(c_country.getString(c_country
								.getColumnIndex("ProvinceName")));
						Items.setZipCode(c_country.getString(c_country
								.getColumnIndex("ZipCode")));
						zipcodelist.add(Items);
						String str = c_country.getString(c_country
								.getColumnIndex("ProvinceName"));
					}
					c_country.close();

				} else {
					String byCity = "SELECT * FROM main.ziptb where CityName like '%"
							+ placeName + "%'";
					Cursor c_city = sqlitedatabase.rawQuery(byCity, null);

					if (c_city != null && c_city.getCount() != 0) {
						while (c_city.moveToNext()) {
							Items= new zipcode();
							Items.setAreaCode(c_city.getString(c_city
									.getColumnIndex("IDCard")));
							Items.setCityName(c_city.getString(c_city
									.getColumnIndex("CityName")));
							Items.setCountryName(c_city.getString(c_city
									.getColumnIndex("CountryName")));
							Items.setProvinceName(c_city.getString(c_city
									.getColumnIndex("ProvinceName")));
							Items.setZipCode(c_city.getString(c_city
									.getColumnIndex("ZipCode")));
							zipcodelist.add(Items);
							String str = c_city.getString(c_city
									.getColumnIndex("ProvinceName"));
						}
						c_city.close();

					} else {

						String byProvince = "SELECT * FROM main.ziptb where ProvinceName like '%"
								+ placeName + "%'";
						Cursor c_province = sqlitedatabase.rawQuery(byProvince,
								null);

						if (c_province != null) {
							while (c_province.moveToNext()) {
								Items= new zipcode();
								Items.setAreaCode(c_province.getString(c_province
										.getColumnIndex("IDCard")));
								Items.setCityName(c_province.getString(c_province
										.getColumnIndex("CityName")));
								Items.setCountryName(c_province
										.getString(c_province
												.getColumnIndex("CountryName")));
								Items.setProvinceName(c_province
										.getString(c_province
												.getColumnIndex("ProvinceName")));
								Items.setZipCode(c_province.getString(c_province
										.getColumnIndex("ZipCode")));
								zipcodelist.add(Items);
								String str = c_province.getString(c_province
										.getColumnIndex("ProvinceName"));
							}
							c_province.close();

						}

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closedb(context);
			}

			return (ArrayList<zipcode>) zipcodelist;

		}
		
		
		// 通过邮编号码查找邮编号码返回列表
				/**************************** kaishi ******/
				public ArrayList<zipcode> getZipCodeListByCode(String placeName) {
		             List<zipcode>zipcodelist=new ArrayList<zipcode>();
		             zipcode Items;
					try {
						opendb(context);
						String byCountry = "SELECT * FROM main.ziptb where ZipCode="+placeName;
						Cursor c_country = sqlitedatabase.rawQuery(byCountry, null);
						if (c_country != null && c_country.getCount() != 0) {

							while (c_country.moveToNext()) {
								Items= new zipcode();
								Items.setAreaCode(c_country.getString(c_country
										.getColumnIndex("IDCard")));
								Items.setCityName(c_country.getString(c_country
										.getColumnIndex("CityName")));
								Items.setCountryName(c_country.getString(c_country
										.getColumnIndex("CountryName")));
								Items.setProvinceName(c_country.getString(c_country
										.getColumnIndex("ProvinceName")));
								Items.setZipCode(c_country.getString(c_country
										.getColumnIndex("ZipCode")));
								zipcodelist.add(Items);
								String str = c_country.getString(c_country
										.getColumnIndex("ProvinceName"));
							}
							c_country.close();

						} else {
							String byCity = "SELECT * FROM main.ziptb where CityName like '%"
									+ placeName + "%'";
							Cursor c_city = sqlitedatabase.rawQuery(byCity, null);

							if (c_city != null && c_city.getCount() != 0) {
								while (c_city.moveToNext()) {
									Items= new zipcode();
									Items.setAreaCode(c_city.getString(c_city
											.getColumnIndex("IDCard")));
									Items.setCityName(c_city.getString(c_city
											.getColumnIndex("CityName")));
									Items.setCountryName(c_city.getString(c_city
											.getColumnIndex("CountryName")));
									Items.setProvinceName(c_city.getString(c_city
											.getColumnIndex("ProvinceName")));
									Items.setZipCode(c_city.getString(c_city
											.getColumnIndex("ZipCode")));
									zipcodelist.add(Items);
									String str = c_city.getString(c_city
											.getColumnIndex("ProvinceName"));
								}
								c_city.close();

							} else {

								String byProvince = "SELECT * FROM main.ziptb where ProvinceName like '%"
										+ placeName + "%'";
								Cursor c_province = sqlitedatabase.rawQuery(byProvince,
										null);

								if (c_province != null) {
									while (c_province.moveToNext()) {
										Items= new zipcode();
										Items.setAreaCode(c_province.getString(c_province
												.getColumnIndex("IDCard")));
										Items.setCityName(c_province.getString(c_province
												.getColumnIndex("CityName")));
										Items.setCountryName(c_province
												.getString(c_province
														.getColumnIndex("CountryName")));
										Items.setProvinceName(c_province
												.getString(c_province
														.getColumnIndex("ProvinceName")));
										Items.setZipCode(c_province.getString(c_province
												.getColumnIndex("ZipCode")));
										zipcodelist.add(Items);
										String str = c_province.getString(c_province
												.getColumnIndex("ProvinceName"));
									}
									c_province.close();

								}

							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						closedb(context);
					}

					return (ArrayList<zipcode>) zipcodelist;

				}
	//通过身份证号码查询出所有个人信息
	public  zipcode getInfoByIDCard(String idcard){
		
		zipcode code=new zipcode();
		try {
			opendb(context);
			String sql = "select * from ziptb where IDCard=" + idcard;
			Cursor c = sqlitedatabase.rawQuery(sql, null);
			if (c != null) {
				while (c.moveToNext()) {

					code.setAreaCode(c.getString(c.getColumnIndex("IDCard")));
					code.setCityName(c.getString(c.getColumnIndex("CityName")));
					code.setCountryName(c.getString(c
							.getColumnIndex("CountryName")));
					code.setProvinceName(c.getString(c
							.getColumnIndex("ProvinceName")));
					String str = c.getString(c.getColumnIndex("ProvinceName"));
				}
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closedb(context);
		}
		return code;
		
	}

}
