package com.zero.zero.timetable.OVP_Fetcher.debug;

import com.zero.zero.timetable.OVP_Fetcher.receive.HTMLFetcher;

public class Test {
	public static void main(String[] args) {
		HTMLFetcher fetcher = new HTMLFetcher("url");
		fetcher.loadHTML();
	}
}
