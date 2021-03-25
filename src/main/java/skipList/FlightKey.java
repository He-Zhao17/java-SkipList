package skipList;

/**
 * Represents the key in the FlightNode. Stores origin, destination, date and
 * time. Implements Comparable<FlightKey>.
 */
public class FlightKey implements Comparable<FlightKey> {
	// Each key is a tuple: origin, destination, date, time
	private String origin;
	private String dest;
	private String date;
	private String time;


	/**
     *  FlightKey constructor
	 * @param or origin
	 * @param dest destination
	 * @param date date
	 * @param time time
	 */
    public FlightKey(String or, String dest, String date, String time) {
		// FILL IN CODE
		this.origin = or;
		this.dest = dest;
		this.date = date;
		this.time = time;

	}

	/**
     * FlightKey - copy constructor
	 * @param other the other FlightKey
	 */
	public FlightKey(FlightKey other) {
		// FILL IN CODE
		this.origin = other.origin;
		this.date = other.date;
		this.dest = other.dest;
		this.time = other.time;

	}

	// FILL IN CODE: Write getters for origin, destination, date and time


	/**
     * Compares a given flight key with the one given as a parameter.
	 * @param other
     * @return -1, if this key is < other, > -1 if the opposite, and 0 if equal.  </>
	 */
	public int compareTo(FlightKey other) {
		// FILL IN CODE
		if (this.origin.compareTo(other.origin) > 0) {
			return 1;
		} else if (this.origin.compareTo(other.origin) < 0){
			return -1;
		} else {
			if (this.dest.compareTo(other.dest) > 0) {
				return 1;
			} else if(this.dest.compareTo(other.dest) < 0) {
				return -1;
			} else {
				StringBuilder thisDate = new StringBuilder(this.date.substring(6));
				thisDate.append(this.date.substring(0,2));
				thisDate.append(this.date.substring(3,5));
				String thisDateNum = thisDate.toString();
				StringBuilder otherDate = new StringBuilder(other.date.substring(6));
				otherDate.append(other.date.substring(0,2));
				otherDate.append(other.date.substring(3,5));
				String otherDateNum = otherDate.toString();
				if(thisDateNum.compareTo(otherDateNum) > 0) {
					return 1;
				} else if (thisDateNum.compareTo(otherDateNum) < 0) {
					return -1;
				} else {
					StringBuilder thisTimeStrB = new StringBuilder(this.time.substring(0,2));
					thisTimeStrB.append(this.time.substring(3));
					StringBuilder otherTimeStrB = new StringBuilder(other.time.substring(0,2));
					otherTimeStrB.append(other.time.substring(3));
					String thisTimeStr = thisTimeStrB.toString();
					String otherTimeStr = otherTimeStrB.toString();
					if (thisTimeStr.compareTo(otherTimeStr) > 0) {
						return 1;
					} else if (thisTimeStr.compareTo(otherTimeStr) < 0) {
						return -1;
					} else {
						return 0;
					}
				}
			}
		}
		//return 0; // don't forget to change it
	}

	/**
     * Returns a string representation of the key
	 * @return String
	 */
	public String toString() {
		// FILL IN CODE
		StringBuilder resStrB = new StringBuilder(this.getOrigin());
		resStrB.append(" ");
		resStrB.append(this.getDest());
		resStrB.append(" ");
		resStrB.append(this.getDate());
		resStrB.append(" ");
		resStrB.append(this.getTime());
		return resStrB.toString();
	}

	/**
	 * getOrigin
	 * @return origin as a String
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * getDest
	 * @return destination as a String
	 */
	public String getDest() {
		return dest;
	}

	/**
	 * getDate
	 * @return date as a String (format:  "01/03/2019")
	 */
	public String getDate() {
		return date;
	}

	/**
	 * getTime
	 * @return time as a String (format: "16:00")
	 */
	public String getTime() {
		return time;
	}

	public boolean equals(FlightKey other) {
		if (this.getOrigin().equals(other.getOrigin())
				&& this.getDest().equals(other.getDest())
				&& this.getDate().equals(other.getDate())
				&& this.getTime().equals(other.getTime())) {
			return true;
		} else {
			return false;
		}
	}
}
