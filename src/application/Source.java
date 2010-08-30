package application;

public enum Source {
	
	BUS {
		public String toString() {
			return "buses";
		}
	},
	MESSAGE {
		public String toString() {
			return "messages";
		}
	},
	ROUTE {
		public String toString() {
			return "routes";
		};
	}
}
