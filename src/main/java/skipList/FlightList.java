package skipList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/** The class that represents the flight database using a skip list */
public class FlightList {

	// FILL IN CODE: needs to store the head, the tail and the height of the skip
	// list
	private FlightNode head;
	private FlightNode tail;
	private int height;

	/** Default constructor */
	public FlightList() {
		// FILL IN CODE
		head = new FlightNode(null, null);
		tail = new FlightNode(null, null);
		head.next = tail;
		tail.prev = head;
		height = 1;
	}

	/**
	 * Constructor.
	 * Reads flight data from the file and inserts it into this skip list.
	 * @param filename the name of he file
	 */
	public FlightList(String filename) {
		// FILL IN CODE
		head = new FlightNode(null, null);
		tail = new FlightNode(null, null);
		head.next = tail;
		tail.prev = head;
		height = 1;

		String line;
		File file;
		Scanner scan;
		try {
			file = new File(filename);
			scan = new Scanner(file);
			while (scan.hasNext()) {
				line = scan.nextLine();
				String[] strList = line.split(" ");
				FlightKey tempKey = new FlightKey(strList[0], strList[1], strList[2], strList[3]);
				double tempPrice = Double.valueOf(strList[5]);
				FlightData tempData = new FlightData(strList[4], tempPrice);
				FlightNode tempNode = new FlightNode(tempKey,tempData);


			}



		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Returns true if the node with the given key exists in the skip list,
	 * false otherwise. This method needs to be efficient.
	 * 
	 * @param key flight key
	 * @return true if the key is in the skip list, false otherwise
	 */
	public boolean find(FlightKey key) {
		// FILL IN CODE
		return false; // don't forget to change it
	}

	/**
	 * Insert a (key, value) pair to the skip list. Returns true if it was able
	 * to insert.
	 *
	 * @param key flight key
	 * @param data associated flight data
	 * @return true if insertion was successful
	 */
	public boolean insert(FlightKey key, FlightData data) {
		// FILL IN CODE


		// call find()
		// past;


		try {
			int coin = -1;
			int level = 1;
			Random rand = new Random();
			while (coin < 0) {
				coin = rand.nextInt(10) - 5;
				level++;
			}
			FlightNode curr = this.head;
			if (level <= this.height) {
				for (int i = 0; i < level - 1; i++) {
					curr = curr.up;
				}
				FlightNode temp = new FlightNode(key, data);
				FlightNode temp1 = temp;
				for (int i = 0; i < level; i++) {
					temp1.down = new FlightNode(temp);
					temp1.down.up = temp;
					temp1 = temp.down;
				}
				for (int j = 0; j < level; j++) {
					while (curr.next.getKey() != null && curr.next.getKey().compareTo(key) < 0) {
						curr = curr.next;
					}

					temp.next = curr.next;
					curr.next.prev = temp;
					curr.next = temp;
					temp.prev = curr;
					temp = temp.down;
					curr = curr.down;
				}
			} else {
				FlightNode currTail = this.tail;
				for (int i = 0; i < this.height - 1; i++) {
					curr = curr.up;
					currTail = this.tail;
				}
				for (int i = 0; i < level - this.height; i++) {
					curr.up = new FlightNode(curr);
					curr.up.down = curr;
					currTail.up = new FlightNode(currTail);
					currTail.up.down = currTail;
					curr = curr.up;
					currTail = currTail.up;
					curr.next = currTail;
					currTail.prev = curr;
				}
				FlightNode temp = new FlightNode(key, data);
				FlightNode temp1 = temp;
				for (int i = 0; i < level; i++) {
					temp1.down = new FlightNode(temp);
					temp1.down.up = temp;
					temp1 = temp.down;
				}
				for (int j = 0; j < level; j++) {
					while (curr.next.getKey() != null && curr.next.getKey().compareTo(key) < 0) {
						curr = curr.next;
					}

					temp.next = curr.next;
					curr.next.prev = temp;
					curr.next = temp;
					temp.prev = curr;
					temp = temp.down;
					curr = curr.down;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
		//return false; // don't forget to change it
	}

	/**
	 * Returns the list of nodes that are successors of a given key. Refer to
	 * the project pdf for a detailed description of the method.
	 * 
	 * @param key flight key
	 * @return successors of the given key
	 */
	public List<FlightNode> successors(FlightKey key) {
		List<FlightNode> arr = new ArrayList<FlightNode>();
		// FILL IN CODE

		return arr;
	}

	/**
	 * Returns the list of nodes that are predecessors of a given key
	 * (that corresponds to flights that are earlier than the given flight).
	 *  Refer to the project pdf for a detailed description of the method.
	 * 
	 * @param key flight key
	 * @return predecessors of the given key
	 */
	public List<FlightNode> predecessors(FlightKey key, int timeFrame) {
		List<FlightNode> arr = new ArrayList<FlightNode>();

		// FILL IN CODE
		return arr;

	}

	/**
	 * Returns the string representing the SkipList (contains the elements on all levels starting at the
	 * top. Each level should be on a separate line, for instance:
	 * (SFO, PVD, 03/14, 09:15)
	 * (SFO, JFK, 03/15, 06:30), (SFO, PVD, 03/14, 09:15)
	 * (SFO, JFK, 03/15, 06:30),   (SFO, JFK, 03/15, 7:15), (SFO, JFK, 03/20, 5:00), (SFO, PVD, 03/14, 09:15)
	 */
	public String toString() {
		// FILL IN CODE

		return ""; // don't forget to change it
	}

	/**
	 * Outputs the SkipList to a file
	 * (prints the elements on all levels starting at the top.
	 * Each level should be printed on a separate line.
	 * @param filename the name of the file
	 */
	public void print(String filename) {
		// FILL IN CODE
	}

	/**
	 * Returns a list of nodes that have the same origin and destination cities
	 * and the same date as the key, with departure times within the given time
	 * frame of the departure time of the key.
	 *
	 * @param key flight key
	 * @param timeFrame interval of time
	 * @return list of flight nodes that have the same origin, destination and date
	 * as the key, and whose departure time is within a given timeframe
	 */
	public List<FlightNode> findFlights(FlightKey key, int timeFrame) {
		List<FlightNode> resFlights = new ArrayList<FlightNode>();
		// FILL IN CODE

		return resFlights;
	}

}
