/* MergeSort.java
   CSC 225 - Summer 2015
   Assignment 2 - Template for Merge Sort (Linked List version)
      
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java MergeSort
	
   To conveniently test the algorithm with a large input, create a 
   text file containing space-separated integer values and run the program with
	java MergeSort file.txt
   where file.txt is replaced by the name of the text file.

   NOTE: For large input files, the depth of recursion may cause the Java
   runtime environment to run out of stack space. To remedy this, run java
   with the extra parameter "-Xss64m" (which increases the stack size to 64
   megabytes). For example: java -Xss64m MergeSort input_data.txt
   
   B. Bird - 03/22/2015
*/

import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.io.File;

//Do not change the name of the MergeSort class
public class MergeSort{
	/* MergeSort(head)
		Given a reference to the head of a list, sort the contents of the list 
		with merge sort and return a reference to the sorted list. Your 
		implementation may create a new list or modify the input list.
		
		To achieve full marks, you may not use any iterative loop constructs
		(including for loops, while	loops or do-while loops); all iteration
		must be accomplished with recursion. 
		
		You may add additional functions (or classes) if needed, but the entire program must be
		contained in this file. 

		Do not change the function signature (name/parameters).
	*/
	
	public static ListNode addBack(ListNode head, ListNode value){
		ListNode sorted;
		if(value == null){
			return head;
		}
		if(head == null){
			return value;

		}
		if (head.value<value.value){
			sorted = new ListNode(head.value, addBack(head.next,value));
			//sorted.next = addBack(head.next, value);
			//return sorted;
		}
		else{
			sorted = new ListNode(value.value, addBack(head,value.next));
			//sorted.next = addBack(head,value.next);
			//return sorted;

		}
		return sorted;
		//rfeturn addBack(head.next, value);

	}



	
	public static ListNode findMid(ListNode head,ListNode end){
		ListNode mid = head;
		
		
		if (end == null||end.next == null||end.next.next==null){ //end.next.next is required or else null pointers would happen on a few cases.
			return mid;														// also size n  = 4, middle pointer would be too far.
		}
		end = end.next;
		return findMid(head.next,end.next);			// recursively increment back pointer by 2, front by one making front pointer be at middle.

	}

	public static ListNode lastNodeTraverser(ListNode head){	//thought i would need this when i first started
		if(head.next == null || head == null){						// pretty much created findMid and addback off this and original add to back function
			return head;	
		}
		return lastNodeTraverser(head.next);//recursively traverses until final node

	}

/*
public static ListNode listMerge(ListNode head, ListNode tail){
	ListNode sorted;
	if(tail == null){
		return head;
	}
	if(head == null){
		 return tail;
	}

	if(head.value<tail.value){
		sorted = head;
		sorted.next = listMerge(head.next, tail);
	}
	else{
		sorted = tail;
		sorted.next = listMerge(head, tail.next);

	}

	return sorted;




}
*/



	public static ListNode MergeSort(ListNode head){
		
		if(listLength(head)<=1 || head ==null ){

			return head;
		}
		
		ListNode middle = findMid(head,head); 
		ListNode tail = middle.next; //break from middle pointer.next to make tail		
		middle.next = null; //middle pointer edits head linked list, breaking it into head nodes
		
		ListNode left =  MergeSort(head); //recursively create lists of size one
		ListNode right = MergeSort(tail); //by continuously splitting in half by findMid



		//addBack(head,end);

		//addBack(head, end); //addback WORKS
		//return end;
		return addBack(left,right);   //originally was listMerge, edited my add to back function to create a sort and merge function
	}
	
	
	/* ListNode class */
	/* Do not change, add or remove any aspect of the class definition below.
	   If any of the contents of this class are changed, your submission will
	   not be marked.
	   
	   The members of the class should be accessed directly (e.g. node.next, 
	   node.value), since no get/set methods exist.
	   
	   However, you may create a subclass of this class if you want to extend
	   its functionality. Ensure that your subclass is contained in MergeSort.java
	   with the rest of your code (since you may only submit one file).
	*/
	public static class ListNode{
		int value;
		ListNode next;
		public ListNode(int value){
			this.value = value;
			this.next = null;
		}
		public ListNode(int value, ListNode next){
			this.value = value;
			this.next = next;
		}
	}
	
	/* Testing code */
	
	/* listLength(node)
	   Compute the length of the list starting at the provided node.
	*/
	public static int listLength(ListNode node){
		if (node == null)
			return 0;
		return 1 + listLength(node.next);
	}
	
	/* testSorted(node)
	   Returns true if all elements of the list starting at the provided node
	   are in ascending order.
	*/
	public static boolean testSorted(ListNode node){
		//An empty list is always considered sorted.
		if (node == null)
			return true;
		//A list with one element is always considered sorted.
		if (node.next == null)
			return true;
		//Test whether the first element is greater than the second element
		if (node.value > node.next.value){
			//If so, the list is not sorted.
			return false;
		}else{
			//Otherwise, test whether the remaining elements are sorted and
			//return the result.
			return testSorted(node.next);
		}
	}
	
	/* printList(node)
	   Print all list elements starting at the provided node.
	*/
	public static void printList(ListNode node){
		if (node == null)
			System.out.println();
		else{
			System.out.print(node.value + " ");
			printList(node.next);
		}
	}
	
	/* readInput(inputScanner)
	   Read integer values from the provided scanner into a linked list.
	   Each recursive call reads one value, and recursion ends when the user
	   enters a negative value or the input file ends.
	*/
	public static ListNode readInput(Scanner inputScanner){
		//If no input is left, return an empty list (i.e. null)
		if (!inputScanner.hasNextInt())
			return null;
		int v = inputScanner.nextInt();
		//If the user entered a negative value, return an empty list.
		if (v < 0)
			return null;
		//If the user entered a valid input value, create a list node for it.
		ListNode node = new ListNode(v);
		//Scan for more values recursively, and set the returned list of values
		//to follow the node we just created.
		node.next = readInput(inputScanner);
		//Return the created list.
		return node;
	}

	/* main()
	   Contains code to test the MergeSort function. Nothing in this function 
	   will be marked. You are free to change the provided code to test your 
	   implementation, but only the contents of the MergeSort() function above 
	   will be considered during marking.
	*/
	public static void main(String[] args){
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
		}
		ListNode inputListHead = readInput(s);
		
		int inputLength = listLength(inputListHead);
		System.out.printf("Read %d values.\n",inputLength);
		
		
		long startTime = System.currentTimeMillis();
		
		ListNode sortedListHead = MergeSort(inputListHead);
		
		long endTime = System.currentTimeMillis();
		
		double totalTimeSeconds = (endTime-startTime)/1000.0;
		
		//Compute the length of the output list
		int sortedListLength = listLength(sortedListHead);
		
		//Don't print out the values if there are more than 100 of them
		if (sortedListLength <= 100){
			System.out.println("Sorted values:");
			printList(sortedListHead);
		}
		
		//Check whether the sort was successful
		boolean isSorted = testSorted(sortedListHead);
		
		System.out.printf("List %s sorted.\n",isSorted? "is":"is not");
		System.out.printf("Total Time (seconds): %.2f\n",totalTimeSeconds);
	}
}
