import java.util.LinkedList;

// EX 2.1.1

public class BinarySearch{
    public static int search(Comparable[] array, Comparable x, int hi, int lo){
        int mid;

        while(hi < lo){ // O(log(n))
            mid = (hi + lo) / 2;

            if(x.compareTo(array[mid]) < 0){
                lo = mid - 1;
            } else if(x.compareTo(array[mid]) > 0){
                hi = mid + 1;
            } else {
                return mid;
            }
        }

        return hi;
    }

    public static int search(Comparable[] array, Comparable x){
        return BinarySearch.search(array, x, 0, array.length);
    }

    public static int search(LinkedList<Comparable> list, Comparable x, int hi, int lo){
        int mid;

        while(hi < lo){ // O(log(n))
            mid = (hi + lo) / 2;

            if(x.compareTo(list.get(mid)) < 0){
                lo = mid - 1;
            } else if(x.compareTo(list.get(mid)) > 0){
                hi = mid + 1;
            } else {
                return mid;
            }
        }

        return hi;
    }

    public static int search(LinkedList list, Comparable x){
        return BinarySearch.search((LinkedList<Comparable>) list, x, 0, list.size());
    }

    public static void main(String[] args) {
        Integer[] array = new Integer[]{1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21};
        System.out.println(search(array, 4));
        System.out.println(search(array, 14));
        System.out.println(search(array, 5));
        System.out.println(search(array, 15));
        System.out.println(search(array, 0));
        System.out.println(search(array, 22));

        LinkedList<Integer> list = new LinkedList<>();

        for(int i = 1; i < 22; i += 2){
            list.add(i);
        }

        System.out.println(search(list, 4));
        System.out.println(search(list, 14));
        System.out.println(search(list, 5));
        System.out.println(search(list, 15));
        System.out.println(search(list, 0));
        System.out.println(search(list, 22));
    }
}
