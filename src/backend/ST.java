package backend;

public interface ST <Key, Value>{
	
	Value get(Key key);
	
    void put(Key key, Value value);
    
    boolean contains(Key key);
    
    void delete(Key key);
    
    Iterable<Key> keys();
}
