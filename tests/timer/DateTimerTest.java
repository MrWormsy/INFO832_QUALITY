package timer;

import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Executable;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DateTimerTest {

    @org.junit.jupiter.api.Test
    void hasNext() {

        // I create a new DateTimer with an empty set of Integer and we should get false as there is no next value
        DateTimer emptyDates = new DateTimer(new TreeSet<>());
        Assertions.assertFalse(emptyDates.hasNext());

        // I create a new DateTimer with an empty set of Integer and we should get false as there is no next value
        DateTimer emptyLapsTime = new DateTimer(new ArrayList<>());
        Assertions.assertFalse(emptyLapsTime.hasNext());

        // If i put a null List value in the constructor we should get an exception
        // DateTimer dateTimerFromNullList = new DateTimer((List<Integer>) null);
        // Assertions.assertFalse(dateTimerFromNullList.hasNext());

        // If i put a null Set value in the constructor we should get an exception
        // DateTimer dateTimerFromNullSet = new DateTimer((Set<Integer>) null);
        // Assertions.assertFalse(dateTimerFromNullSet.hasNext());


        // Here we create a dummy set of dates to know if the has next method works
        Set<Integer> treeSet = new TreeSet();
        treeSet.add(1);
        treeSet.add(2);
        treeSet.add(3);
        DateTimer dateTimerWithSet = new DateTimer(treeSet);

        // We need to get the size of treeSet of true hasNext assertions
        for (int i = 0; i < treeSet.size(); i++) {
            Assertions.assertTrue(dateTimerWithSet.hasNext());
            dateTimerWithSet.next();
        }

        // And as there is no next value next this is Assert needs to be false
        Assertions.assertFalse(dateTimerWithSet.hasNext());

        // Here we create a dummy set of dates to know if the has next method works
        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        DateTimer dateTimerWithList = new DateTimer(arrayList);

        // We need to get the size of arrayList of true hasNext assertions
        for (int i = 0; i < arrayList.size(); i++) {
            Assertions.assertTrue(dateTimerWithList.hasNext());
            dateTimerWithList.next();
        }
        // And as there is no next value next this is Assert needs to be false
        Assertions.assertFalse(dateTimerWithList.hasNext());
    }

    @org.junit.jupiter.api.Test
    void next() {

        // If there is the iterator has no (more) next values we assert a exception was thrown
        DateTimer emptyDates = new DateTimer(new TreeSet<>());
        assertThrows(NoSuchElementException.class, () -> emptyDates.next());

        DateTimer emptyLapsTime = new DateTimer(new ArrayList<>());
        assertThrows(NoSuchElementException.class, () -> emptyLapsTime.next());

        // Here we want to get the next value of a DateTimer made from
        // ArrayList which is an Array of timelaps
        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        DateTimer dateTimerWithList = new DateTimer(arrayList);

        // As we provided an Array list the next values must be respectively 1, 2 and 3
        for (int i = 0; i < arrayList.size(); i++) {
            Assertions.assertEquals(arrayList.get(i), dateTimerWithList.next());
        }

        // We check that the next value throws an exception if there is no more values
        assertThrows(NoSuchElementException.class, () -> dateTimerWithList.next());

        // Here we create a dummy set of dates and then the timelaps will be in the iterator
        Set<Integer> treeSet = new TreeSet();
        treeSet.add(1);
        treeSet.add(2);
        treeSet.add(3);
        DateTimer dateTimerWithSet = new DateTimer(treeSet);

        // As the treeSet has as values stepped by 1 we should always get 1 as the next value because the DateTimer will have an iterator of the n+1 value minus the n one
        // And thus we should get one as 1-0 = 1, 2-1 = 1 & 3-2 = 1
        for (int i = 0; i < treeSet.size(); i++) {
            Assertions.assertEquals(1, dateTimerWithSet.next());
        }

        // We check that the next value throws an exception if there is no more values
        assertThrows(NoSuchElementException.class, () -> dateTimerWithSet.next());
    }
}