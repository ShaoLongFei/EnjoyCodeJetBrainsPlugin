package com.liuyue.plugin;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.Clip;
import java.io.InputStream;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyTypedHandler implements TypedActionHandler {

    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8, 8, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
    private final int[] speeds;
    private final Clip[] clips;

    public MyTypedHandler(int[] speeds, Clip[] clips) {
        this.speeds = speeds;
        this.clips = clips;
    }


    @Override
    public void execute(@NotNull Editor editor, char c, @NotNull DataContext dataContext) {
//        Document document = editor.getDocument();
//        Project project = editor.getProject();
//        Runnable runnable = () -> document.insertString(0, "Typed\n");
//        WriteCommandAction.runWriteCommandAction(project, runnable);

        if (!editor.isViewer()) {
            Document doc = editor.getDocument();
            doc.startGuardedBlockChecking();
            try {
                String str = String.valueOf(c);
                CommandProcessor.getInstance().setCurrentCommandName(EditorBundle.message("typing.in.editor.command.name", new Object[0]));
                EditorModificationUtil.typeInStringAtCaretHonorMultipleCarets(editor, str, true);
            } catch (ReadOnlyFragmentModificationException var9) {
                EditorActionManager.getInstance().getReadonlyFragmentModificationHandler(doc).handle(var9);
            } finally {
                doc.stopGuardedBlockChecking();
            }
        }
        // todo 音乐播放放到线程池中去做
        InputStream inputStream = getClass().getResourceAsStream("/music/miao.mp3");
        try {
            Player player = new Player(inputStream);
            player.play();
            player.close();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
}
