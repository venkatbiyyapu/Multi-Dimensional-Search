package VXB220005;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class MDS {
    // Add fields of MDS here
    TreeMap <Long,Item> tree;
    HashMap <Item,TreeSet<Long>> table;
    private static final DecimalFormat decfor = new DecimalFormat("0.00");

    // Constructors
    public MDS() {
        tree=new TreeMap<>();
        table=new HashMap<>();
        decfor.setRoundingMode(RoundingMode.DOWN);
    }
    class Item{
        Money price;
        List<Long> list;
        Item(Money p,List<Long> l){
            price=p;
            list=l;
        }

    }

    /* Public methods of MDS. Do not change their signatures.
       __________________________________________________________________
       a. Insert(id,price,list): insert a new item whose description is given
       in the list.  If an entry with the same id already exists, then its
       description and price are replaced by the new values, unless list
       is null or empty, in which case, just the price is updated.
       Returns 1 if the item is new, and 0 otherwise.
    */
    public int insert(long id, Money price, List<Long> list) {
        Item val=tree.get(id);
        if(val!=null){
            if(list.size()==0)
                val.price = price;
            else {
                tree.remove(id);
                add(id,price,list);
            }
            return 0;
        }
        else {
            add(id, price, list);
            return 1;
        }
    }

    // b. Find(id): return price of item with given id (or 0, if not found).
    public Money find(long id) {
        Item i=tree.get(id);
        if(i!=null)
            return i.price;
        return new Money();
    }

    /*
       c. Delete(id): delete item from storage.  Returns the sum of the
       long ints that are in the description of the item deleted,
       or 0, if such an id did not exist.
    */
    public long delete(long id) {
        Item value = tree.remove(id);
        if (value != null) {
            long sum = 0;
            for (long desc : value.list) {
                sum += desc;
            }
            TreeSet<Long> set = table.get(value);
            if (set.size() > 1)
                set.remove(id);
            else
                table.remove(value);
            return sum;
        }
        return 0;
    }


    /*
       d. FindMinPrice(n): given a long int, find items whose description
       contains that number (exact match with one of the long ints in the
       item's description), and return lowest price of those items.
       Return 0 if there is no such item.
    */
    public Money findMinPrice(long n) {
        Money min_price = new Money(Long.MAX_VALUE,Integer.MAX_VALUE);
        if(tree!=null) {
            for (Map.Entry<Long, Item> entry : tree.entrySet()) {
                Item i = entry.getValue();
                if (i.list.contains(n))
                    if (i.price.compareTo(min_price) < 0)
                        min_price = i.price;
            }
            return min_price;
        }
        return new Money();
    }

    /*
       e. FindMaxPrice(n): given a long int, find items whose description
       contains that number, and return highest price of those items.
       Return 0 if there is no such item.
    */
    public Money findMaxPrice(long n) {
        Money max_price = new Money();
        if(tree!=null) {
            for (Map.Entry<Long, Item> entry : tree.entrySet()) {
                Item i = entry.getValue();
                if (i.list.contains(n))
                    if (i.price.compareTo(max_price) > 0)
                        max_price = i.price;
            }
            return max_price;
        }
        return new Money();
    }

    /*
       f. FindPriceRange(n,low,high): given a long int n, find the number
       of items whose description contains n, and in addition,
       their prices fall within the given range, [low, high].
    */
    public int findPriceRange(long n, Money low, Money high) {
        int count = 0;
        if(tree!=null) {
            for (Map.Entry<Long, Item> entry : tree.entrySet()) {
                Item i = entry.getValue();
                if (i.list.contains(n))
                    if ((i.price.compareTo(low) >= 0) && (i.price.compareTo(high) <= 0)) {
                        count++;
                    }
            }
        }
        return count;
    }

    /*
       g. PriceHike(l,h,r): increase the price of every product, whose id is
       in the range [l,h] by r%.  Discard any fractional pennies in the new
       prices of items.  Returns the sum of the net increases of the prices.
    */
    public Money priceHike(long l, long h, double rate) {
        double np;
        double op;
        double ni = 0;
        if(tree!=null) {
            for (long id = l; id <= h; id++) {
                Item val = tree.get(id);
                if (val != null) {
                    double cen = val.price.c;
                    op = val.price.dollars()+((cen/100));
                    np = (op * (100 + rate)) / 100;
                    np = Double.valueOf(decfor.format(np));
                    ni = ni + (np - op);
                    String a = String.format("%.2f", np);
                    Money newPrice = new Money(a);
                    val.price = newPrice;
                }
            }
            String b = String.format("%.2f", ni);
            return new Money(b);
        }
        return new Money();
    }


    public void add(Long id, Money price, List<Long> list){
        Item i=new Item(price,list);
        tree.put(id,i);
        TreeSet<Long> set = table.get(i);
        if(set==null)
            table.put(i,new TreeSet<>());
        else
            set.add(id);
    }

    /*
      h. RemoveNames(id, list): Remove elements of list from the description of id.
      It is possible that some of the items in the list are not in the
      id's description.  Return the sum of the numbers that are actually
      deleted from the description of id.  Return 0 if there is no such id.
    */
    public long removeNames(long id, List<Long> list) {
        long sum = 0;
        if(tree!=null) {
            Item i=tree.get(id);
            if(i!=null)
            {
                for (long l:list) {
                    if (i.list.contains(l)){
                        i.list.remove(l);
                        sum += l;
                    }
                }
                return sum;
            }

        }

        return 0;

    }

    // Do not modify the Money class in a way that breaks LP4Driver.java
    public static class Money implements Comparable<Money> {
        long d;
        int c;

        public Money() {
            d = 0;
            c = 0;
        }

        public Money(long d, int c) {
            this.d = d;
            this.c = c;
        }

        public Money(String s) {
            String[] part = s.split("\\.");
            int len = part.length;
            if (len < 1) {
                d = 0;
                c = 0;
            } else if (len == 1) {
                d = Long.parseLong(s);
                c = 0;
            } else {
                d = Long.parseLong(part[0]);
                c = Integer.parseInt(part[1]);
                if (part[1].length() == 1) {
                    c = c * 10;
                }
            }
        }

        public long dollars() {
            return d;
        }

        public int cents() {
            return c;
        }

        public int compareTo(Money other) { // Complete this, if needed
            if(this.dollars()< other.dollars())
                return -1;
            else if(this.dollars()>other.dollars())
                return 1;
            else{
                if(this.cents()<other.cents())
                    return -1;
                else if(this.cents()>other.cents())
                    return 1;
                else
                    return 0;
            }
        }


        public String toString() {
            if (c < 10) return d + ".0" + c;
            return d + "." + c;
        }
    }

}
