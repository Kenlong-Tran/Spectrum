import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;

public class Spectrum {
	public static List<Node> connections;
	public static HashMap<String, Node> map;
	public static ArrayList<Node> queue;
	public static int[] table;
	public static int numNode = -1;

	static class Node {
		@SuppressWarnings("unused")
		private String key;
		private ArrayList<Node> direction;
		private int keyValue;

		Node(String key) {
			this.key = key;
			this.direction = new ArrayList<Node>();
			this.keyValue = numNode;
		}

		public void addDirection(Node next) {
			this.direction.add(next);
		}
	}

	public static void add(String target1) {
		if (!map.containsKey(target1)) {
			numNode = numNode + 1;
			Node node = new Node(target1);
			map.put(target1, node);
		}
	}

	public static void add(String target1, String target2) {
		if (target1.equals(target2)) {
			if (!map.containsKey(target1)) {
				add(target1);
			}
		} else {
			if (!map.containsKey(target1)) {
				add(target1);
			}
			if (!map.containsKey(target2)) {
				add(target2);
			}
			Node first = map.get(target1);
			Node second = map.get(target2);
			boolean inside = first.direction.contains(second);
			if (!inside) {
				first.addDirection(second);
				second.addDirection(first);
			}
		}
	}

	public static void connections(String target1) {
		if (!map.containsKey(target1)) {
			System.out.println("target does not exist");
		} else if (map.get(target1).direction.isEmpty()) {
			System.out.println("no connections");
		} else {
			queue = new ArrayList<Node>();
			table = new int[map.size()];
			for (int i = 0; i < map.size(); i++) {
				table[i] = Integer.MAX_VALUE;
			}
			Node source = map.get(target1);
			queue.add(source);
			table[source.keyValue] = -1;
			while (!queue.isEmpty()) {
				Node v = queue.remove(0);
				ArrayList<Node> edges = v.direction;
				for (int i = 0; i < edges.size(); i++) {
					Node w = edges.get(i);
					if (table[w.keyValue] == Integer.MAX_VALUE) {
						table[w.keyValue] = table[v.keyValue] + 1;
						queue.add(w);
					}
				}
			}
			HashMap<Integer, Integer> answer = new HashMap<Integer, Integer>();
			for (int i = 0; i < map.size(); i++) {
				if (!(table[i] == -1) && !(table[i] == Integer.MAX_VALUE)) {
					answer.putIfAbsent(table[i], 0);
					answer.replace(table[i], answer.get(table[i]) + 1);
				}
			}
			int index = 0;
			while (!answer.isEmpty()) {
				System.out.println((index + 1) + ": " + answer.get(index));
				answer.remove(index);
				index = index + 1;
			}
		}
	}

	public static Integer associated(String target1, String target2) {
		connections = new ArrayList<Node>();
		int[] table = new int[numNode + 1];
		for (int i = 0; i <= numNode; i++) {
			table[i] = Integer.MAX_VALUE;
		}
		Node now = map.get(target1);
		table[now.keyValue] = -1;
		Node want = map.get(target2);
		queue = new ArrayList<Node>();
		boolean found = false;
		queue.add(now);
		connections.add(now);
		while (!found && !queue.isEmpty()) {
			if (found) {
				break;
			}
			Node current = queue.remove(0);
			ArrayList<Node> edges = current.direction;
			for (int i = 0; i < edges.size(); i++) {
				Node w = edges.get(i);
				if (!connections.contains(w)) {
					queue.add(w);
					connections.add(w);
					table[w.keyValue] = table[current.keyValue] + 1;
					if (want.equals(w)) {
						found = true;
						break;
					}
				}
				if (found) {
					break;
				}
			}
		}
		return table[want.keyValue];
	}

	public static void main(String[] args) {
		Scanner Scan = new Scanner(System.in);
		int Cases = 1;
		System.out.println("Case " + Cases + ":");
		map = new HashMap<String, Node>();
		while (Scan.hasNextLine()) {
			String command = Scan.nextLine();
			String[] values = command.split(" ");
			int size = values.length;
			String function = values[0];
			if (function.equals("reset")) {
				numNode = -1;
				Cases = Cases + 1;
				System.out.println("----------");
				System.out.println("Case " + Cases + ":");
				map = new HashMap<String, Node>();
			} else if (size == 2) {
				switch (function) {
				case "add":
					add(values[1]);
					break;
				case "connections":
					connections(values[1]);
					break;
				}
			} else {
				switch (function) {
				case "add":
					add(values[1], values[2]);
					break;
				case "associated":
					if (!map.containsKey(values[1]) || !map.containsKey(values[2]))
					{
						throw new IllegalArgumentException("At least one of the values do not exist.");
					}
					int value = associated(values[1], values[2]);
					if (value == Integer.MAX_VALUE) {
						System.out.println("no");
					} else {
						System.out.println("yes: " + (value+1));
					}
					break;
					
				}
			}
		}
		System.out.println("----------");
		Scan.close();
	}
}
