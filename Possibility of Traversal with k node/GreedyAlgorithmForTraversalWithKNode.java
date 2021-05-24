import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class GreedyAlgorithmForTraversalWithKNode {

	static int k = 3;
	static int count = 0;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String s= "(2,{(3,{(18),(19)}),(6,{(7),(8,{(4),(5)}),(9)}),(10,{(11,{(12),(13)}),(14,{(15),(16),(17)})})})";
		
		JSONObject j = prepareJsonObjectFromString(s);		
		Node n = new Node();
		for (String key : j.keySet()) {			
			n.setData(Integer.parseInt(key));
			n.setNodeList(prepareSpanningTree(j.get(key)));			
		}		
		depthFirstSearch(n);
		System.out.println("Maximum number of person:" + count);
	}
	
	
	
	
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		Node n1 = new Node(5, null);
//		Node n2= new Node(6, null);
//		List<Node> list1 = new ArrayList<Node>();
//		list1.add(n1);
//		list1.add(n2);
//		
//		List<Node> list2 = new ArrayList<Node>();
//		Node n3 = new Node(55, null);
//		Node n4= new Node(66, null);
//		list2.add(n3);
//		list2.add(n4);
//		
//		
//		Node n5 = new Node(12, null);
//		Node n6= new Node(13, list2);
//		
//		List<Node> list3= new ArrayList<Node>();
//		list3.add(n5);
//		list3.add(n6);
//		
//		Node n7 = new Node(2, list1);
//		Node n8 = new Node(11, list3);
//		
//		List<Node> list4= new ArrayList<Node>();
//		list4.add(n7);
//		list4.add(n8);
//		
//		Node n9 = new Node(8, list4);		
//		List<Node> list5 = new ArrayList<Node>();
//		list5.add(n9);		
//		
//		
//		Node n10= new Node(10, list5);		
//		Node n11 = new Node(20, null);
//		Node n12 = new Node(21, null);
//		
//		
//		List<Node> list6 = new ArrayList<Node>();
//		list6.add(n11);
//		list6.add(n12);
//		
//		Node n13 = new Node(22, list6);		
//		
//		
//		Node n14 = new Node(25, null);
//		List<Node> list8 = new ArrayList<Node>();
//		list8.add(n14);
//		
//		Node n15 = new Node(26, list8);
//		List<Node> list9= new ArrayList<Node>();
//		list9.add(n15);
//		
//		Node n16 = new Node(27, list9);
//		List<Node> list10 = new ArrayList<Node>();
//		list10.add(n16);
//		
//		Node n17 = new Node(28, list10);
//		
//		List<Node> list7= new ArrayList<Node>();
//		list7.add(n13);
//		list7.add(n10);
//		list7.add(n17);
//		
//		System.out.println("List:"+ list7.toString());
//		Node n18 = new Node(23, list7);
//		
//		depthFirstSearch(n18);
//		System.out.println("Maximum number of person:" + count);
//		
//
//	}
	
	private static void depthFirstSearch(Node n) {
		if(n.getNodeList() != null) {
			for (Node nodeElement : n.getNodeList()) {
				depthFirstSearch(nodeElement);				
			}
			n.depth = depthOfNode(n, 0);
			System.out.println("element parent: "+n.data+" child depth: " +n.depth);
			if(n.depth >= k -1) {
				System.out.println("node Selected:" + n.data);
				if(isValidNodeForTraversal(n, k-1)) {
					System.out.println("is valid:" + n.data + "true");
					System.out.println("taken: " + n.getData());
					n.setTaken(true);
					count ++;
				} else {
					System.out.println("is valid:" + n.data + "false");
				}
			}
		} else  {
			System.out.println("element: " +n.data);
		}
	}
	

	private static int depthOfNode(Node n, int i) {
		i++;
		if(n.getNodeList() != null) {	
			int maxDepth = 0;
			for (Node nodeElement : n.getNodeList()) {				
				maxDepth = maxDepth > 1+ depthOfNode(nodeElement,i) ? maxDepth : 1+ depthOfNode(nodeElement,i);
			}	
			return maxDepth;
		} 
		return 0;
	}
	
	private static boolean isValidNodeForTraversal(Node n, int k) {
		System.out.println("validation for " + n.getData());
		if(n.getNodeList() != null) {	
			for (Node nodeElement : n.getNodeList()) {		
				System.out.println("validation check for " + nodeElement.getData() + "k=" + k);
				if(!nodeElement.isTaken() && nodeElement.depth >= k-1) {
					if(nodeElement.getNodeList() != null) {
						k--;
						System.out.println("taken: " + nodeElement.getData());
						nodeElement.setTaken(Boolean.TRUE);
						for (Node nodeChildElement : nodeElement.getNodeList()) {							
							return isValidNodeForTraversal(nodeChildElement, k);
						}
					}
				}
			}	
		} else {
			System.out.println("reach at end for validation: "+ n.data);
			if(!n.isTaken()) {
				System.out.println("taken: " + n.getData());
				n.setTaken(Boolean.TRUE);
				return true;
			}			
		}
		return false;
		
	}
	
	public static List<Node> prepareSpanningTree(Object list) {		
		JSONArray listOfNode = new JSONArray(list.toString());
		List<Node> result = new ArrayList<Node>();
		for (Object object : listOfNode) {
			JSONObject jsoneObj = new JSONObject(object.toString());
			Node n = new Node();
			for (String key : jsoneObj.keySet()) {
				n.setData(Integer.parseInt(key));
				if(jsoneObj.get(key) != null && !jsoneObj.get(key).equals("")) {
					n.setNodeList(prepareSpanningTree(jsoneObj.get(key)));
				} else {
					n.setNodeList(null);
				}
			}
			result.add(n);
		}
		return result;
	}
	
	public static JSONObject prepareJsonObjectFromString(String s) {
		s= s.replace(",{", ":[");
		s= s.replace("}", "]");
		s= s.replace("(", "{");
		s= s.replace(")", "}");
		System.out.println(s);
		s= s.replaceAll("[{]([0-9]+)[}]", "{$1:''}");
		System.out.println(s);
		JSONObject j = new JSONObject(s);
		return j;
	}

}

class Node {
	int data;
	List<Node> nodeList = new ArrayList<Node>();
	int depth;
	boolean taken;
	
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	public List<Node> getNodeList() {
		return nodeList;
	}
	public void setNodeList(List<Node> nodeList) {
		this.nodeList = nodeList;
	}	
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}	
	public boolean isTaken() {
		return taken;
	}
	public void setTaken(boolean taken) {
		this.taken = taken;
	}
	public Node(int d , List<Node> list) {
		// TODO Auto-generated constructor stub
		this.data = d;
		this.nodeList = list;
	}
	public Node() {
		
	}
}
