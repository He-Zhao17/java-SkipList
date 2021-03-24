package skipList;

/**
 * The class that represents a node in a flight skip list. 
 * Contains the key of type FlightKey and the data of type FlightData. 
 * Also stores the following pointers to FlightNode(s): next, down, prev and up.
 */
public class FlightNode {

	// FILL IN CODE, declare instance variables (make them private)
	private FlightKey fKey;
	private FlightData fData;
	public FlightNode next;
	public FlightNode prev;
	public FlightNode down;
	public FlightNode up;

	/**
     * Copy constructor for FlightNode
	 * @param node FlightNode
	 */
	public FlightNode(FlightNode node) {
		// FILL IN CODE
		this.fKey = node.fKey;
		this.fData = node.fData;
//		this.next = node.next;
//		this.down = node.down;
//		this.prev = node.prev;
//		this.up	= node.up;
//		this.up = null;
//		this.down = null;
//		this.prev = null;
//		this.next = null;
	}

	/**
     * FlightNode Constructor
	 * @param key flight key
	 * @param data fight daya
	 */
	public FlightNode(FlightKey key, FlightData data) {
		// FILL IN CODE
		this.fKey = key;
		this.fData = data;
//		this.up = null;
//		this.down = null;
//		this.prev = null;
//		this.next = null;
	}

	// FILL IN CODE: write getters and setters for all private variables
	/**
     * A getter for the key
	 * @return key
	 */
	public FlightKey getKey() {
		// FILL IN CODE
		return this.fKey; // don't forget to change it
	}

	/**
	 * A getter for the data
	 * @return data
	 */
	public FlightData getData() {
		return this.fData;
	}

//	/**
//	 * A getter for the pointer of the next node
//	 * @retrun next
//	 */
//	public FlightNode getNext() {
//		return this.next;
//	}
//
//	/**
//	 * A getter for the pointer of the previous node
//	 * @retrun prev
//	 */
//	public FlightNode getPrev() {
//		return this.prev;
//	}
//
//	/**
//	 * A getter for the pointer of the up node
//	 * @retrun up
//	 */
//	public FlightNode getUp() {
//		return this.up;
//	}
//
//	/**
//	 * A getter for the pointer of the down node
//	 * @retrun down
//	 */
//	public FlightNode getDown() {
//		return this.down;
//	}

	/**
	 * Returns the string representing the FlightNode in FlightList.toString().
	 */
	public String toStringInSkipList() {
		return "(" + this.fKey.getOrigin() + ", " + this.fKey.getDest() + ", " + this.fKey.getDate().substring(0, 5)
				+ ", " + this.fKey.getTime() + ")";
	}

//	/**
//	 * Returns the string representing the FlightNode.
//	 */
//	public String toStringInSkipList() {
//		return "(" + this.fKey.getOrigin() + ", " + this.fKey.getDest() + ", " + this.fKey.getDate()
//				+ ", " + this.fKey.getTime() + this.getData().getFlightNumber() + ", " + this.getData().getPrice() + ")";
//	}

	public boolean equals(FlightNode other) {
		if (this.getData().equals(other.getData()) && this.getKey().equals(other.getKey())) {
			return true;
		} else {
			return false;
		}
	}

}
