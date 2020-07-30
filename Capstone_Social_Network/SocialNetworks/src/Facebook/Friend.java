package Facebook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import graph.CapGraph;
import util.GraphLoader;

public class Friend {
	CapGraph socialGraph;
	
	public Friend() {
		socialGraph = new CapGraph();
	}
	
	public Friend(String fname) {
		socialGraph = new CapGraph();
		GraphLoader.loadGraph(socialGraph, fname);
	}
	
	public HashMap<Integer, HashSet<Integer>> recommendFriendL(int user) {
		// get the friend list of user
		HashMap<Integer, HashSet<Integer>> nodeMap = socialGraph.exportGraph();
		Set<Integer> neighbor = nodeMap.get(user);
		HashMap<Integer, HashSet<Integer>> recFriendL = 
				new HashMap<Integer, HashSet<Integer>>();
		for (int person : neighbor) {
			recFriendL.put(person, new HashSet<Integer>());
			for (int another: neighbor) {
				if ((!nodeMap.get(person).contains(another)) &&
						(person != another)) 
					recFriendL.get(person).add(another);
			}
		}
		return recFriendL;
	}
	
	public void hasMostFriend() {
		HashMap<Integer, HashSet<Integer>> nodeMap = socialGraph.exportGraph();
		HashMap<Integer, Integer> friendNum = new HashMap<Integer, Integer>();
		HashMap<Integer, HashSet<Integer>> friendDist =
						new HashMap<Integer, HashSet<Integer>>();
		for (int person : nodeMap.keySet()) {
			int friendSize = nodeMap.get(person).size();
			if (!friendDist.containsKey(friendSize))
				friendDist.put(friendSize, new HashSet<Integer>());
			friendNum.put(person, friendSize);
			friendDist.get(friendSize).add(person);
		}
		int maxVal = Collections.max(friendNum.values());
		System.out.println("THe most number of friends is: " +maxVal);
		System.out.println("The users who have most friends are: " +
					friendDist.get(maxVal));
	}
	
	public static void main(String[] args) {
		String fname1 = "data/scc/test_2.txt";
		String fname2 = "data/facebook_1000.txt";
		String fname3 = "data/facebook_2000.txt";
		String fname4 = "data/facebook_ucsd.txt";
		Friend myFriend = new Friend(fname4);
		int user = 732;
		HashMap<Integer, HashSet<Integer>> recList = 
							myFriend.recommendFriendL(user);
		System.out.println("The recommended friend among user " +user);
		for (int person : recList.keySet())
			System.out.println(person + ":\t" +recList.get(person));
		
		myFriend.hasMostFriend();
	}
}
