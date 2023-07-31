package com.guidopierri.api.model;
/*
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class WhiteNoiseGenerator {
    private static final int SAMPLE_RATE = 44100;
    private static final int BITS_PER_SAMPLE = 16;
    private static final int CHANNELS = 1;
    private static final double AMPLITUDE = 1.0;

    public static AudioInputStream generateWhiteNoise(int duration) {
        byte[] audioData = new byte[SAMPLE_RATE * BITS_PER_SAMPLE / 8 * CHANNELS * duration];
        Random random = new Random();
        for (int i = 0; i < audioData.length; i++) {
            audioData[i] = (byte) (random.nextDouble() * AMPLITUDE * 127);
        }

        AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE, BITS_PER_SAMPLE, CHANNELS, true, false);
        return new AudioInputStream(new ByteArrayInputStream(audioData), audioFormat, audioData.length / audioFormat.getFrameSize());
    }
}


import java.io.*;
        import javax.sound.sampled.*;

public class WhiteNoiseGenerator {

    public   main(String[] args) {
        //int duration = 5; // in seconds
        int sampleRate = 44100; // CD-quality audio
        int bitDepth = 16; // CD-quality audio
        int numChannels = 1; // mono audio

        byte[] audioData = generateWhiteNoise(duration, sampleRate, bitDepth, numChannels);

        saveAsWav(audioData, "whitenoise.wav", sampleRate, bitDepth, numChannels);

        System.out.println("White noise saved as whitenoise.wav");
    }

    public static AudioInputStream generateWhiteNoise(int duration, int sampleRate, int bitDepth, int numChannels) {
        int numSamples = duration * sampleRate * numChannels;
        byte[] audioData = new byte[numSamples * (bitDepth / 8)];

        for (int i = 0; i < numSamples; i++) {
            short sample = (short) (Math.random() * (1 << bitDepth) - (1 << (bitDepth - 1)));
            for (int j = 0; j < numChannels; j++) {
                audioData[(i * numChannels + j) * (bitDepth / 8)] = (byte) (sample & 0xff);
                audioData[(i * numChannels + j) * (bitDepth / 8) + 1] = (byte) ((sample >> 8) & 0xff);
            }
        }

        return audioData;
    }

    public static void saveAsWav(byte[] audioData, String filename, int sampleRate, int bitDepth, int numChannels) {
        try {
            AudioFormat format = new AudioFormat(sampleRate, bitDepth, numChannels, true, false);
            AudioInputStream audioInputStream = new AudioInputStream(new ByteArrayInputStream(audioData), format,
                    audioData.length / (bitDepth / 8));
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
*/
import java.io.FileOutputStream;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;

public class WhiteNoiseGenerator {

    private static final int SAMPLE_RATE = 44100; // Hz
    private static final int BITS_PER_SAMPLE = 16; // bits
    private static final int CHANNELS = 2; // mono
    private static final double DURATION_SECONDS = 30.0;
    private static final double AMPLITUDE = 0.5;

    public static void generateWhiteNoiseWav(String fileName) throws IOException {
        AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE, BITS_PER_SAMPLE, CHANNELS, true, true);
        byte[] samples = new byte[(int) (DURATION_SECONDS * SAMPLE_RATE * BITS_PER_SAMPLE / 8)];

        for (int i = 0; i < samples.length; i += 2) {
            short value = (short) (Math.random() * 32767 * AMPLITUDE);
            samples[i] = (byte) value;
            samples[i + 1] = (byte) (value >> 8);
        }

        AudioSystem.write(new javax.sound.sampled.AudioInputStream(new java.io.ByteArrayInputStream(samples), audioFormat, samples.length / 2),
                AudioFileFormat.Type.WAVE, new FileOutputStream(fileName));
    }
}


