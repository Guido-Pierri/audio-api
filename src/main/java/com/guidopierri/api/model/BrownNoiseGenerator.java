package com.guidopierri.api.model;

import java.io.FileOutputStream;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;


public class BrownNoiseGenerator {

    private static final int SAMPLE_RATE = 44100; // Hz
    private static final int BITS_PER_SAMPLE = 16; // bits
    private static final int CHANNELS = 2; // mono
    private static final double DURATION_SECONDS = 30.0;
    private static final double AMPLITUDE = 0.5;
    private static final double CUTOFF_FREQUENCY = 100.0; // Hz

    public static void generateBrownNoiseWav(String fileName) throws IOException {
        AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE, BITS_PER_SAMPLE, CHANNELS, true, true);
        byte[] samples = new byte[(int) (DURATION_SECONDS * SAMPLE_RATE * BITS_PER_SAMPLE / 8)];

        double lastValue = 0;
        for (int i = 0; i < samples.length; i += 2) {
            double white = Math.random() * 2 - 1;
            double value = (lastValue + (0.02 * white)) / 1.02;
            lastValue = value;
            value *= 3.5 * AMPLITUDE;

            short sample = (short) (value * 32767);
            samples[i] = (byte) sample;
            samples[i + 1] = (byte) (sample >> 8);
        }

        AudioSystem.write(new javax.sound.sampled.AudioInputStream(new java.io.ByteArrayInputStream(samples), audioFormat, samples.length / 2),
                AudioFileFormat.Type.WAVE, new FileOutputStream(fileName));
    }
    public static void generateFilteredBrownNoiseWav(String fileName) throws IOException {
        AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE, BITS_PER_SAMPLE, CHANNELS, true, true);
        byte[] samples = new byte[(int) (DURATION_SECONDS * SAMPLE_RATE * BITS_PER_SAMPLE / 8)];

        double lastValue = 0;
        for (int i = 0; i < samples.length; i += 4) {
            double white1 = Math.random() * 2 - 1;
            double white2 = Math.random() * 2 - 1;
            double value1 = (lastValue + (0.02 * white1)) / 1.02;
            double value2 = (value1 + (0.02 * white2)) / 1.02;
            lastValue = value2;
            value2 *= 3.5 * AMPLITUDE;

            short sample1 = (short) (value1 * 32767);
            short sample2 = (short) (value2 * 32767);
            samples[i] = (byte) sample1;
            samples[i + 1] = (byte) (sample1 >> 8);
            samples[i + 2] = (byte) sample2;
            samples[i + 3] = (byte) (sample2 >> 8);
        }

        // Apply a low-pass filter to the samples using a simple RC filter
        double RC = 1.0 / (2.0 * Math.PI * CUTOFF_FREQUENCY);
        double alpha = SAMPLE_RATE / (SAMPLE_RATE + RC);
        double[] filteredSamples = new double[samples.length / 2];
        filteredSamples[0] = samples[0] / 32767.0;
        for (int i = 1; i < filteredSamples.length; i++) {
            filteredSamples[i] = alpha * (samples[2 * i] / 32767.0) + (1 - alpha) * filteredSamples[i - 1];
        }

        // Convert the filtered samples back to bytes
        byte[] filteredBytes = new byte[filteredSamples.length * 2];
        for (int i = 0; i < filteredSamples.length; i++) {
            short filteredSample = (short) (filteredSamples[i] * 32767);
            filteredBytes[2 * i] = (byte) filteredSample;
            filteredBytes[2 * i + 1] = (byte) (filteredSample >> 8);
        }

        AudioSystem.write(new javax.sound.sampled.AudioInputStream(new java.io.ByteArrayInputStream(filteredBytes), audioFormat, filteredBytes.length),
                AudioFileFormat.Type.WAVE, new FileOutputStream(fileName));
    }
}
