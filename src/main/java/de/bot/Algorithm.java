package de.bot;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Algorithm {



    /**
     *
     * @param list
     * @param searchedNumber
     * @return position of searched number
     */
    public static int binarySearch(List<Integer> list, int searchedNumber){
        if(!list.isEmpty()){
            Collections.sort(list);
            int leftBorder = 0;
            int rightBorder = list.size()-1;
            int middle;
            while(leftBorder<= rightBorder){
                middle = leftBorder + ((rightBorder - leftBorder)/2);
                if(list.get(middle)==searchedNumber){
                    return middle;
                } else if (list.get(middle) < searchedNumber) {
                    leftBorder= middle + 1;

                }else {
                    rightBorder = middle - 1;
                }
            }
        }
        return -1;
    }

    /**
     *
     * @param list
     * @return a sorted list
     */
    public static List<Integer> selectionSort(List<Integer> list){
        int lowestNumber = 0;
        int position = 0;

        for(int i = 0; i < list.size(); i++){
            lowestNumber = list.get(i);
            position = i;
            for(int j = i; j < list.size(); j++) {
                if(lowestNumber > list.get(j)){
                    lowestNumber = list.get(j);
                    position = j;

                }
            }
            changeNumbers(list, position, i);
        }

        return list;

    }



    /**
     *
     * @param list
     * @return a sorted list
     */
    public static List<Integer> insertionSort(List<Integer> list){
        List<Integer> newList = new LinkedList<>();
        int compareNumber = 0;
        int position = 0;
        newList.add(list.get(0));
        for(int i = 1; i<list.size(); i++){
            compareNumber = list.get(i);
            position = 0;
            for(int j=0; j< newList.size(); j++){
                if(compareNumber>newList.get(j)){
                    position = j+1;
                }else{
                    break;
                }
            }
            newList.add(position, compareNumber);
        }
        return newList;
    }

    /**
     *
     * @param list
     * @return a sorted list
     */
    public static List<Integer> bubbleSort(List<Integer> list){
        boolean newChange;
        for(int i = list.size()-1; i >0 ; i--){
            newChange = false;
            for(int j = 0 ; j< i; j++ ){
                if(list.get(j)> list.get(j+1)){
                    changeNumbers(list, j, j+1);
                    newChange = true;
                }
            }
            if(!newChange){
                break;
            }
        }
        return list;
    }


    /**
     * change Numbers of a list for indices a and b
     * @param list
     * @param a
     * @param b
     */
    public static void changeNumbers(List<Integer> list, int a, int b){
        if(a==b){
            return;
        }
        int aValue = list.get(a);
        int bValue = list.get(b);
        list.set(a, bValue);
        list.set(b, aValue);

    }

    /**
     *
     * @param list
     * @return a sorted list
     */
    public static List<Integer> mergeSort(List<Integer> list){
        if(list.size() <= 1){
            return list;
        }else{
            int middle = list.size()/2;
            List<Integer> leftList = list.subList(0, middle);
            List<Integer> rightList = list.subList(middle , list.size());
            leftList = mergeSort(leftList);
            rightList = mergeSort(rightList);
            return merge(leftList, rightList);
        }
    }

    /**
     * merges sublists in mergeSort
     * @param leftList
     * @param rightList
     * @return the sorted list
     */
    public static List<Integer> merge(List<Integer> leftList, List<Integer> rightList){
        List<Integer> newList = new LinkedList<>();
        int leftIndex = 0;
        int rightIndex = 0;
        while(leftIndex < leftList.size() && rightIndex < rightList.size()){
            if(leftList.get(leftIndex) <= rightList.get(rightIndex)){
                newList.add(leftList.get(leftIndex));
                leftIndex++;
            }else{
                newList.add(rightList.get(rightIndex));
                rightIndex++;
            }
        }
        while (leftIndex < leftList.size()){
            newList.add(leftList.get(leftIndex));
            leftIndex++;
        }
        while (rightIndex< rightList.size()){
            newList.add(rightList.get(rightIndex));
            rightIndex++;
        }

        return newList;
    }

    /**
     *
     * @param list
     * @param low
     * @param high
     * @return a sorted list
     */
    public static List<Integer> quickSort(List<Integer> list, int low, int high){
        if(low < high){
            int pivotIndex = part(list, low, high);
            quickSort(list, low, pivotIndex - 1);
            quickSort(list,pivotIndex + 1, high);
        }

        return list;
    }

    /**
     * for the quick sort algorithm
     * @param list
     * @param low
     * @param high
     *
     */
    public static int part(List<Integer> list, int low, int high){
        int i = low;
        int j = high - 1;
        int pivot = list.get(high);

        while(i<j){
            while (i<j && list.get(i) <= pivot){
                i++;
            }
            while(j>i && list.get(j) > pivot){
                j--;
            }
            if (list.get(i) > list.get(j)){
                changeNumbers(list, i, j);
            }
        }

        if(list.get(i) > pivot){
            changeNumbers(list, i, high);
        }else{
            i = high;
        }
        return i;
    }

    /**
     *
     * @param list
     * @return list of integers which are in the string
     */
    public static List<Integer> getStringAsList(String list){
        if(!list.isEmpty()){
            List<Integer> numbers = new LinkedList<>();
            String value = "";
            for(int i = 0; i<list.length(); i++){
                if(Character.isDigit(list.charAt(i)) || Character.toString(list.charAt(i)).equals("-") && Character.isDigit(list.charAt(i+1))){
                    value = value + list.charAt(i);
                    if(i == list.length() - 1 || !Character.isDigit(list.charAt(i + 1))){
                        numbers.add(Integer.parseInt(value));
                        value = "";
                    }
                }
            }
            return numbers;

        }else{
            return null;
        }

    }

    /**
     *
     * @param list
     * @return a string which includes integers of a list
     */

    public static String getListAsString(List<Integer> list){
        String numbers = "";
        for(int i=0; i< list.size(); i++){
            numbers += list.get(i) + " ";
        }
        return numbers;


    }
}


