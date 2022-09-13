package com.endava.internship.warmup.domain.service;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.ToIntFunction;

public class ArrayProcessorWithForLoops implements ArrayProcessor {

    /**
     * Return true if there are no numbers that divide by 10
     * @param input non-null immutable array of ints
     */
    @Override
    public boolean noneMatch(final int[] input) {
        for (int num : input) {
            if (num % 10 == 0)
                return false;
        }
        return true;
    }

    /**
     * Return true if at least one value in input matches the predicate
     * @param input non-null immutable array of ints
     * @param predicate invoke the predicate.test(int value) on each input element
     */
    @Override
    public boolean someMatch(final int[] input, IntPredicate predicate) {
        for (int num : input) {
            if (predicate.test(num))
                return true;
        }
        return false;
    }

    /**
     * Return true if all values processed by function, matches the predicate
     * @param input non-null immutable array of Strings. No element is null
     * @param function invoke function.applyAsInt(String value) to transform all the input elements into an int value
     * @param predicate invoke predicate.test(int value) to test the int value obtained from the function
     */
    @Override
    public boolean allMatch(final String[] input,
                            ToIntFunction<String> function,
                            IntPredicate predicate) {

        for (String s : input) {
            if (!predicate.test(function.applyAsInt(s))){
                return false;
            }
        }
        return true;
    }

    /**
     * Copy values into a separate array from specific index to stopindex
     * @param input non-null array of ints
     * @param startInclusive the first index of the element from input to be included in the new array
     * @param endExclusive the last index prior to which the elements are to be included in the new array
     * @throws IllegalArgumentException when parameters are outside of input index bounds
     */
    @Override
    public int[] copyValues(int[] input, int startInclusive, int endExclusive) throws IllegalArgumentException {
        if (startInclusive > endExclusive
                || startInclusive < 0
                || endExclusive > input.length)
        {
            throw new IllegalArgumentException("Index out of bounds");
        }

        int[] output = new int[endExclusive - startInclusive];
        for(int i = 0; i< output.length; ++i){
            output[i] = input[startInclusive + i];
        }

        return output;
    }

    /**
     * Replace even index values with their doubles and odd indexed elements with their negative
     * @param input non-null immutable array of ints
     * @return new array with changed elements
     */
    @Override
    public int[] replace(final int[] input) {
        for(int i =0; i<input.length; ++i){
            if (i % 2 == 0) input[i] *=2;
            else input[i] *= -1;
        }
        return input;
    }

    /**
     * Find the second max value in the array
     * @param input non-null immutable array of ints
     */
    @Override
    public int findSecondMax(final int[] input) {
        Arrays.sort(input);
        for (int i = input.length - 2; i >= 0; i--) {
            if (input[i] != input[i+1]){
                return input[i];
            }
        }
        throw new RuntimeException();
    }

    /**
     * Return in reverse first negative numbers, then positive numbers from array
     * @param input non-null immutable array of ints.
     * @return example: input {3, -5, 4, -7, 2 , 9}
     *                  result: {-7, -5, 9, 2, 4, 3}
     */
    @Override
    public int[] rearrange(final int[] input) {
        int[] output = new int[input.length];
        int index = 0;

        for (int i = input.length - 1; i >= 0; i--) {
            int num = input[i];
            if (num < 0)
                output[index++] = num;
        }
        for (int i = input.length - 1; i >= 0; i--) {
            int num = input[i];
            if (num >= 0)
                output[index++] = num;
        }

        return output;
    }

    /**
     * Remove (filter) all values which are smaller than (input max element - 10)
     * @param input non-null immutable array of ints
     * @return The result array should not contain empty cells!
     */
    @Override
    public int[] filter(final int[] input) {
        int max = Arrays.stream(input).max().orElseThrow(RuntimeException::new);

        return Arrays.stream(input)
                .filter(num -> num >= max - 10)
                .toArray();
    }

    /**
     * Insert values into input array at a specific index.
     * @param input non-null immutable array of ints.
     * @param startInclusive the index of input at which the first element from values array should be inserted
     * @param values the values to be inserted from startInclusive index
     * @return new array containing the combined elements of input and values
     * @throws IllegalArgumentException when startInclusive is out of bounds for input
     */
    @Override
    public int[] insertValues(final int[] input, int startInclusive, int[] values) throws IllegalArgumentException {
        if (startInclusive < 0 || startInclusive >= input.length){
            throw new IllegalArgumentException("Index: " + startInclusive);
        }

        int[] output = new int[input.length + values.length];
        for(int i=0; i<startInclusive; ++i){
           output[i] = input[i];
        }
        for(int i=startInclusive; i<values.length+startInclusive; ++i){
            output[i] = values[i - startInclusive];
        }
        for(int i=startInclusive + values.length; i<input.length + values.length;++i){
            output[i] = input[i - values.length];
        }

        return output;
    }

