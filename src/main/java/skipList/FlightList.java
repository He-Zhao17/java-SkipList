package skipList;

import javax.swing.plaf.synth.Region;
import javax.xml.stream.events.StartDocument;
import java.io.*;
import java.util.*;

/** The class that represents the flight database using a skip list */
public class FlightList {

	// FILL IN CODE: needs to store the head, the tail and the height of the skip
	// list
	private FlightNode head;
	private FlightNode tail;
	private int size;
	private int height;

	/** Default constructor */
	public FlightList() {
		// FILL IN CODE
		head = new FlightNode(new FlightKey("AAA", "AAA", "00/00/0000", "00:00"), null);
		tail = new FlightNode(new FlightKey("ZZZ", "ZZZ", "99/13/2060", "25:61"), null);
		head.next = tail;
		tail.prev = head;
		height = 1;
		size = 0;
	}

	/**
	 * Constructor.
	 * Reads flight data from the file and inserts it into this skip list.
	 * @param filename the name of he file
	 */
	public FlightList(String filename) {
		// FILL IN CODE
		head = new FlightNode(new FlightKey("AAA", "AAA", "00/00/0000", "00:00"), new FlightData("AA000", 000));
		tail = new FlightNode(new FlightKey("ZZZ", "ZZZ", "99/13/2060", "25:61"), new FlightData("ZZ999", 999));
		head.next = tail;
		tail.prev = head;
		height = 1;
		size = 0;

		try {
			File file = new File(filename);
			Scanner scan = new Scanner(file);
			while (scan.hasNext()) {
				String line = scan.nextLine();
				String[] strList = line.split(" ");
				FlightKey tempKey = new FlightKey(strList[0], strList[1], strList[2], strList[3]);
				double tempPrice = Double.valueOf(strList[5]);
				FlightData tempData = new FlightData(strList[4], tempPrice);
				insert(tempKey, tempData);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public FlightNode findNode(FlightKey key) {
		if (size == 0) {
			return new FlightNode(new FlightKey("AAA", "AAA", "00/00/0000", "00:00"), new FlightData("AA000", 000));
		}
		FlightNode curr = this.head;
		while (curr != null) {
			while (curr.getKey().compareTo(key) <= 0) {
				if (curr.getKey().compareTo(key) == 0) {
					return curr;
				}
				curr = curr.next;
			}
			curr = curr.prev;
			curr = curr.down;
		}
		return new FlightNode(new FlightKey("AAA", "AAA", "00/00/0000", "00:00"), new FlightData("AA000", 000));
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
		// Binary Search?
		if (size == 0) {
			return false;
		}
		FlightNode curr = this.head;
		while (curr != null) {
			while (curr.getKey().compareTo(key) <= 0) {
				if (curr.getKey().compareTo(key) == 0) {
					return true;
				}
				curr = curr.next;
			}
			curr = curr.prev;
			curr = curr.down;
		}
		return false;


//		if (size > 0 && (this.head.next.getKey().compareTo(key) > 0 || this.tail.prev.getKey().compareTo(key) < 0)) {
//			return false;
//		}
//		FlightNode curr = this.head;
//		for (int i = 0; i < this.height - 1; i++) {
//			curr = curr.up;
//		}
//		int currLevel = this.height;
//		FlightNode currprev;
//		while (currLevel > 0) {
//
//			while (curr.next.next != null && curr.next.getKey().compareTo(key) <= 0) {
//				if (curr.next.getKey().compareTo(key) == 0) {
//					return true;
//				}
//				curr = curr.next;
//
//			}
//			currprev = curr;
//			curr = curr.down;
//			currLevel--;
//		}
//		return false; // don't forget to change it
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
		if (find(key)) {
			return false;
		}

		int coin = -1;
		int level = 0;
		Random rand = new Random();
		while (coin < 0) {
			coin = rand.nextInt(10) - 5;
			level++;
		}
		FlightNode item = new FlightNode(key, data);
		for (int i = 0; i < level - 1; i++) {
			item.up = new FlightNode(item);
			item.up.down = item;
			item = item.up;
		}
		try {
			if (level <= this.height) {
				int temp = this.height - level;
				FlightNode curr = this.head;
				for (int i = 0; i < temp; i++) {
					while (curr.getKey().compareTo(key) < 0) {
						curr = curr.next;
					}
					curr = curr.prev;
					curr = curr.down;
				}
				for (int i = 0; i < level; i++) {
					while (curr.getKey().compareTo(key) < 0) {
						curr = curr.next;
					}
					curr = curr.prev;
					item.next = curr.next;
					curr.next.prev = item;
					curr.next = item;
					item.prev = curr;
					item = item.down;
					curr = curr.down;
				}
				size++;
				return true;
			} else {
				int temp = level - this.height;
				for (int i = 0; i < temp; i++) {
					this.head.up = new FlightNode(this.head);
					this.head.up.down = this.head;
					this.head = this.head.up;
					this.tail.up = new FlightNode(this.tail);
					this.tail.up.down = this.tail;
					this.tail = this.tail.up;
					this.head.next = this.tail;
					this.tail.prev = this.head;
				}
				FlightNode curr = this.head;
				for (int i = 0; i < level; i++) {
					while (curr.getKey().compareTo(key) < 0) {
						curr = curr.next;
					}
					curr = curr.prev;
					item.next = curr.next;
					curr.next.prev = item;
					curr.next = item;
					item.prev = curr;
					item = item.down;
					curr = curr.down;
				}
				size++;
				this.height = level;
				return true;
			}
		} catch (Exception e) {
			return false;
		}



//		try {
//			int coin = -1;
//			int level = 0;
//			Random rand = new Random();
//			while (coin < 0) {
//				coin = rand.nextInt(10) - 5;
//				level++;
//			}
//			FlightNode curr = this.head;
//			if (level <= this.height) {
//				for (int i = 0; i < level - 1; i++) {
//					curr = curr.up;
//				}
//				FlightNode temp = new FlightNode(key, data);
//				FlightNode temp1 = temp;
//				for (int i = 0; i < level - 1; i++) {
//					temp1.down = new FlightNode(temp);
//					temp1.down.up = temp;
//					temp1 = temp1.down;
//				}
//				for (int j = 0; j < level; j++) {
//					while (curr.next.next != null && curr.next.getKey().compareTo(key) < 0) {
//						curr = curr.next;
//					}
//
//					temp.next = curr.next;
//					curr.next.prev = temp;
//					curr.next = temp;
//					temp.prev = curr;
//					temp = temp.down;
//					curr = curr.down;
//				}
//				this.size++;
//			} else {
//				FlightNode currTail = this.tail;
//				curr = this.head;
//				for (int i = 0; i < this.height - 1; i++) {
//					curr = curr.up;
//					currTail = currTail.up;
//				}
//				for (int i = 0; i < level - this.height; i++) {
//					curr.up = new FlightNode(curr);
//					curr.up.down = curr;
//					currTail.up = new FlightNode(currTail);
//					currTail.up.down = currTail;
//					curr = curr.up;
//					currTail = currTail.up;
//					curr.next = currTail;
//					currTail.prev = curr;
//				}
//				FlightNode temp = new FlightNode(key, data);
//				FlightNode temp1 = temp;
//				for (int i = 0; i < level - 1; i++) {
//					temp1.down = new FlightNode(temp);
//					temp1.down.up = temp;
//					temp1 = temp1.down;
//				}
//				for (int j = 0; j < level; j++) {
//					while (curr.next.next != null && curr.next.getKey().compareTo(key) < 0) {
//						curr = curr.next;
//					}
//
//					temp.next = curr.next;
//					curr.next.prev = temp;
//					curr.next = temp;
//					temp.prev = curr;
//					temp = temp.down;
//					curr = curr.down;
//				}
//				this.height = level;
//				this.size++;
//			}
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
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
		FlightNode curr = this.head;
		int currLevel = this.height;
		while (curr.down != null) {
			while (curr.getKey().compareTo(key) <= 0) {
				curr = curr.next;
			}
			curr = curr.prev;
			curr = curr.down;
		}
		while (curr.getKey().compareTo(key) <= 0) {
			curr = curr.next;
		}
		while (curr.getKey().getOrigin().equals(key.getOrigin())
				&& curr.getKey().getDest().equals(key.getDest())
				&& curr.getKey().getDate().equals(key.getDate())) {
			arr.add(curr);
			curr = curr.next;
		}
		return arr;





//		if (this.size > 0 && this.tail.prev.getKey().compareTo(key) <= 0) {
//			return arr;
//		}
//
//
//		FlightNode curr = this.head;
//		for (int i = 0; i < this.height - 1; i++) {
//			curr = curr.up;
//		}
//		int currLevel = this.height;
//		FlightNode res = null;
//		outer:
//		while (currLevel > 0) {
//			while (!curr.next.getKey().getOrigin().equals("ZZZ")
//					&& curr.next.getKey().compareTo(key) <= 0
//					&& curr.next.getKey().getOrigin().equals(key.getOrigin())
//					&& curr.next.getKey().getDest().equals(key.getDest())
//					&& curr.next.getKey().getDate().equals(key.getDate())) {
//				if (curr.next.getKey().compareTo(key) == 0) {
//					res = curr.next;
//					break outer;
//				}
//				curr = curr.next;
//			}
//			curr = curr.down;
//			currLevel--;
//		}
//
//		if (res != null) {
//
//			for (int i = 0; i < currLevel - 1; i++) {
//				res = res.down;
//			}
//			res = res.next;
//			while (!res.getKey().getOrigin().equals("ZZZ") && res.getKey().getDate().equals(key.getDate())) {
//				arr.add(res);
//				res = res.next;
//			}
//			return arr;
//		}
//		curr = curr.next;
//		while (!res.getKey().getOrigin().equals("ZZZ") && res.getKey().getDate().equals(key.getDate())) {
//			arr.add(curr);
//			curr = curr.next;
//		}
//		return arr;
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
		FlightNode curr = this.tail;
		while (curr.down != null) {
			while (curr.getKey().compareTo(key) >= 0) {
				curr = curr.prev;
			}
			curr = curr.next;
			curr = curr.down;
		}
		while (curr.getKey().compareTo(key) >= 0) {
			curr = curr.prev;
		}
		Stack<FlightNode> sta = new Stack<FlightNode>();
		while (curr.getKey().getOrigin().equals(key.getOrigin())
				&& curr.getKey().getDest().equals(key.getDest())
				&& curr.getKey().getDate().equals(key.getDate())) {
			sta.push(curr);
			curr = curr.prev;
		}
		int size = sta.size();
		for (int i = 0; i < size; i++) {
			arr.add(sta.pop());
		}
		return arr;





//		if (this.size > 0 && this.head.next.getKey().compareTo(key) >= 0) {
//			return arr;
//		}
//
//		FlightNode curr = this.tail;
//		for (int i = 0; i < this.height - 1; i++) {
//			curr = curr.up;
//		}
//		int currLevel = this.height;
//		FlightNode res = null;
//		outer:
//		while (currLevel > 0) {
//			while (!curr.prev.getKey().getOrigin().equals("AAA")
//					&& curr.prev.getKey().compareTo(key) >= 0
//					&& curr.prev.getKey().getOrigin().equals(key.getOrigin())
//					&& curr.prev.getKey().getDest().equals(key.getDest())
//					&& curr.prev.getKey().getDate().equals(key.getDate())) {
//				if (curr.next.getKey().compareTo(key) == 0) {
//					res = curr.prev;
//					break outer;
//				}
//				curr = curr.prev;
//			}
//			curr = curr.down;
//			currLevel--;
//		}
//		Stack<FlightNode> stack = new Stack<FlightNode>();
//
//		if (res != null) {
//
//			for (int i = 0; i < currLevel - 1; i++) {
//				res = res.down;
//			}
//			res = res.prev;
//			while (!curr.prev.getKey().getOrigin().equals("AAA") && res.getKey().getDate().equals(key.getDate())) {
//				stack.push(res);
//				res = res.prev;
//			}
//			for (int i = 0; i < stack.size(); i++) {
//				arr.add(stack.pop());
//			}
//			return arr;
//		}
//		curr = curr.prev;
//		while (!curr.prev.getKey().getOrigin().equals("AAA") && res.getKey().getDate().equals(key.getDate())) {
//			stack.push(curr);
//			curr = curr.prev;
//		}
//		for (int i = 0; i < stack.size(); i++) {
//			arr.add(stack.pop());
//		}
//		return arr;
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
		StringBuilder resSb = new StringBuilder("");
		FlightKey t = new FlightKey("ZZZ", "ZZZ", "99/13/2060", "25:61");



		FlightNode h = this.head;
		for (int i = 0; i < this.height; i++) {
			FlightNode curr = h.next;
			while (curr.getKey().compareTo(t) < 0) {
				resSb.append(curr.getKey().toString());
				resSb.append(", ");
				curr = curr.next;
			}
			resSb.deleteCharAt(resSb.length() - 1);
			resSb.deleteCharAt(resSb.length() - 1);
			resSb.append("\n");
			h = h.down;
		}
		return resSb.toString();

	}

	/**
	 * Outputs the SkipList to a file
	 * (prints the elements on all levels starting at the top.
	 * Each level should be printed on a separate line.
	 * @param filename the name of the file
	 */
	public void print(String filename) {
		// FILL IN CODE
		StringBuilder resSb;
		FlightNode currHead = this.head;
		FlightNode currTail = this.tail;
		for (int i = 0; i < this.height - 1; i++) {
			currHead = currHead.up;
			currTail = currTail.up;
		}
		FlightNode curr;
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < this.height - 1; i++) {
			resSb = new StringBuilder();
			curr = currHead.next;
			while (!curr.getKey().getOrigin().equals("ZZZ")) {
				resSb.append(curr.toString());
				resSb.append(", ");
			}
			resSb.deleteCharAt(resSb.length() - 1);
			resSb.deleteCharAt(resSb.length() - 1);
			resSb.append("\n");
			try {
				writer.write(resSb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			currHead = currHead.down;
		}
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		int timeH = Integer.valueOf(key.getTime().substring(0, 2)) ;
		int endH = timeH + timeFrame;
		int startH = timeH - timeFrame;
		String startTstr;
		String endTstr;
		if (startH < 0) {
			startH = 0;
			startTstr = "00:00";
			if (endH < 10) {
				endTstr = new String("0" + String.valueOf(endH) + "59");
			} else {
				endTstr = new String(String.valueOf(endH) + "59");
			}
		} else {
			if (startH < 10) {
				startTstr = new String("0" + String.valueOf(startH) + "00");
			} else {
				startTstr = new String(String.valueOf(startH) + "00");
			}
			if (endH < 10) {
				endTstr = new String("0" + String.valueOf(endH) + "59");
			} else {
				endTstr = new String(String.valueOf(endH) + "59");
			}
		}

		FlightKey startKey = new FlightKey(key.getOrigin(), key.getDest(), key.getDate(), startTstr);
		FlightKey endKey = new FlightKey(key.getOrigin(), key.getDest(), key.getDate(), endTstr);
		List<FlightNode> resP = predecessors(key, 2);
		List<FlightNode> resS = successors(key);
		for (FlightNode n : resP) {
			if (n.getKey().compareTo(startKey) >= 0) {
				resFlights.add(n);
			}
		}
		FlightNode actNode = findNode(key);
		if (!actNode.getKey().getOrigin().equals("AAA")) {
			resFlights.add(actNode);
		}
		for (FlightNode n : resS) {
			if (n.getKey().compareTo(endKey) <= 0) {
				resFlights.add(n);
			}
		}
		return resFlights;












//		String keyTime = key.getTime();
//		int upInt = Integer.valueOf(keyTime.substring(0, 2)) + 2;
//		int downInt = upInt - 4;
//		if (downInt < 0) {
//			downInt = 0;
//		}
//		String up;
//		String down;
//		if (upInt < 10) {
//			up = String.valueOf(0) + String.valueOf(upInt) + keyTime.substring(2);
//		} else {
//			up = String.valueOf(upInt) + keyTime.substring(2);
//		}
//		if (downInt < 10) {
//			down = String.valueOf(0) + String.valueOf(downInt) + keyTime.substring(2);
//		} else {
//			down = String.valueOf(downInt) + keyTime.substring(2);
//		}
//		FlightKey upKey = new FlightKey(key.getOrigin(), key.getDest(), key.getDate(), up);
//		FlightKey downKey = new FlightKey(key.getOrigin(), key.getDest(), key.getDate(), down);
//
//		FlightNode end;
//		if (this.size > 0 && this.tail.prev.getKey().compareTo(upKey) <= 0) {
//			end = this.tail.prev;
//		}
//		FlightNode curr = this.head;
//		for (int i = 0; i < this.height - 1; i++) {
//			curr = curr.up;
//		}
//		int currLevel = this.height;
//		Boolean isElement = false;
//		outer:
//		while (currLevel > 1) {
//			while (!curr.next.getKey().getOrigin().equals("ZZZ")
//					&& curr.next.getKey().compareTo(upKey) <= 0) {
////				if (curr.next.getKey().getOrigin().equals(upKey.getOrigin())
////						&& curr.next.getKey().getDest().equals(upKey.getDest())
////						&& curr.next.getKey().getDate().equals(upKey.getDate())) {
////
////				}
//				if (curr.next.getKey().compareTo(upKey) == 0) {
//					curr = curr.next;
//					break outer;
//				}
//				curr = curr.next;
//			}
//			FlightNode currPrev = curr;
//			curr = curr.down;
//			currLevel--;
//		}
//
//		if (isElement) {
//
//			for (int i = 0; i < currLevel - 1; i++) {
//				curr = curr.down;
//			}
//			end = curr;
//		} else {
//			end = curr;
//		}
//		if (!end.getKey().getOrigin().equals(upKey.getOrigin())
//				|| !end.getKey().getDest().equals(upKey.getDest())
//				|| !end.getKey().getDate().equals(upKey.getDate())) {
//			return resFlights;
//		}
//
//		FlightNode start;
//		if (this.size > 0 && this.head.next.getKey().compareTo(downKey) >= 0) {
//			start = this.head.next;
//		}
//
//		curr = this.tail;
//		for (int i = 0; i < this.height - 1; i++) {
//			curr = curr.up;
//		}
//		currLevel = this.height;
//		isElement = false;
//		outer:
//		while (currLevel > 1) {
//			while (!curr.prev.getKey().getOrigin().equals("AAA")
//					&& curr.prev.getKey().compareTo(downKey) >= 0) {
//				if (curr.prev.getKey().compareTo(downKey) == 0) {
//					curr = curr.prev;
//					break outer;
//				}
//				curr = curr.prev;
//			}
//			curr = curr.down;
//			currLevel--;
//		}
//		if (isElement) {
//
//			for (int i = 0; i < currLevel - 1; i++) {
//				curr = curr.down;
//			}
//			start = curr;
//
//		} else {
//			start = curr;
//		}
//		if (!start.getKey().getOrigin().equals(downKey.getOrigin())
//				|| !start.getKey().getDest().equals(downKey.getDest())
//				|| !start.getKey().getDate().equals(downKey.getDate())) {
//			return resFlights;
//		}
//
//		curr = start;
//		while (!curr.equals(end)) {
//			resFlights.add(curr);
//			curr = curr.next;
//		}
//		resFlights.add(end);
//		return resFlights;
	}

}
