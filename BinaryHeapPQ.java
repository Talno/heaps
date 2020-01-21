public class BinaryHeapPQ<T, P extends Comparable<P>> implements PriorityQueue<T,P>
{
		private int capacity;//max capacity
		private int size;//number of elements in array
		private BinaryHeapPQ<T,P>.Element [] heap;//queue/heap
    //constructor
    public BinaryHeapPQ()
    {
			capacity = 10;
			size = 1; //0 index has nothing for child/parent calculation purposes
			heap = new BinaryHeapPQ.Element[capacity];
    }

		/**
    *		Add the given value to queue based on priority
		*
		*		@param value  the value of the element
		*		@param priority  value indicating the ordering in the queue
		**/
    public void enqueue(T value, P priority)
    {
			if(size == capacity)
				increaseCapacity();
			
			//percolating upwards
			Element e = new Element(value, priority);
			int loc = size++;
			//compare value percolating upwards to parent and switch if higher priority
			while(loc / 2 > 0 && (e.priority).compareTo(heap[loc / 2].priority) < 0)
			{
				heap[loc] = heap[loc / 2];
				loc /= 2;
			}
			heap[loc] = e;
    }
		/**
    *		Doubles capacity of the queue array
		**/
		private void increaseCapacity()
		{
			capacity *= 2;
			BinaryHeapPQ<T,P>.Element temp[] = new BinaryHeapPQ.Element[capacity];
			
			for(int i = 1; i < size; i++)
			{
				temp[i] = heap[i];
			}
			
			heap = temp;
		}

    /**
    *		Removes and returns value with the highest priority(smallest priority amount)
		*
		*		@return the value with highest priority
		**/
    public T dequeue()
    {
			if(size() == 0)//no more elements
				return null;
			
			//swap root with last element
			Element e = heap[1];
			Element percDown = heap[--size];

			//percolate downwards
			int parent = 1;
			int child = parent * 2;
			while(child < size)
			{
				//if it has a right child, compare left and right for to find greater priority
				if(child != size - 1 && (heap[child].priority).compareTo((heap[child + 1].priority)) > 0)
					child++;
				//compare highest priority child to element being percolated
				if((heap[child].priority).compareTo(percDown.priority) < 0)//child has higher priority
					heap[parent] = heap[child];
				else//percDown has higher priority, queue is in order
					break;
				
				parent = child;
				child *= 2;
			}
			heap[parent] = percDown;
			
      return e.data;
    }

    /**
    *		Returns value with the highest priority(smallest priority amount)
		*
		*		@return the value with highest priority
		**/
    public T peek()
    {
			if(size() == 0) return null;
      return heap[1].data;
    }

    /**
    *		Returns priority of the value with the highest priority(smallest priority amount)
		*
		*		@return the priority of the value with the highest priority
		**/
    public P peekPriority()
    {
			if(size() == 0) return null;
      return heap[1].priority;
    }

    /**
    *		Clears the queue of all values
		**/
    public void clear()
    {
			size = 1;
    }


		/**
    *		Combines this queue with another queue and returns the new queue
		*
		*		@param other  the other queue to combine with
		*		@return the new queue containing all values from the two queues
		**/
    @SuppressWarnings("unchecked")
    protected BinaryHeapPQ merge(BinaryHeapPQ other)
    {
				BinaryHeapPQ newHeap = new BinaryHeapPQ();
				
				//add both heaps into new heap
				int otherSize =  other.size() + 1;//offset by 1 for null value at 0
				
				for(int i = 1; i < otherSize; i++)//starts at 1 as 0 index is null
				{
					newHeap.enqueue(other.heap[i].data, other.heap[i].priority);
				}
				for(int i = 1; i < size; i++)
				{
					newHeap.enqueue(heap[i].data, heap[i].priority);
				}
        return newHeap;
    }


    /**
    *		Returns the size of the queue
		*
		*		@return the size of the queue
		**/
    public int size()
    {
        return size - 1;//offset due to empty 0 index
    }
		
		//Element class that stores priority and value
		private class Element
		{
			T data;//value
			P priority;
			
			public Element(T t, P p)
			{
				data = t;
				priority = p;
			}
		}
		
    //merge two priority queues into one and return the merged priority queue
    public PriorityQueue  merge(PriorityQueue other)
    {
      return merge((BinaryHeapPQ)other);
    }
}
