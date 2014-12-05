package com.sam.util;

public class IDCardUtil {
	private static int sum=0;

	// 18λ���֤:6λ����������+8λ��������+3λ˳����+1λ������
	// ˳��������Ϊ�У�ż��ΪŮ
	// ���� ISO 7064:1983.MOD 11-2 У������㷨
	// 1�����ݸ�����17λ���֣���������Ӧ�ļ�Ȩ���ӵĳ˻��ĺ�S
	// 2������ S/11 ������ T
	// 3������ ��12-T��/11 ������ R
	// 4��R��Ϊ����õ���У���룬���R=10����У����λ"x"������Ϊ����R
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
