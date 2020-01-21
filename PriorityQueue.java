public interface PriorityQueue<DataType, PriorityType extends Comparable<PriorityType>>
{
    //add the given value using the provided priority
    public void enqueue(DataType value, PriorityType priority);

    //remove the value with the highest priority (i.e. smallest priority value)
    public DataType dequeue();

    //return the value of the element with highest priority (i.e. smallest priority value)
    public DataType peek();

    //return the priority of the element with highest priority
    //(i.e. smallest priority value)
    public PriorityType peekPriority();

    //remove everything in the priority queue
    public void clear();

    //merge two priority queues into one and return the merged priority queue
    public PriorityQueue  merge(PriorityQueue other);

    //return the size of the given priority queue
    public int size();

}
