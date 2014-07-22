package com.kanhan.had.utils;

import java.util.Random;

public class MathUtils {
	/** 
	 * Generate number from  0 - RANGE
	 * 
	 * @param RANGE
	 * @return int
	 */
	public static int genRandomNumber(final int RANGE){
		Random r = new Random();
		return Math.abs(r.nextInt()) % RANGE ;
	}
	/**
	 * Generate an array of number , which can be set to unique
	 * If Size > Range , it will return null
	 * 
	 * @param RANGE
	 * @param SIZE
	 * @param unique
	 * @return int[ ]
	 */
	public static int[] genRandomNumberArray(final int RANGE,final int SIZE ,boolean unique){
		if(SIZE > RANGE)
			return null;
		Random r = new Random();
		int [] array = new int[SIZE];
		boolean isSame = false;
		for(int i =0 ; i< SIZE ;i ++){
			if(unique && !isSame){
				int tmp = -1;
				do{
					tmp =  Math.abs(r.nextInt()) % RANGE;
					isSame = false;
					for(int j=0 ; j < SIZE ; j ++){
						if(array[j] == tmp){
							isSame = true;
							break;
						}
					}
				}while(isSame);
				
				// Must be unique after while loop
				array[i] = tmp;
			}
			else
				array[i] = Math.abs(r.nextInt()) % RANGE;
		}
		return array ;
	}
}
