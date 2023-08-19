package de.bot;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class AlgorithmTest {

    List<Integer> positiveTestList = new LinkedList<>();
    List<Integer> negativeTestList = new LinkedList<>();

    public AlgorithmTest(){
        positiveTestList.add(5);
        positiveTestList.add(3);
        positiveTestList.add(6);
        positiveTestList.add(2);
        positiveTestList.add(1);
        positiveTestList.add(52);
        negativeTestList.add(4);
        negativeTestList.add(-4);
        negativeTestList.add(3);
        negativeTestList.add(52);
        negativeTestList.add(6);
        negativeTestList.add(-12);

    }
    @Test
    public void binarySearch() {
        AlgorithmTest tester = new AlgorithmTest();
        assertEquals(3, Algorithm.binarySearch(positiveTestList, 5));
        assertEquals(-1, Algorithm.binarySearch(positiveTestList, 4));
        assertEquals(2, Algorithm.binarySearch(negativeTestList, 3));
    }


    @Test
    public void selectionSort() {
        AlgorithmTest tester = new AlgorithmTest();
        Algorithm.selectionSort(positiveTestList);
        for(int i = 0; i < positiveTestList.size() - 1; i++){
            assertTrue(positiveTestList.get(i) < positiveTestList.get(i+1));
        }
        Algorithm.selectionSort(negativeTestList);
        for(int i = 0; i < negativeTestList.size() - 1; i++){
            assertTrue(negativeTestList.get(i)< negativeTestList.get(i+1));
        }
    }

    @Test
    public void insertionSort() {
        AlgorithmTest tester = new AlgorithmTest();
        List<Integer> newList = Algorithm.insertionSort(positiveTestList);
        List<Integer> negativeNewList = Algorithm.insertionSort(negativeTestList);
        for(int i = 0; i < newList.size() - 1; i++){
            assertTrue(newList.get(i) < newList.get(i+1));
        }
        for(int i = 0; i < negativeNewList.size() - 1; i++){
            assertTrue(negativeNewList.get(i) < negativeNewList.get(i+1));
        }
    }

    @Test
    public void bubbleSort() {
        AlgorithmTest tester = new AlgorithmTest();
        Algorithm.bubbleSort(positiveTestList);
        for(int i = 0; i < positiveTestList.size() - 1; i++){
            assertTrue(positiveTestList.get(i) < positiveTestList.get(i+1));
        }
        Algorithm.bubbleSort(negativeTestList);
        for(int i = 0; i < negativeTestList.size() - 1; i++){
            assertTrue(negativeTestList.get(i)< negativeTestList.get(i+1));
        }
    }

    @Test
    public void changeNumbers() {
        AlgorithmTest tester = new AlgorithmTest();
        Algorithm.changeNumbers(positiveTestList, 2 ,3);
        assertTrue(positiveTestList.get(2)== 2 && positiveTestList.get(3) == 6);
    }

    @Test
    public void mergeSort() {
        AlgorithmTest tester = new AlgorithmTest();
        List<Integer> newPositveList = Algorithm.mergeSort(positiveTestList);
        List<Integer> newNegativeList = Algorithm.mergeSort(newPositveList);
        for(int i = 0; i < newPositveList.size() - 1; i++){
            assertTrue(newPositveList.get(i) < newPositveList.get(i+1));
        }
        for(int i = 0; i < newNegativeList.size() - 1; i++){
            assertTrue(newNegativeList.get(i)< newNegativeList.get(i+1));
        }
    }

    @Test
    public void quickSort() {
        AlgorithmTest tester = new AlgorithmTest();
        List<Integer> positiveNewList = Algorithm.quickSort(positiveTestList, 0,5);
        List<Integer> negativeNewList = Algorithm.quickSort(negativeTestList, 0,5);
        for(int i = 0; i < positiveNewList.size() - 1; i++){
            assertTrue(positiveNewList.get(i) < positiveNewList.get(i+1));
        }
        for(int i = 0; i < negativeNewList.size() - 1; i++){
            assertTrue(negativeNewList.get(i)< negativeNewList.get(i+1));
        }
    }

    @Test
    public void getStringAsList() {
        AlgorithmTest tester = new AlgorithmTest();
        List<Integer> positiveCompareList = Algorithm.getStringAsList("5 3 6 2 1 52");
        List<Integer> negativeCompareList = Algorithm.getStringAsList("4 -4 3 52 6 -12");
        List<Integer> failPositiveCompareList = Algorithm.getStringAsList(" 5 3 6 s2 1a 52 shjhg ");
        List<Integer> failNegativeCompareList = Algorithm.getStringAsList(" 4 -4ds 3 s52 6a -12 shjhg ");
        for(int i = 0; i < positiveTestList.size(); i++){
            assertTrue(positiveTestList.get(i) == positiveCompareList.get(i));
        }
        for(int i = 0; i < negativeCompareList.size(); i++){
            assertTrue(negativeCompareList.get(i) == negativeCompareList.get(i));
        }
        for(int i = 0; i < positiveTestList.size(); i++){
            assertTrue(positiveTestList.get(i) == failPositiveCompareList.get(i));
        }
        for(int i = 0; i < negativeCompareList.size(); i++){
            assertTrue(negativeCompareList.get(i) == failNegativeCompareList.get(i));
        }

    }

    @Test
    public void getListAsString() {
        AlgorithmTest tester = new AlgorithmTest();
        assertTrue(Algorithm.getListAsString(positiveTestList).equals("5 3 6 2 1 52 "));
        assertTrue(Algorithm.getListAsString(negativeTestList).equals("4 -4 3 52 6 -12 "));
    }
}