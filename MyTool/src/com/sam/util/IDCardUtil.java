package com.sam.util;

public class IDCardUtil {
	private static int sum=0;

	// 18位身份证:6位行政区域码+8位出生日期+3位顺序码+1位检验码
	// 顺序码奇数为男，偶数为女
	// 根据 ISO 7064:1983.MOD 11-2 校验码计算法
	// 1、根据给定的17位数字，计算其相应的加权因子的乘积的和S
	// 2、计算 S/11 的余数 T
	// 3、计算 （12-T）/11 的余数 R
	// 4、R即为计算得到的校验码，如果R=10，这校验码位"x"，否则为数字R
public   static String   getVerify(int[] num17){
	    sum=0;
		final int[] power = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4,
				2 };
		for (int i = 0; i < num17.length; i++) {
			sum = sum + num17[i] * power[i];
		}
		int T = sum % 11;
		int R = (12 - T) % 11;
		return R == 10 ? "x" : String.valueOf(R);
}
}
