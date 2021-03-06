package com.example.aidan.tilegameredo;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.aidan.tilegameredo.particles.Particle;
import com.example.aidan.tilegameredo.particles.winParticle;

import java.util.ArrayList;

public class ParticleManager {
    private static ArrayList<Particle> particles = new ArrayList<Particle>();

    public static void paint(Canvas canvas, Paint paint) {
        for (int p = 0; p < particles.size(); p++) {
            particles.get(p).paint(canvas, paint);
        }
        for (int p = 0; p < particles.size(); p++) {
            if (particles.get(p).isDone()) {
                particles.remove(p);
                p--;
            }
        }
    }

    public static void addParticle(Particle particle) {
        particles.add(particle);
    }

    public static void clear() {
        particles.clear();
    }

    public static ArrayList<Particle> getParticles() {
        return particles;
    }

    public static void pickup() {
        for (int p = 0; p < particles.size(); p++) {
            if (particles.get(p) instanceof winParticle) {
                particles.remove(p);
                p--;
            }
        }
    }
}