    /**
     * Merge two sorted input and input2 arrays so that the return values are also sorted
     * @param input first non-null array
     * @param input2 second non-null array
     * @return new array containing all elements sorted from input and input2
     * @throws IllegalArgumentException if either input or input are not sorted ascending
     */
    @Override
    public int[] mergeSortedArrays(int[] input, int[] input2) throws IllegalArgumentException {
        int[] output = new int[input.length + input2.length];

        int idx1 = 0;
        int idx2 = 0;

        while( idx1 < input.length && idx2 < input2.length){
            if (input[idx1] < input2[idx2]){
                output[idx1 + idx2] = input[idx1++];
            } else {
                output[idx1 + idx2] = input2[idx2++];
            }
            if (idx1 + idx2 > 1){
                if (output[idx1 + idx2 - 1] < output[idx1 + idx2 - 2]){
                    throw new IllegalArgumentException("Inputs are not sorted.");
                }
            }
        }

        while(idx1 < input.length){
            output[idx1+idx2] = input[idx1++];
        }
        while(idx2 < input2.length){
            output[idx1+idx2] = input2[idx2++];
        }

        return output;
    }

    /**
     * In order to execute a matrix multiplication, in this method, please validate the input data throwing exceptions for invalid input. If the the
     * input params are satisfactory, do not throw any exception.
     *
     * Please review the matrix multiplication https://www.mathsisfun.com/algebra/matrix-multiplying.html
     * @param leftMatrix the left matrix represented by array indexes [row][column]
     * @param rightMatrix the right matrix represented by array indexes [row][column]
     * @throws NullPointerException when any of the inputs are null. (arrays, rows and columns)
     * @throws IllegalArgumentException when any array dimensions are not appropriate for matrix multiplication
     */
    @Override
    public void validateForMatrixMultiplication(int[][] leftMatrix, int[][] rightMatrix) throws NullPointerException, IllegalArgumentException {
        if (leftMatrix == null || rightMatrix == null){
            throw new NullPointerException();
        }
        if (leftMatrix.length == 0 || rightMatrix.length == 0){
            throw new IllegalArgumentException();
        }
        if(Arrays.stream(leftMatrix).anyMatch(Objects::isNull)
                || Arrays.stream(rightMatrix).anyMatch(Objects::isNull)){
            throw new NullPointerException();
        }

        for (int[] leftMatrixRow : leftMatrix) {
            if (leftMatrixRow.length != rightMatrix.length) {
                throw new IllegalArgumentException("Left matrix row != right matrix column ("
                        + leftMatrixRow.length + "," + rightMatrix.length + ")");
            }
        }
    }

    /**
     * Perform the matrix multiplication as described in previous example Please review the matrix multiplication
     * https://www.mathsisfun.com/algebra/matrix-multiplying.html
     * @param leftMatrix the left matrix represented by array indexes [row][column]
     * @param rightMatrix the right matrix represented by array indexes [row][column]
     * @throws NullPointerException when any of the inputs are null. (arrays, rows and columns)
     * @throws IllegalArgumentException when any array dimensions are not appropriate for matrix multiplication
     */
    @Override
    public int[][] matrixMultiplication(final int[][] leftMatrix, final int[][] rightMatrix) throws NullPointerException, IllegalArgumentException {
        validateForMatrixMultiplication(leftMatrix, rightMatrix);
        int[][] output = new int[leftMatrix.length][rightMatrix[0].length];

        for(int k =0; k<leftMatrix.length; ++k){
            for(int i =0; i<rightMatrix[0].length; ++i){
                for(int j = 0; j<leftMatrix[i].length; ++j){
                    output[k][i] += leftMatrix[k][j] * rightMatrix[j][i];
                }
            }
        }

        return output;
    }

    /**
     * Return only distinct values in an array.
     * @param input non-null immutable array of ints.
     */
    @Override
    public int[] distinct(final int[] input) {
        return Arrays.stream(input)
                .distinct()
                .toArray();
    }
}
