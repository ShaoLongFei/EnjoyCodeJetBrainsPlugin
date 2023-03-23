package com.liuyue.plugin;

import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class ProjectListener implements ProjectManagerListener {
    @Override
    public void projectOpened(@NotNull Project project) {
        ProjectManagerListener.super.projectOpened(project);

        EditorActionManager actionManager = EditorActionManager.getInstance();
        TypedAction typedAction = actionManager.getTypedAction();
        int[] speeds = new int[]{10, 20, 30, 40};
        Clip[] clips = new Clip[speeds.length + 1];
        for (int i = 0; i <= speeds.length; i++) {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/music/miao.mp3"));
                clip.open(audioStream);
                clips[i] = clip;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        typedAction.setupHandler(new MyTypedHandler(speeds, clips));
    }

    @Override
    public void projectClosed(@NotNull Project project) {
        ProjectManagerListener.super.projectClosed(project);
    }

    @Override
    public void projectClosing(@NotNull Project project) {
        ProjectManagerListener.super.projectClosing(project);
    }

    @Override
    public void projectClosingBeforeSave(@NotNull Project project) {
        ProjectManagerListener.super.projectClosingBeforeSave(project);
    }
}
