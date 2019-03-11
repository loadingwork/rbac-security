package com.iveiv.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.apache.commons.rng.RandomProviderState;
import org.apache.commons.rng.RestorableUniformRandomProvider;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.core.RandomProviderDefaultState;
import org.apache.commons.rng.sampling.CollectionSampler;
import org.apache.commons.rng.sampling.PermutationSampler;
import org.apache.commons.rng.sampling.distribution.ContinuousSampler;
import org.apache.commons.rng.sampling.distribution.GaussianSampler;
import org.apache.commons.rng.sampling.distribution.MarsagliaNormalizedGaussianSampler;
import org.apache.commons.rng.simple.RandomSource;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;

public class Demo1 {
	
	
	@Test
	public void test1() {
		UniformRandomProvider rng = RandomSource.create(RandomSource.MT);
		
		boolean isOn = rng.nextBoolean();
		
		System.out.println(isOn);
		
		byte[] a = new byte[47];
		rng.nextBytes(a);
		
	}
	
	
	@Test
	public void test2() {
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("Apache");
		list.add("Commons");
		list.add("RNG");

		CollectionSampler<String> sampler = new CollectionSampler<String>(RandomSource.create(RandomSource.MWC_256), list);
		String word = sampler.sample();
		System.err.println(word);
		
	}
	
	@Test
	public void test3() {
		PermutationSampler sampler = new PermutationSampler(RandomSource.create(RandomSource.KISS),
                6, 3);
	//3 elements from a shuffling of the (0, 1, 2, 3, 4, 5) tuplet.
	int[] random = sampler.sample();
	
	System.err.println(random);
	
	}
	
	
	@Test
	public void test4() {
		ContinuousSampler sampler = new GaussianSampler(new MarsagliaNormalizedGaussianSampler(RandomSource.create(RandomSource.MT_64)),
                45.6, 2.3);
		double random = sampler.sample();
		System.err.println(random);
	}
	
	@Test
	public void test5() throws Exception {
		RandomSource source = RandomSource.MT_64; // Known source identifier.

		RestorableUniformRandomProvider rngOrig = RandomSource.create(source); // Original RNG instance.

		// Save and serialize state.
		RandomProviderState stateOrig = rngOrig.saveState();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(((RandomProviderDefaultState) stateOrig).getState());

		// Deserialize state.
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		RandomProviderState stateNew = new RandomProviderDefaultState((byte[]) ois.readObject());

		RestorableUniformRandomProvider rngNew = RandomSource.create(source); // New RNG instance from the same "source".

		// Restore original state on the new instance.
		rngNew.restoreState(stateNew);
	}
	
	@Test
	public void test6() throws Exception {
		RestorableUniformRandomProvider rng = RandomSource.create(RandomSource.WELL_512_A);
		RandomProviderDefaultState state = (RandomProviderDefaultState) rng.saveState();
		double x = rng.nextDouble();
		rng.restoreState(state);
		double y = rng.nextDouble(); // x == y.
		
		System.err.println(x);
		System.err.println(y);
		
		System.out.println(state);
		
		System.err.println(state.getState());
		System.err.println(Base64.encodeBase64String(state.getState()));
		
		
	}
	
	

}
