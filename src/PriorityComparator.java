import java.util.Comparator;

public class PriorityComparator<Integer> implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        int first = (int) o1;
        int snd = (int) o2;

        if(first < snd)
            return 1;
        else if(first > snd)
            return -1;
        else
            return 0;
    }
}
