package ru.dpwg.itnews.domain;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class GetArticlesWorker extends Worker {
    public GetArticlesWorker(@NonNull @NotNull Context context,
                             @NonNull @NotNull WorkerParameters workerParams
    ) {
        super(context, workerParams);
    }

    @NonNull
    @NotNull
    @Override
    public Result doWork() {
        System.out.println("Работа началась");
        System.out.println("1");
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("2");
        return Result.success();
    }
}
