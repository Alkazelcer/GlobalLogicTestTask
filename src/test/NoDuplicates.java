import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class NoDuplicates<E> extends PriorityQueue<E> 
{
	public NoDuplicates(int initialCapacity, Comparator<? super E> comparator) {
		super(initialCapacity, comparator);
	}
	
    public boolean add(E e) 
    {
        boolean isAdded = false;
        if(!super.contains(e))
        {
            isAdded = super.add(e);
        }
        return isAdded;
    }
}