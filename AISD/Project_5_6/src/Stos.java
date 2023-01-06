
public class Stos<Item>
{
	private Node first = null;
	private int stackSize = 0;
	private class Node 
	{
		Item item;
        Node next;
	}
	public boolean isEmpty() 
	{ 
	return first == null; 
	}
	public void push(Item item) 
	{
	Node oldfirst = first;
	first = new Node();
	first.item = item;
	first.next = oldfirst;
	stackSize++;
	}
	public Item pop() 
	{
	Item item = first.item;
	first = first.next;
	stackSize--;
	return item;
	}
	public void showQueue() 
    {
        System.out.println("Ilo�� element�w w kolejce: "+this.size());
    }
    public int size() 
    {
        return stackSize;
    }
	 void print() 
     {
     	for(Node x=first; x!=null; x=x.next)
 		{
 			System.out.print(x.item);
 		}
     }
}

