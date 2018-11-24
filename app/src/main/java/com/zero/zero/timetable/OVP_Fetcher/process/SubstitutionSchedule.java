package com.zero.zero.timetable.OVP_Fetcher.process;
import java.util.ArrayList;
import java.util.Arrays;

public class SubstitutionSchedule {
	private String title = "";
	private ArrayList<String[]> data = null;
	private ArrayList<String[]> infos = null;
	
	public SubstitutionSchedule(ArrayList<String[]> data, ArrayList<String[]> infos, String title) {
		this.data = data;
		this.title = title;
		this.infos = infos;
	}
l
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<String[]> getData() {
		return data;
	}
	
	public ArrayList<String[]> getData(String Identifier) {
		ArrayList<String[]> result = new ArrayList<String[]>();
		data.forEach(row -> {
			if(Arrays.asList(row).contains(Identifier))
				result.add(row);
		});
		return result;
	}

	public void setData(ArrayList<String[]> data) {
		this.data = data;
	}
	
	public void log() {
		System.out.println(title);
		infos.forEach(x -> System.out.println(Arrays.toString(x)));
		data.forEach(x -> System.out.println(Arrays.toString(x)));
		System.out.println("---------\n\n\n---------");
	}
	
	public void log(String CLASS) {
		System.out.println(title);
		data.forEach(x -> {
			if(x[0].equals(CLASS))
				System.out.println(Arrays.toString(x));
		});
		System.out.println("---------\n\n\n---------");
	}

	public ArrayList<String[]> getInfos() {
		return infos;
	}

	public void setInfos(ArrayList<String[]> infos) {
		this.infos = infos;
	}
	
}
