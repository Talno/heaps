public class KaryHeapPQ<T, P extends Comparable<P>> implements PriorityQueue<T,P>
{
		
		private int K;//number of children per node
		private int capacity;//max capacity
		private int size;//number of elements in array
		private KaryHeapPQ<T,P>.Element heap[];//queue/heap
    //constructor
    public KaryHeapPQ(int K)
    {
			this.K = K;
			capacity = 10; //could use a better scaling capacity for a larger K
			size = 1;//offset by 1 for calculation of child/parent
			heap = new KaryHeapPQ.Element[capacity];
    }

		/**
    *		Doubles capacity of the queue array
		**/
		private void increaseCapacity()
		{
			capacity *= 2;
			KaryHeapPQ<T,P>.Element temp[] = new KaryHeapPQ.Element[capacity];
			
			for(int i = 1; i < size; i++)
			{
				temp[i] = heap[i];
			}
			
			heap = temp;
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
			int child = size++;
			int parent = (child + K - 2) / K;
			//compare value percolting upwards to parent and swap if higher priority
			while(parent  > 0 && (e.priority).compareTo(heap[parent].priority) < 0)
			{
				heap[child] = heap[parent];
				child = parent;
				parent = (child + K - 2) / K;
			}
			//might not be right check here first if something goes wrong
			heap[child] = e;
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
			int child = parent * K - K + 2;//calculates the index of the first(smallest index) child
			while(child < size)
			{
				
				int lastChild = child + K;//index after last child
				int firstChild = child;
				if(lastChild > size)//might not have all K children
					lastChild = size;
				//compare all children for highest priority child
				for(int i = 0; i < lastChild-firstChild; i++)
				{
					if((heap[child].priority).compareTo((heap[firstChild + i].priority)) > 0)
						child = firstChild + i;
				}
				
				//compare highest prio child to percDown element
				if((heap[child].priority).compareTo(percDown.priority) < 0)//child has higher priority
					heap[parent] = heap[child];
				else//percDown has higher priority, queue is in order
					break;
				
				parent = child;
				child = parent * K - K + 2;
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
    public KaryHeapPQ  merge(KaryHeapPQ other)
    {
        KaryHeapPQ newHeap = new KaryHeapPQ();
				//add both heaps into new heap
				int otherSize =  other.size() + 1;//offset by 1 for null value at 0
				
				for(int i = 1; i < otherSize; i++)
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
        return size - 1;
    }
		
		//Element class that stores priority and value
		private class Element
		{
			T data;
			P priority;
			
			public Element(T t, P p)
			{
				data = t;
				priority = p;
			}
		}

    public KaryHeapPQ()
    {
      this(7);
    }

    //merge two priority queues into one and return the merged priority queue
    public PriorityQueue  merge(PriorityQueue other)
    {
      return merge((KaryHeapPQ)other);
    }
}
