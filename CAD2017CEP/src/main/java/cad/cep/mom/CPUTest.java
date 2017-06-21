package cad.cep.mom;

import java.util.Arrays;

import org.eclipse.paho.client.mqttv3.MqttException;

import cad.cep.exceptions.MoMException;

/**
 * 
 * Important inforamtion: This class has no purpose in the real app. It is used to test the cpu since we can�t get esper to use much cpu
 */
public class CPUTest implements Runnable{

	private int n;
	public CPUTest(int n){
		this.n = n;
		
	}
	public int calculateNDamenForScaling(int n){
		System.out.println("Calc ");
		int noSol = 0;
		int[] x = new int[n];
		int k = 0;
		x[k] = -1;

		while(k >= 0){

			boolean valid = false;

			while(!valid && x[k] < n-1){
				x[k]++;
				valid = true;
				for(int i = 0; i < k; i++){
					if(x[i] == x[k] || Math.abs(x[i] - x[k]) == k - i){ //untereinander || diagonale
						valid = false;
					}
				}
			}

			if(valid){
				if(k == n-1){
					System.out.println(Arrays.toString(x));
					noSol++;
				} else {
					x[++k] = -1;
				}
			} else {
				k--;
			}
		}

		System.out.printf("n: %d, Solutions: %d", n, noSol);
		System.out.println("");
		return noSol;
	}
	@Override
	public void run() {
		try {
			int sol =this.calculateNDamenForScaling(n);
			MQTTMom tempMom = (MQTTMom) MomFactory.createOrLoadMom();
			System.out.println(sol);
			tempMom.sendInt(sol, "cep/ndamenresult");
		} catch (MoMException | MqttException e) {
			e.printStackTrace();
		}
		
	}
}
