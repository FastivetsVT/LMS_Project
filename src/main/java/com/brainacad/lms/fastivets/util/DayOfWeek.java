package com.brainacad.lms.fastivets.util;

public enum DayOfWeek {
	MONDAY(1),
	MON(1),
	TUESDAY(2),
	TUE(2),
	WEDNESDAY(3),
	WED(3),
	THURSDAY(4),
	THU(4),
	FRIDAY(5),
	FRI(5),
	SATURDAY(6),
	SAT(6),
	SUNDAY(7),
	SUN(7);
	
	private int value;
	private static DayOfWeek[] values = DayOfWeek.values();
	
	private DayOfWeek(int value){
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public int getBitMask() {
		return 1<<(value-1);
	}
	
	public int addToBitMask(int bitMask) {
		return bitMask | getBitMask();
	}
	
	public static String getName(int dayOfWeek, boolean fullName){
		if (dayOfWeek >7 || dayOfWeek < 1) throw new IllegalArgumentException("DayOfWeek out of range");
		String name = values[fullName?(dayOfWeek-1)*2:dayOfWeek*2-1].name();
		return String.format("%.1S%s", name,name.substring(1).toLowerCase());
	}
	
	public static String toStringFromBitMask (int binaryMask, boolean fullNames){
		String binaryString = Integer.toBinaryString(binaryMask);
		StringBuilder daysFormat = new StringBuilder();
		for (int i = binaryString.length()-1; i >= 0; i--)
			if (binaryString.charAt(i) == '1') {
				daysFormat.append(DayOfWeek.getName(binaryString.length()-i, fullNames)).append(", ");		
			}
		return daysFormat.delete(daysFormat.length()-2, daysFormat.length()).toString();
	}
	
}
